package viewholder;

import com.autoreport.datamodel.ImageInfo;
import com.autoreport.datamodel.Info;

import android.view.View;

public interface MyViewHolder
{
	public void findView(View view);
	public void setData(Info info);
}
