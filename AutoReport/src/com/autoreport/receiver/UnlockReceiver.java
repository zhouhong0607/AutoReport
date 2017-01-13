package com.autoreport.receiver;

import com.autoreport.service.BackMonitor;

import android.R.integer;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class UnlockReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context,Intent intent)
	{
		Intent serviceIntent=new Intent(context,BackMonitor.class);
		context.startService(serviceIntent);
		Toast.makeText(context, "屏幕点亮开启服务成功", Toast.LENGTH_SHORT).show();
		
	}
}
