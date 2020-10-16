package com.maning.gankmm.ui.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.maning.gankmm.R;
import com.maning.gankmm.bean.mob.MobCookCategoryEntity;
import com.maning.gankmm.listeners.OnItemClickListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by maning on 16/5/17.
 */
public class RecycleCookCategoryAdapter extends RecyclerView.Adapter<RecycleCookCategoryAdapter.MyViewHolder> {

    private Context context;
    private List<MobCookCategoryEntity> mDatas;
    private LayoutInflater layoutInflater;
    private int flag = 0;   //0左边还,1右边菜单
    private int currentPosition;

    public RecycleCookCategoryAdapter(Context context, List<MobCookCategoryEntity> mDatas, int flag) {
        this.context = context;
        this.mDatas = mDatas;
        this.flag = flag;
        layoutInflater = LayoutInflater.from(this.context);
    }

    private OnItemClickListener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickListener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View inflate = layoutInflater.inflate(R.layout.item_cook_category, parent, false);

        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position) {

        MobCookCategoryEntity mobCookCategoryEntity = mDatas.get(position);

        viewHolder.tv_title.setText(mobCookCategoryEntity.getCategoryInfo().getName());
        //如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(viewHolder.itemView, position);
                    if (flag == 0) {
                        currentPosition = position;
                        notifyDataSetChanged();
                    }
                }
            });
        }

        if (flag == 0) {
            viewHolder.tv_title.setTextColor(context.getResources().getColor(R.color.black_text2_color));
            viewHolder.item_bg.setBackgroundResource(R.color.itemGrayBg);
            viewHolder.line.setVisibility(View.GONE);
            if (currentPosition == position) {
                viewHolder.tv_title.setTextColor(context.getResources().getColor(R.color.main_color));
                viewHolder.item_bg.setBackgroundResource(R.color.white);
                viewHolder.line.setVisibility(View.VISIBLE);
            }
        } else {
            viewHolder.item_bg.setBackgroundResource(R.color.white);
            viewHolder.line.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_title)
        TextView tv_title;
        @Bind(R.id.line)
        TextView line;
        @Bind(R.id.item_bg)
        RelativeLayout item_bg;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
