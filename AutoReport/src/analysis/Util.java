package analysis;

public class Util
{
	/**
	 * 计算UE 期望
	 * @param value
	 * @return
	 */
	public static double calExp(double[] value)
	{
		double exp = 0;
		for (int i = 0; i < value.length; i++)
		{
			exp += value[i];
		}
		exp = exp / value.length;
		return exp;
	}
	/**
	 * 计算RSRP对应区间
	 * @param value
	 * @return
	 */
	public static int getRsrpIndex(double value)
	{
		if (value < -120)
		{
			return 0;
		} else if (value < -115)
		{
			return 1;
		} else if (value < -80)
		{
			return 2 + (int)( value + 115);
		} else if (value < -60)
		{
			return 37 + (int)(( value + 80) / 2);
		} else
		{
			return 47;
		}
	}

	/**
	 * 计算RSRQ对应区间
	 * @param value
	 * @return
	 */
	public static int getRsrqIndex(double value)
	{
		if(value<19.5)
		{
			return 0;
		}else if (value>-3.5)
		{
			return 17;
		}else
		{
			return (int)(19.5+value)+1;
		}
	}
	/**
	 * 计算Rsrp加权期望
	 * @param value
	 * @return
	 */
	public static double calRsrpExp(double[] value)
	{
		
	}
	/**
	 * 计算Rsrq加权期望
	 * @param value
	 * @return
	 */
	public static double calRsrqExp(double[] value)
	{
		
	}
	/**
	 * 计算PRB加权期望
	 * @param value
	 * @return
	 */
	public static double calPrbExp(double[] value)
	{
		
	}
	/**
	 * 计算PLR加权期望（上行、下行）
	 * @param value6
	 * @param value8
	 * @param value9
	 * @return
	 */
	public static double calPlrExp(double[] value6,double[] value8,double[] value9)
	{
		
	}
	/**
	 * 计算上行信噪比加权期望
	 * @param value
	 * @return
	 */
	public static double calUlSinrExp(double[] value)
	{
		
	}
}
