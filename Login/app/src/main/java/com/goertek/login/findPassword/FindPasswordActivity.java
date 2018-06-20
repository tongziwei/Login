package com.goertek.login.findPassword;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.goertek.login.R;
import com.goertek.login.login.LoginContract;

public class FindPasswordActivity extends AppCompatActivity implements FindPasswordContract.View,View.OnClickListener{
    private static final String TAG = "FindPasswordActivity";

    private EditText etUsername;
    private Button btnFindPassword;
    private TextView tvShowPassword;

    private FindPasswordContract.Presenter mPresenter;
    private String error = "username is invalid";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);
        initView();
        setPresenter(new FindPasswordPresenter(this));
    }

    public FindPasswordActivity() {
        super();
    }

    @Override
    public void onPasswordFoundResult(String result) {
        if(result != null && result.equals(error)){
            Toast.makeText(this,"用户名无效，请重新输入",Toast.LENGTH_SHORT).show();
        }else{
            tvShowPassword.setText(result);
        }
    }

    @Override
    public void setPresenter(FindPasswordContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void initView() {
        etUsername = (EditText)findViewById(R.id.et_username);
        btnFindPassword = (Button)findViewById(R.id.btn_find_password);
        tvShowPassword = (TextView)findViewById(R.id.tv_show_password);

        btnFindPassword.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_find_password:
                mPresenter.doFindPassword(etUsername.getText().toString());
                break;
            default:
                break;
        }
    }
}
