package com.adiaz.managetables.entities;

import android.content.ContentValues;
import android.database.Cursor;

import static com.adiaz.managetables.data.ManageTablesContract.MealsEntry;

/**
 * Created by toni on 09/03/2017.
 */

public class Meals {

	private long id;
	private int hours;
	private int minutes;

	public Meals(long id, int hours, int minutes) {
		this.id = id;
		this.hours = hours;
		this.minutes = minutes;
	}

	public Meals (Cursor cursor) {
		if (cursor!=null) {
			this.id = cursor.getLong(MealsEntry.POSITION_ID);
			this.hours = cursor.getInt(MealsEntry.POSITION_HOUR);
			this.minutes = cursor.getInt(MealsEntry.POSITION_MINUTES);
		}
	}

	public ContentValues getContentValues(){
		ContentValues contentValues = new ContentValues();
		contentValues.put(MealsEntry.COLUMN_MEAL_HOUR, this.getHours());
		contentValues.put(MealsEntry.COLUMN_MEAL_MINUTES, this.getMinutes());
		return contentValues;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}
}
