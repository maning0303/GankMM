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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.maning.gankmm.R;
import com.maning.gankmm.bean.mob.MobCookDetailEntity;
import com.maning.gankmm.bean.mob.MobCookStepEntity;
import com.maning.gankmm.utils.IntentUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 更多功能的Adapter
 */
public class RecycleCookDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private MobCookDetailEntity.ListBean mData;
    private LayoutInflater layoutInflater;
    private ArrayList<MobCookStepEntity> steps = new ArrayList<>();
    private ArrayList<String> imagesList = new ArrayList<>();

    public RecycleCookDetailsAdapter(Context context, MobCookDetailEntity.ListBean mData) {
        this.context = context;
        this.mData = mData;
        layoutInflater = LayoutInflater.from(this.context);

        String method = mData.getRecipe().getMethod();
        if (!TextUtils.isEmpty(method)) {
            //解析数据
            try {
                Type type = new TypeToken<ArrayList<MobCookStepEntity>>() {
                }.getType();
                steps = new Gson().fromJson(method, type);
            } catch (Exception e) {

            }

        }

        //图片集合
        imagesList.add(mData.getRecipe().getImg());
        for (int i = 0; i < steps.size(); i++) {
            String img = steps.get(i).getImg();
            imagesList.add(img);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View inflate = layoutInflater.inflate(R.layout.item_cook_details_01, parent, false);
            return new MyViewHolder(inflate);
        } else {
            View inflate = layoutInflater.inflate(R.layout.item_cook_details_02, parent, false);
            return new MyViewHolder2(inflate);
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        }
        return 1;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof MyViewHolder) {
            final MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
            //图片展示
            RequestOptions options = new RequestOptions();
            options.centerCrop();
            options.placeholder(R.drawable.pic_gray_bg);
            Glide.with(context).asBitmap().load(mData.getRecipe().getImg()).apply(options).into(myViewHolder.iv_show);
            //大标题
            myViewHolder.tv_name.setText(mData.getName());
            //介绍
            myViewHolder.tv_sumary.setText(mData.getRecipe().getSumary());
            //食物清单
            myViewHolder.tv_ingredients.setText(mData.getRecipe().getIngredients());

            myViewHolder.iv_show.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (imagesList.size() > 0) {
                        IntentUtils.startToImageShow(context, imagesList, 0, myViewHolder.iv_show);
                    }
                }
            });


        } else if (viewHolder instanceof MyViewHolder2) {
            final MyViewHolder2 myViewHolder2 = (MyViewHolder2) viewHolder;

            MobCookStepEntity mobCookStepEntity = steps.get(position - 1);
            //图片展示
            RequestOptions options = new RequestOptions();
            options.centerCrop();
            options.placeholder(R.drawable.pic_gray_bg);
            Glide.with(context).asBitmap().load(mobCookStepEntity.getImg()).apply(options).into(myViewHolder2.iv_step);
            //文字步骤
            myViewHolder2.tv_step.setText(mobCookStepEntity.getStep());

            myViewHolder2.iv_step.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (imagesList.size() > 0) {
                        IntentUtils.startToImageShow(context, imagesList, position, myViewHolder2.iv_step);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return steps.size() + 1;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_show)
        ImageView iv_show;
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_sumary)
        TextView tv_sumary;
        @BindView(R.id.tv_ingredients)
        TextView tv_ingredients;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class MyViewHolder2 extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_step)
        ImageView iv_step;
        @BindView(R.id.tv_step)
        TextView tv_step;

        public MyViewHolder2(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
