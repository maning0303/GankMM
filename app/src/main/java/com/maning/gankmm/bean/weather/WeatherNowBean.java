package com.maning.gankmm.bean.weather;

import java.util.List;

/**
 * @author : maning
 * @date : 2020-10-14
 * @desc : 天气实况
 */
public class WeatherNowBean extends WeatherBaseBean{

    private List<ResultsEntity> results;

    public List<ResultsEntity> getResults() {
        return results;
    }

    public void setResults(List<ResultsEntity> results) {
        this.results = results;
    }

    public static class ResultsEntity {
        /**
         * location : {"id":"C23NB62W20TF","name":"西雅图","country":"US","path":"西雅图,华盛顿州,美国","timezone":"America/Los_Angeles","timezone_offset":"-07:00"}
         * now : {"text":"多云","code":"4","temperature":"14","feels_like":"14","pressure":"1018","humidity":"76","visibility":"16.09","wind_direction":"西北","wind_direction_degree":"340","wind_speed":"8.05","wind_scale":"2","clouds":"90","dew_point":"-12"}
         * last_update : 2015-09-25T22:45:00-07:00
         */

        private LocationEntity location;
        private NowEntity now;
        private String last_update;

        public LocationEntity getLocation() {
            return location;
        }

        public void setLocation(LocationEntity location) {
            this.location = location;
        }

        public NowEntity getNow() {
            return now;
        }

        public void setNow(NowEntity now) {
            this.now = now;
        }

        public String getLast_update() {
            return last_update;
        }

        public void setLast_update(String last_update) {
            this.last_update = last_update;
        }

        public static class LocationEntity {
            /**
             * id : C23NB62W20TF
             * name : 西雅图
             * country : US
             * path : 西雅图,华盛顿州,美国
             * timezone : America/Los_Angeles
             * timezone_offset : -07:00
             */

            private String id;
            private String name;
            private String country;
            private String path;
            private String timezone;
            private String timezone_offset;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public String getTimezone() {
                return timezone;
            }

            public void setTimezone(String timezone) {
                this.timezone = timezone;
            }

            public String getTimezone_offset() {
                return timezone_offset;
            }

            public void setTimezone_offset(String timezone_offset) {
                this.timezone_offset = timezone_offset;
            }
        }

        public static class NowEntity {
            /**
             * text : 多云
             * code : 4
             * temperature : 14
             * feels_like : 14
             * pressure : 1018
             * humidity : 76
             * visibility : 16.09
             * wind_direction : 西北
             * wind_direction_degree : 340
             * wind_speed : 8.05
             * wind_scale : 2
             * clouds : 90
             * dew_point : -12
             */

            private String text;
            private String code;
            private String temperature;
            private String feels_like;
            private String pressure;
            private String humidity;
            private String visibility;
            private String wind_direction;
            private String wind_direction_degree;
            private String wind_speed;
            private String wind_scale;
            private String clouds;
            private String dew_point;

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getTemperature() {
                return temperature;
            }

            public void setTemperature(String temperature) {
                this.temperature = temperature;
            }

            public String getFeels_like() {
                return feels_like;
            }

            public void setFeels_like(String feels_like) {
                this.feels_like = feels_like;
            }

            public String getPressure() {
                return pressure;
            }

            public void setPressure(String pressure) {
                this.pressure = pressure;
            }

            public String getHumidity() {
                return humidity;
            }

            public void setHumidity(String humidity) {
                this.humidity = humidity;
            }

            public String getVisibility() {
                return visibility;
            }

            public void setVisibility(String visibility) {
                this.visibility = visibility;
            }

            public String getWind_direction() {
                return wind_direction;
            }

            public void setWind_direction(String wind_direction) {
                this.wind_direction = wind_direction;
            }

            public String getWind_direction_degree() {
                return wind_direction_degree;
            }

            public void setWind_direction_degree(String wind_direction_degree) {
                this.wind_direction_degree = wind_direction_degree;
            }

            public String getWind_speed() {
                return wind_speed;
            }

            public void setWind_speed(String wind_speed) {
                this.wind_speed = wind_speed;
            }

            public String getWind_scale() {
                return wind_scale;
            }

            public void setWind_scale(String wind_scale) {
                this.wind_scale = wind_scale;
            }

            public String getClouds() {
                return clouds;
            }

            public void setClouds(String clouds) {
                this.clouds = clouds;
            }

            public String getDew_point() {
                return dew_point;
            }

            public void setDew_point(String dew_point) {
                this.dew_point = dew_point;
            }
        }
    }
}
