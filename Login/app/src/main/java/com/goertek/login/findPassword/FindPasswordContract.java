package com.goertek.login.findPassword;

import com.goertek.login.base.BasePresenter;
import com.goertek.login.base.BaseView;
import com.goertek.login.login.LoginContract;

/**
 * Created by clara.tong on 2018/5/29.
 */

public interface FindPasswordContract {
    interface Presenter extends BasePresenter {
        void doFindPassword(String username);

    }

    interface View extends BaseView<FindPasswordContract.Presenter> {
        void onPasswordFoundResult(String result);

    }
}
