package com.luoxiao.zhilianSpider.main;

import java.io.IOException;
import java.sql.SQLException;
import com.luoxiao.zhilianSpider.utils.ParseThread;

/**
 * @Description: 多线程爬虫
 * @author losho
 * @date 2017年5月25日
 */
public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {

        ParseThread parseThread = new ParseThread();
        Thread th1 = new Thread(parseThread);
        th1.setName("爬虫 [1]号");
        Thread th2 = new Thread(parseThread);
        th2.setName("爬虫 [2]号");
        Thread th3 = new Thread(parseThread);
        th3.setName("爬虫 [3]号");
        Thread th4 = new Thread(parseThread);
        th4.setName("爬虫 [4]号");
        th1.start();
        th2.start();
        th3.start();
        th4.start();

    }

}
