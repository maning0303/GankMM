package com.maning.gankmm.bean.rolltools;

import java.util.List;

/**
 * @author : maning
 * @date : 2020-10-15
 * @desc :
 */
public class WorldPhoneCodeResultBean extends RollToolsBaseBean {


    private List<DataEntity> data;

    public List<DataEntity> getData() {
        return data;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * zhCn : 中国
         * enUs : China
         * phoneCode : +86
         */

        private String zhCn;
        private String enUs;
        private String phoneCode;

        public String getZhCn() {
            return zhCn;
        }

        public void setZhCn(String zhCn) {
            this.zhCn = zhCn;
        }

        public String getEnUs() {
            return enUs;
        }

        public void setEnUs(String enUs) {
            this.enUs = enUs;
        }

        public String getPhoneCode() {
            return phoneCode;
        }

        public void setPhoneCode(String phoneCode) {
            this.phoneCode = phoneCode;
        }
    }
}
