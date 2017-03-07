package com.adiaz.managetables;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by toni on 03/03/2017.
 */

class TablesAdapter extends RecyclerView.Adapter<TablesAdapter.TablesViewHolder> {

	private List<String> tablesList;

	@Override
	public TablesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
		View view = layoutInflater.inflate(R.layout.tables_list_item, parent, false);
		return new TablesViewHolder(view);
	}

	@Override
	public void onBindViewHolder(TablesViewHolder holder, int position) {
		holder.textViewTable.setText(tablesList.get(position));
	}

	@Override
	public int getItemCount() {
		return tablesList==null?0:tablesList.size();
	}

	public void setTablesList(List<String> tablesList) {
		this.tablesList = tablesList;
		notifyDataSetChanged();
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
