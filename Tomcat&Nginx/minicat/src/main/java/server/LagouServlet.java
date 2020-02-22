package server;

import java.io.IOException;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 22:11 2020/2/15 0015
 */
public class LagouServlet extends HttpServlet{

    @Override
    public void doGet(Request request, Response response) throws InterruptedException {
        //Thread.sleep(10000);
        String content = "<h1>LagouServlet get</h1>";
        try {
            response.output((HttpProtocolUtil.getHttpHeader200(content.getBytes().length) + content));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(Request request, Response response) {
        String content = "<h1>LagouServlet post</h1>";
        try {
            response.output((HttpProtocolUtil.getHttpHeader200(content.getBytes().length) + content));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() throws Exception {

    }

    @Override
    public void destory() throws Exception {

    }
}
