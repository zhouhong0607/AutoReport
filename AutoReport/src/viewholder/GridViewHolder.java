package viewholder;

import com.autoreport.app.R;
import com.autoreport.datamodel.ImageInfo;
import com.autoreport.datamodel.Info;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GridViewHolder implements MyViewHolder
{
	 private ImageView imageView;
	 private RelativeLayout relativeLayout;
	 private TextView textView;
	 public GridViewHolder()
	{
		// TODO Auto-generated constructor stub
	}
	 
	 
	@Override
	public void findView(View fromView)
	{
		imageView = (ImageView) fromView.findViewById(R.id.imageView1);
		relativeLayout = (RelativeLayout) fromView.findViewById(R.id.relativeLayout);
		textView = (TextView) fromView.findViewById(R.id.msg);
	}

	@Override
	public void setData(Info info)
	{
		ImageInfo allInfo=(ImageInfo)info;
		imageView.setImageResource(allInfo.getImageId());
		relativeLayout.setBackgroundResource(allInfo.getBgId());
		relativeLayout.getBackground().setAlpha(225);
		textView.setText(allInfo.getImageMsg());
	}

}
