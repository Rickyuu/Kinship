package com.speed.kinship;

import com.speed.kinship.controller.UserHandler;
import com.speed.kinship.controller.impl.UserHandlerImpl;
import com.speed.kinship.model.Identity;
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
		View button = findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				LoginAsyncTask loginAsyncTask = new LoginAsyncTask("ricky", "giveme5", Identity.CHILD);
				loginAsyncTask.execute();
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
	
	private class LoginAsyncTask extends AsyncTask<Void, Void, Boolean> {

		private String username;
		private String password;
		private Identity identity;
		
		public LoginAsyncTask(String username, String password, Identity identity) {
			this.username = username;
			this.password = password;
			this.identity = identity;
		}
		
		@Override
		protected Boolean doInBackground(Void... params) {
			UserHandler userHandler = new UserHandlerImpl();
			return userHandler.register(username, password, identity);
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			if(result) {
				Log.i("register ", "succeed!");
			} else {
				Log.i("register ", "fail!");
			}
		}
		
	}
	
}
