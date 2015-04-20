package com.dmbteam.catalogapp.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dmbteam.catalogapp.MainActivity;
import com.dmbteam.catalogapp.R;
import com.dmbteam.catalogapp.animation.SupportAnimator;
import com.dmbteam.catalogapp.arcanimator.ArcAnimator;
import com.dmbteam.catalogapp.arcanimator.Side;
import com.dmbteam.catalogapp.cart.CartManager;
import com.dmbteam.catalogapp.cmn.Product;
import com.dmbteam.catalogapp.settings.AppSettings;
import com.dmbteam.catalogapp.util.ImageOptionsBuilder;
import com.dmbteam.catalogapp.util.Utils;
import com.dmbteam.catalogapp.xmlparse.CatalogXmlParser;
import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ObservableScrollView;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.viewpagerindicator.IconPagerAdapter;

/**
 * The Class ItemsPagerAdapter.
 */
public class ItemsPagerAdapter extends PagerAdapter implements IconPagerAdapter {

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

	/** The Floating action button. */
	private FloatingActionButton mFloatingActionButton;

	/**
	 * Instantiates a new items pager adapter.
	 * 
	 * @param context
	 *            the context
	 * @param adapterData
	 *            the adapter data
	 */
	public ItemsPagerAdapter(Context context, List<Product> adapterData) {
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

		View mainLayout = mInflater.inflate(R.layout.pager_item_product, null);

		ImageView mainImageView = (ImageView) mainLayout
				.findViewById(R.id.pager_item_product_image);

		if (currentProduct.isNetworkResource()) {
			mImageLoader.displayImage(currentProduct.getPhoto(mContext),
					mainImageView, mDisplayImageOptions);
		} else {
			mainImageView.setImageDrawable(mContext.getResources().getDrawable(
					currentProduct.getDrawableId(mContext)));
		}

		TextView categoryView = (TextView) mainLayout
				.findViewById(R.id.pager_item_product_category);
		categoryView.setText(CatalogXmlParser.getInstance()
				.findCategoryById(currentProduct.getCategory()).getTitle());

		TextView pageInfo = (TextView) mainLayout
				.findViewById(R.id.pager_item_product_page_info);
		pageInfo.setText(String.format(
				mContext.getString(R.string.selected_page_number), ++position,
				mAdapterData.size()));

		TextView discountInfo = (TextView) mainLayout
				.findViewById(R.id.pager_item_product_discount);
		if (currentProduct.getDiscount() > 0) {
			discountInfo.setVisibility(View.VISIBLE);

			String discountToDisplay = mContext.getResources().getString(
					R.string.discount_format);
			discountToDisplay = String.format(discountToDisplay, ""
					+ (int) currentProduct.getDiscount() + "%");
		} else {
			discountInfo.setVisibility(View.GONE);
		}

		TextView priceNoDiscount = (TextView) mainLayout
				.findViewById(R.id.pager_item_product_oval_price_no_discount);

		TextView priceUnderlined = (TextView) mainLayout
				.findViewById(R.id.pager_item_product_oval_price);
		TextView priceWithDiscount = (TextView) mainLayout
				.findViewById(R.id.pager_item_product_oval_price_discount);

		String noDiscountPrice = Utils.mFormatter.format(currentProduct
				.getPrice()) + AppSettings.CURRENCY;
		if (currentProduct.getDiscount() > 0) {
			priceNoDiscount.setVisibility(View.GONE);

			priceUnderlined.setVisibility(View.VISIBLE);
			priceUnderlined.setText("" + noDiscountPrice);
			priceUnderlined.setPaintFlags(priceUnderlined.getPaintFlags()
					| Paint.STRIKE_THRU_TEXT_FLAG);

			double discountedPriceDouble = currentProduct.getPrice()
					* (1 - currentProduct.getDiscount() / 100);
			String discountedPrice = Utils.mFormatter
					.format(discountedPriceDouble) + AppSettings.CURRENCY;
			priceWithDiscount.setText("" + discountedPrice);
			priceWithDiscount.setVisibility(View.VISIBLE);

		} else {
			priceUnderlined.setVisibility(View.GONE);
			priceWithDiscount.setVisibility(View.GONE);

			priceNoDiscount.setVisibility(View.VISIBLE);

			priceNoDiscount.setText("" + noDiscountPrice);
		}

		TextView titlePagerItem = (TextView) mainLayout
				.findViewById(R.id.pager_item_product_title);

		titlePagerItem.setText(currentProduct.getTitle());

		TextView color = (TextView) mainLayout
				.findViewById(R.id.pager_item_product_color_value);
		color.setText(currentProduct.getColor());

		TextView condition = (TextView) mainLayout
				.findViewById(R.id.pager_item_product_condition_value);
		condition.setText(currentProduct.getCondition());

		TextView descriptionTextView = (TextView) mainLayout
				.findViewById(R.id.pager_item_product_description);
		descriptionTextView.setText(currentProduct.getDescription());

		ObservableScrollView observableScrollView = (ObservableScrollView) mainLayout
				.findViewById(R.id.pager_item_ObservableScrollView);

		mFloatingActionButton = (FloatingActionButton) mainLayout
				.findViewById(R.id.fab);
		mFloatingActionButton.attachToScrollView(observableScrollView);

		mFloatingActionButton
				.setOnClickListener(new FloatingActionButtonListener(
						currentProduct));

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
		collection.removeView((FrameLayout) view);
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

		return R.drawable.pager_icon_selector;
	}

