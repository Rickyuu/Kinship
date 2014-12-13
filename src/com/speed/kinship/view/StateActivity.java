package com.speed.kinship.view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.speed.kinship.controller.impl.StateHandlerImpl;
import com.speed.kinship.model.State;
import com.speed.kinship.util.PicFormatTools;

//必须传入username以获取状态
public class StateActivity extends Activity{
	private ImageButton setting;
	private ImageButton create;
	private ListView list;
	private View listFooter;
	private Button timeline;
	private Button status;
	private Button memory;
	
	private String username;
	private String id;
	private String identity;
	
	private int startid;
	private stateAdapter mSchedule;
	ArrayList<HashMap<String, Object>> stlist;
	
	private boolean refreshable;
	private GestureDetector myGesture;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		Log.e("errorTag","already");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.state);
        //绑定XML中的ListView，作为Item的容器
        setting = (ImageButton) findViewById(R.id.imageButton2);
        list = (ListView) findViewById(R.id.listView1);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        listFooter = layoutInflater.inflate(R.layout.loadingfooter, null);
        create = (ImageButton) findViewById(R.id.imageButton1);
        timeline = (Button) findViewById(R.id.button2);
        status = (Button) findViewById(R.id.create);
        status.setTextColor(0xff0099ff);
        memory = (Button) findViewById(R.id.button3);
        
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        username = b.getString("userName");//待修改，主要获取username
        id = b.getString("id");
        identity = b.getString("identity");
        //username = new String("echo");
        //id = new String("3");
        //identity = new String("PARENT");
        setStartid(-1);
        stlist = new ArrayList<HashMap<String, Object>>();
        
        mSchedule = new stateAdapter(this, stlist);

        //View firstChild = list.getChildAt(0);
        //firstChild.getTop();
        list.addFooterView(listFooter);
        list.setAdapter(mSchedule);//添加并且显示
        
        GetStateAsyncTask stateTask = new GetStateAsyncTask();
        stateTask.execute();
        
        create.setOnClickListener(new OnClickListener(){
        	//跳转到StateCreate
        	@Override
        	public void onClick(View v){
        		Bundle data = new Bundle();
        		data.putString("userName",username);
        		data.putString("id", id);
        		data.putString("identity", identity);
        		Intent intent = new Intent(StateActivity.this, StateCreateActivity.class);
        		intent.putExtras(data);
        		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        		startActivity(intent);
        	}
        });
        
        setting.setOnClickListener(new OnClickListener(){
        	//跳转到StateCreate
        	@Override
        	public void onClick(View v){
        		Bundle data = new Bundle();
        		data.putString("userName",username);
        		data.putString("id", id);
        		data.putString("identity", identity);
        		Intent intent = new Intent(StateActivity.this, settingActivity.class);
        		intent.putExtras(data);
        		startActivity(intent);
        	}
        });
        
        timeline.setOnClickListener(new OnClickListener(){
        	//跳转到StateCreate
        	@Override
        	public void onClick(View v){
        		Bundle data = new Bundle();
        		data.putString("userName",username);
        		data.putString("id", id);
        		data.putString("identity", identity);
        		Intent intent = new Intent(StateActivity.this, ThingActivity.class);
        		intent.putExtras(data);
        		startActivity(intent);
        		StateActivity.this.finish();
        	}
        });
        
        status.setOnClickListener(new OnClickListener(){
        	//跳转到StateCreate
        	@Override
        	public void onClick(View v){
        		refreshStateAsyncTask stateTask = new refreshStateAsyncTask();
                stateTask.execute();
        	}
        });
        
        memory.setOnClickListener(new OnClickListener(){
        	//跳转到StateCreate
        	@Override
        	public void onClick(View v){
        		Bundle data = new Bundle();
        		data.putString("userName",username);
        		data.putString("id", id);
        		data.putString("identity", identity);
        		Intent intent = new Intent(StateActivity.this, MemoryActivity.class);
        		intent.putExtras(data);
        		startActivity(intent);
        		StateActivity.this.finish();
        	}
        });
        
        list.setOnScrollListener(new OnScrollListener(){
        	private int lastItemIndex;
        	private int FirstItemIndex;
        	
        	@Override
        	public void onScrollStateChanged(AbsListView view, int scrollState) {
                	if ((lastItemIndex == mSchedule.getCount() - 1) && scrollState == SCROLL_STATE_IDLE) {
                    	GetStateAsyncTask stateTask = new GetStateAsyncTask();
                        stateTask.execute();//加载数据代码-test
                    }
            }  
            @Override  
            public void onScroll(AbsListView view, int firstVisibleItem,  
                    int visibleItemCount, int totalItemCount) {  
                lastItemIndex = firstVisibleItem + visibleItemCount -1;
                FirstItemIndex = firstVisibleItem;
            }  
        });

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
		        	//if (e1.getX() - e2.getX() > 80||e1.getY()-e2.getY()>80) 
		        	if (e1.getX() - e2.getX() > 300) {    
		            
		        		Bundle data = new Bundle();
		        		data.putString("userName",username);
		        		data.putString("id", id);
		        		data.putString("identity", identity);
		        		Intent intent = new Intent(StateActivity.this, MemoryActivity.class);
		        		intent.putExtras(data);
		        		startActivity(intent);
		        		StateActivity.this.finish();
		                
		                return true;    
		            }   
		            //右滑动  
		            //else if (e1.getX() - e2.getX() <-80||e1.getY()-e2.getY()<-80) 
			        else if (e1.getX() - e2.getX() <-300){    
		              
			        	Bundle data = new Bundle();
		        		data.putString("userName",username);
		        		data.putString("id", id);
		        		data.putString("identity", identity);
		        		Intent intent = new Intent(StateActivity.this, ThingActivity.class);
		        		intent.putExtras(data);
		        		startActivity(intent);
		        		StateActivity.this.finish();
		                
		                return true;    
		            } 
			        else if ((e1.getY() - e2.getY() <-400) && getRefreshable()){    
			              
			        	refreshStateAsyncTask stateTask = new refreshStateAsyncTask();
		                stateTask.execute();
		                
		                return true;    
		            } 
				return false;
			}
			
		});//可能需要重写gestureDetector
    }
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent event){
		View firstChild = list.getChildAt(0);  
        if (firstChild != null) {  
            int firstVisiblePos = list.getFirstVisiblePosition();  
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
	
	private void setStartid(int i){
		startid = i;
	}
	
	public boolean setRefreshable(boolean status){
		refreshable = status;
		return true;
	}
	
	public boolean getRefreshable(){
		return refreshable;
	}
	
	private class GetStateAsyncTask extends AsyncTask<Void, Void, List<State>> {
		
		@Override
		protected void onPreExecute(){
			//Scroller.startScroll(getScrollX(), getScrollY(), 0, y); 
			list.addFooterView(listFooter);
			mSchedule.notifyDataSetChanged();
		}
		
		@Override
		protected List<State> doInBackground(Void... params) {
			StateHandlerImpl StateHler = new StateHandlerImpl();
	        List<State> stateList;
	        if (startid == -1){
	        	stateList = StateHler.getFirstNStates(username, 10);
	        }else{
	        	stateList = StateHler.getNextNStates(username, startid, 5);
	        }
			return stateList;
		}
		
		@Override
		protected void onPostExecute(List<State> stateList) {
			if(stateList != null){
				Iterator<State> it = stateList.iterator();
				HashMap<String, Object> map = null;  
				State ob = new State();
				for (; it.hasNext();) {
					ob = it.next();
					map = new HashMap<String, Object>(); 
					if(ob.getCreator().getId() == Integer.parseInt(id)){
						map.put("username", "Me");
					}else{
						if(ob.getCreator().getId()%2 == 1){
							map.put("username", "Mom and Dad");
						}else{
							map.put("username", "Child");
						}
					} 
					map.put("content", ob.getContent());
					DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
					map.put("time", format1.format(ob.getTime())); 
					map.put("stateId", Integer.toString(ob.getId()));
					if(ob.getFeedbacks() != null){
						feedbackList replies = new feedbackList();
						HashMap<String, Object> feedmap = null;
						for(int i=0; i<(ob.getFeedbacks().length); i++){
							feedmap = new HashMap<String, Object>();
							String fbCreator;
							if(ob.getFeedbacks()[i].getCreator().getId() == Integer.parseInt(id)){
								fbCreator = "Me";
							}else{
								if(ob.getFeedbacks()[i].getCreator().getId()%2 == 1){
									fbCreator = "Mom and Dad";
								}else{
									fbCreator = "Child";
								}
							} 
							//map.put("username", ob.getFeedbacks()[i].getCreator().getUserName());
							feedmap.put("creator", fbCreator);
							feedmap.put("feedbackId", String.valueOf(ob.getFeedbacks()[i].getId()));
							feedmap.put("content", fbCreator+" : "+ob.getFeedbacks()[i].getContent());
							replies.feedback.add(feedmap);
							map.put("feedbacks", replies);
							//#D3D3D3
						}
					}else{
						map.put("feedbacks", null);
					}
					if((ob.getPic() != null)){
						map.put("pic", ob.getPic());
					}else{
						map.put("pic", null);
					}
					stlist.add(map);  
				} 
				setStartid(ob.getId());
				mSchedule.notifyDataSetChanged();
			}else{
				;
			}
			list.removeFooterView(listFooter);
		}
		
	}
	
	private class refreshStateAsyncTask extends AsyncTask<Void, Void, List<State>> {
		
		@Override
		protected void onPreExecute(){
			list.addHeaderView(listFooter);//添加footer，header
			mSchedule.notifyDataSetChanged();
		}
		
		@Override
		protected List<State> doInBackground(Void... params) {
			StateHandlerImpl StateHler = new StateHandlerImpl();
	        List<State> stateList;
	        stateList = StateHler.getFirstNStates(username, 10);
			return stateList;
		}
		
		@Override
		protected void onPostExecute(List<State> stateList) {
			if(stateList != null){
				Iterator<State> it = stateList.iterator();
				HashMap<String, Object> map = null;  
				State ob = new State();
				stlist.clear();
				for (; it.hasNext();) {
					ob = it.next();
					map = new HashMap<String, Object>(); 
					if(ob.getCreator().getId() == Integer.parseInt(id)){
						map.put("username", "Me");
					}else{
						if(ob.getCreator().getId()%2 == 1){
							map.put("username", "Mom and Dad");
						}else{
							map.put("username", "Child");
						}
					} 
					map.put("content", ob.getContent());
					DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
					map.put("time", format1.format(ob.getTime())); 
					map.put("stateId", Integer.toString(ob.getId()));
					if(ob.getFeedbacks() != null){
						feedbackList replies = new feedbackList();
						HashMap<String, Object> feedmap = null;
						for(int i=0; i<(ob.getFeedbacks().length); i++){
							feedmap = new HashMap<String, Object>();
							String fbCreator;
							if(ob.getFeedbacks()[i].getCreator().getId() == Integer.parseInt(id)){
								fbCreator = "Me";
							}else{
								if(ob.getFeedbacks()[i].getCreator().getId()%2 == 1){
									fbCreator = "Mom and Dad";
								}else{
									fbCreator = "Child";
								}
							} 
							//map.put("username", ob.getFeedbacks()[i].getCreator().getUserName());
							feedmap.put("creator", fbCreator);
							feedmap.put("feedbackId", String.valueOf(ob.getFeedbacks()[i].getId()));
							feedmap.put("content", fbCreator+" : "+ob.getFeedbacks()[i].getContent());
							replies.feedback.add(feedmap);
							map.put("feedbacks", replies);
							//#D3D3D3
						}
					}else{
						map.put("feedbacks", null);
					}
					if((ob.getPic() != null)){
						map.put("pic", ob.getPic());
					}else{
						map.put("pic", null);
					}
					stlist.add(map);  
				} 
				setStartid(ob.getId());
				mSchedule.notifyDataSetChanged();
			}else{
				;
			}
			list.removeHeaderView(listFooter);
		}
		
	}
	
	
	public class stateAdapter extends BaseAdapter{
		
		private Context context;
		private LayoutInflater inflater;
		private ArrayList<HashMap<String, Object>> arrayList;
		private HashMap<String, Object> item;
		
		public stateAdapter(Context context, ArrayList<HashMap<String, Object>> arrayList){
			this.context = context;
			this.arrayList = arrayList;
			this.inflater = LayoutInflater.from(this.context);
		}
		
		public class ViewHolder {  
	        public TextView username;  
	        public TextView content;
	        public TextView time;
	        public ImageButton delete;
	        public ImageButton comment;
	        public ListView replies;
	        public ImageView image;
	    }  
		
		@Override
		public int getCount() {
			return arrayList.size();
		}
		@Override
		public Object getItem(int position) {
			return arrayList.get(position);
		}
		@Override
		public long getItemId(int position) {
			return position;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;  
	        convertView = inflater.inflate(R.layout.semistateitem, null);  
	  
	        holder = new ViewHolder();  
	        holder.username = (TextView) convertView.findViewById(R.id.ItemUser);  
	        holder.content = (TextView) convertView.findViewById(R.id.ItemText);  
	        holder.time = (TextView) convertView.findViewById(R.id.ItemTime);  
	        holder.delete = (ImageButton) convertView.findViewById(R.id.imageButtonDe);
	        holder.comment = (ImageButton) convertView.findViewById(R.id.imageButtonRe);
	        holder.replies = (ListView) convertView.findViewById(R.id.listViewReplies);
	        holder.image = (ImageView) convertView.findViewById(R.id.statusimage);
	        convertView.setTag(holder);  
	  
	        item = arrayList.get(position);  
	        holder.username.setText((String)item.get("username"));  
	        holder.content.setText((String)item.get("content"));
	        holder.time.setText((String)item.get("time"));
	        
	        if(item.get("pic") != null){
	        	Bitmap imageBitmap = PicFormatTools.getInstance().Bytes2Bitmap((byte[]) item.get("pic"));
	        	holder.image.setVisibility(View.VISIBLE);
	        	holder.image.setImageBitmap(imageBitmap);
	        }else{
	        	holder.image.setVisibility(View.GONE);
	        }
	        
	        if(item.get("feedbacks")!=null){
	        	final feedbackList feedbacks = (feedbackList)item.get("feedbacks");
	        	SimpleAdapter feedbackAdapter = new SimpleAdapter(context, feedbacks.feedback, R.layout.statereply, new String[]{"content"}, new int[]{R.id.stateReply});

	            holder.replies.setAdapter(feedbackAdapter);
	            holder.replies.setOnItemLongClickListener(new OnItemLongClickListener(){

					@Override
					public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
						if(feedbacks.feedback.get(position).get("creator") == "Me"){
							final int fposition = position;
							Builder dialog = new AlertDialog.Builder(context);
							dialog.setIcon(android.R.drawable.btn_star);
						    dialog.setTitle("Alert").setMessage("Delete this reply?").setPositiveButton("Yes", new DialogInterface.OnClickListener(){

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									deleteFeedbackTask deleteFbTask = new deleteFeedbackTask(Integer.parseInt(feedbacks.feedback.get(fposition).get("feedbackId").toString()), fposition, feedbacks);
				        			deleteFbTask.execute();
									
								}
						    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener(){

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									;
								}
						    });
						    dialog.show();// show很关键</span>  
							
		        			return true;
						}
						return false;
					}
	            	
	            });
	            setListViewHeightBasedOnChildren(holder.replies);
	        }else{
	        	holder.replies.setVisibility(View.INVISIBLE);
	        }
	        
	        holder.comment.setOnClickListener(new OnClickListener() { 
	        	final String stateid = (String)item.get("stateId");
	  
	            @Override  
	            public void onClick(View v) {  
	                //跳转到回复页面
	            	Log.e("ClickState", "ClickComment");
	            	Bundle data = new Bundle();
	        		data.putString("userName",username);
	        		data.putString("id", id);
	        		data.putString("stateId", stateid);
	        		data.putString("identity", identity);
	        		Intent intent = new Intent(StateActivity.this, FeedbackCreate.class);
	        		intent.putExtras(data);
	        		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        		startActivity(intent); 
	            }  
	        });
	        
	        if(item.get("username") == "Me"){
	        	final int itemPosition = position;
	        	final int stateid = Integer.parseInt((String)item.get("stateId"));
	        	final stateAdapter present = this;
	        	holder.delete.setOnClickListener(new OnClickListener() {
	        		
	        		@Override
	        		public void onClick(View v){
	        			//TODO
	        			Log.e("ClickState", "ClickDelete");
	        			deleteStateAsyncTask deleteTask = new deleteStateAsyncTask(stateid, itemPosition);
	        			deleteTask.execute();
	        		}
	        	});
	        }else{
	        	//holder.delete.setClickable(false);
	        	holder.delete.setVisibility(View.INVISIBLE);
	        }
	  
	        return convertView;  
	    }
		
		private class deleteFeedbackTask extends AsyncTask<Void, Void, Boolean> {
			
			private int feedbackId;
			private int position;
			feedbackList feedbacks;
			
			public deleteFeedbackTask(int feedbackid, int position, feedbackList feedbacks){
				this.feedbackId = feedbackid;
				this.position = position;
				this.feedbacks = feedbacks;
			}
			
			@Override
			protected Boolean doInBackground(Void... params) {
				StateHandlerImpl StateHler = new StateHandlerImpl();
		        boolean result = StateHler.deleteFeedback(feedbackId);
		        if(result == true){
					feedbacks.feedback.remove(position);
				}else{
					Log.e("DeleteFeedback","Failed");
				}
		        return (new Boolean(result));
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				if(result == true){
					mSchedule.notifyDataSetChanged();
				}else{
					Toast toast = Toast.makeText(getApplicationContext(), "Failed to delete feedback.", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				}
			}
			
		}
		
		private class deleteStateAsyncTask extends AsyncTask<Void, Void, Boolean> {
			
			private int stateId;
			private int position;
			
			public deleteStateAsyncTask(int stateid, int position){
				this.stateId = stateid;
				this.position = position;
			}
			
			@Override
			protected Boolean doInBackground(Void... params) {
				StateHandlerImpl StateHler = new StateHandlerImpl();
		        boolean result = StateHler.deleteState(stateId);
		        if(result == true){
					arrayList.remove(position);
				}else{
					Log.e("DeleteState","Failed");
				}
		        return (new Boolean(result));
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				mSchedule.notifyDataSetChanged();
			}
			
		}
	}
	
	public static void setListViewHeightBasedOnChildren(ListView listView) { 
	    if(listView == null) return;

	    ListAdapter listAdapter = listView.getAdapter(); 
	    if (listAdapter == null) { 
	        return; 
	    } 

	    int totalHeight = 0; 
	    for (int i = 0; i < listAdapter.getCount(); i++) { 
	        View listItem = listAdapter.getView(i, null, listView); 
	        listItem.measure(0, 0); 
	        totalHeight += listItem.getMeasuredHeight(); 
	    } 

	    ViewGroup.LayoutParams params = listView.getLayoutParams(); 
	    params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1)); 
	    listView.setLayoutParams(params); 
	}

}

class feedbackList{
	public ArrayList<HashMap<String, Object>> feedback;
	
	public feedbackList(){
		feedback = new ArrayList<HashMap<String, Object>>();
	}
}
