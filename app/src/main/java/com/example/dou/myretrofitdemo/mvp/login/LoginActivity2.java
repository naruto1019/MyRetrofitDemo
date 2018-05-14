package com.example.dou.myretrofitdemo.mvp.login;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.dou.myretrofitdemo.Bean.UserInfoEntity;
import com.example.dou.myretrofitdemo.MyApplication;
import com.example.dou.myretrofitdemo.R;
import com.example.dou.myretrofitdemo.ui.base.BaseActivity;
import com.example.dou.myretrofitdemo.util.SPUtil;
import com.example.dou.myretrofitdemo.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登录
 * 
 * @author dd
 * 
 */
public class LoginActivity2 extends BaseActivity implements loginContact.View{

	@BindView(R.id.et_login_username)
    EditText etUsername;
	@BindView(R.id.et_login_pwd)
    EditText etPwd;
	@BindView(R.id.cb_remeber_pwd)
    CheckBox cbRemeber;

	String username;
	String pwd;

	SPUtil spUtil;

	private Builder builder;
	private View layout;
	private EditText seturladdress;

	loginContact.Presenter mPresenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		ButterKnife.bind(this);

		initView();
		context = this;
		setPresenter(new LoginPresenter(this));
	}

	private void initView() {
		// TODO Auto-generated method stub
		spUtil = SPUtil.getInstance(this);

	}

	@OnClick(R.id.sethelp)
	public void setHelp(View v){
		Builder builder = new Builder(this);
		builder.setMessage("1.如何手机端地址设置?\n\n回复:请在登陆界面,点击地址设置,系统弹出输入框填写内容为:服务器地址+:+端口。\n\n2.完成了地址设置,系统还是登陆不了,并且查看地址设置默认为空了？\n\n回复:请检查地址输入是否正确 “：”要在英文下进行输入。\n\n3.系统登陆输入密码登陆不成功？\n\n回复:请检查用户名密码是否输入正确。\n\n4.系统登陆提示网络异常?\n\n回复:请检查手机网络是否畅通,地址设置是否正确。");
		builder.setTitle("系统帮助");
		builder.setPositiveButton("确认",
				new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog,
					int which) {
				dialog.dismiss();
			}
		});
		builder.show();
	}

	@OnClick(R.id.seturl)
	public void setUrl(View v){

	}
	private void setLayout() {
		builder = new Builder(this);
		builder.setTitle("请输入服务器地址");
		builder.setView(layout);
		builder.setPositiveButton("确认",
				new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog,
					int which) {

			}
		});
		builder.setNegativeButton("取消", null);
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.show();
	}

	@OnClick(R.id.btn_login)
	public void login(View v) {

		username = etUsername.getText().toString();
		pwd = etPwd.getText().toString();
		if (TextUtils.isEmpty(username) || TextUtils.isEmpty(pwd)) {
			ToastUtil.showShort(this, "用户名或密码不能为空！");
			return;
		}
		mPresenter.login(username, pwd);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			MyApplication.wsConnect = false;
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void setPresenter(loginContact.Presenter presenter) {
		mPresenter = presenter;
	}

	@Override
	public void onSuccessLogin(UserInfoEntity userInfo) {

	}

    @Override
	public Context getContext() {
		return this;
	}
}
