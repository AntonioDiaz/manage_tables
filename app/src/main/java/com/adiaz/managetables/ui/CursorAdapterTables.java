package com.adiaz.managetables.ui;

/* Created by toni on 14/03/2017. */

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adiaz.managetables.R;
import com.adiaz.managetables.data.ManageTablesContract;

public class CursorAdapterTables extends CursorAdapter {


	public CursorAdapterTables(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return LayoutInflater.from(context).inflate(R.layout.list_item_simple, parent, false);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		TextView text1 = (TextView) view.findViewById(R.id.tv_list_item);
		int tables = cursor.getInt(ManageTablesContract.TableEntry.POSITION_TABLES);
		int people = cursor.getInt(ManageTablesContract.TableEntry.POSITION_PEOPLE);
		String hourStr = mContext.getString(R.string.tables_list_item, tables, people);
		text1.setText(hourStr);
	}
}
