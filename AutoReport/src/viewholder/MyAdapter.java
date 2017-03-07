package viewholder;

import java.util.List;
import com.autoreport.datamodel.Info;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
/**
 * 试图把Adapter抽象出来
 * 未成功
 * @author 周宏
 *
 */

public class MyAdapter extends ArrayAdapter<Info>
{
	private int resourceId;
	private static MyViewHolder viewHolder;
	public MyAdapter(Context context, int textViewResourceId, List<Info> objects,MyViewHolder viewholder)
	{
		super(context, textViewResourceId, objects);
		resourceId = textViewResourceId;
		viewHolder=viewholder;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		// TODO Auto-generated method stub
		View view = convertView;
		if (view == null)
		{
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
			viewHolder.findView(convertView);
			view.setTag(viewHolder);
		} else
		{
			viewHolder = (MyViewHolder)view.getTag();
		}
	
		Info info = getItem(position);// 获取当前Info实例
		viewHolder.setData(info);
		return view;
	}
}
