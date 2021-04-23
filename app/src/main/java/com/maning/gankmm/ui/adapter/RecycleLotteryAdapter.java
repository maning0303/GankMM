package com.maning.gankmm.ui.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.maning.gankmm.R;
import com.maning.gankmm.listeners.OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by maning on 16/5/17.
 */
public class RecycleLotteryAdapter extends RecyclerView.Adapter<RecycleLotteryAdapter.MyViewHolder> {

    private Context context;
    private List<String> mDatas;
    private LayoutInflater layoutInflater;

    public RecycleLotteryAdapter(Context context, List<String> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        layoutInflater = LayoutInflater.from(this.context);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View inflate = layoutInflater.inflate(R.layout.item_lottery_item, parent, false);

        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position) {

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickLitener != null) {
                    mOnItemClickLitener.onItemClick(view, position);
                }
            }
        });

//        "双色球",
//        "大乐透",
//        "3D",
//        "排列3",
//        "排列5",
//        "七星彩",
//        "七乐彩",
//        "胜负彩",
//        "任选九",
//        "六场半全场",
//        "四场进球"

        String name = mDatas.get(position);
        viewHolder.tv_name.setText(name);

        if ("双色球".equals(name)) {
            viewHolder.iv_pic.setBackgroundResource(R.drawable.gank_icon_lottery_ssq);
        } else if ("大乐透".equals(name)) {
            viewHolder.iv_pic.setBackgroundResource(R.drawable.gank_icon_lottery_dlt);
        } else if ("3D".equals(name)) {
            viewHolder.iv_pic.setBackgroundResource(R.drawable.gank_icon_lottery_welfare3d);
        } else if ("排列3".equals(name)) {
            viewHolder.iv_pic.setBackgroundResource(R.drawable.gank_icon_lottery_pl3);
        } else if ("排列5".equals(name)) {
            viewHolder.iv_pic.setBackgroundResource(R.drawable.gank_icon_lottery_pl5);
        } else if ("七星彩".equals(name)) {
            viewHolder.iv_pic.setBackgroundResource(R.drawable.gank_icon_lottery_sevenstar);
        } else if ("七乐彩".equals(name)) {
            viewHolder.iv_pic.setBackgroundResource(R.drawable.gank_icon_lottery_sevenle);
        } else if ("胜负彩".equals(name)) {
            viewHolder.iv_pic.setBackgroundResource(R.drawable.gank_icon_lottery_sfzc14);
        } else if ("任选九".equals(name)) {
            viewHolder.iv_pic.setBackgroundResource(R.drawable.gank_icon_lottery_sfzc9);
        } else if ("六场半全场".equals(name)) {
            viewHolder.iv_pic.setBackgroundResource(R.drawable.gank_icon_lottery_6);
        } else if ("四场进球".equals(name)) {
            viewHolder.iv_pic.setBackgroundResource(R.drawable.gank_icon_lottery_4);
        }


    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_pic)
        ImageView iv_pic;
        @BindView(R.id.tv_name)
        TextView tv_name;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    private OnItemClickListener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickListener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

}
