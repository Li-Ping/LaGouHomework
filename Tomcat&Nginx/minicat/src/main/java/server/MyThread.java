package server;

import java.io.InputStream;
import java.net.Socket;
import java.util.Map;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 10:39 2020/2/16 0016
 */
public class MyThread extends Thread {

    private Socket socket;
    /*private Map<String,HttpServlet> servletMap;

    public MyThread(Socket socket, Map<String, HttpServlet> servletMap) {
        this.socket = socket;
        this.servletMap = servletMap;
    }*/

    private Mapper mapper;

    public MyThread(Socket socket, Mapper mapper) {
        this.socket = socket;
        this.mapper = mapper;
    }

    @Override
    public void run() {
        try{
            InputStream inputStream = socket.getInputStream();

            // 封装Request对象和Response对象
            Request request = new Request(inputStream);
            Response response = new Response(socket.getOutputStream());

            String url = request.getUrl();
            String[] split = url.split("/");

            // 静态资源处理
            if(mapper.getHostMap().get("localhost").getContextMap().get("/"+split[1]).getWrapperMap().get("/"+split[2]) == null) {
                response.outputHtml(request.getUrl());
            }else{
                // 动态资源servlet请求
                //HttpServlet httpServlet = servletMap.get(request.getUrl());
                HttpServlet httpServlet = mapper.getHostMap().get("localhost").getContextMap().get("/"+split[1]).getWrapperMap().get("/"+split[2]).getHttpServlet();
                httpServlet.service(request,response);
            }

            socket.close();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
