package com.autoreport.activity;


import com.autoreport.app.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;


public class SplashActivity extends Activity
{
	/**
	 * Called when the activity is first created.
	 */

	@Override
	public void onCreate(Bundle icicle)
	{
		super.onCreate(icicle);
		getWindow().setFormat(PixelFormat.RGBA_8888);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash);

		new Handler().postDelayed(new Runnable()
		{
			public void run()
			{
				Intent mainIntent = new Intent(SplashActivity.this, GridActivity.class);
				SplashActivity.this.startActivity(mainIntent);
				SplashActivity.this.finish();
			}
		}, 2000); 

	}
}