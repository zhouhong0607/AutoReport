package com.autoreport.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import com.autoreport.datastructure.Info;

/**
 * Created by Administrator on 2016/2/26. 数据库操作类， 负责 添加 ，删除 ，更新 ，查询，数据
 */
public class DatabaseOperator
{
	private SQLiteDatabase db;

	/************* 构造函数******************/
	public DatabaseOperator(InfoDatabase infoDatabase)
	{
		this.db = infoDatabase.getWritableDatabase();// 创建数据表，并返回数据库对象
	}

	/**%%%%%%%%%%%%%%%%%%%%%%%%%对表Info操作%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%**/	
	/************添加数据,向表Info插入一条info******************/
	public void insertToInfo(Info info)
	{
		db.beginTransaction();// 开启事务
		try
		{
			ContentValues values = new ContentValues();
			// 装填数据
			values.put("brand", info.getBrand());
			values.put("type", info.getType());
			values.put("launTime", info.getLaunTime());
			values.put("exitTime", info.getExitTime());
			values.put("excepTime", info.getExcepTime());
			values.put("uploadNum", info.getUploadNum());
			values.put("uploadTime", info.getUploadTime());
			values.put("useTime", info.getUseTime());
			values.put("version", info.getVersion());
			values.put("IMEI", info.getIMEI());
			values.put("IMSI", info.getIMSI());
			values.put("corporation", info.getCorporation());
			values.put("LAC_GSM", info.getLAC_GSM());
			values.put("Cell_Id_GSM", info.getCell_Id_GSM());
			values.put("RSRP", info.getRSRP());
			values.put("RSRQ", info.getRSRQ());
			values.put("cpuRate", info.getCpuRate());
			values.put("localIp", info.getLocalIp());
			values.put("AppName", info.getAppName());
			values.put("uid", info.getUid());
			values.put("pid", info.getPid());
			values.put("gid", info.getGid());
			values.put("pidNumber", info.getPidNumber());
			values.put("MemRate", info.getMemRate());
			values.put("TxByte", info.getTxByte());
			values.put("RxByte", info.getRxByte());
			values.put("NetType", info.getNetType());
			values.put("RSSNR", info.getRSSNR());
			values.put("PCI", info.getPCI());
			values.put("CI", info.getCI());
			values.put("ENODBID", info.getENODBID());
			values.put("CELLID", info.getCELLID());
			values.put("TAC", info.getTAC());
			values.put("Flag", info.getFlag());
			values.put("upload_Flag", info.getUpload_Flag());
			// 插入数据到表Info
			db.insert("Info", null, values);
			db.setTransactionSuccessful();// 事务成功
		} catch (Exception e)
		{
			Log.e("AAA", "插入事务失败");
			e.printStackTrace();
		} finally
		{
			db.endTransaction();// 结束事务
		}
		
	}

	/*************删除数据,删除所有数据 ******************/
	public void deleteFromInfo()
	{
		db.beginTransaction();// 开启事务
		try
		{
		
			db.delete("Info", null, null);// 删除所有数据
			db.setTransactionSuccessful();// 事务成功
		} catch (Exception e)
		{
			Log.e("AAA", "删除事务失败");
			e.printStackTrace();
		} finally
		{
			db.endTransaction();// 结束事务
		}
	}

	/*************更新数据,更新id位置数据，为新info******************/
	public void updateFromInfo(Info info, int id)
	{
		db.beginTransaction();// 开启事务
		try
		{
			ContentValues values = new ContentValues();
			// 装填数据
			values.put("brand", info.getBrand());
			values.put("type", info.getType());
			values.put("launTime", info.getLaunTime());
			values.put("exitTime", info.getExitTime());
			values.put("excepTime", info.getExcepTime());
			values.put("uploadNum", info.getUploadNum());
			values.put("uploadTime", info.getUploadTime());
			values.put("useTime", info.getUseTime());
			values.put("version", info.getVersion());
			values.put("IMEI", info.getIMEI());
			values.put("IMSI", info.getIMSI());
			values.put("corporation", info.getCorporation());
			values.put("LAC_GSM", info.getLAC_GSM());
			values.put("Cell_Id_GSM", info.getCell_Id_GSM());
			values.put("RSRP", info.getRSRP());
			values.put("RSRQ", info.getRSRQ());
			values.put("cpuRate", info.getCpuRate());
			values.put("localIp", info.getLocalIp());
			values.put("AppName", info.getAppName());
			values.put("uid", info.getUid());
			values.put("pid", info.getPid());
			values.put("gid", info.getGid());
			values.put("pidNumber", info.getPidNumber());
			values.put("MemRate", info.getMemRate());
			values.put("TxByte", info.getTxByte());
			values.put("RxByte", info.getRxByte());
			values.put("NetType", info.getNetType());
			values.put("RSSNR", info.getRSSNR());
			values.put("PCI", info.getPCI());
			values.put("CI", info.getCI());
			values.put("ENODBID", info.getENODBID());
			values.put("CELLID", info.getCELLID());
			values.put("TAC", info.getTAC());
			values.put("Flag", info.getFlag());
			values.put("upload_Flag", info.getUpload_Flag());
			// 更新数据
			String[] s = new String[1];
			s[0] = String.valueOf(id);
			db.update("Info", values, "id=?", s);
			db.setTransactionSuccessful();// 事务成功
		} catch (Exception e)
		{
			Log.e("AAA", "更新事务失败");
			e.printStackTrace();
		} finally
		{
			db.endTransaction();// 结束事务
		}
	}

