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
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
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
	private View listFooter;
	
	private SimpleAdapter myAdapter;
	private ArrayList<HashMap<String, Object>>  mylist;
	private ArrayList<Thing> thiList=new ArrayList<Thing>();
	private Date lastdate;
	private int id;
	private String identity;
	private String userName;
	
	//for gestureDetector
	private boolean refreshable;
	private GestureDetector myGesture;
	
	public static final String ACTION_INTENT = "DELETE_POSITION";
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.thing);
		
		thingList=(ListView) findViewById(R.id.thingList);
		timeLine=(Button) findViewById(R.id.timeLine);
		timeLine.setTextColor(0xff0099ff);
		state=(Button) findViewById(R.id.state);
		memory=(Button) findViewById(R.id.memory);
		writeThing=(ImageButton) findViewById(R.id.writeThing);
		setting=(ImageButton) findViewById(R.id.setting);
		
		//user information
		Intent intent=getIntent();
		id=Integer.parseInt(intent.getStringExtra("id"));
		identity=intent.getStringExtra("identity");
		userName=intent.getStringExtra("userName");
		LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		listFooter = layoutInflater.inflate(R.layout.loadingfooter, null);
		mylist = new ArrayList<HashMap<String, Object>>();
		myAdapter=new SimpleAdapter(ThingActivity.this,mylist,R.layout.timelineitem,
				new String[]{"time","title"},
				new int[]{R.id.timeLineTime,R.id.timeLineTitle});
		thingList.addHeaderView(listFooter);
		thingList.setAdapter(myAdapter);
		
		
		
		getThingAsyncTask getThing=new getThingAsyncTask(10,ThingActivity.this.identity,ThingActivity.this.userName);
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
				HashMap<String,Object> map=(HashMap<String,Object>) parent.getItemAtPosition(position);
				Intent intent=new Intent();
				intent.putExtra("title", map.get("title").toString());
				intent.putExtra("time", map.get("time").toString());
				intent.putExtra("content", map.get("content").toString());
				if(map.get("pic")!=null) {
					intent.putExtra("pic", (byte[])map.get("pic"));
				} else {
					intent.putExtra("pic", "");
				}
				
				
				intent.putExtra("thingId", map.get("thingId").toString());
				intent.putExtra("userId", map.get("userId").toString());
				intent.putExtra("position", String.valueOf(position));
				
				intent.putExtra("id", String.valueOf(ThingActivity.this.id));
				intent.putExtra("identity", ThingActivity.this.identity);
				intent.putExtra("userName",ThingActivity.this.userName);
				
				//Toast.makeText(getApplicationContext(), map.toString(), Toast.LENGTH_SHORT).show();
				intent.setClass(ThingActivity.this, ThingContentActivity.class);
				
				startActivityForResult(intent, 1);
			}
			
			
		});
		thingList.setOnScrollListener(new OnScrollListener() {
			private boolean isBottom=false;

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				if(isBottom&& scrollState==OnScrollListener.SCROLL_STATE_IDLE) {
					int pos=thingList.getLastVisiblePosition();
					getNextNThingAsyncTask nextN=new getNextNThingAsyncTask(5,ThingActivity.this.identity,ThingActivity.this.userName);
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
				getThingAsyncTask getthing=new getThingAsyncTask(10,ThingActivity.this.identity,ThingActivity.this.userName);
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
		
		//for GestureDetector
		refreshable = true;
		myGesture = new GestureDetector(this, new OnGestureListener(){

			@Override
			public boolean onDown(MotionEvent e) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void onShowPress(MotionEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2,
					float distanceX, float distanceY) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void onLongPress(MotionEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) {
					Log.i("Fling", "baseactivity");    
		          //左滑动  
		        	if (e1.getX() - e2.getX() > 300) {    
		            
		        		Intent intent=new Intent();
						intent=ThingActivity.this.getIntent();
						intent.setClass(ThingActivity.this, StateActivity.class);
						intent.putExtra("id", String.valueOf(id));
						intent.putExtra("identity", identity);
						intent.putExtra("userName",userName);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
//						ThingActivity.this.finish();
		                
		                return true;    
		            }   
		            //右滑动  
		            else if (e1.getX() - e2.getX() <-300) {    
		              
		            	Intent intent=new Intent();
						intent.setClass(ThingActivity.this,MemoryActivity.class);
						intent.putExtra("id", String.valueOf(id));
						intent.putExtra("identity", identity);
						intent.putExtra("userName",userName);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
						//ThingActivity.this.finish();
		                
		                return true;    
		            } 
		            else if ((e1.getY() - e2.getY() <-400) && getRefreshable()){    
			              
		            	thiList=new ArrayList<Thing>();
						getThingAsyncTask getthing=new getThingAsyncTask(10, ThingActivity.this.identity,ThingActivity.this.userName);
						getthing.execute( );
		                
		                return true;    
		            } 
				return false;
			}
			
		});//可能需要重写gestureDetector
	}
	
	//for Gesture Detector
	@Override
	public boolean dispatchTouchEvent(MotionEvent event){
		View firstChild = thingList.getChildAt(0);  
        if (firstChild != null) {  
            int firstVisiblePos = thingList.getFirstVisiblePosition();  
            if (firstVisiblePos == 0 && firstChild.getTop() == 0) { 
                // 如果首个元素的上边缘，距离父布局值为0，就说明ListView滚动到了最顶部，此时应该允许下拉刷新  
                setRefreshable(true);  
            } else {
            	setRefreshable(false); 
            }  
        } else {  
            // 如果ListView中没有元素，也应该允许下拉刷新  
        	setRefreshable(true); 
        }  
		myGesture.onTouchEvent(event);
		super.dispatchTouchEvent(event);
		return true;
	}
	
	public boolean setRefreshable(boolean status){
		refreshable = status;
		return true;
	}
	
	public boolean getRefreshable(){
		return refreshable;
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
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(resultCode==Activity.RESULT_OK) {
			int delete=Integer.parseInt(data.getStringExtra("position"));
			Log.i("onActivityResult","delete"+delete);
			mylist.remove(delete);
			myAdapter.notifyDataSetChanged();
		}
		
	}

	@SuppressLint("SimpleDateFormat") 
	private class getNextNThingAsyncTask extends AsyncTask<Void,Void,List<Thing>> {
		private int num;
		@SuppressWarnings("unused")
		private String identity;
		private String userName;
		

		public getNextNThingAsyncTask(int num, String identity, String userName) {
			this.num = num;
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
				HashMap<String, Object> hm=new HashMap<String,Object>();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String str=sdf.format(temp.getTime());
				hm.put("time", str);
				hm.put("title", temp.getTitle());
				hm.put("content",temp.getContent());
				if(temp.getPic()!=null) {
					hm.put("pic", temp.getPic());
				} else {
					hm.put("pic", null);
				}
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
		private int num;
		@SuppressWarnings("unused")
		private String identity;
		private String userName;
		
		public getThingAsyncTask(int num, String identity, String userName) {
			this.num = num;
			this.identity = identity;
			this.userName = userName;
		}
		
		protected void onPreExecute(){
			thingList.addHeaderView(listFooter);//添加footer，header
			myAdapter.notifyDataSetChanged();
		}

		@Override
		protected List<Thing> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			ThingHandler thingHandler=new ThingHandlerImpl();
			return thingHandler.getFirstNThings(userName, num);
		}

		@Override
		protected void onPostExecute(List<Thing> result) {
			
			// TODO Auto-generated method stub
			//ArrayList<Thing> thiList=new ArrayList<Thing>();
			thiList.addAll(result);
			//mylist = new ArrayList<HashMap<String, Object>>();
			for(int i=0;i<thiList.size();i++) {
				Thing temp=thiList.get(i);
				HashMap<String, Object> hm=new HashMap<String,Object>();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String str=sdf.format(temp.getTime());
				hm.put("time", str);
				hm.put("title", temp.getTitle());
				hm.put("content",temp.getContent());
				if(temp.getPic()!=null) {
					hm.put("pic", temp.getPic());
				} else {
					hm.put("pic", null);
				}
				hm.put("thingId",String.valueOf(temp.getId()));
				hm.put("userId", String.valueOf(temp.getCreator().getId()));
				mylist.add(hm);
				if(i==thiList.size()-1) {
					ThingActivity.this.lastdate=temp.getTime();
				}
				
			}
			myAdapter.notifyDataSetChanged();
			thingList.removeHeaderView(listFooter);//remove header
		}
		
	}
	
}
