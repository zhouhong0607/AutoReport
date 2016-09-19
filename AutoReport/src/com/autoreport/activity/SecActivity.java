package com.autoreport.activity;

import java.util.List;

import com.autoreport.app.R;
import com.autoreport.database.DatabaseOperator;
import com.autoreport.database.InfoDatabase;
import com.autoreport.datastructure.Info;
import com.autoreport.datastructure.SignalInfo;
import com.autoreport.datastructure.AutoreportApp;

import android.app.Activity;
import android.content.Intent;

import android.graphics.Color;
import android.os.Bundle;

import android.text.TextPaint;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SecActivity extends Activity
{
	TextView mtextview1;
	TextView mtextview2;
	TextView mtextview3;
	TextView mtextview4;
	TextView mtextview5;
	TextView mtextview6;
	Button upload;
	int position;
	TextPaint tp;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sec);
		mtextview1 = (TextView) findViewById(R.id.textview1);
		mtextview2 = (TextView) findViewById(R.id.textview2);
		mtextview3 = (TextView) findViewById(R.id.textview3);
		mtextview4 = (TextView) findViewById(R.id.textview4);
		mtextview5 = (TextView) findViewById(R.id.textview5);
		mtextview6 = (TextView) findViewById(R.id.textview6);
		mtextview1.setTextColor(Color.RED);
		mtextview3.setTextColor(Color.RED);
		mtextview5.setTextColor(Color.RED);
		Intent intent = getIntent();
		position = intent.getIntExtra("position", 0);

		Log.i("AAA", String.valueOf(position));

		upload = (Button) findViewById(R.id.upload);
		upload.setVisibility(View.GONE);
		upload.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				new Thread()
				{
					public void run()
					{
						// upload_info(MyApp.infolist.get(position));
					}

				}.start();

			}
		});

		Info info = AutoreportApp.infolist.get(position);
		// 25项信息
		// 手机基本信息
		mtextview1.append("手机基本信息:");
		mtextview2.append("手机品牌:            " + info.getBrand() + "\n");
		mtextview2.append("手机型号:            " + info.getType() + "\n");
		mtextview2.append("Android版本:      " + info.getVersion() + "\n");
		mtextview2.append("本机IP地址:        " + info.getLocalIp() + "\n");
		mtextview2.append("IMEI:                    " + info.getIMEI() + "\n");
		mtextview2.append("IMSI:                    " + info.getIMSI() + "\n");
		mtextview2.append("内存占用率:        " + info.getMemRate() + "\n");
		mtextview2.append("CPU使用率:         " + info.getCpuRate() + "\n");
		mtextview2.append("运营商:                " + info.getCorporation() + "\n");

		// 应用业务信息

		mtextview3.append("应用进程信息:");
		mtextview4.append("应用进程名称:   " + info.getAppName() + "\n");
		mtextview4.append("异常时间:           " + info.getExcepTime() + "\n");
		mtextview4.append("上报次数:           " + info.getUploadNum() + "\n");
		mtextview4.append("启动时间:           " + info.getLaunTime() + "\n");
		mtextview4.append("退出时间:           " + info.getExitTime() + "\n");
		mtextview4.append("UID:                     " + info.getUid() + "\n");
		mtextview4.append("PID:                     " + info.getPid() + "\n");
		mtextview4.append("进程数:               " + info.getPidNumber() + "\n");
		mtextview4.append("GID:                     " + info.getGid() + "\n");

		mtextview4.append("判决依据:                     " + info.getExcepType() + "\n");
		
		// mtextview4.append("上报时间: " + info.getUploadTime() + "\n");
		// mtextview4.append("运行时间: " + info.getUseTime() + "\n");

		// 无线环境信息

		mtextview5.append("业务流量与无线环境信息:\n");
		mtextview5.append("(时间戳，发送字节量，接收字节量，RSRP，RSRQ，SINR，PCI，CI，Enodeb_id，cell_id，TAC，网络类型，经度，纬度，地址)");
		// mtextview6.append("运营商: " + info.getCorporation() + "\n");
		// mtextview6.append("LAC_GSM: " + info.getLAC_GSM() + "\n");
		// mtextview6.append("Cell-ID_GSM: " + info.getCell_Id_GSM() + "\n\n");

		InfoDatabase infoDatabase = new InfoDatabase(this, "AutoReprt.db", null, 1);// 创建数据库
																					// //
																					// “AutoReport”
		DatabaseOperator databaseOperator = new DatabaseOperator(infoDatabase);
		List<SignalInfo> signalInfos = databaseOperator.queryFromSignalInfoById(info.getId());

		databaseOperator.CloseDatabase();

		
		
		
		
		
		String siglist = "";
		if (signalInfos.size() != 0)
		{
			//标注流量最大值和未通过通信测试点
			int maxIndex=info.getMaxIndex();
			int noRxIndex=info.getNoRxIndex();
			if(maxIndex>=0) 
			{
				String value=signalInfos.get(maxIndex).getRxByte();
				signalInfos.get(maxIndex).setRxByte(value+"(最大值)");
				
			}
			if(noRxIndex>=0)
			{
				String value=signalInfos.get(noRxIndex).getRxByte();
				signalInfos.get(noRxIndex).setRxByte(value+"(未通过通信测试)");
			}
			
			
			for (int i = 0; i < signalInfos.size(); i++)
			{
				siglist += signalInfos.get(i).getTimeStamp() + ", " + signalInfos.get(i).getTxByte() + ", "
						+ signalInfos.get(i).getRxByte() + ", " + signalInfos.get(i).getRsrp() + ", "
						+ signalInfos.get(i).getRsrq() + ", " + signalInfos.get(i).getRssinr() + ", "
						+ signalInfos.get(i).getPci() + ", " + signalInfos.get(i).getCi() + ", "
						+ signalInfos.get(i).getEnodbId() + ", " + signalInfos.get(i).getCellId() + ", "
						+ signalInfos.get(i).getTac() + ", " + signalInfos.get(i).getNetType() + ", "
						+ signalInfos.get(i).getLongitude() + ", " + signalInfos.get(i).getLatitude() + ", "
						+ signalInfos.get(i).getAddr() + "\n\n";
				// if((i+1)!=signalInfos.size())
				// siglist+= "|";

			}

		}

		mtextview6.append(siglist);
	}

}
