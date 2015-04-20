package com.dmbteam.catalogapp.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dmbteam.catalogapp.MainActivity;
import com.dmbteam.catalogapp.R;
import com.dmbteam.catalogapp.adapter.FilterAdapter;
import com.dmbteam.catalogapp.adapter.MainProductAdapter;
import com.dmbteam.catalogapp.cmn.Product;

/**
 * The Class FragmentFilter.
 */
public class FragmentFilter extends Fragment {

	/** The Constant TAG. */
	public static final String TAG = FragmentFilter.class.getSimpleName();
	
	/** The Adapter data. */
	private List<Product> mAdapterData;

	/**
	 * New instance.
	 *
	 * @param adapterData the adapter data
	 * @return the fragment filter
	 */
	public static FragmentFilter newInstance(List<Product> adapterData) {

		FragmentFilter fragmentMain = new FragmentFilter();
		fragmentMain.mAdapterData = adapterData;

		return fragmentMain;
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onResume()
	 */
	@Override
	public void onResume() {
		super.onResume();
	}

	/** The Parent view. */
	private LinearLayout mParentView;
	
	/** The Adapter main. */
	private FilterAdapter mAdapterMain;
	
	/** The Recycler view. */
	private RecyclerView mRecyclerView;
	
	/** The Layout manager. */
	private LinearLayoutManager mLayoutManager;

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mParentView = (LinearLayout) inflater.inflate(R.layout.fragment_main,
				container, false);

		mRecyclerView = (RecyclerView) mParentView
				.findViewById(android.R.id.list);
		mLayoutManager = new LinearLayoutManager(getActivity());
		mRecyclerView.setLayoutManager(mLayoutManager);
		
		mAdapterMain = new FilterAdapter(getActivity(), mAdapterData);

		mRecyclerView.setAdapter(mAdapterMain);

		return mParentView;
	}

	/**
	 * Notify change.
	 *
	 * @param filteredProducts the filtered products
	 */
	public void notifyChange(List<Product> filteredProducts) {

		mAdapterMain = new FilterAdapter(getActivity(), filteredProducts);

		mRecyclerView.setAdapter(mAdapterMain);

		
		mAdapterMain.notifyDataSetChanged();
	}
}
