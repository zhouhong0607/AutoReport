package com.autoreport.activity;

import com.autoreport.app.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class TabFlowActivity extends Activity
{
	TextView textView;
	public void onCreate(Bundle savedInstanceState) {  
	       super.onCreate(savedInstanceState);  
	       setContentView(R.layout.tab_flow);
	       textView=(TextView)findViewById(R.id.textView4);
	       textView.setText("this is flow");
	    }  
}
