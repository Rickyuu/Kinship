package com.speed.kinship.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.speed.kinship.controller.MemoryHandler;
import com.speed.kinship.controller.impl.MemoryHandlerImpl;
import com.speed.kinship.model.Memory;
import com.speed.kinship.model.Thing;

import android.app.Activity;
import android.content.Context;
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
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MemoryActivity extends Activity {
	private ImageButton setting;
	private ImageButton add;
	private ListView memoryList;
	private Button timeLine;
	private Button state;
	private Button memory;
	private myAdapter mAdapter;
	private ArrayList<HashMap<String, String>>  myList=new ArrayList<HashMap<String, String>>();
	
	private int id;
	private String identity;
	private String userName;
	
	private View listFooter;
	
	//for gestureDetector
	private boolean refreshable;
	private GestureDetector myGesture;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.memory);
		setting=(ImageButton) findViewById(R.id.settingInMemory);
		add=(ImageButton) findViewById(R.id.addMemory);
		memoryList=(ListView) findViewById(R.id.memoryList);
		timeLine=(Button) findViewById(R.id.timeLine3);
		state=(Button) findViewById(R.id.state3);
		memory=(Button) findViewById(R.id.memory3);
		memory.setTextColor(0xff0099ff);
		
		Intent intent=getIntent();
		id=Integer.parseInt(intent.getStringExtra("id"));
		identity=intent.getStringExtra("identity");
		userName=intent.getStringExtra("userName");
		
		myList=new ArrayList<HashMap<String,String>>();
		mAdapter = new myAdapter(MemoryActivity.this);
		LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		listFooter = layoutInflater.inflate(R.layout.loadingfooter, null);
		memoryList.addHeaderView(listFooter);
		memoryList.setAdapter(mAdapter);
		
		//getMemoryAsyncTask getMemory=new getMemoryAsyncTask();
		//getMemory.execute( );
		
		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(MemoryActivity.this, MemoryCreateActivity.class);
				intent.putExtra("id", String.valueOf(id));
				intent.putExtra("identity", identity);
				intent.putExtra("userName",userName);
				startActivity(intent);
			}
			
		});
		//jump to timeline
		timeLine.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(MemoryActivity.this, ThingActivity.class);
				intent.putExtra("id", String.valueOf(id));
				intent.putExtra("identity", identity);
				intent.putExtra("userName",userName);
				startActivity(intent);
				MemoryActivity.this.finish();
			}
			
			
		});
		//jump to state
		state.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(MemoryActivity.this, StateActivity.class);
				intent.putExtra("id", String.valueOf(id));
				intent.putExtra("identity", identity);
				intent.putExtra("userName",userName);
				startActivity(intent);
				MemoryActivity.this.finish();
			}
		});	
		memory.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				refreshMemoryAsyncTask refreshMemory=new refreshMemoryAsyncTask();
        		refreshMemory.execute( );
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
								intent.setClass(MemoryActivity.this, ThingActivity.class);
								intent.putExtra("id", String.valueOf(id));
								intent.putExtra("identity", identity);
								intent.putExtra("userName",userName);
								startActivity(intent);
								MemoryActivity.this.finish();
				                
				                return true;    
				            }   
				            //右滑动  
				            else if (e1.getX() - e2.getX() <-300) {    
				              
				            	Intent intent=new Intent();
								intent.setClass(MemoryActivity.this, StateActivity.class);
								intent.putExtra("id", String.valueOf(id));
								intent.putExtra("identity", identity);
								intent.putExtra("userName",userName);
								startActivity(intent);
								MemoryActivity.this.finish();
				                
				                return true;    
				            } 
				            else if ((e1.getY() - e2.getY() <-400) && getRefreshable()){    
					              
				            	
				            	refreshMemoryAsyncTask refreshMemory=new refreshMemoryAsyncTask();
				        		refreshMemory.execute( );
				                
				                return true;    
				            } 
						return false;
					}
					
				});//可能需要重写gestureDetector
		
	}
	
	//for Gesture Detector
		@Override
		public boolean dispatchTouchEvent(MotionEvent event){
			View firstChild = memoryList.getChildAt(0);  
	        if (firstChild != null) {  
	            int firstVisiblePos = memoryList.getFirstVisiblePosition();  
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
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		refreshMemoryAsyncTask refresh=new refreshMemoryAsyncTask();
		refresh.execute( );
		
	}



	private class getMemoryAsyncTask extends AsyncTask<Void,Void,List<Memory>> {
		

		public getMemoryAsyncTask() {
			
			// TODO Auto-generated constructor stub
		}
		
		@Override
		protected void onPreExecute(){
			memoryList.addHeaderView(listFooter);//添加footer，header
			mAdapter.notifyDataSetChanged();
		}

		@Override
		protected List<Memory> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			MemoryHandler memoryHandler=new MemoryHandlerImpl();
			return memoryHandler.getAllMemories(MemoryActivity.this.userName);
		}

		@Override
		protected void onPostExecute(List<Memory> result) {
			// TODO Auto-generated method stub
			
			for(Memory temp:result) {
				HashMap<String, String> hm=new HashMap<String,String>();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String str=sdf.format(temp.getTime());
				hm.put("content", temp.getContent());
				hm.put("id", String.valueOf(temp.getId()));
				hm.put("username", temp.getCreator().getUserName());
				hm.put("creatorId", String.valueOf(temp.getCreator().getId()));
				hm.put("time", str);
				myList.add(hm);
			}
			mAdapter.notifyDataSetChanged();
			memoryList.removeHeaderView(listFooter);//remove header
			System.out.println("data!!!");
		}
		
	}
	
