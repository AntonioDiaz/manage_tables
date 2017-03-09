package com.adiaz.managetables.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.adiaz.managetables.data.ManageTablesContract.MealsEntry;
import static com.adiaz.managetables.data.ManageTablesContract.TableEntry;

/* Created by toni on 07/03/2017. */

public class ManageTablesContentProvider extends ContentProvider {

	public static final String TAG = ManageTablesContentProvider.class.getSimpleName();
	
	ManageTablesDbHelper mDbHelper;

	public static final int TABLES = 100;
	public static final int TABLES_WITH_ID = 101;
	public static final int MEALS = 200;
	public static final int MEALS_WITH_ID = 201;

	private static final UriMatcher sUriMatcher = buildUriMatcher();

	private static UriMatcher buildUriMatcher() {
		UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(ManageTablesContract.AUTHORITY, ManageTablesContract.PATH_TABLE, TABLES);
		uriMatcher.addURI(ManageTablesContract.AUTHORITY, ManageTablesContract.PATH_TABLE + "/#", TABLES_WITH_ID);
		uriMatcher.addURI(ManageTablesContract.AUTHORITY, ManageTablesContract.PATH_MEAL, MEALS);
		uriMatcher.addURI(ManageTablesContract.AUTHORITY, ManageTablesContract.PATH_MEAL + "/#", MEALS_WITH_ID);
		return uriMatcher;
	}

	@Override
	public boolean onCreate() {
		mDbHelper = new ManageTablesDbHelper(getContext());
		return true;
	}

	@Nullable
	@Override
	public Cursor query(@NonNull Uri uri, @Nullable String[] projection,
						@Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
		Cursor cursorReturn = null;
		SQLiteDatabase database = mDbHelper.getReadableDatabase();
		switch (sUriMatcher.match(uri)) {
			case TABLES:
				cursorReturn = database.query(TableEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
				break;
			case MEALS:
				cursorReturn = database.query(MealsEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
				break;
			default:
				throw new UnsupportedOperationException("error " + uri);
		}
		if (cursorReturn!=null) {
			cursorReturn.setNotificationUri(getContext().getContentResolver(), uri);
		}
		return cursorReturn;
	}

	@Nullable
	@Override
	public String getType(@NonNull Uri uri) {
		return null;
	}

	@Nullable
	@Override
	public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
		Uri uriReturn = null;
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		switch (sUriMatcher.match(uri)) {
			case TABLES:
				long idNewTable = db.insert(TableEntry.TABLE_NAME, null, contentValues);
				if (idNewTable>0) {
					uriReturn = TableEntry.makeUriFromTableId(idNewTable);
				} else {
					throw new SQLException("error on insert " + uri);
				}
				break;
			case MEALS:
				long idNewMeal = db.insert(MealsEntry.TABLE_NAME, null, contentValues);
				if (idNewMeal>0) {
					uriReturn = MealsEntry.makeUriFromMealId(idNewMeal);
				} else {
					throw new SQLException("error on insert " + uri);
				}
				break;
			default:
				throw new UnsupportedOperationException("error " + uri);
		}
		if (uriReturn!=null) {
			getContext().getContentResolver().notifyChange(uriReturn, null);
		}
		return uriReturn;
	}

	@Override
	public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
		int deletes;
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		switch (sUriMatcher.match(uri)) {
			case TABLES_WITH_ID:
				String idTables = uri.getPathSegments().get(1);
				String whereClauseTables = "_id = ?";
				String[] whereArgsTables = new String[]{idTables};
				deletes = db.delete(TableEntry.TABLE_NAME, whereClauseTables, whereArgsTables);
				break;
			case MEALS_WITH_ID:
				String idMeals = uri.getPathSegments().get(1);
				String whereClauseMeals = "_id = ?";
				String[] whereArgsMeals = new String[]{idMeals};
				deletes = db.delete(MealsEntry.TABLE_NAME, whereClauseMeals, whereArgsMeals);
				break;
			default:
				throw new UnsupportedOperationException("error " + uri);
		}
		if (deletes>0) {
			getContext().getContentResolver().notifyChange(uri, null);
		}
		return deletes;
	}

	@Override
	public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
		return 0;
	}
}
