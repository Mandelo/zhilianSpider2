package com.luoxiao.zhilianSpider.bean;

/**
 * @Description: bean
 * @author losho
 * @date 2017年5月25日
 */
public class JobInfoBean {

    // 公司名
    private String companyName;
    // 网址
    private String url;
    // 工资待遇/月
    private String wages;
    // 公司地点
    private String location;
    // 发布日期
    private String date;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWages() {
        return wages;
    }

    public void setWages(String wages) {
        this.wages = wages;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "JobInfoBean [ companyName=" + companyName + ", wages=" + wages + ", location=" + location + ", date="
                + date + "]";
    }

}
