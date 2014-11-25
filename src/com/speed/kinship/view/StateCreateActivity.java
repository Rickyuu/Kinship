package com.speed.kinship.view;

import com.speed.kinship.controller.StateHandler;
import com.speed.kinship.controller.impl.StateHandlerImpl;
import com.speed.kinship.model.State;
import com.speed.kinship.model.User;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class StateCreateActivity extends Activity {
	private EditText create;
	private Button post;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.statecreat);
		create=(EditText) findViewById(R.id.thingEditTime);
		post=(Button) findViewById(R.id.timeLine);
		post.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
			}
			
		});
	}
	private class stateCreateAsyncTask extends AsyncTask<Void,Void, Boolean> {
		
		private User user;
		

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			String str=create.getText().toString();
			StateHandler stahl=new StateHandlerImpl();
			stahl.addState(user, str, null);
			return null;
		}


		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
		
		
	}

}
