package server;

import com.sun.jdi.connect.Connector;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 14:30 2020/2/13 0013
 */
public class Bootstrap {

    private int port;

    private Map<String,HttpServlet> servletMap = new HashMap<String,HttpServlet>();

    private Map<String,Mapper> mapperMap = new HashMap<String,Mapper>();

    private Mapper mapper;

    public void start() throws Exception {

        /*MyRunnable myRunnable = new MyRunnable();
        Thread thread = new Thread(myRunnable);
        thread.start();*/

        // 加载解析server.xml
        loadServer();
        // 加载解析相关的配置，web.xml
        loadServlet();

        // 监听端口号
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Minicat start on port：" + port);

        /**
         * MiniCat V1.0
         * 接收请求，返回固定字符串
         */
        /*while (true){
            Socket socket = serverSocket.accept();
            OutputStream outputStream = socket.getOutputStream();
            String output = "Hello world!";
            String headerInfo = HttpProtocolUtil.getHttpHeader200(output.getBytes().length) + output;
            outputStream.write(headerInfo.getBytes());
            socket.close();
        }*/

        /**
         * MiniCat V2.0
         * 封装Request和Response对象，返回html静态资源文件
         */
        /*while (true){
            Socket socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream();

            Request request = new Request(inputStream);

            Response response = new Response(socket.getOutputStream());

            response.outputHtml(request.getUrl());

            System.out.println("over");
            socket.close();

        }*/

        /**
         * MinCat V3.0
         * 请求动态资源
         */
        /*while (true){
            Socket socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream();

            Request request = new Request(inputStream);

            Response response = new Response(socket.getOutputStream());

            // 静态资源处理
            if(servletMap.get(request.getUrl()) == null) {
                response.outputHtml(request.getUrl());
            }else{
                // 动态资源servlet请求
                HttpServlet httpServlet = servletMap.get(request.getUrl());
                httpServlet.service(request,response);
            }

            socket.close();
        }*/

        //ServerSocket serverSocket = new ServerSocket (port) ;
        /*while (true) {
            Socket client = serverSocket.accept();
            try {
                InputStream inputStream = client.getInputStream();
                // 获取输入流中的请求信息
                int count = 0;
                // 由于网络间断性，请求到达时，数据可能还没到达，此时available()可能为空
                while (count == 0){
                    count = inputStream.available();
                }

                BufferedReader in = new BufferedReader (
                        new InputStreamReader(inputStream)
                );
                OutputStream out = client.getOutputStream();
                in.lines ().forEach(line -> {
                    try {
                        out.write(line.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                client. close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/

        /**
         * 多线程版本
         */
        /*while (true){
            Socket socket = serverSocket.accept();
            MyThread myThread = new MyThread(socket,servletMap);
            myThread.start();
        }*/

        // 定义⼀个线程池
        int corePoolSize = 10;
        int maximumPoolSize =50;
        long keepAliveTime = 100L;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(50);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                workQueue,
                threadFactory,
                handler
        );

        /**
         * 多线程版本:使用线程池
         */
        /*
        while (true){
            Socket socket = serverSocket.accept();
            MyThread myThread = new MyThread(socket,servletMap);
            threadPoolExecutor.execute(myThread);
        }*/