	/*************查询数据，返回info的list******************/
	public List<Info> queryFromInfo()
	{
		List<Info> queryResult = new ArrayList<Info>();// 实例Info的容器
		db.beginTransaction();// 开启事务
		try
		{
			Cursor cursor = db.query("Info", null, null, null, null, null, null);// 查询数据
			if (cursor.moveToFirst())
			{
				do
				{
					Info querydata = new Info();// 查询到的每个数据
					querydata.setId(cursor.getInt(cursor.getColumnIndex("id")));
					querydata.setBrand(cursor.getString(cursor.getColumnIndex("brand")));
					querydata.setType(cursor.getString(cursor.getColumnIndex("type")));
					querydata.setLaunTime(cursor.getString(cursor.getColumnIndex("launTime")));
					querydata.setExitTime(cursor.getString(cursor.getColumnIndex("exitTime")));
					querydata.setExcepTime(cursor.getString(cursor.getColumnIndex("excepTime")));
					querydata.setUploadNum(cursor.getInt(cursor.getColumnIndex("uploadNum")));
					querydata.setUploadTime(cursor.getString(cursor.getColumnIndex("uploadTime")));
					querydata.setUseTime(cursor.getString(cursor.getColumnIndex("useTime")));
					querydata.setVersion(cursor.getString(cursor.getColumnIndex("version")));
					querydata.setIMEI(cursor.getString(cursor.getColumnIndex("IMEI")));
					querydata.setIMSI(cursor.getString(cursor.getColumnIndex("IMSI")));
					querydata.setCorporation(cursor.getString(cursor.getColumnIndex("corporation")));
					querydata.setLAC_GSM(cursor.getString(cursor.getColumnIndex("LAC_GSM")));
					querydata.setCell_Id_GSM(cursor.getString(cursor.getColumnIndex("Cell_Id_GSM")));
					querydata.setRSRP(cursor.getString(cursor.getColumnIndex("RSRP")));
					querydata.setRSRQ(cursor.getString(cursor.getColumnIndex("RSRQ")));
					querydata.setCpuRate(cursor.getString(cursor.getColumnIndex("cpuRate")));
					querydata.setLocalIp(cursor.getString(cursor.getColumnIndex("localIp")));
					querydata.setAppName(cursor.getString(cursor.getColumnIndex("AppName")));
					querydata.setUid(cursor.getString(cursor.getColumnIndex("uid")));
					querydata.setPid(cursor.getString(cursor.getColumnIndex("pid")));
					querydata.setGid(cursor.getString(cursor.getColumnIndex("gid")));
					querydata.setPidNumber(cursor.getString(cursor.getColumnIndex("pidNumber")));
					querydata.setMemRate(cursor.getString(cursor.getColumnIndex("MemRate")));
					querydata.setTxByte(cursor.getString(cursor.getColumnIndex("TxByte")));
					querydata.setRxByte(cursor.getString(cursor.getColumnIndex("RxByte")));
					querydata.setNetType(cursor.getString(cursor.getColumnIndex("NetType")));
					querydata.setRSSNR(cursor.getString(cursor.getColumnIndex("RSSNR")));
					querydata.setPCI(cursor.getString(cursor.getColumnIndex("PCI")));
					querydata.setCI(cursor.getString(cursor.getColumnIndex("CI")));
					querydata.setENODBID(cursor.getString(cursor.getColumnIndex("ENODBID")));
					querydata.setCELLID(cursor.getString(cursor.getColumnIndex("CELLID")));
					querydata.setTAC(cursor.getString(cursor.getColumnIndex("TAC")));
					querydata.setFlag(cursor.getString(cursor.getColumnIndex("Flag")));
					querydata.setUpload_Flag(cursor.getInt(cursor.getColumnIndex("upload_Flag")));
					queryResult.add(querydata);
				} while (cursor.moveToNext());
			}
			cursor.close();
			db.setTransactionSuccessful();// 事务成功
		} catch (Exception e)
		{
			Log.e("AAA", "查询事务失败");
			e.printStackTrace();
		} finally
		{
			db.endTransaction();// 结束事务
		}
		return queryResult;
	}
	/**%%%%%%%%%%%%%%%%%%%%%%%%%对表Info操作%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%**/	
	
