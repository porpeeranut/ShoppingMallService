package com.dmbteam.catalogapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dmbteam.catalogapp.MainActivity;
import com.dmbteam.catalogapp.R;
import com.dmbteam.catalogapp.adapter.CheckoutPreviewAdapter;

/**
 * The Class FragmentCartPreview.
 */
public class FragmentCartPreview extends Fragment {

	/** The Constant TAG. */
	public static final String TAG = FragmentCartPreview.class.getSimpleName();
	
	/** The Constant MAIL_REQUEST_CODE. */
	public static final int MAIL_REQUEST_CODE = 999;

	/**
	 * New instance.
	 *
	 * @return the fragment cart preview
	 */
	public static FragmentCartPreview newInstance() {
		FragmentCartPreview fragment = new FragmentCartPreview();

		return fragment;
	}

	/** The Parent view. */
	private View mParentView;
	
	/** The Recycler view. */
	private RecyclerView mRecyclerView;
	
	/** The Layout manager. */
	private LinearLayoutManager mLayoutManager;
	
	/** The Adapter. */
	private CheckoutPreviewAdapter mAdapter;

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mParentView = inflater.inflate(R.layout.fragment_cart_preview, null);

		mRecyclerView = (RecyclerView) mParentView
				.findViewById(android.R.id.list);
		mLayoutManager = new LinearLayoutManager(getActivity());
		mRecyclerView.setLayoutManager(mLayoutManager);

		mAdapter = new CheckoutPreviewAdapter(getActivity(), true);

		mRecyclerView.setAdapter(mAdapter);

		return mParentView;
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onResume()
	 */
	@Override
	public void onResume() {
		super.onResume();

		((MainActivity) getActivity()).showOrderSummaryAb();
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onActivityCreated(android.os.Bundle)
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}
}
