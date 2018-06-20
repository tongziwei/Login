package com.goertek.login.register;

import android.os.Handler;
import android.os.Looper;

import com.goertek.login.util.WebService;


/**
 * Created by clara.tong on 2018/5/23.
 */

public class RegisterPresenter implements RegisterContract.Presenter {
    private RegisterContract.View mRegisterView;
    private Handler mHandler;
    private String info;

    public RegisterPresenter(RegisterContract.View mRegisterView) {
        this.mRegisterView = mRegisterView;
        mHandler = new Handler(Looper.getMainLooper());

    }

    @Override
    public void doRegister(final String name, final String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                info = WebService.executeHttpPost(name,password); //
                String code = info; //result =1,注册成功
                boolean isRegisterSuccess = false;
                if(code != null &&code.equals("1")){
                    isRegisterSuccess = true;
                }else {
                    isRegisterSuccess = false;
                }
                final boolean result = isRegisterSuccess;
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRegisterView.onRegisterResult(result);
                    }
                },3000);
            }
        }).start();
//        info = WebService.executeHttpPost(name,password);

    }

    @Override
    public void start() {

    }
}
