package com.maning.gankmm.utils;

import com.google.android.material.snackbar.Snackbar;

import android.view.View;
import android.widget.TextView;

import com.maning.gankmm.R;


/**
 * Created by maning on 16/1/18.
 * <p/>
 * 给SnackBar设置颜色的
 */
public class ColoredSnackbar {

    private static final int red = 0xffb95050;
    private static final int green = 0xff4caf50;
    private static final int blue = 0xff2195f3;
    private static final int orange = 0xffffc107;
    private static final int black = 0xff2e2e2e;
    private static final int white = 0xffFFFFFF;
    private static final int nightTextColor = 0xff9AACEC;
    private static final int nightBgColor = 0xff2A2F41;

    private static View getSnackBarLayout(Snackbar snackbar) {
        if (snackbar != null) {
            return snackbar.getView();
        }
        return null;
    }

    private static Snackbar colorSnackBar(Snackbar snackbar, int bgColorId, int textColorId) {
        View snackBarView = getSnackBarLayout(snackbar);
        if (snackBarView != null) {
            //设置透明度
            snackBarView.setAlpha(0.95f);
            //设置背景色
            snackBarView.setBackgroundColor(bgColorId);
            //这只内容文字的颜色
            ((TextView) snackBarView.findViewById(R.id.snackbar_text)).setTextColor(textColorId);
        }

        return snackbar;
    }

    public static Snackbar defaultInfo(Snackbar snackbar) {
        return colorSnackBar(snackbar, black, white);
    }

    public static Snackbar defaultInfoNight(Snackbar snackbar) {
        return colorSnackBar(snackbar, nightBgColor, nightTextColor);
    }

    public static Snackbar info(Snackbar snackbar) {
        return colorSnackBar(snackbar, blue, white);
    }

    public static Snackbar warning(Snackbar snackbar) {
        return colorSnackBar(snackbar, orange, white);
    }

    public static Snackbar alert(Snackbar snackbar) {
        return colorSnackBar(snackbar, red, white);
    }

    public static Snackbar confirm(Snackbar snackbar) {
        return colorSnackBar(snackbar, green, white);
    }

}
