package com.autoreport.adapter;

import java.util.List;

import com.autoreport.app.R;
import com.autoreport.datamodel.BaseInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import viewholder.MyViewHolder;


public class InfoListAdapter extends ArrayAdapter<BaseInfo>
{
	private int resourceId;
	public InfoListAdapter(Context context, int textViewResourceId, List<BaseInfo> objects)
	{
		super(context, textViewResourceId, objects);
		resourceId = textViewResourceId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		// TODO Auto-generated method stub
		View view = convertView;
		ViewHolder viewHolder;
		if (view == null)
		{
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
			viewHolder = new ViewHolder();
			viewHolder.info_list_view = (TextView) view.findViewById(R.id.item_view);
			view.setTag(viewHolder);
		} else
		{
			viewHolder = (ViewHolder) view.getTag();
		}
	
		BaseInfo info = getItem(position);// 获取当前Info实例
		viewHolder.info_list_view.setText(info.getExcepTime());// 按异常事件
																// 的异常时间信息显示
		return view;
	}

	private static class ViewHolder
	{
		TextView info_list_view;

	}

}
