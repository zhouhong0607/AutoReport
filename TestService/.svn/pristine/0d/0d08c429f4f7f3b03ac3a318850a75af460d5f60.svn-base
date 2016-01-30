package com.example.testservice;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.app.Activity;
import android.app.usage.UsageStats;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
//import android.telecom.Connection;
import android.view.View;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity
{
	// TextView textview1;
	ListView listView;

	// 用于UI更新的广播
	MyBroadcast mBroadcast = new MyBroadcast();

	// 广播过滤
	IntentFilter filter1 = new IntentFilter();

	// 绑定服务*******************
	private TestService.UpdateBinder updatebinder;
	private ServiceConnection connection = new ServiceConnection()
	{

		@Override
		public void onServiceDisconnected(ComponentName name)
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service)
		{
			// TODO Auto-generated method stub
			updatebinder = (TestService.UpdateBinder) service;
			updatebinder.sendUpdateBro();// 建立连接时发送广播
		}
	};

	// 绑定服务*******************
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Check if permission enabled
		try
		{
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP)
		{
			
			if (UStats.getUsageStatsList(this).isEmpty())
			{
				Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
				startActivity(intent);
			}
		}
		}catch(Exception e)
		{
			Toast.makeText(getApplicationContext(), "权限不能获取", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		// 注册广播
		filter1.addAction("UPDATE_ACTION");
		registerReceiver(mBroadcast, filter1);

		listView = (ListView) findViewById(R.id.list_view);
		listView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				// 监听点击事件，开启新Activity显示数据
				Intent intent = new Intent(MainActivity.this, SecActivity.class);
				intent.putExtra("position", position);
				startActivity(intent);
			}
		});

		Intent i = new Intent(this, TestService.class);
		startService(i);

		// this.finish();

	}

	protected void onDestroy()
	{
		// Intent i = new Intent(this, TestService.class);
		// stopService(i);
		unregisterReceiver(mBroadcast);// 注销广播
		unbindService(connection);// 解绑服务
		super.onDestroy();
	}

	public class MyBroadcast extends BroadcastReceiver
	{

		@Override
		public void onReceive(Context context, Intent intent)
		{
			// Toast.makeText(getApplicationContext(), "收到广播",
			// Toast.LENGTH_SHORT).show();
			if (MyApp.infolist.size() != 0)// 有异常信息，显示到Listview
			{
				InfoListAdapter adapter = new InfoListAdapter(MainActivity.this, R.layout.info_list_item,
						MyApp.infolist);
				listView.setAdapter(adapter);
			}

		}
	}

	// @Override
	protected void onResume()
	{
		super.onResume();
		Intent bindIntent = new Intent(this, TestService.class);// 每次调用onStart执行一次绑定
		bindService(bindIntent, connection, BIND_AUTO_CREATE);

		if (MyApp.infolist.size() != 0)// 有异常信息，显示到Listview
		{
			InfoListAdapter adapter = new InfoListAdapter(MainActivity.this, R.layout.info_list_item, MyApp.infolist);
			listView.setAdapter(adapter);
		}
	}

}
