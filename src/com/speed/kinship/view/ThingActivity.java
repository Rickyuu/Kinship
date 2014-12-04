package com.speed.kinship.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.speed.kinship.controller.ThingHandler;
import com.speed.kinship.controller.impl.ThingHandlerImpl;
import com.speed.kinship.model.Thing;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

 
public class ThingActivity extends Activity {
	
	private ListView thingList;
	private Button timeLine;
	private Button state;
	private Button memory;
	private ImageButton writeThing;
	private ImageButton setting;
	
	private SimpleAdapter myAdapter;
	private ArrayList<HashMap<String, String>>  mylist;
	private ArrayList<Thing> thiList=new ArrayList<Thing>();
	private Date lastdate;
	private int id;
	private String identity;
	private String userName;
	
	MyBroadcast broadcastReceiver=null;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.thing);
		
		thingList=(ListView) findViewById(R.id.thingList);
		timeLine=(Button) findViewById(R.id.timeLine);
		state=(Button) findViewById(R.id.state);
		memory=(Button) findViewById(R.id.memory);
		writeThing=(ImageButton) findViewById(R.id.writeThing);
		setting=(ImageButton) findViewById(R.id.setting);
		//user information
		Intent intent=getIntent();
		id=Integer.parseInt(intent.getStringExtra("id"));
		identity=intent.getStringExtra("identity");
		userName=intent.getStringExtra("userName");
		
		
		
		getThingAsyncTask getThing=new getThingAsyncTask(ThingActivity.this.id,ThingActivity.this.identity,ThingActivity.this.userName);
		getThing.execute( );
		
		
		setting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(ThingActivity.this, settingActivity.class);
				
				intent.putExtra("id", String.valueOf(ThingActivity.this.id));
				intent.putExtra("identity", ThingActivity.this.identity);
				intent.putExtra("userName",ThingActivity.this.userName);
				
				startActivity(intent);
			}
			
		});
		
		
		
		thingList.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				HashMap<String,String> map=(HashMap<String, String>) parent.getItemAtPosition(position);
				Intent intent=new Intent();
				intent.putExtra("title", map.get("title"));
				intent.putExtra("time", map.get("time"));
				intent.putExtra("content", map.get("content"));
				intent.putExtra("thingId", map.get("thingId"));
				intent.putExtra("userId", map.get("userId"));
				intent.putExtra("position", String.valueOf(position));
				
				intent.putExtra("id", String.valueOf(ThingActivity.this.id));
				intent.putExtra("identity", ThingActivity.this.identity);
				intent.putExtra("userName",ThingActivity.this.userName);
				
				//Toast.makeText(getApplicationContext(), map.toString(), Toast.LENGTH_SHORT).show();
				intent.setClass(ThingActivity.this, ThingContentActivity.class);
				startActivity(intent);
			}
			
			
		});
		thingList.setOnScrollListener(new OnScrollListener() {
			private boolean isBottom=false;

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				if(isBottom&& scrollState==OnScrollListener.SCROLL_STATE_IDLE) {
					int pos=thingList.getLastVisiblePosition();
					getNextNThingAsyncTask nextN=new getNextNThingAsyncTask(ThingActivity.this.id,ThingActivity.this.identity,ThingActivity.this.userName);
					nextN.execute();
					
					isBottom=false;
				}
				
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				
				if(firstVisibleItem+visibleItemCount==totalItemCount) {
					isBottom=true;
				} else {
					isBottom=false;
				}
				
			}
			
		});
		//refresh timeline
		timeLine.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				thiList=new ArrayList<Thing>();
				getThingAsyncTask getthing=new getThingAsyncTask(ThingActivity.this.id,ThingActivity.this.identity,ThingActivity.this.userName);
				getthing.execute( );
			}
			
		});
		//jump to state
		state.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent=ThingActivity.this.getIntent();
				intent.setClass(ThingActivity.this, StateActivity.class);
				intent.putExtra("id", String.valueOf(id));
				intent.putExtra("identity", identity);
				intent.putExtra("userName",userName);
				startActivity(intent);
			}
			
		});
		//jump to memory
		memory.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(ThingActivity.this,MemoryActivity.class);
				intent.putExtra("id", String.valueOf(id));
				intent.putExtra("identity", identity);
				intent.putExtra("userName",userName);
				startActivity(intent);
			}
			
		});
		writeThing.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(ThingActivity.this,ThingCreateActivity.class);
				intent.putExtra("id", String.valueOf(id));
				intent.putExtra("identity", identity);
				intent.putExtra("userName",userName);
				startActivity(intent);
			}
			
		});
		
	}
	private class MyBroadcast extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			int delete=Integer.parseInt(intent.getStringExtra("position"));
			mylist.remove(delete);
			myAdapter.notifyDataSetChanged();
		}
		
	}
	
	
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
	}
	

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		broadcastReceiver=new MyBroadcast();
		IntentFilter filter = new IntentFilter("DELETE_POSITION");
		registerReceiver(broadcastReceiver, filter);
		
	}
	
	

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	
	



	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver( broadcastReceiver);
	}
	
	@SuppressLint("SimpleDateFormat") private class getNextNThingAsyncTask extends AsyncTask<Void,Void,List<Thing>> {
		private int id;
		@SuppressWarnings("unused")
		private String identity;
		private String userName;
		

		public getNextNThingAsyncTask(int id, String identity, String userName) {
			this.id = id;
			this.identity = identity;
			this.userName = userName;
		}

		@SuppressWarnings("unused")
		public getNextNThingAsyncTask() {
			super();
			// TODO Auto-generated constructor stub
		}

		@Override
		protected List<Thing> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			ThingHandler thingHandler=new ThingHandlerImpl();
			return thingHandler.getNextNThings(userName, ThingActivity.this.lastdate, id);
		}

		@Override
		protected void onPostExecute(List<Thing> result) {
			// TODO Auto-generated method stub
			ArrayList<Thing> thiList=new ArrayList<Thing>();
			thiList.addAll(result);
			//mylist = new ArrayList<HashMap<String, String>>();
			for(int i=0;i<thiList.size();i++) {
				Thing temp=thiList.get(i);
				HashMap<String, String> hm=new HashMap<String,String>();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String str=sdf.format(temp.getTime());
				hm.put("time", str);
				hm.put("title", temp.getTitle());
				hm.put("content",temp.getContent());
				hm.put("thingId",String.valueOf(temp.getId()));
				hm.put("userId", String.valueOf(temp.getCreator().getId()));
				mylist.add(hm);
				if(i==thiList.size()-1) {
					ThingActivity.this.lastdate=temp.getTime();
				}
			}
			myAdapter.notifyDataSetChanged();
//			myAdapter=new SimpleAdapter(ThingActivity.this,mylist,R.layout.timelineitem,
//					new String[]{"time","title"},
//					new int[]{R.id.timeLineTime,R.id.timeLineTitle});
//			thingList.setAdapter(myAdapter);
		}
		
		
	}
	
	@SuppressLint("SimpleDateFormat") 
	private class getThingAsyncTask extends AsyncTask<Void, Void, List<Thing>> {
		private int id;
		@SuppressWarnings("unused")
		private String identity;
		private String userName;
		
		public getThingAsyncTask(int id, String identity, String userName) {
			this.id = id;
			this.identity = identity;
			this.userName = userName;
		}

		@Override
		protected List<Thing> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			ThingHandler thingHandler=new ThingHandlerImpl();
			return thingHandler.getFirstNThings(userName, id);
		}

		@Override
		protected void onPostExecute(List<Thing> result) {
			
			// TODO Auto-generated method stub
			//ArrayList<Thing> thiList=new ArrayList<Thing>();
			thiList.addAll(result);
			mylist = new ArrayList<HashMap<String, String>>();
			for(int i=0;i<thiList.size();i++) {
				Thing temp=thiList.get(i);
				HashMap<String, String> hm=new HashMap<String,String>();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String str=sdf.format(temp.getTime());
				hm.put("time", str);
				hm.put("title", temp.getTitle());
				hm.put("content",temp.getContent());
				hm.put("thingId",String.valueOf(temp.getId()));
				hm.put("userId", String.valueOf(temp.getCreator().getId()));
				mylist.add(hm);
				if(i==thiList.size()-1) {
					ThingActivity.this.lastdate=temp.getTime();
				}
			}
			myAdapter=new SimpleAdapter(ThingActivity.this,mylist,R.layout.timelineitem,
					new String[]{"time","title"},
					new int[]{R.id.timeLineTime,R.id.timeLineTitle});
			thingList.setAdapter(myAdapter);
		}
		
	}
	
}
