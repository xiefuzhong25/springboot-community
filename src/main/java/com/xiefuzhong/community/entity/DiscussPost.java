package com.xiefuzhong.community.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @Description:
 * @Author: xiefuzhong
 * @CreateTime: 2022/5/22 0022 22:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DiscussPost {
    private int id;
    private int userId;
    private String title;
    private String content;
    private int type;
    private int status;
    private Date createTime;
    private int commentCount;
    private double score;

}
