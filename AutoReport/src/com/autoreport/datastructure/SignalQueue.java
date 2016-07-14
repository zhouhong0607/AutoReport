package com.autoreport.datastructure;

import com.autoreport.database.DatabaseOperator;

import android.util.Log;

public class SignalQueue
{

	int maxSize; // 队列长度，由构造函数初始化
	long max_Value;// 流量最大值
	long expectation;// 期望
	long variance;// 方差
	int front; // 队头指向队列第一个数据
	int rear; // 队尾, 指向最后一个数据后一位
	public int nItems; // 元素的个数
	/******** 队列数据 *************/


	public SignalInfo[] queSignalInfo;

	
	// public int[] queId;// 主键对列
	// public String[] queInfoId;// 外键队列
	// public String[] queRsrp;// RSRP队列
	// public String[] queRsrq;// RSRQ队列
	// public String[] queTxByte;// 发送字节量队列
	// public String[] queRxByte;// 接收字节量队列
	// public String[] queNetType;//网络类型 队列
	// public String[] queRssinr;//信噪比 队列
	// public String[] quePci;//PCI 队列
	// public String[] queCi;//CI 队列
	// public String[] queEnodbId;//ENODBID 队列
	// public String[] queCellId;//CELLID 队列
	// public String[] queTac;//TAC 队列
	// public String[] queTimeStamp;//时间戳队列
	/******** 队列数据 *************/

	// --------------------------------------------------------------

	public SignalQueue(int s)
	{
		maxSize = s;
		queSignalInfo=new SignalInfo[maxSize];//实例对象数组，并且对数组内元素实例化
		/******** 声明长度 *************/
		for (int i = 0; i < maxSize; i++)
		{
			queSignalInfo[i] = new SignalInfo();
		}
		
		
	
		// queId=new int[maxSize];
		// queInfoId=new String[maxSize];
		//
		// queRsrp = new String[maxSize];
		// queRsrq = new String[maxSize];
		// queTxByte = new String[maxSize];
		// queRxByte = new String[maxSize];
		// queNetType = new String[maxSize];
		// queRssinr = new String[maxSize];
		// quePci = new String[maxSize];
		// queCi = new String[maxSize];
		// queEnodbId = new String[maxSize];
		// queCellId = new String[maxSize];
		// queTac = new String[maxSize];
		// queTimeStamp = new String[maxSize];
		/******** 声明长度 *************/
		front = 0;
		rear = 0;
		nItems = 0;
		max_Value = 0;
		variance = 0;
		expectation = 0;
	}

	// --------------------------------------------------------------
	public void insert(SignalInfo signalInfo) // 进队列
	{
		if (nItems == maxSize) //
		{
			front = (front + 1) % maxSize;
			
			/******** 队列满,采取循环方式替换数据 *************/

			// queSignalInfo[rear++].setId(signalInfo.getId()) ;
			// queSignalInfo[rear++].setInfoId(signalInfo.getInfoId());

			queSignalInfo[rear].setRsrp(signalInfo.getRsrp());
			queSignalInfo[rear].setRsrq(signalInfo.getRsrq());
			queSignalInfo[rear].setTxByte(signalInfo.getTxByte());
			queSignalInfo[rear].setRxByte(signalInfo.getRxByte());
			queSignalInfo[rear].setNetType(signalInfo.getNetType());
			queSignalInfo[rear].setRssinr(signalInfo.getRssinr());
			queSignalInfo[rear].setPci(signalInfo.getPci());
			queSignalInfo[rear].setCi(signalInfo.getCi());
			queSignalInfo[rear].setEnodbId(signalInfo.getEnodbId());
			queSignalInfo[rear].setCellId(signalInfo.getCellId());
			queSignalInfo[rear].setTac(signalInfo.getTac());
			queSignalInfo[rear].setTimeStamp(signalInfo.getTimeStamp());
			rear = (rear + 1) % maxSize;
			/******** 队列满,采取循环方式替换数据 *************/
		} else
		{
			/******** 队尾指针加1,把值j加入队尾 *************/

			// queSignalInfo[rear++].setId(signalInfo.getId()) ;
			// queSignalInfo[rear++].setInfoId(signalInfo.getInfoId());

			queSignalInfo[rear].setRsrp(signalInfo.getRsrp());
			queSignalInfo[rear].setRsrq(signalInfo.getRsrq());
			queSignalInfo[rear].setTxByte(signalInfo.getTxByte());
			queSignalInfo[rear].setRxByte(signalInfo.getRxByte());
			queSignalInfo[rear].setNetType(signalInfo.getNetType());
			queSignalInfo[rear].setRssinr(signalInfo.getRssinr());
			queSignalInfo[rear].setPci(signalInfo.getPci());
			queSignalInfo[rear].setCi(signalInfo.getCi());
			queSignalInfo[rear].setEnodbId(signalInfo.getEnodbId());
			queSignalInfo[rear].setCellId(signalInfo.getCellId());
			queSignalInfo[rear].setTac(signalInfo.getTac());
			queSignalInfo[rear].setTimeStamp(signalInfo.getTimeStamp());
			rear = (rear + 1) % maxSize;
			/******** 队尾指针加1,把值j加入队尾 *************/
			nItems++;
		}
	}

	// --------------------------------------------------------------
	public void emovefront() // 删除队头元素
	{
		front = (front + 1) % maxSize;
		nItems--;
	}

	// --------------------------------------------------------------
	public long peekfront() // 取得队列的队头元素指针
	{
		return front;
	}

