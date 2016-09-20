package com.autoreport.adapter;

import java.util.List;

import com.autoreport.app.R;
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
 * Created by Lunger on 7/13 0013 16:06
 */
public class LvInfoAdapter extends ArrayAdapter<SignalInfo>
{
	private int resourceId;
	public LvInfoAdapter(Context context,int layoutId,List<SignalInfo> objects)
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
		
		TextView rsrp=(TextView)view.findViewById(R.id.rsrp);
		TextView rsrq=(TextView)view.findViewById(R.id.rsrq);
		TextView sinr=(TextView)view.findViewById(R.id.sinr);
		TextView pci=(TextView)view.findViewById(R.id.pci);
		TextView ci=(TextView)view.findViewById(R.id.ci);
		TextView enodebId=(TextView)view.findViewById(R.id.enodeb_id);
		TextView cellId=(TextView)view.findViewById(R.id.cell_id);
		TextView tac=(TextView)view.findViewById(R.id.tac);
		TextView netType=(TextView)view.findViewById(R.id.nettype);
		TextView longitude=(TextView)view.findViewById(R.id.longitude);
		TextView latitude=(TextView)view.findViewById(R.id.latitude);
		TextView address=(TextView)view.findViewById(R.id.address);
		rsrp.setText(signalInfo.getRsrp());
		rsrq.setText(signalInfo.getRsrq());
		sinr.setText(signalInfo.getRssinr());
		pci.setText(signalInfo.getPci());
		ci.setText(signalInfo.getCi());
		enodebId.setText(signalInfo.getEnodbId());
		cellId.setText(signalInfo.getCellId());
		tac.setText(signalInfo.getTac());
		netType.setText(signalInfo.getNetType());
		longitude.setText(signalInfo.getLongitude());
		latitude.setText(signalInfo.getLatitude());
		address.setText(signalInfo.getAddr());
		return view;
	}
}