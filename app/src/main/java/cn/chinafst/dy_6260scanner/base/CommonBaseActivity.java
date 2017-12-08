package cn.chinafst.dy_6260scanner.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.chinafst.dy_6260scanner.R;
import cn.chinafst.dy_6260scanner.ShowTime;

public abstract class CommonBaseActivity extends FragmentActivity implements OnClickListener{
	protected Button bt1,bt2,bt3,bt4,bt5;
	protected TextView tittle,time,page;
	protected FrameLayout centerView;
	protected Context mContext;
	protected SharedPreferences sp ;
	protected LinearLayout llfoot;
	View view;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_common_base_activity);
		mContext=this;
		sp = PreferenceManager.getDefaultSharedPreferences(mContext);
		initView();
		getBegin(view);
	}


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		ShowTime.ShowTime(mContext, true, time);
	}

	private void initView() {
		bt1=(Button) findViewById(R.id.bt1);
		bt2=(Button) findViewById(R.id.bt2);
		bt3=(Button) findViewById(R.id.bt3);
		bt4=(Button) findViewById(R.id.bt4);
		bt5=(Button) findViewById(R.id.btn_back);
		llfoot=(LinearLayout) findViewById(R.id.ll_foot);
		tittle=(TextView) findViewById(R.id.tv_tittle);
		page=(TextView) findViewById(R.id.tv_page);
		time=(TextView) findViewById(R.id.tv_time);
		centerView=(FrameLayout) findViewById(R.id.fl_center);
		setButton(llfoot,page,bt1, bt2, bt3, bt4, bt5);
		 view = LayoutInflater.from(mContext).inflate(setLayout(), null);
		centerView.addView(view);
		settitle(tittle);
	}
	

	@Override
	public void onClick(View v) {
		doClick(v);
	}
	
	
	/*
	 * 初始化
	 * */
	protected abstract void getBegin(View view);
	protected abstract int setLayout() ;
	protected abstract void doClick(View v) ;
	protected abstract void settitle(TextView tittle) ;
	protected abstract void setButton(LinearLayout llLayout,TextView page, Button bt1,Button bt2,Button bt3,Button bt4,Button bt5);

}
