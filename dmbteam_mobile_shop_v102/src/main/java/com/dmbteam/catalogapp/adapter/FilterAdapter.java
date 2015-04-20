package com.dmbteam.catalogapp.adapter;

import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.dmbteam.catalogapp.MainActivity;
import com.dmbteam.catalogapp.R;
import com.dmbteam.catalogapp.cmn.Product;
import com.dmbteam.catalogapp.settings.AppSettings;
import com.dmbteam.catalogapp.util.Utils;

/**
 * The Class FilterAdapter.
 */
public class FilterAdapter extends
		RecyclerView.Adapter<FilterAdapter.ViewHolder> {

	/**
	 * The Class ViewHolder.
	 */
	public static class ViewHolder extends RecyclerView.ViewHolder {

		/** The main container. */
		private View mainContainer;
		
		/** The title. */
		private TextView title;
		
		/** The price. */
		private TextView price;

		/**
		 * Instantiates a new view holder.
		 *
		 * @param itemView the item view
		 */
		public ViewHolder(View itemView) {
			super(itemView);

			mainContainer = itemView.findViewById(R.id.list_item_filter_main);
			title = (TextView) itemView
					.findViewById(R.id.list_item_filter_title);
			price = (TextView) itemView
					.findViewById(R.id.list_item_filter_price);
		}
	}

	/** The Context. */
	private Context mContext;
	
	/** The Inflater. */
	private LayoutInflater mInflater;
	
	/** The Adapter data. */
	private List<Product> mAdapterData;

	/**
	 * Instantiates a new filter adapter.
	 *
	 * @param context the context
	 * @param filteredResult the filtered result
	 */
	public FilterAdapter(Context context, List<Product> filteredResult) {
		this.mContext = context;
		this.mInflater = LayoutInflater.from(this.mContext);
		this.mAdapterData = filteredResult;
	}

	/* (non-Javadoc)
	 * @see android.support.v7.widget.RecyclerView.Adapter#getItemCount()
	 */
	@Override
	public int getItemCount() {

		return mAdapterData.size();
	}

	/* (non-Javadoc)
	 * @see android.support.v7.widget.RecyclerView.Adapter#onCreateViewHolder(android.view.ViewGroup, int)
	 */
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View convertView = mInflater.inflate(R.layout.list_item_filter, parent,
				false);
		ViewHolder holder = new ViewHolder(convertView);

		return holder;
	}

	/* (non-Javadoc)
	 * @see android.support.v7.widget.RecyclerView.Adapter#onBindViewHolder(android.support.v7.widget.RecyclerView.ViewHolder, int)
	 */
	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		Product currentProduct = mAdapterData.get(position);

		holder.title.setText(currentProduct.getTitle());

		double price = 0.0;

		if (currentProduct.getDiscount() > 0.0) {
			price = (currentProduct.getDiscountedPrice());
		} else {
			price = (currentProduct.getPrice());
		}

		holder.price.setText(Utils.mFormatter.format(price)
				+ AppSettings.CURRENCY);

		holder.mainContainer.setOnClickListener(new OnItemClickListener(
				position));
	}

	/**
	 * The listener interface for receiving onItemClick events.
	 * The class that is interested in processing a onItemClick
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addOnItemClickListener<code> method. When
	 * the onItemClick event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see OnItemClickEvent
	 */
	private class OnItemClickListener implements View.OnClickListener {

		/** The position. */
		int position;

		/**
		 * Instantiates a new on item click listener.
		 *
		 * @param position the position
		 */
		public OnItemClickListener(int position) {
			super();
			this.position = position;
		}

		/* (non-Javadoc)
		 * @see android.view.View.OnClickListener#onClick(android.view.View)
		 */
		@Override
		public void onClick(View v) {
			((MainActivity) mContext).showSelectedProductFragment(true, false,
					mAdapterData, position);

			((MainActivity) mContext).showSearchAb();

			InputMethodManager imm = (InputMethodManager) ((MainActivity) mContext)
					.getSystemService(((MainActivity) mContext).INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(((MainActivity) mContext)
					.getAbSearchEt().getWindowToken(), 0);

		}

	}
}
