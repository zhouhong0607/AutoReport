package analysis;

public class MrData
{
	
	
	private	double[] mrRsrp;
	private	double[] mrRsrq;
	private	double[] mrPrbNum;
	private	double[] mrPlrUl6;
	private	double[] mrPlrUl8;
	private	double[] mrPlrUl9;
	private	double[] mrPlrDl6;
	private	double[] mrPlrDl8;
	private	double[] mrPlrDl9;
	private	double[] mrSinrUl;
	
	private	double expMrRsrp;
	private	double expMrRsrq;
	private	double expMrPrbNum;
	private	double expMrPlrUl;
	private	double expMrPlrDl;
	private	double expMrSinrUl;
	
	private	int indexExpRsrp;
	private	int indexExpRsrq;
	private	int indexExpSinrUl;
	
	private double sumMrRsrp;//Rsrp的样本数量合 ， 请在 数据初始化中计算
	private double sumMrRsrq;//Rsrq同上
	
	
	public MrData()
	{
		/**
		 * 数据初始化
		 */
	
		/**
		 * 数据初始化
		 */
		expMrRsrp=Util.calRsrpExp(mrRsrp);
		indexExpRsrp=Util.getRsrpIndex(expMrRsrp);
		expMrRsrq=Util.calRsrqExp(mrRsrq);
		indexExpRsrq=Util.getRsrqIndex(expMrRsrq);
		expMrPrbNum=Util.calPrbExp(mrPrbNum);
		expMrPlrUl=Util.calPlrExp(mrPlrUl6, mrPlrUl8, mrPlrUl9);
		expMrPlrDl=Util.calPlrExp(mrPlrDl6, mrPlrDl8, mrPlrDl9);
		expMrSinrUl=Util.calUlSinrExp(mrSinrUl);
		indexExpSinrUl=Util.getSinrUlIndex(expMrSinrUl);
		
	}
	
	
	
	public double getExpMrRsrp() {
		return expMrRsrp;
	}



	public void setExpMrRsrp(double expMrRsrp) {
		this.expMrRsrp = expMrRsrp;
	}



	public double getExpMrRsrq() {
		return expMrRsrq;
	}



	public void setExpMrRsrq(double expMrRsrq) {
		this.expMrRsrq = expMrRsrq;
	}



	public double getExpMrPrbNum() {
		return expMrPrbNum;
	}



	public void setExpMrPrbNum(double expMrPrbNum) {
		this.expMrPrbNum = expMrPrbNum;
	}



	public double getExpMrPlrUl() {
		return expMrPlrUl;
	}



	public void setExpMrPlrUl(double expMrPlrUl) {
		this.expMrPlrUl = expMrPlrUl;
	}



	public double getExpMrPlrDl() {
		return expMrPlrDl;
	}



	public void setExpMrPlrDl(double expMrPlrDl) {
		this.expMrPlrDl = expMrPlrDl;
	}



	public double getExpMrSinrUl() {
		return expMrSinrUl;
	}



	public void setExpMrSinrUl(double expMrSinrUl) {
		this.expMrSinrUl = expMrSinrUl;
	}



	
	
	
	
	public double[] getMrPlrUl6() {
		return mrPlrUl6;
	}


	public void setMrPlrUl6(double[] mrPlrUl6) {
		this.mrPlrUl6 = mrPlrUl6;
	}


	public double[] getMrPlrUl8() {
		return mrPlrUl8;
	}


	public void setMrPlrUl8(double[] mrPlrUl8) {
		this.mrPlrUl8 = mrPlrUl8;
	}


	public double[] getMrPlrUl9() {
		return mrPlrUl9;
	}


	public void setMrPlrUl9(double[] mrPlrUl9) {
		this.mrPlrUl9 = mrPlrUl9;
	}


	public double[] getMrPlrDl6() {
		return mrPlrDl6;
	}


	public void setMrPlrDl6(double[] mrPlrDl6) {
		this.mrPlrDl6 = mrPlrDl6;
	}


	public double[] getMrPlrDl8() {
		return mrPlrDl8;
	}


	public void setMrPlrDl8(double[] mrPlrDl8) {
		this.mrPlrDl8 = mrPlrDl8;
	}


	public double[] getMrPlrDl9() {
		return mrPlrDl9;
	}


	public void setMrPlrDl9(double[] mrPlrDl9) {
		this.mrPlrDl9 = mrPlrDl9;
	}
	
	
	
	public double[] getMrRsrp() {
		return mrRsrp;
	}
	public void setMrRsrp(double[] mrRsrp) {
		this.mrRsrp = mrRsrp;
	}
	public double[] getMrRsrq() {
		return mrRsrq;
	}
	public void setMrRsrq(double[] mrRsrq) {
		this.mrRsrq = mrRsrq;
	}
	public double[] getMrPrbNum() {
		return mrPrbNum;
	}
	public void setMrPrbNum(double[] mrPrbNum) {
		this.mrPrbNum = mrPrbNum;
	}

	public double[] getMrSinrUl() {
		return mrSinrUl;
	}
	public void setMrSinrUl(double[] mrSinrUl) {
		this.mrSinrUl = mrSinrUl;
	}



	public int getIndexExpRsrp()
	{
		return indexExpRsrp;
	}



	public void setIndexExpRsrp(int indexExpRsrp)
	{
		this.indexExpRsrp = indexExpRsrp;
	}



	public int getIndexExpRsrq()
	{
		return indexExpRsrq;
	}



	public void setIndexExpRsrq(int indexExpRsrq)
	{
		this.indexExpRsrq = indexExpRsrq;
	}



	public int getIndexExpSinrUl()
	{
		return indexExpSinrUl;
	}



	public void setIndexExpSinrUl(int indexExpSinrUl)
	{
		this.indexExpSinrUl = indexExpSinrUl;
	}



	public double getSumMrRsrp()
	{
		return sumMrRsrp;
	}



	public void setSumMrRsrp(double sumMrRsrp)
	{
		this.sumMrRsrp = sumMrRsrp;
	}



	public double getSumMrRsrq()
	{
		return sumMrRsrq;
	}



	public void setSumMrRsrq(double sumMrRsrq)
	{
		this.sumMrRsrq = sumMrRsrq;
	}
	
}