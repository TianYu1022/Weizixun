package com.tianyu.weizixun.callback;

/**
 * @date：2020/6/21
 * @describe：
 * @author：TianYu
 */
public interface TouchCallBack {
    //数据交换
    void onItemMove(int fromPositon, int toPosition);

    //数据删除
    void onItemDelete(int position);
}