package com.autoreport.adapter;

import java.util.ArrayList;

import com.autoreport.activity.InfoListAcitivity;
import com.autoreport.activity.SecActivity;
import com.autoreport.app.R;
import com.autoreport.datastructure.AutoreportApp;
import com.autoreport.datastructure.ImageInfo;
import com.autoreport.service.BackMonitor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
//import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 自定义适配器
 * 
 */
public class MyPagerAdapter extends PagerAdapter
{
	Vibrator vibrator;
	ArrayList<ImageInfo> data;
	Activity activity;
	LayoutParams params;

	public MyPagerAdapter(Activity activity, ArrayList<ImageInfo> data)
	{
		this.activity = activity;
		this.data = data;
		vibrator = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
	}

	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1)
	{
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	@Override
	public Object instantiateItem(ViewGroup container, final int index)
	{
		View view = activity.getLayoutInflater().inflate(R.layout.gridview, null);
		GridView gridView = (GridView) view.findViewById(R.id.gridView1);
		gridView.setNumColumns(2);
		gridView.setVerticalSpacing(5);
		gridView.setHorizontalSpacing(5);
		gridView.setAdapter(new BaseAdapter()
		{

			@Override
			public int getCount()
			{
				return 8;
			}

			@Override
			public Object getItem(int position)
			{
				return position;
			}

			@Override
			public long getItemId(int position)
			{
				return position;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent)
			{
				View item = LayoutInflater.from(activity).inflate(R.layout.gridview_item, null);
				ImageView iv = (ImageView) item.findViewById(R.id.imageView1);
				RelativeLayout relativeLayout = (RelativeLayout) item.findViewById(R.id.relativeLayout);
				iv.setImageResource((data.get(index * 8 + position)).imageId);
				relativeLayout.setBackgroundResource((data.get(index * 8 + position)).bgId);
				relativeLayout.getBackground().setAlpha(225);
				TextView tv = (TextView) item.findViewById(R.id.msg);
				tv.setText((data.get(index * 8 + position)).imageMsg);
				return item;
			}
		});

		// gridView.setOnItemLongClickListener(new OnItemLongClickListener()
		// {
		//
		// @Override
		// public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int
		// arg2, long arg3)
		// {
		// View view = arg1;
		// arg1.setVisibility(View.INVISIBLE);
		//
		// params = new WindowManager.LayoutParams();
		// // activity.getWindowManager().addView(view, params);
		// vibrator.vibrate(2500);
		// return true;
		// }
		// });

		// 添加点击事件
		gridView.setOnItemClickListener(new OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				// arg2为选中的第几个图标 0开始
				switch (arg2)
				{
				case 0:

					// 启动后台服务 BackMonitor
					try
					{
						Intent intent0 = new Intent(activity, BackMonitor.class);
						activity.startService(intent0);
						Toast.makeText(activity, "开启服务成功", Toast.LENGTH_SHORT).show();
					} catch (Exception e)
					{
						// TODO: handle exception
						Toast.makeText(activity, "开启服务失败", Toast.LENGTH_SHORT).show();
					}

					break;

				case 1:
					Intent intent1 = new Intent(activity, InfoListAcitivity.class);
//					intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
					activity.startActivity(intent1);
					// Toast.makeText(getApplicationContext(),
					// "信息列表",Toast.LENGTH_SHORT).show();
					break;

				case 2:

					Intent intent2 = new Intent(activity, SecActivity.class);
					intent2.putExtra("position", 0);
//					 intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
					
					activity.startActivity(intent2);
					Toast.makeText(activity, "功能3", Toast.LENGTH_SHORT).show();
					break;
				case 3:
					Toast.makeText(activity, "功能4", Toast.LENGTH_SHORT).show();
					break;
				case 4:
					Toast.makeText(activity, "功能5", Toast.LENGTH_SHORT).show();
					break;
				case 7:
					Toast.makeText(activity, "退出", Toast.LENGTH_SHORT).show();
					System.exit(0);
					break;
				default:
					break;
				}

			}
		});

		((ViewPager) container).addView(view);

		return view;
	}
}
