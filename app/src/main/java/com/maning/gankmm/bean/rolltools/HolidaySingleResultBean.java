package com.maning.gankmm.bean.rolltools;

/**
 * @author : maning
 * @date : 2020-10-15
 * @desc :
 */
public class HolidaySingleResultBean extends RollToolsBaseBean {

    /**
     * data : {"date":"2018-11-21","weekDay":3,"yearTips":"戊戌","type":0,"typeDes":"工作日","chineseZodiac":"狗","solarTerms":"立冬后","avoid":"嫁娶.安葬","lunarCalendar":"十月十四","suit":"破屋.坏垣.祭祀.余事勿取","dayOfYear":325,"weekOfYear":47,"constellation":"天蝎座","indexWorkDayOfMonth":1}
     */

    private HolidayBean data;

    public HolidayBean getData() {
        return data;
    }

    public void setData(HolidayBean data) {
        this.data = data;
    }

}
