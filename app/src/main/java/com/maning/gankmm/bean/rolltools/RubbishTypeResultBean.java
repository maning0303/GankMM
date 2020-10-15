package com.maning.gankmm.bean.rolltools;

import java.util.List;

/**
 * @author : maning
 * @date : 2020-10-15
 * @desc :
 */
public class RubbishTypeResultBean extends RollToolsBaseBean {


    /**
     * data : {"aim":{"goodsName":"西瓜","goodsType":"湿垃圾"},"recommendList":[{"goodsName":"西瓜霜含片塑料铝箔包装","goodsType":"有害垃圾"}]}
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
         * aim : {"goodsName":"西瓜","goodsType":"湿垃圾"}
         * recommendList : [{"goodsName":"西瓜霜含片塑料铝箔包装","goodsType":"有害垃圾"}]
         */

        private AimEntity aim;
        private List<AimEntity> recommendList;

        public AimEntity getAim() {
            return aim;
        }

        public void setAim(AimEntity aim) {
            this.aim = aim;
        }

        public List<AimEntity> getRecommendList() {
            return recommendList;
        }

        public void setRecommendList(List<AimEntity> recommendList) {
            this.recommendList = recommendList;
        }

        public static class AimEntity {
            /**
             * goodsName : 西瓜
             * goodsType : 湿垃圾
             */

            private String goodsName;
            private String goodsType;

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public String getGoodsType() {
                return goodsType;
            }

            public void setGoodsType(String goodsType) {
                this.goodsType = goodsType;
            }
        }
    }
}
