package com.autoreport.activity;

import com.autoreport.app.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class TabWirelessActivity extends Activity
{
	TextView textView;
	public void onCreate(Bundle savedInstanceState) {  
	       super.onCreate(savedInstanceState);  
	       setContentView(R.layout.tab_wireless);
	       textView=(TextView)findViewById(R.id.textView3);
	       textView.setText("this is wireless");
	    }  
}