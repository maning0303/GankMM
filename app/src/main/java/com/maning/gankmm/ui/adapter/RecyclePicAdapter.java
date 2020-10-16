package com.maning.gankmm.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.ldoublem.thumbUplib.ThumbUpView;
import com.maning.gankmm.R;
import com.maning.gankmm.bean.gank2.GankEntity;
import com.maning.gankmm.bean.PicSizeEntity;
import com.maning.gankmm.db.GankDaoManager;
import com.maning.gankmm.utils.DensityUtil;
import com.maning.gankmm.utils.IntentUtils;
import com.maning.gankmm.utils.MySnackbar;
import com.maning.library.SwitcherView;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by maning on 16/5/17.
 * 首页瀑布流图片展示
 */
public class RecyclePicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<GankEntity> commonDataResults;
    private List<GankEntity> headLines;
    private ArrayList<String> headLinesStrs;
    private LayoutInflater layoutInflater;
    private int screenWidth;
    private MyViewHolderHeader myViewHolderHeader;
    private RequestOptions options;
    //保存图片Size的集合
    private ArrayMap<String, PicSizeEntity> picSizeEntityArrayMap = new ArrayMap<>();

    public RecyclePicAdapter(Context context, List<GankEntity> commonDataResults) {
        this.context = context;
        this.commonDataResults = commonDataResults;
        layoutInflater = LayoutInflater.from(this.context);
        screenWidth = DensityUtil.getWidth(context);
        options = new RequestOptions();
        options.fitCenter();
        options.placeholder(R.drawable.pic_gray_bg);
        options.diskCacheStrategy(DiskCacheStrategy.ALL);

    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    private void updateHeadLinesStrs() {
        if (headLines != null && headLines.size() > 0) {
            headLinesStrs = new ArrayList<>();
            for (int i = 0; i < headLines.size(); i++) {
                headLinesStrs.add(headLines.get(i).getDesc());
            }
        }
    }

    public void updateDatas(List<GankEntity> commonDataResults) {
        this.commonDataResults = commonDataResults;
        notifyDataSetChanged();
    }

    public void updateHeadLines(List<GankEntity> headLines) {
        this.headLines = headLines;
        updateHeadLinesStrs();
        notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    public void destroyList() {
        if (headLines != null) {
            headLines.clear();
            headLines = null;
        }
        if (commonDataResults != null) {
            commonDataResults.clear();
            commonDataResults = null;
        }
        if (myViewHolderHeader != null) {
            myViewHolderHeader.destroyHeadLines();
        }
    }

    public List<GankEntity> getAllDatas() {
        return this.commonDataResults;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (0 == viewType) {
            View inflate = layoutInflater.inflate(R.layout.item_girl_header, parent, false);
            myViewHolderHeader = new MyViewHolderHeader(inflate);
            return myViewHolderHeader;
        } else {
            View inflate = layoutInflater.inflate(R.layout.item_girl_staggered, parent, false);
            return new MyViewHolder(inflate);
        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MyViewHolderHeader) {
            final MyViewHolderHeader viewHolder = (MyViewHolderHeader) holder;
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) viewHolder.itemView.getLayoutParams();
            layoutParams.setFullSpan(true);
            if (headLines != null && headLines.size() > 0) {
                viewHolder.tvLoadingHeadLine.setVisibility(View.GONE);
                viewHolder.switcherView.setVisibility(View.VISIBLE);
                //设置数据源
                viewHolder.switcherView.setResource(headLinesStrs);
                //开始滚动
                viewHolder.switcherView.startRolling();
                //点击事件
                viewHolder.switcherView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int index = viewHolder.switcherView.getCurrentIndex();
                        GankEntity randomGankEntity = headLines.get(index);
                        IntentUtils.startToWebActivity(context, randomGankEntity.getType(), randomGankEntity.getDesc(), randomGankEntity.getUrl());
                    }
                });
            } else {
                viewHolder.tvLoadingHeadLine.setVisibility(View.VISIBLE);
                viewHolder.switcherView.setVisibility(View.GONE);
            }
        } else if (holder instanceof MyViewHolder) {
            final int newPosition = position - 1;
            final MyViewHolder viewHolder = (MyViewHolder) holder;
            final GankEntity resultsEntity = commonDataResults.get(newPosition);

            viewHolder.tvShowTime.setText(resultsEntity.getDesc());

            //图片显示
            String url = resultsEntity.getUrl();

            PicSizeEntity picSizeEntity = picSizeEntityArrayMap.get(resultsEntity.getUrl());
            if (picSizeEntity != null && !picSizeEntity.isNull()) {
                int width = picSizeEntity.getPicWidth();
                int height = picSizeEntity.getPicHeight();
                //计算高宽比
                int finalHeight = (screenWidth / 2) * height / width;
                ViewGroup.LayoutParams layoutParams = viewHolder.rlRoot.getLayoutParams();
                layoutParams.height = finalHeight;
            }

            Glide.with(context)
                    .asBitmap()
                    .load(url)
                    .apply(options)
                    .thumbnail(0.2f)
                    .listener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                            KLog.e("图片加载失败：" + e.toString());
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                            PicSizeEntity picSizeEntity = picSizeEntityArrayMap.get(resultsEntity.getUrl());
                            if (picSizeEntity == null || picSizeEntity.isNull()) {
                                int width = resource.getWidth();
                                int height = resource.getHeight();
                                //计算高宽比
                                int finalHeight = (screenWidth / 2) * height / width;
                                ViewGroup.LayoutParams layoutParams = viewHolder.rlRoot.getLayoutParams();
                                layoutParams.height = finalHeight;
                                picSizeEntityArrayMap.put(resultsEntity.getUrl(), new PicSizeEntity(width, height));
                            }
                            return false;
                        }
                    })
                    .into(viewHolder.image);

            //查询是否存在收藏
            boolean isCollect = GankDaoManager.getCollectDao().queryOneCollectByID(resultsEntity.get_id());
            if (isCollect) {
                viewHolder.btnCollect2.setLike();
            } else {
                viewHolder.btnCollect2.setUnlike();
            }

            viewHolder.btnCollect2.setOnThumbUp(new ThumbUpView.OnThumbUp() {
                @Override
                public void like(boolean like) {
                    if (like) {
                        boolean insertResult = GankDaoManager.getCollectDao().insertOneCollect(resultsEntity);
                        if (insertResult) {
                            MySnackbar.makeSnackBarBlack(viewHolder.tvShowTime, "收藏成功");
                        } else {
                            viewHolder.btnCollect2.setUnlike();
                            MySnackbar.makeSnackBarRed(viewHolder.tvShowTime, "收藏失败");
                        }
                    } else {
                        boolean deleteResult = GankDaoManager.getCollectDao().deleteOneCollect(resultsEntity.get_id());
                        if (deleteResult) {
                            MySnackbar.makeSnackBarBlack(viewHolder.tvShowTime, "取消收藏成功");
                        } else {
                            viewHolder.btnCollect2.setLike();
                            MySnackbar.makeSnackBarRed(viewHolder.tvShowTime, "取消收藏失败");
                        }
                    }
                }
            });

            //如果设置了回调，则设置点击事件
            if (mOnItemClickLitener != null) {
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickLitener.onItemClick(viewHolder.itemView, newPosition);
                    }
                });
            }
        }

    }

    @Override
    public int getItemCount() {
        if (commonDataResults != null) {
            return commonDataResults.size() + 1;
        } else {
            return 0;
        }

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.image)
        ImageView image;
        @Bind(R.id.tvShowTime)
        TextView tvShowTime;
        @Bind(R.id.rl_root)
        RelativeLayout rlRoot;
        @Bind(R.id.btn_collect2)
        ThumbUpView btnCollect2;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class MyViewHolderHeader extends RecyclerView.ViewHolder {
        @Bind(R.id.switcherView)
        SwitcherView switcherView;

        @Bind(R.id.tv_loading_headline)
        TextView tvLoadingHeadLine;

        public MyViewHolderHeader(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void destroyHeadLines() {
            switcherView.destroySwitcher();
        }
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }
}
