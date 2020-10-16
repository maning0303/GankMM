package com.maning.gankmm.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.maning.gankmm.R;
import com.maning.gankmm.bean.mob.MobUserInfo;
import com.maning.gankmm.http.callback.MyCallBack;
import com.maning.gankmm.skin.SkinManager;
import com.maning.gankmm.ui.base.BaseActivity;
import com.maning.gankmm.utils.AppDateMgr;
import com.maning.gankmm.utils.DialogUtils;
import com.maning.gankmm.utils.MySnackbar;
import com.socks.library.KLog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 编辑用户信息页面
 */
@Deprecated
public class EditUserInfoActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.avatar)
    CircleImageView mAvatar;
    @Bind(R.id.tv_sex)
    TextView mTvSex;
    @Bind(R.id.tv_birth)
    TextView mTvBirth;
    @Bind(R.id.tv_signature)
    TextView mTvSignature;
    @Bind(R.id.tv_user_name)
    TextView mTvUserName;

    private TimePickerView mTimePickerView;
    private MobUserInfo mUserCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);
        ButterKnife.bind(this);

        initMyToolBar();

        initDatas();

        initPickerView();

        queryUserInfo();
    }

    private void initDatas() {
        //获取信息
//        mUserCache = UserUtils.getUserCache();
        //设置头像
        RequestOptions options = new RequestOptions();
        options.placeholder(R.drawable.icon_default_avatar);
        options.error(R.drawable.icon_default_avatar);
        Glide.with(mContext).load(mUserCache.getAvatarLocal()).apply(options).into(mAvatar);
        //设置用户名
        mTvUserName.setText(mUserCache.getUserName());
        //设置性别
        mTvSex.setText(!TextUtils.isEmpty(mUserCache.getSex()) ? mUserCache.getSex() : "未填写");
        //设置出生日期
        mTvBirth.setText(!TextUtils.isEmpty(mUserCache.getBirth()) ? mUserCache.getBirth() : "未填写");
        //设置签名
        mTvSignature.setText(!TextUtils.isEmpty(mUserCache.getSignature()) ? mUserCache.getSignature() : "未填写");
    }

    private void initPickerView() {
        //时间选择器
        mTimePickerView = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                //日期转换
                String chooseDateStr = AppDateMgr.parseYyyyMmDd(date);
                mTvBirth.setText(chooseDateStr);
                mUserCache.setBirth(chooseDateStr);
            }
        })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("", "", "", "", "", "")
                .setSubmitColor(mContext.getResources().getColor(R.color.main_color))
                .setCancelColor(mContext.getResources().getColor(R.color.main_color))
                .build();
    }

    private void initMyToolBar() {
        int currentSkinType = SkinManager.getCurrentSkinType(this);
        if (SkinManager.THEME_DAY == currentSkinType) {
            initToolBar(mToolbar, "编辑资料", R.drawable.gank_ic_back_white);
        } else {
            initToolBar(mToolbar, "编辑资料", R.drawable.gank_ic_back_night);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @OnClick(R.id.rl_item_avatar)
    public void rl_item_avatar() {
        //跳转选择图片
        choosePicture();
    }

    private void choosePicture() {
        // 进入相册 以下是例子：不需要的api可以不写
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_QQ_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
                .previewImage(true)// 是否可预览图片
                .compressGrade(Luban.THIRD_GEAR)// luban压缩档次，默认3档 Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
                .isCamera(true)// 是否显示拍照按钮
                .enableCrop(true)// 是否裁剪
                .compress(true)// 是否压缩
                .compressMode(PictureConfig.LUBAN_COMPRESS_MODE)//系统自带 or 鲁班压缩 PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .withAspectRatio(1, 1)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .freeStyleCropEnabled(false)// 裁剪框是否可拖拽
                .circleDimmedLayer(false)// 是否圆形裁剪
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .cropCompressQuality(90)// 裁剪压缩质量 默认100
                .rotateEnabled(true) // 裁剪是否可旋转图片
                .scaleEnabled(true)// 裁剪是否可放大缩小图片
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    @OnClick(R.id.rl_item_user_name)
    public void rl_item_user_name() {
        MySnackbar.makeSnackBarGreen(mToolbar, "不能修改用户名");
    }

    @OnClick(R.id.rl_item_user_sex)
    public void rl_item_user_sex() {
        DialogUtils.showMyListDialog(mContext, "性别", R.array.sexs, new DialogUtils.OnDialogListCallback() {
            @Override
            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                mTvSex.setText(text);

                mUserCache.setSex(String.valueOf(text));
            }
        });
    }

    @OnClick(R.id.rl_item_user_birth)
    public void rl_item_user_birth() {
        String birth = mTvBirth.getText().toString();
        if (TextUtils.isEmpty(birth) || birth.equals("未填写")) {
            mTimePickerView.setDate(Calendar.getInstance());
        } else {
            //日期转换
            Calendar selectedDate = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date;
            try {
                date = sdf.parse(birth);
            } catch (ParseException e) {
                e.printStackTrace();
                date = new Date();
            }
            selectedDate.setTime(date);
            mTimePickerView.setDate(selectedDate);
        }
        mTimePickerView.show();
    }

    @OnClick(R.id.rl_item_user_signature)
    public void rl_item_user_signature() {
        Intent intent = new Intent(mContext, EditSignatureActivity.class);
        intent.putExtra(EditSignatureActivity.IntentKey_Signature, mUserCache.getSignature());
        startActivityForResult(intent, 0x003);
    }

    @OnClick(R.id.btn_save)
    public void btn_save() {
        updateUserInfo();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    List<LocalMedia> localMedias = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    //获取路径
                    if (localMedias != null && localMedias.size() > 0) {
                        KLog.i("onActivityResult:" + localMedias.toString());
                        LocalMedia localMedia = localMedias.get(0);
                        String compressPath = localMedia.getCompressPath();

                        //更新数据
                        mUserCache.setAvatarLocal(compressPath);

                        RequestOptions options = new RequestOptions();
                        options.placeholder(R.drawable.icon_default_avatar);
                        options.error(R.drawable.icon_default_avatar);
                        Glide.with(mContext).load(compressPath).apply(options).into(mAvatar);
                    }
                    break;
                case 0x003:
                    //个性签名回调
                    if (data != null) {
                        String signature = data.getStringExtra(EditSignatureActivity.IntentKey_Signature);
                        //更新数据
                        mUserCache.setSignature(signature);
                        //设置数据
                        mTvSignature.setText(signature);
                    }


                    break;
            }
        }
    }

    private MyCallBack httpCallBack = new MyCallBack() {
        @Override
        public void onSuccess(int what, Object result) {
            dissmissProgressDialog();
            if (what == 0x001) {
                //保存成功
                MySnackbar.makeSnackBarGreen(mToolbar, "数据更新成功");

            } else if (what == 0x002) {
                String userBaseInfo = (String) result;
                if (TextUtils.isEmpty(userBaseInfo)) {
                    return;
                }
                KLog.i("userBaseInfo:" + userBaseInfo);
                String[] values = userBaseInfo.split("&");
                if (values.length > 2) {
                    String sex = values[0];
                    String birth = values[1];
                    String signature = values[2];

                    //本地数据更新
//                    MobUserInfo userCache = UserUtils.getUserCache();
//                    userCache.setSex(sex);
//                    userCache.setBirth(birth);
//                    userCache.setSignature(signature);
//                    //保存用户信息
//                    UserUtils.saveUserCache(userCache);
                    //刷新界面
                    initDatas();
                }

            }
        }

        @Override
        public void onSuccessList(int what, List results) {

        }

        @Override
        public void onFail(int what, String result) {
            dissmissProgressDialog();
            if (what == 0x001) {
                MySnackbar.makeSnackBarRed(mToolbar, result);
            }
        }
    };

    private void updateUserInfo() {
        showProgressDialog("正在更新...");
        //信息拼接: 性别&出生年月&个性签名
        String sex = TextUtils.isEmpty(mUserCache.getSex()) ? "未填写" : mUserCache.getSex();
        String birth = TextUtils.isEmpty(mUserCache.getBirth()) ? "未填写" : mUserCache.getBirth();
        String signature = TextUtils.isEmpty(mUserCache.getSignature()) ? "未填写" : mUserCache.getSignature();
        String userBaseInfo = sex + "&" + birth + "&" + signature;
        KLog.i("userBaseInfo:" + userBaseInfo);
    }

    private void queryUserInfo() {
        showProgressDialog("加载中...");
    }

    @Override
    public void onBackPressed() {
        //对比数据
//        MobUserInfo userCacheBefore = UserUtils.getUserCache();

//        String avatarLocal = userCacheBefore.getAvatarLocal();
//        String sex = userCacheBefore.getSex();
//        String birth = userCacheBefore.getBirth();
//        String signature = userCacheBefore.getSignature();
//
//        String avatarLocal_new = mUserCache.getAvatarLocal();
//        String sex_new = mUserCache.getSex();
//        String birth_new = mUserCache.getBirth();
//        String signature_new = mUserCache.getSignature();
//
//        if (
//                (TextUtils.isEmpty(avatarLocal) ? "" : avatarLocal).equals((TextUtils.isEmpty(avatarLocal_new) ? "" : avatarLocal_new)) &&
//                        (TextUtils.isEmpty(sex) ? "" : sex).equals((TextUtils.isEmpty(sex_new) ? "" : sex_new)) &&
//                        (TextUtils.isEmpty(birth) ? "" : birth).equals((TextUtils.isEmpty(birth_new) ? "" : birth_new)) &&
//                        (TextUtils.isEmpty(signature) ? "" : signature).equals((TextUtils.isEmpty(signature_new) ? "" : signature_new))
//                ) {
//            //关闭页面
//            EditUserInfoActivity.this.finish();
//        } else {
//            DialogUtils.showMyDialog(mContext, "提示", "您有数据发生了修改,需要保存吗?", "不修改了,关闭页面", "去保存", new DialogUtils.OnDialogClickListener() {
//                @Override
//                public void onConfirm() {
//                    //关闭页面
//                    EditUserInfoActivity.this.finish();
//                }
//
//                @Override
//                public void onCancel() {
//
//                }
//            });
//        }
    }
}
