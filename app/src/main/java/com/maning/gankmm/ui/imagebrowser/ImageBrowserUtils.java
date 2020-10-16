package com.maning.gankmm.ui.imagebrowser;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import androidx.fragment.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;

import com.maning.gankmm.R;
import com.maning.gankmm.app.MyApplication;
import com.maning.gankmm.bean.gank2.GankEntity;
import com.maning.gankmm.constant.Constants;
import com.maning.gankmm.db.GankDaoManager;
import com.maning.gankmm.listeners.OnItemClickListener;
import com.maning.gankmm.ui.dialog.ListFragmentDialog;
import com.maning.gankmm.utils.BitmapUtils;
import com.maning.gankmm.utils.IntentUtils;
import com.maning.gankmm.utils.PermissionUtils;
import com.maning.gankmm.utils.ThreadPoolUtils;
import com.maning.imagebrowserlibrary.listeners.OnLongClickListener;
import com.maning.imagebrowserlibrary.model.ImageBrowserConfig;
import com.maning.mndialoglibrary.MProgressDialog;
import com.maning.mndialoglibrary.MStatusDialog;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author : maning
 * @date : 2020-10-16
 * @desc :
 */
public class ImageBrowserUtils {

    public static void open(FragmentActivity context, View view, int position, final ArrayList<String> imageList, final ArrayList<GankEntity> gankEntityList) {
        if (imageList == null || imageList.size() == 0) {
            return;
        }
        com.maning.imagebrowserlibrary.MNImageBrowser.with(context)
                //指示器效果
                .setIndicatorType(ImageBrowserConfig.IndicatorType.Indicator_Number)
                //设置隐藏指示器
                .setIndicatorHide(false)
                //当前位置
                .setCurrentPosition(position)
                //图片引擎
                .setImageEngine(new GlideImageEngine())
                //图片集合
                .setImageList(imageList)
                //长按监听
                .setOnLongClickListener(new OnLongClickListener() {
                    @Override
                    public void onLongClick(final FragmentActivity activity, final View imageView, int position, String url) {
                        //长按事件
                        if (gankEntityList != null && gankEntityList.size() == imageList.size()) {
                            showBottomSheet(activity, (ImageView) imageView, url, gankEntityList.get(position));
                        } else {
                            showBottomSheet(activity, (ImageView) imageView, url, null);
                        }
                    }
                })
                //全屏模式
                .setFullScreenMode(false)
                //显示：传入当前View
                .show(view);
    }

    public static void showBottomSheet(final FragmentActivity context, final ImageView imageView, final String url, final GankEntity gankEntity) {
        ArrayList<String> mListDialogDatas = new ArrayList<>();
        mListDialogDatas.add("保存");
        mListDialogDatas.add("分享");
        mListDialogDatas.add("设置壁纸");

        if (gankEntity != null) {
            //查询是否存在收藏
            boolean isCollect = GankDaoManager.getCollectDao().queryOneCollectByID(gankEntity.get_id());
            if (isCollect) {
                mListDialogDatas.add("取消收藏");
            } else {
                mListDialogDatas.add("收藏");
            }
        }

        ListFragmentDialog.newInstance(context).showDailog(context.getSupportFragmentManager(), mListDialogDatas, new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == 0) {
                    //保存图片
                    PermissionUtils.checkWritePermission(context, new PermissionUtils.PermissionCallBack() {
                        @Override
                        public void onGranted() {
                            saveImage(context, imageView, url);
                        }

                        @Override
                        public void onDenied() {
                            showProgressError(context, "获取存储权限失败，请前往设置页面打开存储权限");
                        }
                    });
                } else if (position == 1) {
                    IntentUtils.startAppShareText(context, "GankMM图片分享", "分享图片：" + url);
                } else if (position == 2) {
                    setWallpaper(context, imageView);
                } else if (position == 3) {
                    //查询是否存在收藏
                    boolean isCollect = GankDaoManager.getCollectDao().queryOneCollectByID(gankEntity.get_id());
                    if (isCollect) {
                        //取消收藏
                        boolean deleteResult = GankDaoManager.getCollectDao().deleteOneCollect(gankEntity.get_id());
                        if (deleteResult) {
                            showProgressSuccess(context, "取消收藏成功");
                        } else {
                            showProgressError(context, "取消收藏失败");
                        }
                    } else {
                        //收藏
                        boolean insertResult = GankDaoManager.getCollectDao().insertOneCollect(gankEntity);
                        if (insertResult) {
                            showProgressSuccess(context, "收藏成功");
                        } else {
                            showProgressError(context, "收藏失败");
                        }
                    }
                }
            }
        });
    }

    private static void saveImage(final Context context, final ImageView imageView, final String url) {
        showProgressDialog(context, "正在保存图片...");
        imageView.setDrawingCacheEnabled(true);
        final Bitmap bitmap = Bitmap.createBitmap(imageView.getDrawingCache());
        imageView.setDrawingCacheEnabled(false);
        if (bitmap == null) {
            return;
        }
        ThreadPoolUtils.execute(new Runnable() {
            @Override
            public void run() {
                final boolean saveBitmapToSD = BitmapUtils.saveBitmapToSD(bitmap, Constants.BasePath, System.currentTimeMillis() + ".jpg", true);
                MyApplication.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        dissmissProgressDialog();
                        if (saveBitmapToSD) {
                            showProgressSuccess(context, "保存成功");
                        } else {
                            showProgressError(context, "保存失败");
                        }
                    }
                });
            }
        });
    }

    private static void setWallpaper(final Context context, ImageView imageView) {
        showProgressDialog(context, "正在设置壁纸...");
        imageView.setDrawingCacheEnabled(true);
        final Bitmap bitmap = Bitmap.createBitmap(imageView.getDrawingCache());
        imageView.setDrawingCacheEnabled(false);
        if (bitmap == null) {
            showProgressError(context, "设置失败");
            return;
        }
        ThreadPoolUtils.execute(new Runnable() {
            @Override
            public void run() {
                boolean flag = false;
                WallpaperManager manager = WallpaperManager.getInstance(context);
                try {
                    manager.setBitmap(bitmap);
                    flag = true;
                } catch (IOException e) {
                    e.printStackTrace();
                    flag = false;
                } finally {
                    if (flag) {
                        MyApplication.getHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                dissmissProgressDialog();
                                showProgressSuccess(context, "设置成功");
                            }
                        });
                    } else {
                        MyApplication.getHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                dissmissProgressDialog();
                                showProgressError(context, "设置失败");
                            }
                        });
                    }

                }
            }
        });
    }

    public static void showProgressDialog(Context context) {
        MProgressDialog.showProgress(context);
    }

    public static void showProgressDialog(Context context, String message) {
        MProgressDialog.showProgress(context, message);
    }

    public static void showProgressSuccess(Context context, String message) {
        new MStatusDialog(context).show(message, context.getResources().getDrawable(R.drawable.mn_icon_dialog_success));
    }

    public static void showProgressError(Context context, String message) {
        new MStatusDialog(context).show(message, context.getResources().getDrawable(R.drawable.mn_icon_dialog_fail));
    }

    public static void dissmissProgressDialog() {
        MProgressDialog.dismissProgress();
    }

}
