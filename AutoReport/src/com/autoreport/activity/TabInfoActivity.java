package com.autoreport.activity;
import com.autoreport.app.R;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TabHost;
/**
 * 选项卡界面
 * @author 周宏
 *
 */
public class TabInfoActivity extends ActivityGroup
{
	 private int position;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tab);
		Intent intent=getIntent();
		position=intent.getIntExtra("position", 0);
		// 获取TabHost
		TabHost th = (TabHost)findViewById(R.id.tabhost);
		th.setup();
		
	      th.setup(this.getLocalActivityManager());   
	      
		// 通过TabHost获得存放Tab标签页内容的FrameLayout
//		LayoutInflater.from(this).inflate(R.layout.tab_phone, th.getTabContentView());
//		LayoutInflater.from(this).inflate(R.layout.tab_app, th.getTabContentView());
//		LayoutInflater.from(this).inflate(R.layout.tab_wireless, th.getTabContentView());
//		LayoutInflater.from(this).inflate(R.layout.tab_flow, th.getTabContentView());
		
	     // 设置Tab的标签内容和显示内容
		Intent intent1=new Intent(getApplicationContext(),TabPhoneActivity.class);
		Intent intent2=new Intent(getApplicationContext(),TabAppActivity.class);
		Intent intent3 =new Intent(getApplicationContext(),TabWirelessActivity.class);
		Intent intent4=new Intent(getApplicationContext(),TabFlowActivity.class);
		
		intent1.putExtra("position", position);
		intent2.putExtra("position", position);
		intent3.putExtra("position", position);
		intent4.putExtra("position", position);
		
		
		th.addTab(th.newTabSpec("tab1").setIndicator("手机").setContent(intent1));
		th.addTab(th.newTabSpec("tab2").setIndicator("进程").setContent(intent2));
		th.addTab(th.newTabSpec("tab3").setIndicator("无线").setContent(intent3));
		th.addTab(th.newTabSpec("tab4").setIndicator("流量").setContent(intent4));
		
		
	}
}
