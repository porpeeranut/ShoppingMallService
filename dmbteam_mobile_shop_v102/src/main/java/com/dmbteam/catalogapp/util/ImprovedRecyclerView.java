package com.dmbteam.catalogapp.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * The Class ImprovedRecyclerView.
 */
public class ImprovedRecyclerView extends RecyclerView {
	
	/**
	 * Instantiates a new improved recycler view.
	 *
	 * @param context the context
	 */
	public ImprovedRecyclerView(Context context) {
		super(context);
	}

	/**
	 * Instantiates a new improved recycler view.
	 *
	 * @param context the context
	 * @param attrs the attrs
	 */
	public ImprovedRecyclerView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * Instantiates a new improved recycler view.
	 *
	 * @param context the context
	 * @param attrs the attrs
	 * @param defStyle the def style
	 */
	public ImprovedRecyclerView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	/* (non-Javadoc)
	 * @see android.view.View#canScrollVertically(int)
	 */
	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@Override
	public boolean canScrollVertically(int direction) {
		// check if scrolling up
		if (direction < 1) {
			boolean original = super.canScrollVertically(direction);
			return !original && getChildAt(0) != null
					&& getChildAt(0).getTop() < 0 || original;
		}
		return super.canScrollVertically(direction);

	}

}
