package com.adiaz.managetables.ui;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adiaz.managetables.R;
import com.adiaz.managetables.data.ManageTablesContract;

/* Created by toni on 14/03/2017. */

class CursorAdapterMeals extends CursorAdapter {
	public CursorAdapterMeals(Context context, Cursor cursorMeals, boolean b) {
		super(context, cursorMeals, b);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return LayoutInflater.from(context).inflate(R.layout.list_item_simple, parent, false);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		TextView tv = (TextView)view.findViewById(R.id.tv_list_item);
		int hour = cursor.getInt(ManageTablesContract.MealsEntry.POSITION_HOUR);
		int minutes = cursor.getInt(ManageTablesContract.MealsEntry.POSITION_MINUTES);
		String mealStr = context.getString(R.string.meals_list_item, hour, minutes);
		tv.setText(mealStr);
	}
}
