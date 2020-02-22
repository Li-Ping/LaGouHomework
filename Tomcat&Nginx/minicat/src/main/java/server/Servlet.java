package server;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 22:09 2020/2/15 0015
 */
public interface Servlet {

    void init() throws Exception;

    void destory() throws Exception;

    void service(Request request,Response response) throws Exception;
}
