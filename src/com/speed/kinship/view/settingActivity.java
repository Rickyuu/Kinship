package com.speed.kinship.view;

import java.io.File;
import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class settingActivity extends Activity {
	private EditText numberMom;
	private EditText numberDad;
	private EditText numberChi;
	private Button confirm;
	
	private String username;
	private String id;
	private String identity;
	
	@Override 
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        
        numberMom = (EditText) findViewById (R.id.phoneNum1);
        numberDad = (EditText) findViewById (R.id.phoneNum2);
        numberChi = (EditText) findViewById (R.id.phoneNum3);
        confirm = (Button) findViewById (R.id.settingConfirm);
        
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        username = b.getString("userName");
        id = b.getString("id");
        identity = b.getString("identity");
        
        confirm.setOnClickListener(new OnClickListener(){
        	
			@Override
			public void onClick(View v) {
				String momPhone = new String(numberMom.getText().toString());
				String dadPhone = new String(numberDad.getText().toString());
				String chiPhone = new String(numberChi.getText().toString());
				if(momPhone.isEmpty() && dadPhone.isEmpty() && chiPhone.isEmpty()){
					Toast toast = Toast.makeText(getApplicationContext(), "You cannot set empty phone numbers.", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				}else{
					SharedPreferences file = getSharedPreferences("kinship_setting"+id, Context.MODE_PRIVATE);
					Editor editor = file.edit();
					editor.putString("mother", momPhone);
					editor.putString("father", dadPhone);
					editor.putString("child", chiPhone);
					editor.commit();
					
					/*SystemClock.sleep(1000);
					file = getSharedPreferences("kinship_setting"+id, Context.MODE_PRIVATE);
					String mom = file.getString("mother", "");
					Log.e("result", mom);*/
				}
			}
        });
        
	}
}