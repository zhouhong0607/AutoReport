package viewholder;

import com.autoreport.app.R;
import com.autoreport.datamodel.Info;
import com.autoreport.datamodel.SignalInfo;

import android.view.View;
import android.widget.TextView;

public class InfoWirelessNameViewHolder implements MyViewHolder
{
	private	TextView timeStamp;
	@Override
	public void findView(View fromView)
	{
	timeStamp=(TextView)fromView.findViewById(R.id.timestamp);
	}

	@Override
	public void setData(Info info)
	{
		SignalInfo allInfo = (SignalInfo) info;
		timeStamp.setText(allInfo.getTimeStamp());
	}
}
