package com.speed.kinship.view;

import com.speed.kinship.controller.impl.StateHandlerImpl;
import com.speed.kinship.model.Feedback;
import com.speed.kinship.model.Identity;
import com.speed.kinship.model.State;
import com.speed.kinship.model.User;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FeedbackCreate extends Activity {
	private EditText text;
	private Button post;
	
	private String username;
	private String id;
	private String identity;
	private String stateId;
	
	@Override 
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
        setContentView(R.layout.feedbackcreat);
        
        text = (EditText) findViewById (R.id.newFeedback);
        post = (Button) findViewById (R.id.postFeedback);
        
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        username = b.getString("userName");//待修改，主要获取username
        id = b.getString("id");
        stateId = b.getString("stateId");
        identity = b.getString("identity");
        
        post.setOnClickListener(new OnClickListener(){
        	
			@Override
			public void onClick(View v) {
				String context = new String(text.getText().toString());
				if(context.isEmpty()){
					Toast toast = Toast.makeText(getApplicationContext(), "You cannot post an empty reply.", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				}else{
					FeedbackCreateAsyncTask postState = new FeedbackCreateAsyncTask(username, id, identity, context);
					postState.execute();
					Bundle data = new Bundle();
	        		data.putString("username",username);
	        		data.putString("id", id);
	        		data.putString("identity", identity);
	        		Intent intent = new Intent(FeedbackCreate.this, StateActivity.class);
	        		intent.putExtras(data);
	        		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        		startActivity(intent);
				}
			}
        });
	}
	
	private class FeedbackCreateAsyncTask extends AsyncTask<Void, Void, Feedback> {
		
		private String username;
		private int id;
		private String identity;
		private String context;
		private int stateid;

		public FeedbackCreateAsyncTask(String username, String id, String identity, String context) {
			this.username = username;
			this.id = Integer.parseInt(id);
			this.identity = identity;
			this.context = context;
			this.stateid = Integer.parseInt(stateId);
		}
		
		@Override
		protected Feedback doInBackground(Void... params) {
			User creator = new User();
			creator.setId(id);
			creator.setUserName(username);
			StateHandlerImpl StateHler = new StateHandlerImpl();
			Feedback result = StateHler.addFeedback(stateid, creator, context);
			return result;
		}
		
		@Override
		protected void onPostExecute(Feedback result) {
			if(result == null){
				Toast toast = Toast.makeText(getApplicationContext(), "Post Failed. Please try again.", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			}else{
				Toast toast = Toast.makeText(getApplicationContext(), "Post new reply successfully.", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			}
		}
		
	}
}