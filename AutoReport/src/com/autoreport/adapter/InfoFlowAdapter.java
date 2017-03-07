package com.autoreport.adapter;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.autoreport.app.R;
import com.autoreport.datamodel.BaseInfo;
import com.autoreport.datamodel.SignalInfo;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class InfoFlowAdapter extends ArrayAdapter<SignalInfo>
{
	private int resourceId;

	public InfoFlowAdapter(Context context, int listViewItemId, List<SignalInfo> objects)
	{
		super(context, listViewItemId, objects);
		resourceId = listViewItemId;
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
			viewHolder.flowListTime = (TextView) view.findViewById(R.id.flow_list_time);
			viewHolder.flowListTx = (TextView) view.findViewById(R.id.flow_list_tx);
			viewHolder.flowListRx = (TextView) view.findViewById(R.id.flow_list_rx);
			view.setTag(viewHolder);
		} else
		{
			viewHolder = (ViewHolder) view.getTag();
		}

		SignalInfo info = getItem(position);// 获取当前Info实例

//		if (!isNumeric(info.getRxByte()))
//		{
//			viewHolder.flowListRx.setTextColor(Color.BLUE);
//		}
		
		viewHolder.flowListTime.setText(info.getTimeStamp());
		viewHolder.flowListTx.setText(info.getTxByte());
		viewHolder.flowListRx.setText(info.getRxByte());
		return view;
	}

	private static class ViewHolder
	{
		private TextView flowListTime;
		private TextView flowListTx;
		private TextView flowListRx;
	}

	/**
	 * 判决是否是数字
	 * 
	 * @param str
	 * @return
	 */
	private boolean isNumeric(String str)
	{
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches())
		{
			return false;
		}
		return true;
	}

}
