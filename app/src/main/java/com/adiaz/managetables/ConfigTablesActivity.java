package com.adiaz.managetables;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConfigTablesActivity extends AppCompatActivity implements SaveChangesFragmentDialog.SaveChangesDialogListener {

	private static final String TAG = ConfigTablesActivity.class.getSimpleName();
	private static final String TABLES_ADDED = "TABLES_ADDED";
	@BindView(R.id.rv_tables_list)
	RecyclerView rvTables;

	@BindView(R.id.tv_empty_tables_list)
	TextView tvEmptyTablesList;

	private TablesAdapter mAdapter;
	private List<Integer[]> tables;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_config_tables);
		ButterKnife.bind(this);

		tables = new ArrayList<>();
		if (savedInstanceState!=null && savedInstanceState.containsKey(TABLES_ADDED)) {
			tables = convertStringArrayListToList(savedInstanceState.getStringArrayList(TABLES_ADDED));
		}
		Log.d(TAG, "onCreate: tables.size() " + tables.size());
		mAdapter = new TablesAdapter();
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
				tables.remove(viewHolder.getAdapterPosition());
				refreshAdapter();
			}
		}).attachToRecyclerView(rvTables);

		refreshAdapter();
		ActionBar actionBar = getSupportActionBar();
		if (actionBar!=null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
	}

	private List<Integer[]> convertStringArrayListToList(ArrayList<String> stringArrayList) {
		List<Integer[]> listAux = new ArrayList<>();
		for (String s : stringArrayList) {
			Integer n0 = Integer.valueOf(s.split("-")[0]);
			Integer n1 = Integer.valueOf(s.split("-")[1]);
			listAux.add(new Integer[]{n0, n1});
		}
		return listAux;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putStringArrayList(TABLES_ADDED, convertListToStringList(tables));
	}

	private ArrayList<String> convertListToStringList(List<Integer[]> tables) {
		ArrayList<String> listAux = new ArrayList<>();
		for (Integer[] table : tables) {
			listAux.add(table[0] + "-" + table[1]);
		}
		return listAux;
	}

	private void refreshAdapter() {
		if (tables.size()>0) {
			List<String> tablesStr = new ArrayList<>();
			for (Integer[] table : tables) {
				tablesStr.add(getString(R.string.tables_list_item, table[0], table[1]));
			}
			mAdapter.setTablesList(tablesStr);
			tvEmptyTablesList.setVisibility(View.INVISIBLE);
			rvTables.setVisibility(View.VISIBLE);
		} else {
			tvEmptyTablesList.setVisibility(View.VISIBLE);
			rvTables.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.config_tables, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_save_tables:
				break;
			case android.R.id.home:
				confirmBack();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void confirmBack() {
		new SaveChangesFragmentDialog().show(getFragmentManager(), "SaveChangesDialog");
		//onBackPressed();
	}

	public void openDialogAddTable(View view) {
		new AddTableDialogFragment().show(getFragmentManager(), "AddTableDialog");
	}

	public void addTable(Integer tablesNumber, Integer peopleNumber) {
		tables.add(new Integer[]{tablesNumber, peopleNumber});
		refreshAdapter();
//		mAdapter.
/*		MatrixCursor cursor = (MatrixCursor) mAdapter.();
		Toast.makeText(this, "cursor " + cursor.getCount(), Toast.LENGTH_SHORT).show();
		Log.d(TAG, "addTable: tables " + tables);
		Log.d(TAG, "addTable: people " + people);
		cursor.addRow(new String[]{Integer.toString(cursor.getCount()), tables.toString(), people.toString()});
		mAdapter.swapCursor(cursor);*/
	}

	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		onBackPressed();
	}
}
