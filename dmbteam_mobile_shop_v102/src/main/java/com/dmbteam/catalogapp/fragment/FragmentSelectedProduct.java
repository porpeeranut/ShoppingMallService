package com.dmbteam.catalogapp.fragment;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dmbteam.catalogapp.MainActivity;
import com.dmbteam.catalogapp.R;
import com.dmbteam.catalogapp.adapter.ItemsPagerAdapter;
import com.dmbteam.catalogapp.cmn.Product;

/**
 * The Class FragmentSelectedProduct.
 */
public class FragmentSelectedProduct extends Fragment {

	/** The Constant TAG. */
	public static final String TAG = FragmentSelectedProduct.class
			.getSimpleName();
	
	/** The All products. */
	private List<Product> mAllProducts;
	
	/** The Selected position. */
	private int mSelectedPosition;

	/**
	 * New instance.
	 *
	 * @param allProducts the all products
	 * @param selectedPosition the selected position
	 * @return the fragment selected product
	 */
	public static FragmentSelectedProduct newInstance(
			List<Product> allProducts, int selectedPosition) {

		FragmentSelectedProduct fragment = new FragmentSelectedProduct();

		fragment.mAllProducts = allProducts;
		fragment.mSelectedPosition = selectedPosition;

		return fragment;
	}

	/** The Parent view. */
	private View mParentView;

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mParentView = inflater
				.inflate(R.layout.fragment_selected_product, null);

		ViewPager viewPager = (ViewPager) mParentView
				.findViewById(R.id.list_selected_produt_viewpager);

		final ItemsPagerAdapter itemsPagerAdapter = new ItemsPagerAdapter(
				getActivity(), mAllProducts);

		viewPager.setAdapter(itemsPagerAdapter);

		viewPager.setCurrentItem(mSelectedPosition);

		((MainActivity) getActivity()).setAbTitle(mAllProducts.get(
				mSelectedPosition).getTitle());

		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				((MainActivity) getActivity()).setAbTitle(mAllProducts
						.get(arg0).getTitle());
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

		return mParentView;
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onResume()
	 */
	@Override
	public void onResume() {
		super.onResume();

		((MainActivity) getActivity()).showCommonAb();
		((MainActivity) getActivity()).refreshCartCounter();
	}
}
