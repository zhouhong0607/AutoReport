package com.autoreport.adapter;

import java.util.List;

import com.autoreport.app.R;
import com.autoreport.datastructure.Info;
import com.autoreport.datastructure.SignalInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 左侧标题适配器
 * @author 周宏
 *
 */
public class LvNameAdapter extends ArrayAdapter<SignalInfo>
{
	private int resourceId;
	public LvNameAdapter(Context context,int layoutId,List<SignalInfo> objects)
	{
		super(context,layoutId,objects);
		resourceId=layoutId;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		// TODO Auto-generated method stub
		SignalInfo signalInfo=getItem(position);//获取当前Info实例
		View view=LayoutInflater.from(getContext()).inflate(resourceId, null);
		
		TextView timestamp=(TextView)view.findViewById(R.id.timestamp);
		timestamp.setText(signalInfo.getTimeStamp());//按异常事件 的异常时间信息显示
		return view;
	}
	
	
	
}