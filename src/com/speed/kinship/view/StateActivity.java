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
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Scroller;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;

import com.speed.kinship.controller.UserHandler;
import com.speed.kinship.controller.impl.StateHandlerImpl;
import com.speed.kinship.controller.impl.UserHandlerImpl;
import com.speed.kinship.model.Identity;
import com.speed.kinship.model.State;
import com.speed.kinship.model.User;

//���봫��username�Ի�ȡ״̬
public class StateActivity extends Activity{
	private ImageButton setting;
	private ImageButton create;
	private ListView list;
	private View listFooter;
	private Button timeline;
	//private Button status;
	private Button memory;
	
	private String username;
	private String id;
	private String identity;
	
	private int startid;
	private SimpleAdapter mSchedule;
	ArrayList<HashMap<String, Object>> stlist;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.state);
        //��XML�е�ListView����ΪItem������
        setting = (ImageButton) findViewById(R.id.imageButton2);
        list = (ListView) findViewById(R.id.listView1);
        //listFooter = (View) findViewById(R.layout.loadingfooter);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        listFooter = layoutInflater.inflate(R.layout.loadingfooter, null);
        create = (ImageButton) findViewById(R.id.imageButton1);
        timeline = (Button) findViewById(R.id.button2);
        //status = (Button) findViewById(R.id.create);
        memory = (Button) findViewById(R.id.button3);
        
        /*Intent intent = getIntent();
        Bundle b = intent.getExtras();
        username = b.getString("userName");//���޸ģ���Ҫ��ȡusername
        id = b.getString("id");
        identity = b.getString("identity");*/
        username = new String("echo");
        id = new String("3");
        identity = new String("PARENT");
        setStartid(-1);
        stlist = new ArrayList<HashMap<String, Object>>();
        
        mSchedule = new SimpleAdapter(this, 
				  	stlist,//������Դ 
				  	R.layout.stateitem,//ListItem��XMLʵ��
				  	new String[] {"userName", "content", "time"}, //��̬������ListItem��Ӧ������ 
				  	new int[] {R.id.ItemUser,R.id.ItemText,R.id.ItemTime}); //ListItem��XML�ļ����������TextView ID  	

        list.addFooterView(listFooter);
        list.setAdapter(mSchedule);//��Ӳ�����ʾ
        
        GetStateAsyncTask stateTask = new GetStateAsyncTask();
        stateTask.execute();
        
        create.setOnClickListener(new OnClickListener(){
        	//��ת��StateCreate
        	@Override
        	public void onClick(View v){
        		Bundle data = new Bundle();
        		data.putString("username",username);
        		data.putString("id", id);
        		data.putString("identity", identity);
        		Intent intent = new Intent(StateActivity.this, StateCreateActivity.class);
        		intent.putExtras(data);
        		startActivity(intent);
        	}
        });
        
        timeline.setOnClickListener(new OnClickListener(){
        	//��ת��StateCreate
        	@Override
        	public void onClick(View v){
        		Bundle data = new Bundle();
        		data.putString("username",username);
        		data.putString("id", id);
        		data.putString("identity", identity);
        		Intent intent = new Intent(StateActivity.this, ThingActivity.class);
        		intent.putExtras(data);
        		startActivity(intent);
        	}
        });
        
        memory.setOnClickListener(new OnClickListener(){
        	//��ת��StateCreate
        	@Override
        	public void onClick(View v){
        		Bundle data = new Bundle();
        		data.putString("username",username);
        		data.putString("id", id);
        		data.putString("identity", identity);
        		Intent intent = new Intent(StateActivity.this, MemoryActivity.class);
        		intent.putExtras(data);
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
                        stateTask.execute();//�������ݴ���-test
                    }
            }  
            //������int���͵Ĳ�����������Log��ӡһ�¾�֪����ʲô��˼��  
            @Override  
            public void onScroll(AbsListView view, int firstVisibleItem,  
                    int visibleItemCount, int totalItemCount) {  
                //ListView ��FooterViewҲ���㵽visibleItemCount��ȥ������Ҫ�ټ�ȥһ  
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
					map.put("userName", ob.getCreator().getUserName());  
					map.put("content", ob.getContent());
					DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
					map.put("time", format1.format(ob.getTime())); 
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
			list.addHeaderView(listFooter);//���footer��header
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
					map.put("userName", ob.getCreator().getUserName());  
					map.put("content", ob.getContent());
					DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
					map.put("time", format1.format(ob.getTime())); 
					stlist.add(map);  
				} 
				setStartid(ob.getId());
			}else{
				;
			}
			if(stlist != null){
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
	
}
