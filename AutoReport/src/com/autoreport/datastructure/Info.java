package com.autoreport.datastructure;

/**
 * Created by Administrator on 2016/2/26.
 */

import java.io.Serializable;
import java.util.UUID;

public class Info implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4017674411859976594L;

	private String id;//主键
	
	private String brand;// 品牌
	private String type;// 型号
	private String launTime;// 启动时刻
	private String exitTime;// 退出时刻
	private String excepTime;// 异常时刻
	private int uploadNum;// 失败上传的次数
	private String uploadTime;// 上报时刻
	private String useTime;// 使用时间
	private String version;// 版本
	private String IMEI;// IMEI
	private String IMSI;// IMSI
	private String corporation;// 运营商
	private String LAC_GSM;// LAC号
	private String Cell_Id_GSM;// cell-id号
	private String cpuRate;// cpu使用率 ！！！！！！！！！！！需要修改，全部占比
	private String localIp;// 本机IP
	private String AppName;// 应用名称
	private String uid;// uid
	private String pid;// pid(多个)
	private String gid;// 进程组id
	private String pidNumber;// 进程数
	private String MemRate;// 内存占用率
	private String Flag;// 标志位
	private int upload_Flag;// 上传标志位
	
//	private SignalQueue signalQueue;//信号队列
//	private int queLength;//队列长度
	
	
	//构造方法
	public Info( )
	{
		id=String.valueOf(UUID.randomUUID());//构造函数生成唯一外键
		this.Flag = "0";
		this.upload_Flag = 0;
		this.uploadNum = 1;
		
	}
	
	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public String getId()
	{
		return id;
	}
	
