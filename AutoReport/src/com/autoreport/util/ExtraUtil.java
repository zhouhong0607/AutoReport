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
import java.util.Enumeration;

import com.autoreport.datastructure.MyApp;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.util.Log;

/****************提取工具类，提取相关参数**********************/

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
		ActivityManager am = (ActivityManager)MyApp.getContext().getSystemService(Activity.ACTIVITY_SERVICE);
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
