package com.dmbteam.catalogapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dmbteam.catalogapp.MainActivity;
import com.dmbteam.catalogapp.R;
import com.dmbteam.catalogapp.adapter.CartAdapter;
import com.dmbteam.catalogapp.cart.CartManager;
import com.dmbteam.catalogapp.settings.AppSettings;
import com.dmbteam.catalogapp.util.SwipeDismissRecyclerViewTouchListener;

/**
 * The Class FragmentCart.
 */
public class FragmentCart extends Fragment {

	/** The Constant TAG. */
	public static final String TAG = FragmentCart.class.getSimpleName();

	/**
	 * New instance.
	 *
	 * @return the fragment cart
	 */
	public static FragmentCart newInstance() {
		FragmentCart fragment = new FragmentCart();

		return fragment;
	}

	/** The Parent view. */
	private View mParentView;
	
	/** The Recycler view. */
	private RecyclerView mRecyclerView;
	
	/** The saved value. */
	private TextView savedValue;
	
	/** The subtotal value. */
	private TextView subtotalValue;
	
	/** The vat value. */
	private TextView vatValue;
	
	/** The total value. */
	private TextView totalValue;
	
	/** The vat label. */
	private TextView vatLabel;
	
	/** The Layout manager. */
	private LinearLayoutManager mLayoutManager;
	
	/** The Adapter. */
	private CartAdapter mAdapter;

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		((MainActivity) getActivity()).setAbTitle("");

		mParentView = inflater.inflate(R.layout.fragment_cart, null);

		mRecyclerView = (RecyclerView) mParentView
				.findViewById(android.R.id.list);
		mLayoutManager = new LinearLayoutManager(getActivity());
		mRecyclerView.setLayoutManager(mLayoutManager);

		mAdapter = new CartAdapter(getActivity(), this);

		mRecyclerView.setAdapter(mAdapter);

		SwipeDismissRecyclerViewTouchListener touchListener = new SwipeDismissRecyclerViewTouchListener(
				mRecyclerView,
				new SwipeDismissRecyclerViewTouchListener.DismissCallbacks() {
					@Override
					public boolean canDismiss(int position) {
						return true;
					}

					@Override
					public void onDismiss(RecyclerView recyclerView,
							int[] reverseSortedPositions) {
						for (int position : reverseSortedPositions) {

							// TODO: this is temp solution for preventing
							// blinking item onDismiss
							mLayoutManager.findViewByPosition(position)
									.setVisibility(View.GONE);

							CartManager.getInstance().removeItem(position); // mItems.remove(position);
							mAdapter.notifyItemRemoved(position);
							generateValues();
						}
					}
				});
		mRecyclerView.setOnTouchListener(touchListener);
		// Setting this scroll listener is required to ensure that during
		// ListView scrolling,
		// we don't look for swipes.
		mRecyclerView.setOnScrollListener(touchListener.makeScrollListener());

		return mParentView;
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onResume()
	 */
	@Override
	public void onResume() {
		super.onResume();

		((MainActivity) getActivity()).showCheckoutAb();
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onActivityCreated(android.os.Bundle)
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		savedValue = (TextView) mParentView
				.findViewById(R.id.fra_cart_tv_saved_value);

		subtotalValue = (TextView) mParentView
				.findViewById(R.id.fra_cart_tv_subtotal_value);

		vatLabel = (TextView) mParentView
				.findViewById(R.id.fra_cart_tv_vat_label);
		vatValue = (TextView) mParentView
				.findViewById(R.id.fra_cart_tv_vat_value);

		if (AppSettings.VAT == 0.0) {
			vatLabel.setVisibility(View.GONE);
			vatValue.setVisibility(View.GONE);
		} else {
			vatLabel.setText(String.format(getString(R.string.fra_cart_vat), ""
					+ (int) (AppSettings.VAT * 100)));
		}

		totalValue = (TextView) mParentView
				.findViewById(R.id.fra_cart_tv_total_value);

		generateValues();
	}

	/**
	 * Generate values.
	 */
	public void generateValues() {
		savedValue.setText(CartManager.getInstance().getSavedValue()
				+ AppSettings.CURRENCY);
		subtotalValue.setText(CartManager.getInstance().getSubTotal()
				+ AppSettings.CURRENCY);
		vatValue.setText(CartManager.getInstance().getVat()
				+ AppSettings.CURRENCY);
		totalValue.setText(CartManager.getInstance().getTotal()
				+ AppSettings.CURRENCY);
	}
}
