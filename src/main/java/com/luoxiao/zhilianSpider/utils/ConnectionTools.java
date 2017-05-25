package com.luoxiao.zhilianSpider.utils;

import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionTools {
    //智联主页链接
	public static final String ZHILIAN_URL = "http://sou.zhaopin.com/jobs/searchresult.ashx";
	//黑名单列表
	public static final String[] blackList = { "北京优才创智科技有限公司武汉分公司",
			"湖北省软帝职业培训学校", "武汉冲锋互联网技术服务有限公司", "武汉学思成管理咨询有限公司", "武汉市华信智原科技有限公司",
			"武汉斯泽维信息技术有限公司", "武汉科瑞亚设计有限公司", "武汉智动天下信息科技有限公司","武汉拓福软件有限公司","武汉弘博职业培训学校","武汉代尔长星科技有限责任公司","武汉文思华腾信息服务有限公司","武汉瑞才教育科技有限公司","青岛东软睿道教育信息技术有限公司武汉办事处","北京艺耘诺布投资管理有限公司","武汉龙才企业管理咨询有限公司","南昌市高新职业培训学校","黄冈升技使能教育咨询有限公司","武汉卓研云软件科技有限公司"};
	//需要抓取的页数.推荐爬取25页左右
	public static final int pages = 10;
	// 获取数据库连接对象
	public java.sql.Connection getConnection() throws ClassNotFoundException,
			SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://localhost:3306/zhilian_spider";
		String username = "root";
		String password = "1111";
		java.sql.Connection conn = DriverManager.getConnection(url, username,
				password);
		return conn;
	}
}
