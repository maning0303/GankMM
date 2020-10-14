package com.maning.gankmm.http.weather;

import android.text.TextUtils;

import com.maning.gankmm.bean.weather.CaiyunWeatherRealTimeBean;
import com.maning.gankmm.bean.weather.WeatherBean;
import com.maning.gankmm.bean.weather.WeatherNowBean;
import com.maning.gankmm.http.BuildApi;
import com.maning.gankmm.http.callback.CommonHttpCallback;

import java.util.HashMap;

/**
 * @author : maning
 * @date : 2020-09-29
 * @desc :
 */
public class WeatherApi {

    public interface OnWeatherCallback {
        void onSuccess(WeatherBean weatherBean);

        void onFail(String msg);
    }
    

    public static void getWeatherFromZhixin(double longitude, double latitude, final OnWeatherCallback onWeatherCallback) {
        //经纬度（格式是 纬度:经度，英文冒号分隔）
        String location = latitude + ":" + longitude;
        BuildApi.getWeatherAPIService().getWeatherNow("SkAwmmz_N361lNLX3", location).enqueue(new CommonHttpCallback<WeatherNowBean>() {
            @Override
            public void onSuccess(WeatherNowBean result) {
                if (result == null || result.getResults() == null || result.getResults().size() == 0) {
                    if (onWeatherCallback != null) {
                        onWeatherCallback.onFail("天气数据获取异常");
                    }
                    return;
                }
                WeatherNowBean.ResultsEntity resultsEntity = result.getResults().get(0);
                if (resultsEntity == null) {
                    if (onWeatherCallback != null) {
                        onWeatherCallback.onFail("天气数据获取异常");
                    }
                    return;
                }
                WeatherNowBean.ResultsEntity.NowEntity nowEntity = resultsEntity.getNow();

                WeatherBean weatherBean = new WeatherBean();
                weatherBean.setWeather_desc(nowEntity.getText());
                weatherBean.setFeels_like(nowEntity.getFeels_like());
                weatherBean.setHumidity(nowEntity.getHumidity());
                weatherBean.setPressure(nowEntity.getPressure());
                weatherBean.setTemperature(nowEntity.getTemperature());
                weatherBean.setVisibility(nowEntity.getVisibility());
                weatherBean.setWind_direction(nowEntity.getWind_direction());
                weatherBean.setWind_scale(nowEntity.getWind_scale());
                weatherBean.setWind_speed(nowEntity.getWind_speed());
                if (onWeatherCallback != null) {
                    onWeatherCallback.onSuccess(weatherBean);
                }
            }

            @Override
            public void onFail(int code, String message) {
                if (onWeatherCallback != null) {
                    onWeatherCallback.onFail(message);
                }
            }
        });
    }

    public static void getWeatherFromCaiyun(double longitude, double latitude, final OnWeatherCallback onWeatherCallback) {
        String location = longitude + "," + latitude;
        BuildApi.getWeatherAPIService().getWeatherFromCaiyun(location).enqueue(new CommonHttpCallback<CaiyunWeatherRealTimeBean>() {
            @Override
            public void onSuccess(CaiyunWeatherRealTimeBean caiyunWeatherRealTimeBean) {
                CaiyunWeatherRealTimeBean.ResultEntity result = caiyunWeatherRealTimeBean.getResult();
                CaiyunWeatherRealTimeBean.ResultEntity.RealtimeEntity realtime = result.getRealtime();
                if (realtime == null) {
                    if (onWeatherCallback != null) {
                        onWeatherCallback.onFail("天气数据获取异常");
                    }
                    return;
                }

                WeatherBean weatherBean = new WeatherBean();
                weatherBean.setWeather_desc(CaiyunUtils.getWeatherText(realtime.getSkycon()));
                weatherBean.setTemperature(String.valueOf(realtime.getTemperature()));
                weatherBean.setFeels_like(String.valueOf(realtime.getApparent_temperature()));
                weatherBean.setHumidity(String.valueOf(realtime.getHumidity() * 100));
                weatherBean.setPressure(String.valueOf(realtime.getPressure()));
                weatherBean.setVisibility(String.valueOf(realtime.getVisibility()));
                if (realtime.getWind() != null) {
                    weatherBean.setWind_direction(CaiyunUtils.getWindDirection(realtime.getWind().getDirection()));
                    weatherBean.setWind_scale(String.valueOf(CaiyunUtils.getWindScale(realtime.getWind().getSpeed())));
                    weatherBean.setWind_speed(String.valueOf(realtime.getWind().getSpeed()));
                }
                if (onWeatherCallback != null) {
                    onWeatherCallback.onSuccess(weatherBean);
                }
            }

            @Override
            public void onFail(int code, String message) {
                if (onWeatherCallback != null) {
                    onWeatherCallback.onFail(message);
                }
            }
        });
    }

