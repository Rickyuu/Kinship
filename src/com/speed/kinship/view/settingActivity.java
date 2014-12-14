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
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);

		numberMom = (EditText) findViewById(R.id.phoneNum1);
		numberDad = (EditText) findViewById(R.id.phoneNum2);
		numberChi = (EditText) findViewById(R.id.phoneNum3);
		confirm = (Button) findViewById(R.id.settingConfirm);

		Intent intent = getIntent();
		Bundle b = intent.getExtras();
		username = b.getString("userName");
		id = b.getString("id");
		identity = b.getString("identity");

		SharedPreferences file = getSharedPreferences("kinship_setting" + id,
				Context.MODE_PRIVATE);
		String mom = file.getString("mother", "");
		String dad = file.getString("father", "");
		String chi = file.getString("child", "");

		// editText2.setText(str1.toCharArray(), 0, str1.length());

		if (identity.equals("PARENT")) {
			numberMom.setVisibility(View.GONE);
			numberDad.setVisibility(View.GONE);
			numberChi.setText(chi.toCharArray(), 0, chi.length());
		} else {
			numberChi.setVisibility(View.GONE);
			numberMom.setText(mom.toCharArray(), 0, mom.length());
			numberDad.setText(dad.toCharArray(), 0, dad.length());
		}

		confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String momPhone = new String(numberMom.getText().toString());
				String dadPhone = new String(numberDad.getText().toString());
				String chiPhone = new String(numberChi.getText().toString());

				SharedPreferences file = getSharedPreferences("kinship_setting"
						+ id, Context.MODE_PRIVATE);
				Editor editor = file.edit();
				editor.putString("mother", momPhone);
				editor.putString("father", dadPhone);
				editor.putString("child", chiPhone);
				editor.commit();
				settingActivity.this.finish();

				/*
				 * SystemClock.sleep(1000); file =
				 * getSharedPreferences("kinship_setting"+id,
				 * Context.MODE_PRIVATE); String mom = file.getString("mother",
				 * ""); Log.e("result", mom);
				 */

				settingActivity.this.finish();
			}

		});

	}
}