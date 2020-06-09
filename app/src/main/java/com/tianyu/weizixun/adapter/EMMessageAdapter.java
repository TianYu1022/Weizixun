package com.tianyu.weizixun.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;
import com.tianyu.weizixun.R;

import java.util.ArrayList;

import static com.hyphenate.chat.EMClient.TAG;

public class EMMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<EMMessage> list;
    private Context context;
    private String curName;
    private String name;
    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }


    public EMMessageAdapter(ArrayList<EMMessage> list, Context context, String name, String curName) {
        this.list = list;
        this.context = context;
        this.curName = curName;
        this.name = name;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_right, null);
            return new MyEmMessageViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_left, null);
            return new OtherEmMessageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        EMMessage emMessage = list.get(position);

        if (holder instanceof MyEmMessageViewHolder) {
            EMMessageBody body = emMessage.getBody();
            ((MyEmMessageViewHolder) holder).tv_my_message.setText(body.toString().substring(4));
            ((MyEmMessageViewHolder) holder).tv_my_name.setText(curName);
        } else {
            String from = emMessage.getFrom();
            EMMessageBody body = emMessage.getBody();
            ((OtherEmMessageViewHolder) holder).tv_other_message.setText(body.toString().substring(4));
            ((OtherEmMessageViewHolder) holder).tv_other_name.setText(from);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EMMessageBody body = emMessage.getBody();
                //Log.d(TAG, "onClick: "+body.toStrin g());
                String s = body.toString();
                String localUrl = "";
                String[] split = s.split(",");
                for (int i = 0; i < split.length; i++) {
                    String s1 = split[i];
                    String[] split1 = s1.split(":");
                    for (int j = 0; j < split1.length; j++) {
                        String s2 = split1[j];
                        Log.d(TAG, "onClick: " + s2);
                        if (s2.startsWith("/storage/emulated")) {
                            localUrl = s2;
                            break;
                        }
                    }
                }

                Log.d(TAG, "localUrl: " + localUrl);
                if (onItemClick != null) {
                    onItemClick.onItemClick(localUrl);
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        EMMessage emMessage = list.get(position);
        String from = emMessage.getFrom();

        if (from.equals(curName)) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class OtherEmMessageViewHolder extends RecyclerView.ViewHolder {
        TextView tv_other_name;
        TextView tv_other_message;

        public OtherEmMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_other_name = itemView.findViewById(R.id.tv_other_name);
            tv_other_message = itemView.findViewById(R.id.tv_other_message);
        }
    }

    public class MyEmMessageViewHolder extends RecyclerView.ViewHolder {
        TextView tv_my_name;
        TextView tv_my_message;

        public MyEmMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_my_name = itemView.findViewById(R.id.tv_my_name);
            tv_my_message = itemView.findViewById(R.id.tv_my_message);
        }
    }

    public interface OnItemClick {
        void onItemClick(String localUrl);
    }

}