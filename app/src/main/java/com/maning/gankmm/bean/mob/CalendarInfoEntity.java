package com.maning.gankmm.bean.mob;

/**
 * Created by maning on 2017/4/11.
 */
@Deprecated
public class CalendarInfoEntity {


    /**
     * avoid : 安葬 出行 安葬 针灸
     * date : 2015-05-01
     * holiday : 劳动节
     * lunar : 三月十三
     * lunarYear : 乙未
     * suit : 纳财 收获 开仓 交易 入学 婚礼 造作 动土
     * weekday : 星期五
     * zodiac : 羊
     */

    private String avoid;
    private String date;
    private String holiday;
    private String lunar;
    private String lunarYear;
    private String suit;
    private String weekday;
    private String zodiac;

    public String getAvoid() {
        return avoid;
    }

    public void setAvoid(String avoid) {
        this.avoid = avoid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHoliday() {
        return holiday;
    }

    public void setHoliday(String holiday) {
        this.holiday = holiday;
    }

    public String getLunar() {
        return lunar;
    }

    public void setLunar(String lunar) {
        this.lunar = lunar;
    }

    public String getLunarYear() {
        return lunarYear;
    }

    public void setLunarYear(String lunarYear) {
        this.lunarYear = lunarYear;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public String getZodiac() {
        return zodiac;
    }

    public void setZodiac(String zodiac) {
        this.zodiac = zodiac;
    }
}
