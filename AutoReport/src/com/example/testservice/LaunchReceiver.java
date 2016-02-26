package com.example.testservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class LaunchReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent)
	{
		try
		{
			Thread.sleep(10000);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		InfoDatabase infoDatabase = new InfoDatabase(context, "AutoReprt.db", null, 1);// 创建数据库
																						// “AutoReport”
		DatabaseOperator databaseOperator = new DatabaseOperator(infoDatabase);
		databaseOperator.deleteFromInfo();// 每次开机 删除数据库里面数据
		// Toast.makeText(context, "数据删除成功", Toast.LENGTH_SHORT).show();
		// 开机启动
		// Intent intent1=new Intent(context,MainActivity.class);
		// intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//Intent.FLAG_ACTIVITY_NEW_TASK
		// context.startActivity(intent1);
	}

}