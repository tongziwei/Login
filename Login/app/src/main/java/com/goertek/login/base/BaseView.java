package com.goertek.login.base;

/**
 * Created by david.liang on 2018/3/28.
 */

public interface BaseView<T> {
    void setPresenter(T presenter);
    void initView();
}
