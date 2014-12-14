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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.speed.kinship.controller.UserHandler;
import com.speed.kinship.controller.impl.UserHandlerImpl;
import com.speed.kinship.model.Identity;
import com.speed.kinship.model.User;

public class RegisterActivity extends Activity {
	private EditText newAccount;
	private EditText newPassword;
	private Button register;

	private RadioGroup check;
	private RadioButton parent;
	private RadioButton child;
	
	private int identity=1;
	private int id=11111;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		newAccount=(EditText) findViewById(R.id.thingEditTime);
		newPassword=(EditText) findViewById(R.id.thingEditTitle);
		register=(Button) findViewById(R.id.timeLine);
		parent=(RadioButton) findViewById(R.id.registerparent);
		check=(RadioGroup) findViewById(R.id.registerIdentity);
		child=(RadioButton) findViewById(R.id.registerchild);
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
				LoginAsyncTask loginAsyncTask=new LoginAsyncTask(account,password,identity);
				loginAsyncTask.execute( );
				
			}
			
		});
		check.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if(checkedId==parent.getId()) {
					RegisterActivity.this.identity=1;
				}
				if(checkedId==child.getId()) {
					RegisterActivity.this.identity=0;
				}
				
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
				Toast.makeText(RegisterActivity.this, "The username has already exist!", Toast.LENGTH_SHORT).show();
				Log.i("Register", "Fail!");
			}
		}
		
	}
	
	private class LoginAsyncTask extends AsyncTask<Void, Void, User> {
		private String username;
		private String password;
		private int identity;
		
		
		
		public LoginAsyncTask(String username,String password, int identity) {
			this.username=username;
			this.password=password;
			this.identity=identity;
		}

		@Override
		protected User doInBackground(Void... params) {
			// TODO Auto-generated method stub
			UserHandler userHandler = new UserHandlerImpl();
			if(identity==1) {
				User user=userHandler.login(username, password, Identity.PARENT);
				id=user.getId();
				return user;
			}
			else if(identity==0) {
				User user=userHandler.login(username, password, Identity.CHILD);
				id=user.getId();
				return user;
			} else {
				return null;
			}
			
		}

		@Override
		protected void onPostExecute(User result) {
			// TODO Auto-generated method stub
			if(result!=null) {
				Intent intent=new Intent();
				intent.putExtra("userName", result.getUserName());
				intent.putExtra("identity", result.getIdentity().toString());
				intent.putExtra("id", String.valueOf(result.getId()));
				intent.setClass(RegisterActivity.this, LoginingActivity.class);
				startActivity(intent);
				RegisterActivity.this.finish();
				Log.i("Login", "Succeed!"+result.getId());
				
			} else {
				Toast.makeText(RegisterActivity.this, "ERROR", Toast.LENGTH_LONG).show();
				Log.i("Login", "Fail!");
			}
		}
	}
	
}
