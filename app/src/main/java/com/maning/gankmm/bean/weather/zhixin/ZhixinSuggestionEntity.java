package com.maning.gankmm.bean.weather.zhixin;

/**
 * @author : maning
 * @date : 2020-10-15
 * @desc :
 */
public class ZhixinSuggestionEntity  {
    /**
     * car_washing : {"brief":"不宜","details":""}
     * dressing : {"brief":"较舒适","details":""}
     * flu : {"brief":"易发","details":""}
     * sport : {"brief":"较不宜","details":""}
     * travel : {"brief":"适宜","details":""}
     * uv : {"brief":"最弱","details":""}
     */

    private CarWashingEntity car_washing;
    private DressingEntity dressing;
    private FluEntity flu;
    private SportEntity sport;
    private TravelEntity travel;
    private UvEntity uv;

    public CarWashingEntity getCar_washing() {
        return car_washing;
    }

    public void setCar_washing(CarWashingEntity car_washing) {
        this.car_washing = car_washing;
    }

    public DressingEntity getDressing() {
        return dressing;
    }

    public void setDressing(DressingEntity dressing) {
        this.dressing = dressing;
    }

    public FluEntity getFlu() {
        return flu;
    }

    public void setFlu(FluEntity flu) {
        this.flu = flu;
    }

    public SportEntity getSport() {
        return sport;
    }

    public void setSport(SportEntity sport) {
        this.sport = sport;
    }

    public TravelEntity getTravel() {
        return travel;
    }

    public void setTravel(TravelEntity travel) {
        this.travel = travel;
    }

    public UvEntity getUv() {
        return uv;
    }

    public void setUv(UvEntity uv) {
        this.uv = uv;
    }

    public static class CarWashingEntity {
        /**
         * brief : 不宜
         * details :
         */

        private String brief;
        private String details;

        public String getBrief() {
            return brief;
        }

        public void setBrief(String brief) {
            this.brief = brief;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }
    }

    public static class DressingEntity {
        /**
         * brief : 较舒适
         * details :
         */

        private String brief;
        private String details;

        public String getBrief() {
            return brief;
        }

        public void setBrief(String brief) {
            this.brief = brief;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }
    }

    public static class FluEntity {
        /**
         * brief : 易发
         * details :
         */

        private String brief;
        private String details;

        public String getBrief() {
            return brief;
        }

        public void setBrief(String brief) {
            this.brief = brief;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }
    }

    public static class SportEntity {
        /**
         * brief : 较不宜
         * details :
         */

        private String brief;
        private String details;

        public String getBrief() {
            return brief;
        }

        public void setBrief(String brief) {
            this.brief = brief;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }
    }

    public static class TravelEntity {
        /**
         * brief : 适宜
         * details :
         */

        private String brief;
        private String details;

        public String getBrief() {
            return brief;
        }

        public void setBrief(String brief) {
            this.brief = brief;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }
    }

    public static class UvEntity {
        /**
         * brief : 最弱
         * details :
         */

        private String brief;
        private String details;

        public String getBrief() {
            return brief;
        }

        public void setBrief(String brief) {
            this.brief = brief;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }
    }
}
