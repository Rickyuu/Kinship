package com.speed.kinship.view;

import com.speed.kinship.controller.impl.StateHandlerImpl;
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

public class StateCreateActivity extends Activity {
	private EditText text;
	private Button post;
	private Button picture;
	
	private String username;
	private String id;
	private String identity;
	
	@Override 
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
        setContentView(R.layout.statecreat);
        
        text = (EditText) findViewById (R.id.newText);
        post = (Button) findViewById (R.id.create);
        picture = (Button) findViewById (R.id.buttonAddPic);
        
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        username = b.getString("userName");//待修改，主要获取username
        id = b.getString("id");
        identity = b.getString("identity");
        
        post.setOnClickListener(new OnClickListener(){
        	
			@Override
			public void onClick(View v) {
				String context = new String(text.getText().toString());
				if(context.isEmpty()){
					Toast toast = Toast.makeText(getApplicationContext(), "You cannot post an empty status.", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				}else{
					PostStateAsyncTask postState = new PostStateAsyncTask(username, id, identity, context);
					postState.execute();
					Bundle data = new Bundle();
	        		data.putString("username",username);
	        		data.putString("id", id);
	        		data.putString("identity", identity);
	        		Intent intent = new Intent(StateCreateActivity.this, StateActivity.class);
	        		intent.putExtras(data);
	        		startActivity(intent);
				}
			}
        });
        
        /*
        picture.setOnClickListener(new OnClickListener(){
        	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
        });
        */
	}
	
	 public static interface DataFinishListener { 
	        void dataFinishSuccessfully(); 
	    } 
	
	private class PostStateAsyncTask extends AsyncTask<Void, Void, State> {
		
		private String username;
		private String id;
		private String identity;
		private String context;

		public PostStateAsyncTask(String username, String id, String identity, String context) {
			this.username = username;
			this.id = id;
			this.identity = identity;
			this.context = context;
		}
		
		@Override
		protected State doInBackground(Void... params) {
			User creator = new User();
			creator.setId(Integer.parseInt(id));
			creator.setUserName(username);
			StateHandlerImpl StateHler = new StateHandlerImpl();
			State feedback = StateHler.addState(creator, context, null);
			return feedback;
		}
		
		@Override
		protected void onPostExecute(State feedback) {
			if(feedback == null){
				Toast toast = Toast.makeText(getApplicationContext(), "Post Failed. Please try again.", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			}else{
				Toast toast = Toast.makeText(getApplicationContext(), "Post new state successfully.", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			}
		}
		
	}
}
