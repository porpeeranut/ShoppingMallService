package com.dmbteam.catalogapp.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dmbteam.catalogapp.MainActivity;
import com.dmbteam.catalogapp.R;
import com.dmbteam.catalogapp.cmn.Product;
import com.dmbteam.catalogapp.listener.MainItemListener;
import com.dmbteam.catalogapp.util.ImageOptionsBuilder;
import com.dmbteam.catalogapp.util.Utils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.viewpagerindicator.IconPageIndicator;

/**
 * The Class MainProductAdapter.
 */
public class MainProductAdapter extends
		RecyclerView.Adapter<MainProductAdapter.ViewHolder> {

	/**
	 * The Class ViewHolder.
	 */
	public static class ViewHolder extends RecyclerView.ViewHolder {

		/** The View pager container. */
		private LinearLayout mViewPagerContainer;
		
		/** The View pager. */
		private ViewPager mViewPager;
		
		/** The pager indicator. */
		private IconPageIndicator pagerIndicator;

		/** The container1 cell. */
		private LinearLayout container1Cell;
		
		/** The container1 cell image. */
		private ImageView container1CellImage;
		
		/** The container1 cell top title. */
		private TextView container1CellTopTitle;
		
		/** The container1 cell top sub title. */
		private TextView container1CellTopSubTitle;
		
		/** The container1 cell bottom title. */
		private TextView container1CellBottomTitle;
		
		/** The container1 cell bottom sub title. */
		private TextView container1CellBottomSubTitle;
		
		/** The container1 cell bottom sub title1. */
		private TextView container1CellBottomSubTitle1;

		/** The container2 cell. */
		private LinearLayout container2Cell;
		
		/** The container2_1 cell. */
		private LinearLayout container2_1Cell;
		
		/** The container2_2 cell. */
		private LinearLayout container2_2Cell;

		/** The container2_1 cell image. */
		private ImageView container2_1CellImage;
		
		/** The container2_1 cell top title. */
		private TextView container2_1CellTopTitle;
		
		/** The container2_1 cell bottom title. */
		private TextView container2_1CellBottomTitle;
		
		/** The container2_1 cell bottom sub title. */
		private TextView container2_1CellBottomSubTitle;
		
		/** The container2_1 cell bottom sub title1. */
		private TextView container2_1CellBottomSubTitle1;

		/** The container2_2 cell image. */
		private ImageView container2_2CellImage;
		
		/** The container2_2 cell top title. */
		private TextView container2_2CellTopTitle;
		
		/** The container2_2 cell bottom title. */
		private TextView container2_2CellBottomTitle;
		
		/** The container2_2 cell bottom sub title. */
		private TextView container2_2CellBottomSubTitle;
		
		/** The container2_2 cell bottom sub title1. */
		private TextView container2_2CellBottomSubTitle1;

		/** The container2_3 cell image. */
		private ImageView container2_3CellImage;
		
		/** The container2_3 cell top title. */
		private TextView container2_3CellTopTitle;
		
		/** The container2_3 cell bottom title. */
		private TextView container2_3CellBottomTitle;
		
		/** The container2_3 cell bottom sub title. */
		private TextView container2_3CellBottomSubTitle;
		
		/** The container2_3 cell bottom sub title1. */
		private TextView container2_3CellBottomSubTitle1;

		/** The container3 cell. */
		private LinearLayout container3Cell;
		
		/** The container3_1 cell. */
		private LinearLayout container3_1Cell;
		
		/** The container3_2 cell. */
		private LinearLayout container3_2Cell;
		
		/** The container3_3 cell. */
		private LinearLayout container3_3Cell;
		
		/** The container3_1 cell image. */
		private ImageView container3_1CellImage;
		
		/** The container3_1 cell top title. */
		private TextView container3_1CellTopTitle;
		
		/** The container3_1 cell bottom title. */
		private TextView container3_1CellBottomTitle;
		
		/** The container3_1 cell bottom sub title. */
		private TextView container3_1CellBottomSubTitle;
		
		/** The container3_1 cell bottom sub title1. */
		private TextView container3_1CellBottomSubTitle1;

		/** The container3_2 cell image. */
		private ImageView container3_2CellImage;
		
		/** The container3_2 cell top title. */
		private TextView container3_2CellTopTitle;
		
		/** The container3_2 cell bottom title. */
		private TextView container3_2CellBottomTitle;
		
		/** The container3_2 cell bottom sub title. */
		private TextView container3_2CellBottomSubTitle;
		
		/** The container3_2 cell bottom sub title1. */
		private TextView container3_2CellBottomSubTitle1;

		/**
		 * Instantiates a new view holder.
		 *
		 * @param itemView the item view
		 */
		public ViewHolder(View itemView) {
			super(itemView);
			mViewPagerContainer = (LinearLayout) itemView
					.findViewById(R.id.list_item_pager_container);
			mViewPager = (ViewPager) itemView
					.findViewById(R.id.list_item_main_viewpager);

			container1Cell = (LinearLayout) itemView
					.findViewById(R.id.list_item_container_1);
			container1CellImage = (ImageView) itemView
					.findViewById(R.id.list_item_container_1_image);

			container1CellTopTitle = (TextView) itemView
					.findViewById(R.id.list_item_container_1_top_description_title);
			container1CellTopSubTitle = (TextView) itemView
					.findViewById(R.id.list_item_container_1_top_description_subtitle);

			container1CellBottomTitle = (TextView) itemView
					.findViewById(R.id.list_item_container_1_bottom_description_title);
			container1CellBottomSubTitle = (TextView) itemView
					.findViewById(R.id.list_item_container_1_bottom_description_subtitle_1);
			container1CellBottomSubTitle1 = (TextView) itemView
					.findViewById(R.id.list_item_container_1_bottom_description_subtitle_2);

			pagerIndicator = (IconPageIndicator) itemView
					.findViewById(R.id.indicator);

			// Cell 2 items initialization
			container2Cell = (LinearLayout) itemView
					.findViewById(R.id.list_item_container_3);
			container2_1Cell = (LinearLayout) itemView
					.findViewById(R.id.list_item_container_3_1);
			container2_2Cell = (LinearLayout) itemView
					.findViewById(R.id.list_item_container_3_2);

			container2_1CellImage = (ImageView) itemView
					.findViewById(R.id.list_item_container_2_1_image);
			container2_2CellImage = (ImageView) itemView
					.findViewById(R.id.list_item_container_2_2_image);
			container2_3CellImage = (ImageView) itemView
					.findViewById(R.id.list_item_container_2_3_image);

			container2_1CellTopTitle = (TextView) itemView
					.findViewById(R.id.list_item_container_2_1_title);
			container2_2CellTopTitle = (TextView) itemView
					.findViewById(R.id.list_item_container_2_2_title);
			container2_3CellTopTitle = (TextView) itemView
					.findViewById(R.id.list_item_container_2_3_title);

			container2_1CellBottomTitle = (TextView) itemView
					.findViewById(R.id.list_item_container_2_1_discount);
			container2_2CellBottomTitle = (TextView) itemView
					.findViewById(R.id.list_item_container_2_2_discount);
			container2_3CellBottomTitle = (TextView) itemView
					.findViewById(R.id.list_item_container_2_3_discount);

			container2_1CellBottomSubTitle = (TextView) itemView
					.findViewById(R.id.list_item_container_2_1_discounted_price);
			container2_2CellBottomSubTitle = (TextView) itemView
					.findViewById(R.id.list_item_container_2_2_discounted_price);
			container2_3CellBottomSubTitle = (TextView) itemView
					.findViewById(R.id.list_item_container_2_3_discounted_price);

			container2_1CellBottomSubTitle1 = (TextView) itemView
					.findViewById(R.id.list_item_container_2_1_real_price);
			container2_2CellBottomSubTitle1 = (TextView) itemView
					.findViewById(R.id.list_item_container_2_2_real_price);
			container2_3CellBottomSubTitle1 = (TextView) itemView
					.findViewById(R.id.list_item_container_2_3_real_price);

			// Cell 3 items initialization
			container3Cell = (LinearLayout) itemView
					.findViewById(R.id.list_item_container_2);
			container3_1Cell = (LinearLayout) itemView
					.findViewById(R.id.list_item_container_2_1);
			container3_2Cell = (LinearLayout) itemView
					.findViewById(R.id.list_item_container_2_2);
			container3_3Cell = (LinearLayout) itemView
					.findViewById(R.id.list_item_container_2_3);

			container3_1CellImage = (ImageView) itemView
					.findViewById(R.id.list_item_container_3_1_image);
			container3_1CellTopTitle = (TextView) itemView
					.findViewById(R.id.list_item_container_3_1_title);
			container3_1CellBottomTitle = (TextView) itemView
					.findViewById(R.id.list_item_container_3_1_discount);
			container3_1CellBottomSubTitle = (TextView) itemView
					.findViewById(R.id.list_item_container_3_1_discounted_price);
			container3_1CellBottomSubTitle1 = (TextView) itemView
					.findViewById(R.id.list_item_container_3_1_real_price);

			container3_2CellImage = (ImageView) itemView
					.findViewById(R.id.list_item_container_3_2_image);
			container3_2CellTopTitle = (TextView) itemView
					.findViewById(R.id.list_item_container_3_2_title);
			container3_2CellBottomTitle = (TextView) itemView
					.findViewById(R.id.list_item_container_3_2_discount);
			container3_2CellBottomSubTitle = (TextView) itemView
					.findViewById(R.id.list_item_container_3_2_discounted_price);
			container3_2CellBottomSubTitle1 = (TextView) itemView
					.findViewById(R.id.list_item_container_3_2_real_price);
		}
	}

	/** The Context. */
	private Context mContext;
	
	/** The Inflater. */
	private LayoutInflater mInflater;
	
	/** The Adapter data. */
	private List<List<Product>> mAdapterData;
	
	/** The Display image options. */
	private DisplayImageOptions mDisplayImageOptions;
	
	/** The is have slider. */
	private boolean isHaveSlider;

	/**
	 * Instantiates a new main product adapter.
	 *
	 * @param context the context
	 * @param adapterData the adapter data
	 * @param isHaveSlider the is have slider
	 */
	public MainProductAdapter(Context context, List<List<Product>> adapterData,
			boolean isHaveSlider) {
		this.mContext = context;
		this.mInflater = LayoutInflater.from(this.mContext);
		this.mAdapterData = adapterData;
		this.isHaveSlider = isHaveSlider;

		this.mDisplayImageOptions = ImageOptionsBuilder
				.buildGeneralImageOptions(false, R.drawable.home_nexus9);

	}

	/* (non-Javadoc)
	 * @see android.support.v7.widget.RecyclerView.Adapter#getItemCount()
	 */
	@Override
	public int getItemCount() {

		return mAdapterData.size();
	}

	/* (non-Javadoc)
	 * @see android.support.v7.widget.RecyclerView.Adapter#onCreateViewHolder(android.view.ViewGroup, int)
	 */
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View convertView = mInflater.inflate(R.layout.list_item_main, parent,
				false);
		ViewHolder holder = new ViewHolder(convertView);

		return holder;
	}

	/* (non-Javadoc)
	 * @see android.support.v7.widget.RecyclerView.Adapter#onBindViewHolder(android.support.v7.widget.RecyclerView.ViewHolder, int)
	 */
	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {

		holder.mViewPagerContainer.setVisibility(View.GONE);
		holder.container1Cell.setVisibility(View.GONE);
		holder.container2Cell.setVisibility(View.GONE);
		holder.container3Cell.setVisibility(View.GONE);

		int sizeOfRow = mAdapterData.get(position).size();

		if (isHaveSlider && position == 0) {
			initViewPager(holder, position);

			return;
		}

		if (sizeOfRow == 1) {
			init1ItemCell(holder, position);
		} else if (sizeOfRow == 3) {
			init3ItemCell(holder, position);
		} else if (sizeOfRow == 2) {
			init2ItemCell(holder, position);
		}

	}

	/**
	 * Inits the view pager.
	 *
	 * @param holder the holder
	 * @param position the position
	 */
	private void initViewPager(final ViewHolder holder, int position) {
		holder.mViewPagerContainer.setVisibility(View.VISIBLE);

		MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(mContext,
				mAdapterData.get(position));
		mainPagerAdapter.setMainProductAdapter(this);


		holder.mViewPager.setVisibility(View.VISIBLE);
		holder.mViewPager.setAdapter(mainPagerAdapter);

		holder.pagerIndicator.setViewPager(holder.mViewPager);

		holder.mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				holder.pagerIndicator.onPageSelected(arg0);

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	/**
	 * Init1 item cell.
	 *
	 * @param holder the holder
	 * @param position the position
	 */
	private void init1ItemCell(ViewHolder holder, int position) {

		holder.container1Cell.setVisibility(View.VISIBLE);

		Utils.constructMainCellView(mContext,
				mAdapterData.get(position).get(0), null,
				holder.container1CellImage, mDisplayImageOptions,
				holder.container1CellTopTitle,
				holder.container1CellTopSubTitle,
				holder.container1CellBottomTitle,
				holder.container1CellBottomSubTitle,
				holder.container1CellBottomSubTitle1);

		holder.container1Cell.setOnClickListener(new MainItemListener(
				exportAdapterDataAsList(), mAdapterData.get(position).get(0)
						.getId(), (MainActivity) mContext));
	}

	/**
	 * Init2 item cell.
	 *
	 * @param holder the holder
	 * @param position the position
	 */
	private void init2ItemCell(ViewHolder holder, int position) {
		holder.container2Cell.setVisibility(View.VISIBLE);

		Utils.constructMainCellView(mContext,
				mAdapterData.get(position).get(0), null,
				holder.container3_1CellImage, mDisplayImageOptions,
				holder.container3_1CellTopTitle, null,
				holder.container3_1CellBottomTitle,
				holder.container3_1CellBottomSubTitle,
				holder.container3_1CellBottomSubTitle1);

		holder.container2_1Cell.setOnClickListener(new MainItemListener(
				exportAdapterDataAsList(), mAdapterData.get(position).get(0)
						.getId(), (MainActivity) mContext));

		Utils.constructMainCellView(mContext,
				mAdapterData.get(position).get(1), null,
				holder.container3_2CellImage, mDisplayImageOptions,
				holder.container3_2CellTopTitle, null,
				holder.container3_2CellBottomTitle,
				holder.container3_2CellBottomSubTitle,
				holder.container3_2CellBottomSubTitle1);

		holder.container2_2Cell.setOnClickListener(new MainItemListener(
				exportAdapterDataAsList(), mAdapterData.get(position).get(1)
						.getId(), (MainActivity) mContext));
	}

	/**
	 * Init3 item cell.
	 *
	 * @param holder the holder
	 * @param position the position
	 */
	private void init3ItemCell(ViewHolder holder, int position) {
		holder.container3Cell.setVisibility(View.VISIBLE);

		Utils.constructMainCellView(mContext,
				mAdapterData.get(position).get(0), null,
				holder.container2_1CellImage, mDisplayImageOptions,
				holder.container2_1CellTopTitle, null,
				holder.container2_1CellBottomTitle,
				holder.container2_1CellBottomSubTitle,
				holder.container2_1CellBottomSubTitle1);
		holder.container3_1Cell.setOnClickListener(new MainItemListener(
				exportAdapterDataAsList(), mAdapterData.get(position).get(0)
						.getId(), (MainActivity) mContext));

		Utils.constructMainCellView(mContext,
				mAdapterData.get(position).get(1), null,
				holder.container2_2CellImage, mDisplayImageOptions,
				holder.container2_2CellTopTitle, null,
				holder.container2_2CellBottomTitle,
				holder.container2_2CellBottomSubTitle,
				holder.container2_2CellBottomSubTitle1);
		holder.container3_2Cell.setOnClickListener(new MainItemListener(
				exportAdapterDataAsList(), mAdapterData.get(position).get(1)
						.getId(), (MainActivity) mContext));

		Utils.constructMainCellView(mContext,
				mAdapterData.get(position).get(2), null,
				holder.container2_3CellImage, mDisplayImageOptions,
				holder.container2_3CellTopTitle, null,
				holder.container2_3CellBottomTitle,
				holder.container2_3CellBottomSubTitle,
				holder.container2_3CellBottomSubTitle1);
		holder.container3_3Cell.setOnClickListener(new MainItemListener(
				exportAdapterDataAsList(), mAdapterData.get(position).get(2)
						.getId(), (MainActivity) mContext));

	}

	/**
	 * Export adapter data as list.
	 *
	 * @return the list
	 */
	public List<Product> exportAdapterDataAsList() {

		List<Product> adapterDataAsList = new ArrayList<Product>();

		for (int i = 0; i < mAdapterData.size(); i++) {

			List<Product> currentData = mAdapterData.get(i);

			for (int j = 0; j < currentData.size(); j++) {
				adapterDataAsList.add(currentData.get(j));
			}
		}

		return adapterDataAsList;
	}
}
