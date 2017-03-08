package com.adiaz.managetables.data;

import android.net.Uri;
import android.provider.BaseColumns;

import com.google.common.collect.ImmutableList;

/** Created by toni on 07/03/2017. */

public class ManageTablesContract {

	public static final String AUTHORITY = "com.adiaz.managetables";
	public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);
	public static final String PATH_TABLE = "table";

	public static final class RestaurantTablesEntry implements BaseColumns {

		public static final Uri URI = BASE_URI.buildUpon().appendPath(PATH_TABLE).build();

		public static final String TABLE_NAME = "tables";
		public static final String COLUMN_NUMBER_TABLES = "column_number_tables";
		public static final String COLUMN_NUMBER_PEOPLE = "column_number_people";

		public static final int POSITION_ID = 0;
		public static final int POSITION_TABLES = 1;
		public static final int POSITION_PEOPLE = 2;

		public static final ImmutableList<String> TABLES_COLUMNS = ImmutableList.of(
				_ID,
				COLUMN_NUMBER_TABLES,
				COLUMN_NUMBER_PEOPLE
		);

		public static Uri makeUriFromTableId(long idNew) {
			String idNewStr = Long.toString(idNew);
			return URI.buildUpon().appendPath(idNewStr).build();
		}
	}
}
