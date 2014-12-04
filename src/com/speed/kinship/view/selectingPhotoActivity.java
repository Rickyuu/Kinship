package com.speed.kinship.view;

import java.io.File;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class selectingPhotoActivity extends Activity{
	private GridView gridView;
	private MyPhotoAdapter adapter;
	private HashMap<Integer, String> mediaImage;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photoselection);
		
		gridView = (GridView) findViewById(R.id.gridview);
		mediaImage = getMediaImage(this);
		adapter = new MyPhotoAdapter(this, mediaImage);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent resultIntent = new Intent();
				String url = mediaImage.get(position);
				resultIntent.putExtra("imageUrl", url); 
				setResult(Activity.RESULT_OK, resultIntent); 
				selectingPhotoActivity.this.finish();
			}
			
		});
	}
	
	public class MyPhotoAdapter extends BaseAdapter {

		private DisplayImageOptions options;
		private ImageLoader imageLoader;
		private Context context;
		private HashMap<Integer, String> datas;

		
		public MyPhotoAdapter(Context context, HashMap<Integer, String> datas){
			this.context = context;
			this.datas = datas;
			int size = datas.size();
			
			imageLoader = ImageLoader.getInstance();
			imageLoader.init(ImageLoaderConfiguration.createDefault(context));
			
			options = new DisplayImageOptions.Builder()
			.cacheInMemory(true)
			.cacheOnDisk(true)
			.considerExifParams(true)
			.bitmapConfig(Bitmap.Config.RGB_565)
			.build();
			
		}
		
		@Override
		public int getCount() {
			return datas.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			ImageView imaView;
			if(convertView == null){
				view = LayoutInflater.from(context).inflate(R.layout.photoitem, parent , false);
				
				imaView = (ImageView) view.findViewById(R.id.statusimage);
				view.setTag(imaView);
			} else{
				imaView = (ImageView) view.getTag();
			}
			imageLoader.displayImage(datas.get(position), imaView, options);
			
			return view;
		}
	}
	
	public static HashMap<Integer, String> getMediaImage(Context context) {  
        HashMap<Integer, String> datas = new HashMap<Integer, String>();  
        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;  
        final String[] columns = { MediaStore.Images.Media.DATA,  
                MediaStore.Images.Media._ID,  
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME };  
        Cursor imagecursor = context.getContentResolver().query(  
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,  
                null, orderBy + " DESC");  
  
        for (int i = 0; i < imagecursor.getCount(); i++) {  
            imagecursor.moveToPosition(i);  
            int dataColumnIndex = imagecursor  
                    .getColumnIndex(MediaStore.Images.Media.DATA);  
            int dirColumnIndex = imagecursor  
                    .getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);  
            String buckedName = imagecursor.getString(dirColumnIndex);  
            Log.v("image", "buckedName = " + buckedName);  
            String filename = imagecursor.getString(dataColumnIndex);  
            try {  
                File file = new File(filename);  
                if (!file.exists()) {  
                    continue;  
                }  
            } catch (Exception e) {  
                continue;  
            }  
  
            Log.v("image", "filename = " + filename);  
            String galleryModel = new String("file:/" + imagecursor.getString(dataColumnIndex).toString());
            datas.put(i, galleryModel);  
        }  
        imagecursor.close();  
        return datas;  
    }  
}