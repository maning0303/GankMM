package com.maning.gankmm.ui.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.maning.gankmm.R;
import com.maning.gankmm.bean.mob.MobCookDetailEntity;
import com.maning.gankmm.listeners.OnItemClickListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by maning on 16/5/17.
 */
public class RecycleCookDetailItemAdapter extends RecyclerView.Adapter<RecycleCookDetailItemAdapter.MyViewHolder> {

    private Context context;
    private List<MobCookDetailEntity.ListBean> mDatas;
    private LayoutInflater layoutInflater;

    public RecycleCookDetailItemAdapter(Context context, List<MobCookDetailEntity.ListBean> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        layoutInflater = LayoutInflater.from(this.context);
    }

    public void updateDatas(List<MobCookDetailEntity.ListBean> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View inflate = layoutInflater.inflate(R.layout.item_cook_detail_item, parent, false);

        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position) {

        MobCookDetailEntity.ListBean listBean = mDatas.get(position);

        //图片
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        options.placeholder(R.drawable.pic_gray_bg);
        Glide.with(context)
                .load(listBean.getThumbnail())
                .apply(options)
                .into(viewHolder.iv_cook_show);

        //标题
        viewHolder.tv_title.setText(listBean.getName());
        //配料
        viewHolder.tv_ingredients.setText(listBean.getRecipe().getIngredients());
        //分类标题
        viewHolder.tv_tag.setText(listBean.getCtgTitles());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickLitener != null) {
                    mOnItemClickLitener.onItemClick(view, position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_cook_show)
        ImageView iv_cook_show;
        @Bind(R.id.tv_title)
        TextView tv_title;
        @Bind(R.id.tv_ingredients)
        TextView tv_ingredients;
        @Bind(R.id.tv_tag)
        TextView tv_tag;

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
