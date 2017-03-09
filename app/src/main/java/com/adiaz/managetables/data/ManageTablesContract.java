package com.adiaz.managetables.data;

import android.net.Uri;
import android.provider.BaseColumns;

import com.google.common.collect.ImmutableList;

/** Created by toni on 07/03/2017. */

public class ManageTablesContract {

	public static final String AUTHORITY = "com.adiaz.managetables";
	public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);
	public static final String PATH_TABLE = "table";
	public static final String PATH_MEAL = "meal";

	public static final class TableEntry implements BaseColumns {

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

		public static Uri makeUriFromTableId(long idTable) {
			String idTableStr = Long.toString(idTable);
			return URI.buildUpon().appendPath(idTableStr).build();
		}
	}

	public static final class MealsEntry implements BaseColumns {

		public static final Uri URI = BASE_URI.buildUpon().appendPath(PATH_MEAL).build();

		public static final String TABLE_NAME = "meals";
		public static final String COLUMN_MEAL_HOUR = "column_meal_hour";
		public static final String COLUMN_MEAL_MINUTES = "column_meal_minutes";

		public static final int POSITION_ID = 0;
		public static final int POSITION_HOUR = 1;
		public static final int POSITION_MINUTES = 2;
		public static final ImmutableList<String> TABLES_COLUMNS = ImmutableList.of(
				_ID,
				COLUMN_MEAL_HOUR,
				COLUMN_MEAL_MINUTES
		);
		public static Uri makeUriFromMealId(long idMeal) {
			String idMealStr = Long.toString(idMeal);
			return URI.buildUpon().appendPath(idMealStr).build();
		}

	}
}
