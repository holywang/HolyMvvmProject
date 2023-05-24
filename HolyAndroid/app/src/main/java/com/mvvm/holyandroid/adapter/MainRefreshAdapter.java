package com.mvvm.holyandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.mvvm.holyandroid.R;

import java.util.List;

/**
 * @author rg wang
 * created on  2023/5/16
 */
public class MainRefreshAdapter extends RecyclerView.Adapter<MainRefreshAdapter.ViewHolder> {
    private List<String> mData; // 数据集合
    private Context mContext;
    private OnItemClickListener mOnItemClickListener; // 点击事件监听器
    // 构造方法
    public MainRefreshAdapter(Context context, List<String> data) {
        mContext = context;
        mData = data;
    }
    // 创建ViewHolder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    // 绑定ViewHolder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String item = mData.get(position);
        holder.tvItem.setText(item);
        // 设置点击事件
        holder.itemView.setOnClickListener(v -> {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(position);
            }
        });
    }

    // 获取数据数量
    @Override
    public int getItemCount() {
        return mData.size();
    }
    // ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvItem;
        public ViewHolder(View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tv_item);
        }
    }
    // 设置点击事件监听器
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
    // 点击事件监听器接口
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
