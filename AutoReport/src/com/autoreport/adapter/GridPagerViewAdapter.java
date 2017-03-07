package com.autoreport.adapter;

import java.util.ArrayList;
import com.autoreport.activity.InfoListAcitivity;
import com.autoreport.activity.SecActivity;
import com.autoreport.app.R;
import com.autoreport.datamodel.ImageInfo;
import com.autoreport.service.BackMonitor;
import com.autoreport.util.UStats;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 九宫格导航界面 ViewPager 自定义适配器 周宏
 */
public class GridPagerViewAdapter extends PagerAdapter
{
	ArrayList<ImageInfo> data;
	Context context;

	public GridPagerViewAdapter(Activity activity, ArrayList<ImageInfo> data)
	{
		this.context = activity;
		this.data = data;
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
		// View view =
		// activity.getLayoutInflater().inflate(R.layout.root_gridview, null);
		View view = LayoutInflater.from(context).inflate(R.layout.root_gridview, null);
		GridView gridView = (GridView) view.findViewById(R.id.gridView1);
		gridView.setNumColumns(2);
		gridView.setVerticalSpacing(5);
		gridView.setHorizontalSpacing(5);

		gridView.setAdapter(new ArrayAdapter<ImageInfo>(context, R.layout.root_gridview_item, data)
		{
			@Override
			public View getView(int position, View convertView, ViewGroup parent)
			{
				// TODO Auto-generated method stub
				View view = convertView;
				ViewHolder viewHolder;
				if (view == null)
				{
					view = LayoutInflater.from(context).inflate(R.layout.root_gridview_item, null);
					viewHolder = new ViewHolder();
					viewHolder.imageView = (ImageView) view.findViewById(R.id.imageView1);
					viewHolder.relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout);
					viewHolder.textView = (TextView) view.findViewById(R.id.msg);
					view.setTag(viewHolder);
				} else
				{
					viewHolder = (ViewHolder) view.getTag();
				}

				ImageInfo info = getItem(position);

				// viewHolder.imageView.setImageResource((data.get(index * 8 +
				// position)).imageId);
				// viewHolder.relativeLayout.setBackgroundResource((data.get(index
				// * 8 + position)).bgId);
				// viewHolder.relativeLayout.getBackground().setAlpha(225);
				// viewHolder.textView.setText((data.get(index * 8 +
				// position)).imageMsg);

				viewHolder.imageView.setImageResource(info.getImageId());
				viewHolder.relativeLayout.setBackgroundResource(info.getBgId());
				viewHolder.relativeLayout.getBackground().setAlpha(225);
				viewHolder.textView.setText(info.getImageMsg());

				return view;
			}
		});
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
						Intent intent0 = new Intent(context, BackMonitor.class);
						context.startService(intent0);
						Toast.makeText(context, "开启服务成功", Toast.LENGTH_SHORT).show();
					} catch (Exception e)
					{
						// TODO: handle exception
						Toast.makeText(context, "开启服务失败", Toast.LENGTH_SHORT).show();
					}

					break;

				case 1:
					Intent intent1 = new Intent(context, InfoListAcitivity.class);
					// intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(intent1);
					break;

				case 2:

//					Intent intent2 = new Intent(context, SecActivity.class);
//					intent2.putExtra("position", 0);
//					// intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//					context.startActivity(intent2);
					Toast.makeText(context, "功能3", Toast.LENGTH_SHORT).show();
					break;
				case 3:

					try
					{
						if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP)
						{

							
								Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
								context.startActivity(intent);
							
						} else
						{
							Toast.makeText(context, "版本低于安卓5.1，不需要检查权限", Toast.LENGTH_LONG).show();
						}
					} catch (Exception e)
					{
						Toast.makeText(context, "权限不能获取", Toast.LENGTH_LONG).show();
						e.printStackTrace();
					}

					// Toast.makeText(context, "功能4",
					// Toast.LENGTH_SHORT).show();
					break;
				case 4:
					Toast.makeText(context, "功能5", Toast.LENGTH_SHORT).show();
					break;
				case 5:
					Toast.makeText(context, "功能6", Toast.LENGTH_SHORT).show();
					break;
				case 6:
					Toast.makeText(context, "功能7", Toast.LENGTH_SHORT).show();
					break;
				case 7:
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

	private static class ViewHolder
	{
		ImageView imageView;
		RelativeLayout relativeLayout;
		TextView textView;
	}
}
