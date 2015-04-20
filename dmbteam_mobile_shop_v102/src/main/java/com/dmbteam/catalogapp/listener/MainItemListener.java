package com.dmbteam.catalogapp.listener;

import java.util.List;

import android.view.View;
import android.view.View.OnClickListener;

import com.dmbteam.catalogapp.MainActivity;
import com.dmbteam.catalogapp.cmn.Product;

/**
 * The listener interface for receiving mainItem events.
 * The class that is interested in processing a mainItem
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addMainItemListener<code> method. When
 * the mainItem event occurs, that object's appropriate
 * method is invoked.
 *
 * @see MainItemEvent
 */
public class MainItemListener implements OnClickListener {

	/** The All products. */
	List<Product> mAllProducts;
	
	/** The Selected position. */
	int mSelectedPosition;

	/** The Main activity. */
	MainActivity mMainActivity;

	/**
	 * Instantiates a new main item listener.
	 *
	 * @param allProducts the all products
	 * @param selectedProductId the selected product id
	 * @param mainActivity the main activity
	 */
	public MainItemListener(List<Product> allProducts, int selectedProductId,
			MainActivity mainActivity) {
		super();
		mAllProducts = allProducts;

		for (int i = 0; i < allProducts.size(); i++) {
			if (allProducts.get(i).getId() == selectedProductId) {

				mSelectedPosition = i;
			}
		}

		mMainActivity = mainActivity;
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {

		mMainActivity.showSelectedProductFragment(true, false, mAllProducts,
				mSelectedPosition);
	}

}
