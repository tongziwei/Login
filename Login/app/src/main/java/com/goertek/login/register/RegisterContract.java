package com.goertek.login.register;

import com.goertek.login.base.BasePresenter;
import com.goertek.login.base.BaseView;
import com.goertek.login.login.LoginContract;

/**
 * Created by clara.tong on 2018/5/23.
 */

public interface RegisterContract {
    interface Presenter extends BasePresenter {
        void doRegister(String name, String password);

    }

    interface View extends BaseView<RegisterContract.Presenter> {
        void onRegisterResult(boolean result);


    }
}