        /**
         * Minicat V4.0，在已有Minicat基础上进⼀步扩展，模拟出webapps部署效果
         */
        while (true){
            Socket socket = serverSocket.accept();
            MyThread myThread = new MyThread(socket,mapper);
            threadPoolExecutor.execute(myThread);
        }

    }

    /**
     * 加载解析server.xml
     */
    private void loadServer(){
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("server.xml");
        SAXReader saxReader = new SAXReader();

        try {
            Document document = saxReader.read(resourceAsStream);
            Element serverElment = document.getRootElement();
            // 获取Connector中port值
            Element connector = (Element)serverElment.selectSingleNode("//Connector");
            port = Integer.valueOf(connector.attributeValue("port"));
            // 获取Host
            List<Element> hostList = serverElment.selectNodes("//Host");
            Map<String,Mapper.Host> hostMap = new HashMap<String,Mapper.Host>();
            for (int i = 0; i < hostList.size(); i++) {
                Element element =  hostList.get(i);
                // 获取Host的name
                String name = element.attributeValue("name");
                // 获取Host的appBase
                String appBase = element.attributeValue("appBase");

                // 获取Context
                List<Element> contextList = element.selectNodes("//Context");
                Map<String,Mapper.Context> contextMap = new HashMap<String,Mapper.Context>();
                for (Element context : contextList) {
                    // 获取docBase
                    String docBase = context.attributeValue("docBase");
                    System.out.println("docBase :" + docBase);
                    // 获取path
                    String path = context.attributeValue("path");
                    System.out.println("path : " + path);
                    Map<String,Mapper.Wrapper> wrapperMap = new HashMap<String,Mapper.Wrapper>();
                    Mapper.Context con = new Mapper.Context(docBase,wrapperMap);
                    contextMap.put(path, con);
                }
                Mapper.Host host = new Mapper.Host(appBase, contextMap);
                hostMap.put(name, host);
            }
            mapper = new Mapper(hostMap);

        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }

    /**
     * 加载解析web.xml，初始化Servlet
     */
    private void loadServlet() {

        try {
            Map<String, Mapper.Host> hostMap = mapper.getHostMap();
            for(Mapper.Host host : hostMap.values()){
                Map<String, Mapper.Context> contextMap = host.getContextMap();
                for (Mapper.Context context:contextMap.values()) {
                    // 获取需要加载的项目的web.xml的路径
                    String webPath = host.getAppBase() + context.getDocBase();
                    if(webPath.contains("/")){
                        webPath = webPath.replace("/", java.io.File.separator);
                    }
                    if(webPath.contains("\\")){
                        webPath = webPath.replace("\\", java.io.File.separator);
                    }
                    if (!webPath.endsWith(java.io.File.separator)) {
                        webPath = webPath + java.io.File.separator;
                    }
                    System.out.println("webPath = " + webPath);
                    // 根据项目的绝对路径加载项目的web.xml
                    InputStream resourceAsStream = new FileInputStream(webPath + "web.xml");
                    //InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("web.xml");
                    SAXReader saxReader = new SAXReader();

                    Document document = saxReader.read(resourceAsStream);
                    Element rootElement = document.getRootElement();

                    List<Element> selectNodes = rootElement.selectNodes("//servlet");
                    Map<String,Mapper.Wrapper> wrapperMap = new HashMap<String,Mapper.Wrapper>();
                    for (int i = 0; i < selectNodes.size(); i++) {
                        Element element =  selectNodes.get(i);
                        // <servlet-name>lagou</servlet-name>
                        Element servletnameElement = (Element) element.selectSingleNode("servlet-name");
                        String servletName = servletnameElement.getStringValue();
                        // <servlet-class>server.LagouServlet</servlet-class>
                        Element servletclassElement = (Element) element.selectSingleNode("servlet-class");
                        String servletClass = servletclassElement.getStringValue();
                        // 根据servlet-name的值找到url-pattern
                        Element servletMapping = (Element) rootElement.selectSingleNode("/web-app/servlet-mapping[servlet-name='" + servletName + "']");
                        // /lagou
                        String urlPattern = servletMapping.selectSingleNode("url-pattern").getStringValue();

                        URL url = new URL("file:\\" + webPath); //储存文件目录的地址
                        URLClassLoader classLoader = new URLClassLoader(new URL[]{url});  //classloader从哪个目录找
                        Object o  = classLoader.loadClass(servletClass).newInstance();//找哪个class文件 注意不带后缀名
                        //servletMap.put(urlPattern, (HttpServlet) Class.forName(servletClass).newInstance());
                        wrapperMap.put(urlPattern, new Mapper.Wrapper((HttpServlet)o));
                    }
                    context.setWrapperMap(wrapperMap);
                }

            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws Exception {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.start();
    }
}
