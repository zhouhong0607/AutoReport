package com.autoreport.datastructure;

public class MyQueue
{

	int maxSize; // 队列长度，由构造函数初始化
	long[] queArray; // 队列

	long max_Value;// 最大值
	long expectation;// 期望
	long variance;// 方差

	int front; // 队头指向队列第一个数据
	int rear; // 队尾, 指向最后一个数据后一位
	int nItems; // 元素的个数

	// long result = 0;//存放测试结果
	// --------------------------------------------------------------

	public MyQueue(int s)
	{
		maxSize = s;
		queArray = new long[maxSize];
		front = 0;
		rear = 0;
		nItems = 0;
		max_Value = 0;
		variance = 0;
		expectation = 0;
	}

	// --------------------------------------------------------------
	public void insert(long j) // 进队列
	{
		if (nItems == maxSize) // 队列满,采取循环方式替换数据
		{
			front = (front + 1) % maxSize;
			rear = (rear + 1) % maxSize;
			queArray[rear] = j;
		} else
		{
			queArray[rear++] = j; // 队尾指针加1,把值j加入队尾
			nItems++;
		}
	}

	// --------------------------------------------------------------
	public long emovefront() // 删除队头元素，并返回该值,队列头前移一位
	{
		long temp = queArray[front]; // 取值和修改队头指针
		front = (front + 1) % maxSize;
		nItems--;
		return temp;
	}

	// --------------------------------------------------------------
	public long peekfront() // 取得队列的队头元素。该运算与 remove()不同，后者要修改队头元素指针。
	{
		return queArray[front];
	}

	// --------------------------------------------------------------
	public long removerear() // 删除队尾元素，并返回该值,队尾后移一位
	{

		if (rear == 0)
		{
			rear = maxSize - 1;
		} else
		{
			rear = rear - 1;
		}
		long temp = queArray[rear];
		nItems--;
		return temp;
	}

	// --------------------------------------------------------------
	public long peekrear() // 取得队列的队尾元素。该运算与 remove()不同，后者要修改队头元素指针。
	{
		if (rear == 0)
		{
			return queArray[maxSize - 1];
		} else
		{
			return queArray[rear - 1];
		}

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

	public long get_maxValue()
	{
		int location = front;
		for (int i = 0; i < nItems; i++)// 取出所有数据
		{
			if (queArray[location] > max_Value)
				max_Value = queArray[location];
			location = (location + 1) % maxSize;
		}
		return max_Value;
	}

	public void calculate_expectation()// 计算期望
	{
		long sum = 0;
		int location = front;
		int record = 0;
		for (int i = 0; i < nItems; i++)// 取出所有数据
		{
			if (queArray[location] != 0)// 剔除非零数据
			{
				sum += queArray[location];
				record++;
			}
			location = (location + 1) % maxSize;
		}
		if (record == 0)
		{
			expectation = 0;
		} else
		{
			expectation = sum / record;
		}

	}

	public void calculate_variance()// 计算方差
	{
		long sum = 0;
		int location = front;
		int record = 0;
		for (int i = 0; i < nItems; i++)// 取出所有数据
		{
			if (queArray[location] != 0)// 取出非零数据计算
			{
				sum += Math.pow(queArray[location] - expectation, 2);
				record++;
			}
			location = (location + 1) % maxSize;
		}
		if (record == 0)
		{
			variance = 0;
		} else
		{
			variance = sum / record;
		}
	}

	public String get_data()
	{
		String s = "";
		long sum=0;
		int location = front;

		for (int i = 0; i < nItems; i++)// 取出所有数据
		{
			sum+=queArray[location];
			if(i!=nItems-1)
			{
			s += String.valueOf(queArray[location]) + ",";
			}else {
				s+=String.valueOf(queArray[location]);
			}
			location = (location + 1) % maxSize;
		}
		return "("+sum+")"+s;

	}

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
