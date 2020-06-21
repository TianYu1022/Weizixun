package com.tianyu.weizixun.bean;

/**
 * @date：2020/6/21
 * @describe：IT咨询tab
 * @author：TianYu
 */

import java.io.Serializable;

public class ITTabBean implements Serializable {
    private String name;
    private int id;
    private boolean isShow;

    public ITTabBean(String name, int id, boolean isShow) {
        this.name = name;
        this.id = id;
        this.isShow = isShow;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }
}