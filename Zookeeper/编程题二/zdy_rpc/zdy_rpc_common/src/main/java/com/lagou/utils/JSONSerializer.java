package com.lagou.utils;

import com.alibaba.fastjson.JSON;

/**
 * @author:LiPing
 * @description：采用JSON的方式，定义JSONSerializer的实现类
 * @date:Created in 15:32 2020/3/18 0018
 */
public class JSONSerializer implements Serializer{

    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }


    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}
