package com.example.testservice;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import android.app.Application;
import android.util.Log;

public class MyApp extends Application
{
	public static List<Info> infolist=new ArrayList<Info>();//用于ListView显示
}
