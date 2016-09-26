package com.autoreport.view;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.autoreport.app.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
//import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 该类为自定义ImageView，用于显示背景图片，并显示背景图片到移动效果
 *
 */
public class GridBackImageView extends ImageView
{
	private Bitmap back; // 背景图片资源
	private Bitmap mBitmap; // 生成位图
	private double startX = -80; // 移动起始X坐标
	private int backPictureId = R.drawable.rootblock_default_bg9;
	private Context contextBack;
	
	// 构造函数中必须有context,attributeSet这两个 参数，否则父类无法调用
	public GridBackImageView(Context context, AttributeSet attributeSet)
	{
		super(context, attributeSet);
		contextBack=context;
		
		selectBackPicture(context);

		final Handler handler = new Handler()
		{
			public void handleMessage(Message msg)
			{
				if (msg.what == 1)
				{
					// Log.i("TAG", "-----"+startX);
					if (startX <= -260)
					{
						selectBackPicture(contextBack);
						
						startX = -80;
					} else
					{
						startX -= 0.09;
					}
				}
				invalidate();
			}
		};
		new Timer().schedule(new TimerTask()
		{
			@Override
			public void run()
			{
				handler.sendEmptyMessage(1);
			}
		}, 0, 10);

	}

	@Override
	public void onDraw(Canvas canvas)
	{
		// Log.i("TAG", "-----onDraw");
		Bitmap bitmap2 = Bitmap.createBitmap(mBitmap);
		canvas.drawBitmap(mBitmap, (float) startX, 0, null);
	}

	private void selectBackPicture(Context context)
	{
		// 由于不是Activity子类，只能通过DisplayMetrics来获取屏幕信息
		DisplayMetrics dm = getResources().getDisplayMetrics();

		// 屏幕宽度
		int screenWidth = dm.widthPixels;
		// 屏幕高度
		int screenHeight = dm.heightPixels;

		// 随机选择一张背景图片
		int rand = new Random().nextInt(11);

		switch (rand)
		{
		case 0:
			backPictureId = R.drawable.rootblock_default_bg;
			break;
		case 1:
			backPictureId = R.drawable.rootblock_default_bg1;
			break;
		case 2:
			backPictureId = R.drawable.rootblock_default_bg2;
			break;
		case 3:
			backPictureId = R.drawable.rootblock_default_bg3;
			break;
		case 4:
			backPictureId = R.drawable.rootblock_default_bg4;
			break;
		case 5:
			backPictureId = R.drawable.rootblock_default_bg5;
			break;
		case 6:
			backPictureId = R.drawable.rootblock_default_bg6;
			break;
		case 7:
			backPictureId = R.drawable.rootblock_default_bg7;
			break;
		case 8:
			backPictureId = R.drawable.rootblock_default_bg8;
			break;
		case 9:
			backPictureId = R.drawable.rootblock_default_bg9;
			break;
		case 10:
			backPictureId = R.drawable.rootblock_default_bg10;
			break;
		default:
			backPictureId = R.drawable.rootblock_default_bg9;
			break;
		}
		back = BitmapFactory.decodeResource(context.getResources(), backPictureId);

		mBitmap = Bitmap.createScaledBitmap(back, screenWidth * 3, screenHeight, true);
	}
}
