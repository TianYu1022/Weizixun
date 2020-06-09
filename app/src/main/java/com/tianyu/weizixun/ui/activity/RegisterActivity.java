package com.tianyu.weizixun.ui.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.tianyu.weizixun.R;
import com.tianyu.weizixun.base.BaseMvpActivity;
import com.tianyu.weizixun.bean.RegisterBean;
import com.tianyu.weizixun.presenter.RegisterPresenter;
import com.tianyu.weizixun.view.RegiseterView;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseMvpActivity<RegisterPresenter, RegiseterView> implements RegiseterView {

    @BindView(R.id.btn_reg)
    Button btnReg;
    @BindView(R.id.et_pwd_reg)
    EditText etPwd;
    @BindView(R.id.et_name_reg)
    EditText etName;
    @BindView(R.id.btn_im_reg)
    Button btnImReg;
    @BindView(R.id.et_psw_reg)
    EditText etPswReg;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected RegiseterView initMvpView() {
        return this;
    }

    @Override
    protected RegisterPresenter initMvpPresenter() {
        return new RegisterPresenter();
    }

    @Override
    public void onSuccess(RegisterBean registerBean) {
        if (registerBean.getErrorCode() == 0) {
            toast("注册成功");
            finish();
        } else {
            toast("账号已注册，重新输入账号");
        }
    }

    @Override
    public void onFail(String error) {
        toast(error);
    }

    @OnClick({R.id.btn_reg, R.id.btn_im_reg, R.id.et_psw_reg})
    public void onViewClicked(View view) {
        String name = etName.getText().toString().trim();
        String pwd = etPwd.getText().toString().trim();
        String psw = etPswReg.getText().toString().trim();
        switch (view.getId()) {
            case R.id.btn_reg:
                mPresenter.register(name, pwd, psw);
                break;
            case R.id.btn_im_reg:
                registerIm(name, psw);
                break;
        }
    }

    /**
     * 环信三方注册
     *
     * @param name
     * @param psw
     */
    private void registerIm(String name, String psw) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //注册失败会抛出HyphenateException
                    EMClient.getInstance().createAccount(name, psw);//同步方法
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                    finish();
                } catch (HyphenateException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RegisterActivity.this, "注册失败：" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    e.printStackTrace();
                }
            }
        }).start();
    }
}