package com.maning.gankmm.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.maning.gankmm.R;
import com.maning.gankmm.listeners.OnItemClickListener;

import java.util.ArrayList;


/**
 * 列表弹框Dialog
 */
public class ListFragmentDialog extends DialogFragment implements View.OnClickListener {

    private static Context mContext;
    private View view;
    private Button btnCancle;
    private RecyclerView recyclerView;
    private RelativeLayout rl_root;

    private FragmentManager manager;
    private ArrayList<String> mDatas = new ArrayList<>();
    private ListDialogAdapter listDialogAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //隐藏title
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().getWindow().setWindowAnimations(R.style.animate_list_dialog);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        getDialog().getWindow().setBackgroundDrawable(getResources().getDrawable(R.color.transparent));
        view = inflater.inflate(R.layout.dialog_list, null);
        btnCancle = (Button) view.findViewById(R.id.btn_cancle);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewList);
        rl_root = (RelativeLayout) view.findViewById(R.id.rl_root);

        btnCancle.setOnClickListener(this);
        rl_root.setOnClickListener(this);

        initRecyclerView();

        initAdapter();

        return view;
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public void showDailog(FragmentManager manager, ArrayList<String> mDatas, OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        this.mDatas = mDatas;
        this.manager = manager;
        //显示
        show(this.manager, "");
    }

    private void initAdapter() {
        if (listDialogAdapter == null) {
            listDialogAdapter = new ListDialogAdapter(mContext, mDatas);
            recyclerView.setAdapter(listDialogAdapter);
            listDialogAdapter.setOnItemClickLitener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(view, position);
                    }
                    dismiss();
                }
            });
        } else {
            listDialogAdapter.updateDatas(mDatas);
        }
    }


    public static ListFragmentDialog newInstance(Context context) {
        mContext = context;
        ListFragmentDialog fragment = new ListFragmentDialog();
        return fragment;
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_cancle) {
            dismiss();
        } else if (v.getId() == R.id.rl_root) {
            dismiss();
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.dimAmount = 0.5f;
        window.setAttributes(windowParams);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }


    class ListDialogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private Context context;
        private ArrayList<String> mDatas;
        private LayoutInflater layoutInflater;
        private OnItemClickListener mOnItemClickLitener;

        public void setOnItemClickLitener(OnItemClickListener mOnItemClickLitener) {
            this.mOnItemClickLitener = mOnItemClickLitener;
        }

        public ListDialogAdapter(Context context, ArrayList<String> mDatas) {
            this.context = context;
            this.mDatas = mDatas;
            layoutInflater = LayoutInflater.from(this.context);
        }

        public void updateDatas(ArrayList<String> mDatas) {
            this.mDatas = mDatas;
            notifyDataSetChanged();
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate = layoutInflater.inflate(R.layout.layout_list_dialog_item, parent, false);
            return new MyViewHolder(inflate);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            if (holder instanceof MyViewHolder) {
                final MyViewHolder myViewHolder = (MyViewHolder) holder;
                myViewHolder.btn_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickLitener != null) {
                            mOnItemClickLitener.onItemClick(v, position);
                        }
                    }
                });
                myViewHolder.btn_item.setText(mDatas.get(position));
            }
        }

        @Override
        public int getItemCount() {
            if (mDatas != null && mDatas.size() > 0) {
                return mDatas.size();
            } else {
                return 0;
            }
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            public Button btn_item;

            public MyViewHolder(View itemView) {
                super(itemView);
                btn_item = (Button) itemView.findViewById(R.id.btn_item);

            }
        }
    }

}
