package com.maning.gankmm.bean.rolltools;

import java.util.List;

/**
 * @author : maning
 * @date : 2020-10-15
 * @desc :
 */
public class HistoryTodayBean extends RollToolsBaseBean {


    private List<DataEntity> data;

    public List<DataEntity> getData() {
        return data;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * picUrl : http://www.todayonhistory.com/uploadfile/2016/0909/20160909104645350.jpg
         * title : 毛泽东逝世40周年纪念日
         * year : 2016
         * month : 9
         * day : 9
         * details : 毛泽东
         　　1976年9月9日，中国人民的领袖，伟大的无产阶级革命家、战略家和理论家，中国共产党、中国人民解放军和中华人民共和国的主要缔造者和领导人毛泽东逝世，享年83岁。
         　　今天（2016年9月9日）是毛泽东逝世40周年纪念日，让我们一起回顾他的一生，缅怀这位伟人。
         　　...
         */

        private String picUrl;
        private String title;
        private String year;
        private int month;
        private int day;
        private String details;

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }
    }
}
