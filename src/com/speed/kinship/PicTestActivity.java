package com.speed.kinship;

import com.speed.kinship.model.Pic;
import com.speed.kinship.net.Arguments;
import com.speed.kinship.net.MessageHandler;
import com.speed.kinship.net.MethodMessage;
import com.speed.kinship.util.PicFormatTools;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class PicTestActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pic_test);
		
		View button5 = findViewById(R.id.button5);
		button5.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ImageView imageView1 = (ImageView) findViewById(R.id.imageView1);
				Drawable picDrawable = imageView1.getDrawable();
				byte[] bytes = PicFormatTools.getInstance().Drawable2Bytes(picDrawable);
				Pic pic = new Pic(bytes);
				UploadTask uploadTask = new UploadTask(pic);
				uploadTask.execute();
			}
			
		});
		
	}
	
	private class UploadTask extends AsyncTask<Void, Void, Pic> {

		private Pic pic;
		
		public UploadTask(Pic pic) {
			this.pic = pic;
		}
		
		@Override
		protected Pic doInBackground(Void... params) {
			Arguments arguments = new Arguments();
			arguments.addArgument("pic", pic);
			MethodMessage methodMessage = new MethodMessage("uploadpic", arguments);
			MessageHandler messageHandler = new MessageHandler();
			Object resultObject = messageHandler.handleMessage(methodMessage);
			Pic result = (Pic) resultObject;
			return result;
		}
		
		@Override
		protected void onPostExecute(Pic result) {
			ImageView imageView2 = (ImageView) findViewById(R.id.imageView2);
			Bitmap bitmap = PicFormatTools.getInstance().Bytes2Bitmap(result.getContent());
			imageView2.setImageBitmap(bitmap);
			Log.i("upload", "succeed!");
		}
		
	}
	
}