    public static class CaiyunUtils {

        private static HashMap<String, String> weatherTextMap = new HashMap<String, String>() {
            {
                put("CLEAR_DAY", "晴（白天）");
                put("CLEAR_NIGHT", "晴（夜间）");
                put("PARTLY_CLOUDY_DAY", "多云（白天）");
                put("PARTLY_CLOUDY_NIGHT", "多云（夜间）");
                put("CLOUDY", "阴");
                put("LIGHT_HAZE", "轻度雾霾");
                put("MODERATE_HAZE", "中度雾霾");
                put("HEAVY_HAZE", "重度雾霾");
                put("LIGHT_RAIN", "小雨");
                put("MODERATE_RAIN", "中雨");
                put("HEAVY_RAIN", "大雨");
                put("STORM_RAIN", "暴雨");
                put("FOG", "雾");
                put("LIGHT_SNOW", "小雪");
                put("MODERATE_SNOW", "中雪");
                put("HEAVY_SNOW", "大雪");
                put("STORM_SNOW", "暴雪");
                put("DUST", "浮尘");
                put("SAND", "沙尘");
                put("WIND", "大风");
            }
        };

        public static String getWeatherText(String code) {
            String text = weatherTextMap.get(code);
            if (TextUtils.isEmpty(text)) {
                text = "未知";
            }
            return text;
        }


        public static int getWindScale(double speed) {
            int scale = 0;
            if (speed < 1) {
                scale = 0;
            } else if (speed >= 1 && speed <= 5) {
                scale = 1;
            } else if (speed >= 6 && speed <= 11) {
                scale = 2;
            } else if (speed >= 12 && speed <= 19) {
                scale = 3;
            } else if (speed >= 20 && speed <= 28) {
                scale = 4;
            } else if (speed >= 29 && speed <= 38) {
                scale = 5;
            } else if (speed >= 39 && speed <= 49) {
                scale = 6;
            } else if (speed >= 50 && speed <= 61) {
                scale = 7;
            } else if (speed >= 62 && speed <= 74) {
                scale = 8;
            } else if (speed >= 75 && speed <= 88) {
                scale = 9;
            } else if (speed >= 89 && speed <= 102) {
                scale = 10;
            } else if (speed >= 103 && speed <= 117) {
                scale = 11;
            } else if (speed >= 118 && speed <= 133) {
                scale = 12;
            } else if (speed >= 134 && speed <= 149) {
                scale = 13;
            } else if (speed >= 150 && speed <= 166) {
                scale = 14;
            } else if (speed >= 167 && speed <= 183) {
                scale = 15;
            } else if (speed >= 184 && speed <= 201) {
                scale = 16;
            } else if (speed >= 202 && speed <= 220) {
                scale = 17;
            }
            return scale;
        }


        public static String getWindDirection(double direction) {
            String result = "";
            if (direction >= 348.76 || direction <= 11.25) {
                result = "北";
            } else if (direction >= 11.26 && direction <= 33.75) {
                result = "北东北";
            } else if (direction >= 33.76 && direction <= 56.25) {
                result = "东北";
            } else if (direction >= 56.26 && direction <= 78.75) {
                result = "东东北";
            } else if (direction >= 78.76 && direction <= 101.25) {
                result = "东";
            } else if (direction >= 101.26 && direction <= 123.75) {
                result = "东东南";
            } else if (direction >= 123.76 && direction <= 146.25) {
                result = "东南";
            } else if (direction >= 146.26 && direction <= 168.75) {
                result = "南东南";
            } else if (direction >= 168.76 && direction <= 191.25) {
                result = "南";
            } else if (direction >= 191.26 && direction <= 213.75) {
                result = "南西南";
            } else if (direction >= 213.76 && direction <= 236.25) {
                result = "西南";
            } else if (direction >= 236.26 && direction <= 258.75) {
                result = "西西南";
            } else if (direction >= 258.76 && direction <= 281.25) {
                result = "西";
            } else if (direction >= 281.26 && direction <= 303.75) {
                result = "西西北";
            } else if (direction >= 303.76 && direction <= 326.25) {
                result = "西北";
            } else if (direction >= 326.26 && direction <= 348.75) {
                result = "北西北";
            }
            return result;
        }

    }


}
