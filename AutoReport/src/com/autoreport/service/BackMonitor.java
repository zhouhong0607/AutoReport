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
import java.math.BigInteger;
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
import org.apache.http.util.EntityUtils;

import com.autoreport.database.DatabaseOperator;
import com.autoreport.database.InfoDatabase;
import com.autoreport.datamodel.AppList;
import com.autoreport.datamodel.AutoreportApp;
import com.autoreport.datamodel.BaseInfo;
import com.autoreport.datamodel.SignalInfo;
import com.autoreport.datamodel.SignalQueue;
import com.autoreport.util.ExtraUtil;
import com.autoreport.util.FileUtil;
import com.autoreport.util.UStats;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.location.LocationClientOption.LocationMode;

import android.content.Context;
import android.content.Entity;
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

/**
 * 后台监控服务
 * 
 * @author 周宏
 *
 */
public class BackMonitor extends Service
{
	private static final String EXP_TYPE1 = "下行流量峰值过低";
	private static final String EXP_TYPE2 = "下行流量峰值过低且响应超时";
	private static final String EXP_TYPE3 = "未通过通信测试且响应超时";

	/** 文件操作 **/
	private String filePath = "/sdcard/Test/";
	// 文件1
	private StringBuilder fileRecord1 = new StringBuilder();
	private String fileName1 = "record1.txt";
	private String[] records1 = new String[17];
	// 文件2
	private StringBuilder fileRecord2 = new StringBuilder();
	private String fileName2 = "record2.txt";
	private String[] records2 = new String[14];

	/** 文件操作 **/

	long tx1 = 0;
	long rx1 = 0;
	long drx = 0;
	long dtx = 0;

	boolean Browserun = false;// 浏览器运行判断
	boolean assit = false;// 辅助判断参数
	boolean Browserquit = false;// 浏览器退出判断
	SignalQueue launQue = new SignalQueue(30);// 构建发送接受队列，时间长度10秒
	// SignalQueue rxqueue_laun = new SignalQueue(30);// 0.1秒间隔 ，20秒的数据
	SignalQueue exitQue = new SignalQueue(10);// 构建发送接受队列，时间长度10秒
	// SignalQueue rxqueue_exit = new SignalQueue(10);// 0.1秒间隔 ，20秒的数据
	int count = 0;// 应用运行时间记录
	int upload_time = 0;// 周期上传时间
	String pkgname = "";

	TelephonyManager tm = null;
	// 信息提取
	String[] pidInfo = new String[2];

	int uid = 0;
	String RSRP = "";
	String RSRQ = "";
	String RSSNR = "";// 新@@@@@@@@@
	String LaunTime = "";
	String exitTime = "";
	String excepTime1 = "";// 第一次异常时间点
	String excepTime2 = "";// 第二次异常时间点
	String AppName = "";

	String netType = "";

	boolean isAbnormal = false;// 异常标志
	boolean isAbnormal2 = false;// 异常标志

	boolean excepTing = false;// 动态异常标志
	int excepCount = 0;// 动态异常计数

	String longitude = "";// 经度
	String latitude = "";// 纬度
	String addr = "";// 地址

	int excepType;// 异常类型 取值 1 2 3；
	// int maxIndex=-1;//下行流量最大值索引
	// int noRxIndex=-1;//未通过通信测试索引

	public LocationClient mLocationClient;
	public BDLocationListener myListener;

