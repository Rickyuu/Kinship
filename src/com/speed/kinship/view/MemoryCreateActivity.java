package com.speed.kinship.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.speed.kinship.controller.MemoryHandler;
import com.speed.kinship.controller.impl.MemoryHandlerImpl;
import com.speed.kinship.model.Identity;
import com.speed.kinship.model.Memory;
import com.speed.kinship.model.User;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MemoryCreateActivity extends Activity {
	private EditText memoryTitleEdit;
	private EditText memoryTimeEdit;
	private Button memoryPost;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.memorycreate);
		memoryTitleEdit=(EditText) findViewById(R.id.memoryEditTitle);
		memoryTimeEdit=(EditText) findViewById(R.id.memoryEditTime);
		memoryPost=(Button) findViewById(R.id.memoryPost);
		
		final User user=new User();
		user.setIdentity(Identity.PARENT);
		user.setUserName("tttty");
		user.setId(5);
		
		memoryPost.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String title=memoryTitleEdit.getText().toString();
				String time=memoryTimeEdit.getText().toString();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date=null;
				try {
					date = sdf.parse(time);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				addMemoryAsyncTask addMemory=new addMemoryAsyncTask(user,date,title);
				addMemory.execute( );
				Intent intent=new Intent();
				intent.setClass(MemoryCreateActivity.this, MemoryActivity.class);
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
			} else {
				Log.i("memoryadd","fail");
			}
		}
		
	}

}
