package com.speed.kinship;

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
				UploadTask uploadTask = new UploadTask(bytes);
				uploadTask.execute();
			}
			
		});
		
	}
	
	private class UploadTask extends AsyncTask<Void, Void, byte[]> {

		private byte[] pic;
		
		public UploadTask(byte[] pic) {
			this.pic = pic;
		}
		
		@Override
		protected byte[] doInBackground(Void... params) {
			Arguments arguments = new Arguments();
			arguments.addArgument("pic", pic);
			MethodMessage methodMessage = new MethodMessage("uploadpic", arguments);
			MessageHandler messageHandler = new MessageHandler();
			Object resultObject = messageHandler.handleMessage(methodMessage);
			byte[] result = (byte[]) resultObject;
			return result;
		}
		
		@Override
		protected void onPostExecute(byte[] result) {
			ImageView imageView2 = (ImageView) findViewById(R.id.imageView2);
			Bitmap bitmap = PicFormatTools.getInstance().Bytes2Bitmap(result);
			imageView2.setImageBitmap(bitmap);
			Log.i("upload", "succeed!");
		}
		
	}
	
}
