package com.adiaz.managetables.data;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.adiaz.managetables.data.ManageTablesContract.MealsEntry;
import static com.adiaz.managetables.data.ManageTablesContract.TableEntry;

/* Created by toni on 07/03/2017. */

public class ManageTablesDbHelper extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "bookings.db";
	private static final int DATABASE_VERSION = 5;
	private static final String TAG = ManageTablesDbHelper.class.getSimpleName();

	public ManageTablesDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase) {
		final String SQL_CREATE_TABLE_TABLES =
				"CREATE TABLE " + TableEntry.TABLE_NAME +
						"(" +
							TableEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
							TableEntry.COLUMN_NUMBER_TABLES + " INTEGER NOT NULL, " +
							TableEntry.COLUMN_NUMBER_PEOPLE + " INTEGER NOT NULL, " +
							" UNIQUE (" + TableEntry.COLUMN_NUMBER_PEOPLE + ") ON CONFLICT REPLACE " +
						")";
		sqLiteDatabase.execSQL(SQL_CREATE_TABLE_TABLES);
		final String SQL_CREATE_TABLE_MEALS =
				"CREATE TABLE " + MealsEntry.TABLE_NAME +
						"(" +
							MealsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
							MealsEntry.COLUMN_MEAL_HOUR + " INTEGER NOT NULL, " +
							MealsEntry.COLUMN_MEAL_MINUTES + " INTEGER NOT NULL, " +
							" UNIQUE (" + MealsEntry.COLUMN_MEAL_HOUR + "," + MealsEntry.COLUMN_MEAL_MINUTES + ") ON CONFLICT REPLACE " +
						")";
		sqLiteDatabase.execSQL(SQL_CREATE_TABLE_MEALS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
		try {
			final String SQL_DROP_TABLE_TABLES = "DROP TABLE " +  TableEntry.TABLE_NAME;
			sqLiteDatabase.execSQL(SQL_DROP_TABLE_TABLES);
		} catch (SQLException e) {
			Log.e(TAG, "onUpgrade: error ", e);
		}
		try {
			final String SQL_DROP_TABLE_MEALS = "DROP TABLE " +  MealsEntry.TABLE_NAME;
			sqLiteDatabase.execSQL(SQL_DROP_TABLE_MEALS);
		} catch (SQLException e) {
			Log.e(TAG, "onUpgrade: error", e);
		}
		onCreate(sqLiteDatabase);
	}
}
