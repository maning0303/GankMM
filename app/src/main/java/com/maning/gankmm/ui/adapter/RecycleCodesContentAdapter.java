package com.maning.gankmm.ui.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.maning.gankmm.R;
import com.maning.gankmm.bean.web.CategoryContentBean;

import java.util.List;

/**
 * Created by maning on 16/6/22.
 */
public class RecycleCodesContentAdapter extends RecyclerView.Adapter<RecycleCodesContentAdapter.MyViewHolder> {

    private Context context;
    private final RequestManager glide;
    private List<CategoryContentBean> mDatas;
    private LayoutInflater layoutInflater;

    public RecycleCodesContentAdapter(Context context, List<CategoryContentBean> mDatas, RequestManager glide) {
        this.context = context;
        this.glide = glide;
        this.mDatas = mDatas;
        layoutInflater = LayoutInflater.from(this.context);
    }

    public void setDatas(List<CategoryContentBean> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = layoutInflater.inflate(R.layout.item_codes_content, parent, false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        CategoryContentBean categoryContentBean = mDatas.get(position);

        holder.tvTitle.setText(categoryContentBean.getTitle());
        holder.tvDescription.setText(categoryContentBean.getDescription());
        holder.tvOtherInfo.setText(categoryContentBean.getOtherInfo());


        String imageUrl = categoryContentBean.getImageUrl();
        RequestOptions options = new RequestOptions();
        if (imageUrl.contains(".gif")) {
            options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
            glide.asGif().load(imageUrl).apply(options).into(holder.ivShow);
        } else {
            options.centerCrop();
            glide.asBitmap().load(imageUrl).apply(options).into(holder.ivShow);
        }


        //如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(holder.itemView, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvDescription;
        TextView tvOtherInfo;
        ImageView ivShow;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivShow = (ImageView) itemView.findViewById(R.id.iv_show);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvOtherInfo = (TextView) itemView.findViewById(R.id.tv_other_info);
            tvDescription = (TextView) itemView.findViewById(R.id.tv_description);
        }
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
}
