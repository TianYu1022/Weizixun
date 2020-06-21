package com.tianyu.weizixun.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tianyu.weizixun.R;
import com.tianyu.weizixun.bean.NaviBean;
import com.tianyu.weizixun.ui.widget.FlowLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @date：2020/6/21
 * @describe：粘性头布局，流式布局
 * @author：TianYu
 */

public class NaviAdapter extends RecyclerView.Adapter<NaviAdapter.ViewHolder> {
    private Context context;
    private ArrayList<NaviBean.DataBean> list;

    public NaviAdapter(Context context, ArrayList<NaviBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //使用自定义view流式布局：FlowLayout
        View view = LayoutInflater.from(context).inflate(R.layout.item_navi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NaviBean.DataBean dataBean = list.get(position);
        for (int i = 0; i < dataBean.getArticles().size(); i++) {
            //获取视图，视图可以自定义，添加自己想要的效果
            TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.item_navi_lable, null);
            //获取数据并赋值
            tv.setText(dataBean.getArticles().get(i).getTitle());
            //点击事件
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, tv.getText(), Toast.LENGTH_SHORT).show();
                }
            });
            //把自己定义的视图添加到FlowLayout中
            holder.fl.addView(tv);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.fl)
        FlowLayout fl;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
