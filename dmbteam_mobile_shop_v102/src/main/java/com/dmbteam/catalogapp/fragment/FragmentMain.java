package com.dmbteam.catalogapp.fragment;

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
import com.dmbteam.catalogapp.adapter.MainProductAdapter;
import com.dmbteam.catalogapp.cmn.Product;

/**
 * The Class FragmentMain.
 */
public class FragmentMain extends Fragment {

	/** The Constant TAG. */
	public static final String TAG = FragmentMain.class.getSimpleName();
	
	/** The Adapter data. */
	private List<List<Product>> mAdapterData;

	/**
	 * New instance.
	 *
	 * @param adapterData the adapter data
	 * @return the fragment main
	 */
	public static FragmentMain newInstance(List<List<Product>> adapterData) {

		FragmentMain fragmentMain = new FragmentMain();
		fragmentMain.mAdapterData = adapterData;

		return fragmentMain;
	}
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onResume()
	 */
	@Override
	public void onResume() {
		super.onResume();
		
		((MainActivity)getActivity()).showCommonAb();
		((MainActivity) getActivity()).refreshCartCounter();
	}

	/** The Parent view. */
	private LinearLayout mParentView;
	
	/** The Adapter main. */
	private MainProductAdapter mAdapterMain;
	
	/** The Recycler view. */
	private RecyclerView mRecyclerView;
	
	/** The Layout manager. */
	private LinearLayoutManager mLayoutManager;
	
	/** The is have slider. */
	private boolean isHaveSlider;

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

		mAdapterMain = new MainProductAdapter(getActivity(), mAdapterData, isHaveSlider);

		mRecyclerView.setAdapter(mAdapterMain);

		return mParentView;
	}

	/**
	 * Sets the have slider.
	 *
	 * @param isHaveSlider the new have slider
	 */
	public void setHaveSlider(boolean isHaveSlider) {
		this.isHaveSlider = isHaveSlider;
	}

}
