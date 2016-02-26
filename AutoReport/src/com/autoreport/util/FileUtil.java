package com.autoreport.util;

import java.io.File;
import java.io.RandomAccessFile;

import android.util.Log;

public class FileUtil
{
	/****************数据记录到txt**********************/
	public final  void writeTxtToFile(String strcontent, String filePath, String fileName)
	{
		// 生成文件夹之后，再生成文件，不然会出错
		makeFilePath(filePath, fileName);

		String strFilePath = filePath + fileName;
		// 每次写入时，都换行写
		String strContent = strcontent + "\r\n";
		try
		{
			File file = new File(strFilePath);
			if (!file.exists())
			{
				Log.d("TestFile", "Create the file:" + strFilePath);
				file.getParentFile().mkdirs();
				file.createNewFile();
			}

			RandomAccessFile raf = new RandomAccessFile(file, "rwd");
			raf.seek(file.length());
			raf.write(strContent.getBytes());

			raf.close();
		} catch (Exception e)
		{
			Log.e("TestFile", "Error on write File:" + e);
		}
	}
	/****************生成文件**********************/
	public final File makeFilePath(String filePath, String fileName)
	{
		File file = null;
		makeRootDirectory(filePath);
		try
		{
			file = new File(filePath + fileName);
			if (!file.exists())
			{
				file.createNewFile();
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return file;
	}

	/****************生成文件夹**********************/
	public final void makeRootDirectory(String filePath)
	{
		File file = null;
		try
		{
			file = new File(filePath);
			if (!file.exists())
			{
				file.mkdir();
			}
		} catch (Exception e)
		{
			Log.i("error:", e + "");
		}

	}
}



/****************************** 文件操作 ***********************/
//文件记录
//	String filePath = "/sdcard/Test/";
//	String drxfileName = "drx.txt";

// if (MyApp.infolist.size() > 0)
// {
// // 记录到文件
// String sdrx = "\r\n" + "时间:" +
// MyApp.infolist.get(MyApp.infolist.size() -
// 1).gettime()
// + "\r\n" + "TX数据:" + txqueue.get_data() + "\r\n" +
// "TX期望:" + txqueue.expectation
// + "\r\n" + "TX方差:" + txqueue.variance + "\r\n" +
// "TX最大值:" + txqueue.get_maxValue()
// + "\r\n" + "RX数据:" + rxqueue.get_data() + "\r\n" +
// "RX期望:" + rxqueue.expectation
// + "\r\n" + "RX方差:" + rxqueue.variance + "\r\n" +
// "RX最大值:" + rxqueue.get_maxValue()
// + "\r\n" + "RSRP:" +
// MyApp.infolist.get(MyApp.infolist.size() -
// 1).getRSRP()
// + "\r\n" + "RSRQ:" +
// MyApp.infolist.get(MyApp.infolist.size() -
// 1).getRSRQ()
// + /*
// * "\r\n" + "CQI:" +
// * MyApp.infolist.get(MyApp.infolist.
// * size() - 1).getCQI() +
// */"\r\n" + "SNR:" +
// MyApp.infolist.get(MyApp.infolist.size() -
// 1).getSNR()
// + "\r\n"
// + "\r\n" + "网络类型:" +
// MyApp.infolist.get(MyApp.infolist.size() -
// 1).getNetType();
// writeTxtToFile(sdrx, filePath, drxfileName);
// }
/****************************** 文件操作 ***********************/