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
 * APP信息展示界面
 * @author 周宏
 *
 */
public class TabAppActivity extends Activity
{
	private int position;

	private TextView appTitle;
	private TextView appInfo;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_app);

		Intent intent = getIntent();
		position = intent.getIntExtra("position", 0);

		appTitle = (TextView) findViewById(R.id.app_title);
		appTitle.setTextColor(Color.RED);
		appInfo = (TextView) findViewById(R.id.app_info);
		
		Info info = AutoreportApp.infolist.get(position);
		
		appTitle.append("应用进程信息:");
		appInfo.append("应用进程名称:   " + info.getAppName() + "\n");
		appInfo.append("异常时间:           " + info.getExcepTime() + "\n");
		appInfo.append("上报次数:           " + info.getUploadNum() + "\n");
		appInfo.append("启动时间:           " + info.getLaunTime() + "\n");
		appInfo.append("退出时间:           " + info.getExitTime() + "\n");
		appInfo.append("UID:                     " + info.getUid() + "\n");
		appInfo.append("PID:                     " + info.getPid() + "\n");
		appInfo.append("进程数:               " + info.getPidNumber() + "\n");
		appInfo.append("GID:                     " + info.getGid() + "\n");
		
		
		
	}
}