package com.adiaz.managetables.ui;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import com.adiaz.managetables.data.ManageTablesContract;

import java.util.Calendar;

/* Created by toni on 07/03/2017. */

public class AddMealTimeDialogFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

	private static final String TAG = AddMealTimeDialogFragment.class.getSimpleName();

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current time as the default values for the picker
		final Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);

		// Create a new instance of TimePickerDialog and return it
		return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
	}

	@Override
	public void onTimeSet(TimePicker timePicker, int i, int i1) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(ManageTablesContract.MealsEntry.COLUMN_MEAL_HOUR, timePicker.getCurrentHour());
		contentValues.put(ManageTablesContract.MealsEntry.COLUMN_MEAL_MINUTES, timePicker.getCurrentMinute());
		Uri uri = ManageTablesContract.MealsEntry.URI;
		getActivity().getContentResolver().insert(uri, contentValues);
		dismiss();
	}
}
