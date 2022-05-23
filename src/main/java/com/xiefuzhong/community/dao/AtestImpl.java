package com.xiefuzhong.community.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

/**
 * @Description:
 * @Author: xiefuzhong
 * @CreateTime: 2022/5/22 0022 11:28
 */

@Repository
//@Repository("beanName")
//@Primary  //优先级高
public class AtestImpl implements  AtestDao {
    @Override
    public String select() {
        return "数据库数据";
    }
}
