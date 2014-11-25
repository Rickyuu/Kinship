package com.speed.kinship.view;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGestureListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Scroller;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;

import com.speed.kinship.controller.impl.StateHandlerImpl;
import com.speed.kinship.model.Feedback;
import com.speed.kinship.model.Identity;
import com.speed.kinship.model.State;
import com.speed.kinship.model.User;

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
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.state);
        //绑定XML中的ListView，作为Item的容器
        setting = (ImageButton) findViewById(R.id.imageButton2);
        list = (ListView) findViewById(R.id.listView1);
        //listFooter = (View) findViewById(R.layout.loadingfooter);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        listFooter = layoutInflater.inflate(R.layout.loadingfooter, null);
        create = (ImageButton) findViewById(R.id.imageButton1);
        timeline = (Button) findViewById(R.id.button2);
        status = (Button) findViewById(R.id.create);
        memory = (Button) findViewById(R.id.button3);
        //status.setClickable(false);
        //status.setSelected(true);
        
        /*Intent intent = getIntent();
        Bundle b = intent.getExtras();
        username = b.getString("userName");//待修改，主要获取username
        id = b.getString("id");
        identity = b.getString("identity");*/
        username = new String("echo");
        id = new String("3");
        identity = new String("PARENT");
        setStartid(-1);
        stlist = new ArrayList<HashMap<String, Object>>();
        
        mSchedule = new stateAdapter(this, stlist);

        list.addFooterView(listFooter);
        list.setAdapter(mSchedule);//添加并且显示
        //list.setVisibility(View.INVISIBLE);
        
        GetStateAsyncTask stateTask = new GetStateAsyncTask();
        stateTask.execute();
        
        create.setOnClickListener(new OnClickListener(){
        	//跳转到StateCreate
        	@Override
        	public void onClick(View v){
        		Bundle data = new Bundle();
        		data.putString("username",username);
        		data.putString("id", id);
        		data.putString("identity", identity);
        		Intent intent = new Intent(StateActivity.this, StateCreateActivity.class);
        		intent.putExtras(data);
        		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        		startActivity(intent);
        	}
        });
        
        timeline.setOnClickListener(new OnClickListener(){
        	//跳转到StateCreate
        	@Override
        	public void onClick(View v){
        		Bundle data = new Bundle();
        		data.putString("username",username);
        		data.putString("id", id);
        		data.putString("identity", identity);
        		Intent intent = new Intent(StateActivity.this, ThingActivity.class);
        		intent.putExtras(data);
        		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        		startActivity(intent);
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
        		data.putString("username",username);
        		data.putString("id", id);
        		data.putString("identity", identity);
        		Intent intent = new Intent(StateActivity.this, MemoryActivity.class);
        		intent.putExtras(data);
        		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        		startActivity(intent);
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
            //这三个int类型的参数可以自行Log打印一下就知道是什么意思了  
            @Override  
            public void onScroll(AbsListView view, int firstVisibleItem,  
                    int visibleItemCount, int totalItemCount) {  
                //ListView 的FooterView也会算到visibleItemCount中去，所以要再减去一  
                lastItemIndex = firstVisibleItem + visibleItemCount -1;
                FirstItemIndex = firstVisibleItem;
            }  
        });

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
							feedmap.put("content", fbCreator+" : "+ob.getFeedbacks()[i].getContent());
							replies.feedback.add(feedmap);
							map.put("feedbacks", replies);
							//#D3D3D3
						}
					}else{
						map.put("feedbacks", null);
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
							feedmap = new HashMap<String, Object>();String fbCreator;
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
							feedmap.put("content", fbCreator+" : "+ob.getFeedbacks()[i].getContent());
							replies.feedback.add(feedmap);
							map.put("feedbacks", replies);
							//#D3D3D3
						}
					}else{
						map.put("feedbacks", null);
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
	
	private void setStartid(int i){
		startid = i;
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
	        //if (convertView == null) {  
	            convertView = inflater.inflate(R.layout.semistateitem, null);  
	  
	            holder = new ViewHolder();  
	            holder.username = (TextView) convertView.findViewById(R.id.ItemUser);  
	            holder.content = (TextView) convertView.findViewById(R.id.ItemText);  
	            holder.time = (TextView) convertView.findViewById(R.id.ItemTime);  
	            holder.delete = (ImageButton) convertView.findViewById(R.id.imageButtonDe);
	            holder.comment = (ImageButton) convertView.findViewById(R.id.imageButtonRe);
	            holder.replies = (ListView) convertView.findViewById(R.id.listViewReplies);
	            convertView.setTag(holder);  
	        //} else {  
	        //    holder = (ViewHolder) convertView.getTag();  
	        //}  
	  
	        item = arrayList.get(position);  
	        holder.username.setText((String)item.get("username"));  
	        holder.content.setText((String)item.get("content"));
	        holder.time.setText((String)item.get("time"));
	        //此处添加评论区设置
	        if(item.get("feedbacks")!=null){
	        	SimpleAdapter mSchedule = new SimpleAdapter(context, ((feedbackList)item.get("feedbacks")).feedback, R.layout.statereply, new String[]{"content"}, new int[]{R.id.stateReply});

	            holder.replies.setAdapter(mSchedule);
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
	        		data.putString("username",username);
	        		data.putString("id", id);
	        		data.putString("stateId", stateid);
	        		data.putString("identity", identity);
	        		Intent intent = new Intent(StateActivity.this, FeedbackCreate.class);
	        		intent.putExtras(data);
	        		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        		startActivity(intent);
	                // notifyDataSetChanged();  
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
	        // pre-condition 
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
