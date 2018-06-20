package com.goertek.login.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.goertek.login.HomeActivity;
import com.goertek.login.R;
import com.goertek.login.base.BaseView;
import com.goertek.login.findPassword.FindPasswordActivity;
import com.goertek.login.register.RegisterActivity;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.xml.sax.helpers.LocatorImpl;

import java.util.Map;

public class LoginActivity extends AppCompatActivity implements LoginContract.View,View.OnClickListener,CompoundButton.OnCheckedChangeListener{
    private static final String TAG = "LoginActivity";

    private EditText etUserName;
    private EditText etUserPassword;
    private CheckBox cbRememberPass;
    private CheckBox cbAutoLogin;
    private Button btnLogin;
    private Button btnRegister;
    private Button btnFindPassword;

    private Button btnQQLogin;
    private Button btnWeixinLogin;
    private Button btnSinaWeiboLogin;

    private LoginContract.Presenter mPresenter;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        setPresenter(new LoginPresenter(this));

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRemember = pref.getBoolean("remember_password",false);
        boolean isAutoLogin = pref.getBoolean("auto_login",false);
        if (isRemember){
            String userName = pref.getString("user_name","");
            String password = pref.getString("user_password","");
            etUserName.setText(userName);   //账号和密码设置到文本
            etUserPassword.setText(password);
            cbRememberPass.setChecked(true);
            if(isAutoLogin){
                cbAutoLogin.setChecked(true);
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        }
    }

    @Override
    public void initView() {
        etUserName = (EditText)findViewById(R.id.et_username);
        etUserPassword = (EditText)findViewById(R.id.et_user_password);
        cbRememberPass = (CheckBox)findViewById(R.id.cb_remember_pass);
        cbAutoLogin = (CheckBox)findViewById(R.id.cb_auto_login);
        btnLogin = (Button)findViewById(R.id.btn_login);
        btnFindPassword = (Button)findViewById(R.id.btn_forget_password);
        btnRegister = (Button)findViewById(R.id.btn_register);

        btnQQLogin  = (Button)findViewById(R.id.btn_qqlogin);
        btnWeixinLogin = (Button)findViewById(R.id.btn_weixinlogin);
        btnSinaWeiboLogin = (Button)findViewById(R.id.btn_sinaweibo_login);

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        btnFindPassword.setOnClickListener(this);

        btnQQLogin.setOnClickListener(this);
        btnWeixinLogin.setOnClickListener(this);
        btnSinaWeiboLogin.setOnClickListener(this);

        cbRememberPass.setOnCheckedChangeListener(this);
        cbAutoLogin.setOnCheckedChangeListener(this);

    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onFindPassword() {
        Intent intent = new Intent(LoginActivity.this, FindPasswordActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRegister() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLoginResult(boolean result, int code) {
        if(result){   //账号密码正确
            editor = pref.edit();
            if(cbRememberPass.isChecked()){  //检查记住密码复选框是否被选中
                editor.putBoolean("remember_password",true);
                editor.putString("user_name",etUserName.getText().toString());
                editor.putString("user_password",etUserPassword.getText().toString());
                // pref = mPresenter.doRememberPassword(etUserName.getText().toString(),etUserPassword.getText().toString(),this);
            }else {
                editor.clear();
            }
            editor.apply();
            //进入主界面
            Log.i(TAG, "onLoginResult: success");
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(this,"Login failed ,userName or Password is invalid",Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                mPresenter.doLogin(etUserName.getText().toString(),etUserPassword.getText().toString());
                break;
            case R.id.btn_register:
                mPresenter.doRegister();
                break;
            case R.id.btn_forget_password:
                mPresenter.findPassword();
                break;
            case R.id.btn_qqlogin:
                authorization(SHARE_MEDIA.QQ);
                break;
            case R.id.btn_weixinlogin:
                authorization(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.btn_sinaweibo_login:
                authorization(SHARE_MEDIA.SINA);
                break;
            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        switch (compoundButton.getId()){
            case R.id.cb_remember_pass: 
                editor = pref.edit();
                if(isChecked){
                    Log.i(TAG, "onCheckedChanged:remember password is checked");
                    editor.putBoolean("remember_password",true);
                }else{
                    Log.i(TAG, "onCheckedChanged:remember password is not checked");
                    editor.putBoolean("remember_password",false);
                }
                editor.apply();
                break;
            case R.id.cb_auto_login:
                editor = pref.edit();
                if(isChecked){
                    Log.i(TAG, "onCheckedChanged:auto login is checked");
                    editor.putBoolean("auto_login",true);
                }else{
                    Log.i(TAG, "onCheckedChanged:auto login is not checked");
                    editor.putBoolean("auto_login",false);
                }
                editor.apply();
                break;
            default:
                break;
        }
    }

    //授权
    private void authorization(SHARE_MEDIA share_media) {
        UMShareAPI.get(this).getPlatformInfo(this, share_media, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                Log.d(TAG, "onStart " + "授权开始");
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                Log.d(TAG, "onComplete " + "授权完成");

                //sdk是6.4.4的,但是获取值的时候用的是6.2以前的(access_token)才能获取到值,未知原因
                String uid = map.get("uid");
                String openid = map.get("openid");//微博没有
                String unionid = map.get("unionid");//微博没有
                String access_token = map.get("access_token");
                String refresh_token = map.get("refresh_token");//微信,qq,微博都没有获取到
                String expires_in = map.get("expires_in");
                String name = map.get("name");
                String gender = map.get("gender");
                String iconurl = map.get("iconurl");

                Toast.makeText(getApplicationContext(), "name=" + name + ",gender=" + gender, Toast.LENGTH_SHORT).show();

                //拿到信息去请求登录接口。。。
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                Log.d(TAG, "onError " + "授权失败");
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                Log.d(TAG, "onCancel " + "授权取消");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
