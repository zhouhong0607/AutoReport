package analysis;

public class UeData {
	double[] iRsrp;
	double[] iRsrq;
	double expRsrp;
	double expRsrq;
	int indexRsrp;
	int indexRsrq;
	
	public UeData(String rsrp ,String rsrq)
	{
		String[] srsrp=rsrp.split(" ");
		String[] srsrq=rsrq.split(" ");
		iRsrp=new double[srsrp.length];
		iRsrq=new double[srsrq.length];
		for(int i=0;i<srsrp.length;i++)
		{
			iRsrp[i]=Double.parseDouble(srsrp[i]);
			iRsrq[i]=Double.parseDouble(srsrq[i]);
		}
		expRsrp=Util.calExp(iRsrp);
		expRsrq=Util.calExp(iRsrq);
		indexRsrp=Util.getRsrpIndex(expRsrp);
		indexRsrq=Util.getRsrqIndex(expRsrq);
		
	}
	
	
	
	
	
	
	
//	public double getExpRsrp() {
//		return expRsrp;
//	}
//	public void setExpRsrp(double expRsrp) {
//		this.expRsrp = expRsrp;
//	}
//	public double getExpRsrq() {
//		return expRsrq;
//	}
//	public void setExpRsrq(double expRsrq) {
//		this.expRsrq = expRsrq;
//	}
//	public int getIndexRsrp() {
//		return indexRsrp;
//	}
//	public void setIndexRsrp(int indexRsrp) {
//		this.indexRsrp = indexRsrp;
//	}
//	public int getIndexRsrq() {
//		return indexRsrq;
//	}
//	public void setIndexRsrq(int indexRsrq) {
//		this.indexRsrq = indexRsrq;
//	}
//	
//	public double[] getiRsrp() {
//		return iRsrp;
//	}
//	public void setiRsrp(double[] iRsrp) {
//		this.iRsrp = iRsrp;
//	}
//	public double[] getiRsrq() {
//		return iRsrq;
//	}
//	public void setiRsrq(double[] iRsrq) {
//		this.iRsrq = iRsrq;
//	}
	
	
	
}
