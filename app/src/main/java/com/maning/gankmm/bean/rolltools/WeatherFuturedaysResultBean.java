package com.maning.gankmm.bean.rolltools;

import java.util.List;

/**
 * @author : maning
 * @date : 2020-10-15
 * @desc :
 */
public class WeatherFuturedaysResultBean extends RollToolsBaseBean{

    /**
     * data : {"address":"广东省 深圳市","cityCode":"440300","reportTime":"2018-11-27 22:40:53","forecasts":[{"date":"2018-11-27","dayOfWeek":"2","dayWeather":"阵雨","nightWeather":"小雨","dayTemp":"22℃","nightTemp":"17℃","dayWindDirection":"无风向","nightWindDirection":"无风向","dayWindPower":"≤3级","nightWindPower":"≤3级"}]}
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
         * address : 广东省 深圳市
         * cityCode : 440300
         * reportTime : 2018-11-27 22:40:53
         * forecasts : [{"date":"2018-11-27","dayOfWeek":"2","dayWeather":"阵雨","nightWeather":"小雨","dayTemp":"22℃","nightTemp":"17℃","dayWindDirection":"无风向","nightWindDirection":"无风向","dayWindPower":"≤3级","nightWindPower":"≤3级"}]
         */

        private String address;
        private String cityCode;
        private String reportTime;
        private List<ForecastsEntity> forecasts;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCityCode() {
            return cityCode;
        }

        public void setCityCode(String cityCode) {
            this.cityCode = cityCode;
        }

        public String getReportTime() {
            return reportTime;
        }

        public void setReportTime(String reportTime) {
            this.reportTime = reportTime;
        }

        public List<ForecastsEntity> getForecasts() {
            return forecasts;
        }

        public void setForecasts(List<ForecastsEntity> forecasts) {
            this.forecasts = forecasts;
        }

        public static class ForecastsEntity {
            /**
             * date : 2018-11-27
             * dayOfWeek : 2
             * dayWeather : 阵雨
             * nightWeather : 小雨
             * dayTemp : 22℃
             * nightTemp : 17℃
             * dayWindDirection : 无风向
             * nightWindDirection : 无风向
             * dayWindPower : ≤3级
             * nightWindPower : ≤3级
             */

            private String date;
            private String dayOfWeek;
            private String dayWeather;
            private String nightWeather;
            private String dayTemp;
            private String nightTemp;
            private String dayWindDirection;
            private String nightWindDirection;
            private String dayWindPower;
            private String nightWindPower;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getDayOfWeek() {
                return dayOfWeek;
            }

            public void setDayOfWeek(String dayOfWeek) {
                this.dayOfWeek = dayOfWeek;
            }

            public String getDayWeather() {
                return dayWeather;
            }

            public void setDayWeather(String dayWeather) {
                this.dayWeather = dayWeather;
            }

            public String getNightWeather() {
                return nightWeather;
            }

            public void setNightWeather(String nightWeather) {
                this.nightWeather = nightWeather;
            }

            public String getDayTemp() {
                return dayTemp;
            }

            public void setDayTemp(String dayTemp) {
                this.dayTemp = dayTemp;
            }

            public String getNightTemp() {
                return nightTemp;
            }

            public void setNightTemp(String nightTemp) {
                this.nightTemp = nightTemp;
            }

            public String getDayWindDirection() {
                return dayWindDirection;
            }

            public void setDayWindDirection(String dayWindDirection) {
                this.dayWindDirection = dayWindDirection;
            }

            public String getNightWindDirection() {
                return nightWindDirection;
            }

            public void setNightWindDirection(String nightWindDirection) {
                this.nightWindDirection = nightWindDirection;
            }

            public String getDayWindPower() {
                return dayWindPower;
            }

            public void setDayWindPower(String dayWindPower) {
                this.dayWindPower = dayWindPower;
            }

            public String getNightWindPower() {
                return nightWindPower;
            }

            public void setNightWindPower(String nightWindPower) {
                this.nightWindPower = nightWindPower;
            }
        }
    }
}
