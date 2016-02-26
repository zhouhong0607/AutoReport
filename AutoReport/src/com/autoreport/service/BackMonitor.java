package com.autoreport.service;

import android.R.integer;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.sql.Array;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.util.InetAddressUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;

import com.autoreport.database.DatabaseOperator;
import com.autoreport.database.InfoDatabase;
import com.autoreport.datastructure.AppList;
import com.autoreport.datastructure.Info;
import com.autoreport.datastructure.MyApp;
import com.autoreport.datastructure.MyQueue;
import com.autoreport.util.ExtraUtil;
import com.autoreport.util.UStats;

import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.Settings;
import android.telephony.CellInfo;
import android.telephony.CellInfoLte;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextPaint;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.util.Log;
import android.widget.Toast;

public class BackMonitor extends Service
{
	long tx1 = 0;
	long rx1 = 0;
	boolean Browserun = false;// 浏览器运行判断
	boolean assit = false;// 辅助判断参数
	boolean Browserquit = false;// 浏览器退出判断
	MyQueue txqueue_laun = new MyQueue(30);// 构建发送接受队列，时间长度10秒
	MyQueue rxqueue_laun = new MyQueue(30);// 0.1秒间隔 ，20秒的数据
	MyQueue txqueue_exit = new MyQueue(10);// 构建发送接受队列，时间长度10秒
	MyQueue rxqueue_exit = new MyQueue(10);// 0.1秒间隔 ，20秒的数据
	int count = 0;// 应用运行时间记录
	int upload_time = 0;// 周期上传时间
	String pkgname = "";
	ActivityManager am = null;
	ConnectivityManager cm = null;
	TelephonyManager tm = null;
	// 信息提取
	String pid = "";// 多个pid加入到一个字符串中
	int pidNum = 0;// pid计数,每次进入清零
//	String add_pid;
//	String add_pid_num;
	int uid = 0;
	String RSRP = "";
	String RSRQ = "";
	String RSSNR = "";// 新@@@@@@@@@
	String LaunTime;
	String exitTime;
	String excepTime1;// 第一次异常时间点
	String excepTime2;// 第二次异常时间点
	String AppName;
	String add_uid;
	String netType;
	
	boolean isAbnormal = false;// 异常标志
	boolean isAbnormal2 = false;// 异常标志

