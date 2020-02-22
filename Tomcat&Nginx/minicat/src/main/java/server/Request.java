package server;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 20:54 2020/2/15 0015
 */
public class Request {

    // 请求方式：GET / POST
    private String method;

    // 请求路径
    private String url;

    // 输入流，其他属性从输入流中解析出来
    private InputStream inputStream;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public Request(InputStream inputStream) throws IOException {
        this.inputStream = inputStream;

        // 获取输入流中的请求信息
        int count = 0;
        // 由于网络间断性，请求到达时，数据可能还没到达，此时available()可能为空
        while (count == 0){
            count = inputStream.available();
        }
        byte[] bytes = new byte[count];
        inputStream.read(bytes);

        String inputStr = new String(bytes);
        // 获取第一行请求头信息
        String firstLineStr = inputStr.split("\\n")[0];  // GET / HTTP/1.1
        String[] strings = firstLineStr.split(" ");

        this.method = strings[0];
        this.url = strings[1];


        System.out.println("=====>>method:" + method);
        System.out.println("=====>>url:" + url);
    }

}
