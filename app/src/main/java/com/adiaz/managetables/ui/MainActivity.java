package com.adiaz.managetables.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.adiaz.managetables.R;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
	private static final String TAG = MainActivity.class.getSimpleName();

/*	@BindView(R.id.layout_functionalities)
	View layoutFunctionalities;

	@BindView(R.id.layout_buttons)
	View layoutButtons;

	@BindView(R.id.pb_loading)
	ProgressBar pbLoading;*/

	Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate: ");
		super.onCreate(savedInstanceState);
		/* check if is the first execution. */
//		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
/*		layoutFunctionalities.setVisibility(View.GONE);
		layoutButtons.setVisibility(View.GONE);
		pbLoading.setVisibility(View.VISIBLE);*/
		mContext = this;
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		String keyConfigDone = getString(R.string.key_config_created);
		boolean isConfigDone = preferences.getBoolean(keyConfigDone, false);
		if (!isConfigDone) {
			Intent intent = new Intent(mContext, ConfigAppActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent);
		} else {
			setContentView(R.layout.activity_main);
		}
	}

	public void newReservation(View view) {
		Intent intent = new Intent(this, NewReservationActivity.class);
		startActivity(intent);
	}

	public void reviewReservations(View view) {
		View viewMainActivity = findViewById(R.id.activity_main);
		final Snackbar snackbar = Snackbar.make(viewMainActivity, "Coming soon", Snackbar.LENGTH_SHORT);
		snackbar.show();
	}
}

