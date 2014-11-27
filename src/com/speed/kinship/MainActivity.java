package com.speed.kinship;

import java.util.Iterator;
import java.util.List;

import com.speed.kinship.controller.StateHandler;
import com.speed.kinship.controller.UserHandler;
import com.speed.kinship.controller.impl.StateHandlerImpl;
import com.speed.kinship.controller.impl.UserHandlerImpl;
import com.speed.kinship.model.Identity;
import com.speed.kinship.model.State;
import com.speed.kinship.model.User;
import com.speed.kinship.view.R;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		View test_register_button = findViewById(R.id.test_register_button);
		test_register_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				RegisterAsyncTask registerAsyncTask = new RegisterAsyncTask("rickytest", "17599", Identity.PARENT);
				registerAsyncTask.execute();
			}
			
		});
		View test_state_button = findViewById(R.id.test_state_button);
		test_state_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				StateAsyncTask stateAsyncTask = new StateAsyncTask("rickyuu", 10);
				stateAsyncTask.execute();
			}
			
		});
		View test_delete_state_button = findViewById(R.id.test_delete_state_button);
		test_delete_state_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DeleteStateAsyncTask deleteStateAsyncTask = new DeleteStateAsyncTask(13);
				deleteStateAsyncTask.execute();
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private class RegisterAsyncTask extends AsyncTask<Void, Void, User> {

		private String username;
		private String password;
		private Identity identity;
		
		public RegisterAsyncTask(String username, String password, Identity identity) {
			this.username = username;
			this.password = password;
			this.identity = identity;
		}
		
		@Override
		protected User doInBackground(Void... params) {
			UserHandler userHandler = new UserHandlerImpl();
			return userHandler.register(username, password, identity);
		}
		
		@Override
		protected void onPostExecute(User result) {
			if(result != null) {
				Log.i("register", "succeed!" + " " + result.getId() + " " + result.getUserName() 
						+ " " + result.getPassword() + " " + result.getIdentity());
			} else {
				Log.i("register", "fail!");
			}
		}
		
	}
	
	private class StateAsyncTask extends AsyncTask<Void, Void, List<State>> {

		private String username;
		private int n;
		
		public StateAsyncTask(String username, int n) {
			this.username = username;
			this.n = n;
		}
		
		@Override
		protected List<State> doInBackground(Void... params) {
			StateHandler stateHandler = new StateHandlerImpl();
			return stateHandler.getFirstNStates(username, n);
		}
		
		@Override
		protected void onPostExecute(List<State> result) {
			if(result != null) {
				Iterator<State> iterator = result.iterator();
				while(iterator.hasNext()) {
					State state = iterator.next();
					Log.i("stateGet", "succeed!" + " " + state.getId() + " " + state.getCreator().getUserName() 
							+ " " + state.getContent() + " " + state.getFeedbacks().length);
				}
			} else {
				Log.i("stateGet", "fail!");
			}
		}
		
	}
	
	private class DeleteStateAsyncTask extends AsyncTask<Void, Void, Boolean> {

		private int stateId;
		
		public DeleteStateAsyncTask(int stateId) {
			this.stateId = stateId;
		}
		
		@Override
		protected Boolean doInBackground(Void... params) {
			StateHandler stateHandler = new StateHandlerImpl();
			return stateHandler.deleteState(stateId);
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			if(result == true) {
				Log.i("stateDelete", "succeed!");
			} else {
				Log.i("stateDelete", "fail!");
			}
		}
		
	}
	
}
