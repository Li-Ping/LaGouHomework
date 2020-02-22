package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 21:07 2020/2/15 0015
 */
public class Response {

    private OutputStream outputStream;

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public Response() {
    }

    public Response(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    /**
     * 使用输出流输出指定字符串
     * @param content
     * @throws IOException
     */
    public void output(String content) throws IOException {
        outputStream.write(content.getBytes());
    }


    /**
     * 输出静态资源
     * @param path
     */
    public void outputHtml(String path) throws IOException {

        System.out.println("path = " + path);
        // 获取静态资源文件的绝对路径
        String absoluteResourcePath = StaticResourceUtil.getAbsolutePath(path);

        File file = new File(absoluteResourcePath);

        System.out.println("absoluteResourcePath = " + absoluteResourcePath);

        if (file.exists() && file.isFile()){
            System.out.println("file is exists");
            // 输出静态资源
            StaticResourceUtil.outputStaticResource(new FileInputStream(file),outputStream);
        }else{
            System.out.println("404 not found");
            output(HttpProtocolUtil.getHttpHeader404());
        }


    }
}
