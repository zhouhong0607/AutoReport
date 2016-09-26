package com.autoreport.adapter;

import java.util.List;

import com.autoreport.app.R;
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
 * Tab_Wireless 右侧数据List适配器
 * 
 * @author Administrator
 *
 */
public class InfoWirelessListDetailAdapter extends ArrayAdapter<SignalInfo>
{
	private int resourceId;

	public InfoWirelessListDetailAdapter(Context context, int layoutId, List<SignalInfo> objects)
	{
		super(context, layoutId, objects);
		resourceId = layoutId;
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
			viewHolder.rsrp = (TextView) view.findViewById(R.id.rsrp);
			viewHolder.rsrq = (TextView) view.findViewById(R.id.rsrq);
			viewHolder.sinr = (TextView) view.findViewById(R.id.sinr);
			viewHolder.pci = (TextView) view.findViewById(R.id.pci);
			viewHolder.ci = (TextView) view.findViewById(R.id.ci);
			viewHolder.enodebId = (TextView) view.findViewById(R.id.enodeb_id);
			viewHolder.cellId = (TextView) view.findViewById(R.id.cell_id);
			viewHolder.tac = (TextView) view.findViewById(R.id.tac);
			viewHolder.netType = (TextView) view.findViewById(R.id.nettype);
			viewHolder.longitude = (TextView) view.findViewById(R.id.longitude);
			viewHolder.latitude = (TextView) view.findViewById(R.id.latitude);
			viewHolder.address = (TextView) view.findViewById(R.id.address);
			view.setTag(viewHolder);
		} else
		{
			viewHolder = (ViewHolder) view.getTag();
		}
		SignalInfo signalInfo = getItem(position);// 获取当前Info实例

		viewHolder.rsrp.setText(signalInfo.getRsrp());
		viewHolder.rsrq.setText(signalInfo.getRsrq());
		viewHolder.sinr.setText(signalInfo.getRssinr());
		viewHolder.pci.setText(signalInfo.getPci());
		viewHolder.ci.setText(signalInfo.getCi());
		viewHolder.enodebId.setText(signalInfo.getEnodbId());
		viewHolder.cellId.setText(signalInfo.getCellId());
		viewHolder.tac.setText(signalInfo.getTac());
		viewHolder.netType.setText(signalInfo.getNetType());
		viewHolder.longitude.setText(signalInfo.getLongitude());
		viewHolder.latitude.setText(signalInfo.getLatitude());
		viewHolder.address.setText(signalInfo.getAddr());
		return view;
	}

	private static class ViewHolder
	{
		private TextView rsrp;
		private TextView rsrq;
		private TextView sinr;
		private TextView pci;
		private TextView ci;
		private TextView enodebId;
		private TextView cellId;
		private TextView tac;
		private TextView netType;
		private TextView longitude;
		private TextView latitude;
		private TextView address;
	}

}