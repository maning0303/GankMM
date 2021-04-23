package com.maning.gankmm.ui.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maning.gankmm.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by maning on 16/5/17.
 */
public class RecycleLotteryNumberAdapter extends RecyclerView.Adapter<RecycleLotteryNumberAdapter.MyViewHolder> {

    private Context context;
    private List<String> mDatas;
    private LayoutInflater layoutInflater;

    public RecycleLotteryNumberAdapter(Context context, List<String> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        layoutInflater = LayoutInflater.from(this.context);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View inflate = layoutInflater.inflate(R.layout.item_lottery_number_item, parent, false);

        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position) {

        String name = mDatas.get(position);
        viewHolder.tv_number.setText(name);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_number)
        TextView tv_number;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