	@Override
	public void onCreate()
	{
		super.onCreate();
		cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

		/******************* 信号强度监听 **********************/
		PhoneStateListener MyPhoneListener = new PhoneStateListener()
		{
			public void onSignalStrengthsChanged(SignalStrength signalStrength)
			{
				try
				{
					String singalInformation = signalStrength.toString();
					String parts[] = singalInformation.split(" ");
					// 手机品牌判断,根据不同品牌提取参数
					if (android.os.Build.BRAND.toUpperCase().equals("HUAWEI"))
					{
						RSRP = parts[11];
						RSRQ = parts[12];

						if (ExtraUtil.isBigDecimal(String.format("%.5f", Math.log10(Double.valueOf(parts[13])))))
						{
							RSSNR = String.format("%.5f", Math.log10(Double.valueOf(parts[13])));
						}
					} else if (android.os.Build.BRAND.toUpperCase().equals("HONOR"))
					{
						RSRP = parts[8];
						RSRQ = parts[9];
						if (ExtraUtil.isBigDecimal(String.format("%.5f", Math.log10(Double.valueOf(parts[10])))))
						{
							RSSNR = String.format("%.5f", Math.log10(Double.valueOf(parts[10])));
						}
					} else
					{
						RSRP = parts[9];
						RSRQ = parts[10];
						if (ExtraUtil.isBigDecimal(String.format("%.5f", Math.log10(Double.valueOf(parts[11])))))
						{
							RSSNR = String.format("%.5f", Math.log10(Double.valueOf(parts[11])));
						}
					}

				} catch (Exception e)
				{
					// e.printStackTrace();
					// Log.e("BBB", "信号强度监视有问题");
				}
			}
		};
		tm.listen(MyPhoneListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
		/******************* 信号强度监听 **********************/

		new Timer().schedule(new TimerTask()
		{
			@Override
			public void run()
			{
				upload_time++;
				/******************** 自动上传 ****************************/
				if (upload_time % 60 == 0)// 60秒上传一次
				{
					new Thread(new Runnable()
					{
						@Override
						public void run()
						{
							// TODO Auto-generated method stub

							synchronized ("")
							{
								List<Info> uploadList = new ArrayList<Info>();
								InfoDatabase infoDatabase = new InfoDatabase(getApplicationContext(), "AutoReprt.db",
										null, 1);// 创建数据库 “AutoReport”
								DatabaseOperator databaseOperator = new DatabaseOperator(infoDatabase);
								uploadList = databaseOperator.queryFromInfo();

								if (uploadList.size() != 0)// 有数据则上传
								{
									for (int i = 0; i < uploadList.size(); i++)
									{
										if (uploadList.get(i).getUpload_Flag() == 0)// 检测没上传过的
										{
											uploadList.get(i).setUploadTime(ExtraUtil.getCurTime());// 每次上传提取时间
											if (upload_data(uploadList.get(i)))
											{
												uploadList.get(i).setUpload_FlagOK();// 上传成功设置上传标志位为true
												Log.i("AAA", "60秒上传成功");
											} else
											{

												uploadList.get(i).setUploadTime("");// 清空上传时间
												uploadList.get(i).setUploadNumAdd();// 上传失败记录次数+1
												Log.i("AAA", "60秒上传失败");
											}
										}
										// 将改变的这条Info信息同步到数据库, 根据id更新
										databaseOperator.updateFromInfo(uploadList.get(i), uploadList.get(i).getId());

									}
								}

								databaseOperator.CloseDatabase();// 关闭数据库
							}
						}
					}).start();

				}

				/******************** 自动上传 ****************************/

				if (getNetWorkType())// 有网络情况下再进行如下操作
				{
					// 对浏览器状态监视
					if (AppList.FindAppName(getAppName()) != null)// 查找当前应用是否在Applist
					{
						// 第一次进入应用获取pid与uid

						Browserun = true;
						if (assit == false)// 进入应用的时刻
						{
							pid = "";// 每次进入清零
							pidNum = 0;// 每次进入清零

							LaunTime = ExtraUtil.getCurTime();
							AppName = AppList.CurAppName;
							add_uid = String.valueOf(uid);

							getpid();// 根据uid获取pid
//							add_pid = pid;
//							add_pid_num = String.valueOf(pidNum);

							// handler.sendEmptyMessage(2);
							Log.i("AAA", "应用启动");
						}
					} else
					{
						Browserun = false;
					}
					if (Browserun == false && assit == true)
					{
						Browserquit = true;
						Log.i("AAA", "应用退出");
						exitTime = ExtraUtil.getCurTime();
						// handler.sendEmptyMessage(3);
					} else
					{
						Browserquit = false;

					}

					assit = Browserun;

					if (Browserun)
					{
						// 流量统计
						count++;

						// 如果返回-1，代表不支持使用该方法，注意必须是2.2以上的 接收RX
						long rx = TrafficStats.getUidRxBytes(uid);
						long drx = rx - rx1;
						rx1 = rx;
						// 如果返回-1，代表不支持使用该方法，注意必须是2.2以上的 发送TX
						long tx = TrafficStats.getUidTxBytes(uid);
						long dtx = tx - tx1;
						tx1 = tx;

						if (count == 1)
						{
							drx = 0;
							dtx = 0;
						}

						if (count < 31) // 30个数据
						{
							rxqueue_laun.insert(drx);
							txqueue_laun.insert(dtx);
						}

						rxqueue_exit.insert(drx);
						txqueue_exit.insert(dtx);

						if (count == 30)// 30秒进行第一个 判断
						{
							rxqueue_laun.calculate_expectation();// 计算期望rx
							rxqueue_laun.calculate_variance();// 计算方差tx

							txqueue_laun.calculate_expectation();// 计算期望rx
							txqueue_laun.calculate_variance();// 计算方差tx

							if (rxqueue_laun.get_maxValue() < 10000)// 异常判决
							{

								Log.i("AAA", "可疑异常出现");
								Log.i("AAA", "开始http测试");
								if (!upload_data(new Info()))// http测试不成功
								{
									excepTime1 = ExtraUtil.getCurTime();
									Log.i("AAA", "第一次异常时间" + excepTime1);
									isAbnormal = true;
									Log.i("AAA", "异常出现");
								} else
								{
									Log.i("AAA", "不是异常");

								}
				
							} else
							{
								Log.i("AAA", "初次判决不是异常");
							}
						}

					} else if (Browserquit)// 浏览器退出时候进行判断，并清空两个队列，以及一些全局变量
					{
						if (count > 4 && count < 30)// 5~30秒退出的情况
						{
							rxqueue_laun.calculate_expectation();// 计算期望rx
							rxqueue_laun.calculate_variance();// 计算方差tx

							txqueue_laun.calculate_expectation();// 计算期望rx
							txqueue_laun.calculate_variance();// 计算方差tx

							Log.i("AAA", "30秒内最大值" + rxqueue_laun.get_maxValue());

							if (rxqueue_laun.get_maxValue() < 10000)// 异常判决
							{
								excepTime1 = ExtraUtil.getCurTime();
								Log.i("AAA", "第一次异常时间" + excepTime1);
								isAbnormal = true;
								Log.i("AAA", "30秒内异常出现");

							} else
							{
								Log.i("AAA", "30秒内判决不是异常");
							}
						}

						if (count > 35)// 进行第二次 测试
						{
							if (rxqueue_exit.get_maxValue() < 10000)
							{
								Log.i("AAA", "开始http测试");
								if (!upload_data(new Info()))// http测试不成功
								{
									excepTime2 = ExtraUtil.getCurTime();
									Log.i("AAA", "第二次异常时间" + excepTime1);
									isAbnormal2 = true;
									Log.i("AAA", "第二次异常出现");
								} else
								{
									Log.i("AAA", "第二次不是异常");

								}
							}
							if (isAbnormal2)
							{
								getInfo(false);// 参数true 为 第二次 异常，

							}
						}
						if (isAbnormal)// 第一次判决异常
						{
							getInfo(true);// 参数true为 第一次 异常，
						}

						// 数据清零
						rx1 = 0;
						tx1 = 0;
						count = 0;

						rxqueue_laun.clear();
						txqueue_laun.clear();
						txqueue_exit.clear();
						rxqueue_exit.clear();
						// max_rx = 0;
						isAbnormal = false;
						isAbnormal2 = false;
						excepTime1 = "";
						excepTime2 = "";

					}

				} else
				// 没网络情况把参数重置
				{
					// 数据清零
					rx1 = 0;
					tx1 = 0;
					count = 0;

					rxqueue_laun.clear();
					txqueue_laun.clear();
					txqueue_exit.clear();
					rxqueue_exit.clear();
					isAbnormal = false;
					isAbnormal2 = false;
					excepTime1 = "";
					excepTime2 = "";
				}
			}
		}, 0, 1000);
	}

	public boolean upload_data(Info info)
	{
		// String urlStr = "http://10.1.0.222:8080/androidweb/LoginServlet";
		String urlStr = "http://www.mengqi.win/LoginServlet";

		HttpPost request = new HttpPost(urlStr);
		BasicHttpParams httpParams = new BasicHttpParams();
		// 设置请求超时
		int timeoutConnection = 1000;// 800
		HttpConnectionParams.setConnectionTimeout(httpParams, timeoutConnection);
		// // 设置响应超时
		int timeoutSocket = 1000; // 500临界点
		HttpConnectionParams.setSoTimeout(httpParams, timeoutSocket);

		List<NameValuePair> params = new ArrayList<NameValuePair>();

		// 上传信息加入
		params.add(new BasicNameValuePair("launtime", info.getLaunTime()));
		params.add(new BasicNameValuePair("exittime", info.getExitTime()));
		params.add(new BasicNameValuePair("usetime", info.getUseTime()));

		params.add(new BasicNameValuePair("excepTime", info.getExcepTime()));
		params.add(new BasicNameValuePair("uploadNum", String.valueOf(info.getUploadNum())));
		params.add(new BasicNameValuePair("uploadTime", info.getUploadTime()));

		params.add(new BasicNameValuePair("brand", info.getBrand()));
		params.add(new BasicNameValuePair("type", info.getType()));
		params.add(new BasicNameValuePair("version", info.getVersion()));
		params.add(new BasicNameValuePair("IMEI", info.getIMEI()));
		params.add(new BasicNameValuePair("IMSI", info.getIMSI()));
		params.add(new BasicNameValuePair("corporation", info.getCorporation()));
		params.add(new BasicNameValuePair("LAC", info.getLAC_GSM()));
		params.add(new BasicNameValuePair("Cell_Id", info.getCell_Id_GSM()));
		params.add(new BasicNameValuePair("RSRP", info.getRSRP()));
		params.add(new BasicNameValuePair("PCI", info.getPCI()));
		params.add(new BasicNameValuePair("CI", info.getCI()));
		params.add(new BasicNameValuePair("ENODBID", info.getENODBID()));
		params.add(new BasicNameValuePair("CELLID", info.getCELLID()));
		params.add(new BasicNameValuePair("TAC", info.getTAC()));
		params.add(new BasicNameValuePair("RSRQ", info.getRSRQ()));
		params.add(new BasicNameValuePair("cpuRate", info.getCpuRate()));
		params.add(new BasicNameValuePair("localIp", info.getLocalIp()));
		params.add(new BasicNameValuePair("AppName", info.getAppName()));
		params.add(new BasicNameValuePair("uid", info.getUid()));
		params.add(new BasicNameValuePair("pid", info.getPid()));
		params.add(new BasicNameValuePair("gid", info.getGid()));
		params.add(new BasicNameValuePair("pidNumber", info.getPidNumber()));
		params.add(new BasicNameValuePair("MemRate", info.getMemRate()));
		params.add(new BasicNameValuePair("TxByte", info.getTxByte()));
		params.add(new BasicNameValuePair("RxByte", info.getRxByte()));
		params.add(new BasicNameValuePair("NetType", info.getNetType()));
		params.add(new BasicNameValuePair("RSSNR", info.getRSSNR()));
		params.add(new BasicNameValuePair("Flag", info.getFlag()));
		try
		{
			request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpClient httpclient = new DefaultHttpClient(httpParams);

			HttpResponse response = httpclient.execute(request);

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
			{
				Log.i("AAA", "上传成功");
				return true;

			} else
			{
				Log.i("AAA", "上传失败");
				return false;

			}
		} catch (ConnectTimeoutException e)
		{
			Log.i("AAA", "连接超时");
			return false;
		} catch (SocketTimeoutException e)
		{
			// TODO: handle exception
			Log.i("AAA", "响应超时");
			return false;
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
			Log.i("AAA", "上传失败");
			return false;
		}
	}

	/*************** 获取手机运营商名字 ************************/
	public String getProvidersName(TelephonyManager telephonyManager)
	{
		String ProvidersName = null;
		telephonyManager = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
		String IMSI; // 返回唯一的用户ID;就是这张卡的编号神马的
		IMSI = telephonyManager.getSubscriberId();
		if (IMSI == null)
			return "unkwon";
		// IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。其中
		if (IMSI.startsWith("46000") || IMSI.startsWith("46002"))
		{
			ProvidersName = "中国移动";
		} else if (IMSI.startsWith("46001"))
		{
			ProvidersName = "中国联通";
		} else if (IMSI.startsWith("46003"))
		{
			ProvidersName = "中国电信";
		}
		return ProvidersName;
	}

	// 判决是否是LTE
	public boolean getNetWorkType()// 移动网络返回true
	{
		/*********** 对网络类型监视 ***************/
		String OPname = getProvidersName(tm);
		if (OPname.equals("中国移动"))
		{
			NetworkInfo networkInfo = cm.getActiveNetworkInfo();

			if (networkInfo != null)
			{
				if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE)
				{
					netType = networkInfo.getSubtypeName();
					// Log.i("AAA", "网络类型" + netType);
					return true;
				} else
				{
					// Log.i("AAA", "不是移动数据，WIFI");
					netType = null;
					return false;
				}

			} else
			{
				// Log.i("AAA", "没有网络");

				netType = null;
				return false;
				// return false;
			}
		} else
		{
			// Log.i("AAA", "不是CMCC," + OPname);
			return false;
		}
		/*********** 对网络类型监视 ***************/

	}

