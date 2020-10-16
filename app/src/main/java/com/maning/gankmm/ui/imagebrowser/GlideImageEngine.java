package com.maning.gankmm.ui.imagebrowser;

import android.content.Context;
import android.graphics.Bitmap;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.maning.gankmm.R;
import com.maning.imagebrowserlibrary.ImageEngine;

/**
 * @author : maning
 * @date : 2020-10-16
 * @desc :
 */
public class GlideImageEngine implements ImageEngine {
    @Override
    public void loadImage(Context context, String url, ImageView imageView, final View progressView, View customImageView) {
        RequestOptions options = new RequestOptions();
        options.fitCenter();
        options.placeholder(R.drawable.pic_gray_bg);
        options.error(R.drawable.pic_gray_bg);
        Glide.with(context)
                .asBitmap()
                .load(url)
                .apply(options)
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        //隐藏进度View,必须设置setCustomProgressViewLayoutID
                        progressView.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        //隐藏进度View,必须设置setCustomProgressViewLayoutID
                        progressView.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(imageView);
    }
}
