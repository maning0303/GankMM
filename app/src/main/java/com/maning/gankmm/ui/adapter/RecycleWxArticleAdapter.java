package com.maning.gankmm.ui.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.maning.gankmm.R;
import com.maning.gankmm.bean.mob.MobWxArticleListEntity;
import com.maning.gankmm.listeners.OnItemClickListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 微信分类
 */
public class RecycleWxArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<MobWxArticleListEntity.ListBean> mDatas;
    private LayoutInflater layoutInflater;

    public RecycleWxArticleAdapter(Context context, ArrayList<MobWxArticleListEntity.ListBean> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        layoutInflater = LayoutInflater.from(this.context);
    }

    public void updateDatas(ArrayList<MobWxArticleListEntity.ListBean> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = layoutInflater.inflate(R.layout.item_wx_article, parent, false);
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

            MobWxArticleListEntity.ListBean listBean = mDatas.get(position);

            myViewHolder.tv_title_wx.setText(listBean.getTitle());
            myViewHolder.tv_time_wx.setText(listBean.getPubTime());

            //图片处理
            myViewHolder.iv_show_wx.setVisibility(View.VISIBLE);
            myViewHolder.iv_show_wx.setImageResource(R.drawable.pic_gray_bg);
            String thumbnails = listBean.getThumbnails();
            if (!TextUtils.isEmpty(thumbnails)) {
                String[] images = thumbnails.split("$");
                if (images.length > 0) {
                    RequestOptions options = new RequestOptions();
                    options.centerCrop();
                    options.placeholder(R.drawable.pic_gray_bg);
                    Glide.with(context)
                            .load(images[0])
                            .apply(options)
                            .into(myViewHolder.iv_show_wx);
                } else {
                    myViewHolder.iv_show_wx.setVisibility(View.GONE);
                }
            } else {
                myViewHolder.iv_show_wx.setVisibility(View.GONE);
            }

        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title_wx)
        TextView tv_title_wx;
        @BindView(R.id.tv_time_wx)
        TextView tv_time_wx;
        @BindView(R.id.iv_show_wx)
        ImageView iv_show_wx;

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
