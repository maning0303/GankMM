package com.maning.gankmm.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.maning.gankmm.R;
import com.maning.gankmm.utils.ClipUtils;
import com.maning.gankmm.utils.MySnackbar;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 扫码历史
 */
public class RecycleScanHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<String> mDatas;
    private LayoutInflater layoutInflater;

    public RecycleScanHistoryAdapter(Context context, ArrayList<String> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        layoutInflater = LayoutInflater.from(this.context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = layoutInflater.inflate(R.layout.item_scan_history, parent, false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof MyViewHolder) {
            final MyViewHolder myViewHolder = (MyViewHolder) viewHolder;

            final String result = mDatas.get(position);
            myViewHolder.tv_title.setText(result);
            myViewHolder.root_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //复制到剪切板
                    ClipUtils.copy(context, result);
                    MySnackbar.makeSnackBarGreen(myViewHolder.root_view, "结果已经复制到剪切板");
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_title)
        TextView tv_title;
        @Bind(R.id.root_view)
        RelativeLayout root_view;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