//	public SignalQueue getSignalQueue()
//	{
//		return signalQueue;
//	}
//
//	public void setSignalQueue(SignalQueue signalQueue)
//	{
//		this.signalQueue = signalQueue;
//	}
//	
//
//	public int getQueLength()
//	{
//		return queLength;
//	}
//
//	public void setQueLength(int queLength)
//	{
//		this.queLength = queLength;
//	}

	
	/**
	 * @return the brand
	 */
	public String getBrand()
	{
		return brand;
	}
	/**
	 * @param brand the brand to set
	 */
	public void setBrand(String brand)
	{
		this.brand = brand;
	}
	/**
	 * @return the type
	 */
	public String getType()
	{
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type)
	{
		this.type = type;
	}
	/**
	 * @return the launTime
	 */
	public String getLaunTime()
	{
		return launTime;
	}
	/**
	 * @param launTime the launTime to set
	 */
	public void setLaunTime(String launTime)
	{
		this.launTime = launTime;
	}
	/**
	 * @return the exitTime
	 */
	public String getExitTime()
	{
		return exitTime;
	}
	/**
	 * @param exitTime the exitTime to set
	 */
	public void setExitTime(String exitTime)
	{
		this.exitTime = exitTime;
	}
	/**
	 * @return the excepTime
	 */
	public String getExcepTime()
	{
		return excepTime;
	}
	/**
	 * @param excepTime the excepTime to set
	 */
	public void setExcepTime(String excepTime)
	{
		this.excepTime = excepTime;
	}
	/**
	 * @return the uploadNum
	 */
	public int getUploadNum()
	{
		return uploadNum;
	}
	/**
	 * @param uploadNum the uploadNum to set
	 */
	public void setUploadNum(int uploadNum)
	{
		this.uploadNum = uploadNum;
	}
	/**
	 * @return the uploadTime
	 */
	public String getUploadTime()
	{
		return uploadTime;
	}
	/**
	 * @param uploadTime the uploadTime to set
	 */
	public void setUploadTime(String uploadTime)
	{
		this.uploadTime = uploadTime;
	}
	/**
	 * @return the useTime
	 */
	public String getUseTime()
	{
		return useTime;
	}
	/**
	 * @param useTime the useTime to set
	 */
	public void setUseTime(String useTime)
	{
		this.useTime = useTime;
	}
	/**
	 * @return the version
	 */
	public String getVersion()
	{
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(String version)
	{
		this.version = version;
	}
	/**
	 * @return the iMEI
	 */
	public String getIMEI()
	{
		return IMEI;
	}
	/**
	 * @param iMEI the iMEI to set
	 */
	public void setIMEI(String iMEI)
	{
		IMEI = iMEI;
	}
	/**
	 * @return the iMSI
	 */
	public String getIMSI()
	{
		return IMSI;
	}
	/**
	 * @param iMSI the iMSI to set
	 */
	public void setIMSI(String iMSI)
	{
		IMSI = iMSI;
	}
	/**
	 * @return the corporation
	 */
	public String getCorporation()
	{
		return corporation;
	}
	/**
	 * @param corporation the corporation to set
	 */
	public void setCorporation(String corporation)
	{
		this.corporation = corporation;
	}
	/**
	 * @return the lAC_GSM
	 */
	public String getLAC_GSM()
	{
		return LAC_GSM;
	}
	/**
	 * @param lAC_GSM the lAC_GSM to set
	 */
	public void setLAC_GSM(String lAC_GSM)
	{
		LAC_GSM = lAC_GSM;
	}
	/**
	 * @return the cell_Id_GSM
	 */
	public String getCell_Id_GSM()
	{
		return Cell_Id_GSM;
	}
	/**
	 * @param cell_Id_GSM the cell_Id_GSM to set
	 */
	public void setCell_Id_GSM(String cell_Id_GSM)
	{
		Cell_Id_GSM = cell_Id_GSM;
	}
	/**
	 * @return the rSRP
	 */
	
	/**
	 * @return the cpuRate
	 */
	public String getCpuRate()
	{
		return cpuRate;
	}
	/**
	 * @param cpuRate the cpuRate to set
	 */
	public void setCpuRate(String cpuRate)
	{
		this.cpuRate = cpuRate;
	}
	/**
	 * @return the localIp
	 */
	public String getLocalIp()
	{
		return localIp;
	}
	/**
	 * @param localIp the localIp to set
	 */
	public void setLocalIp(String localIp)
	{
		this.localIp = localIp;
	}
	/**
	 * @return the appName
	 */
	public String getAppName()
	{
		return AppName;
	}
	/**
	 * @param appName the appName to set
	 */
	public void setAppName(String appName)
	{
		AppName = appName;
	}
	/**
	 * @return the uid
	 */
	public String getUid()
	{
		return uid;
	}
	/**
	 * @param uid the uid to set
	 */
	public void setUid(String uid)
	{
		this.uid = uid;
	}
	/**
	 * @return the pid
	 */
	public String getPid()
	{
		return pid;
	}
	/**
	 * @param pid the pid to set
	 */
	public void setPid(String pid)
	{
		this.pid = pid;
	}
	/**
	 * @return the gid
	 */
	public String getGid()
	{
		return gid;
	}
	/**
	 * @param gid the gid to set
	 */
	public void setGid(String gid)
	{
		this.gid = gid;
	}
	/**
	 * @return the pidNumber
	 */
	public String getPidNumber()
	{
		return pidNumber;
	}
	/**
	 * @param pidNumber the pidNumber to set
	 */
	public void setPidNumber(String pidNumber)
	{
		this.pidNumber = pidNumber;
	}
	/**
	 * @return the memRate
	 */
	public String getMemRate()
	{
		return MemRate;
	}
	/**
	 * @param memRate the memRate to set
	 */
	public void setMemRate(String memRate)
	{
		MemRate = memRate;
	}
	/**
	 * @return the txByte
	 */
	
	/**
	 * @return the flag
	 */
	public String getFlag()
	{
		return Flag;
	}
	/**
	 * @param flag the flag to set
	 */
	public void setFlag(String flag)
	{
		Flag = flag;
	}
	/**
	 * @return the upload_Flag
	 */
	public int getUpload_Flag()
	{
		return upload_Flag;
	}
	/**
	 * @param upload_Flag the upload_Flag to set
	 */
	public void setUpload_Flag(int upload_Flag)
	{
		this.upload_Flag = upload_Flag;
	}

//  额外的方法////////////////////////////////////////	
	
	public void setUpload_FlagOK()
	{
		this.upload_Flag = 1;
	}
	
	public void setFlagOK()
	{
		this.Flag = "1";
	}
	
	public void setUploadNumAdd()
	{
		this.uploadNum ++;
	}
}
