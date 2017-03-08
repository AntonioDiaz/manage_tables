package com.adiaz.managetables.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.adiaz.managetables.R;

public class ConfigSchedulesActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_config_schedules);
	}

	public void openDialogAddTime(View view) {
		new AddMealTimeDialogFragment().show(getFragmentManager(), "AddMealTimeDialog");
	}
}
