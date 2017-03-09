package com.adiaz.managetables.ui;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.adiaz.managetables.R;
import com.adiaz.managetables.entities.Meals;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.adiaz.managetables.data.ManageTablesContract.MealsEntry;

public class ConfigSchedulesActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

	private static final String TAG = ConfigSchedulesActivity.class.getSimpleName();
	private static final int ID_LOADER_MEALS = 12;

	@BindView(R.id.rv_meals_list)
	RecyclerView rvMeals;

	@BindView(R.id.tv_empty_meals_list)
	TextView tvEmptyMealsList;

	private MealsAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_config_schedules);
		ButterKnife.bind(this);
		mAdapter = new MealsAdapter(this);
		rvMeals.setAdapter(mAdapter);
		rvMeals.setHasFixedSize(false);
		rvMeals.setLayoutManager(new LinearLayoutManager(this));

		new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
			@Override
			public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
				return false;
			}

			@Override
			public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
				Meals meals = mAdapter.getMealAtPosition(viewHolder.getAdapterPosition());
				Uri uri = MealsEntry.makeUriFromMealId(meals.getId());
				Log.d(TAG, "onSwiped: " + uri.toString());
				getContentResolver().delete(uri, null, null);
			}
		}).attachToRecyclerView(rvMeals);

		getSupportLoaderManager().initLoader(ID_LOADER_MEALS, null, this);

		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				onBackPressed();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void openDialogAddTime(View view) {
		new AddMealTimeDialogFragment().show(getFragmentManager(), "AddMealTimeDialog");
	}

	private void refreshAdapter(Cursor cursor) {
		if (cursor!=null && cursor.getCount()>0) {
			mAdapter.setmCursor(cursor);
			rvMeals.setVisibility(View.VISIBLE);
			tvEmptyMealsList.setVisibility(View.INVISIBLE);
		} else {
			rvMeals.setVisibility(View.INVISIBLE);
			tvEmptyMealsList.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		String sortOrder = MealsEntry.COLUMN_MEAL_HOUR + " asc ";
		String[] projection = MealsEntry.TABLES_COLUMNS.toArray(new String[]{});
		return new CursorLoader(this, MealsEntry.URI, projection,null, null, sortOrder);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		refreshAdapter(data);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {

	}
}
