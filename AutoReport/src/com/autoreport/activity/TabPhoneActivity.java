package com.autoreport.activity;



import com.autoreport.app.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class TabPhoneActivity extends Activity
{
	 private int position;
	
	TextView textView;
	public void onCreate(Bundle savedInstanceState) {  
	       super.onCreate(savedInstanceState);  
	       setContentView(R.layout.tab_phone);
	       
	   	Intent intent=getIntent();
		position=intent.getIntExtra("position", 0);
	       
	       textView=(TextView)findViewById(R.id.textView1);
	       textView.setText("this is phone"+position);
	    }  
}
