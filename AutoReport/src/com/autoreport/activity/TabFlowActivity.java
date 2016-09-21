package com.autoreport.activity;

import java.util.List;

import com.autoreport.adapter.InfoFlowAdapter;
import com.autoreport.adapter.LinkedHorizontalScrollView;
import com.autoreport.adapter.LvInfoAdapter;
import com.autoreport.adapter.LvNameAdapter;
import com.autoreport.adapter.NoScrollHorizontalScrollView;
import com.autoreport.app.R;
import com.autoreport.database.DatabaseOperator;
import com.autoreport.database.InfoDatabase;
import com.autoreport.datastructure.AutoreportApp;
import com.autoreport.datastructure.Info;
import com.autoreport.datastructure.SignalInfo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.TextView;
/**
 * 流量信息展示界面
 * @author 周宏
 *
 */
public class TabFlowActivity extends Activity
{
	private int position;
	private TextView flowExcepTitle;
	private TextView flowExcepInfo;

	private ListView flowListInfo;
	private InfoFlowAdapter flowListAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_flow);

		Intent intent = getIntent();
		position = intent.getIntExtra("position", 0);
		Info info = AutoreportApp.infolist.get(position);

		flowExcepTitle = (TextView) findViewById(R.id.flow_excep_title);
		flowExcepInfo = (TextView) findViewById(R.id.flow_excep_info);


		flowListInfo=(ListView)findViewById(R.id.tab_flow_list);
		
		flowExcepTitle.setTextColor(Color.RED);


		flowExcepTitle.append("异常判决依据");
		flowExcepInfo.append(info.getExcepType());
	



		// 查询对应的流量信息
		InfoDatabase infoDatabase = new InfoDatabase(this, "AutoReprt.db", null, 1);// 创建数据库
		DatabaseOperator databaseOperator = new DatabaseOperator(infoDatabase);
		List<SignalInfo> signalInfos = databaseOperator.queryFromSignalInfoById(info.getId());
		databaseOperator.CloseDatabase();

		String siglist = "";
		if (signalInfos.size() != 0)
		{
			// 标注流量最大值和未通过通信测试点
			int maxIndex = info.getMaxIndex();
			int noRxIndex = info.getNoRxIndex();
			if (maxIndex >= 0)
			{
				String value = signalInfos.get(maxIndex).getRxByte();
				signalInfos.get(maxIndex).setRxByte(value + "(最大值)");

			}
			if (noRxIndex >= 0)
			{
				String value = signalInfos.get(noRxIndex).getRxByte();
				signalInfos.get(noRxIndex).setRxByte(value + "(未通过通信测试)");
			}

		}



		
		flowListAdapter=new InfoFlowAdapter(TabFlowActivity.this, R.layout.tab_flow_list_item, signalInfos);
		flowListInfo.setAdapter(flowListAdapter);
		
	}

}
