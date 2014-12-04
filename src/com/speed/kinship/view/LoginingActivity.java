package com.speed.kinship.view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.speed.kinship.model.Identity;
import com.speed.kinship.model.User;

import android.app.Activity;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CallLog;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoginingActivity extends Activity {
	private TextView welcome;
	//private ProgressBar p;
	
	private Date date;
	private String num;
	private int numIndex;
	private int dateIndex;
	private String lastChild;
	private String lastMum;
	private String lastDad;
	private String phoneNum;
	private String phoneMum;
	private String phoneDad;
	private int id;
	private String identity;
	private String userName;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.logining);
		welcome=(TextView) findViewById(R.id.welcome);
		Intent intent=getIntent();
		String tr=intent.getStringExtra("id");
		id=Integer.parseInt(intent.getStringExtra("id"));
		identity=intent.getStringExtra("identity");
		userName=intent.getStringExtra("userName");
		SharedPreferences file = getSharedPreferences("kinship_setting"+id, Context.MODE_PRIVATE);
		String mom = file.getString("mother", "");
		String dad = file.getString("father", "");
		String chi = file.getString("child", "");
		
		if(identity.equals("PARENT")) {
			phoneNum=chi;
		} else if (identity.equals("CHILD")) {
			phoneMum=mom;
			phoneDad=dad;
		}
		
		String[] projection = { CallLog.Calls.DATE, // 日期  
                CallLog.Calls.NUMBER, // 号码  
                CallLog.Calls.TYPE, // 类型  
                CallLog.Calls.CACHED_NAME, // 名字  
                CallLog.Calls._ID, // id  
        };
		ContentResolver resolver = getContentResolver();
		
		final Cursor cr=resolver.query(CallLog.Calls.CONTENT_URI, projection, null, null, null);
		numIndex=cr.getColumnIndex(CallLog.Calls.NUMBER);
		dateIndex=cr.getColumnIndex(CallLog.Calls.DATE);
		if(identity.equals("PARENT")) {
			for(int i=cr.getCount()-1;i>-1;i--) {
				cr.moveToPosition(i);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				date = new Date(Long.parseLong(cr.getString(dateIndex)));
				String time = sdf.format(date);
				num=cr.getString(numIndex);
				if(num.equals(phoneNum)) {
					lastChild=time;
					break;
				}
			}
			welcome.setText("The date of the last contact is "+lastChild);
		}
		if(identity.equals("CHILD")) {
			for(int i=cr.getCount()-1;i>-1;i--) {
				cr.moveToPosition(i);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				date = new Date(Long.parseLong(cr.getString(dateIndex)));
				String time = sdf.format(date);
				num=cr.getString(numIndex);
				if((num.equals(phoneMum))) {
					lastMum=time;
					break;
				}
			}
			for(int i=cr.getCount()-1;i>-1;i--) {
				cr.moveToPosition(i);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				date = new Date(Long.parseLong(cr.getString(dateIndex)));
				String time = sdf.format(date);
				num=cr.getString(numIndex);
				if((num.equals(phoneDad))) {
					lastMum=time;
					break;
				}
			}
			welcome.setText("The date of the last contact with Mum is"+lastMum+"The date of the last contact with Dad is"+lastDad);
			
		}
		
		
		Timer timer=new Timer();
		TimerTask task=new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent=LoginingActivity.this.getIntent();
				intent.setClass(LoginingActivity.this, ThingActivity.class);
				intent.putExtra("id", String.valueOf(LoginingActivity.this.id));
				intent.putExtra("identity", LoginingActivity.this.identity);
				intent.putExtra("userName",LoginingActivity.this.userName);
				startActivity(intent);
			}
		};
		timer.schedule(task, 3000);
	}
}


