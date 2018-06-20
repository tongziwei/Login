package com.goertek.login.register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.goertek.login.HomeActivity;
import com.goertek.login.R;
import com.goertek.login.login.LoginActivity;
import com.goertek.login.login.LoginContract;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.View,View.OnClickListener{
    private static final String TAG = "RegisterActivity";

    private EditText etUsername;
    private EditText etEnterPassword;
    private EditText etEnterPasswordAgain;
    private Button btnRegister;
    private ImageView ivBack;

    private RegisterContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        setPresenter(new RegisterPresenter(this));
    }

    @Override
    public void initView() {
        etUsername = (EditText)findViewById(R.id.et_username);
        etEnterPassword = (EditText)findViewById(R.id.et_enter_password);
        etEnterPasswordAgain = (EditText)findViewById(R.id.et_enter_password_again);
        ivBack = (ImageView)findViewById(R.id.iv_guild_back);
        btnRegister  = (Button) findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);
        ivBack.setOnClickListener(this);
    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {
        mPresenter =  presenter;
    }

    @Override
    public void onRegisterResult(boolean result) {
        if (result){
            Toast.makeText(this,"register success",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(this,"register failed,please enter another username",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_register:
                String userName = etUsername.getText().toString();
                String firstPassword = etEnterPassword.getText().toString();
                String confirmPassword = etEnterPasswordAgain.getText().toString();
                if (firstPassword.equals(confirmPassword)){
                    mPresenter.doRegister(userName,confirmPassword);
                }else{
                    etEnterPassword.setText(null);
                    etEnterPasswordAgain.setText(null);
                    Toast.makeText(this,R.string.password_inconsistent,Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_guild_back:
                RegisterActivity.this.finish();
                break;
            default:
                break;

        }

    }
}
