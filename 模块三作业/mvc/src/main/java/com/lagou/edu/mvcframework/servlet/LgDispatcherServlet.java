package com.lagou.edu.mvcframework.servlet;

import com.lagou.edu.mvcframework.annotations.*;
import com.lagou.edu.mvcframework.pojo.Handler;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author:LiPing
 * @description：前端控制器
 * @date:Created in 15:50 2020/1/14 0014
 */
public class LgDispatcherServlet extends HttpServlet {

    // 配置文件信息
    private Properties properties = new Properties();

    // 缓存扫描到的类的全限定类名
    private List<String> classNameList = new ArrayList<>();

    // IOC容器
    private Map<String,Object> ioc = new HashMap<String,Object>();

    // handlerMapping
    // 存储url和Method之间的映射关系
    //private Map<String,Method> handlerMapping = new HashMap<>();
    private List<Handler> handlerMapping = new ArrayList<>();

    /**
     * 初始化
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        // 加载配置文件
        String initParameter = config.getInitParameter("contextConfigLocation");
        doLoadConfig(initParameter);
        // 扫描相关类与注解
        doScan(properties.getProperty("scanPackage"));
        // 初始化Bean对象（注解方式实现ioc容器）
        doInstance();
        // 依赖注入
        doAutowired();
        // 初始化组件（构造HandlerMapping处理器映射器，将配置好的url和Method建立映射关系）
        initHandlerMapping();
        // 等待请求进入，处理请求
    }

    /**
     * 构造映射器
     */
    private void initHandlerMapping() {
        if(ioc.isEmpty()) {return;}
        for(Map.Entry<String,Object> entry: ioc.entrySet()) {
            // 获取ioc中当前遍历的对象的class类型
            Class<?> aClass = entry.getValue().getClass();
            if(!aClass.isAnnotationPresent(LagouController.class)) {continue;}
            String baseUrl = "";
            if(aClass.isAnnotationPresent(LagouRequestMapping.class)) {
                LagouRequestMapping annotation = aClass.getAnnotation(LagouRequestMapping.class);
                baseUrl = annotation.value(); // 等同于/demo
            }
            // 获取方法
            Method[] methods = aClass.getMethods();
            for (int i = 0; i < methods.length; i++) {
                Method method = methods[i];
                //  方法没有标识LagouRequestMapping，就不处理
                if(!method.isAnnotationPresent(LagouRequestMapping.class)) {continue;}
                // 如果标识，就处理
                LagouRequestMapping annotation = method.getAnnotation(LagouRequestMapping.class);
                String methodUrl = annotation.value();  // /query
                String url = baseUrl + methodUrl;    // 计算出来的url /demo/query
                // handlerMapping.put(url, method);
                // 把method所有信息及url封装为一个Handler
                Handler handler = new Handler(entry.getValue(),method, Pattern.compile(url));
                // 计算方法的参数位置信息  // query(HttpServletRequest request, HttpServletResponse response,String name)
                Parameter[] parameters = method.getParameters();
                for (int j = 0; j < parameters.length; j++) {
                    Parameter parameter = parameters[j];
                    if(parameter.getType() == HttpServletRequest.class || parameter.getType() == HttpServletResponse.class) {
                        // 如果是request和response对象，那么参数名称写HttpServletRequest和HttpServletResponse
                        handler.getParamIndexMapping().put(parameter.getType().getSimpleName(),j);
                    }else{
                        handler.getParamIndexMapping().put(parameter.getName(),j);  // <name,2>
                    }
                }
                // 建立url和method之间的映射关系（map缓存起来）
                handlerMapping.add(handler);
            }
        }
    }

