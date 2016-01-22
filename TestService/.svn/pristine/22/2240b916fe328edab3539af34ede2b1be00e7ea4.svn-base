package alertpacket;

import com.example.testservice.Info;

public class AlertQueue
{
	// private final static int CID=1;
	// private final static int LAC=2;
	// private final static int RSRP=3;
	// private final static int RSRQ=4;
	// private final static int RSSI=5;
	int maxSize; // 队列长度，由构造函数初始化
	Info[] queArray; // 队列
	// short para1;//参数1 CID 或者 LAC
	// short para2;//参数2 RSRP 或者 RSRQ或者RSSI

	int front; // 队头指向队列第一个数据
	int rear; // 队尾, 指向最后一个数据后一位
	int nItems; // 元素的个数

	
	// --------------------------------------------------------------

	public AlertQueue(int s)// 两个参数构造, 预警次数和 位置信息（CID,LAC）
	{
		maxSize = s;
		queArray = new Info[maxSize];
		front = 0;
		rear = 0;
		nItems = 0;

	}

	// --------------------------------------------------------------
	public void insert(Info add_data) // 两个参数进队列
	{
		if (nItems == maxSize) // 队列满,采取循环方式替换数据
		{
			front = (front + 1) % maxSize;
			rear = (rear + 1) % maxSize;
			queArray[rear] = add_data;
		} else
		{
			queArray[rear++] = add_data; // 队尾指针加1,把值j加入队尾
			nItems++;
		}
	}

	

	// --------------------------------------------------------------
	public Info removefront() // 删除队头元素，并返回该值,队列头前移一位
	{
		Info temp = queArray[front]; // 取值和修改队头指针
		front = (front + 1) % maxSize;
		nItems--;
		return temp;
	}

	// --------------------------------------------------------------
	public Info peekfront() // 取得队列的队头元素。该运算与 remove()不同，后者要修改队头元素指针。
	{
		return queArray[front];
	}

	// --------------------------------------------------------------
	public Info removerear() // 删除队尾元素，并返回该值,队尾后移一位
	{

		if (rear == 0)
		{
			rear = maxSize - 1;
		} else
		{
			rear = rear - 1;
		}
		Info temp = queArray[rear];
		nItems--;
		return temp;
	}

	// --------------------------------------------------------------
	public Info peekrear() // 取得队列的队尾元素。该运算与 remove()不同，后者要修改队头元素指针。
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

	}

}