	/*********** 信息提取，记录到数据库 ***************/
	public void getInfo(boolean judge)// 获取信息
	{
		Info updateinfo = new Info();
		GsmCellLocation location = (GsmCellLocation) tm.getCellLocation();// *#*#4636#*#*
		/******************** 4G位置信息 ***********************/
		List<CellInfo> cellInfoList = tm.getAllCellInfo();

		int index = 0;
		for (CellInfo cellInfo : cellInfoList)
		{
			// 获取所有Lte网络信息
			if (cellInfo instanceof CellInfoLte)
			{

				if (cellInfo.isRegistered())
				{

					updateinfo.setPCI(String.valueOf(((CellInfoLte) cellInfo).getCellIdentity().getPci()));
					updateinfo.setCI(String.valueOf(((CellInfoLte) cellInfo).getCellIdentity().getCi()));
					updateinfo.setENODBID(String.valueOf(((CellInfoLte) cellInfo).getCellIdentity().getCi() / 256));
					updateinfo.setCELLID(String.valueOf(((CellInfoLte) cellInfo).getCellIdentity().getCi() % 256));
					updateinfo.setTAC(String.valueOf(((CellInfoLte) cellInfo).getCellIdentity().getTac()));
					//
				}

			}

			index++;
		}

		/******************** 4G位置信息 ***********************/
		updateinfo.setLaunTime(LaunTime);
		updateinfo.setAppName(AppName);
		updateinfo.setUid(add_uid);
		updateinfo.setGid(add_uid);// gid与uid相同
		updateinfo.setLocalIp(ExtraUtil.getlocalIP());// 获取本机IP地址
		updateinfo.setBrand(android.os.Build.BRAND);
		updateinfo.setType(android.os.Build.MODEL);
		updateinfo.setVersion(android.os.Build.VERSION.RELEASE);
		updateinfo.setIMEI(tm.getDeviceId());
		updateinfo.setIMSI(tm.getSubscriberId());
		updateinfo.setCorporation(tm.getSimOperatorName());
		updateinfo.setLAC_GSM(String.valueOf(location.getLac()));
		updateinfo.setCell_Id_GSM(String.valueOf(location.getCid()));
		if (netType.equals("LTE"))
		{
			updateinfo.setRSRP(RSRP);
			updateinfo.setRSRQ(RSRQ);
			updateinfo.setRSSNR(RSSNR);

		} else
		{
			updateinfo.setRSRP("N/A");
			updateinfo.setRSRQ("N/A");
			updateinfo.setRSSNR("N/A");

		}
		updateinfo.setMemRate(ExtraUtil.getMemRate());// 内存占用率
		updateinfo.setNetType(netType);// 网络类型
		updateinfo.setCpuRate(ExtraUtil.getCpuRate());
		updateinfo.setExitTime(exitTime);
		updateinfo.setUseTime(String.valueOf(count));

		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP)
		{
			updateinfo.setPid("N/A");
			updateinfo.setPidNumber("N/A");
		} else
		{
			updateinfo.setPid(pid);
			updateinfo.setPidNumber(String.valueOf(pidNum));
		}

