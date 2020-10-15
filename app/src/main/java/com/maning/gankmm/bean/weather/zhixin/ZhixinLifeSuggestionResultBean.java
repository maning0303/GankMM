package com.maning.gankmm.bean.weather.zhixin;

import java.util.List;

/**
 * @author : maning
 * @date : 2020-10-15
 * @desc : 生活指数
 */
public class ZhixinLifeSuggestionResultBean extends ZhixinBaseBean {


    private List<ResultsEntity> results;

    public List<ResultsEntity> getResults() {
        return results;
    }

    public void setResults(List<ResultsEntity> results) {
        this.results = results;
    }

    public static class ResultsEntity {
        /**
         * location : {"id":"WTW3SJ5ZBJUY","name":"上海","country":"CN","path":"上海,上海,中国","timezone":"Asia/Shanghai","timezone_offset":"+08:00"}
         * suggestion : {"car_washing":{"brief":"不宜","details":""},"dressing":{"brief":"较舒适","details":""},"flu":{"brief":"易发","details":""},"sport":{"brief":"较不宜","details":""},"travel":{"brief":"适宜","details":""},"uv":{"brief":"最弱","details":""}}
         * last_update : 2020-10-15T08:32:45+08:00
         */

        private LocationEntity location;
        private ZhixinSuggestionEntity suggestion;
        private String last_update;

        public LocationEntity getLocation() {
            return location;
        }

        public void setLocation(LocationEntity location) {
            this.location = location;
        }

        public ZhixinSuggestionEntity getSuggestion() {
            return suggestion;
        }

        public void setSuggestion(ZhixinSuggestionEntity suggestion) {
            this.suggestion = suggestion;
        }

        public String getLast_update() {
            return last_update;
        }

        public void setLast_update(String last_update) {
            this.last_update = last_update;
        }

        public static class LocationEntity {
            /**
             * id : WTW3SJ5ZBJUY
             * name : 上海
             * country : CN
             * path : 上海,上海,中国
             * timezone : Asia/Shanghai
             * timezone_offset : +08:00
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

    }
}
