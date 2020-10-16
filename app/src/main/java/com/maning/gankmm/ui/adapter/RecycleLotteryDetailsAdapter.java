package com.maning.gankmm.ui.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maning.gankmm.R;
import com.maning.gankmm.bean.mob.MobLotteryEntity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by maning on 16/5/17.
 */
public class RecycleLotteryDetailsAdapter extends RecyclerView.Adapter<RecycleLotteryDetailsAdapter.MyViewHolder> {

    private Context context;
    private List<MobLotteryEntity.LotteryDetailsBean> mDatas;
    private LayoutInflater layoutInflater;

    public RecycleLotteryDetailsAdapter(Context context, List<MobLotteryEntity.LotteryDetailsBean> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        layoutInflater = LayoutInflater.from(this.context);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View inflate = layoutInflater.inflate(R.layout.item_lottery_details_item, parent, false);

        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position) {
        viewHolder.ll_header.setVisibility(View.GONE);

        if (position == 0) {
            viewHolder.ll_header.setVisibility(View.VISIBLE);
        }

        MobLotteryEntity.LotteryDetailsBean lotteryDetailsBean = mDatas.get(position);
        viewHolder.tv_awards.setText(lotteryDetailsBean.getAwards());
        if (TextUtils.isEmpty(lotteryDetailsBean.getType())) {
            viewHolder.tv_awardType.setText("基本");
        } else {
            viewHolder.tv_awardType.setText(lotteryDetailsBean.getType());
        }
        viewHolder.tv_awardNumber.setText(lotteryDetailsBean.getAwardNumber() + "");
        viewHolder.tv_awardPrice.setText(lotteryDetailsBean.getAwardPrice() + "");

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_awards)
        TextView tv_awards;
        @Bind(R.id.tv_awardType)
        TextView tv_awardType;
        @Bind(R.id.tv_awardNumber)
        TextView tv_awardNumber;
        @Bind(R.id.tv_awardPrice)
        TextView tv_awardPrice;
        @Bind(R.id.ll_header)
        LinearLayout ll_header;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
