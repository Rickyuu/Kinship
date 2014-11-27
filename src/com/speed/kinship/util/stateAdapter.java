package com.speed.kinship.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.speed.kinship.controller.impl.StateHandlerImpl;
import com.speed.kinship.model.State;
import com.speed.kinship.view.R;
import com.speed.kinship.view.StateActivity;
import com.speed.kinship.view.StateCreateActivity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

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
        if (convertView == null) {  
            convertView = inflater.inflate(R.layout.semistateitem, null);  
  
            holder = new ViewHolder();  
            holder.username = (TextView) convertView.findViewById(R.id.ItemUser);  
            holder.content = (TextView) convertView.findViewById(R.id.ItemText);  
            holder.time = (TextView) convertView.findViewById(R.id.ItemTime);  
            holder.delete = (ImageButton) convertView.findViewById(R.id.imageButtonDe);
            holder.comment = (ImageButton) convertView.findViewById(R.id.imageButtonRe);
            holder.replies = (ListView) convertView.findViewById(R.id.listViewReplies);
            convertView.setTag(holder);  
        } else {  
            holder = (ViewHolder) convertView.getTag();  
        }  
  
        item = arrayList.get(position);  
        holder.username.setText((String)item.get("username"));  
        holder.content.setText((String)item.get("content"));
        holder.time.setText((String)item.get("time"));
        //此处添加评论区设置
        holder.replies.setVisibility(View.INVISIBLE);
  
        holder.comment.setOnClickListener(new OnClickListener() {  
  
            @Override  
            public void onClick(View v) {  
                //跳转到回复页面
            	Log.e("ClickState", "ClickComment");
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
        			present.notifyDataSetChanged();
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
		
	}
}