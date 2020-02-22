package server;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 22:09 2020/2/15 0015
 */
public abstract class HttpServlet implements Servlet {

    public abstract void doGet(Request request,Response response) throws InterruptedException;

    public abstract void doPost(Request request,Response response);

    @Override
    public void service(Request request, Response response) throws Exception {
        if("GET".equalsIgnoreCase(request.getMethod())) {
            doGet(request,response);
        }else{
            doPost(request,response);
        }
    }
}
