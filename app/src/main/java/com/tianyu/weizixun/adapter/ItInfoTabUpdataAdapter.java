package com.tianyu.weizixun.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.tianyu.weizixun.R;
import com.tianyu.weizixun.bean.ITTabBean;
import com.tianyu.weizixun.callback.TouchCallBack;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @date：2020/6/21
 * @describe：
 * @author：TianYu
 */

public class ItInfoTabUpdataAdapter extends RecyclerView.Adapter<ItInfoTabUpdataAdapter.ViewHolder> implements TouchCallBack {
    private Context context;
    private ArrayList<ITTabBean> list;

    public ItInfoTabUpdataAdapter(Context context, ArrayList<ITTabBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tab_choose, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ITTabBean itTabBean = list.get(position);
        holder.name.setText(itTabBean.getName());

        holder.sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //如果用户点击了，就修改bean类中的选择状态 并修改 注意：一定要修改
                if (buttonView.isPressed()) {
                    itTabBean.setShow(isChecked);
                }
            }
        });

        /** 一定要在改变之后修改SwitchCompat的选择状态 **/
        holder.sw.setChecked(itTabBean.isShow());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onItemMove(int fromPositon, int toPosition) {
        //交换位置
        Collections.swap(list, fromPositon, toPosition);
        //局部刷新，移动
        notifyItemMoved(fromPositon, toPosition);
    }

    @Override
    public void onItemDelete(int position) {
        //删除数据
        list.remove(position);
        //局部刷新，移除
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.sw)
        SwitchCompat sw;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}