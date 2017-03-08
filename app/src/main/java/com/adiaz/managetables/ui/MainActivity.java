package com.adiaz.managetables.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.adiaz.managetables.R;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* check if is the first execution. */
		setContentView(R.layout.activity_main);
	}

	public void configTables(View view) {
		Intent intent = new Intent(this, ConfigTablesActivity.class);
		startActivity(intent);
	}


	public void configSchedules(View view) {
		Intent intent = new Intent(this, ConfigSchedulesActivity.class);
		startActivity(intent);
	}
}
