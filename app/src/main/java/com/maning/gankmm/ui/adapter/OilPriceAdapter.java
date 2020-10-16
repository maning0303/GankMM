package com.maning.gankmm.ui.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kelin.scrollablepanel.library.PanelAdapter;
import com.maning.gankmm.R;
import com.maning.gankmm.bean.mob.MobOilPriceEntity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by maning on 2017/5/11.
 */

public class OilPriceAdapter extends PanelAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private HashMap<String, MobOilPriceEntity.OilBean> modelAttributeAndValueMap;
    private ArrayList<String> proviceNames;


    public OilPriceAdapter(Context context, HashMap<String, MobOilPriceEntity.OilBean> modelAttributeAndValueMap, ArrayList<String> proviceNames) {
        this.context = context;
        this.modelAttributeAndValueMap = modelAttributeAndValueMap;
        this.proviceNames = proviceNames;
        layoutInflater = LayoutInflater.from(this.context);
    }


    @Override
    public int getRowCount() {
        return modelAttributeAndValueMap.size() + 1;
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public int getItemViewType(int row, int column) {
        if (column == 0 && row == 0) {
            //左上角标题
            return 0;
        }
        if (column == 0) {
            //左边省市标题
            return 1;
        }
        if (row == 0) {
            //顶部类型标题
            return 2;
        }
        //油价
        return 3;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int row, int column) {
        if (holder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;

            myViewHolder.rl_root.setBackgroundColor(context.getResources().getColor(R.color.white));

            int viewType = getItemViewType(row, column);
            if (viewType == 0) {  //左上角标题
                myViewHolder.tv_content.setText("省份/类型");
                myViewHolder.rl_root.setBackgroundColor(context.getResources().getColor(R.color.itemGrayBg));
            } else if (viewType == 1) {  //左边省市标题
                String name = proviceNames.get(row - 1);
                myViewHolder.tv_content.setText(name);
                myViewHolder.rl_root.setBackgroundColor(context.getResources().getColor(R.color.itemGrayBg));
            } else if (viewType == 2) {  //左边省市标题
                String name = "类型";
                if (column == 1) {
                    name = "0号柴油";
                } else if (column == 2) {
                    name = "90号汽油";
                } else if (column == 3) {
                    name = "93号汽油";
                } else if (column == 4) {
                    name = "97号汽油";
                }
                myViewHolder.tv_content.setText(name);
                myViewHolder.rl_root.setBackgroundColor(context.getResources().getColor(R.color.itemGrayBg));
            } else if (viewType == 3) {  //油价
                String name = proviceNames.get(row - 1);
                MobOilPriceEntity.OilBean oilBean = modelAttributeAndValueMap.get(name);
                String content = "";
                if (column == 1) {
                    content = oilBean.getDieselOil0();
                } else if (column == 2) {
                    content = oilBean.getGasoline90();
                } else if (column == 3) {
                    content = oilBean.getGasoline93();
                } else if (column == 4) {
                    content = oilBean.getGasoline97();
                }
                myViewHolder.tv_content.setText(content);
            }

        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {  //左上角标题

        } else if (viewType == 1) {  //左边省市标题

        } else if (viewType == 2) {  //左边省市标题

        } else if (viewType == 3) {  //左边省市标题

        }
        View inflate = layoutInflater.inflate(R.layout.item_oil_price, parent, false);
        return new MyViewHolder(inflate);
    }

    private static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_content;
        public RelativeLayout rl_root;

        public MyViewHolder(View view) {
            super(view);
            this.tv_content = (TextView) view.findViewById(R.id.tv_content);
            this.rl_root = (RelativeLayout) view.findViewById(R.id.rl_root);
        }
    }

}
