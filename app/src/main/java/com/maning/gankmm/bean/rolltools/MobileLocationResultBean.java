package com.maning.gankmm.bean.rolltools;

/**
 * @author : maning
 * @date : 2020-10-15
 * @desc :
 */
public class MobileLocationResultBean extends RollToolsBaseBean {


    /**
     * data : {"mobile":"13227293721","province":"湖北","carrier":"湖北联通"}
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
         * mobile : 13227293721
         * province : 湖北
         * carrier : 湖北联通
         */

        private String mobile;
        private String province;
        private String carrier;

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCarrier() {
            return carrier;
        }

        public void setCarrier(String carrier) {
            this.carrier = carrier;
        }
    }
}
