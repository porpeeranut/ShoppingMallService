package com.dmbteam.catalogapp.adapter;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dmbteam.catalogapp.MainActivity;
import com.dmbteam.catalogapp.R;
import com.dmbteam.catalogapp.cmn.Product;
import com.dmbteam.catalogapp.lib.Normal;
import com.dmbteam.catalogapp.listener.MainItemListener;
import com.dmbteam.catalogapp.util.ImageOptionsBuilder;
import com.dmbteam.catalogapp.util.ThemeManager;
import com.dmbteam.catalogapp.util.Utils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.viewpagerindicator.IconPagerAdapter;

/**
 * The Class MainPagerAdapter.
 */
public class MainPagerAdapter extends PagerAdapter implements IconPagerAdapter {

	/** The Context. */
	private Context mContext;

	/** The Inflater. */
	private LayoutInflater mInflater;

	/** The Adapter data. */
	private List<Product> mAdapterData;

	/** The Display image options. */
	private DisplayImageOptions mDisplayImageOptions;

	/** The Image loader. */
	private ImageLoader mImageLoader;

	/** The Main product adapter. */
	private MainProductAdapter mMainProductAdapter;

	/**
	 * Instantiates a new main pager adapter.
	 * 
	 * @param context
	 *            the context
	 * @param adapterData
	 *            the adapter data
	 */
	public MainPagerAdapter(Context context, List<Product> adapterData) {
		this.mContext = context;
		this.mInflater = LayoutInflater.from(this.mContext);

		this.mAdapterData = adapterData;

		this.mDisplayImageOptions = ImageOptionsBuilder
				.buildGeneralImageOptions(false, R.drawable.home_nexus9);
		this.mImageLoader = ImageLoader.getInstance();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.view.PagerAdapter#getCount()
	 */
	@Override
	public int getCount() {
		return mAdapterData.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.view.PagerAdapter#isViewFromObject(android.view.View,
	 * java.lang.Object)
	 */
	@Override
	public boolean isViewFromObject(View view, Object object) {
		return (view == object);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.view.PagerAdapter#instantiateItem(android.view.ViewGroup
	 * , int)
	 */
	@Override
	public Object instantiateItem(ViewGroup container, int position) {

		Product currentProduct = mAdapterData.get(position);

		View mainLayout = mInflater.inflate(R.layout.pager_item_main, null);

		ImageView mainImageView = (ImageView) mainLayout
				.findViewById(R.id.pager_item_imageview);

		if (currentProduct.isNetworkResource()) {
			mImageLoader.displayImage(currentProduct.getPhoto(mContext),
					mainImageView, mDisplayImageOptions);
		} else {
			//mainImageView.setImageDrawable(mContext.getResources().getDrawable(currentProduct.getDrawableId(mContext)));
            mainImageView.setImageBitmap(Normal.loadImage(mContext, currentProduct.getPhoto(mContext)));
		}

		TextView mainViewTitle = (TextView) mainLayout
				.findViewById(R.id.pager_item_top_description_title);
		// mainViewTitle.setText(currentProduct.getTitle());

		TextView mainViewSubTitle = (TextView) mainLayout
				.findViewById(R.id.pager_item_top_description_subtitle);
		// mainViewSubTitle.setText(mAdapterData.get(position).getDescription());

		TextView mainViewDiscount = (TextView) mainLayout
				.findViewById(R.id.pager_item_bottom_description_discount);

		TextView mainViewRealPrice = (TextView) mainLayout
				.findViewById(R.id.pager_item_bottom_description_realprice);

		TextView mainViewDiscountedPrice = (TextView) mainLayout
				.findViewById(R.id.pager_item_bottom_description_discountprice);

		Utils.constructMainCellView(mContext, currentProduct, null,
				mainImageView, mDisplayImageOptions, mainViewTitle,
				mainViewSubTitle, mainViewDiscount, mainViewDiscountedPrice,
				mainViewRealPrice);

		mainLayout.setOnClickListener(new MainItemListener(mMainProductAdapter
				.exportAdapterDataAsList(), currentProduct.getId(),
				(MainActivity) mContext));

		container.addView(mainLayout, 0);

		return mainLayout;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.view.PagerAdapter#destroyItem(android.view.ViewGroup,
	 * int, java.lang.Object)
	 */
	@Override
	public void destroyItem(ViewGroup collection, int position, Object view) {
		collection.removeView((LinearLayout) view);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.view.PagerAdapter#setPrimaryItem(android.view.ViewGroup
	 * , int, java.lang.Object)
	 */
	public void setPrimaryItem(ViewGroup container, int position, Object object) {
		super.setPrimaryItem(container, position, object);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.view.PagerAdapter#startUpdate(android.view.ViewGroup)
	 */
	public void startUpdate(ViewGroup arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.view.PagerAdapter#saveState()
	 */
	@Override
	public Parcelable saveState() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.view.PagerAdapter#restoreState(android.os.Parcelable,
	 * java.lang.ClassLoader)
	 */
	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.view.PagerAdapter#finishUpdate(android.view.ViewGroup)
	 */
	@Override
	public void finishUpdate(ViewGroup arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.viewpagerindicator.IconPagerAdapter#getIconResId(int)
	 */
	@Override
	public int getIconResId(int index) {
		
		int pagerIconAttr = ThemeManager.getIdForSpecificAttribute(mContext, R.attr.pager_indicator_icon);
		
		

		return pagerIconAttr;
	}

	/**
	 * Sets the main product adapter.
	 * 
	 * @param mainProductAdapter
	 *            the new main product adapter
	 */
	public void setMainProductAdapter(MainProductAdapter mainProductAdapter) {
		this.mMainProductAdapter = mainProductAdapter;
	}

}
