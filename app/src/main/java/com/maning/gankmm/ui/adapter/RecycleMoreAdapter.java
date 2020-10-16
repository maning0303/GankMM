package com.maning.gankmm.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maning.gankmm.R;
import com.maning.gankmm.listeners.OnItemClickListener;
import com.maning.gankmm.ui.activity.tools.ChineseCalendarActivity;
import com.maning.gankmm.ui.activity.tools.DictionaryActivity;
import com.maning.gankmm.ui.activity.tools.FlightActivity;
import com.maning.gankmm.ui.activity.tools.HistoryTodayActivity;
import com.maning.gankmm.ui.activity.tools.IPQueryActivity;
import com.maning.gankmm.ui.activity.tools.LotteryCategoryActivity;
import com.maning.gankmm.ui.activity.tools.PhoneAddressActivity;
import com.maning.gankmm.ui.activity.tools.RubbishActivity;
import com.maning.gankmm.ui.activity.tools.ScanResultActivity;
import com.maning.gankmm.ui.activity.tools.TrainActivity;
import com.maning.gankmm.ui.activity.tools.WorldPhoneCodeActivity;
import com.maning.gankmm.utils.IntentUtils;
import com.maning.gankmm.utils.MySnackbar;
import com.maning.gankmm.utils.ZxingScanUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 更多功能的Adapter
 */
public class RecycleMoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<String> mDatas;
    private LayoutInflater layoutInflater;

    public RecycleMoreAdapter(Context context, List<String> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        layoutInflater = LayoutInflater.from(this.context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = layoutInflater.inflate(R.layout.item_more_tools, parent, false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof MyViewHolder) {
            final MyViewHolder myViewHolder = (MyViewHolder) viewHolder;

            myViewHolder.tvTitleMore.setText(mDatas.get(position));

            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
            myViewHolder.recyclerViewItem.setLayoutManager(gridLayoutManager);
            myViewHolder.recyclerViewItem.setItemAnimator(new DefaultItemAnimator());

            ArrayList<String> mDatasItem = new ArrayList<>();
            if (position == 0) {
                //便民服务
                mDatasItem.add("垃圾分类");
                mDatasItem.add("新华字典");
                mDatasItem.add("二维码生成");
                mDatasItem.add("扫一扫");
                mDatasItem.add("扫码记录");
//                mDatasItem.add("邮编查询");
//                mDatasItem.add("菜谱查询");
//                mDatasItem.add("身份证查询");
//                mDatasItem.add("中国彩票开奖结果");
//                mDatasItem.add("微信精选");
            } else if (position == 1) {
                //生活阅读
                mDatasItem.add("历史上的今天");
                mDatasItem.add("笑话段子");
            } else if (position == 2) {
                //休闲旅游
                mDatasItem.add("周公解梦");
                mDatasItem.add("婚姻匹配");
                mDatasItem.add("八字算命");
                mDatasItem.add("老黄历");
//                mDatasItem.add("电影票房");
//                mDatasItem.add("足球五大联赛");
//                mDatasItem.add("火车票查询");
//                mDatasItem.add("航班信息查询");
            } else if (position == 3) {
                //工具集合
                mDatasItem.add("手机号码归属地");
                mDatasItem.add("IP地址");
                mDatasItem.add("世界电话区号");
//                mDatasItem.add("全国省市今日油价");
//                mDatasItem.add("汽车信息查询");
//                mDatasItem.add("驾考题库");
            }

            final ArrayList<String> mDatasTitle = mDatasItem;
            RecycleMoreItemAdapter recycleMoreItemAdapter = new RecycleMoreItemAdapter(context, mDatasItem);
            myViewHolder.recyclerViewItem.setAdapter(recycleMoreItemAdapter);
            recycleMoreItemAdapter.setOnItemClickLitener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    String title = mDatasTitle.get(position);
                    if (title.equals("手机号码归属地")) {
                        context.startActivity(new Intent(context, PhoneAddressActivity.class));
                    } else if (title.equals("扫一扫")) {
                        ZxingScanUtils.open((Activity) context);
                    } else if (title.equals("扫码记录")) {
                        context.startActivity(new Intent(context, ScanResultActivity.class));
                    } else if (title.equals("IP地址")) {
                        context.startActivity(new Intent(context, IPQueryActivity.class));
                    } else if (title.equals("中国彩票开奖结果")) {
                        context.startActivity(new Intent(context, LotteryCategoryActivity.class));
                    } else if (title.equals("周公解梦")) {
                        IntentUtils.startToWebActivity(context, "工具", "周公解梦", "http://tools.2345.com/zhgjm.htm");
                    } else if (title.equals("婚姻匹配")) {
                        IntentUtils.startToWebActivity(context, "工具", "婚姻匹配", "http://www.jjdzc.com/peidui/hehun.html");
                    } else if (title.equals("八字算命")) {
                        IntentUtils.startToWebActivity(context, "工具", "八字算命", "http://www.jjdzc.com/sm/bz.html");
                    } else if (title.equals("老黄历")) {
                        context.startActivity(new Intent(context, ChineseCalendarActivity.class));
                    } else if (title.equals("火车票查询")) {
                        context.startActivity(new Intent(context, TrainActivity.class));
                    } else if (title.equals("航班信息查询")) {
                        context.startActivity(new Intent(context, FlightActivity.class));
                    } else if (title.equals("垃圾分类")) {
                        context.startActivity(new Intent(context, RubbishActivity.class));
                    } else if (title.equals("历史上的今天")) {
                        context.startActivity(new Intent(context, HistoryTodayActivity.class));
                    } else if (title.equals("世界电话区号")) {
                        context.startActivity(new Intent(context, WorldPhoneCodeActivity.class));
                    } else if (title.equals("新华字典")) {
                        context.startActivity(new Intent(context, DictionaryActivity.class));
                    } else if (title.equals("笑话段子")) {
                        MySnackbar.makeSnackBarGreen(myViewHolder.recyclerViewItem, "功能暂未开通,敬请期待");
                    }
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_title_more)
        TextView tvTitleMore;
        @Bind(R.id.recyclerViewItem)
        RecyclerView recyclerViewItem;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
