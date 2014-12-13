package com.speed.kinship.view;

import com.speed.kinship.controller.UserHandler;
import com.speed.kinship.controller.impl.UserHandlerImpl;
import com.speed.kinship.model.Identity;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class LoginActivity extends Activity {
	private EditText account;
	private EditText pwd;
	private Button login;
	private Button register;
	private RadioGroup check;
	private RadioButton parent;
	private RadioButton child;
	private int identity=1;
	
	private String userName=null;
	private String password=null;
	private int id=11111;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		account=(EditText) findViewById(R.id.account);
		pwd=(EditText) findViewById(R.id.password);
		login=(Button) findViewById(R.id.login);
		register=(Button) findViewById(R.id.register);
		check=(RadioGroup) findViewById(R.id.Identity);
		parent=(RadioButton) findViewById(R.id.parent);
		child=(RadioButton) findViewById(R.id.child);
		check.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if(checkedId==parent.getId()) {
					LoginActivity.this.identity=1;
				}
				if(checkedId==child.getId()) {
					LoginActivity.this.identity=0;
				}
				
			}
		});
		
		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				userName=account.getText().toString();
				password=pwd.getText().toString();
				LoginAsyncTask loginAsyncTask=new LoginAsyncTask(userName,password,identity);
				loginAsyncTask.execute( );
				
			}
			
		});
		
		register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(LoginActivity.this, RegisterActivity.class);
				startActivity(intent);
			}
			
		});
		
	}
	public void setId(int id) {
		this.id=id;
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
				intent.setClass(LoginActivity.this, LoginingActivity.class);
				startActivity(intent);
				Log.i("Login", "Succeed!"+result.getId());
			} else {
				Toast.makeText(LoginActivity.this, "ERROR", Toast.LENGTH_LONG).show();
				Log.i("Login", "Fail!");
			}
		}

	}
	

}
