package viewholder;

import com.autoreport.app.R;
import com.autoreport.datamodel.Info;
import com.autoreport.datamodel.SignalInfo;

import android.view.View;
import android.widget.TextView;

public class InfoWirelessDetailViewHolder implements MyViewHolder
{
	private TextView rsrp;
	private TextView rsrq;
	private TextView sinr;
	private TextView pci;
	private TextView ci;
	private TextView enodebId;
	private TextView cellId;
	private TextView tac;
	private TextView netType;
	private TextView longitude;
	private TextView latitude;
	private TextView address;
	@Override
	public void findView(View fromView)
	{
		rsrp = (TextView) fromView.findViewById(R.id.rsrp);
		rsrq = (TextView) fromView.findViewById(R.id.rsrq);
		sinr = (TextView) fromView.findViewById(R.id.sinr);
		pci = (TextView) fromView.findViewById(R.id.pci);
		ci = (TextView) fromView.findViewById(R.id.ci);
		enodebId = (TextView) fromView.findViewById(R.id.enodeb_id);
		cellId = (TextView) fromView.findViewById(R.id.cell_id);
		tac = (TextView) fromView.findViewById(R.id.tac);
		netType = (TextView) fromView.findViewById(R.id.nettype);
		longitude = (TextView) fromView.findViewById(R.id.longitude);
		latitude = (TextView) fromView.findViewById(R.id.latitude);
		address = (TextView) fromView.findViewById(R.id.address);
	}

	@Override
	public void setData(Info info)
	{
		SignalInfo allInfo = (SignalInfo) info;
		rsrp.setText(allInfo.getRsrp());
		rsrq.setText(allInfo.getRsrq());
		sinr.setText(allInfo.getRssinr());
		pci.setText(allInfo.getPci());
		ci.setText(allInfo.getCi());
		enodebId.setText(allInfo.getEnodbId());
		cellId.setText(allInfo.getCellId());
		tac.setText(allInfo.getTac());
		netType.setText(allInfo.getNetType());
		longitude.setText(allInfo.getLongitude());
		latitude.setText(allInfo.getLatitude());
		address.setText(allInfo.getAddr());
	}

}
