package viewholder;

import com.autoreport.app.R;
import com.autoreport.datamodel.ImageInfo;
import com.autoreport.datamodel.Info;
import com.autoreport.datamodel.SignalInfo;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class InfoFlowViewHolder implements MyViewHolder
{
	private TextView flowListTime;
	private TextView flowListTx;
	private TextView flowListRx;

	@Override
	public void findView(View fromView)
	{
		flowListTime = (TextView) fromView.findViewById(R.id.flow_list_time);
		flowListTx = (TextView) fromView.findViewById(R.id.flow_list_tx);
		flowListRx = (TextView) fromView.findViewById(R.id.flow_list_rx);
	}

	@Override
	public void setData(Info info)
	{
		
		SignalInfo allInfo=(SignalInfo)info;
		flowListTime.setText(allInfo.getTimeStamp());
		flowListTx.setText(allInfo.getTxByte());
		flowListRx.setText(allInfo.getRxByte());
	}

}
