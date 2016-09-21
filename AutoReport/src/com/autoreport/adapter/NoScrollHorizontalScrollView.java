package com.autoreport.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/**
 * 固定滚动条View
 * @author Administrator
 *
 */
public class NoScrollHorizontalScrollView extends HorizontalScrollView
{
	public NoScrollHorizontalScrollView(Context context)
	{
		super(context);
	}

	public NoScrollHorizontalScrollView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public NoScrollHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
	}

	@Override
	public boolean onInterceptHoverEvent(MotionEvent event)
	{
		return false;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev)
	{
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev)
	{
		return false;
	}
}
