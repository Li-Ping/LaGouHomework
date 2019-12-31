package com.lagou.io;

import java.io.InputStream;

public class Resources {

    // 获取字节流
    public static InputStream getResourcesAsStream(String path){
        InputStream inputStream = Resources.class.getClassLoader().getResourceAsStream(path);
        return inputStream;
    }
}
