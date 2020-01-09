package com.lagou.edu.pojo;

/**
 * @author:LiPing
 * @description：账户实体
 * @date:Created in 12:30 2020/1/4 0004
 */
public class Account {

    /**
     * 卡号
     */
    private String cardNo;

    /**
     * 用户名
     */
    private String name;

    /**
     * 金额
     */
    private int money;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getCardNo() { return cardNo; }

    public void setCardNo(String cardNo) { this.cardNo = cardNo;}

    @Override
    public String toString() {
        return "Account{" +
                "cardNo='" + cardNo + '\'' +
                ", name='" + name + '\'' +
                ", money=" + money +
                '}';
    }
}
