package com.lagou.edu.factory;

import com.lagou.edu.annotation.Autowired;
import com.lagou.edu.annotation.Component;
import com.lagou.edu.annotation.Service;
import com.lagou.edu.annotation.Transactional;
import com.lagou.edu.utils.FindClassFileUtils;
import com.lagou.edu.utils.TransactionManager;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;

import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author:LiPing
 * @description：实例化有注解注释的类
 * @date:Created in 13:08 2020/1/8 0008
 */
public class MyBeanFactory {

    // 存储实例化后的对象
    private static Map<String,Object> map = new HashMap<>();

    static {
        try {
            Reflections reflection = new Reflections("com.lagou.edu");
            // 获得此路径下所有带有自定义Service注解的类
            Set<Class<?>> service = reflection.getTypesAnnotatedWith(Service.class);
            // 将这些类通过反射 新建对象并保存到map
            for (Class<?> aClass : service) {
                Object bean = bean = aClass.newInstance();
                Service annotation = aClass.getAnnotation(Service.class);
                // 判断注解有无value属性值的情况
                if (!"".equals(annotation.value())){
                    map.put(annotation.value(), bean);
                }else{
                    String beanName = getBeanName(aClass.getName());
                    map.put(beanName, bean);
                }
            }

            // 获得此路径下所有带有自定义Component注解的类
            Set<Class<?>> component = reflection.getTypesAnnotatedWith(Component.class);
            // 将这些类通过反射 新建对象并保存到map
            for (Class<?> aClass : component) {
                Object bean = bean = aClass.newInstance();
                Component annotation = aClass.getAnnotation(Component.class);
                // 判断注解有无value属性值的情况
                if (!"".equals(annotation.value())){
                    map.put(annotation.value(), bean);
                }else{
                    String beanName = getBeanName(aClass.getName());
                    map.put(beanName, bean);
                }
            }

            // 获得此路径下所有带有自定义Autowired注解的类
            Set<Field> autowired = new Reflections("com.lagou.edu", new FieldAnnotationsScanner())
                    .getFieldsAnnotatedWith(Autowired.class);
            for (Field field : autowired) {
                // 获取属性所在类在map中的key
                String parentName = getBeanName(getValue(field.getDeclaringClass().getName()));
                // 参数所在类的实例化对象
                Object o = map.get(parentName);
                if (o == null){
                    // 通过反射技术实例化对象
                    Class<?> aClass = aClass = Class.forName(field.getDeclaringClass().getName());
                    //实例化后的对象
                    o = aClass.newInstance();
                }
                String implClassName;
                // 如果该参数是个接口，就获取它的实现类
                if (field.getType().isInterface()){
                    implClassName = FindClassFileUtils.getImplClassByInterface(field.getType().getName());
                }else {
                    implClassName = field.getType().getName();
                }

                // 判断实现类的注解有没有value
                implClassName = getValue(implClassName);

                // 实例化参数
                Object object = map.get(getBeanName(implClassName));
                if (!field.isAccessible())
                    field.setAccessible(true);
                try {
                    field.set(o,object);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                // 把处理之后的父对象重新放到map中
                map.put(parentName, o);
            }

            // 获得此路径下所有带有自定义Transactional注解的类
            Set<Class<?>> transactional = reflection.getTypesAnnotatedWith(Transactional.class);
            Object o = map.get(getBeanName(getValue("com.lagou.edu.utils.TransactionManager")));
            // 将这些类通过反射 新建对象并保存到map
            for (Class<?> aClass : transactional) {
                Object bean = map.get(getBeanName(getValue(aClass.getName())));
                // 判断service层是否实现接口的情况【jdk还是cglib】
                if (bean.getClass().getInterfaces().length > 0){
                    bean = getJdkProxy((TransactionManager) o,bean);
                }else{
                    bean = getCglibProxy((TransactionManager) o,bean);
                }
                // 把处理之后的对象重新放到map中
                map.put(getBeanName(getValue(aClass.getName())), bean);
            }

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取beanName
     * @param s
     * @return
     */
    public static String getBeanName(String s){
        s = s.substring(s.lastIndexOf(".") + 1,s.length());
        if(Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    /**
     * 根据类路径判断该类的注解有没有值
     * @param classPath
     * @return
     */
    public static String getValue(String classPath){
        try {
            Class<?> implClass = Class.forName(classPath);
            Component componentImpl = implClass.getAnnotation(Component.class);
            Service serviceImpl = implClass.getAnnotation(Service.class);
            if (componentImpl != null && !"".equals(componentImpl.value())){
                classPath = componentImpl.value();
            }else if (serviceImpl != null && !"".equals(serviceImpl.value())){
                classPath = serviceImpl.value();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return classPath;
    }

    /**
     * 对外提供获取实例对象的接口
     * @param id
     * @return
     */
    public static Object getBean(String id){
        return  map.get(id);
    }

    /**
     * 使用jdk动态代理增强事务控制
     * @param o
     * @return
     */
    public static Object getJdkProxy(TransactionManager transactionManager,Object o){
        return Proxy.newProxyInstance(o.getClass().getClassLoader(), o.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object ob = null;
                try{
                    // 开启事务(关闭事务的自动提交)
                    transactionManager.beginTransaction();
                    ob = method.invoke(o, args);
                    // 提交事务
                    transactionManager.commit();
                }catch (Exception e) {
                    e.printStackTrace();
                    // 回滚事务
                    transactionManager.rollback();
                    // 抛出异常便于上层servlet捕获
                    throw e;
                }
                return ob;
            }
        });
    }

    /**
     * 使用CGLIB动态代理增强事务控制
     * @param o
     * @return
     */
    public static Object getCglibProxy(TransactionManager transactionManager,Object o){
        return Enhancer.create(o.getClass(), new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                Object ob = null;
                try{
                    // 开启事务(关闭事务的自动提交)
                    transactionManager.beginTransaction();
                    ob = method.invoke(o, objects);
                    // 提交事务
                    transactionManager.commit();
                }catch (Exception e) {
                    e.printStackTrace();
                    // 回滚事务
                    transactionManager.rollback();
                    // 抛出异常便于上层servlet捕获
                    throw e;
                }
                return ob;
            }
        });
    }
}
