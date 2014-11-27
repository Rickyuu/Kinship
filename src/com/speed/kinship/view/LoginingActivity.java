package com.speed.kinship.view;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoginingActivity extends Activity {
	private TextView welcome;
	private ProgressBar p;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.logining);
		welcome=(TextView) findViewById(R.id.welcome);
		p=(ProgressBar) findViewById(R.id.progressBar1);
		String name=" ";
		String num=" ";
		int type;
		long callTime;
        Date date;
        String time= "";
		ContentResolver cr=getContentResolver();
		Cursor cursor = cr.query(CallLog.Calls.CONTENT_URI, new String[]{CallLog.Calls.NUMBER,CallLog.Calls.CACHED_NAME,
				CallLog.Calls.TYPE, CallLog.Calls.DATE}, null, null,CallLog.Calls.DEFAULT_SORT_ORDER);
		for(int i=0;i<cursor.getCount();i++) {
			cursor.moveToPosition(i);
			num=cursor.getString(0);
			name=cursor.getString(1);
			type=cursor.getInt(2);
			SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			date = new Date(Long.parseLong(cursor.getString(3)));
			time = sfd.format(date);
			if(num.equals("54897349")) {
				welcome.setText("Last contract time is"+date);
				Timer timer=new Timer();
				TimerTask task=new TimerTask(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						Intent intent=new Intent(LoginingActivity.this,StateActivity.class);
						startActivity(intent);
					}
					
				};
				timer.schedule(task, 2000);
			}
		}
	}
	

}
