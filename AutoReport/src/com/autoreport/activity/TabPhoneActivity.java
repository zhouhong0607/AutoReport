package com.autoreport.activity;

import com.autoreport.app.R;
import com.autoreport.datastructure.AutoreportApp;
import com.autoreport.datastructure.Info;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
/**
 * 手机信息显示界面
 * @author 周宏
 *
 */
public class TabPhoneActivity extends Activity
{
	private int position;
	private TextView phoneTitle;
	private TextView phoneInfo;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_phone);

		Intent intent = getIntent();
		position = intent.getIntExtra("position", 0);

		phoneTitle = (TextView) findViewById(R.id.phone_title);
		phoneTitle.setTextColor(Color.RED);
		phoneInfo = (TextView) findViewById(R.id.phone_info);
		
		
		
		Info info = AutoreportApp.infolist.get(position);
		phoneTitle.append("手机基本信息:");
		phoneInfo.append("手机品牌:            " + info.getBrand() + "\n");
		phoneInfo.append("手机型号:            " + info.getType() + "\n");
		phoneInfo.append("Android版本:      " + info.getVersion() + "\n");
		phoneInfo.append("本机IP地址:        " + info.getLocalIp() + "\n");
		phoneInfo.append("IMEI:                    " + info.getIMEI() + "\n");
		phoneInfo.append("IMSI:                    " + info.getIMSI() + "\n");
		phoneInfo.append("内存占用率:        " + info.getMemRate() + "\n");
		phoneInfo.append("CPU使用率:         " + info.getCpuRate() + "\n");
		phoneInfo.append("运营商:                " + info.getCorporation() + "\n");
		
		 
		
		
	}
}
