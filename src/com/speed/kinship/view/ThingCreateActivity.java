package com.speed.kinship.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.speed.kinship.controller.ThingHandler;
import com.speed.kinship.controller.impl.ThingHandlerImpl;
import com.speed.kinship.model.Identity;
import com.speed.kinship.model.Thing;
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

public class ThingCreateActivity extends Activity {
	private EditText thingTitle;
	private EditText thingTime;
	private EditText thingContent;
	private Button post;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.thingcreate);
		thingTitle=(EditText) findViewById(R.id.thingEditTitle);
		thingTime=(EditText) findViewById(R.id.thingEditTime);
		thingContent=(EditText) findViewById(R.id.thingEditContent);
		post=(Button) findViewById(R.id.post);
		
		/*暂时不考虑用户信息传递
		 * */
		final User user=new User();
		user.setIdentity(Identity.PARENT);
		user.setUserName("tttty");
		user.setId(5);
		//ThingCreateActivity.this.
		
		post.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String title=thingTitle.getText().toString();
				//String time=thingTime;
				String time=thingTime.getText().toString();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date=null;
				try {
					date = sdf.parse(time);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String content=thingContent.getText().toString();
				addThingAsyncTask addthing=new addThingAsyncTask(user,title,date,content,null);
				addthing.execute( );
				Intent intent=new Intent();
				intent.setClass(ThingCreateActivity.this, ThingActivity.class);
				startActivity(intent);
			}
			
		});
		
	}
	private class addThingAsyncTask extends AsyncTask<Void,Void, Thing> {
		private User user;
		private String title;
		private Date time;
		private String content;
		private byte[] pic;
		

		@SuppressWarnings("unused")
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
			} else {
				Log.i("Post","Fail!");
			}
		}
		
	}
	

}
