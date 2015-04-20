package com.dmbteam.catalogapp.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

import com.dmbteam.catalogapp.BaiSed;
import com.dmbteam.catalogapp.LoginActivity;
import com.dmbteam.catalogapp.MainActivity;
import com.dmbteam.catalogapp.R;

import com.dmbteam.catalogapp.Setting;
import com.dmbteam.catalogapp.cmn.Category;
import com.dmbteam.catalogapp.fraglogin;
import com.dmbteam.catalogapp.settings.AppSettings;
import com.dmbteam.catalogapp.topup;
import com.dmbteam.catalogapp.util.ThemeManager;
import com.dmbteam.catalogapp.util.Utils;
import com.dmbteam.catalogapp.widget.RippleView;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

/**
 * The Class SlidingAdapter.
 */
public class SlidingAdapter extends ArrayAdapter<Category> {

	/**
	 * Instantiates a new sliding adapter.
	 *
	 * @param context the context
	 * @param resource the resource
	 * @param objects the objects
	 */
	public SlidingAdapter(Context context, int resource, List<Category> objects) {
		super(context, 0, objects);
	}

	/* (non-Javadoc)
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		SlidingAdapterHolder holder;

		if (convertView == null) {
			holder = new SlidingAdapterHolder();

			convertView = getInflater(getContext()).inflate(
					R.layout.list_item_sliding, null);

			holder.mainLinearLayout = (RelativeLayout) convertView
					.findViewById(R.id.list_item_sliding_main);

			holder.subLinearLayout = (RelativeLayout) convertView
					.findViewById(R.id.list_item_sliding_sub);

			int rippleColour = ThemeManager.getIdForSpecificAttribute(getContext(), R.attr.first_active_color);

			
			holder.mainTextViewTitle = (RippleView) convertView
					.findViewById(R.id.list_item_sliding_main_text);
			holder.mainTextViewTitle.setRippleColor(getContext().getResources()
					.getColor(rippleColour), 0.7f);
			holder.mainTextViewTitle.setHover(true);

			holder.subTextViewTitle = (RippleView) convertView
					.findViewById(R.id.list_item_sliding_sub_text);
			holder.subTextViewTitle.setRippleColor(getContext().getResources()
					.getColor(rippleColour), 0.7f);
			holder.subTextViewTitle.setHover(true);

			holder.mainLayoutPlusMinus = (ImageView) convertView
					.findViewById(R.id.list_item_sliding_main_plus_minus);

			holder.subLayoutPlusMinus = (ImageView) convertView
					.findViewById(R.id.list_item_sliding_sub_plus_minus);

			holder.delimiter = convertView
					.findViewById(R.id.list_item_sliding_delimiter);

			convertView.setTag(holder);
		} else {
			holder = (SlidingAdapterHolder) convertView.getTag();
		}

		Category currentCategory = getItem(position);

		if (currentCategory.isMain()) {
			holder.mainLinearLayout.setVisibility(View.VISIBLE);
			holder.mainTextViewTitle.setText(currentCategory.getTitle());

			holder.mainLayoutPlusMinus.setVisibility(currentCategory
					.getSubCategoriesIds().size() == 0 ? View.GONE
					: View.VISIBLE);

			holder.subLinearLayout.setVisibility(View.GONE);

			setCorrectPlusMinusImage(currentCategory,
					holder.mainLayoutPlusMinus);
		} else {

			int treeIndex = currentCategory.getTreeIndex();
			holder.subLinearLayout.setPadding(treeIndex, 0, 0, 0);

			Log.i("Sliding_Padding", "" + treeIndex);

			holder.subLinearLayout.setVisibility(View.VISIBLE);
			holder.subTextViewTitle.setText(currentCategory.getTitle());

			holder.mainLinearLayout.setVisibility(View.GONE);

			holder.subLayoutPlusMinus.setVisibility(currentCategory
					.getSubCategoriesIds().size() == 0 ? View.GONE
					: View.VISIBLE);

			setCorrectPlusMinusImage(currentCategory, holder.subLayoutPlusMinus);
		}

		int paddingMain = Utils.dipsToPixels(getContext(), 20);

		int currentSubPadding = holder.subLinearLayout.getPaddingLeft();

		// Set delimiter margin
		LinearLayout.LayoutParams lp = (LayoutParams) holder.delimiter
				.getLayoutParams();
		if (currentCategory.isMain()) {
			lp.setMargins(0, 0, 0, 0);
			holder.delimiter.setLayoutParams(lp);

		} else {
			lp.setMargins(currentSubPadding, 0, 0, 0);
			holder.delimiter.setLayoutParams(lp);
		}

		holder.mainTextViewTitle
				.setOnClickListener(new OnCategoryStringClickedListener(
						currentCategory, position, paddingMain
								+ currentSubPadding));

		holder.subTextViewTitle
				.setOnClickListener(new OnCategoryStringClickedListener(
						currentCategory, position, paddingMain
								+ currentSubPadding));

		holder.subLayoutPlusMinus
				.setOnClickListener(new SlidingMenuOpenHierarchyListener(
						currentCategory, position, paddingMain
								+ currentSubPadding));

		holder.mainLayoutPlusMinus
				.setOnClickListener(new SlidingMenuOpenHierarchyListener(
						currentCategory, position, paddingMain));

		return convertView;
	}

	/**
	 * Gets the inflater.
	 *
	 * @param context the context
	 * @return the inflater
	 */
	private LayoutInflater getInflater(Context context) {
		return LayoutInflater.from(context);
	}

