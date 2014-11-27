package com.speed.kinship.view;

import com.speed.kinship.controller.ThingHandler;
import com.speed.kinship.controller.impl.ThingHandlerImpl;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ThingContentActivity extends Activity {
	private TextView title;
	private TextView time;
	private TextView content;
	private Button delete;
	private int id;
	private int userId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.thingcontent);
		title=(TextView) findViewById(R.id.ContentTitle);
		time=(TextView) findViewById(R.id.ContentTime);
		content=(TextView) findViewById(R.id.thingContent);
		delete=(Button) findViewById(R.id.delete);
		title.setText(getIntent().getStringExtra("title"));
		time.setText(getIntent().getStringExtra("time"));
		content.setText(getIntent().getStringExtra("content"));
		id=Integer.parseInt(getIntent().getStringExtra("id"));
		delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				deleteThingAsyncTask deleteThing=new deleteThingAsyncTask();
				deleteThing.execute( );
				Intent intent=new Intent();
				intent.setClass(ThingContentActivity.this, ThingActivity.class);
				startActivity(intent);
			}
			
		});
	}
	private class deleteThingAsyncTask extends AsyncTask<Void,Void,Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			ThingHandler thingHandler=new ThingHandlerImpl();
			return thingHandler.deleteThing(ThingContentActivity.this.id);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			if(result==true) {
				Log.i("delete", "success!");
			} else {
				Log.i("delete","fail!");
			}
		}
		
	}

}
