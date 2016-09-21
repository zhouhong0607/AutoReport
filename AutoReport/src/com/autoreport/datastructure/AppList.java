package com.autoreport.datastructure;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.util.Log;
/**
 * 监控APP列表
 * @author 周宏
 *
 */
public class AppList
{
	private static final String[] AppName = { "UC浏览器", "QQ浏览器", "百度手机浏览器", "搜狐浏览器", "傲游浏览器", "360浏览器", "Chrome浏览器",
			"火狐浏览器", "欧朋浏览器", "Safari浏览器", "猎豹浏览器", "海豚浏览器", "浏览器", "优酷视频", "爱奇艺", "腾讯视频", "搜狐视频", "暴风影音", "百度视频",
			"乐视视频", "PPS影音", "土豆视频", "芒果TV", "腾讯新闻", "搜狐新闻", "今日头条", "网易新闻", "百度新闻", "凤凰新闻", "新浪新闻", "ZAKER", "畅读",
			"一点资讯" };
	public static String CurAppName = null;

	public static String FindAppName(String SourceName)// 查找AppName是否在应用库里,在返回应用名称,不在返回null
	{
		CurAppName = null;
		for (int i = 0; i < AppName.length; i++)
		{
			if (AppName[i].equals(SourceName))
				CurAppName = SourceName;
		}
		return CurAppName;
	}

}
