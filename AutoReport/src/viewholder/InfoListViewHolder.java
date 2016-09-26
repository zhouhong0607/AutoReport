package viewholder;

import com.autoreport.app.R;
import com.autoreport.datamodel.BaseInfo;
import com.autoreport.datamodel.Info;


import android.view.View;
import android.widget.TextView;

public class InfoListViewHolder implements MyViewHolder
{
	private TextView info_list_view;
	@Override
	public void findView(View fromView)
	{
		info_list_view = (TextView) fromView.findViewById(R.id.item_view);
	}

	@Override
	public void setData(Info info)
	{
		BaseInfo allInfo=(BaseInfo)info;
		info_list_view.setText(allInfo.getExcepTime());
	}
}
