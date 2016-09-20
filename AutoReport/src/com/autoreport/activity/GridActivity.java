package com.autoreport.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.autoreport.app.R;
import com.autoreport.service.BackMonitor;
import com.autoreport.util.UStats;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
/**
 * 九宫格引导界面
 * @author 周宏
 *
 */
public class GridActivity extends Activity
{
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.grid);
		
		//检查安卓版本，设置权限
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
		
		
		
		
		
		GridView gridview = (GridView) findViewById(R.id.gridview);
		ArrayList<HashMap<String, Object>> meumList = new ArrayList<HashMap<String, Object>>();

		// 添加九宫格中数据源（功能选项）
		// 添加 “开启服务”
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ItemImage", R.drawable.ic_launcher);
		map.put("ItemText", "开启服务");
		meumList.add(map);

		// 添加 “查询信息”
		map = new HashMap<String, Object>();
		map.put("ItemImage", R.drawable.ic_launcher);
		map.put("ItemText", "查询信息");
		meumList.add(map);
		// 添加 “功能3”
		map = new HashMap<String, Object>();
		map.put("ItemImage", R.drawable.ic_launcher);
		map.put("ItemText", "功能3");
		meumList.add(map);
		// 添加 “功能4”
		map = new HashMap<String, Object>();
		map.put("ItemImage", R.drawable.ic_launcher);
		map.put("ItemText", "功能4");
		meumList.add(map);
		// 添加 “功能5”
		map = new HashMap<String, Object>();
		map.put("ItemImage", R.drawable.ic_launcher);
		map.put("ItemText", "功能5");
		meumList.add(map);
		// 添加 “退出”
		map = new HashMap<String, Object>();
		map.put("ItemImage", R.drawable.ic_launcher);
		map.put("ItemText", "退出");
		meumList.add(map);
		
		SimpleAdapter saItem = new SimpleAdapter(this, meumList, // 数据源
				R.layout.grid_item, // xml实现
				new String[]
				{ "ItemImage", "ItemText" }, // 对应map的Key
				new int[]
				{ R.id.ItemImage, R.id.ItemText }); // 对应R的Id

		// 添加Item到网格中
		gridview.setAdapter(saItem);
		// 添加点击事件
		gridview.setOnItemClickListener(new OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				//arg2为选中的第几个图标 0开始
				switch (arg2)
				{
				case 0:
					
					//启动后台服务 BackMonitor
					try
					{
					Intent intent0 = new Intent(getApplicationContext(), BackMonitor.class);
					startService(intent0);
					Toast.makeText(getApplicationContext(), "开启服务成功",Toast.LENGTH_SHORT).show();
					}catch (Exception e) {
						// TODO: handle exception
						Toast.makeText(getApplicationContext(), "开启服务失败",Toast.LENGTH_SHORT).show();
					}
					
					break;
					
				case 1:
					Intent intent1=new Intent(getApplicationContext(),InfoListAcitivity.class);
					startActivity(intent1);
//					Toast.makeText(getApplicationContext(), "信息列表",Toast.LENGTH_SHORT).show();
					break;

				case 2:
					
					Intent intent2 = new Intent(getApplicationContext(), SecActivity.class);
					intent2.putExtra("position", 0);
					startActivity(intent2);
					Toast.makeText(getApplicationContext(), "功能3",Toast.LENGTH_SHORT).show();
					break;
				case 3:
					Toast.makeText(getApplicationContext(), "功能4",Toast.LENGTH_SHORT).show();
					break;
				case 4:
					Toast.makeText(getApplicationContext(), "功能5",Toast.LENGTH_SHORT).show();
					break;
				case 5:
					Toast.makeText(getApplicationContext(), "退出",Toast.LENGTH_SHORT).show();
					System.exit(0);
					break;
				default:
					break;
				}

			}
		});
	}
}