package com.goertek.login.login;


import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.VisualVoicemailSmsFilterSettings;

import com.goertek.login.base.BasePresenter;
import com.goertek.login.base.BaseView;

/**
 * Created by clara.tong on 2018/5/22.
 */

public interface LoginContract {
    interface Presenter extends BasePresenter {
        void doLogin(String name, String password);
        void doRegister();
        void findPassword();
    }

    interface View extends BaseView<Presenter> {
        void onLoginResult(boolean result, int code);
        void onRegister();
        void onFindPassword();

    }
}
