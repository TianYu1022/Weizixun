package com.tianyu.weizixun.ui.activity;

import android.widget.Button;
import android.widget.EditText;

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

    @OnClick(R.id.btn_reg)
    public void onViewClicked() {
        String name = etName.getText().toString().trim();
        String pwd = etPwd.getText().toString().trim();
        mPresenter.register(name,pwd,"","");
    }


    @Override
    public void onSuccess(RegisterBean registerBean) {
        toast(registerBean.getCode());
    }

    @Override
    public void onFail(String error) {
        toast(error);
    }
}