private class refreshMemoryAsyncTask extends AsyncTask<Void,Void,List<Memory>> {
		

		public refreshMemoryAsyncTask() {
			
			// TODO Auto-generated constructor stub
		}
		
		@Override
		protected void onPreExecute(){
			memoryList.addHeaderView(listFooter);//添加footer，header
			mAdapter.notifyDataSetChanged();
		}

		@Override
		protected List<Memory> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			MemoryHandler memoryHandler=new MemoryHandlerImpl();
			return memoryHandler.getAllMemories(MemoryActivity.this.userName);
		}

		@Override
		protected void onPostExecute(List<Memory> result) {
			// TODO Auto-generated method stub
			myList=new ArrayList<HashMap<String,String>>();
			for(Memory temp:result) {
				HashMap<String, String> hm=new HashMap<String,String>();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String str=sdf.format(temp.getTime());
				hm.put("content", temp.getContent());
				hm.put("id", String.valueOf(temp.getId()));
				hm.put("username", temp.getCreator().getUserName());
				hm.put("creatorId", String.valueOf(temp.getCreator().getId()));
				hm.put("time", str);
				myList.add(hm);
			}
			mAdapter.notifyDataSetChanged();
			memoryList.removeHeaderView(listFooter);//remove header
			System.out.println("data!!!");
		}
		
	}
	
	public final class ViewHolder{
		public TextView Time;
		public TextView Content;
		public ImageButton Delete;
	}
	private class myAdapter extends BaseAdapter {
		
		private LayoutInflater mInflater;
		

		public myAdapter(Context context) {
			this.mInflater=LayoutInflater.from(context);
			// TODO Auto-generated constructor stub
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return myList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder=null;
			if(convertView==null) {
				holder=new ViewHolder();
				convertView=mInflater.inflate(R.layout.memroyitemlayout, null);
				holder.Content=(TextView) convertView.findViewById(R.id.memoryContent);
				holder.Time=(TextView) convertView.findViewById(R.id.memoryTime);
				holder.Delete=(ImageButton) convertView.findViewById(R.id.memoryDelete);
				convertView.setTag(holder);
			} else {
				holder=(ViewHolder) convertView.getTag();
			}
			holder.Content.setText(myList.get(position).get("content"));
			holder.Time.setText(myList.get(position).get("time"));
			if(myList.get(position).get("creatorId").equals(String.valueOf(MemoryActivity.this.id))) {
				holder.Delete.setVisibility(View.VISIBLE);
			} else {
				holder.Delete.setVisibility(View.INVISIBLE);
			}
			
			final int deleteId=Integer.parseInt(myList.get(position).get("id"));
			final int listPosition=position;
			holder.Delete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					deleteMemoryAsyncTask deleteMemory=new deleteMemoryAsyncTask(deleteId,listPosition);
					deleteMemory.execute( );
					
				}
				
			});
			return convertView;
		}
		
	}
	private class deleteMemoryAsyncTask extends AsyncTask<Void,Void,Boolean> {
		
		private int memoryId;
		private int listPosition;
		

		public deleteMemoryAsyncTask(int memoryId,int listPosition) {
			super();
			this.memoryId = memoryId;
			this.listPosition=listPosition;
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			MemoryHandler memoryHandler=new MemoryHandlerImpl();
			return memoryHandler.deleteMemory(memoryId);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			
			mAdapter.notifyDataSetChanged();
			if(result==true) {
				myList.remove(listPosition);
				Log.i("DeleteMemory", "Success");
			} else {
				Log.i("DeleteMemory","Fail");
			}
		}
		
	}

}
