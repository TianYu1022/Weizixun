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
import com.tianyu.weizixun.bean.SpecilBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SpecilRvAdapter extends RecyclerView.Adapter<SpecilRvAdapter.ViewHolder> {
    private Context context;
    private ArrayList<SpecilBean.DataBean> datas;

    public SpecilRvAdapter(Context context, ArrayList<SpecilBean.DataBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_specil_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvSpecilTitle.setText(datas.get(position).getName());
        Glide.with(context).load(datas.get(position).getThumbnail()).into(holder.ivSpecil);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    static

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_specil)
        ImageView ivSpecil;
        @BindView(R.id.tv_specil_title)
        TextView tvSpecilTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
