package com.speed.kinship.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.speed.kinship.controller.ThingHandler;
import com.speed.kinship.controller.impl.ThingHandlerImpl;
import com.speed.kinship.model.Identity;
import com.speed.kinship.model.Thing;
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

@SuppressLint("SimpleDateFormat") 
public class ThingCreateActivity extends Activity {
	private EditText thingTitle;
	private EditText thingContent;
	private Button post;
	private DatePicker chooseDate;
	private int id;
	private String identity;
	private String userName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.thingcreate);
		thingTitle=(EditText) findViewById(R.id.thingEditTitle);
		thingContent=(EditText) findViewById(R.id.thingEditContent);
		post=(Button) findViewById(R.id.post);
		chooseDate=(DatePicker) findViewById(R.id.chooseDate);
		
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
		
		
		
		post.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String title=thingTitle.getText().toString();
				String dateChosen=String.valueOf(chooseDate.getYear())+"-"+String.valueOf(chooseDate.getMonth())+"-"+String.valueOf(chooseDate.getDayOfMonth());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date=null;
				try {
					date = sdf.parse(dateChosen);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String content=thingContent.getText().toString();
				addThingAsyncTask addthing=new addThingAsyncTask(user,title,date,content,null);
				addthing.execute( );
				
			}
			
		});
		
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
	}
	

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	


	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}




	private class addThingAsyncTask extends AsyncTask<Void,Void, Thing> {
		private User user;
		private String title;
		private Date time;
		private String content;
		private byte[] pic;
		

		public addThingAsyncTask(User user, String title, Date time,
				String content, byte[] pic) {
			super();
			this.user = user;
			this.title = title;
			this.time = time;
			this.content = content;
			this.pic = pic;
		}

		@Override
		protected Thing doInBackground(Void... params) {
			// TODO Auto-generated method stub
			ThingHandler thingHandler=new ThingHandlerImpl();
			return thingHandler.addThing(user, title, time, content, pic);
		}

		@Override
		protected void onPostExecute(Thing result) {
			// TODO Auto-generated method stub
			if(result!=null) {
				Log.i("Post", "Success!");
				Intent intent=new Intent();
				intent.setClass(ThingCreateActivity.this, ThingActivity.class);
				intent.putExtra("id", String.valueOf(id));
				intent.putExtra("identity", identity);
				intent.putExtra("userName",userName);
				startActivity(intent);
				
			} else {
				Log.i("Post","Fail!");
			}
		}
		
	}
	

}
