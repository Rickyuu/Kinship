package com.speed.kinship.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.speed.kinship.controller.impl.StateHandlerImpl;
import com.speed.kinship.model.Identity;
import com.speed.kinship.model.State;
import com.speed.kinship.model.User;
import com.speed.kinship.util.PicFormatTools;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class StateCreateActivity extends Activity {
	private EditText text;
	private Button post;
	private Button picture;
	private ImageView imageAttach;
	
	private String username;
	private String id;
	private String identity;
	
	Bitmap imageBitmap;
	
	@Override 
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
        setContentView(R.layout.statecreat);
        
        text = (EditText) findViewById (R.id.newText);
        post = (Button) findViewById (R.id.create);
        picture = (Button) findViewById (R.id.buttonAddPic);
        imageAttach = (ImageView) findViewById(R.id.addimage);
        
        imageBitmap = null;
        
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        username = b.getString("userName");//待修改，主要获取username
        id = b.getString("id");
        identity = b.getString("identity");
        
        post.setOnClickListener(new OnClickListener(){
        	
			@Override
			public void onClick(View v) {
				String context = new String(text.getText().toString());
				if(context.isEmpty()){
					Toast toast = Toast.makeText(getApplicationContext(), "You cannot post an empty status.", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				}else{
					PostStateAsyncTask postState = new PostStateAsyncTask(username, id, identity, context, imageBitmap);
					postState.execute();
					Bundle data = new Bundle();
	        		data.putString("username",username);
	        		data.putString("id", id);
	        		data.putString("identity", identity);
	        		Intent intent = new Intent(StateCreateActivity.this, StateActivity.class);
	        		intent.putExtras(data);
	        		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        		startActivity(intent);
				}
			}
        });
        
        
        picture.setOnClickListener(new OnClickListener(){
        	
			@Override
			public void onClick(View v) {
        		Intent intent = new Intent(StateCreateActivity.this, selectingPhotoActivity.class);

        		startActivityForResult(intent, 1);//REQUESTCODE = 1				
			}
        });
        
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if(resultCode==Activity.RESULT_OK && requestCode==1){
			String imaUrl = data.getStringExtra("imageUrl").substring(6);
			Options options = new Options();
			options.inJustDecodeBounds = true;
			imageBitmap = BitmapFactory.decodeFile(imaUrl, options);
			// Math.ceil表示获取与它最近的整数（向上取值 如：4.1->5 4.9->5）  
	        int widthRatio = (int) Math.ceil(options.outWidth / 400);  
	        int heightRatio = (int) Math.ceil(options.outHeight / 400);  
	          
	        // 设置最终加载的像素比例，表示最终显示的像素个数为总个数的  
	        if (widthRatio > 1 || heightRatio > 1) {  
	            if (widthRatio > heightRatio) {  
	                options.inSampleSize = widthRatio;  
	            } else {  
	                options.inSampleSize = heightRatio;  
	            }  
	        }  
	        // 解码像素的模式，在该模式下可以直接按照option的配置取出像素点
	        options.inJustDecodeBounds = false; 
	        imageBitmap = BitmapFactory.decodeFile(imaUrl, options);
			imageAttach.setVisibility(View.VISIBLE);
			imageAttach.setImageBitmap(imageBitmap);
			imageAttach.postInvalidate();

		}
	}
	
	public static interface DataFinishListener { 
	        void dataFinishSuccessfully(); 
	    } 
	
	private class PostStateAsyncTask extends AsyncTask<Void, Void, State> {
		
		private String username;
		private String id;
		private String identity;
		private String context;
		private Bitmap imageBitmap;

		public PostStateAsyncTask(String username, String id, String identity, String context, Bitmap imageBitmap) {
			this.username = username;
			this.id = id;
			this.identity = identity;
			this.context = context;
			this.imageBitmap = imageBitmap;
		}
		
		@Override
		protected State doInBackground(Void... params) {
			User creator = new User();
			creator.setId(Integer.parseInt(id));
			creator.setUserName(username);
			StateHandlerImpl StateHler = new StateHandlerImpl();
			State feedback;
			if(imageBitmap == null){
				feedback = StateHler.addState(creator, context, null);
			}else{
				byte[] pic;
				pic = PicFormatTools.getInstance().Bitmap2Bytes(imageBitmap);
				feedback = StateHler.addState(creator, context, pic);
			}
			return feedback;
		}
		
		@Override
		protected void onPostExecute(State feedback) {
			if(feedback == null){
				Toast toast = Toast.makeText(getApplicationContext(), "Post Failed. Please try again.", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			}else{
				Toast toast = Toast.makeText(getApplicationContext(), "Post new state successfully.", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			}
		}
		
	}


}

