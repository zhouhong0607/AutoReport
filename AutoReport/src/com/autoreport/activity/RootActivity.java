package com.autoreport.activity;

import java.util.ArrayList;

import com.autoreport.adapter.GridPagerViewAdapter;
import com.autoreport.app.R;
import com.autoreport.datamodel.ImageInfo;
import com.autoreport.receiver.UnlockReceiver;
import com.autoreport.service.BackMonitor;
import com.autoreport.util.UStats;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
/**
 * 九宫格导航界面
 * @author 周宏
 *
 */
public class RootActivity extends Activity
{
	private ArrayList<ImageInfo> data; // 菜单数据
	private static TextView mynum; // 页码
	private	GridView gridView;

	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.root);

		
		
		
		// 检查安卓版本，设置权限
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
		} catch (Exception e)
		{
			Toast.makeText(getApplicationContext(), "权限不能获取", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}

		mynum = (TextView) findViewById(R.id.mynum);
		// 初始化数据
		initData();
		ViewPager vpager = (ViewPager) findViewById(R.id.vPager);
		vpager.setAdapter(new GridPagerViewAdapter(RootActivity.this, data));
		vpager.setPageMargin(50);
		vpager.setOnPageChangeListener(new OnPageChangeListener()
		{

			@Override
			public void onPageSelected(int arg0)
			{
				mynum.setText("" + (int) (arg0 + 1));
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2)
			{

			}

			@Override
			public void onPageScrollStateChanged(int arg0)
			{

			}
		});

	}

	private void initData()
	{
		data = new ArrayList<ImageInfo>();
		mynum.setText("1");
		data.add(new ImageInfo("开启服务", R.drawable.icon1, R.drawable.icon_bg01));
		data.add(new ImageInfo("信息列表", R.drawable.icon2, R.drawable.icon_bg01));
		data.add(new ImageInfo("功能3", R.drawable.icon3, R.drawable.icon_bg02));
		data.add(new ImageInfo("检查权限", R.drawable.icon12, R.drawable.icon_bg01));
		data.add(new ImageInfo("功能5", R.drawable.icon5, R.drawable.icon_bg02));
		data.add(new ImageInfo("功能6", R.drawable.icon6, R.drawable.icon_bg02));
		data.add(new ImageInfo("功能7", R.drawable.icon7, R.drawable.icon_bg02));
		data.add(new ImageInfo("退出", R.drawable.icon10, R.drawable.icon_bg01));
//		data.add(new ImageInfo("ͨ功能9", R.drawable.icon9, R.drawable.icon_bg02));
//		data.add(new ImageInfo("功能10", R.drawable.icon10, R.drawable.icon_bg02));
//		data.add(new ImageInfo("功能11", R.drawable.icon11, R.drawable.icon_bg02));
//		data.add(new ImageInfo("功能12", R.drawable.icon12, R.drawable.icon_bg02));
//		data.add(new ImageInfo("功能13", R.drawable.icon13, R.drawable.icon_bg02));
//		data.add(new ImageInfo("功能14", R.drawable.icon14, R.drawable.icon_bg02));
//		data.add(new ImageInfo("功能15", R.drawable.icon15, R.drawable.icon_bg02));
//		data.add(new ImageInfo("功能16", R.drawable.icon16, R.drawable.icon_bg02));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