	/**%%%%%%%%%%%%%%%%%%%%%%%%%对表SignalInfo操作%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%**/	
	/************添加数据,向表SignalInfo插入一条info******************/
	public void insertToInfo(Info info)
	{
		db.beginTransaction();// 开启事务
		try
		{
			ContentValues values = new ContentValues();
			// 装填数据
			values.put("brand", info.getBrand());
			values.put("type", info.getType());
			values.put("launTime", info.getLaunTime());
			values.put("exitTime", info.getExitTime());
			values.put("excepTime", info.getExcepTime());
			values.put("uploadNum", info.getUploadNum());
			values.put("uploadTime", info.getUploadTime());
			values.put("useTime", info.getUseTime());
			values.put("version", info.getVersion());
			values.put("IMEI", info.getIMEI());
			values.put("IMSI", info.getIMSI());
			values.put("corporation", info.getCorporation());
			values.put("LAC_GSM", info.getLAC_GSM());
			values.put("Cell_Id_GSM", info.getCell_Id_GSM());
			values.put("RSRP", info.getRSRP());
			values.put("RSRQ", info.getRSRQ());
			values.put("cpuRate", info.getCpuRate());
			values.put("localIp", info.getLocalIp());
			values.put("AppName", info.getAppName());
			values.put("uid", info.getUid());
			values.put("pid", info.getPid());
			values.put("gid", info.getGid());
			values.put("pidNumber", info.getPidNumber());
			values.put("MemRate", info.getMemRate());
			values.put("TxByte", info.getTxByte());
			values.put("RxByte", info.getRxByte());
			values.put("NetType", info.getNetType());
			values.put("RSSNR", info.getRSSNR());
			values.put("PCI", info.getPCI());
			values.put("CI", info.getCI());
			values.put("ENODBID", info.getENODBID());
			values.put("CELLID", info.getCELLID());
			values.put("TAC", info.getTAC());
			values.put("Flag", info.getFlag());
			values.put("upload_Flag", info.getUpload_Flag());
			// 插入数据到表Info
			db.insert("Info", null, values);
			db.setTransactionSuccessful();// 事务成功
		} catch (Exception e)
		{
			Log.e("AAA", "插入事务失败");
			e.printStackTrace();
		} finally
		{
			db.endTransaction();// 结束事务
		}
		
	}

	/*************删除数据,删除所有数据 ******************/
	public void deleteFromInfo()
	{
		db.beginTransaction();// 开启事务
		try
		{
		
			db.delete("Info", null, null);// 删除所有数据
			db.setTransactionSuccessful();// 事务成功
		} catch (Exception e)
		{
			Log.e("AAA", "删除事务失败");
			e.printStackTrace();
		} finally
		{
			db.endTransaction();// 结束事务
		}
	}

	/*************更新数据,更新id位置数据，为新info******************/
	public void updateFromInfo(Info info, int id)
	{
		db.beginTransaction();// 开启事务
		try
		{
			ContentValues values = new ContentValues();
			// 装填数据
			values.put("brand", info.getBrand());
			values.put("type", info.getType());
			values.put("launTime", info.getLaunTime());
			values.put("exitTime", info.getExitTime());
			values.put("excepTime", info.getExcepTime());
			values.put("uploadNum", info.getUploadNum());
			values.put("uploadTime", info.getUploadTime());
			values.put("useTime", info.getUseTime());
			values.put("version", info.getVersion());
			values.put("IMEI", info.getIMEI());
			values.put("IMSI", info.getIMSI());
			values.put("corporation", info.getCorporation());
			values.put("LAC_GSM", info.getLAC_GSM());
			values.put("Cell_Id_GSM", info.getCell_Id_GSM());
			values.put("RSRP", info.getRSRP());
			values.put("RSRQ", info.getRSRQ());
			values.put("cpuRate", info.getCpuRate());
			values.put("localIp", info.getLocalIp());
			values.put("AppName", info.getAppName());
			values.put("uid", info.getUid());
			values.put("pid", info.getPid());
			values.put("gid", info.getGid());
			values.put("pidNumber", info.getPidNumber());
			values.put("MemRate", info.getMemRate());
			values.put("TxByte", info.getTxByte());
			values.put("RxByte", info.getRxByte());
			values.put("NetType", info.getNetType());
			values.put("RSSNR", info.getRSSNR());
			values.put("PCI", info.getPCI());
			values.put("CI", info.getCI());
			values.put("ENODBID", info.getENODBID());
			values.put("CELLID", info.getCELLID());
			values.put("TAC", info.getTAC());
			values.put("Flag", info.getFlag());
			values.put("upload_Flag", info.getUpload_Flag());
			// 更新数据
			String[] s = new String[1];
			s[0] = String.valueOf(id);
			db.update("Info", values, "id=?", s);
			db.setTransactionSuccessful();// 事务成功
		} catch (Exception e)
		{
			Log.e("AAA", "更新事务失败");
			e.printStackTrace();
		} finally
		{
			db.endTransaction();// 结束事务
		}
	}

