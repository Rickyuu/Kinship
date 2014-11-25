package com.speed.kinship.view;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.speed.kinship.controller.UserHandler;
import com.speed.kinship.controller.impl.UserHandlerImpl;
import com.speed.kinship.model.Identity;
import com.speed.kinship.model.User;

public class RegisterActivity extends Activity {
	private EditText newAccount;
	private EditText newPassword;
	private Button register;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		newAccount=(EditText) findViewById(R.id.thingEditTime);
		newPassword=(EditText) findViewById(R.id.thingEditTitle);
		register=(Button) findViewById(R.id.timeLine);
		register.setOnClickListener(new OnClickListener() {
			String account=null;
			String password=null;
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				account=newAccount.getText().toString();
				password=newPassword.getText().toString();
				RegisterAsyncTask asynctask=new RegisterAsyncTask(account,password,Identity.PARENT);
				asynctask.execute( );		
				Intent intent=new Intent();
				intent.setClass(RegisterActivity.this, LoginActivity.class);
				startActivity(intent);
			}
			
		});
	}
	private class RegisterAsyncTask extends AsyncTask<Void, Void, User> {
		private String username;
		private String password;
		private Identity identity;

		public RegisterAsyncTask(String username, String password,Identity identity) {
			this.username = username;
			this.password = password;
			this.identity = identity;
		}


		@Override
		protected User doInBackground(Void... params) {
			// TODO Auto-generated method stub
			UserHandler userhandler=new UserHandlerImpl();
			return userhandler.register(username, password, identity);
		}


		@Override
		protected void onPostExecute(User result) {
			// TODO Auto-generated method stub
			if(result!=null) {
				Log.i("Register", "Success!");
			} else {
				Log.i("Register", "Fail!");
			}
		}
		
	}
	

}
