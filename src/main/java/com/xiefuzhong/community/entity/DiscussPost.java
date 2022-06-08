package com.xiefuzhong.community.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.util.Date;

/**
 * @Description:
 * @Author: xiefuzhong
 * @CreateTime: 2022/5/22 0022 22:16
 *
 *  es 7.0需注意地方 shards ,replicas 在document弃用，改用setting注入
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setting(shards = 6,replicas = 3)
@Document(indexName = "discusspost")
public class DiscussPost {

    @Id
    private int id;

    @Field(type = FieldType.Integer)
    private int userId;

    //analyzer:存储数据的时候指定分析器为ik_max_word,能够拆分更多的词条来；
    //searchAnalyzer:搜索数据的时候指定分析器为 ik_smart,使用更聪明的分析器来查询
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String title;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String content;

    @Field(type = FieldType.Integer)
    private int type;

    @Field(type = FieldType.Integer)
    private int status;

    @Field(type = FieldType.Date)
    private Date createTime;

    @Field(type = FieldType.Integer)
    private int commentCount;

    @Field(type = FieldType.Double)
    private double score;

}
