package com.speed.kinship.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.speed.kinship.controller.ThingHandler;
import com.speed.kinship.controller.impl.ThingHandlerImpl;
import com.speed.kinship.model.State;
import com.speed.kinship.model.Thing;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
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
import android.widget.Toast;

 
public class ThingActivity extends Activity {
	
	private ListView thingList;
	private Button timeLine;
	private Button state;
	private Button memory;
	private ImageButton writeThing;
	
	private SimpleAdapter myAdapter;
	private ArrayList<HashMap<String, String>>  mylist;
	private ArrayList<Thing> thiList=new ArrayList<Thing>();
	private Date lastdate;
	
	

	
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
		
		getThingAsyncTask getThing=new getThingAsyncTask();
		getThing.execute( );
		
		
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
				intent.putExtra("id", map.get("id"));
				intent.putExtra("userId", map.get("userId"));
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
					getNextNThingAsyncTask nextN=new getNextNThingAsyncTask();
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
		timeLine.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				thiList=new ArrayList<Thing>();
				getThingAsyncTask getthing=new getThingAsyncTask();
				getthing.execute( );
			}
			
		});
		state.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent=ThingActivity.this.getIntent();
				intent.setClass(ThingActivity.this, StateActivity.class);
				startActivity(intent);
			}
			
		});
		memory.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(ThingActivity.this,MemoryActivity.class);
				startActivity(intent);
			}
			
		});
		writeThing.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(ThingActivity.this,ThingCreateActivity.class);
				startActivity(intent);
			}
			
		});
		
	}
	
	private class getNextNThingAsyncTask extends AsyncTask<Void,Void,List<Thing>> {
		

		public getNextNThingAsyncTask() {
			super();
			// TODO Auto-generated constructor stub
		}

		@Override
		protected List<Thing> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			ThingHandler thingHandler=new ThingHandlerImpl();
			return thingHandler.getNextNThings("tttty", ThingActivity.this.lastdate, 5);
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
				hm.put("id",String.valueOf(temp.getId()));
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
	
	private class getThingAsyncTask extends AsyncTask<Void, Void, List<Thing>> {

		@Override
		protected List<Thing> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			ThingHandler thingHandler=new ThingHandlerImpl();
			return thingHandler.getFirstNThings("tttty", 5);
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
				hm.put("id",String.valueOf(temp.getId()));
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

		public getThingAsyncTask() {
			super();
			// TODO Auto-generated constructor stub
		}
		
	}
	
}