	@Override
	public void onCreate()
	{
		super.onCreate();

		tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

		mLocationClient = new LocationClient(getApplicationContext());
		initLocation();
		myListener = new MyLocationListener();
		mLocationClient.registerLocationListener(myListener);
		mLocationClient.start();

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

							RSSNR = String.format("%.0f", 10.0*Math.log10(Double.valueOf(parts[13])));

						}
					} else if (android.os.Build.BRAND.toUpperCase().equals("HONOR"))
					{
						RSRP = parts[8];
						RSRQ = parts[9];
						if (ExtraUtil.isBigDecimal(String.format("%.5f", Math.log10(Double.valueOf(parts[10])))))
						{
							RSSNR = String.format("%.0f",  10.0*Math.log10(Double.valueOf(parts[10])));
						}
					} else
					{
						RSRP = parts[9];
						RSRQ = parts[10];
						if (ExtraUtil.isBigDecimal(String.format("%.5f", Math.log10(Double.valueOf(parts[11])))))
						{
							RSSNR = String.format("%.0f", 10.0* Math.log10(Double.valueOf(parts[11])));
						}
					}

				} catch (Exception e)
				{
					// e.printStackTrace();
					Log.e("BBB", "信号强度监视有问题");

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
				if (upload_time % 60 == 0 && isConnected())// 60秒上传一次
				{
					new Thread(new Runnable()
					{
						@Override
						public void run()
						{
							// TODO Auto-generated method stub

							synchronized ("")
							{
								List<BaseInfo> uploadList = new ArrayList<BaseInfo>();
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
					if (AppList.FindAppName(ExtraUtil.getAppName(ExtraUtil.getCurPackname())) != null)// 查找当前应用是否在Applist
					{
						// 第一次进入应用获取pid与uid

						Browserun = true;
						if (assit == false)// 进入应用的时刻
						{
							LaunTime = ExtraUtil.getCurTime();
							AppName = AppList.CurAppName;
							uid = ExtraUtil.getUid(ExtraUtil.getCurPackname());
							pidInfo = ExtraUtil.getPidInfo(uid);// 根据uid获取pid,由于pid提取依赖uid，所以uid提取应该在pid之前,返回的pidInfo是String[2],第一项是pid信息，第二项是pidNum

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
						/** 文件记录部分 **/
						records1[0] = ExtraUtil.getCurTime();
						records1[1] = ExtraUtil.getMemRate();
						records1[2] = ExtraUtil.getCpuRate();

						/** 文件记录部分 **/

						// 流量统计
						count++;

						// 如果返回-1，代表不支持使用该方法，注意必须是2.2以上的 接收RX
					
					
//						long rx = TrafficStats.getMobileRxBytes();
						long rx = TrafficStats.getUidRxBytes(uid);
						drx = rx - rx1;
						rx1 = rx;
						// 如果返回-1，代表不支持使用该方法，注意必须是2.2以上的 发送TX
						
						
//						long tx = TrafficStats.getMobileTxBytes();
						long tx = TrafficStats.getUidTxBytes(uid);
						dtx = tx - tx1;
						tx1 = tx;

						if (count == 1)
						{
							drx = 0;
							dtx = 0;
						}

						SignalInfo signalInfo = getSignalInfo();

						if (count < 31) // 30个数据
						{
							launQue.insert(signalInfo);

						}

						exitQue.insert(signalInfo);

						if (count == 30)// 30秒进行第一个 判断
						{
							// rxqueue_laun.calculate_expectation();// 计算期望rx
							// rxqueue_laun.calculate_variance();// 计算方差tx
							//
							// txqueue_laun.calculate_expectation();// 计算期望rx
							// txqueue_laun.calculate_variance();// 计算方差tx

							if (launQue.get_sum() > 0 && launQue.get_maxValue() < 10000)// 异常判决
							{

								Log.i("AAA", "可疑异常出现");
								Log.i("AAA", "开始http测试");
								if (!upload_data(new BaseInfo()))// http测试不成功
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
							// rxqueue_laun.calculate_expectation();// 计算期望rx
							// rxqueue_laun.calculate_variance();// 计算方差tx
							//
							// txqueue_laun.calculate_expectation();// 计算期望rx
							// txqueue_laun.calculate_variance();// 计算方差tx

							Log.i("AAA", "30秒内最大值" + launQue.get_maxValue());

							//launQue.get_sum() > 0 &&
							if ( launQue.get_maxValue() < 10000)// 异常判决
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
							if (exitQue.get_sum() > 0 && (exitQue.get_maxValue() < 10000 || exitQue.judege()))
							{
								Log.i("AAA", "开始http测试");
								if (!upload_data(new BaseInfo()))// http测试不成功
								{
									excepTime2 = ExtraUtil.getCurTime();
									Log.i("AAA", "第二次异常时间" + excepTime2);
									isAbnormal2 = true;
									Log.i("AAA", "第二次异常出现");
								} else
								{
									Log.i("AAA", "第二次不是异常");

								}
							}
							if (isAbnormal2)
							{
								if (exitQue.judege())
								{
									excepType = 3;
								} else
								{
									excepType = 2;
								}
								recordInfo(false);// 参数true 为 第二次 异常，
							}
						}
						if (isAbnormal)// 第一次判决异常
						{
							excepType = 1;
							recordInfo(true);// 参数true为 第一次 异常，
						}

						// 数据清零
						rx1 = 0;
						tx1 = 0;

						count = 0;
						launQue.clear();
						exitQue.clear();

						isAbnormal = false;
						isAbnormal2 = false;

						/** 记录数据到文件， 清空StringBuilder **/

						FileUtil.writeTxtToFile(fileRecord1.toString(), filePath, fileName1);
						fileRecord1.delete(0, fileRecord1.length());

						file2Record();

						/** 记录数据到文件， 清空StringBuilder **/

					}

				} else
				// 没网络情况把参数重置
				{
					// 数据清零
					rx1 = 0;
					tx1 = 0;
					count = 0;
					launQue.clear();
					exitQue.clear();
					isAbnormal = false;
					isAbnormal2 = false;

				}
			}
		}, 0, 1000);
	}

	private void initLocation()
	{
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系
		int span = 1000;
		option.setScanSpan(span);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
		option.setOpenGps(true);// 可选，默认false,设置是否使用gps
		option.setLocationNotify(true);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
		option.setIsNeedLocationDescribe(true);// 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
		option.setIsNeedLocationPoiList(true);// 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
		option.setIgnoreKillProcess(false);// 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
		option.SetIgnoreCacheException(false);// 可选，默认false，设置是否收集CRASH信息，默认收集
		option.setEnableSimulateGps(false);// 可选，默认false，设置是否需要过滤gps仿真结果，默认需要
		mLocationClient.setLocOption(option);
	}

	private void excep()
	{
		Log.i("AAA", "运行时异常");
	}

	private SignalInfo getSignalInfo()
	{
		SignalInfo signalInfo = new SignalInfo();

		if (netType.equals("LTE"))
		{
			signalInfo.setRsrp(RSRP);
			signalInfo.setRsrq(RSRQ);
			signalInfo.setRssinr(RSSNR);

			/** 文件记录部分 **/
			records1[3] = RSRP;
			records1[4] = RSRQ;
			records1[5] = RSSNR;
			/** 文件记录部分 **/

		} else
		{
			signalInfo.setRsrp("N/A");
			signalInfo.setRsrq("N/A");
			signalInfo.setRssinr("N/A");

			/** 文件记录部分 **/
			records1[3] = "N/A";
			records1[4] = "N/A";
			records1[5] = "N/A";

			/** 文件记录部分 **/

		}

		signalInfo.setTxByte(String.valueOf(dtx));
		signalInfo.setRxByte(String.valueOf(drx));
		signalInfo.setNetType(netType);

		/** 文件记录部分 **/
		records1[6] = String.valueOf(dtx);
		records1[7] = String.valueOf(drx);
		records1[8] = netType;

		/** 文件记录部分 **/

		/******************** 4G位置信息 ***********************/
		List<CellInfo> cellInfoList = tm.getAllCellInfo();

		if (cellInfoList == null || cellInfoList.size() == 0)
		{
			GsmCellLocation location = (GsmCellLocation) tm.getCellLocation();// *#*#4636#*#*
			signalInfo.setPci("null");
			signalInfo.setCi(String.valueOf(location.getCid()));
			signalInfo.setEnodbId(String.valueOf((location.getCid() / 256)));
			signalInfo.setCellId(String.valueOf((location.getCid() % 256)));
			signalInfo.setTac(String.valueOf(location.getLac()));

			/** 文件记录部分 **/
			records1[9] = "null";
			records1[10] = String.valueOf(location.getCid());
			records1[11] = String.valueOf((location.getCid() / 256));
			records1[12] = String.valueOf((location.getCid() % 256));
			records1[13] = String.valueOf(location.getLac());

			/** 文件记录部分 **/
		} else
		{
			for (CellInfo cellInfo : cellInfoList)
			{
				// 获取所有Lte网络信息
				if (cellInfo instanceof CellInfoLte)
				{

					if (cellInfo.isRegistered())
					{

						if (netType.equals("LTE"))
						{
							signalInfo.setPci(String.valueOf(((CellInfoLte) cellInfo).getCellIdentity().getPci()));
							signalInfo.setCi(String.valueOf(((CellInfoLte) cellInfo).getCellIdentity().getCi()));
							signalInfo.setEnodbId(
									String.valueOf(((CellInfoLte) cellInfo).getCellIdentity().getCi() / 256));
							signalInfo.setCellId(
									String.valueOf(((CellInfoLte) cellInfo).getCellIdentity().getCi() % 256));
							signalInfo.setTac(String.valueOf(((CellInfoLte) cellInfo).getCellIdentity().getTac()));

							/** 文件记录部分 **/

							records1[9] = String.valueOf(((CellInfoLte) cellInfo).getCellIdentity().getPci());
							records1[10] = String.valueOf(((CellInfoLte) cellInfo).getCellIdentity().getCi());
							records1[11] = String.valueOf(((CellInfoLte) cellInfo).getCellIdentity().getCi() / 256);
							records1[12] = String.valueOf(((CellInfoLte) cellInfo).getCellIdentity().getCi() % 256);
							records1[13] = String.valueOf(((CellInfoLte) cellInfo).getCellIdentity().getTac());

							/** 文件记录部分 **/

						} else
						{
							signalInfo.setPci("N/A");
							signalInfo.setCi("N/A");
							signalInfo.setEnodbId("N/A");
							signalInfo.setCellId("N/A");
							signalInfo.setTac("N/A");

							/** 文件记录部分 **/
							records1[9] = "N/A";
							records1[10] = "N/A";
							records1[11] = "N/A";
							records1[12] = "N/A";
							records1[13] = "N/A";

							/** 文件记录部分 **/

						}
					}

				}

			}
		}

		/******************** 4G位置信息 ***********************/
		signalInfo.setTimeStamp(ExtraUtil.getCurTime());

		// 设置经纬度信息
		signalInfo.setLongitude(longitude);
		signalInfo.setLatitude(latitude);
		signalInfo.setAddr(addr);

		/** 文件记录部分 **/
		records1[14] = longitude;
		records1[15] = latitude;
		records1[16] = addr;

		fileRecord1.append(records1[0] + "\t");// 时间
		fileRecord1.append(records1[6] + "\t");// 发送字节
		fileRecord1.append(records1[7] + "\t");// 接收字节
		fileRecord1.append(records1[3] + "\t");// RSRP
		fileRecord1.append(records1[4] + "\t");// RSRQ
		fileRecord1.append(records1[5] + "\t");// SINR
		fileRecord1.append(records1[9] + "\t");// PCI
		fileRecord1.append(records1[10] + "\t");// CI
		fileRecord1.append(records1[11] + "\t");// ENODBID
		fileRecord1.append(records1[12] + "\t");// CELLID
		fileRecord1.append(records1[13] + "\t");// TAC
		fileRecord1.append(records1[8] + "\t");// 网络类型
		fileRecord1.append(records1[1] + "\t");// 内存
		fileRecord1.append(records1[2] + "\t");// CPU
		fileRecord1.append(records1[14] + "\t");// 经度
		fileRecord1.append(records1[15] + "\t");// 纬度
		fileRecord1.append(records1[16]);// 地址
		fileRecord1.append("\r\n");// 换行
		records1 = new String[17];

		/** 文件记录部分 **/

		return signalInfo;
	}

	public boolean upload_data(BaseInfo info)
	{
		// String urlStr = "http://10.1.0.222:8080/androidweb/LoginServlet";
		// String urlStr = "http://www.mengqi.win/InternalTesting/LoginServlet";
		String urlStr = "http://10.1.0.254:8080/AMonitor/app/common/uploaddata";

		HttpPost request = new HttpPost(urlStr);
		BasicHttpParams httpParams = new BasicHttpParams();
		// 设置请求超时
		int timeoutConnection = 2000;// 800
		HttpConnectionParams.setConnectionTimeout(httpParams, timeoutConnection);
		// // 设置响应超时
		int timeoutSocket = 3000; // 500临界点
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
		params.add(new BasicNameValuePair("cpuRate", info.getCpuRate()));
		params.add(new BasicNameValuePair("localIp", info.getLocalIp()));
		params.add(new BasicNameValuePair("AppName", info.getAppName()));
		params.add(new BasicNameValuePair("uid", info.getUid()));
		params.add(new BasicNameValuePair("pid", info.getPid()));
		params.add(new BasicNameValuePair("gid", info.getGid()));
		params.add(new BasicNameValuePair("pidNumber", info.getPidNumber()));
		params.add(new BasicNameValuePair("MemRate", info.getMemRate()));
		params.add(new BasicNameValuePair("Flag", info.getFlag()));

		InfoDatabase infoDatabase = new InfoDatabase(this, "AutoReprt.db", null, 1);// 创建数据库
																					// //
																					// “AutoReport”
		DatabaseOperator databaseOperator = new DatabaseOperator(infoDatabase);
		List<SignalInfo> signalInfos = databaseOperator.queryFromSignalInfoById(info.getId());
		databaseOperator.CloseDatabase();

		String siglist = "";
		if (signalInfos.size() != 0)
		{
			for (int i = 0; i < signalInfos.size(); i++)
			{
				siglist += signalInfos.get(i).getRsrp() + "," + signalInfos.get(i).getRsrq() + ","
						+ signalInfos.get(i).getRssinr() + "," + signalInfos.get(i).getTxByte() + ","
						+ signalInfos.get(i).getRxByte() + "," + signalInfos.get(i).getNetType() + ","
						+ signalInfos.get(i).getPci() + "," + signalInfos.get(i).getCi() + ","
						+ signalInfos.get(i).getEnodbId() + "," + signalInfos.get(i).getCellId() + ","
						+ signalInfos.get(i).getTac() + "," + signalInfos.get(i).getTimeStamp() + ","
						+ signalInfos.get(i).getLongitude() + "," + signalInfos.get(i).getLatitude() + ","
						+ signalInfos.get(i).getAddr() + "|";
				// if((i+1)!=signalInfos.size())
				// siglist+= "|";
			}
		}

		params.add(new BasicNameValuePair("excepType", info.getExcepType()));
		params.add(new BasicNameValuePair("maxIndex", String.valueOf(info.getMaxIndex())));
		params.add(new BasicNameValuePair("noRxIndex", String.valueOf(info.getNoRxIndex())));
		// params.add(new BasicNameValuePair("longitude",
		// signalInfos.get(0).getLongitude()));
		// params.add(new BasicNameValuePair("latutide",
		// signalInfos.get(0).getLatitude()));
		// params.add(new BasicNameValuePair("addr",
		// signalInfos.get(0).getAddr()));

		params.add(new BasicNameValuePair("signalInfo", siglist));

		try
		{
			request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpClient httpclient = new DefaultHttpClient(httpParams);

			HttpResponse response = httpclient.execute(request);

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)// EntityUtils.toString(response.getEntity()).equals("上传成功")
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
			e.printStackTrace();
			Log.i("AAA", "连接超时");
			return false;
		} catch (SocketTimeoutException e)
		{
			// TODO: handle exception
			e.printStackTrace();
			Log.i("AAA", "响应超时");
			return true;
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
			Log.i("AAA", "上传失败");
			return false;
		}
	}

	private boolean isConnected()
	{
		ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected())
		{
			return true;
		}
		return false;

	}

	// 判决是否是LTE
	public boolean getNetWorkType()// 移动网络返回true
	{

		// return true;
		// /*********** 对网络类型监视 ***************/
		String OPname = ExtraUtil.getProvidersName(tm.getSubscriberId());
		// 获得运营商,参数为IMSI
		if (OPname.equals("中国移动"))
		{
			ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
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
	public void recordInfo(boolean judge)// 获取信息
	{
		InfoDatabase infoDatabase = new InfoDatabase(this, "AutoReprt.db", null, 1);// 创建数据库“AutoReport”
		DatabaseOperator databaseOperator = new DatabaseOperator(infoDatabase);

		BaseInfo updateinfo = new BaseInfo();
		GsmCellLocation location = (GsmCellLocation) tm.getCellLocation();// *#*#4636#*#*

		updateinfo.setLaunTime(LaunTime);
		updateinfo.setAppName(AppName);
		updateinfo.setUid(String.valueOf(uid));
		updateinfo.setGid(String.valueOf(uid));// gid与uid相同
		updateinfo.setLocalIp(ExtraUtil.getlocalIP());// 获取本机IP地址
		updateinfo.setBrand(android.os.Build.BRAND);
		updateinfo.setType(android.os.Build.MODEL);
		updateinfo.setVersion(android.os.Build.VERSION.RELEASE);
		updateinfo.setIMEI(tm.getDeviceId());
		updateinfo.setIMSI(tm.getSubscriberId());
		updateinfo.setCorporation(tm.getSimOperatorName());
		updateinfo.setLAC_GSM(String.valueOf(location.getLac()));
		updateinfo.setCell_Id_GSM(location.getCid() > 65535 ? "N/A" : String.valueOf(location.getCid()));

		updateinfo.setMemRate(ExtraUtil.getMemRate());// 内存占用率

		updateinfo.setCpuRate(ExtraUtil.getCpuRate());
		updateinfo.setExitTime(exitTime);
		updateinfo.setUseTime(String.valueOf(count));

		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP)
		{
			updateinfo.setPid("N/A");
			updateinfo.setPidNumber("N/A");

		} else
		{
			updateinfo.setPid(pidInfo[0]);
			updateinfo.setPidNumber(String.valueOf(pidInfo[1]));

		}

		updateinfo.setFlagOK();// 设置为异常数据
		// 设置异常类型
		switch (excepType)
		{
		case 1:
			updateinfo.setExcepType(EXP_TYPE1);
			break;
		case 2:
			updateinfo.setExcepType(EXP_TYPE2);
			break;
		case 3:
			updateinfo.setExcepType(EXP_TYPE3);
			break;

		default:
			updateinfo.setExcepType("Nothing");
			break;
		}

		if (judge) // true 第一次异常参数
		{
			Log.i("AAA", "第一次记录异常时间" + excepTime1);
			updateinfo.setExcepTime(excepTime1);
			// 设置流量最大值索引和未通过通信测试索引 默认-1
			updateinfo.setMaxIndex(launQue.getMaxIndex());
			updateinfo.setNoRxIndex(launQue.getNoRxIndex());

			databaseOperator.insertToInfo(updateinfo);// 将这条信息插入到数据库

			launQue.setInfoId(updateinfo.getId());// 设置外键
			launQue.insertToDB(databaseOperator);// 将队列插入数据库

			databaseOperator.CloseDatabase();// 关闭数据库

		} else // false 第二次异常参数
		{
			Log.i("AAA", "第二次记录异常时间" + excepTime2);
			updateinfo.setExcepTime(excepTime2);
			// 设置流量最大值索引和未通过通信测试索引 默认-1
			updateinfo.setMaxIndex(exitQue.getMaxIndex());
			updateinfo.setNoRxIndex(exitQue.getNoRxIndex());

			databaseOperator.insertToInfo(updateinfo);// 将这条信息插入到数据库

			exitQue.setInfoId(updateinfo.getId());// 设置外键
			exitQue.insertToDB(databaseOperator);// 将队列插入数据库

			databaseOperator.CloseDatabase();// 关闭数据库

		}

		Log.i("AAA", "异常信息加入成功");

	}

	/*********** 文件2记录 ***************/
	public void file2Record()// 获取信息
	{

		GsmCellLocation location = (GsmCellLocation) tm.getCellLocation();// *#*#4636#*#*

		/** 文件记录2 **/
		records2[0] = android.os.Build.BRAND;// 手机品牌
		records2[1] = android.os.Build.MODEL;// 手机型号
		records2[2] = android.os.Build.VERSION.RELEASE;// android 版本
		records2[3] = ExtraUtil.getlocalIP();// 本机IP
		records2[4] = tm.getDeviceId();// IMEI
		records2[5] = tm.getSubscriberId();// IMSI
		records2[6] = tm.getSimOperatorName();// 运营商
		records2[7] = AppName;// 应用进程名称
		records2[8] = LaunTime;// 启动时间
		records2[9] = exitTime;// 退出时间
		records2[10] = String.valueOf(uid);// 用户ID（UID）
		records2[11] = String.valueOf(uid);// 组ID（GID）
		/** 文件记录2 **/

		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP)
		{

			/** 文件记录2 **/
			records2[12] = "N/A";// 进程ID（PID）
			records2[13] = "N/A";// 进程数
			/** 文件记录2 **/
		} else
		{

			/** 文件记录2 **/
			records2[12] = pidInfo[0];// 进程ID（PID）
			records2[13] = String.valueOf(pidInfo[1]);// 进程数
			/** 文件记录2 **/
		}

		/** 文件记录 **/
		fileRecord2.append(records2[0] + "\t");
		fileRecord2.append(records2[1] + "\t");
		fileRecord2.append(records2[2] + "\t");
		fileRecord2.append(records2[3] + "\t");
		fileRecord2.append(records2[4] + "\t");
		fileRecord2.append(records2[5] + "\t");
		fileRecord2.append(records2[6] + "\t");
		fileRecord2.append(records2[7] + "\t");
		fileRecord2.append(records2[8] + "\t");
		fileRecord2.append(records2[9] + "\t");
		fileRecord2.append(records2[10] + "\t");
		fileRecord2.append(records2[11] + "\t");
		fileRecord2.append(records2[12] + "\t");
		fileRecord2.append(records2[13]);
		fileRecord2.append("\r\n");
		FileUtil.writeTxtToFile(fileRecord2.toString(), filePath, fileName2);

		records2 = new String[14];
		fileRecord2.delete(0, fileRecord2.length());
		/** 文件记录 **/
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

	// 位置监听
	public class MyLocationListener implements BDLocationListener
	{

		@Override
		public void onReceiveLocation(BDLocation location)
		{
			// Receive Location
			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			sb.append("\nradius : ");
			sb.append(location.getRadius());

			// 经纬度
			longitude = String.valueOf(location.getLongitude());
			latitude = String.valueOf(location.getLatitude());

			if (location.getLocType() == BDLocation.TypeGpsLocation)
			{// GPS定位结果
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());// 单位：公里每小时
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
				sb.append("\nheight : ");
				sb.append(location.getAltitude());// 单位：米
				sb.append("\ndirection : ");
				sb.append(location.getDirection());// 单位度
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				// 位置
				addr = location.getAddrStr();

				sb.append("\ndescribe : ");
				sb.append("gps定位成功");

			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation)
			{// 网络定位结果
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());

				addr = location.getAddrStr();
				// 运营商信息
				sb.append("\noperationers : ");
				sb.append(location.getOperators());
				sb.append("\ndescribe : ");
				sb.append("网络定位成功");
			} else if (location.getLocType() == BDLocation.TypeOffLineLocation)
			{// 离线定位结果
				sb.append("\ndescribe : ");
				sb.append("离线定位成功，离线定位结果也是有效的");
			} else if (location.getLocType() == BDLocation.TypeServerError)
			{
				sb.append("\ndescribe : ");
				sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
			} else if (location.getLocType() == BDLocation.TypeNetWorkException)
			{
				sb.append("\ndescribe : ");
				sb.append("网络不同导致定位失败，请检查网络是否通畅");
			} else if (location.getLocType() == BDLocation.TypeCriteriaException)
			{
				sb.append("\ndescribe : ");
				sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
			}
			sb.append("\nlocationdescribe : ");
			sb.append(location.getLocationDescribe());// 位置语义化信息
			List<Poi> list = location.getPoiList();// POI数据
			if (list != null)
			{
				sb.append("\npoilist size = : ");
				sb.append(list.size());
				for (Poi p : list)
				{
					sb.append("\npoi= : ");
					sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
				}
			}

			// Log.i("AAA", sb.toString());
		}

	}

}
