package com.xiefuzhong.community;

import java.io.IOException;

/**
 * 测试wkhtmltoimage生成图片
 */
public class WkTests {

    public static void main(String[] args) {
        String cmd = "D:/wkhtmltopdf/bin/wkhtmltoimage --quality 75  https://www.nowcoder.com D:/wkhtmltopdf/data/wk-images/1.png";
        try {
            //异步执行，下面这句话交给操作系统底层执行，和java程序并行执行
            Runtime.getRuntime().exec(cmd);
            //属于java程序执行
            System.out.println("ok.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
