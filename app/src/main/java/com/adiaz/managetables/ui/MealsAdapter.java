package com.adiaz.managetables.ui;

/* Created by toni on 09/03/2017. */

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adiaz.managetables.R;
import com.adiaz.managetables.data.ManageTablesContract;
import com.adiaz.managetables.entities.Meals;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.ViewHolder> {

	private Context mContext;
	private Cursor mCursor;

	public MealsAdapter(Context mContext) {
		this.mContext = mContext;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
		View view = layoutInflater.inflate(R.layout.list_item_meals, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		if (mCursor !=null && mCursor.moveToPosition(position)) {
			int hours = mCursor.getInt(ManageTablesContract.MealsEntry.POSITION_HOUR);
			int minutes = mCursor.getInt(ManageTablesContract.MealsEntry.POSITION_MINUTES);
			String hourStr = mContext.getString(R.string.meals_list_item, hours, minutes);
			holder.textViewTable.setText(hourStr);
		}
	}

	@Override
	public int getItemCount() {
		return mCursor==null?0:mCursor.getCount();
	}

	public void setmCursor(Cursor mCursor) {
		this.mCursor = mCursor;
		notifyDataSetChanged();
	}

	public Meals getMealAtPosition(int adapterPosition) {
		Meals meals = null;
		if (mCursor!=null && mCursor.moveToPosition(adapterPosition)) {
			meals = new Meals(mCursor);
		}
		return meals;
	}

	public class ViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.tv_add_meal_list_item)
		TextView textViewTable;


		public ViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}
}
