package com.maning.gankmm.ui.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maning.gankmm.R;
import com.maning.gankmm.bean.gank2.Gank2SearchListBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by maning on 2017/3/1.
 */

public class RecycleSearchAdapter extends RecyclerView.Adapter<RecycleSearchAdapter.MyViewHolder> {

    private Context context;
    private List<Gank2SearchListBean.DataEntity> mDatas;
    private LayoutInflater layoutInflater;

    public RecycleSearchAdapter(Context context, List<Gank2SearchListBean.DataEntity> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        layoutInflater = LayoutInflater.from(this.context);
    }

    private RecycleSearchAdapter.OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(RecycleSearchAdapter.OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public RecycleSearchAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = layoutInflater.inflate(R.layout.item_search, parent, false);

        return new RecycleSearchAdapter.MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final RecycleSearchAdapter.MyViewHolder viewHolder, final int position) {
        Gank2SearchListBean.DataEntity searchBean = mDatas.get(position);
        viewHolder.tvDesc.setText(searchBean.getDesc());
        viewHolder.tvType.setText(searchBean.getType());
        viewHolder.tvTime.setText(searchBean.getPublishedAt());

        // Android | iOS | Flutter | frontend | backend | app
        String type = searchBean.getType();
        if("Android".equals(type)){
            viewHolder.tvType.setBackgroundColor(context.getResources().getColor(R.color.type_01));
        }else if("iOS".equals(type)){
            viewHolder.tvType.setBackgroundColor(context.getResources().getColor(R.color.type_02));
        }else if("Flutter".equals(type)){
            viewHolder.tvType.setBackgroundColor(context.getResources().getColor(R.color.type_03));
        }else if("frontend".equals(type)){
            viewHolder.tvType.setBackgroundColor(context.getResources().getColor(R.color.type_04));
        }else if("backend".equals(type)){
            viewHolder.tvType.setBackgroundColor(context.getResources().getColor(R.color.type_05));
        }else if("App".equals(type)){
            viewHolder.tvType.setBackgroundColor(context.getResources().getColor(R.color.type_06));
        }else{
            viewHolder.tvType.setBackgroundColor(context.getResources().getColor(R.color.type_07));
        }

        //如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(viewHolder.itemView, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void setNewDatas(List<Gank2SearchListBean.DataEntity> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.tv_desc)
        TextView tvDesc;
        @Bind(R.id.tv_type)
        TextView tvType;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

}
