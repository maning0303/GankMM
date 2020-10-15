package com.maning.gankmm.bean.rolltools;

/**
 * @author : maning
 * @date : 2020-10-15
 * @desc :
 */
public class IpResultBean extends RollToolsBaseBean {


    /**
     * data : {"ip":"119.123.72.166","province":"广东省","provinceId":440000,"city":"深圳市","cityId":440300,"isp":"电信","desc":"广东省深圳市 电信"}
     */

    private DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * ip : 119.123.72.166
         * province : 广东省
         * provinceId : 440000
         * city : 深圳市
         * cityId : 440300
         * isp : 电信
         * desc : 广东省深圳市 电信
         */

        private String ip;
        private String province;
        private int provinceId;
        private String city;
        private int cityId;
        private String isp;
        private String desc;

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public int getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(int provinceId) {
            this.provinceId = provinceId;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public int getCityId() {
            return cityId;
        }

        public void setCityId(int cityId) {
            this.cityId = cityId;
        }

        public String getIsp() {
            return isp;
        }

        public void setIsp(String isp) {
            this.isp = isp;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
