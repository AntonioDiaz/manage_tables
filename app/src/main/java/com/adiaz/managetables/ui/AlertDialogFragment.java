package com.adiaz.managetables.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.adiaz.managetables.R;

/* Created by toni on 14/03/2017. */

public class AlertDialogFragment extends DialogFragment {
	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
			.setMessage(R.string.config_alert)
			.setPositiveButton(android.R.string.ok, null);
		return builder.create();
	}
}
