package com.example.testservice;

import java.util.List;

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
		info_list_view.setText(info.gettime());//将时间戳显示出来
		return view;
	}
}
