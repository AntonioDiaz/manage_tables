package com.adiaz.managetables.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.adiaz.managetables.R;
import com.adiaz.managetables.data.ManageTablesContract;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConfigAppActivity extends AppCompatActivity {

	@BindView(R.id.lv_config_tables)
	ListView lvTables;

	@BindView(R.id.lv_config_times)
	ListView lvTimes;

	CursorAdapter mealsCursorAdapter;

	CursorAdapter tablesCursorAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_config_app);
		ButterKnife.bind(this);
	}

	@Override
	protected void onStart() {
		super.onStart();
		/* query tables */
		Uri uriTables = ManageTablesContract.TableEntry.URI;
		String[] projectionTable = ManageTablesContract.TableEntry.TABLES_COLUMNS.toArray(new String[]{});
		String sortOrder = ManageTablesContract.TableEntry.COLUMN_NUMBER_PEOPLE + " asc ";
		Cursor cursorTables = getContentResolver().query(uriTables, projectionTable, null, null, sortOrder);
		tablesCursorAdapter = new CursorAdapterTables(this, cursorTables, false);
		lvTables.setAdapter(tablesCursorAdapter);
		tablesCursorAdapter.notifyDataSetChanged();
		/* query meals schedules */
		Uri uriMeals = ManageTablesContract.MealsEntry.URI;
		String[] projectionMeals = ManageTablesContract.MealsEntry.TABLES_COLUMNS.toArray(new String[]{});
		String sortOrderMeals = ManageTablesContract.MealsEntry.COLUMN_MEAL_HOUR
				+ ", "
				+ ManageTablesContract.MealsEntry.COLUMN_MEAL_MINUTES
				+ " asc ";
		Cursor cursorMeals = getContentResolver().query(uriMeals, projectionMeals, null, null, sortOrderMeals);
		mealsCursorAdapter = new CursorAdapterMeals(this, cursorMeals, false);
		lvTimes.setAdapter(mealsCursorAdapter);
		mealsCursorAdapter.notifyDataSetChanged();
	}

	public void configTables(View view) {
		Intent intent = new Intent(this, ConfigTablesActivity.class);
		startActivity(intent);
	}

	public void configSchedules(View view) {
		Intent intent = new Intent(this, ConfigSchedulesActivity.class);
		startActivity(intent);
	}

	public void createConfig(View view) {
		// TODO: 14/03/2017 check is there are meals and tables.
		if (mealsCursorAdapter.getCursor().getCount()>0 && tablesCursorAdapter.getCount()>0) {
			SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(this).edit();
			String key = getString(R.string.key_config_created);
			edit.putBoolean(key, true);
			edit.apply();
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
		} else {
			new AlertDialogFragment().show(getSupportFragmentManager(), "SHOW_DIALOG");
		}
	}
}