	/**
	 * Sets the correct plus minus image.
	 *
	 * @param category the category
	 * @param plusMinus the plus minus
	 */
	private void setCorrectPlusMinusImage(Category category, ImageView plusMinus) {
		if (!category.isOpened()) {

			if (category.isMain()) {
				plusMinus.setImageDrawable(getContext().getResources()
						.getDrawable(R.drawable.ic_menu_plus_level1));
			} else {
				plusMinus.setImageDrawable(getContext().getResources()
						.getDrawable(R.drawable.ic_menu_plus_level2));
			}

		} else {

			if (category.isMain()) {
				plusMinus.setImageDrawable(getContext().getResources()
						.getDrawable(R.drawable.ic_menu_minus_level1));
			} else {
				plusMinus.setImageDrawable(getContext().getResources()
						.getDrawable(R.drawable.ic_menu_minus_level2));
			}
		}
	}

	/**
	 * The Class SlidingAdapterHolder.
	 */
	public static class SlidingAdapterHolder {
		
		/** The main linear layout. */
		RelativeLayout mainLinearLayout;
		
		/** The main text view title. */
		RippleView mainTextViewTitle;
		
		/** The main layout plus minus. */
		ImageView mainLayoutPlusMinus;

		/** The sub linear layout. */
		RelativeLayout subLinearLayout;
		
		/** The sub text view title. */
		RippleView subTextViewTitle;
		
		/** The sub layout plus minus. */
		ImageView subLayoutPlusMinus;

		/** The delimiter. */
		View delimiter;
	}

	/**
	 * The listener interface for receiving slidingMenuOpenHierarchy events.
	 * The class that is interested in processing a slidingMenuOpenHierarchy
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addSlidingMenuOpenHierarchyListener<code> method. When
	 * the slidingMenuOpenHierarchy event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see SlidingMenuOpenHierarchyEvent
	 */
	private class SlidingMenuOpenHierarchyListener implements
			View.OnClickListener {

		/** The category. */
		private Category category;
		
		/** The position. */
		private int position;
		
		/** The padding. */
		private int padding;

		/**
		 * Instantiates a new sliding menu open hierarchy listener.
		 *
		 * @param category the category
		 * @param position the position
		 * @param paddingMain the padding main
		 */
		public SlidingMenuOpenHierarchyListener(Category category,
				int position, int paddingMain) {
			super();
			this.category = category;
			this.position = position;
			this.padding = paddingMain;
		}

		/* (non-Javadoc)
		 * @see android.view.View.OnClickListener#onClick(android.view.View)
		 */
		@Override
		public void onClick(View v) {
			final ImageView plusMinus = ((ImageView) v);

			Animation a = AnimationUtils.loadAnimation(getContext(),
					R.anim.sliding_rotate);
			a.setDuration(500);
			plusMinus.startAnimation(a);

			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					setCorrectPlusMinusImage(category, plusMinus);

					if (category.isOpened()) {
						((MainActivity) getContext()).closeCategory(category,
								position);
					} else {
						((MainActivity) getContext()).openCategoryInSlider(
								category, position, padding);
					}

					category.setOpened(!category.isOpened());
				}
			}, 500);

		}
	}

	/**
	 * The listener interface for receiving onCategoryStringClicked events.
	 * The class that is interested in processing a onCategoryStringClicked
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addOnCategoryStringClickedListener<code> method. When
	 * the onCategoryStringClicked event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see OnCategoryStringClickedEvent
	 */
	private class OnCategoryStringClickedListener implements OnClickListener {
		
		/** The category. */
		private Category category;
		
		/** The position. */
		private int position;
		
		/** The padding. */
		private int padding;

		/**
		 * Instantiates a new on category string clicked listener.
		 *
		 * @param category the category
		 * @param position the position
		 * @param paddingMain the padding main
		 */
		public OnCategoryStringClickedListener(Category category, int position,
				int paddingMain) {
			super();
			this.category = category;
			this.position = position;
			this.padding = paddingMain;
		}

		/* (non-Javadoc)
		 * @see android.view.View.OnClickListener#onClick(android.view.View)
		 */
		@Override
		public void onClick(View v) {

            final Context context = v.getContext();

			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
                    ((MainActivity) getContext()).loadProductForSelectedCategory(category, position, padding);

                    /*if (category.getId()==300)
                    {
                        final Intent intent = new Intent(context, BaiSed.class);
                        getContext().startActivity(intent);
                    }
					else if (category.getId()==400)
                    {
                       final Intent intent = new Intent(context, topup.class);
                        getContext().startActivity(intent);
                    }
                    else if (category.getId()==500)
                    {
                        final Intent intent = new Intent(context, Setting.class);
                        getContext().startActivity(intent);
                    }
                    else if (category.getId()==600)
                    {
                        final Intent intent = new Intent(context, LoginActivity.class);
                        getContext().startActivity(intent);
                    } else
                        ((MainActivity) getContext()).loadProductForSelectedCategory(category, position,
                                padding);*/
				}
			}, 500);
		}

	}

}
