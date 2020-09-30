package com.maning.gankmm.bean.web;

/**
 * Created by maning on 16/6/22.
 */
public class CategoryTitleBean {

    private String url;

    private String title;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "CategoryTitleBean{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
