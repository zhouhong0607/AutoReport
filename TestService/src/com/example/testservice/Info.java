package com.example.testservice;

import java.io.Serializable;

import org.apache.http.entity.SerializableEntity;


public class Info implements Serializable
{
	
	//27项信息
	private String launTime;//启动时刻
	private String exitTime;//退出时刻
	private String excepTime;//异常时刻
	private int uploadNum;//失败上传的次数
	private String uploadTime;//上报时刻
	private String useTime;//使用时间
	private String brand;//品牌
	private String type;//型号
	private String version;//版本
	private String IMEI;//IMEI
	private String IMSI;//IMSI
//	private String number;//手机号
	private String corporation;//运营商
	private String LAC_GSM;//LAC号
	private String Cell_Id_GSM;//cell-id号
//	private String sigStrength;//信号强度   XXXXXXX
	private String RSRP;//RSRP
	private String RSRQ;//RSRQ
	/******************2016/2/23删除参数*****************/
//	private String RSSI;//RSRQ
	/******************2016/2/23删除参数*****************/
	
	
//	private String fullMemory;//总内存  XXXXXXX
//	private String availMemory;//可用内存  XXXXXXX
//	private String cpuName;//cpu名字  XXXXXXX
//	private String cpuMaxFreq;//cpu最大频率  XXXXXXX
//	private String cpuMinFreq;//cpu最小频率  XXXXXXX
//	private String cpuCurFreq;//cpu当前频率  XXXXXXX
	private String cpuRate;//cpu使用率   ！！！！！！！！！！！需要修改，全部占比
	
	//新加项目
	private String localIp;//本机IP
	private String AppName;//应用名称
	private String uid;//uid
	private String pid;//pid(多个)
	private String gid;//进程组id
	private String pidNumber;//进程数
	private String MemRate;//内存占用率
	private String TxByte;//发送字节量
	private String RxByte;//接收字节量
	private String NetType;//网络类型 (LTE)
	private String RSSNR;//SNR
//	private String CQI;//CQI
	/******************2016/2/23新加入参数*****************/
	private String PCI;
	private String CI;
	private String ENODBID;
	private String CELLID;
	/**
	 * @return the pCI
	 */
	public String getPCI()
	{
		return PCI;
	}








	/**
	 * @param pCI the pCI to set
	 */
	public void setPCI(String pCI)
	{
		PCI = pCI;
	}








	/**
	 * @return the cI
	 */
	public String getCI()
	{
		return CI;
	}








	/**
	 * @param cI the cI to set
	 */
	public void setCI(String cI)
	{
		CI = cI;
	}








	/**
	 * @return the eNODBID
	 */
	public String getENODBID()
	{
		return ENODBID;
	}








	/**
	 * @param eNODBID the eNODBID to set
	 */
	public void setENODBID(String eNODBID)
	{
		ENODBID = eNODBID;
	}








	/**
	 * @return the cELLID
	 */
	public String getCELLID()
	{
		return CELLID;
	}








	/**
	 * @param cELLID the cELLID to set
	 */
	public void setCELLID(String cELLID)
	{
		CELLID = cELLID;
	}








	/**
	 * @return the tAC
	 */
	public String getTAC()
	{
		return TAC;
	}








	/**
	 * @param tAC the tAC to set
	 */
	public void setTAC(String tAC)
	{
		TAC = tAC;
	}
	private String TAC;

	/******************2016/2/23新加入参数*****************/
	
	private String Flag;//标志位
	private boolean upload_Flag;//上传标志位
	 Info()
	{
		this.Flag="0";
		this.upload_Flag=false;
		this.uploadNum=0;
	}
	
	 
	 
	 
	 
	 
	 
	 
	public int getUploadNum()
	{
		return uploadNum;
	}








	public void setUploadNum()
	{
		this.uploadNum ++;
	}








	public String getExcepTime()
	{
		return excepTime;
	}








	public void setExcepTime(String excepTime)
	{
		this.excepTime = excepTime;
	}








	public String getUploadTime()
	{
		return uploadTime;
	}








	public void setUploadTime(String uploadTime)
	{
		this.uploadTime = uploadTime;
	}








