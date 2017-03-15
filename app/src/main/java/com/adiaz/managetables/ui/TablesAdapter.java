package com.adiaz.managetables.ui;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adiaz.managetables.R;
import com.adiaz.managetables.data.ManageTablesContract;
import com.adiaz.managetables.entities.Tables;

import butterknife.BindView;
import butterknife.ButterKnife;

/* Created by toni on 03/03/2017. */

class TablesAdapter extends RecyclerView.Adapter<TablesAdapter.TablesViewHolder> {


	private Context context;
	private Cursor cursor;

	public TablesAdapter(Context context) {
		this.context = context;
	}

	@Override
	public TablesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
		View view = layoutInflater.inflate(R.layout.list_item_tables, parent, false);
		return new TablesViewHolder(view);
	}

	@Override
	public void onBindViewHolder(TablesViewHolder holder, int position) {
		if (cursor !=null && cursor.moveToPosition(position)) {
			int people = cursor.getInt(ManageTablesContract.TableEntry.POSITION_PEOPLE);
			int tables = cursor.getInt(ManageTablesContract.TableEntry.POSITION_TABLES);
			String listItemTitle = context.getString(R.string.tables_list_item, tables, people);
			holder.textViewTable.setText(listItemTitle);
		}
	}

	@Override
	public int getItemCount() {
		return cursor ==null?0: cursor.getCount();
	}

	public void swapCursor(Cursor cursor) {
		this.cursor = cursor;
		notifyDataSetChanged();
	}

	public Tables getTableAtPosition(int position) {
		Tables tables = null;
		if (cursor !=null && cursor.moveToPosition(position)) {
			tables = new Tables(cursor);
		}
		return tables;
	}

	public class TablesViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.tv_add_table_list_item)
		TextView textViewTable;

		public TablesViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}
}
