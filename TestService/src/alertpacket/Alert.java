//package alertpacket;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.List;
//
//import com.example.testservice.Info;
//
//import android.R.integer;
//
//public class Alert
//{
//	int para_type;//监视参数的类型
//	int para_num;
//	String[] para_var;//监视参数的值,  数组长度为para_num的值,  para_num为0时该参数无用
//	int time_interval;//监视 时间的长度  ,  以小时为最小单位
//	int abnormal_num;//异常次数的 个数,  也就是队列的长度
//
//	List<AlertQueue> alert_list=null;
//	boolean set_length=false;//监视参数的个数 ，默认是不设置长度，既全部监视
//	
//	public Alert(int p_type,int t_inter,int ab_num)//构造函数不进行  参数合法性判断 ，请在之前判断合法性,不设置监视参数长度的构造函数
//	{
//		// TODO Auto-generated constructor stub
//		
//		para_type=p_type;
//		
//		time_interval=t_inter;
//		abnormal_num=ab_num;
//	}
//
//	public Alert(int p_type,String[] p_var,int t_inter,int ab_num)//设置监视长度的构造函数
//	{
//		// TODO Auto-generated constructor stub
//		
//		para_type=p_type;
//		
//		para_var=p_var;
//		para_num=para_var.length;//监视的个数
//		time_interval=t_inter;
//		abnormal_num=ab_num;
//		set_length=true;//设置监视长度
//	}
//	
////	public AlertQueue judegeByNew(Info fresh_data)
////	{
////		if(set_length)
////		{
////			
////		}else
////		{
////			
////		}
////	}
//	
//	
//
//	/********************************************************/
//
//	private final static int BRAND = 1;
//	private final static int TYPE = 2;
//	private final static int VERSION = 3;
//	private final static int CID = 4;
//	private final static int LAC = 5;
//	private final static int APPNAME = 6;
//	private final static int NETTYPE = 7;
//	
//	/********************************************************/
//	private final static List<AlertQueue> Alert_CID = new ArrayList<AlertQueue>();
//	private final static List<AlertQueue> Alert_LAC = new ArrayList<AlertQueue>();
//	private final static List<AlertQueue> Alert_CID_RSRP = new ArrayList<AlertQueue>();
//	private final static List<AlertQueue> Alert_CID_RSRQ = new ArrayList<AlertQueue>();
//	private final static List<AlertQueue> Alert_CID_RSSI = new ArrayList<AlertQueue>();
//	private final static List<AlertQueue> Alert_LAC_RSRP = new ArrayList<AlertQueue>();
//	private final static List<AlertQueue> Alert_LAC_RSRQ = new ArrayList<AlertQueue>();
//	private final static List<AlertQueue> Alert_LAC_RSSI = new ArrayList<AlertQueue>();
//
//	private final static int CID_CODE = 1;
//	private final static int LAC_CODE= 2;
//	private final static int CID_RSRP = 3;
//	private final static int CID_RSRQ = 4;
//	private final static int CID_RSSI = 5;
//	private final static int LAC_RSRP = 6;
//	private final static int LAC_RSRQ = 7;
//	private final static int LAC_RSSI = 8;
//
//	
//	public final static AlertQueue add_new(Info add_data, int select)
//	{
//		AlertQueue reQueue=null;
//		int i;
//		switch (select)
//		{
//		case 1:
//
//			if (Alert_CID.size() == 0)
//			{
//				Alert_CID.add(new AlertQueue(30));// 新建队列，加入这条数据
//				Alert_CID.get(0).insert(add_data);
//			} else
//			{
//				for (i = 0; i < Alert_CID.size(); i++) // 遍历查询是否存在 id
//				{
//					if (Alert_CID.get(i).peekfront().getCell_Id() == add_data.getCell_Id())// 判断队列里面CELL-ID是否存在
//					{
//						while (judge_time_hour( Alert_CID.get(i).peekrear().getextime(), Alert_CID.get(i).peekfront().getextime() ))// 如果队尾减队首的时间小于一个小时,插入数据,否则删除队头数据
//						{
//							Alert_CID.get(i).removefront();// 删除队头元素
//						}
//
//						Alert_CID.get(i).insert(add_data);// 插入数据
//															// ,该句一定执行，因为只有一个数据的时候
//															// while循环一定不满足条件
//						if (Alert_CID.get(i).isFull())// 如果已满 则报警 return true
//						{
//							reQueue=Alert_CID.get(i);
//							return reQueue;
//						}
//						break;
//					}
//
//				}
//				if (i == Alert_CID.size())// 如果满足该条件，说明 CELL-ID是新的
//				{
//					Alert_CID.add(new AlertQueue(30));// 新建队列
//					Alert_CID.get(i).insert(add_data);// 在新建的队列中插入该元素
//				}
//
//			}
//
//			break;
//		case 2:
//
//			if (Alert_LAC.size() == 0)
//			{
//				Alert_LAC.add(new AlertQueue(30));// 新建队列，加入这条数据
//				Alert_LAC.get(0).insert(add_data);
//			} else
//			{
//				for (i = 0; i < Alert_LAC.size(); i++) // 遍历查询是否存在 id
//				{
//					if (Alert_LAC.get(i).peekfront().getLAC() == add_data.getLAC())// 判断队列里面CELL-ID是否存在
//					{
//						while (judge_time_hour( Alert_LAC.get(i).peekrear().getextime(), Alert_LAC.get(i).peekfront().getextime() ))// 如果队尾减队首的时间小于一个小时,插入数据,否则删除队头数据
//						{
//							Alert_LAC.get(i).removefront();// 删除队头元素
//						}
//
//						Alert_LAC.get(i).insert(add_data);// 插入数据
//															// ,该句一定执行，因为只有一个数据的时候
//															// while循环一定不满足条件
//						if (Alert_LAC.get(i).isFull())// 如果已满 则报警 return true
//						{
//							reQueue=Alert_LAC.get(i);
//							return reQueue;
//						}
//						break;
//					}
//
//				}
//				if (i == Alert_LAC.size())// 如果满足该条件，说明 CELL-ID是新的
//				{
//					Alert_LAC.add(new AlertQueue(30));// 新建队列
//					Alert_LAC.get(i).insert(add_data);// 在新建的队列中插入该元素
//				}
//
//			}
//
//			break;
//		case 3:
//			if (Integer.parseInt(add_data.getRSRP()) < -140 || Integer.parseInt(add_data.getRSRP()) > -44)// RSRP
//																											// 正常范围-140~-44
//			{
//				if (Alert_CID_RSRP.size() == 0)
//				{
//					Alert_CID_RSRP.add(new AlertQueue(30));// 新建队列，加入这条数据
//					Alert_CID_RSRP.get(0).insert(add_data);
//				} else
//				{
//					for (i = 0; i < Alert_CID_RSRP.size(); i++) // 遍历查询是否存在 id
//					{
//						if (Alert_CID_RSRP.get(i).peekfront().getCell_Id() == add_data.getCell_Id())// 判断队列里面CELL-ID是否存在
//						{
//							while (judge_time_hour( Alert_CID_RSRP.get(i).peekrear().getextime(), Alert_CID_RSRP.get(i).peekfront().getextime() ))// 如果队尾减队首的时间小于一个小时,插入数据,否则删除队头数据
//							{
//								Alert_CID_RSRP.get(i).removefront();// 删除队头元素
//							}
//
//							Alert_CID_RSRP.get(i).insert(add_data);// 插入数据
//																	// ,该句一定执行，因为只有一个数据的时候
//																	// while循环一定不满足条件
//							if (Alert_CID_RSRP.get(i).isFull())// 如果已满 则报警
//																// return true
//							{
//								reQueue=Alert_CID_RSRP.get(i);
//								return reQueue;
//							}
//							break;
//						}
//
//					}
//					if (i == Alert_CID_RSRP.size())// 如果满足该条件，说明 CELL-ID是新的
//					{
//						Alert_CID_RSRP.add(new AlertQueue(30));// 新建队列
//						Alert_CID_RSRP.get(i).insert(add_data);// 在新建的队列中插入该元素
//					}
//
//				}
//			}
//			break;
//		case 4:
//			if (Integer.parseInt(add_data.getRSRQ()) < -20 || Integer.parseInt(add_data.getRSRQ()) > -3)// RSRQ
//																										// 正常范围-20~-3
//			{
//				if (Alert_CID_RSRQ.size() == 0)
//				{
//					Alert_CID_RSRQ.add(new AlertQueue(30));// 新建队列，加入这条数据
//					Alert_CID_RSRQ.get(0).insert(add_data);
//				} else
//				{
//					for (i = 0; i < Alert_CID_RSRQ.size(); i++) // 遍历查询是否存在 id
//					{
//						if (Alert_CID_RSRQ.get(i).peekfront().getCell_Id() == add_data.getCell_Id())// 判断队列里面CELL-ID是否存在
//						{
//							while (judge_time_hour( Alert_CID_RSRQ.get(i).peekrear().getextime(), Alert_CID_RSRQ.get(i).peekfront().getextime() ))// 如果队尾减队首的时间小于一个小时,插入数据,否则删除队头数据
//							{
//								Alert_CID_RSRQ.get(i).removefront();// 删除队头元素
//							}
//
//							Alert_CID_RSRQ.get(i).insert(add_data);// 插入数据
//																	// ,该句一定执行，因为只有一个数据的时候
//																	// while循环一定不满足条件
//							if (Alert_CID_RSRQ.get(i).isFull())// 如果已满 则报警
//																// return true
//							{
//								reQueue=Alert_CID_RSRQ.get(i);
//								return reQueue;
//							}
//							break;
//						}
//
//					}
//					if (i == Alert_CID_RSRQ.size())// 如果满足该条件，说明 CELL-ID是新的
//					{
//						Alert_CID_RSRQ.add(new AlertQueue(30));// 新建队列
//						Alert_CID_RSRQ.get(i).insert(add_data);// 在新建的队列中插入该元素
//					}
//
//				}
//			}
//			break;
//		case 5:
//			if (Integer.parseInt(add_data.getRSSI()) < -20 || Integer.parseInt(add_data.getRSSI()) > -3)// RSRQ
//																										// 正常范围-90~-25
//			{
//				if (Alert_CID_RSSI.size() == 0)
//				{
//					Alert_CID_RSSI.add(new AlertQueue(30));// 新建队列，加入这条数据
//					Alert_CID_RSSI.get(0).insert(add_data);
//				} else
//				{
//					for (i = 0; i < Alert_CID_RSSI.size(); i++) // 遍历查询是否存在 id
//					{
//						if (Alert_CID_RSSI.get(i).peekfront().getCell_Id() == add_data.getCell_Id())// 判断队列里面CELL-ID是否存在
//						{
//							while (judge_time_hour( Alert_CID_RSSI.get(i).peekrear().getextime(), Alert_CID_RSSI.get(i).peekfront().getextime() ))// 如果队尾减队首的时间小于一个小时,插入数据,否则删除队头数据
//							{
//								Alert_CID_RSSI.get(i).removefront();// 删除队头元素
//							}
//
//							Alert_CID_RSSI.get(i).insert(add_data);// 插入数据
//																	// ,该句一定执行，因为只有一个数据的时候
//																	// while循环一定不满足条件
//							if (Alert_CID_RSSI.get(i).isFull())// 如果已满 则报警
//																// return true
//							{
//								reQueue=Alert_CID_RSSI.get(i);
//								return reQueue;
//							}
//							break;
//						}
//
//					}
//					if (i == Alert_CID_RSSI.size())// 如果满足该条件，说明 CELL-ID是新的
//					{
//						Alert_CID_RSSI.add(new AlertQueue(30));// 新建队列
//						Alert_CID_RSSI.get(i).insert(add_data);// 在新建的队列中插入该元素
//					}
//
//				}
//			}
//			break;
//		case 6:
//			if (Integer.parseInt(add_data.getRSRP()) < -140 || Integer.parseInt(add_data.getRSRP()) > -44)// RSRP
//			// 正常范围-140~-44
//			{
//				if (Alert_LAC_RSRP.size() == 0)
//				{
//					Alert_LAC_RSRP.add(new AlertQueue(30));// 新建队列，加入这条数据
//					Alert_LAC_RSRP.get(0).insert(add_data);
//				} else
//				{
//					for (i = 0; i < Alert_LAC_RSRP.size(); i++) // 遍历查询是否存在 id
//					{
//						if (Alert_LAC_RSRP.get(i).peekfront().getLAC() == add_data.getLAC())// 判断队列里面LAC是否存在
//						{
//							while (judge_time_hour( Alert_LAC_RSRP.get(i).peekrear().getextime(), Alert_LAC_RSRP.get(i).peekfront().getextime() ))// 如果队尾减队首的时间小于一个小时,插入数据,否则删除队头数据
//							{
//								Alert_LAC_RSRP.get(i).removefront();// 删除队头元素
//							}
//
//							Alert_LAC_RSRP.get(i).insert(add_data);// 插入数据
//							// ,该句一定执行，因为只有一个数据的时候
//							// while循环一定不满足条件
//							if (Alert_LAC_RSRP.get(i).isFull())// 如果已满 则报警
//							// return true
//							{
//								reQueue=Alert_LAC_RSRP.get(i);
//								return reQueue;
//							}
//							break;
//						}
//
//					}
//					if (i == Alert_LAC_RSRP.size())// 如果满足该条件，说明 CELL-ID是新的
//					{
//						Alert_LAC_RSRP.add(new AlertQueue(30));// 新建队列
//						Alert_LAC_RSRP.get(i).insert(add_data);// 在新建的队列中插入该元素
//					}
//
//				}
//			}
//			break;
//		case 7:
//			if (Integer.parseInt(add_data.getRSRQ()) < -20 || Integer.parseInt(add_data.getRSRQ()) > -3)// RSRQ
//			// 正常范围-20~-3
//			{
//				if (Alert_LAC_RSRQ.size() == 0)
//				{
//					Alert_LAC_RSRQ.add(new AlertQueue(30));// 新建队列，加入这条数据
//					Alert_LAC_RSRQ.get(0).insert(add_data);
//				} else
//				{
//					for (i = 0; i < Alert_LAC_RSRQ.size(); i++) // 遍历查询是否存在 id
//					{
//						if (Alert_LAC_RSRQ.get(i).peekfront().getLAC() == add_data.getLAC())// 判断队列里面CELL-ID是否存在
//						{
//							while (judge_time_hour( Alert_LAC_RSRQ.get(i).peekrear().getextime(), Alert_LAC_RSRQ.get(i).peekfront().getextime() ))// 如果队尾减队首的时间小于一个小时,插入数据,否则删除队头数据
//							{
//								Alert_LAC_RSRQ.get(i).removefront();// 删除队头元素
//							}
//
//							Alert_LAC_RSRQ.get(i).insert(add_data);// 插入数据
//							// ,该句一定执行，因为只有一个数据的时候
//							// while循环一定不满足条件
//							if (Alert_LAC_RSRQ.get(i).isFull())// 如果已满 则报警
//							// return true
//							{
//								reQueue=Alert_LAC_RSRQ.get(i);
//								return reQueue;
//							}
//							break;
//						}
//
//					}
//					if (i == Alert_LAC_RSRQ.size())// 如果满足该条件，说明 CELL-ID是新的
//					{
//						Alert_LAC_RSRQ.add(new AlertQueue(30));// 新建队列
//						Alert_LAC_RSRQ.get(i).insert(add_data);// 在新建的队列中插入该元素
//					}
//
//				}
//			}
//			break;
//		case 8:
//			if (Integer.parseInt(add_data.getRSSI()) < -20 || Integer.parseInt(add_data.getRSSI()) > -3)// RSRQ
//			// 正常范围-90~-25
//			{
//				if (Alert_LAC_RSSI.size() == 0)
//				{
//					Alert_LAC_RSSI.add(new AlertQueue(30));// 新建队列，加入这条数据
//					Alert_LAC_RSSI.get(0).insert(add_data);
//				} else
//				{
//					for (i = 0; i < Alert_LAC_RSSI.size(); i++) // 遍历查询是否存在 id
//					{
//						if (Alert_LAC_RSSI.get(i).peekfront().getLAC() == add_data.getLAC())// 判断队列里面CELL-ID是否存在
//						{
//							while (judge_time_hour( Alert_LAC_RSSI.get(i).peekrear().getextime(), Alert_LAC_RSSI.get(i).peekfront().getextime() ))// 如果队尾减队首的时间小于一个小时,插入数据,否则删除队头数据
//							{
//								Alert_LAC_RSSI.get(i).removefront();// 删除队头元素
//							}
//
//							Alert_LAC_RSSI.get(i).insert(add_data);// 插入数据
//							// ,该句一定执行，因为只有一个数据的时候
//							// while循环一定不满足条件
//							if (Alert_LAC_RSSI.get(i).isFull())// 如果已满 则报警
//							// return true
//							{
//								reQueue=Alert_LAC_RSSI.get(i);
//								return reQueue;
//							}
//							break;
//						}
//
//					}
//					if (i == Alert_LAC_RSSI.size())// 如果满足该条件，说明 CELL-ID是新的
//					{
//						Alert_LAC_RSSI.add(new AlertQueue(30));// 新建队列
//						Alert_LAC_RSSI.get(i).insert(add_data);// 在新建的队列中插入该元素
//					}
//
//				}
//			}
//		default:
//			break;
//		}
//		return reQueue;
//
//	}
//
//	private  final static boolean judge_time_hour(String time1, String time2)
//	{
//
//		try
//		{
//			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			java.util.Date t1 = df.parse(time1);
//			java.util.Date t2 = df.parse(time2);
//			long l = t2.getTime() - t1.getTime();
//			long hour = l / (60 * 60 * 1000);// 小时差,大于一小时 则返回true
//			if (hour > 1)
//				return true;
//		} catch (Exception e)
//		{
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return false;
//
//	}
//}
