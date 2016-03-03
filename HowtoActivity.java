package com.hbe.lemondash;


import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class HowtoActivity extends Activity {

	HowtoView mView;
	TextView text;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mView = new HowtoView(this);
		setContentView(R.layout.activity_main);
		text = (TextView)findViewById(R.id.text);

		final DBmanager dbManager = new DBmanager(getApplicationContext(), "Score.db", null, 1);
		

		text.setText(dbManager.PrintData());
	}
	
}
