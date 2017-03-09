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
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.adiaz.managetables.R;
import com.adiaz.managetables.data.ManageTablesContract;
import com.adiaz.managetables.entities.Tables;
import com.google.common.collect.ImmutableList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConfigTablesActivity extends AppCompatActivity
		implements LoaderManager.LoaderCallbacks<Cursor> {

	private static final String TAG = ConfigTablesActivity.class.getSimpleName();
	private static final String BUNDLE_TABLES_ADDED = "bundle_tables_added";
	private static final int ID_LOADER_TABLES = 11;

	@BindView(R.id.rv_tables_list)
	RecyclerView rvTables;

	@BindView(R.id.tv_empty_tables_list)
	TextView tvEmptyTablesList;

	private TablesAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_config_tables);
		ButterKnife.bind(this);

		mAdapter = new TablesAdapter(this);
		rvTables.setHasFixedSize(false);
		rvTables.setLayoutManager(new LinearLayoutManager(this));
		rvTables.setAdapter(mAdapter);

		new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
			@Override
			public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
				return false;
			}

			@Override
			public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
				Tables tables = mAdapter.getTableAtPosition(viewHolder.getAdapterPosition());
				Uri uri = ManageTablesContract.TableEntry.makeUriFromTableId(tables.getId());
				getContentResolver().delete(uri, null, null);
			}
		}).attachToRecyclerView(rvTables);
		getSupportLoaderManager().initLoader(ID_LOADER_TABLES, null, this);
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
	}

	private void refreshAdapter(Cursor cursor) {
		if (cursor!=null && cursor.getCount() > 0) {
			mAdapter.swapCursor(cursor);
			tvEmptyTablesList.setVisibility(View.INVISIBLE);
			rvTables.setVisibility(View.VISIBLE);
		} else {
			tvEmptyTablesList.setVisibility(View.VISIBLE);
			rvTables.setVisibility(View.INVISIBLE);
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

	public void openDialogAddTable(View view) {
		new AddTableDialogFragment().show(getFragmentManager(), "AddTableDialog");
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		Uri uri = ManageTablesContract.TableEntry.URI;
		ImmutableList<String> tablesColumns = ManageTablesContract.TableEntry.TABLES_COLUMNS;
		String[] projection = tablesColumns.toArray(new String[]{});
		String orderField = ManageTablesContract.TableEntry.COLUMN_NUMBER_PEOPLE + " asc";
		return new CursorLoader(this, uri, projection, null, null, orderField);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		refreshAdapter(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {}
}
