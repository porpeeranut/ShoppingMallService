package com.dmbteam.catalogapp.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dmbteam.catalogapp.R;
import com.dmbteam.catalogapp.cart.CartItem;
import com.dmbteam.catalogapp.cart.CartManager;
import com.dmbteam.catalogapp.cmn.Product;
import com.dmbteam.catalogapp.fragment.FragmentCart;
import com.dmbteam.catalogapp.lib.Normal;
import com.dmbteam.catalogapp.settings.AppSettings;
import com.dmbteam.catalogapp.util.ImageOptionsBuilder;
import com.dmbteam.catalogapp.util.Utils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * The Class CartAdapter.
 */
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

	/**
	 * The Class ViewHolder.
	 */
	public static class ViewHolder extends RecyclerView.ViewHolder {

		/** The image view picture. */
		ImageView imageViewPicture;
		
		/** The title. */
		TextView title;
		
		/** The price discount. */
		TextView priceDiscount;
		
		/** The price real underlined. */
		TextView priceRealUnderlined;
		
		/** The image view plus. */
		ImageView imageViewPlus;
		
		/** The image view minus. */
		ImageView imageViewMinus;
		
		/** The quantity. */
		TextView quantity;

		/**
		 * Instantiates a new view holder.
		 *
		 * @param itemView the item view
		 */
		public ViewHolder(View itemView) {
			super(itemView);

			imageViewPicture = (ImageView) itemView
					.findViewById(R.id.list_item_cart_image);
			title = (TextView) itemView.findViewById(R.id.list_item_cart_title);
			priceDiscount = (TextView) itemView
					.findViewById(R.id.list_item_cart_price_discount);
			priceRealUnderlined = (TextView) itemView
					.findViewById(R.id.list_item_cart_price_price_underlined);
			imageViewPlus = (ImageView) itemView
					.findViewById(R.id.list_item_cart_image_plus);
			imageViewMinus = (ImageView) itemView
					.findViewById(R.id.list_item_cart_image_minus);
			quantity = (TextView) itemView
					.findViewById(R.id.list_item_cart_qty_value);
		}
	}

	/** The Context. */
	private Context mContext;
	
	/** The Inflater. */
	private LayoutInflater mInflater;
	
	/** The Adapter data. */
	private List<CartItem> mAdapterData;
	
	/** The Display image options. */
	private DisplayImageOptions mDisplayImageOptions;
	
	/** The Fragment cart. */
	private FragmentCart mFragmentCart;

	/**
	 * Instantiates a new cart adapter.
	 *
	 * @param context the context
	 * @param fragmentCart the fragment cart
	 */
	public CartAdapter(Context context, FragmentCart fragmentCart) {
		this.mContext = context;
		this.mInflater = LayoutInflater.from(this.mContext);
		this.mFragmentCart = fragmentCart;
		this.mAdapterData = CartManager.getInstance().getAllItems();

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
		View convertView = mInflater.inflate(R.layout.list_item_cart, parent,
				false);
		ViewHolder holder = new ViewHolder(convertView);

		return holder;
	}

	/* (non-Javadoc)
	 * @see android.support.v7.widget.RecyclerView.Adapter#onBindViewHolder(android.support.v7.widget.RecyclerView.ViewHolder, int)
	 */
	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {

		CartItem currentCartItem = mAdapterData.get(position);
		Product currentProduct = currentCartItem.getProduct();

		if (currentProduct.isNetworkResource()) {
			ImageLoader.getInstance().displayImage(
					currentProduct.getPhoto(mContext), holder.imageViewPicture,
					mDisplayImageOptions);
		} else {
			//holder.imageViewPicture.setImageDrawable(mContext.getResources().getDrawable(currentProduct.getDrawableId(mContext)));
            holder.imageViewPicture.setImageBitmap(Normal.loadImage(mContext, currentProduct.getPhoto(mContext)));
		}

		holder.title.setText(currentProduct.getTitle());

		String realPrice = Utils.mFormatter.format(currentProduct.getPrice())
				+ AppSettings.CURRENCY;
		if (currentProduct.getDiscount() > 0) {

			String discountedPrice = Utils.mFormatter.format(currentProduct
					.getDiscountedPrice()) + AppSettings.CURRENCY;

			holder.priceDiscount.setText(discountedPrice);

			holder.priceRealUnderlined.setPaintFlags(holder.priceRealUnderlined
					.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
			holder.priceRealUnderlined.setText(realPrice);

			holder.priceDiscount.setVisibility(View.VISIBLE);
			holder.priceRealUnderlined.setVisibility(View.VISIBLE);

		} else {
			holder.priceDiscount.setVisibility(View.GONE);

			holder.priceRealUnderlined.setText(realPrice);
			holder.priceRealUnderlined.setVisibility(View.VISIBLE);
		}

		holder.quantity.setText("" + currentCartItem.getQuantity());

		holder.imageViewPlus.setOnClickListener(new IncrementQuantityListener(
				currentProduct.getId()));
		holder.imageViewMinus.setOnClickListener(new DecrementQuantityListener(
				currentProduct.getId()));

	}

	/**
	 * The listener interface for receiving incrementQuantity events.
	 * The class that is interested in processing a incrementQuantity
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addIncrementQuantityListener<code> method. When
	 * the incrementQuantity event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see IncrementQuantityEvent
	 */
	private class IncrementQuantityListener implements OnClickListener {

		/** The product id. */
		int productId;

		/**
		 * Instantiates a new increment quantity listener.
		 *
		 * @param productId the product id
		 */
		public IncrementQuantityListener(int productId) {
			super();
			this.productId = productId;
		}

		/* (non-Javadoc)
		 * @see android.view.View.OnClickListener#onClick(android.view.View)
		 */
		@Override
		public void onClick(View v) {
			CartManager.getInstance().incrementQuantityOfProduct(productId);

			notifyDataSetChanged();

			mFragmentCart.generateValues();
		}
	}

	/**
	 * The listener interface for receiving decrementQuantity events.
	 * The class that is interested in processing a decrementQuantity
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addDecrementQuantityListener<code> method. When
	 * the decrementQuantity event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see DecrementQuantityEvent
	 */
	private class DecrementQuantityListener implements OnClickListener {
		
		/** The product id. */
		int productId;

		/**
		 * Instantiates a new decrement quantity listener.
		 *
		 * @param productId the product id
		 */
		public DecrementQuantityListener(int productId) {
			super();
			this.productId = productId;
		}

		/* (non-Javadoc)
		 * @see android.view.View.OnClickListener#onClick(android.view.View)
		 */
		@Override
		public void onClick(View v) {
			CartManager.getInstance().decrementQuantityOfProduct(productId);

			notifyDataSetChanged();

			mFragmentCart.generateValues();
		}
	}
}
