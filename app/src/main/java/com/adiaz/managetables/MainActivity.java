package com.adiaz.managetables;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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
}
