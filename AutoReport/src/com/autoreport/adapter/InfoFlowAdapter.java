package com.autoreport.adapter;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.autoreport.app.R;
import com.autoreport.datastructure.Info;
import com.autoreport.datastructure.SignalInfo;

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
		SignalInfo info = getItem(position);// 获取当前Info实例
		View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
		TextView flowListTime = (TextView) view.findViewById(R.id.flow_list_time);
		TextView flowListTx = (TextView) view.findViewById(R.id.flow_list_tx);
		TextView flowListRx = (TextView) view.findViewById(R.id.flow_list_rx);

		if (!isNumeric(info.getRxByte()))
		{
			flowListRx.setTextColor(Color.BLUE);
		}
		
		flowListTime.setText(info.getTimeStamp());
		flowListTx.setText(info.getTxByte());
		flowListRx.setText(info.getRxByte());

		
			
			
			return view;
	}
/**
 * 判决是否是数字
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
