package com.example.dou.myretrofitdemo.ui.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dou.myretrofitdemo.AppManager;
import com.example.dou.myretrofitdemo.R;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public class BaseActivity extends RxAppCompatActivity {

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		//添加Activity到堆栈
		AppManager.getAppManager().addActivity(this);
	}

	//初始化头部
	protected void initHeadView(){};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		//结束Activity&从堆栈中移除
		AppManager.getAppManager().finishActivity(this);
	}


	/**
	 * 设置标题和按钮
	 */
	protected Context context;

	public void setTitle(final Context context, String name) {
		// TODO Auto-generated method stub
		ImageView iv_head_back = (ImageView) findViewById(R.id.iv_head_back);
		iv_head_back.setVisibility(View.VISIBLE);
		iv_head_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				activityFinish();
			}
		});
		TextView title = (TextView) findViewById(R.id.tv_head_title);
		title.setText(name);
		this.context = context;
	}

	public void activityFinish(){
		((Activity)context).finish();
	}
}
