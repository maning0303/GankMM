package com.maning.gankmm.ui.adapter;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.maning.gankmm.R;
import com.maning.gankmm.bean.mob.MobTrainEntity;
import com.maning.gankmm.bean.mob.MobTrainNoEntity;
import com.maning.gankmm.listeners.OnItemClickListener;
import com.maning.gankmm.ui.activity.tools.TrainListActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 火车站站查询
 */
public class RecycleTrainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<MobTrainEntity> mDatas;
    private LayoutInflater layoutInflater;

    public RecycleTrainAdapter(Context context, ArrayList<MobTrainEntity> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        layoutInflater = LayoutInflater.from(this.context);
    }

    public void updateDatas(ArrayList<MobTrainEntity> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = layoutInflater.inflate(R.layout.item_train, parent, false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder) viewHolder;

            if (mOnItemClickLitener != null) {
                myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mOnItemClickLitener.onItemClick(view, position);
                    }
                });
            }

            MobTrainEntity mobTrainEntity = mDatas.get(position);

            //发车地点
            myViewHolder.tv_start_station.setText(mobTrainEntity.getStartStationName());
            //发车时间
            myViewHolder.tv_start_time.setText(mobTrainEntity.getStartTime());
            //车次
            myViewHolder.tv_stationTrainCode.setText(mobTrainEntity.getStationTrainCode());
            //历时
            myViewHolder.tv_lishi.setText(mobTrainEntity.getLishi());
            //到达地址
            myViewHolder.tv_end_station.setText(mobTrainEntity.getEndStationName());
            //到站时间
            myViewHolder.tv_end_time.setText(mobTrainEntity.getArriveTime());

            //判断是高铁还是非高铁
            String priceContent = "";
            if (!TextUtils.isEmpty(mobTrainEntity.getPriceyd())) {//高铁
                //商务
                String pricesw = mobTrainEntity.getPricesw();
                if (!TextUtils.isEmpty(pricesw)) {
                    priceContent += "商务:" + pricesw + "         ";
                }
                //特等座
                String pricetd = mobTrainEntity.getPricetd();
                if (!TextUtils.isEmpty(pricetd)) {
                    priceContent += "特等:" + pricetd + "         ";
                }
                //一等
                String priceyd = mobTrainEntity.getPriceyd();
                if (!TextUtils.isEmpty(priceyd)) {
                    priceContent += "一等:" + priceyd + "         ";
                }
                //二等
                String priceed = mobTrainEntity.getPriceed();
                if (!TextUtils.isEmpty(priceed)) {
                    priceContent += "二等:" + priceed + "         ";
                }
                //无座
                String pricewz = mobTrainEntity.getPricewz();
                if (!TextUtils.isEmpty(pricewz)) {
                    priceContent += "无座:" + pricewz + "         ";
                }
            } else {
                //高级软卧票价
                String pricegrw = mobTrainEntity.getPricegrw();
                if (!TextUtils.isEmpty(pricegrw)) {
                    priceContent += "高软:" + pricegrw + "        ";
                }
                //软卧票价
                String pricerw = mobTrainEntity.getPricerw();
                if (!TextUtils.isEmpty(pricerw)) {
                    priceContent += "软卧:" + pricerw + "         ";
                }
                //硬卧票价
                String priceyw = mobTrainEntity.getPriceyw();
                if (!TextUtils.isEmpty(priceyw)) {
                    priceContent += "硬卧:" + priceyw + "         ";
                }
                //软座票价
                String pricerz = mobTrainEntity.getPricerz();
                if (!TextUtils.isEmpty(pricerz)) {
                    priceContent += "软座:" + pricerz + "         ";
                }
                //硬座票价
                String priceyz = mobTrainEntity.getPriceyz();
                if (!TextUtils.isEmpty(priceyz)) {
                    priceContent += "硬座:" + priceyz + "         ";
                }
                //无座票价
                String pricewz = mobTrainEntity.getPricewz();
                if (!TextUtils.isEmpty(pricewz)) {
                    priceContent += "无座:" + pricewz + "         ";
                }

            }
            myViewHolder.tv_price.setText(priceContent);


            if (mobTrainEntity.isShowDetails()) {
                myViewHolder.iv_arrow.setImageDrawable(context.getResources().getDrawable(R.drawable.gank_icon_arrow_up));
                myViewHolder.rl_details.setVisibility(View.VISIBLE);
                ArrayList<MobTrainNoEntity> trainDetails = mobTrainEntity.getTrainDetails();
                if (trainDetails != null && trainDetails.size() > 0) {
                    myViewHolder.recyclerViewDetails.setVisibility(View.VISIBLE);

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                    myViewHolder.recyclerViewDetails.setLayoutManager(linearLayoutManager);
                    myViewHolder.recyclerViewDetails.setItemAnimator(new DefaultItemAnimator());

                    //刷新Adapter
                    RecycleTrainDetailsAdapter recycleTrainDetailsAdapter = new RecycleTrainDetailsAdapter(context, trainDetails);
                    myViewHolder.recyclerViewDetails.setAdapter(recycleTrainDetailsAdapter);

                } else {
                    myViewHolder.recyclerViewDetails.setVisibility(View.GONE);
                }
            } else {
                myViewHolder.iv_arrow.setImageDrawable(context.getResources().getDrawable(R.drawable.gank_icon_arrow_down));
                myViewHolder.rl_details.setVisibility(View.GONE);
                myViewHolder.recyclerViewDetails.setVisibility(View.GONE);
            }



            myViewHolder.btn_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((TrainListActivity) context).queryTrainNo(position);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_start_station)
        TextView tv_start_station;

        @Bind(R.id.tv_start_time)
        TextView tv_start_time;

        @Bind(R.id.tv_stationTrainCode)
        TextView tv_stationTrainCode;

        @Bind(R.id.tv_lishi)
        TextView tv_lishi;

        @Bind(R.id.tv_end_station)
        TextView tv_end_station;

        @Bind(R.id.tv_end_time)
        TextView tv_end_time;

        @Bind(R.id.iv_arrow)
        ImageView iv_arrow;

        @Bind(R.id.btn_more)
        RelativeLayout btn_more;

        @Bind(R.id.tv_price)
        TextView tv_price;

        @Bind(R.id.rl_details)
        RelativeLayout rl_details;

        @Bind(R.id.recyclerViewDetails)
        RecyclerView recyclerViewDetails;

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
