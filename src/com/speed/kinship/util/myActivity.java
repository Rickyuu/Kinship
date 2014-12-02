package com.speed.kinship.util;  

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;

public class myActivity extends Activity{
	private boolean refreshable;
	private GestureDetector myGesture;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
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
		        	if (e1.getX() - e2.getX() > 80||e1.getY()-e2.getY()>80) {    
		            
		                Log.e("flag","左滑动");  
		                
		                return true;    
		            }   
		            //右滑动  
		            else if (e1.getX() - e2.getX() <-80||e1.getY()-e2.getY()<-80) {    
		              
		                Log.e("flag","右滑动");  
		                
		                return true;    
		            } 
				return false;
			}
			
		});//可能需要重写gestureDetector
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent event){
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

}