		if (judge) // true 第一次异常参数
		{
			Log.i("AAA", "第一次记录异常时间" + excepTime1);
			updateinfo.setExcepTime(excepTime1);
			updateinfo.setTxByte(txqueue_laun.get_data());// 设置发送字节数据
			updateinfo.setRxByte(rxqueue_laun.get_data());// 接收字节量
		} else // false 第二次异常参数
		{
			Log.i("AAA", "第二次记录异常时间" + excepTime2);
			updateinfo.setExcepTime(excepTime2);
			updateinfo.setTxByte(txqueue_exit.get_data());// 设置发送字节数据
			updateinfo.setRxByte(rxqueue_exit.get_data());// 接收字节量
		}
		updateinfo.setFlagOK();// 设置为异常数据
		InfoDatabase infoDatabase = new InfoDatabase(this, "AutoReprt.db", null, 1);// 创建数据库“AutoReport”
		DatabaseOperator databaseOperator = new DatabaseOperator(infoDatabase);
		databaseOperator.insertToInfo(updateinfo);// 将这条信息插入到数据库
		databaseOperator.CloseDatabase();// 关闭数据库
		Log.i("AAA", "异常信息加入成功");

	}

	/********************* binder *****************/
	public class UpdateBinder extends Binder
	{
		public void sendUpdateBro()// 一旦接收到活动的启动，发送更新广播
		{
			Intent intent = new Intent("UPDATE_ACTION");
			sendBroadcast(intent);
		}

	}

	private UpdateBinder myUpdateBinder = new UpdateBinder();

	@Override
	public IBinder onBind(Intent intent)
	{
		return myUpdateBinder;
	}

	/********************* binder *****************/

	Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			int what = msg.what;
			switch (what)
			{
			case 1:
				showToast(1);
				break;
			case 2:
				showToast(2);
				break;
			case 3:
				showToast(3);
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
	};

	public void showToast(int i)
	{
		Toast toast;

		switch (i)
		{
		case 1:
			toast = Toast.makeText(getApplicationContext(), "异常出现", Toast.LENGTH_LONG);
			toast.show();
			break;
		case 2:
			toast = Toast.makeText(getApplicationContext(), "应用启动", Toast.LENGTH_LONG);
			toast.show();
			break;
		case 3:
			toast = Toast.makeText(getApplicationContext(), "应用退出", Toast.LENGTH_SHORT);
			toast.show();
			break;

		default:
			break;
		}

	}

	/*************** 获取最顶层程序包名 **********************************/
	private String getTaskPackname()
	{
		String currentApp = null;
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP)
		{
			if (!UStats.getUsageStatsList(this).isEmpty())
			{
				@SuppressWarnings("ResourceType")
				UsageStatsManager usm = (UsageStatsManager) this.getSystemService("usagestats");
				long time = System.currentTimeMillis();
				List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 1000,
						time);
				if (appList != null && appList.size() > 0)
				{
					SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
					for (UsageStats usageStats : appList)
					{
						mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
					}
					if (mySortedMap != null && !mySortedMap.isEmpty())
					{
						currentApp = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
					}
				}
			}
		} else
		{

			RunningTaskInfo info1 = am.getRunningTasks(1).get(0);
			currentApp = info1.topActivity.getPackageName();
		}
		// Log.i("TAG", "Current App in foreground is: " + currentApp);
		return currentApp;
	}

	/*************** 获取当前应用名称 **********************************/
	public String getAppName()
	{
		ApplicationInfo appinfo = null;
		PackageManager pkgmanager = null;
		// 包名
		String appname = "";
		pkgname = getTaskPackname();
		// Log.i("AAA", "包名："+pkgname);
		if (pkgname != null)
		{
			try
			{
				pkgmanager = (PackageManager) getApplicationContext().getPackageManager();
				appinfo = pkgmanager.getApplicationInfo(pkgname, 0);
				uid = appinfo.uid;
			} catch (PackageManager.NameNotFoundException e)

			{
				appinfo = null;
				// TODO: handle exception
			}

			appname = (String) pkgmanager.getApplicationLabel(appinfo);
			// Log.i("AAA", appname);
		}
		return appname;
	}

	public void getpid()
	{
		// 获取所有正在运行的app
		List<RunningAppProcessInfo> appProcesses = am.getRunningAppProcesses();
		// 遍历app，获取应用名称或者包名
		for (RunningAppProcessInfo appProcess : appProcesses)
		{
			if (appProcess.uid == uid)// 根据uid获取pid
			{
				if (appProcess != appProcesses.get(appProcesses.size() - 1))
				{
					pid += String.valueOf(appProcess.pid) + ",";
					pidNum++;
					// Log.i("AAA", "pid:" + pid);
				} else
				{
					pid += String.valueOf(appProcess.pid);
					pidNum++;
				}
			}
		}

	}

}