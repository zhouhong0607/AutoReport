package com.autoreport.activity;




import com.autoreport.app.R;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TabHost;

public class TabInfoActivity extends ActivityGroup
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab);

	

		// 获取TabHost
		TabHost th = (TabHost)findViewById(R.id.tabhost);
		
		th.setup();
		
	      th.setup(this.getLocalActivityManager());   
		// 通过TabHost获得存放Tab标签页内容的FrameLayout
		LayoutInflater.from(this).inflate(R.layout.tab_phone, th.getTabContentView());
		LayoutInflater.from(this).inflate(R.layout.tab_app, th.getTabContentView());
		LayoutInflater.from(this).inflate(R.layout.tab_wireless, th.getTabContentView());
		LayoutInflater.from(this).inflate(R.layout.tab_flow, th.getTabContentView());
		// 设置Tab的标签内容和显示内容
		th.addTab(th.newTabSpec("tab1").setIndicator("PHONE").setContent(new Intent(getApplicationContext(),TabPhoneActivity.class)));
		th.addTab(th.newTabSpec("tab2").setIndicator("APP").setContent(new Intent(getApplicationContext(),TabAppActivity.class)));
		th.addTab(th.newTabSpec("tab3").setIndicator("Wireless").setContent(new Intent(getApplicationContext(),TabWirelessActivity.class)));
		th.addTab(th.newTabSpec("tab4").setIndicator("Flow").setContent(new Intent(getApplicationContext(),TabFlowActivity.class)));
		
		
	}
}
