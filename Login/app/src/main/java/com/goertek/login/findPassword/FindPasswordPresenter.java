package com.goertek.login.findPassword;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.goertek.login.util.WebService;

import static android.content.ContentValues.TAG;

/**
 * Created by clara.tong on 2018/5/29.
 */

public class FindPasswordPresenter implements FindPasswordContract.Presenter{
    private static final String TAG = "FindPasswordPresenter";

    private String result;

    private FindPasswordContract.View mFindPasswordView;
    private Handler mHandler;
    @Override
    public void doFindPassword(final String username) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                result = WebService.executeHttpGet(username); //
                Log.i(TAG, "server return:"+result);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                      mFindPasswordView.onPasswordFoundResult(result);
                    }
                },3000);
            }
        }).start();

    }

    @Override
    public void start() {

    }

    public FindPasswordPresenter(FindPasswordContract.View mFindPasswordView) {
        this.mFindPasswordView = mFindPasswordView;
        mHandler = new Handler(Looper.getMainLooper());
    }
}
