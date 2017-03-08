package com.adiaz.managetables.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.adiaz.managetables.R;
import com.adiaz.managetables.data.ManageTablesContract;

import butterknife.BindView;
import butterknife.ButterKnife;

/* Created by toni on 02/03/2017. */

public class AddTableDialogFragment extends DialogFragment {

	private AlertDialog mAlertDialog;

	@BindView(R.id.tv_add_table_dialog_people)
	EditText tvPeople;

	@BindView(R.id.tv_add_table_dialog_tables)
	EditText tvTables;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater layoutInflater = getActivity().getLayoutInflater();
		View view = layoutInflater.inflate(R.layout.dialog_add_table, null);
		ButterKnife.bind(this, view);
		TextWatcher textWatcher = new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

			@Override
			public void afterTextChanged(Editable editable) {
				enableAccept();
			}
		};
		tvPeople.addTextChangedListener(textWatcher);
		tvTables.addTextChangedListener(textWatcher);
		builder.setView(view);
		builder.setPositiveButton(getString(R.string.button_accept), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				addTable();
			}
		});

		builder.setNegativeButton(getString(R.string.button_cancel), null);

		mAlertDialog = builder.create();
		Window window = mAlertDialog.getWindow();
		if (window!=null) {
			window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		}
		return mAlertDialog;
	}

	private void enableAccept() {
		boolean isAcceptEnabled = false;
		if (!TextUtils.isEmpty(tvPeople.getText()) && !TextUtils.isEmpty(tvTables.getText())) {
			isAcceptEnabled = true;
		}
		mAlertDialog.getButton(Dialog.BUTTON_POSITIVE).setEnabled(isAcceptEnabled);
	}

	private void addTable() {
		Integer tables = Integer.parseInt(tvTables.getText().toString());
		Integer people = Integer.parseInt(tvPeople.getText().toString());
		Uri uri = ManageTablesContract.RestaurantTablesEntry.URI;
		ContentValues contentValues = new ContentValues();
		contentValues.put(ManageTablesContract.RestaurantTablesEntry.COLUMN_NUMBER_TABLES, tables);
		contentValues.put(ManageTablesContract.RestaurantTablesEntry.COLUMN_NUMBER_PEOPLE, people);
		getActivity().getContentResolver().insert(uri, contentValues);
		dismiss();
	}

	@Override
	public void onStart() {
		super.onStart();
		enableAccept();
	}
}
