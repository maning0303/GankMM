package com.maning.gankmm.bean;

/**
 * Created by maning on 2017/5/8.
 */

public class CommonItemEntity {

    private String title;
    private String desc;

    public CommonItemEntity() {
    }

    public CommonItemEntity(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "MobItemEntity{" +
                "title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