	// --------------------------------------------------------------
	public void removerear() // 删除队尾元素
	{

		if (rear == 0)
		{
			rear = maxSize - 1;
		} else
		{
			rear = rear - 1;
		}
		nItems--;
	}

	// --------------------------------------------------------------
	public long peekrear() // 取得队列的队尾元素指针
	{
		return rear;

	}

	// --------------------------------------------------------------
	public boolean isEmpty() // 判队列是否为空。若为空返回一个真值，否则返回一个假值。
	{
		return (nItems == 0);
	}

	// --------------------------------------------------------------
	public boolean isFull() // 判队列是否已满。若已满返回一个真值，否则返回一个假值。
	{
		return (nItems == maxSize);
	}

	// --------------------------------------------------------------
	public int size() // 返回队列的长度
	{
		return nItems;
	}

	// --------------------------------------------------------------
	public void clear()// 清空队列
	{
		front = 0;
		rear = 0;
		nItems = 0;
		max_Value = 0;
		variance = 0;
		expectation = 0;
	}

	public long get_maxValue()// 获得接收流量最大值
	{
		int location = front;
		for (int i = 0; i < nItems; i++)// 取出所有数据
		{
			if (Long.parseLong(queSignalInfo[location].getRxByte()) > max_Value)
				max_Value = Long.parseLong(queSignalInfo[location].getRxByte());
			location = (location + 1) % maxSize;
		}
		return max_Value;
	}
/**
 * 取得队列流量和
 * @return
 */
	public long get_sum()// 获得接收流量最大值
	{
		int location = front;
		long sum=0;
		for (int i = 0; i < nItems; i++)// 取出所有数据
		{
			sum+=Long.parseLong(queSignalInfo[location].getRxByte())+Long.parseLong(queSignalInfo[location].getTxByte());
			location = (location + 1) % maxSize;
		}
		return sum;
	}
	
	
	public boolean judege()
	{
		/*****************************************************/
		int excepCount=0;
		boolean excepTing=false;
		boolean isExcep=false;
		
		for(SignalInfo sInfo:queSignalInfo)
		{
			long dtx=Long.parseLong(sInfo.getTxByte());
			long drx=Long.parseLong(sInfo.getRxByte());
			if (dtx > 0 && drx == 0)
			{
				excepTing = true;
			}
				
			if (excepTing)
			{
				if (dtx == 0 && drx == 0)
				{
					excepCount++;
					if (excepCount == 5)
					{
						isExcep=true;
						Log.i("AAA", "Excep");
//						excepTing = false;
						break;
					}
				} else if (drx > 0)
				{
					excepTing = false;
				} else if (dtx > 0 && drx == 0)
				{
					excepCount = 0;
				}

			} else
			{
				excepCount = 0;
			}
				
		}
		
		return isExcep;

		

		/*****************************************************/
	}
	
	
	// 设置所有数据的外键
	public void setInfoId(String infoId)// 获得接收流量最大值
	{
		int location = front;
		for (int i = 0; i < nItems; i++)// 取出所有数据
		{
			queSignalInfo[location].setInfoId(infoId);
			location = (location + 1) % maxSize;
		}

	}

	// 将队列中数据插入数据库
	public void insertToDB(DatabaseOperator databaseOperator)
	{
		int location = front;
		for (int i = 0; i < nItems; i++)// 取出所有数据
		{
			databaseOperator.insertToSignalInfo(queSignalInfo[location]);
			location = (location + 1) % maxSize;
		}

	}

	// public void calculate_expectation()// 计算期望
	// {
	// long sum = 0;
	// int location = front;
	// int record = 0;
	// for (int i = 0; i < nItems; i++)// 取出所有数据
	// {
	// if (queArray[location] != 0)// 剔除非零数据
	// {
	// sum += queArray[location];
	// record++;
	// }
	// location = (location + 1) % maxSize;
	// }
	// if (record == 0)
	// {
	// expectation = 0;
	// } else
	// {
	// expectation = sum / record;
	// }
	//
	// }
	//
	// public void calculate_variance()// 计算方差
	// {
	// long sum = 0;
	// int location = front;
	// int record = 0;
	// for (int i = 0; i < nItems; i++)// 取出所有数据
	// {
	// if (queArray[location] != 0)// 取出非零数据计算
	// {
	// sum += Math.pow(queArray[location] - expectation, 2);
	// record++;
	// }
	// location = (location + 1) % maxSize;
	// }
	// if (record == 0)
	// {
	// variance = 0;
	// } else
	// {
	// variance = sum / record;
	// }
	// }
	//
	// public String get_data()
	// {
	// String s = "";
	// long sum=0;
	// int location = front;
	//
	// for (int i = 0; i < nItems; i++)// 取出所有数据
	// {
	// sum+=queArray[location];
	// if(i!=nItems-1)
	// {
	// s += String.valueOf(queArray[location]) + ",";
	// }else {
	// s+=String.valueOf(queArray[location]);
	// }
	// location = (location + 1) % maxSize;
	// }
	// return "("+sum+")"+s;
	//
	// }

	// protected boolean judge(long min)// 做出判断小于min定为异常
	// {
	// long sum=0;
	// int location = front;
	//
	// if (nItems != 0)
	// {
	// for (int i = 0; i < nItems; i++)// 取出所有数据
	// {
	// sum += queArray[location];
	// location = (location + 1) % maxSize;
	// }
	//
	// result = sum / nItems;
	// if (result < min)
	// {
	// return true;
	// } else
	// {
	// return false;
	// }
	//
	// } else
	// {
	// return false;
	// }
	// }

}