	//参数设置
	public void settime(String s)
	{
		this.launTime=s;
	}
	public void setextime(String s)
	{
		this.exitTime=s;
	}
	public void setusetime(String s)
	{
		this.useTime=s;
	}
	public void setbrand(String s)
	{
		this.brand=s;
	}
	public void settype(String s)
	{
		this.type=s;
	}
	public void setversion(String s)
	{
		this.version=s;
	}
	public void setIMEI(String s)
	{
		this.IMEI=s;
	}
	public void setIMSI(String s)
	{
		this.IMSI=s;
	}
//	public void setnumber(String s)
//	{
//		this.number=s;
//	}
	public void setcorporation(String s)
	{
		this.corporation=s;
	}
	public void setLAC_GSM(String s)
	{
		this.LAC_GSM=s;
	}
	public void setCell_Id_GSM(String s)
	{
		this.Cell_Id_GSM=s;
	}
//	public void setsigStrength(String s)
//	{
//		this.sigStrength=s;
//	}
	public void setRSRP(String s)
	{
		this.RSRP=s;
	}
	public void setRSRQ(String s)
	{
		this.RSRQ=s;
	}
//	public void setRSSI(String s)
//	{
//		this.RSSI=s;
//	}
//	public void setfullMemory(String s)
//	{
//		this.fullMemory=s;
//	}
//	public void setavailMemory(String s)
//	{
//		this.availMemory=s;
//	}
//	public void setcpuName(String s)
//	{
//		this.cpuName=s;
//	}
//	public void setcpuMaxFreq(String s)
//	{
//		this.cpuMaxFreq=s;
//	}
//	public void setcpuMinFreq(String s)
//	{
//		this.cpuMinFreq=s;
//	}
//	public void setcpuCurFreq(String s)
//	{
//		this.cpuCurFreq=s;
//	}
	public void setcpuRate(String s)
	{
		this.cpuRate=s;
	}
	//新加项目
		
	
		public void setlocalIp(String s)
		{
			this.localIp=s;
		}
		public void setAppName(String s)
		{
			this.AppName=s;
		}
		public void setuid(String s)
		{
			this.uid=s;
		}
		public void setpid(String s)
		{
			this.pid=s;
		}
		public void setgid(String s)
		{
			this.gid=s;
		}
		public void setpidNumber(String s)
		{
			this.pidNumber=s;
		}
		public void setMemRate(String s)
		{
			this.MemRate=s;
		}
		public void setTxByte(String s)
		{
			this.TxByte=s;
		}
		public void setRxByte(String s)
		{
			this.RxByte=s;
		}
		public void setNetType(String s)
		{
			this.NetType=s;
		}
		public void setSNR(String s)
		{
			this.RSSNR=s;
		}
		public void setFlag()
		{
			this.Flag="1";
		}
		public void setupFlag()
		{
			this.upload_Flag=true;
		}
		
//		public void setCQI(String s)
//		{
//			this.CQI=s;
//		}
	//参数提取
	public String gettime()
	{
		return this.launTime;
	}
	public String getextime()
	{
		return this.exitTime;
	}
	public String getusetime()
	{
		return this.useTime;
	}
	public String getbrand()
	{
		return this.brand;
	}
	public String gettype()
	{
		return this.type;
	}
	public String getversion()
	{
		return this.version;
	}
	public String getIMEI()
	{
		return this.IMEI;
	}
	public String getIMSI()
	{
		return this.IMSI;
	}
//	public String getnumber()
//	{
//		return this.number;
//	}
	public String getcorporation()
	{
		return this.corporation;
	}
	public String getLAC_GSM()
	{
		return this.LAC_GSM;
	}
	public String getCell_Id_GSM()
	{
		return this.Cell_Id_GSM;
	}
//	public String getsigStrength()
//	{
//		return this.sigStrength;
//	}
	public String getRSRP()
	{
		return this.RSRP;
	}
	public String getRSRQ()
	{
		return this.RSRQ;
	}
//	public String getRSSI()
//	{
//		return this.RSSI;
//	}
//	public String getfullMemory()
//	{
//		return this.fullMemory;
//	}
//	public String getavailMemory()
//	{
//		return this.availMemory;
//	}
//	public String getcpuName()
//	{
//		return this.cpuName;
//	}
//	public String getcpuMaxFreq()
//	{
//		return this.cpuMaxFreq;
//	}
//	public String getcpuMinFreq()
//	{
//		return this.cpuMinFreq;
//	}
//	public String getcpuCurFreq()
//	{
//		return this.cpuCurFreq;
//	}
	public String getcpuRate()
	{
		return this.cpuRate;
	}
	//新加项目
		
			public String getlocalIp()
			{
				return this.localIp;
			}
			public String getAppName()
			{
				return this.AppName;
			}
			public String getuid()
			{
				return this.uid;
			}
			public String getpid()
			{
				return this.pid;
			}
			public String getgid()
			{
				return this.gid;
			}
			public String getpidNumber()
			{
				return this.pidNumber;
			}
			public String getMemRate()
			{
				return this.MemRate;
			}
			public String getTxByte()
			{
				return this.TxByte;
			}
			public String getRxByte()
			{
				return this.RxByte;
			}
			public String getNetType()
			{
				return this.NetType;
			}
			public String getSNR()
			{
				return this.RSSNR;
			}
			public String getFlag()
			{
				return this.Flag;
			}
			public boolean getupFlag()
			{
				return this.upload_Flag;
			}
//			public String getCQI()
//			{
//				return this.CQI;
//			}
}
