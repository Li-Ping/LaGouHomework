package com.lagou.pojo;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 21:46 2020/4/1 0001
 */
public class Account {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "{\"Account\":{"
                + "\"id\":"
                + id
                + "}}";

    }
}
