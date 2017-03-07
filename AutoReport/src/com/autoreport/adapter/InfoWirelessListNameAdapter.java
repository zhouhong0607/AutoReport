package com.autoreport.adapter;

import java.util.List;

import com.autoreport.app.R;
import com.autoreport.datamodel.BaseInfo;
import com.autoreport.datamodel.SignalInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Tab_WireLess  左侧标题List适配器
 * @author 周宏
 *
 */
public class InfoWirelessListNameAdapter extends ArrayAdapter<SignalInfo>
{
	private int resourceId;
	public InfoWirelessListNameAdapter(Context context,int layoutId,List<SignalInfo> objects)
	{
		super(context,layoutId,objects);
		resourceId=layoutId;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		// TODO Auto-generated method stub
		View view=convertView;
		ViewHolder viewHolder;
		if(view==null)
		{
			view=LayoutInflater.from(getContext()).inflate(resourceId, null);
			viewHolder=new ViewHolder();
			viewHolder.timeStamp=(TextView)view.findViewById(R.id.timestamp);
			view.setTag(viewHolder);
		}else
		{
			viewHolder=(ViewHolder)view.getTag();
		}
		SignalInfo signalInfo=getItem(position);//获取当前Info实例
		viewHolder.timeStamp.setText(signalInfo.getTimeStamp());
		
		return view;
	}
	private static class ViewHolder
	{
	private	TextView timeStamp;
	}
	
	
}