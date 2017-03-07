package com.autoreport.datamodel;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import android.app.Application;
import android.content.Context;
import android.util.Log;
/**
 * 全局变量
 * @author 周宏
 *
 */
public class AutoreportApp extends Application
{
	public static List<BaseInfo> infolist=new ArrayList<BaseInfo>();//用于ListView显示
//	public static List<SignalInfo> signalInfolist=new ArrayList<SignalInfo>();//用于ListView显示
	private static Context context=null;
	@Override
	public void onCreate()
	{
		context=getApplicationContext();
	}
	
	public static Context getContext()
	{
		return context;
	}
}
