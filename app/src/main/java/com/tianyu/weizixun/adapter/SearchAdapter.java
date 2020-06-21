package com.tianyu.weizixun.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tianyu.weizixun.R;
import com.tianyu.weizixun.bean.SearchBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @date：2020/6/21
 * @describe：
 * @author：TianYu
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private Context context;
    private ArrayList<SearchBean.DataBean.DatasBean> list;
    private final LayoutInflater inflater;

    public SearchAdapter(Context context, ArrayList<SearchBean.DataBean.DatasBean> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_wx_article_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SearchBean.DataBean.DatasBean datasBean = list.get(position);
        holder.tvChapter.setText(datasBean.getSuperChapterName() + "/" + datasBean.getChapterName());
        holder.tvName.setText(datasBean.getAuthor());
        holder.tvTime.setText(datasBean.getNiceDate());
        holder.tvTitle.setText(datasBean.getTitle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name_it)
        TextView tvName;
        @BindView(R.id.tv_title_it)
        TextView tvTitle;
        @BindView(R.id.tv_chapter_it)
        TextView tvChapter;
        @BindView(R.id.tv_time_it)
        TextView tvTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}