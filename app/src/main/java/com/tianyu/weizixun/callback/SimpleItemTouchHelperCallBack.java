package com.tianyu.weizixun.callback;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @date：2020/6/21
 * @describe：(如果是瀑布流或者网格布局还需要做判断处理)
 * @author：TianYu
 */

public class SimpleItemTouchHelperCallBack extends ItemTouchHelper.Callback {
    private TouchCallBack callBack;

    public SimpleItemTouchHelperCallBack(TouchCallBack callBack) {
        this.callBack = callBack;
    }

    /**
     * 返回可以滑动的方向
     *
     * @param recyclerView
     * @param viewHolder
     * @return
     */
    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        //允许上下拖拽
        int drag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        //运行向左滑动
        int swip = ItemTouchHelper.LEFT;
        return makeMovementFlags(drag, swip);
    }

    /**
     * 移动
     * 上下拖动item时回调，可以调用adapter中的notifyItemMoved方法来交换两个viewholder的位置，
     * 最后返回true，表示被拖动的viewholder已经移动到了目的位置：
     *
     * @param recyclerView
     * @param viewHolder
     * @param target
     * @return
     */
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        callBack.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    /**
     * 删除
     * 当用户左右滑动item时达到删除的条件就会调用，一般为一半，条目继续滑动删除，否则弹回
     *
     * @param viewHolder
     * @param direction
     */
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        callBack.onItemDelete(viewHolder.getAdapterPosition());
    }

    /**
     * 支持长按拖动，默认是true，可以不写
     *
     * @return
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return super.isLongPressDragEnabled();
    }

    /**
     * 支持滑动删除，可以调到onSwiped，默认为true，可以不写
     *
     * @return
     */
    @Override
    public boolean isItemViewSwipeEnabled() {
        return super.isItemViewSwipeEnabled();
    }
}