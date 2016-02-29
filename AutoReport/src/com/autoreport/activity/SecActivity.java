package com.autoreport.activity;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.autoreport.app.R;
import com.autoreport.datastructure.Info;
import com.autoreport.datastructure.AutoreportApp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
//						upload_info(MyApp.infolist.get(position));
					}

				}.start();

			}
		});

		Info info = AutoreportApp.infolist.get(position);
		// 25项信息
		// 手机基本信息
		mtextview1.append("手机基本信息:");
		mtextview2.append("品牌:          " + info.getBrand() + "\n");
		mtextview2.append("型号:          " + info.getType() + "\n");
		mtextview2.append("版本:           " + info.getVersion() + "\n");
		mtextview2.append("IMEI:          " + info.getIMEI() + "\n");
		mtextview2.append("IMSI:          " + info.getIMSI() + "\n");
		mtextview2.append("本机IP地址:  " + info.getLocalIp() + "\n");
		mtextview2.append("内存占用率:  " + info.getMemRate() + "\n");
		mtextview2.append("CPU使用率:  " + info.getCpuRate() + "\n");

		// 无线环境信息

		mtextview3.append("无线环境信息:");

		mtextview4.append("运营商:  " + info.getCorporation() + "\n");
		mtextview4.append("网络类型:  " + info.getNetType() + "\n");
		mtextview4.append("LAC_GSM:  " + info.getLAC_GSM() + "\n");
		mtextview4.append("Cell-ID_GSM:  " + info.getCell_Id_GSM() + "\n");
		
		mtextview4.append("PCI:  " + info.getPCI() + "\n");
		mtextview4.append("CI:  " + info.getCI() + "\n");
		mtextview4.append("ENODBID:  " + info.getENODBID() + "\n");
		mtextview4.append("CELLID:  " + info.getCELLID() + "\n");
		mtextview4.append("TAC:  " + info.getTAC() + "\n");
		
		

		mtextview4.append("RSRP:  " + info.getRSRP() + "\n");
		mtextview4.append("RSRQ:  " + info.getRSRQ() + "\n");
//		mtextview4.append("RSSI:  " + info.getRSSI() + "\n");
		mtextview4.append("SNR:  " + info.getRSSNR() + "\n");
		// 应用业务信息

		mtextview5.append("应用业务信息:");

		mtextview6.append("启动时间:  " + info.getLaunTime() + "\n");
		mtextview6.append("异常时间:  " + info.getExcepTime() + "\n");
		mtextview6.append("上报时间:  " + info.getUploadTime() + "\n");
		mtextview6.append("上报次数:  " + info.getUploadNum() + "\n");
		mtextview6.append("退出时间:  " + info.getExitTime() + "\n");
		mtextview6.append("运行时间:  " + info.getUseTime() + "\n");
		mtextview6.append("应用名称:  " + info.getAppName() + "\n");
		mtextview6.append("UID:  " + info.getUid() + "\n");
		mtextview6.append("PID:  " + info.getPid() + "\n");
		mtextview6.append("进程数量:  " + info.getPidNumber() + "\n");
		mtextview6.append("GID:  " + info.getGid() + "\n");
		mtextview6.append("发送字节量:  " + info.getTxByte() + "\n");
		mtextview6.append("接收字节量:  " + info.getRxByte() + "\n");

	}

//	public void upload_info(Info info)
//	{
//
//		String urlStr = "http://www.mengqi.win/LoginServlet";
//		HttpPost request = new HttpPost(urlStr);
//		BasicHttpParams httpParams = new BasicHttpParams();
//		// 设置请求超时
//		int timeoutConnection = 2 * 1000;
//		HttpConnectionParams.setConnectionTimeout(httpParams, timeoutConnection);
//		// 设置响应超时
//		int timeoutSocket = 2 * 1000;
//		HttpConnectionParams.setSoTimeout(httpParams, timeoutSocket);
//
//		List<NameValuePair> params = new ArrayList<NameValuePair>();
//
//		// 上传信息加入
//		params.add(new BasicNameValuePair("launtime", info.gettime()));
//		params.add(new BasicNameValuePair("exittime", info.getextime()));
//		params.add(new BasicNameValuePair("usetime", info.getusetime()));
//		params.add(new BasicNameValuePair("brand", info.getbrand()));
//		params.add(new BasicNameValuePair("type", info.gettype()));
//		params.add(new BasicNameValuePair("version", info.getversion()));
//		params.add(new BasicNameValuePair("IMEI", info.getIMEI()));
//		params.add(new BasicNameValuePair("IMSI", info.getIMSI()));
//		params.add(new BasicNameValuePair("corporation", info.getcorporation()));
//		params.add(new BasicNameValuePair("LAC", info.getLAC()));
//		params.add(new BasicNameValuePair("Cell_Id", info.getCell_Id()));
//		params.add(new BasicNameValuePair("RSRP", info.getRSRP()));
//		params.add(new BasicNameValuePair("RSSI", info.getRSSI()));
//		params.add(new BasicNameValuePair("RSRQ", info.getRSRQ()));
//		params.add(new BasicNameValuePair("cpuRate", info.getcpuRate()));
//		params.add(new BasicNameValuePair("localIp", info.getlocalIp()));
//		params.add(new BasicNameValuePair("AppName", info.getAppName()));
//		params.add(new BasicNameValuePair("uid", info.getuid()));
//		params.add(new BasicNameValuePair("pid", info.getpid()));
//		params.add(new BasicNameValuePair("gid", info.getgid()));
//		params.add(new BasicNameValuePair("pidNumber", info.getpidNumber()));
//		params.add(new BasicNameValuePair("MemRate", info.getMemRate()));
//		params.add(new BasicNameValuePair("TxByte", info.getTxByte()));
//		params.add(new BasicNameValuePair("RxByte", info.getRxByte()));
//		params.add(new BasicNameValuePair("NetType", info.getNetType()));
//		params.add(new BasicNameValuePair("RSSNR", info.getSNR()));
//		params.add(new BasicNameValuePair("Flag", info.getFlag()));
//
//		try
//		{
//			request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
//			HttpClient httpclient = new DefaultHttpClient(httpParams);
//
//			HttpResponse response = httpclient.execute(request);
//
//			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
//			{
//				Log.i("AAA", "上传成功");
//				// Toast.makeText(this, "上传成功", Toast.LENGTH_LONG).show();
//			} else
//			{
//				Log.i("AAA", "上传失败");
//				// Toast.makeText(this, "上传失败", Toast.LENGTH_LONG).show();
//			}
//		} catch (Exception e)
//		{
//			// TODO: handle exception
//			e.printStackTrace();
//			Log.i("AAA", "上传失败");
//			// Toast.makeText(this, "上传失败", Toast.LENGTH_LONG).show();
//		}
//	}

}