	final static AccelerateInterpolator ACCELERATE = new AccelerateInterpolator();
	final static AccelerateDecelerateInterpolator ACCELERATE_DECELERATE = new AccelerateDecelerateInterpolator();
	final static DecelerateInterpolator DECELERATE = new DecelerateInterpolator();

	/**
	 * The listener interface for receiving floatingActionButton events. The
	 * class that is interested in processing a floatingActionButton event
	 * implements this interface, and the object created with that class is
	 * registered with a component using the component's
	 * <code>addFloatingActionButtonListener<code> method. When
	 * the floatingActionButton event occurs, that object's appropriate
	 * method is invoked.
	 * 
	 * @see FloatingActionButtonEvent
	 */
	private class FloatingActionButtonListener implements View.OnClickListener {

		/** The selected product. */
		Product selectedProduct;
		private float startRedX;
		private float startRedY;

		/**
		 * Instantiates a new floating action button listener.
		 * 
		 * @param selectedProduct
		 *            the selected product
		 */
		public FloatingActionButtonListener(Product selectedProduct) {
			super();
			this.selectedProduct = selectedProduct;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.view.View.OnClickListener#onClick(android.view.View)
		 */
		@Override
		public void onClick(final View v) {

			if (CartManager.getInstance().checkExistance(selectedProduct)) {
				CartManager.getInstance().addProductToItems(selectedProduct);

				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
					((MainActivity) mContext).refreshCartCounter();

					return;
				}

				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						((MainActivity) mContext).refreshCartCounter();
					}
				}, 700);

				animate(v);

				// ---------------------
			} else {
				Toast.makeText(mContext,
						mContext.getString(R.string.existing_product_in_cart),
						Toast.LENGTH_LONG).show();
			}

		}

		void animate(final View v) {
			startRedX = ViewHelper.getX(v);
			startRedY = ViewHelper.getY(v);

			int endBlueX = v.getRight() - Utils.dipsToPixels(mContext, 20);
			int endBlueY = (int) (Utils.dipsToPixels(mContext, 10));
			ArcAnimator arcAnimator = ArcAnimator.createArcAnimator(v,
					endBlueX, endBlueY, 90, Side.LEFT).setDuration(600);
			arcAnimator.addListener(new SimpleListener() {
				@Override
				public void onAnimationEnd(Animator animation) {
					// appearBluePair();
				}
			});
			arcAnimator.start();

			int cx = v.getWidth() / 2;
			int cy = v.getHeight() / 2;

			SupportAnimator animator = com.dmbteam.catalogapp.animation.ViewAnimationUtils
					.createCircularReveal(v, cx, cy, v.getWidth() / 2, 0);
			animator.addListener(new SimpleListener() {
				@Override
				public void onAnimationEnd() {
					v.setVisibility(View.INVISIBLE);
				}
			});
			animator.setInterpolator(ACCELERATE);
			animator.setDuration(700);
			animator.start();
		}
	}

	private static class SimpleListener implements
			SupportAnimator.AnimatorListener,
			com.nineoldandroids.animation.ObjectAnimator.AnimatorListener {

		@Override
		public void onAnimationCancel(Animator arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onAnimationEnd(Animator arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onAnimationRepeat(Animator arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onAnimationStart(Animator arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onAnimationStart() {
			// TODO Auto-generated method stub

		}

		@Override
		public void onAnimationEnd() {
			// TODO Auto-generated method stub

		}

		@Override
		public void onAnimationCancel() {
			// TODO Auto-generated method stub

		}

		@Override
		public void onAnimationRepeat() {
			// TODO Auto-generated method stub

		}

	}

	public static float centerX(View view) {
		return ViewHelper.getX(view) + view.getWidth() / 2;
	}

	public static float centerY(View view) {
		return ViewHelper.getY(view) + view.getHeight() / 2;
	}

}
