package com.luoxiao.zhilianSpider.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.luoxiao.zhilianSpider.bean.JobInfoBean;

/**
 * @Description:
 * @author losho
 * @date 2017年5月25日
 */
public class ParseThread implements Runnable {

    String pageNo = "0";
    Object obj = new Object();
    // 定义爬取页数，推荐90以内
    int maxPage = 5;
    Integer pageNoInt = Integer.valueOf(pageNo);
    
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "：starting........");
        while (pageNoInt < maxPage) {
            try {
                parse();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * @Description: 抓取方法
     * @param:   
     * @return: 
     * @throws
     */
    public void parse() throws InterruptedException {
       synchronized (obj) {
            if (pageNoInt < maxPage) {
                pageNoInt++;
                try {
                    praseContent("java", "武汉");
                } catch (ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                }
            } else if(pageNoInt == maxPage) {
                return;
            }
        }
    }

    // 抓取的总数据量
    public void praseContent(String job, String position) throws ClassNotFoundException, SQLException {
        String url = ConnectionTools.ZHILIAN_URL;
        int timeOut = 60 * 3000;
        Document document = null;
        try {
            // 封装请求参数
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("jl", position);
            paramMap.put("kw", job);
            paramMap.put("sm", "0");
            paramMap.put("p", "1");

            document = Jsoup.connect(url).data(paramMap).timeout(timeOut).get();
            pageNo = document.select("div.pagesDown>ul>li>a.current").text();
            // pageNoInt += 1;
            paramMap.put("p", String.valueOf(pageNoInt));
            System.out.println("-----------------------------------------------"
                    + Thread.currentThread().getName() + "WORKING AT "+ new Date().getTime()+"---------------------------------------------------------------");
            // 开始解析并存入数据库
            
            System.out.println("------------------------------>>查询参数:[" + Thread.currentThread().getName()  +" "
                    + paramMap + "]<<------------------------------------------");
            parseAndSave(document);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param 解析document对象
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void parseAndSave(Document document) throws ClassNotFoundException, SQLException {
        Connection connection;
        Statement stmt;
        Elements jobElements = document.select("table.newlist:gt(0)>tbody>tr:eq(0)");
        for (Element jobElement : jobElements) {
            JobInfoBean bean = new JobInfoBean();
            // 封装bean
            bean.setCompanyName(jobElement.select("td.gsmc>a").text());
            bean.setUrl(jobElement.select("td.gsmc a").get(0).attr("href"));
            bean.setWages(jobElement.select("td.zwyx").text());
            bean.setLocation(jobElement.select("td.gzdd").text());
            bean.setDate(jobElement.select("td.gxsj span").text());
            String sql = "INSERT INTO JOB_INFO(COMPANYNAME,URL,WAGES,LOCATION,DATE)VALUES ('" + bean.getCompanyName()
                    + "','" + bean.getUrl() + "','" + bean.getWages() + "','" + bean.getLocation() + "','"
                    + bean.getDate() + "')";
            connection = new ConnectionTools().getConnection();
            stmt = connection.createStatement();
            Set<String> blacklistSet1 = getSet(ConnectionTools.blackList);
            // 过滤黑名单公司
            if (blacklistSet1.contains(bean.getCompanyName())) {
                System.out.println(Thread.currentThread().getName() + "*****"
                        + "[发现黑名单] -----\\w(ﾟДﾟ)w \\w(ﾟДﾟ)w \\w(ﾟДﾟ)w=====>>  " + bean.getCompanyName());
            } else {
                stmt.execute(sql);
                System.out.println(Thread.currentThread().getName() + "====>" + "存入到数据库成功！" + bean);
            }
            stmt.close();
            connection.close();
        }

    }

    /**
     * @Description: 黑名单列表转化为Set
     * @param: 黑名单列表
     * @return: 黑名单Set
     */
    public Set<String> getSet(String[] list) {
        Set<String> blacklistSet = new HashSet<String>();
        for (String s : list) {
            blacklistSet.add(s);
        }
        return blacklistSet;
    }
}
