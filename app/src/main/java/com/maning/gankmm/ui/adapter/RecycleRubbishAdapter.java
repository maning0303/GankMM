package com.maning.gankmm.ui.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maning.gankmm.R;
import com.maning.gankmm.bean.rolltools.RubbishTypeResultBean;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 垃圾分类
 */
public class RecycleRubbishAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<RubbishTypeResultBean.DataEntity.AimEntity> mDatas;
    private LayoutInflater layoutInflater;

    public RecycleRubbishAdapter(Context context, ArrayList<RubbishTypeResultBean.DataEntity.AimEntity> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        layoutInflater = LayoutInflater.from(this.context);
    }

    public void upddateDatas(ArrayList<RubbishTypeResultBean.DataEntity.AimEntity> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = layoutInflater.inflate(R.layout.item_rubbish, parent, false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder) viewHolder;

            RubbishTypeResultBean.DataEntity.AimEntity aimEntity = mDatas.get(position);

            myViewHolder.tv_name.setText(aimEntity.getGoodsName());
            myViewHolder.tv_type.setText(aimEntity.getGoodsType());
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_name)
        TextView tv_name;
        @Bind(R.id.tv_type)
        TextView tv_type;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
