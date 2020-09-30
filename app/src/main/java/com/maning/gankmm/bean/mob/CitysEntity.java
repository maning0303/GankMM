package com.maning.gankmm.bean.mob;

import java.io.Serializable;
import java.util.List;

/**
 * Created by maning on 2017/3/31.
 */

public class CitysEntity implements Serializable {


    private static final long serialVersionUID = 5446955852831089144L;
    /**
     * msg : success
     * result : [{"city":[{"city":"合肥","district":[{"district":"合肥"},{"district":"长丰"},{"district":"肥东"},{"district":"肥西"},{"district":"巢湖"},{"district":"庐江"}]},{"city":"蚌埠","district":[{"district":"蚌埠"},{"district":"怀远"},{"district":"固镇"},{"district":"五河"}]},{"city":"芜湖","district":[{"district":"芜湖"},{"district":"繁昌"},{"district":"芜湖县"},{"district":"南陵"},{"district":"无为"}]},{"city":"淮南","district":[{"district":"淮南"},{"district":"凤台"},{"district":"潘集"}]},{"city":"马鞍山","district":[{"district":"马鞍山"},{"district":"当涂"},{"district":"含山"},{"district":"和县"}]},{"city":"安庆","district":[{"district":"安庆"},{"district":"枞阳"},{"district":"太湖"},{"district":"潜山"},{"district":"怀宁"},{"district":"宿松"},{"district":"望江"},{"district":"岳西"},{"district":"桐城"}]},{"city":"宿州","district":[{"district":"宿州"},{"district":"砀山"},{"district":"灵璧"},{"district":"泗县"},{"district":"萧县"}]},{"city":"阜阳","district":[{"district":"阜阳"},{"district":"阜南"},{"district":"颍上"},{"district":"临泉"},{"district":"界首"},{"district":"太和"}]},{"city":"亳州","district":[{"district":"亳州"},{"district":"涡阳"},{"district":"利辛"},{"district":"蒙城"}]},{"city":"黄山","district":[{"district":"黄山"},{"district":"黄山区"},{"district":"屯溪"},{"district":"祁门"},{"district":"黟县"},{"district":"歙县"},{"district":"休宁"},{"district":"黄山风景区"}]},{"city":"滁州","district":[{"district":"滁州"},{"district":"凤阳"},{"district":"明光"},{"district":"定远"},{"district":"全椒"},{"district":"来安"},{"district":"天长"}]},{"city":"淮北","district":[{"district":"淮北"},{"district":"濉溪"}]},{"city":"铜陵","district":[{"district":"铜陵"}]},{"city":"宣城","district":[{"district":"宣城"},{"district":"泾县"},{"district":"旌德"},{"district":"宁国"},{"district":"绩溪"},{"district":"广德"},{"district":"郎溪"}]},{"city":"六安","district":[{"district":"六安"},{"district":"霍邱"},{"district":"寿县"},{"district":"金寨"},{"district":"霍山"},{"district":"舒城"}]},{"city":"巢湖"},{"city":"池州","district":[{"district":"池州"},{"district":"东至"},{"district":"青阳"},{"district":"九华山"},{"district":"石台"}]}],"province":"安徽"}]
     */

    private String msg;
    private List<ResultBean> result;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable {
        private static final long serialVersionUID = -8013232313793507847L;
        /**
         * city : [{"city":"合肥","district":[{"district":"合肥"},{"district":"长丰"},{"district":"肥东"},{"district":"肥西"},{"district":"巢湖"},{"district":"庐江"}]},{"city":"蚌埠","district":[{"district":"蚌埠"},{"district":"怀远"},{"district":"固镇"},{"district":"五河"}]},{"city":"芜湖","district":[{"district":"芜湖"},{"district":"繁昌"},{"district":"芜湖县"},{"district":"南陵"},{"district":"无为"}]},{"city":"淮南","district":[{"district":"淮南"},{"district":"凤台"},{"district":"潘集"}]},{"city":"马鞍山","district":[{"district":"马鞍山"},{"district":"当涂"},{"district":"含山"},{"district":"和县"}]},{"city":"安庆","district":[{"district":"安庆"},{"district":"枞阳"},{"district":"太湖"},{"district":"潜山"},{"district":"怀宁"},{"district":"宿松"},{"district":"望江"},{"district":"岳西"},{"district":"桐城"}]},{"city":"宿州","district":[{"district":"宿州"},{"district":"砀山"},{"district":"灵璧"},{"district":"泗县"},{"district":"萧县"}]},{"city":"阜阳","district":[{"district":"阜阳"},{"district":"阜南"},{"district":"颍上"},{"district":"临泉"},{"district":"界首"},{"district":"太和"}]},{"city":"亳州","district":[{"district":"亳州"},{"district":"涡阳"},{"district":"利辛"},{"district":"蒙城"}]},{"city":"黄山","district":[{"district":"黄山"},{"district":"黄山区"},{"district":"屯溪"},{"district":"祁门"},{"district":"黟县"},{"district":"歙县"},{"district":"休宁"},{"district":"黄山风景区"}]},{"city":"滁州","district":[{"district":"滁州"},{"district":"凤阳"},{"district":"明光"},{"district":"定远"},{"district":"全椒"},{"district":"来安"},{"district":"天长"}]},{"city":"淮北","district":[{"district":"淮北"},{"district":"濉溪"}]},{"city":"铜陵","district":[{"district":"铜陵"}]},{"city":"宣城","district":[{"district":"宣城"},{"district":"泾县"},{"district":"旌德"},{"district":"宁国"},{"district":"绩溪"},{"district":"广德"},{"district":"郎溪"}]},{"city":"六安","district":[{"district":"六安"},{"district":"霍邱"},{"district":"寿县"},{"district":"金寨"},{"district":"霍山"},{"district":"舒城"}]},{"city":"巢湖"},{"city":"池州","district":[{"district":"池州"},{"district":"东至"},{"district":"青阳"},{"district":"九华山"},{"district":"石台"}]}]
         * province : 安徽
         */

        private String province;
        private List<CityBean> city;

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public List<CityBean> getCity() {
            return city;
        }

        public void setCity(List<CityBean> city) {
            this.city = city;
        }

        public static class CityBean implements Serializable {
            private static final long serialVersionUID = -6301847602106333642L;
            /**
             * city : 合肥
             * district : [{"district":"合肥"},{"district":"长丰"},{"district":"肥东"},{"district":"肥西"},{"district":"巢湖"},{"district":"庐江"}]
             */

            private String city;
            private List<DistrictBean> district;

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public List<DistrictBean> getDistrict() {
                return district;
            }

            public void setDistrict(List<DistrictBean> district) {
                this.district = district;
            }

            public static class DistrictBean implements Serializable {
                private static final long serialVersionUID = 4832591567971353963L;
                /**
                 * district : 合肥
                 */

                private String district;

                public String getDistrict() {
                    return district;
                }

                public void setDistrict(String district) {
                    this.district = district;
                }
            }
        }
    }
}
