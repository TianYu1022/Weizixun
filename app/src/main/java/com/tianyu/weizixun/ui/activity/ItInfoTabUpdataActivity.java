package com.tianyu.weizixun.ui.activity;

import android.content.Intent;
import android.view.KeyEvent;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tianyu.weizixun.R;
import com.tianyu.weizixun.adapter.ItInfoTabUpdataAdapter;
import com.tianyu.weizixun.base.BaseActivity;
import com.tianyu.weizixun.bean.ITTabBean;
import com.tianyu.weizixun.callback.SimpleItemTouchHelperCallBack;
import com.tianyu.weizixun.common.Constants;

import java.util.ArrayList;

import butterknife.BindView;


public class ItInfoTabUpdataActivity extends BaseActivity {

    @BindView(R.id.rv_info_tab_updata)
    RecyclerView rvInfoTabUpdata;
    private ArrayList<ITTabBean> list;
    private ItInfoTabUpdataAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_it_info_tab_updata;
    }

    @Override
    protected void initView() {
        super.initView();
        rvInfoTabUpdata.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new ItInfoTabUpdataAdapter(this, list);
        rvInfoTabUpdata.setAdapter(adapter);

        //为recycleview添加SimpleItemTouchHelperCallBack
        //里式替换原则
        SimpleItemTouchHelperCallBack simpleItemTouchHelperCallBack = new SimpleItemTouchHelperCallBack(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchHelperCallBack);
        itemTouchHelper.attachToRecyclerView(rvInfoTabUpdata);

    }

    @Override
    protected void initData() {
        super.initData();
        ArrayList<ITTabBean> datas = (ArrayList<ITTabBean>) getIntent().getSerializableExtra(Constants.LIST);
        list.addAll(datas);
        adapter.notifyDataSetChanged();
    }

    /**
     * 系统按键点击监听事件
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //点击返回键将集合返回去
        //因为有的SwitchCompat选中之后bean类是发生改变的，回传之后改变页面
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.putExtra(Constants.LIST, list);
            setResult(200, intent);
        }
        return super.onKeyDown(keyCode, event);
    }
}