package com.adiaz.managetables.entities;

import android.content.ContentValues;
import android.database.Cursor;

import static com.adiaz.managetables.data.ManageTablesContract.TableEntry;

/* Created by toni on 07/03/2017. */

public class Tables {

	private long id;
	private int tablesCount;
	private int peopleCount;

	public Tables(long id, int tablesCount, int peopleCount) {
		this.id = id;
		this.tablesCount = tablesCount;
		this.peopleCount = peopleCount;
	}

	public Tables (Cursor cursor) {
		if (cursor!=null) {
			this.id = cursor.getLong(TableEntry.POSITION_ID);
			this.tablesCount = cursor.getInt(TableEntry.POSITION_TABLES);
			this.peopleCount = cursor.getInt(TableEntry.POSITION_PEOPLE);
		}
	}

	public ContentValues getContentValues(){
		ContentValues contentValues = new ContentValues();
		contentValues.put(TableEntry.COLUMN_NUMBER_TABLES, this.tablesCount);
		contentValues.put(TableEntry.COLUMN_NUMBER_PEOPLE, this.peopleCount);
		return contentValues;
	}

	public int getTablesCount() {
		return tablesCount;
	}

	public void setTablesCount(int tablesCount) {
		this.tablesCount = tablesCount;
	}

	public int getPeopleCount() {
		return peopleCount;
	}

	public void setPeopleCount(int peopleCount) {
		this.peopleCount = peopleCount;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
