package com.adiaz.managetables;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by toni on 03/03/2017.
 */

public class SaveChangesFragmentDialog extends DialogFragment {

	private SaveChangesDialogListener mListener;

	@Override
	public void onAttach(Context activity) {
		super.onAttach(activity);
		try {
			mListener = (SaveChangesDialogListener)activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement SaveChangesDialogListener");
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder
				.setMessage("Change will be lost, do you want to back")
				.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						mListener.onDialogPositiveClick(SaveChangesFragmentDialog.this);
					}
				})
				.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						dismiss();
					}
				})
				.setCancelable(true);
		return builder.create();
	}


	public interface SaveChangesDialogListener {
		public void onDialogPositiveClick(DialogFragment dialog);
	}

}
