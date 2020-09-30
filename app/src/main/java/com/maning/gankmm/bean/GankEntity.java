package com.maning.gankmm.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by maning on 16/6/2.
 */
public class GankEntity implements Serializable{

    private static final long serialVersionUID = -2600330151554512031L;

    /**
     * _id : 5f12cc060bd5529b54e71328
     * author : blue
     * category : GanHuo
     * createdAt : 2020-07-18 18:16:38
     * desc : 类似京东、淘宝首页体验的嵌套滑动吸顶效果
     * images : ["https://gank.io/images/15781a5c1376483ba1327c7b7fefec7b"]
     * likeCounts : 0
     * publishedAt : 2020-07-18 18:16:38
     * stars : 1
     * title : NestedCeilingEffect
     * type : Android
     * url : https://github.com/solartcc/NestedCeilingEffect
     * views : 22
     */

    private String _id;
    private String author;
    private String category;
    private String createdAt;
    private String desc;
    private String likeCounts;
    private String publishedAt;
    private String stars;
    private String title;
    private String type;
    private String url;
    private String views;
    private List<String> images;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLikeCounts() {
        return likeCounts;
    }

    public void setLikeCounts(String likeCounts) {
        this.likeCounts = likeCounts;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
