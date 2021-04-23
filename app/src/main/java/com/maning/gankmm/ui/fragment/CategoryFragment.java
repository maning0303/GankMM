package com.maning.gankmm.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maning.gankmm.R;
import com.maning.gankmm.bean.gank2.Gank2CategoryDataBean;
import com.maning.gankmm.bean.gank2.Gank2CategoryListBean;
import com.maning.gankmm.constant.Constants;
import com.maning.gankmm.http.callback.CommonHttpCallback;
import com.maning.gankmm.http.gank2.GankApi2;
import com.maning.gankmm.ui.base.BaseFragment;
import com.maning.gankmm.utils.MyToast;
import com.maning.mndialoglibrary.MProgressDialog;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 分类Fragment
 */
public class CategoryFragment extends BaseFragment {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.viewpagertab)
    SmartTabLayout viewpagertab;

    private List<Gank2CategoryDataBean> mDatas = new ArrayList<>();

    public final String[] TITLES = {
            Constants.FlagAndroid,
            Constants.FlagIOS,
            Constants.FlagVideo,
            Constants.FlagJS,
            Constants.FlagExpand,
            Constants.FlagRecommend,
            Constants.FlagAPP,
    };
    private String category;

    public static CategoryFragment newInstance(String category) {
        CategoryFragment categoryFragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putString("category", category);
        categoryFragment.setArguments(args);
        return categoryFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getString("category");
            //这个页面单独统计
            className = "CategoryFragment-" + category;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, view);

        initDatas();

        return view;
    }

    private void initDatas() {
        MProgressDialog.showProgress(context);
        GankApi2.getCategoryTitles(category, new CommonHttpCallback<Gank2CategoryListBean>() {
            @Override
            public void onSuccess(Gank2CategoryListBean result) {
                mDatas = result.getData();
                //初始化ViewPager
                initViewPager();
            }

            @Override
            public void onFail(int code, String message) {
                MyToast.showShortToast(message);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                MProgressDialog.dismissProgress();
            }
        });
    }

    private void initViewPager() {
        MyAdapter myAdapter = new MyAdapter(getChildFragmentManager());
        viewPager.setAdapter(myAdapter);
        viewPager.setPageMargin(20);

        viewpagertab.setViewPager(viewPager);
    }


    private class MyAdapter extends FragmentStatePagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PublicFragment.newInstance(category, mDatas.get(position).getType());
        }

        @Override
        public int getCount() {
            return mDatas.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mDatas.get(position).getTitle();
        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }


}
