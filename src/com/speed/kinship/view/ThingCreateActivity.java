package com.speed.kinship.view;
/*# CSIT 6000B    
 *# Liang You      20229016   yliangao@connect.ust.hk
 *# Zhan Xiaojun   20244793   xzhanab@connect.ust.hk
 *# Tao Ye         20225905   ytaoac@connect.ust.hk  
 * */ 
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.speed.kinship.controller.ThingHandler;
import com.speed.kinship.controller.impl.ThingHandlerImpl;
import com.speed.kinship.model.Identity;
import com.speed.kinship.model.Thing;
import com.speed.kinship.model.User;
import com.speed.kinship.util.PicFormatTools;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

@SuppressLint("SimpleDateFormat") 
public class ThingCreateActivity extends Activity {
	private EditText thingTitle;
	private EditText thingContent;
	private Button post;
	private Button thingPictureChoose;
	private DatePicker chooseDate;
	private int id;
	private String identity;
	private String userName;
	
	private Bitmap imageBitmap;
	private ImageView imageAttach;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.thingcreate);
		thingTitle=(EditText) findViewById(R.id.thingEditTitle);
		thingContent=(EditText) findViewById(R.id.thingEditContent);
		post=(Button) findViewById(R.id.post);
		chooseDate=(DatePicker) findViewById(R.id.chooseDate);
		thingPictureChoose=(Button) findViewById(R.id.chooseThingPic);
		imageAttach=(ImageView) findViewById(R.id.imageAttachThing);
		imageAttach.setVisibility(View.INVISIBLE);
		Intent intent=getIntent();
		id=Integer.parseInt(intent.getStringExtra("id"));
		identity=intent.getStringExtra("identity");
		userName=intent.getStringExtra("userName");
		
		final User user=new User();
		if(identity.equals("PARENT")) {
			user.setIdentity(Identity.PARENT);
			user.setUserName(userName);
			user.setId(id);
		} else {
			user.setIdentity(Identity.CHILD);
			user.setUserName(userName);
			user.setId(id);
		}
		
		thingPictureChoose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(ThingCreateActivity.this, selectingPhotoActivity.class);
				startActivityForResult(intent, 1);
			}
			
		});
		
		post.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String title=thingTitle.getText().toString();
				String dateChosen=String.valueOf(chooseDate.getYear())+"-"+String.valueOf(chooseDate.getMonth()+1)+"-"+String.valueOf(chooseDate.getDayOfMonth());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date=null;
				try {
					date = sdf.parse(dateChosen);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String content=thingContent.getText().toString();
				addThingAsyncTask addthing=new addThingAsyncTask(user,title,date,content,imageBitmap);
				addthing.execute( );
				
			}
			
		});
		
	}
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
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
	        if(imageBitmap!=null) {
	        	imageAttach.setVisibility(View.VISIBLE);
				imageAttach.setImageBitmap(imageBitmap);
				imageAttach.postInvalidate();
	        } else {
	        	imageAttach.setVisibility(View.INVISIBLE);
	        }
			

		}
		
	}
	
	public static interface DataFinishListener { 
        void dataFinishSuccessfully(); 
    } 



	private class addThingAsyncTask extends AsyncTask<Void,Void, Thing> {
		private User user;
		private String title;
		private Date time;
		private String content;
		private Bitmap imageBitmap;
		

		public addThingAsyncTask(User user, String title, Date time,
				String content, Bitmap imageBitmap) {
			super();
			this.user = user;
			this.title = title;
			this.time = time;
			this.content = content;
			this.imageBitmap=imageBitmap;
		}

		@Override
		protected Thing doInBackground(Void... params) {
			// TODO Auto-generated method stub
			ThingHandler thingHandler=new ThingHandlerImpl();
			if(imageBitmap == null){
				return thingHandler.addThing(user, title, time, content, null);
			}else{
				byte[] pic;
				pic = PicFormatTools.getInstance().Bitmap2Bytes(imageBitmap);
				return thingHandler.addThing(user, title, time, content, pic);
			}
			
		}

		@Override
		protected void onPostExecute(Thing result) {
			// TODO Auto-generated method stub
			if(result!=null) {
				Log.i("Post", "Success!");
//				Intent intent=new Intent();
//				intent.setClass(ThingCreateActivity.this, ThingActivity.class);
//				intent.putExtra("id", String.valueOf(id));
//				intent.putExtra("identity", identity);
//				intent.putExtra("userName",userName);
//				startActivity(intent);
				ThingCreateActivity.this.finish();
				
			} else {
				Log.i("Post","Fail!");
			}
		}
		
	}
	

}
