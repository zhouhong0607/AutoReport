package com.autoreport.activity;

import com.autoreport.adapter.LinkedHorizontalScrollView;
import com.autoreport.adapter.LvInfoAdapter;
import com.autoreport.adapter.LvNameAdapter;
import com.autoreport.adapter.NoScrollHorizontalScrollView;
import com.autoreport.app.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.TextView;

public class TabWirelessActivity extends Activity
{
	private int position;

	private NoScrollHorizontalScrollView sv_normalgoods_title;// 不可滑动的顶部左侧的ScrollView
	private LinkedHorizontalScrollView sv_normalgoods_detail;// 底部左侧的ScrollView
	private ListView lv_normalgoodname;// 底部左侧的ListView
	private ListView lv_normalgood_info;// 底部右侧的ListView

	boolean isLeftListEnabled = false;
	boolean isRightListEnabled = false;

	private LvNameAdapter mLvNormalNameAdapter;
	private LvInfoAdapter mLvNormalInfoAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_wireless);

		Intent intent = getIntent();
		position = intent.getIntExtra("position", 0);

		initView();
		initAdapter();
	}

	private void initView()
	{
		sv_normalgoods_title = (NoScrollHorizontalScrollView) findViewById(R.id.sv_title);
		sv_normalgoods_detail = (LinkedHorizontalScrollView) findViewById(R.id.sv_good_detail);
		lv_normalgoodname = (ListView) findViewById(R.id.lv_goodname);
		lv_normalgood_info = (ListView) findViewById(R.id.lv_good_info);
		combination(lv_normalgoodname, lv_normalgood_info, sv_normalgoods_title, sv_normalgoods_detail);
	}

	private void initAdapter()
	{
		mLvNormalNameAdapter = new LvNameAdapter(this);
		mLvNormalInfoAdapter = new LvInfoAdapter(this);
		lv_normalgoodname.setAdapter(mLvNormalNameAdapter);
		lv_normalgood_info.setAdapter(mLvNormalInfoAdapter);
	}

	private void combination(final ListView lvName, final ListView lvDetail, final HorizontalScrollView title,
			LinkedHorizontalScrollView content)
	{
		/**
		 * 左右滑动同步
		 */
		content.setMyScrollChangeListener(new LinkedHorizontalScrollView.LinkScrollChangeListener()
		{
			@Override
			public void onscroll(LinkedHorizontalScrollView view, int x, int y, int oldx, int oldy)
			{
				title.scrollTo(x, y);
			}
		});

		/**
		 * 上下滑动同步
		 */
		// 禁止快速滑动
		lvName.setOverScrollMode(ListView.OVER_SCROLL_NEVER);
		lvDetail.setOverScrollMode(ListView.OVER_SCROLL_NEVER);
		// 左侧ListView滚动时，控制右侧ListView滚动
		lvName.setOnScrollListener(new AbsListView.OnScrollListener()
		{

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState)
			{
				// 这两个enable标志位是为了避免死循环
				if (scrollState == SCROLL_STATE_TOUCH_SCROLL)
				{
					isRightListEnabled = false;
					isLeftListEnabled = true;
				} else if (scrollState == SCROLL_STATE_IDLE)
				{
					isRightListEnabled = true;
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
			{
				View child = view.getChildAt(0);
				if (child != null && isLeftListEnabled)
				{
					lvDetail.setSelectionFromTop(firstVisibleItem, child.getTop());
				}
			}
		});

		// 右侧ListView滚动时，控制左侧ListView滚动
		lvDetail.setOnScrollListener(new AbsListView.OnScrollListener()
		{
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState)
			{
				if (scrollState == SCROLL_STATE_TOUCH_SCROLL)
				{
					isLeftListEnabled = false;
					isRightListEnabled = true;
				} else if (scrollState == SCROLL_STATE_IDLE)
				{
					isLeftListEnabled = true;
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
			{
				View c = view.getChildAt(0);
				if (c != null && isRightListEnabled)
				{
					lvName.setSelectionFromTop(firstVisibleItem, c.getTop());
				}
			}
		});

	}

}