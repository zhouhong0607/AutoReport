package com.autoreport.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/2/25.
 */
public class InfoDatabase extends SQLiteOpenHelper
{
	// 建数据表Info
	public static final String CREATE_TABLE_INFO = "create table Info(" + " id integer primary key autoincrement ,"
			+ " brand text," + "type text,"
			+ " launTime text,"
			+ " exitTime text,"
			+ " excepTime text,"
			+ " uploadNum integer,"
			+ " uploadTime text,"
			+ " useTime text,"
			+ " version text,"
			+ " IMEI text,"
			+ " IMSI text,"
			+ " corporation text,"
			+ " LAC_GSM text,"
			+ " Cell_Id_GSM text,"
			+ " RSRP text,"
			+ " RSRQ text,"
			+ " cpuRate text,"
			+ " localIp text,"
			+ " AppName text,"
			+ " uid text,"
			+ " pid text,"
			+ " gid text,"
			+ " pidNumber text,"
			+ " MemRate text,"
			+ " TxByte text,"
			+ " RxByte text,"
			+ " NetType text,"
			+ " RSSNR text,"
			+ " PCI text,"
			+ " CI text,"
			+ " ENODBID text,"
			+ " CELLID text,"
			+ " TAC text,"
			+ " Flag text,"
			+ " upload_Flag integer)";// 建数据表info   ,boolean 类型用 integer代替


	public InfoDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
	{
		super(context, name, factory, version);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL(CREATE_TABLE_INFO);
		Log.i("AAA", "数据表Info建立成功");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{

	}

}