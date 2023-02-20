package com.xiefuzhong.community.testtime.model;

/**
 * ClassName:Goods
 * Package:com.seckill.model
 * Description:
 * Date:2022/8/27 16:39
 * author:abc
 */
public class Goods {
    private String randomName;
    private Integer store;

    public String getRandomName() {
        return randomName;
    }

    public Goods setRandomName(String randomName) {
        this.randomName = randomName;
        return this;
    }

    public Integer getStore() {
        return store;
    }

    public Goods setStore(Integer store) {
        this.store = store;
        return this;
    }
}
