package com.maning.gankmm.ui.presenter.impl;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.maning.gankmm.bean.web.CategoryContentBean;
import com.maning.gankmm.bean.web.CategoryTitleBean;
import com.maning.gankmm.ui.activity.CodesActivity;
import com.maning.gankmm.ui.iView.ICodesView;
import com.maning.gankmm.ui.presenter.ICodesPresenter;
import com.socks.library.KLog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by maning on 16/6/23.
 */
public class CodesPresenterImpl extends BasePresenterImpl<ICodesView> implements ICodesPresenter {


    private static final String baseUrl_Jcode = "http://www.jcodecraeer.com";
    private static final String baseUrl_CocoaChina = "http://code.cocoachina.com";

    private ArrayList<CategoryTitleBean> titles = new ArrayList<>();
    private ArrayList<CategoryContentBean> codes = new ArrayList<>();

    private Context context;
    private String nextPageUrl;
    private String type;


    public CodesPresenterImpl(Context context, ICodesView iCodesView, String type) {
        this.context = context;
        this.type = type;
        attachView(iCodesView);
    }

    private void getDatas(final String loadUrl) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect(loadUrl).get();

                    //判断是CocoaChina的诗句还是泡在网上的日志的数据
                    if (type.equals(CodesActivity.IntentTypeName_Jcode)) {
                        if (titles.size() <= 0) {
                            //分类
                            Elements categorys = doc.select("li.slidebar-category-one");
                            CategoryTitleBean categoryTitleBean;
                            for (Element element : categorys) {
                                String url = element.select("li.slidebar-category-one").select("a[href]").attr("href");
                                String title = element.select("li.slidebar-category-one").select("a[href]").text();
                                if (!TextUtils.isEmpty(url)) {
                                    categoryTitleBean = new CategoryTitleBean();
                                    categoryTitleBean.setUrl(baseUrl_Jcode + url);
                                    categoryTitleBean.setTitle(title);
                                    titles.add(categoryTitleBean);
                                    KLog.i("----:" + categoryTitleBean.toString());
                                }
                            }
                        }

                        //获取页码
                        Elements elementsPage = doc.select("div.paginate-container").select("a[href]");
                        KLog.i("elementsPages----" + elementsPage.size());
                        nextPageUrl = "";
                        for (Element element : elementsPage) {
                            String text = element.text();
                            if ("下一页".equals(text.trim())) {
                                String pageUrl = element.select("a[href]").attr("href");
                                if (!TextUtils.isEmpty(pageUrl)) {
                                    nextPageUrl = baseUrl_Jcode + pageUrl;
                                    KLog.i("nextPageUrl----" + nextPageUrl);
                                }
                            }
                        }

                        Elements elements = doc.select("li.codeli");
                        CategoryContentBean categoryContentBean;
                        for (int i = 0; i < elements.size(); i++) {
                            Element element = elements.get(i);
                            String url = element.select("div.codeli-photo").select("a[href]").attr("href");
                            String imageUrl = element.select("div.codeli-photo").select("img[src]").attr("src");
                            String title = element.select("h2.codeli-name").select("a[href]").text();
                            String description = element.select("p.codeli-description").text();
                            String otherInfo = element.select("div.otherinfo").select("span").text();

                            if (!TextUtils.isEmpty(url)) {
                                categoryContentBean = new CategoryContentBean();
                                categoryContentBean.setUrl(baseUrl_Jcode + url);
                                categoryContentBean.setTitle(title);
                                categoryContentBean.setImageUrl(imageUrl);
                                categoryContentBean.setDescription(description);
                                categoryContentBean.setOtherInfo(otherInfo);
                                codes.add(categoryContentBean);
                                KLog.i("categoryContentBean----" + categoryContentBean.toString());
                            }
                        }

                    }else if(type.equals(CodesActivity.IntentTypeName_CocoaChina)){

                        if (titles.size() <= 0) {
                            //分类
                            Elements categorys = doc.select("div.categorylist").select("a[href]");
                            CategoryTitleBean categoryTitleBean;
                            for (Element element : categorys) {
                                String url = element.select("a[href]").attr("href");
                                String name_zh = element.select("span.zh").text();
                                String name_en = element.select("span.en").text();
                                String count = element.select("span.cnt").text();
                                if (!TextUtils.isEmpty(url)) {
                                    categoryTitleBean = new CategoryTitleBean();
                                    categoryTitleBean.setUrl(baseUrl_CocoaChina + url);
                                    categoryTitleBean.setTitle(name_zh+name_en+" "+count);
                                    titles.add(categoryTitleBean);
                                    KLog.i("----:" + categoryTitleBean.toString());
                                }
                            }
                        }

                        //获取页码
                        Elements elementsPage = doc.select("div.pager").select("a[data-ci-pagination-page]");
                        KLog.i("elementsPages----" + elementsPage.size());
                        nextPageUrl = "";
                        for (Element element : elementsPage) {
                            String text = element.text();
                            if ("下一页".equals(text.trim())) {
                                String pageUrl = element.select("a[href]").attr("href");
                                if (!TextUtils.isEmpty(pageUrl)) {
                                    nextPageUrl = baseUrl_CocoaChina + pageUrl;
                                    KLog.i("nextPageUrl----" + nextPageUrl);
                                }
                            }
                        }

                        //数据
                        Elements elementDatas = doc.select("div.waterfall-box");
                        CategoryContentBean categoryContentBean;
                        for (int i = 0; i < elementDatas.size(); i++) {
                            Element element = elementDatas.get(i);

                            String url = element.select("div.w-name").select("a[href]").attr("href");
                            String title = element.select("div.w-name").select("a[href]").text();
                            String imageUrl = element.select("img[src]").attr("src");
                            String description = element.select("div.w-desc").text();
                            String otherInfo = element.select("div.w-puptime").text();
                            if (!TextUtils.isEmpty(url)) {
                                categoryContentBean = new CategoryContentBean();
                                categoryContentBean.setUrl(baseUrl_CocoaChina + url);
                                categoryContentBean.setTitle(title);
                                categoryContentBean.setImageUrl(baseUrl_CocoaChina + imageUrl);
                                categoryContentBean.setDescription(description);
                                categoryContentBean.setOtherInfo(otherInfo);
                                codes.add(categoryContentBean);
                                KLog.i("categoryContentBean----" + categoryContentBean.toString());
                            }
                        }
                    }

                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mView == null) {
                                return;
                            }
                            mView.setCodesTitleList(titles);
                            mView.setCodesContentList(codes);
                            mView.overRefresh();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                    if (mView == null) {
                        return;
                    }
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mView.showToast("解析数据异常");
                            mView.overRefresh();
                        }
                    });
                }
            }
        }).start();
    }

    @Override
    public void getNewDatas(String url) {
        codes.clear();
        getDatas(url);
        if (mView != null) {
            mView.setRefreshEnabled(true);
            mView.setLoadMoreEnabled(true);
        }
    }

    @Override
    public void getMoreDatas() {
        if (!TextUtils.isEmpty(nextPageUrl)) {
            getDatas(nextPageUrl);
        } else {
            if (mView != null) {
                mView.showToast("没有更多数据了");
                mView.overRefresh();
                mView.setLoadMoreEnabled(false);
            }
        }
    }

}
