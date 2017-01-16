package com.autoreport.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.SortedMap;
import java.util.TreeMap;

import com.autoreport.datamodel.AutoreportApp;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.util.Log;


/**
 * 提取工具类
 * @author 周宏
 *
 */
public class ExtraUtil 
{
	/****************提取本机IP地址Begin**********************/
	public static final String getlocalIP()
	{
		try
		{
			for (Enumeration<NetworkInterface> mEnumeration = NetworkInterface.getNetworkInterfaces(); mEnumeration
					.hasMoreElements();)
			{
				NetworkInterface intf = mEnumeration.nextElement();
				for (Enumeration<InetAddress> enumIPAddr = intf.getInetAddresses(); enumIPAddr.hasMoreElements();)
				{
					InetAddress inetAddress = enumIPAddr.nextElement();
					// 如果不是回环地址
					if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address)
					{
						// 直接返回本地IP地址
						// Log.i("AAA", "提取IP成功");
						return inetAddress.getHostAddress().toString();

					}
				}
			}
		} catch (SocketException ex)
		{
			Log.e("AAA", ex.toString());
		}
		return null;

	}
	/****************提取本机IP地址End**********************/
	
	
	
	/****************提取手机内存使用率Begin**********************/
	public static final  String getMemRate()
	{
		ActivityManager am = (ActivityManager)AutoreportApp.getContext().getSystemService(Activity.ACTIVITY_SERVICE);
		ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
		am.getMemoryInfo(mi);
		return String.valueOf((mi.totalMem - mi.availMem) * 100 / mi.totalMem) + "%";// 内存使用率
	}
	/****************提取手机内存使用率End**********************/

	/****************提取手机CPU使用率Begin**********************/
	public static final String getCpuRate()
	{ // 获取系统总CPU使用时间
		String[] cpuInfos = null;
		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("/proc/stat")), 1000);
			String load = reader.readLine();
			reader.close();
			cpuInfos = load.split(" ");
		} catch (IOException ex)
		{
			ex.printStackTrace();
		}

		long usedCpu = Long.parseLong(cpuInfos[2]) + Long.parseLong(cpuInfos[3]) + Long.parseLong(cpuInfos[4]);
		long totalCpu = Long.parseLong(cpuInfos[2]) + Long.parseLong(cpuInfos[3]) + Long.parseLong(cpuInfos[4])
				+ Long.parseLong(cpuInfos[5]);

		return String.valueOf(usedCpu * 100 / totalCpu) + "%";
	}
	/****************提取手机CPU使用率End**********************/
	
	
	
	/****************提取当前时间Begin***********************/
	public static final String getCurTime()
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String str = formatter.format(curDate);
		return str;
	}
	/****************提取当前时间End***********************/
	
	/*************** 获取当前程序包名 Begin**********************************/
	public static final String getCurPackname()
	{
		String currentAppPkg = "testPKG";
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP)
		{
			if (!UStats.getUsageStatsList(AutoreportApp.getContext()).isEmpty())
			{
				@SuppressWarnings("ResourceType")
				UsageStatsManager usm = (UsageStatsManager) AutoreportApp.getContext().getSystemService("usagestats");
				long time = System.currentTimeMillis();
				//设置  时间段  3600 *1000为1个小时   ，设置5小时
				List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 100*3600 * 1000,
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
						currentAppPkg = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
					}
				}
			}
		} else
		{
			ActivityManager am=(ActivityManager)AutoreportApp.getContext().getSystemService(Activity.ACTIVITY_SERVICE);
			RunningTaskInfo info1 = am.getRunningTasks(1).get(0);
			currentAppPkg = info1.topActivity.getPackageName();
		}
		// Log.i("TAG", "Current App in foreground is: " + currentApp);
		return currentAppPkg;
	}
	/*************** 获取当前程序包名End **********************************/
	
	/****************提取UidBegin***********************/ 
	
	public static final int getUid(String pkgname) //根据报名提取uid
	{
		ApplicationInfo appinfo = null;
		PackageManager pkgmanager = null;
		if (pkgname != null)
		{
			try
			{
				pkgmanager = (PackageManager) AutoreportApp.getContext().getPackageManager();
				appinfo = pkgmanager.getApplicationInfo(pkgname, 0);
				return  appinfo.uid;
			} catch (PackageManager.NameNotFoundException e)
			{
				// TODO: handle exception
			}

		}
		return 0;
	}
	/****************提取UidEnd***********************/
	
	/*************** 获取当前应用名称Begin **********************************/
	public static final String getAppName(String pkgname)//根据包名提取应用程序名称
	{
		ApplicationInfo appinfo = null;
		PackageManager pkgmanager = null;
		String appname = "testAPP";
	
		if (pkgname != null)
		{
			try
			{
				pkgmanager = (PackageManager) AutoreportApp.getContext().getPackageManager();
				appinfo = pkgmanager.getApplicationInfo(pkgname, 0);
				
			} catch (PackageManager.NameNotFoundException e)

			{
				appinfo = null;
				// TODO: handle exception
			}
			
			if(appinfo!=null)
			appname = (String) pkgmanager.getApplicationLabel(appinfo);
			// Log.i("AAA", appname);
		}
		return appname;
	}
	
	/*************** 获取当前应用名称End **********************************/
	
	/*************** 提取pid信息Begin **********************************/
	public static final String[] getPidInfo(int uid)  //根据uid提取pid ,返回容器list,第一项是pid信息,第二项是pidnum,
	{
		String[] pidInfo=new String[2];
		String pid="";
		int pidNum=0;
		// 获取所有正在运行的app
		ActivityManager am=(ActivityManager)AutoreportApp.getContext().getSystemService(Activity.ACTIVITY_SERVICE);
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
					
				} else
				{
					pid += String.valueOf(appProcess.pid);
					pidNum++;
				}
			}
		}
		pidInfo[0]=pid;
		pidInfo[1]=String.valueOf(pidNum);
		return pidInfo;
	}
	/*************** 提取PID信息End **********************************/
	
	
	
	/*************** 获取手机运营商名字Begin ************************/
	public static final String getProvidersName(String IMSI)
	{
		String ProvidersName = null;
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
	/*************** 获取手机运营商名字End ************************/
	
	
	
	
	
	
	
	
	/****************判断是否是小数Begin**********************/
	public static final  boolean isBigDecimal(String str)
	{
		Boolean strResult = str.matches("-?[0-9]+.*[0-9]*");
		if (strResult == true)
		{
			return true;
		} else
		{
			return false;
		}
	}
	/****************判断是否是小数End**********************/
}
