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

import android.annotation.SuppressLint;
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
import android.util.Log;
import android.view.Gravity;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoginingActivity extends Activity {
	private TextView welcome;
	//private ProgressBar p;
	
	
	
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
	
	
	@SuppressLint("SimpleDateFormat") @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.logining);
		welcome=(TextView) findViewById(R.id.welcome);
		Intent intent=getIntent();
		id=Integer.parseInt(intent.getStringExtra("id"));
		identity=intent.getStringExtra("identity");
		userName=intent.getStringExtra("userName");
		SharedPreferences file = getSharedPreferences("kinship_setting"+id, Context.MODE_PRIVATE);
		String mom = file.getString("mother", "");
		String dad = file.getString("father", "");
		String chi = file.getString("child", "");
		if(mom != null) {
			Log.i("phoneMe", "mom"+mom+"mom");
		}
		if(dad != null) {
			Log.i("phoneMe", "dad"+dad+"dad");
		}
		if(chi != null) {
			Log.i("phoneMe", "chi"+chi+"chi");
		}
		
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
		boolean Momflag=true;
		boolean Dadflag=true;
		boolean Childflag=true;
		boolean MomflagOpposite=true;
		boolean ChildflagOpposite=true;
		boolean DadflagOpposite=true;
		String tempChild=null;
		String tempChildOpposite=null;
		String tempMom=null;
		String tempMomOpposite=null;
		String tempDad=null;
		String tempDadOpposite=null;
		Date date;
		Date date2;
		String num;
		String num2;
		
		for(int i=0;i<cr.getCount();i++) {
			cr.moveToPosition(i);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			date = new Date(Long.parseLong(cr.getString(dateIndex)));
			String time = sdf.format(date);
			num=cr.getString(numIndex);
			//
			cr.moveToPosition(cr.getCount()-1-i);
			date2 =new Date(Long.parseLong(cr.getString(dateIndex)));
			String time2 =sdf.format(date2);
			num2=cr.getString(numIndex);
			
			if(identity.equals("PARENT")) {
				if(Childflag) {
					if(num.equals(phoneNum)) {
						tempChild=time;
						Childflag=false;
					}
				}
				if(ChildflagOpposite) {
					if(num2.equals(phoneNum)) {
						tempChildOpposite=time2;
						ChildflagOpposite=false;
					}
				}
				if((Childflag==false)&&(ChildflagOpposite==false)) {
					if(tempChild.compareTo(tempChildOpposite)>0) {
						lastChild=tempChild;
					} else {
						lastChild=tempChildOpposite;
					}
					welcome.setText("The date of the last contact is\n"+lastChild);
					break;
				}
				
			}
			if(identity.equals("CHILD")) {
				if(Momflag) {
                	if((num.equals(phoneMum))) {
    					tempMom=time;
    					Momflag=false;
    				}
				}
				if(MomflagOpposite) {
					if((num2.equals(phoneMum))) {
    					tempMomOpposite=time2;
    					MomflagOpposite=false;
    				}
				}
                if(Dadflag) {
                	if((num.equals(phoneDad))) {
    					tempDad=time;
    					Dadflag=false;
    				}
                }
                
                if(DadflagOpposite) {
                	if((num2.equals(phoneDad))) {
    					tempDadOpposite=time2;
    					DadflagOpposite=false;
    				}
                }
                if(!phoneDad.equals("") && !phoneMum.equals("")) {
                	if((Momflag==false)&&(Dadflag==false)&&(DadflagOpposite==false)&&(MomflagOpposite==false)) {
                    	if(tempMom.compareTo(tempMomOpposite)>0) {
                    		lastMum=tempMom;
                    	} else {
                    		lastMum=tempMomOpposite;
                    	}
                    	if(tempDad.compareTo(tempDadOpposite)>0) {
                    		lastDad=tempDad;
                    	} else {
                    		lastDad=tempDadOpposite;
                    	}
                    	welcome.setText("The date of the last contact is\nwith mum: "+lastMum+"\nwith dad: "+lastDad);
            			welcome.setGravity(Gravity.CENTER);
                    	break;
                    }
                }
                if(phoneDad.equals("") && !phoneMum.equals("")) {
                	if((Momflag==false)&&(MomflagOpposite==false)) {
                		if(tempMom.compareTo(tempMomOpposite)>0) {
                    		lastMum=tempMom;
                    	} else {
                    		lastMum=tempMomOpposite;
                    	}
                		welcome.setText("The date of the last comtact with Mum is:" +lastMum);
                		break;
                	}
                	
                }
                if(!phoneDad.equals("") && phoneMum.equals("")) {
                	if((Dadflag==false)&&(DadflagOpposite==false)) {
                		if(tempDad.compareTo(tempDadOpposite)>0) {
                    		lastDad=tempDad;
                    	} else {
                    		lastDad=tempDadOpposite;
                    	}
                		welcome.setText("The date of the last comtact with Dad is:" +lastDad);
                		break;
                	}
                }
                
                
                
			}
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
				LoginingActivity.this.finish();
			}
		};
		timer.schedule(task, 5000);
	}
}