    /**
     * 依赖注入
     */
    private void doAutowired() {
        // 判断ioc容器是否有对象，有对象在进行依赖注入
        if (ioc.isEmpty()) return;

        // 遍历ioc容器。查看对象中是否有@Autowired注解，有就进行依赖关系维护
        for(Map.Entry<String,Object> entry:ioc.entrySet()){
            // 获取bean对象中的字段信息
            Field[] declaredFields = entry.getValue().getClass().getDeclaredFields();
            // 遍历判断处理
            for (int i = 0; i < declaredFields.length; i++) {
                Field declaredField = declaredFields[i];
                if(!declaredField.isAnnotationPresent(LagouAutowired.class)) {
                    continue;
                }
                // 有该注解
                LagouAutowired annotation = declaredField.getAnnotation(LagouAutowired.class);
                // 需要注入的bean的id
                String beanName = annotation.value();
                if("".equals(beanName.trim())) {
                    // 没有配置具体的bean id，那就需要根据当前字段类型注入（接口注入）  IDemoService
                    beanName = declaredField.getType().getName();
                }

                // 开启赋值
                declaredField.setAccessible(true);

                try {
                    declaredField.set(entry.getValue(),ioc.get(beanName));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    /**
     * 实例化bean对象
     * 基于className（缓存的全限定类名）以及反射技术，完成对象的创建和管理
     */
    private void doInstance(){
        try {
            // 判断classNameList是否有类名
            if (classNameList.size() == 0) return;
            for (String className : classNameList) {
                // 反射
                Class<?> aClass = Class.forName(className);
                // 区分注解 @Controller/@Service
                // @Controller
                if (aClass.isAnnotationPresent(LagouController.class)){
                    // 类名 DemoController
                    String simpleName = aClass.getSimpleName();
                    String lowerFirstSimpleName = lowerFirst(simpleName);
                    Object o = aClass.newInstance();
                    ioc.put(lowerFirstSimpleName, o);
                }else if(aClass.isAnnotationPresent(LagouService.class)){
                    LagouService annotation = aClass.getAnnotation(LagouService.class);
                    // 获取注解value
                    String value = annotation.value();
                    // 如果未指定id(value为空)，就以类名首字母小写为id
                    if ("".equals(value)){
                        value = lowerFirst(aClass.getSimpleName());
                    }
                    ioc.put(value, aClass.newInstance());

                    // 用接口名为id，放入ioc容器，便于后期根据接口类型注入
                    Class<?>[] interfaces = aClass.getInterfaces();
                    for (Class<?> anInterface : interfaces) {
                        ioc.put(anInterface.getName(), aClass.newInstance());
                    }
                }else{
                    continue;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 首字母小写方法
     * @param str
     * @return
     */
    public String lowerFirst(String str) {
        char[] chars = str.toCharArray();
        if('A' <= chars[0] && chars[0] <= 'Z') {
            chars[0] += 32;
        }
        return String.valueOf(chars);
    }

    /**
     * 扫描相关类与注解
     * @param scanPackage
     */
    private void doScan(String scanPackage) {
        // 获取classpath在磁盘上的路径
        // 将 . 替换为 /
        String scanPackagePath = Thread.currentThread().getContextClassLoader().getResource("").getPath() + scanPackage.replaceAll("\\.", "/");
        File pack = new File(scanPackagePath);
        File[] files = pack.listFiles();
        if(files != null){
            for (File file : files) {
                if (file.isDirectory()){
                    // 递归扫描子包
                    doScan(scanPackage + "." + file.getName());
                } else if(file.getName().endsWith(".class")){
                    // 扫描到class文件，就将其全限定类名存到缓存的list中
                    String className = scanPackage + "." + file.getName().replaceAll(".class", "");
                    classNameList.add(className);
                }
            }
        }
    }

    /**
     * 加载配置文件
     * @param initParameter
     */
    private void doLoadConfig(String initParameter){
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(initParameter);
        try {
            // 将配置文件中的信息读取到properties对象中
            properties.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 接收处理请求
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    /**
     * 处理请求
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 处理请求
        // 获取URI
        //String requestURI = req.getRequestURI();
        //Method method = handlerMapping.get(requestURI);
        // 反射调用，需要对象和参数
        //method.invoke();


        // 根据uri获取到能够处理当前请求的hanlder（从handlermapping中（list））
        Handler handler = getHandler(req);
        if(handler == null) {
            resp.getWriter().write("404 not found");
            return;
        }

        // 判断handler方法有没有配置访问权限
        if(handler.getMethod().isAnnotationPresent(Security.class)) {
            Security annotation = handler.getMethod().getAnnotation(Security.class);
            // 获取handler方法上配置的用户
            String[] values = annotation.value();
            List<String> userList = Arrays.asList(values);
            // handler方法没有配置访问权限，就判断其所在类有没有配置访问权限
            // 没有就执行，有就判断其url中的用户是否有权限访问

            // 获取request中的username参数
            String username = req.getParameter("username");
            if (!userList.contains(username)){
                resp.setHeader("Content-type", "text/html;charset=UTF-8");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write("该用户没有访问权限！");
                return;
            }
        }



        // 参数绑定
        // 获取所有参数类型数组，这个数组的长度就是我们最后要传入的args数组的长度
        Class<?>[] parameterTypes = handler.getMethod().getParameterTypes();
        // 根据上述数组长度创建一个新的数组（参数数组，是要传入反射调用的）
        Object[] paraValues = new Object[parameterTypes.length];
        // 以下就是为了向参数数组中塞值，而且还得保证参数的顺序和方法中形参顺序一致
        Map<String, String[]> parameterMap = req.getParameterMap();
        // 遍历request中所有参数  （填充除了request，response之外的参数）
        for(Map.Entry<String,String[]> param: parameterMap.entrySet()) {
            // name=1&name=2   name [1,2]
            String value = StringUtils.join(param.getValue(), ",");  // 如同 1,2
            // 如果参数和方法中的参数匹配上了，填充数据
            if(!handler.getParamIndexMapping().containsKey(param.getKey())) {continue;}
            // 方法形参确实有该参数，找到它的索引位置，对应的把参数值放入paraValues
            Integer index = handler.getParamIndexMapping().get(param.getKey());//name在第 2 个位置
            paraValues[index] = value;  // 把前台传递过来的参数值填充到对应的位置去
        }

        int requestIndex = handler.getParamIndexMapping().get(HttpServletRequest.class.getSimpleName()); // 0
        paraValues[requestIndex] = req;

        int responseIndex = handler.getParamIndexMapping().get(HttpServletResponse.class.getSimpleName()); // 1
        paraValues[responseIndex] = resp;

        // 最终调用handler的method属性
        try {
            handler.getMethod().invoke(handler.getController(),paraValues);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    /**
     * 根据请求查询需要执行的handler方法
     * @param req
     * @return
     */
    private Handler getHandler(HttpServletRequest req) {
        if(handlerMapping.isEmpty()){return null;}
        String url = req.getRequestURI();
        for(Handler handler: handlerMapping) {
            Matcher matcher = handler.getPattern().matcher(url);
            if(!matcher.matches()){continue;}
            return handler;
        }
        return null;
    }

}
