package com.tianyu.weizixun.ui.fragment;


import android.content.Intent;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tianyu.weizixun.R;
import com.tianyu.weizixun.adapter.UsersAdapter;
import com.tianyu.weizixun.base.BaseFragment;
import com.tianyu.weizixun.common.Constants;
import com.tianyu.weizixun.ui.activity.ChatActivity;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 联系人
 */
public class ContactsFragment extends BaseFragment {

    @BindView(R.id.rv_contacts)
    RecyclerView rvContacts;
    private ArrayList<String> list;
    private UsersAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_contacts;
    }

    @Override
    protected void initView() {
        super.initView();
        //设置布局管理器
        rvContacts.setLayoutManager(new LinearLayoutManager(getActivity()));
        //添加分割线
        rvContacts.addItemDecoration(new DividerItemDecoration(getActivity(), RecyclerView.VERTICAL));
        //获取名字
        list = new ArrayList<>();
        //适配器
        adapter = new UsersAdapter(list, getActivity());
        rvContacts.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        super.initData();
        //获取好友的名字
        ArrayList<String> names = new ArrayList<>();

        names.add("a");
        names.add("b");
        names.add("c");
        if (list.size() <= 0) {
            list.addAll(names);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void initListener() {
        super.initListener();
        adapter.setOnItemClickListener(new UsersAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, String name) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                //把被接受信息的人name传过去
                //发送人的name通过SharedPreferencesUtil获取到了
                intent.putExtra(Constants.NAME, name);
                startActivity(intent);
            }
        });
    }
}