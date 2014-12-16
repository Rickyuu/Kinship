package com.speed.kinship.view;
/*# CSIT 6000B    
 *# Liang You      20229016   yliangao@connect.ust.hk
 *# Zhan Xiaojun   20244793   xzhanab@connect.ust.hk
 *# Tao Ye         20225905   ytaoac@connect.ust.hk  
 * */ 
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.speed.kinship.controller.MemoryHandler;
import com.speed.kinship.controller.impl.MemoryHandlerImpl;
import com.speed.kinship.model.Identity;
import com.speed.kinship.model.Memory;
import com.speed.kinship.model.User;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

@SuppressLint("SimpleDateFormat") public class MemoryCreateActivity extends Activity {
	private EditText memoryTitleEdit;
	
	private DatePicker memoryDate;
	
	private Button memoryPost;
	private int id;
	private String identity;
	private String userName;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.memorycreate);
		memoryTitleEdit=(EditText) findViewById(R.id.memoryEditTitle);
		memoryPost=(Button) findViewById(R.id.memoryPost);
		
		memoryDate=(DatePicker) findViewById(R.id.memoryEditDate);
		
		Intent intent=getIntent();
		id=Integer.parseInt(intent.getStringExtra("id"));
		identity=intent.getStringExtra("identity");
		userName=intent.getStringExtra("userName");

		final User user=new User();
		if(identity.equals("PARENT")) {
			user.setIdentity(Identity.PARENT);
			user.setUserName(userName);
			user.setId(id);
		} else {
			user.setIdentity(Identity.CHILD);
			user.setUserName(userName);
			user.setId(id);
		}
		
		
		memoryPost.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String title=memoryTitleEdit.getText().toString();
				//String time=memoryTimeEdit.getText().toString();
				String dateChosen=String.valueOf(memoryDate.getYear())+"-"+String.valueOf(memoryDate.getMonth()+1)+"-"+String.valueOf(memoryDate.getDayOfMonth());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date=null;
				try {
					date = sdf.parse(dateChosen);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				addMemoryAsyncTask addMemory=new addMemoryAsyncTask(user,date,title);
				addMemory.execute( );
				
			}
			
		});
		
		
	}
	private class addMemoryAsyncTask extends AsyncTask<Void,Void,Memory> {
		
		private User user;
		private Date date;
		private String str;
		

		public addMemoryAsyncTask(User user, Date date, String str) {
			super();
			this.user = user;
			this.date = date;
			this.str = str;
		}

		@Override
		protected Memory doInBackground(Void... params) {
			// TODO Auto-generated method stub
			MemoryHandler memoryHandler=new MemoryHandlerImpl( );
			return memoryHandler.addMemory(user, date, str);
		}

		@Override
		protected void onPostExecute(Memory result) {
			// TODO Auto-generated method stub
			if(result!=null) {
				Log.i("memoryadd", "success");
//				Intent intent=new Intent();
//				intent.setClass(MemoryCreateActivity.this, MemoryActivity.class);
//				intent.putExtra("id", String.valueOf(id));
//				intent.putExtra("identity", identity);
//				intent.putExtra("userName",userName);
//				startActivity(intent);
				MemoryCreateActivity.this.finish();
			} else {
				Log.i("memoryadd","fail");
			}
		}
		
	}

}
