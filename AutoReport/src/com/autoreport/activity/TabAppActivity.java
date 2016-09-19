package com.autoreport.activity;

import com.autoreport.app.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class TabAppActivity extends Activity
{
	TextView textView;
	public void onCreate(Bundle savedInstanceState) {  
	       super.onCreate(savedInstanceState);  
	       setContentView(R.layout.tab_app);
	       textView=(TextView)findViewById(R.id.textView2);
	       textView.setText("this is app");
	    }  
}