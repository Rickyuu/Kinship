package com.speed.kinship.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.speed.kinship.controller.StateHandler;
import com.speed.kinship.controller.impl.StateHandlerImpl;
import com.speed.kinship.model.State;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class StateActivity extends Activity {
	private ListView stateList;
	private ImageButton write;
	
	private SimpleAdapter myAdatpter;
	private ArrayList<HashMap<String, String>>  mylist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.state);
		stateList=(ListView) findViewById(R.id.thingView);
		write=(ImageButton) findViewById(R.id.setting);
		
		getStateAsyncTask myget=new getStateAsyncTask();
		myget.execute();
		
		
		
		write.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(StateActivity.this, StateCreateActivity.class);
				startActivity(intent);
			}
			
		});
		
	}
	private class getStateAsyncTask extends AsyncTask<Void,Void, List<State>>{
		public getStateAsyncTask() { 			
			// TODO Auto-generated constructor stub
		}
		@Override
		protected List<State> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			StateHandler sta=new StateHandlerImpl();
			List<State> l=sta.getFirstNStates("tttty", 5);
			return sta.getFirstNStates("tttty", 5);
		}
		@Override
		protected void onPostExecute(List<State> result) {
			// TODO Auto-generated method stub
			if(result!=null) {
				ArrayList<State> staList=new ArrayList<State>();
				staList.addAll(result);
				mylist = new ArrayList<HashMap<String, String>>();
				for(int i=0;i<staList.size();i++) {
					HashMap<String, String> hm=new HashMap<String,String>();
					hm.put("Content", staList.get(i).getContent());
					hm.put("Time", staList.get(i).getTime().toString());
					mylist.add(hm);
				}
				myAdatpter=new SimpleAdapter(StateActivity.this,mylist,R.layout.itemlayout,
						new String[]{"Content","Time"},
						new int[]{R.id.thingTitle,R.id.thingTime});
				stateList.setAdapter(myAdatpter);
			}		
		}	
	}
}
