package com.goertek.login.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;

import com.goertek.login.model.IUser;
import com.goertek.login.model.User;
import com.goertek.login.util.WebService;

import static android.content.ContentValues.TAG;


/**
 * Created by clara.tong on 2018/5/22.
 */

public class LoginPresenter implements LoginContract.Presenter{
    private LoginContract.View mLoginView;
    private Handler mHandler;
    private IUser user;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private String info;

    public LoginPresenter(LoginContract.View mLoginView) {
        this.mLoginView = mLoginView;
        mHandler = new Handler(Looper.getMainLooper());
    }


    @Override
    public void doLogin(final String name, final String password) {
              /*  final int code = user.checkUserValidity(name,password);*/
        new Thread(new Runnable() {
            @Override
            public void run() {
                info = WebService.executeHttpGet(name,password); //
                Log.i(TAG, "server return:"+info);
                String code = info; //result =1,注册成功
                boolean isLoginSuccess = false;
                if(code != null && code.equals("1")){
                    isLoginSuccess = true;
                }else {
                    isLoginSuccess= false;
                }
                final int flag= 0; //标志位，后续可添加其他情况
                final boolean result = isLoginSuccess;
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mLoginView.onLoginResult(result,flag);
                    }
                },3000);
            }
        }).start();
//        info = WebService.executeHttpPost(name,password);

    }


    @Override
    public void start() {

    }



    @Override
    public void findPassword() {
        mLoginView.onFindPassword();
    }

    @Override
    public void doRegister() {
        mLoginView.onRegister();

    }
}
