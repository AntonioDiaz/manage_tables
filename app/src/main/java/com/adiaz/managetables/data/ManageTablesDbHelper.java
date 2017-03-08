package com.adiaz.managetables.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static com.adiaz.managetables.data.ManageTablesContract.RestaurantTablesEntry;

/* Created by toni on 07/03/2017. */

public class ManageTablesDbHelper extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "bookings.db";
	private static final int DATABASE_VERSION = 3;

	public ManageTablesDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase) {
		final String SQL_CREATE_TABLE_TABLES =
				"CREATE TABLE " + RestaurantTablesEntry.TABLE_NAME +
						"(" +
							RestaurantTablesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
							RestaurantTablesEntry.COLUMN_NUMBER_TABLES + " INTEGER NOT NULL, " +
							RestaurantTablesEntry.COLUMN_NUMBER_PEOPLE + " INTEGER NOT NULL, " +
							" UNIQUE (" + RestaurantTablesEntry.COLUMN_NUMBER_PEOPLE + ") ON CONFLICT REPLACE " +
						")";
		sqLiteDatabase.execSQL(SQL_CREATE_TABLE_TABLES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
		final String SQL_DROP_TABLE_TABLES = "DROP TABLE " +  RestaurantTablesEntry.TABLE_NAME;
		sqLiteDatabase.execSQL(SQL_DROP_TABLE_TABLES);
		onCreate(sqLiteDatabase);
	}
}