	/*************查询数据，返回SignalInfo的list******************/
	public List<Info> queryFromInfo()
	{
		List<Info> queryResult = new ArrayList<Info>();// 实例Info的容器
		db.beginTransaction();// 开启事务
		try
		{
			Cursor cursor = db.query("Info", null, null, null, null, null, null);// 查询数据
			if (cursor.moveToFirst())
			{
				do
				{
					Info querydata = new Info();// 查询到的每个数据
					querydata.setId(cursor.getInt(cursor.getColumnIndex("id")));
					querydata.setBrand(cursor.getString(cursor.getColumnIndex("brand")));
					querydata.setType(cursor.getString(cursor.getColumnIndex("type")));
					querydata.setLaunTime(cursor.getString(cursor.getColumnIndex("launTime")));
					querydata.setExitTime(cursor.getString(cursor.getColumnIndex("exitTime")));
					querydata.setExcepTime(cursor.getString(cursor.getColumnIndex("excepTime")));
					querydata.setUploadNum(cursor.getInt(cursor.getColumnIndex("uploadNum")));
					querydata.setUploadTime(cursor.getString(cursor.getColumnIndex("uploadTime")));
					querydata.setUseTime(cursor.getString(cursor.getColumnIndex("useTime")));
					querydata.setVersion(cursor.getString(cursor.getColumnIndex("version")));
					querydata.setIMEI(cursor.getString(cursor.getColumnIndex("IMEI")));
					querydata.setIMSI(cursor.getString(cursor.getColumnIndex("IMSI")));
					querydata.setCorporation(cursor.getString(cursor.getColumnIndex("corporation")));
					querydata.setLAC_GSM(cursor.getString(cursor.getColumnIndex("LAC_GSM")));
					querydata.setCell_Id_GSM(cursor.getString(cursor.getColumnIndex("Cell_Id_GSM")));
					querydata.setRSRP(cursor.getString(cursor.getColumnIndex("RSRP")));
					querydata.setRSRQ(cursor.getString(cursor.getColumnIndex("RSRQ")));
					querydata.setCpuRate(cursor.getString(cursor.getColumnIndex("cpuRate")));
					querydata.setLocalIp(cursor.getString(cursor.getColumnIndex("localIp")));
					querydata.setAppName(cursor.getString(cursor.getColumnIndex("AppName")));
					querydata.setUid(cursor.getString(cursor.getColumnIndex("uid")));
					querydata.setPid(cursor.getString(cursor.getColumnIndex("pid")));
					querydata.setGid(cursor.getString(cursor.getColumnIndex("gid")));
					querydata.setPidNumber(cursor.getString(cursor.getColumnIndex("pidNumber")));
					querydata.setMemRate(cursor.getString(cursor.getColumnIndex("MemRate")));
					querydata.setTxByte(cursor.getString(cursor.getColumnIndex("TxByte")));
					querydata.setRxByte(cursor.getString(cursor.getColumnIndex("RxByte")));
					querydata.setNetType(cursor.getString(cursor.getColumnIndex("NetType")));
					querydata.setRSSNR(cursor.getString(cursor.getColumnIndex("RSSNR")));
					querydata.setPCI(cursor.getString(cursor.getColumnIndex("PCI")));
					querydata.setCI(cursor.getString(cursor.getColumnIndex("CI")));
					querydata.setENODBID(cursor.getString(cursor.getColumnIndex("ENODBID")));
					querydata.setCELLID(cursor.getString(cursor.getColumnIndex("CELLID")));
					querydata.setTAC(cursor.getString(cursor.getColumnIndex("TAC")));
					querydata.setFlag(cursor.getString(cursor.getColumnIndex("Flag")));
					querydata.setUpload_Flag(cursor.getInt(cursor.getColumnIndex("upload_Flag")));
					queryResult.add(querydata);
				} while (cursor.moveToNext());
			}
			cursor.close();
			db.setTransactionSuccessful();// 事务成功
		} catch (Exception e)
		{
			Log.e("AAA", "查询事务失败");
			e.printStackTrace();
		} finally
		{
			db.endTransaction();// 结束事务
		}
		return queryResult;
	}
	/**%%%%%%%%%%%%%%%%%%%%%%%%%对表SignalInfo操作%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%**/
	
	
	
	
	
	public void CloseDatabase()
	{
		this.db.close();
	}
	
	
	
}
