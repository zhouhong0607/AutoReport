package com.autoreport.datastructure;

import java.io.Serializable;

public class SignalInfo implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 103895836915207511L;
	private int id;//主键
	private String infoId;//外键
	
	
	private String rsrp;//RSRP
	private String rsrq;//RSRQ
	private String rssinr;//信噪比
	private String txByte;//发送字节量
	private String rxByte;//接收字节量
	private String netType;//网络类型
	
	private String pci;//PCI
	private String ci;//ECI
	private String enodbId;//EnodbID
	private String cellId;//Cell-id
	private String tac;//TAC
	private String timeStamp;//时间戳
	
	private String longitude;//经度
	private String latitude;//纬度
	private String addr;//地址
	
	public String getLongitude()
	{
		return longitude;
	}

	public void setLongitude(String longitude)
	{
		this.longitude = longitude;
	}

	public String getLatitude()
	{
		return latitude;
	}

	public void setLatitude(String latitude)
	{
		this.latitude = latitude;
	}

	public String getAddr()
	{
		return addr;
	}

	public void setAddr(String addr)
	{
		this.addr = addr;
	}

	public SignalInfo()
	{
		
	}
	
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getInfoId()
	{
		return infoId;
	}
	public void setInfoId(String infoId)
	{
		this.infoId = infoId;
	}
	
	
	
	public String getRsrp()
	{
		return rsrp;
	}
	public void setRsrp(String rsrp)
	{
		this.rsrp = rsrp;
	}
	public String getRsrq()
	{
		return rsrq;
	}
	public void setRsrq(String rsrq)
	{
		this.rsrq = rsrq;
	}
	public String getTxByte()
	{
		return txByte;
	}
	public void setTxByte(String txByte)
	{
		this.txByte = txByte;
	}
	public String getRxByte()
	{
		return rxByte;
	}
	public void setRxByte(String rxByte)
	{
		this.rxByte = rxByte;
	}
	public String getNetType()
	{
		return netType;
	}
	public void setNetType(String netType)
	{
		this.netType = netType;
	}
	public String getRssinr()
	{
		return rssinr;
	}
	public void setRssinr(String rssinr)
	{
		this.rssinr = rssinr;
	}
	public String getPci()
	{
		return pci;
	}
	public void setPci(String pci)
	{
		this.pci = pci;
	}
	public String getCi()
	{
		return ci;
	}
	public void setCi(String ci)
	{
		this.ci = ci;
	}
	public String getEnodbId()
	{
		return enodbId;
	}
	public void setEnodbId(String enodbId)
	{
		this.enodbId = enodbId;
	}
	public String getCellId()
	{
		return cellId;
	}
	public void setCellId(String cellId)
	{
		this.cellId = cellId;
	}
	public String getTac()
	{
		return tac;
	}
	public void setTac(String tac)
	{
		this.tac = tac;
	}
	public String getTimeStamp()
	{
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp)
	{
		this.timeStamp = timeStamp;
	}
}
