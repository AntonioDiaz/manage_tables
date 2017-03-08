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

import static com.adiaz.managetables.data.ManageTablesContract.RestaurantTablesEntry;

/* Created by toni on 07/03/2017. */

public class ManageTablesContentProvider extends ContentProvider {

	public static final String TAG = ManageTablesContentProvider.class.getSimpleName();
	
	ManageTablesDbHelper mDbHelper;

	public static final int TABLES = 100;
	public static final int TABLES_WITH_ID = 101;

	private static final UriMatcher sUriMatcher = buildUriMatcher();

	private static UriMatcher buildUriMatcher() {
		UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(ManageTablesContract.AUTHORITY, ManageTablesContract.PATH_TABLE, TABLES);
		uriMatcher.addURI(ManageTablesContract.AUTHORITY, ManageTablesContract.PATH_TABLE + "/#", TABLES_WITH_ID);
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
				cursorReturn = database.query(RestaurantTablesEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
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
		switch (sUriMatcher.match(uri)) {
			case TABLES:
				SQLiteDatabase db = mDbHelper.getWritableDatabase();
				long idNew = db.insert(RestaurantTablesEntry.TABLE_NAME, null, contentValues);
				if (idNew>0) {
					uriReturn = RestaurantTablesEntry.makeUriFromTableId(idNew);
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
		switch (sUriMatcher.match(uri)) {
			case TABLES_WITH_ID:
				SQLiteDatabase db = mDbHelper.getWritableDatabase();
				String id = uri.getPathSegments().get(1);
				String whereClause = "_id = ?";
				String[] whereArgs = new String[]{id};
				deletes = db.delete(RestaurantTablesEntry.TABLE_NAME, whereClause, whereArgs);
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
