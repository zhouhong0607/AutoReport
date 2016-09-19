package com.autoreport.adapter;

import java.util.List;

import com.autoreport.app.R;
import com.autoreport.datastructure.Info;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

//List的适配器     有待优化！！！！！！！！！！！！！！！！！！！！！！！！
public class InfoListAdapter extends ArrayAdapter<Info>
{
	private int resourceId;
	public InfoListAdapter(Context context,int textViewResourceId,List<Info> objects)
	{
		super(context,textViewResourceId,objects);
		resourceId=textViewResourceId;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		// TODO Auto-generated method stub
		Info info=getItem(position);//获取当前Info实例
		View view=LayoutInflater.from(getContext()).inflate(resourceId, null);
		TextView info_list_view=(TextView)view.findViewById(R.id.item_view);
		info_list_view.setText(info.getExcepTime());//按异常事件 的异常时间信息显示
		return view;
	}
}
