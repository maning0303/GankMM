package com.maning.gankmm.ui.imagebrowser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;

import com.maning.gankmm.R;
import com.maning.gankmm.bean.GankEntity;

import java.util.ArrayList;

/**
 * Created by maning on 2017/5/25.
 */
public class MNImageBrowser {

    /**
     * 打开浏览页面
     *
     * @param context   上下文
     * @param view      点击的当前View
     * @param position  默认打开第几个
     * @param imageList 数据源ArrayList<String>
     */
    public static void showImageBrowser(Context context, View view, int position, ArrayList<String> imageList, ArrayList<GankEntity> gankEntityList) {
        Intent intent = new Intent(context, MNImageBrowserActivity2.class);
        intent.putExtra(MNImageBrowserActivity2.IntentKey_ImageList, imageList);
        intent.putExtra(MNImageBrowserActivity2.IntentKey_CurrentPosition, position);
        intent.putExtra(MNImageBrowserActivity2.IntentKey_GankEntityList, gankEntityList);

        //android V4包的类,用于两个activity转场时的缩放效果实现
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);
        try {
            ActivityCompat.startActivity(context, intent, optionsCompat.toBundle());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            context.startActivity(intent);
            ((Activity) context).overridePendingTransition(R.anim.browser_enter_anim, 0);
        }
    }

}
