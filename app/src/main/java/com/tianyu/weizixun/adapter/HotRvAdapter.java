package com.tianyu.weizixun.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tianyu.weizixun.R;
import com.tianyu.weizixun.bean.HotNewsBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HotRvAdapter extends RecyclerView.Adapter<HotRvAdapter.ViewHolder> {
    private Context context;
    private ArrayList<HotNewsBean.RecentBean> datas;

    public HotRvAdapter(Context context, ArrayList<HotNewsBean.RecentBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_daily_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HotNewsBean.RecentBean bean = datas.get(position);
        Glide.with(context).load(bean.getThumbnail()).into(holder.ivDailyImg);
        holder.tvDailyTitle.setText(bean.getTitle());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_daily_img)
        ImageView ivDailyImg;
        @BindView(R.id.tv_daily_title)
        TextView tvDailyTitle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
