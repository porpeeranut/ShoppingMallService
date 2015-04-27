package com.dmbteam.catalogapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dmbteam.catalogapp.adapter.SlidingAdapter;
import com.dmbteam.catalogapp.cart.CartManager;
import com.dmbteam.catalogapp.cmn.Category;
import com.dmbteam.catalogapp.cmn.Product;
import com.dmbteam.catalogapp.fragment.FragmentCart;
import com.dmbteam.catalogapp.fragment.FragmentCartPreview;
import com.dmbteam.catalogapp.fragment.FragmentFilter;
import com.dmbteam.catalogapp.fragment.FragmentMain;
import com.dmbteam.catalogapp.fragment.FragmentSelectedProduct;
import com.dmbteam.catalogapp.settings.AppSettings;
import com.dmbteam.catalogapp.util.ImageOptionsBuilder;
import com.dmbteam.catalogapp.util.ThemesManager;
import com.dmbteam.catalogapp.util.Utils;
import com.dmbteam.catalogapp.xmlparse.CatalogXmlParser;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * The Class MainActivity.
 */
public class MainActivity extends ActionBarActivity implements
		OnBackStackChangedListener {

	/** The Sliding list view. */
	private ListView mSlidingListView;

	/** The Main adapter data. */
	private ArrayList<Category> mMainAdapterData;

	/** The Sliding adapter. */
	private SlidingAdapter mSlidingAdapter;

	/** The catalog xml parser. */
	private CatalogXmlParser catalogXmlParser;

	/** The Drawer toggle. */
	private ActionBarDrawerToggle mDrawerToggle;

	/** The Drawer layout. */
	private DrawerLayout mDrawerLayout;

	/** The Toolbar. */
	private Toolbar mToolbar;

	/** The Ab cart counter. */
	private TextView mAbCartCounter;

	/** The Ab title. */
	private TextView mAbTitle;

	/** The Ab search. */
	private View mAbSearch;

	/** The Ab checkout. */
	private View mAbCheckout;

	/** The Ab cart image view. */
	private View mAbCartImageView;

	/** The Ab clear filter. */
	private View mAbClearFilter;

	/** The Ab search et. */
	private EditText mAbSearchEt;

	/** The Ab cancel search. */
	private View mAbCancelSearch;

	/** The Search text watcher. */
	private SearchTextWatcher mSearchTextWatcher;

	/** The Ab sent. */
	private View mAbSent;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v7.app.ActionBarActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

		ThemesManager.setCorrectTheme(this, AppSettings.CURRENT_THEME);

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		mSearchTextWatcher = new SearchTextWatcher();

		ImageLoaderConfiguration imageLoaderConfiguration = ImageOptionsBuilder
				.createImageLoaderConfiguration(this);
		ImageLoader.getInstance().init(imageLoaderConfiguration);

		catalogXmlParser = CatalogXmlParser.getInstance();

		catalogXmlParser.parseData(this);
        initMainComponents();

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		initActionbar();
	}

	/**
	 * Inits the main components.
	 */
	public void initMainComponents() {
        catalogXmlParser.set_New_Data(getApplicationContext());

		initLeftSlidingMenu();
		setUpAdapter();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onActivityResult(int, int,
	 * android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == FragmentCartPreview.MAIL_REQUEST_CODE) {
			setUpAdapter();

			CartManager.getInstance().getAllItems().clear();

			refreshCartCounter();
		}
	}

	/**
	 * Sets the up adapter.
	 */
	private void setUpAdapter() {
		List<Product> sliderProducts = catalogXmlParser.getCatalog()
				.getSlider();

		List<Product> allProducts = catalogXmlParser.getCatalog()
				.getAllProducts();

		setUpAdapter(sliderProducts, allProducts);
	}

	/**
	 * Sets the up adapter.
	 * 
	 * @param sliderProducts
	 *            the slider products
	 * @param allProducts
	 *            the all products
	 */
	private void setUpAdapter(List<Product> sliderProducts,
			List<Product> allProducts) {
		List<List<Product>> mAdapterData = new ArrayList<List<Product>>();

		if (sliderProducts.size() > 0) {
			mAdapterData.add(sliderProducts);
		}

		if (allProducts.size() > 0) {

			while (allProducts.size() > 3) {
				int randInt = Utils.randInt(1, 3);

				List<Product> currentSelectedion = allProducts.subList(0,
						randInt);
				mAdapterData.add(currentSelectedion);

				allProducts = new ArrayList<Product>(allProducts.subList(
						randInt, allProducts.size()));
			}

			if (allProducts.size() == 1) {

				List<Product> singleProduct = new ArrayList<Product>();

				singleProduct.add(allProducts.get(0));

				mAdapterData.add(singleProduct);
			} else if (allProducts.size() == 2) {
				List<Product> twoProducts = new ArrayList<Product>();

				twoProducts.add(allProducts.get(0));
				twoProducts.add(allProducts.get(1));

				mAdapterData.add(twoProducts);
			} else if (allProducts.size() == 3) {
				List<Product> threeProducts = new ArrayList<Product>();

				threeProducts.add(allProducts.get(0));
				threeProducts.add(allProducts.get(1));
				threeProducts.add(allProducts.get(2));

				mAdapterData.add(threeProducts);
			}
		}

		showMainFragment(false, true, mAdapterData, sliderProducts.size() > 0);

	}

	/**
	 * Inits the left sliding menu.
	 */
	public void initLeftSlidingMenu() {
		mSlidingListView = (ListView) findViewById(R.id.sliding_layout_listview);

		TextView metadatTitle = (TextView) findViewById(R.id.sliding_main_title);
		metadatTitle.setText(AppSettings.CATALOG_NAME);

		TextView metadatSubtitle = (TextView) findViewById(R.id.sliding_main_subtitle);
		metadatSubtitle.setText(AppSettings.MAIL);

		TextView metadatPhone = (TextView) findViewById(R.id.left_drawer_metadata_phone);
		String phoneString = getString(R.string.metadata_phone);
		metadatPhone.setText(String.format(phoneString, AppSettings.PHONE));

		TextView metadatSkype = (TextView) findViewById(R.id.left_drawer_metadata_skype);
		String skypeString = getString(R.string.metadata_skype);
		metadatSkype.setText(String.format(skypeString, AppSettings.MONEY) + String.format(" Baht")); //แก้ Skype เป็นจำนวนเงินซะ

		/*TextView metadatFacebook = (TextView) findViewById(R.id.left_drawer_metadata_facebook);
		String facebookString = getString(R.string.metadata_facebook);
		metadatFacebook.setText(String.format(facebookString,
				AppSettings.FACEBOOK));*/

		mMainAdapterData = catalogXmlParser.getCatalog().getMainCategories();

		mSlidingAdapter = new SlidingAdapter(this, 0, mMainAdapterData);

		mSlidingListView.setAdapter(mSlidingAdapter);

		final LinearLayout metadataLayout = (LinearLayout) findViewById(R.id.left_drawer_metadata_ll);

		final Animation myAnimation1 = AnimationUtils.loadAnimation(
				getApplicationContext(), R.anim.fade_in);
		final Animation myAnimation2 = AnimationUtils.loadAnimation(
				getApplicationContext(), R.anim.fade_out);

		View rightArrow = findViewById(R.id.sliding_right_arrow);

		rightArrow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (metadataLayout.getVisibility() == View.VISIBLE) {
					metadataLayout.startAnimation(myAnimation2);

					new Handler().postDelayed(new Runnable() {

						@Override
						public void run() {
							metadataLayout.setVisibility(View.GONE);

						}
					}, 800);

				} else {
					metadataLayout.setVisibility(View.VISIBLE);
					metadataLayout.startAnimation(myAnimation1);

				}

			}
		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onPostCreate(android.os.Bundle)
	 */
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		mDrawerToggle.syncState();
	}

	/**
	 * Show main fragment.
	 * 
	 * @param addToBackStack
	 *            the add to back stack
	 * @param clearBackStack
	 *            the clear back stack
	 * @param adapterData
	 *            the adapter data
	 * @param isHaveSlider
	 *            the is have slider
	 */
	public void showMainFragment(boolean addToBackStack,
			boolean clearBackStack, List<List<Product>> adapterData,
			boolean isHaveSlider) {
		FragmentMain fragmentMain = FragmentMain.newInstance(adapterData);
		fragmentMain.setHaveSlider(isHaveSlider);

		showScreen(fragmentMain, FragmentMain.TAG, addToBackStack,
				clearBackStack);
	}

    public void showFragment(boolean addToBackStack, boolean clearBackStack, Fragment fragment) {
        showScreen(fragment, FragmentMain.TAG, addToBackStack, clearBackStack);
    }

	/**
	 * Show selected product fragment.
	 * 
	 * @param addToBackStack
	 *            the add to back stack
	 * @param clearBackStack
	 *            the clear back stack
	 * @param adapterData
	 *            the adapter data
	 * @param selectedProductId
	 *            the selected product id
	 */
	@SuppressLint("NewApi")
	public void showSelectedProductFragment(boolean addToBackStack,
			boolean clearBackStack, List<Product> adapterData,
			int selectedProductId) {

		FragmentMain fraMain = (FragmentMain) getSupportFragmentManager()
				.findFragmentByTag(FragmentMain.TAG);

		FragmentFilter fraFilter = (FragmentFilter) getSupportFragmentManager()
				.findFragmentByTag(FragmentFilter.TAG);

		FragmentSelectedProduct fragmentSelectedProduct = FragmentSelectedProduct
				.newInstance(adapterData, selectedProductId);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
				&& (fraFilter != null || fraMain != null)) {

			Transition trExplode = TransitionInflater.from(this)
					.inflateTransition(android.R.transition.slide_bottom);

			Transition trExplode1 = TransitionInflater.from(this)
					.inflateTransition(android.R.transition.slide_top);

			if (fraMain != null) {
				fraMain.setSharedElementReturnTransition(trExplode);
				fraMain.setExitTransition(trExplode);
			} else {
				fraFilter.setSharedElementReturnTransition(trExplode);
				fraFilter.setExitTransition(trExplode);
			}

			fragmentSelectedProduct.setSharedElementEnterTransition(trExplode);
			fragmentSelectedProduct.setEnterTransition(trExplode1);

			// Our shared element (in Fragment A)
			View listItemMainLayout = LayoutInflater.from(this).inflate(
					R.layout.list_item_main, null);
			ImageView mProductImage = (ImageView) listItemMainLayout
					.findViewById(R.id.list_item_container_1_image);

			showScreen(fragmentSelectedProduct, FragmentSelectedProduct.TAG,
					addToBackStack, clearBackStack, mProductImage);

		} else {
			showScreen(fragmentSelectedProduct, FragmentSelectedProduct.TAG,
					addToBackStack, clearBackStack);
		}
	}

	/**
	 * Show cart fragment.
	 * 
	 * @param addToBackStack
	 *            the add to back stack
	 * @param clearBackStack
	 *            the clear back stack
	 */
	public void showCartFragment(boolean addToBackStack, boolean clearBackStack) {

		if (CartManager.getInstance().getAllItems().size() == 0) {

			Toast.makeText(this, getString(R.string.no_cart_items),
					Toast.LENGTH_LONG).show();

			return;
		}

		FragmentCart fragmentCart = FragmentCart.newInstance();

		showScreen(fragmentCart, FragmentCart.TAG, addToBackStack,
				clearBackStack);
	}

	/**
	 * Show cart fragment preview.
	 * 
	 * @param addToBackStack
	 *            the add to back stack
	 * @param clearBackStack
	 *            the clear back stack
	 */
	public void showCartFragmentPreview(boolean addToBackStack,
			boolean clearBackStack) {

		if (CartManager.getInstance().getAllItems().size() == 0) {

			Toast.makeText(this, getString(R.string.no_cart_items),
					Toast.LENGTH_LONG).show();

			return;
		}

		FragmentCartPreview fragmentCart = FragmentCartPreview.newInstance();

		showScreen(fragmentCart, FragmentCartPreview.TAG, addToBackStack,
				clearBackStack);
	}

	/**
	 * Show filtered fragment.
	 * 
	 * @param filteredProducts
	 *            the filtered products
	 */
	private void showFilteredFragment(List<Product> filteredProducts) {
		FragmentFilter fragmentFilter = FragmentFilter
				.newInstance(filteredProducts);

		// if (getSupportFragmentManager().findFragmentByTag(FragmentFilter.TAG)
		// == null) {
		showScreen(fragmentFilter, FragmentFilter.TAG, false, true);
		// } else {
		// ((FragmentFilter) getSupportFragmentManager().findFragmentByTag(
		// FragmentFilter.TAG)).notifyChange(filteredProducts);
		// }

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentManager.OnBackStackChangedListener#
	 * onBackStackChanged()
	 */
	@Override
	public void onBackStackChanged() {
		mDrawerToggle.setDrawerIndicatorEnabled(getSupportFragmentManager()
				.getBackStackEntryCount() == 0);
		getSupportActionBar().setDisplayHomeAsUpEnabled(
				getSupportFragmentManager().getBackStackEntryCount() > 0);
		mDrawerToggle.syncState();
	}

	/**
	 * Open category in slider.
	 * 
	 * @param category
	 *            the category
	 * @param position
	 *            the position
	 * @param padding
	 *            the padding
	 */
	public void openCategoryInSlider(Category category, int position,
			int padding) {
		for (int i = 0; i < mMainAdapterData.size(); i++) {
			if (mMainAdapterData.get(i).getId() == category.getId()) {

				List<Category> subCategories = catalogXmlParser
						.getSubCategories(category, padding);

				if (subCategories.size() > 0) {
					mMainAdapterData.addAll(++position, subCategories);

					mSlidingAdapter.notifyDataSetChanged();
				}

				break;
			}
		}
	}

	/**
	 * Load product for selected category.
	 * 
	 * @param category
	 *            the category
	 * @param position
	 *            the position
	 * @param padding
	 *            the padding
	 */
	public void loadProductForSelectedCategory(final Category category,
			int position, final int padding) {

		// Close sliding menu
		mDrawerLayout.closeDrawer(GravityCompat.START);

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < mMainAdapterData.size(); i++) {
					if (mMainAdapterData.get(i).getId() == category.getId()) {
                        //Toast.makeText(getApplicationContext(), ""+category.getId(), Toast.LENGTH_SHORT).show();
                        List<Category> subCategories;
                        switch (category.getId()) {
                            case 50:
                                setUpAdapter();
                                break;
                            case 300:
                                showFragment(false, true, new BaiSed());
                                break;
                            case 400:
                                showFragment(false, true, new topup());
                                break;
                            case 500:
                                showFragment(false, true, new Setting());
                                break;
                            case 600:
                                final Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                            default:
                                subCategories = catalogXmlParser
                                        .getSubCategories(category, padding);

                                // 3 dimension hieararchy
                                Integer size = new Integer(subCategories.size());
                                for (int j = 0; j < size; j++) {

                                    Category currentCategory = subCategories.get(j);

                                    List<Category> currentSubCategories = catalogXmlParser
                                            .getSubCategories(currentCategory, padding);

                                    subCategories.addAll(currentSubCategories);

                                }

                                subCategories.add(category);

                                List<Product> allProductsForCategories = catalogXmlParser
                                        .getAllProductsForCategories(subCategories);

                                if (allProductsForCategories.size() > 3) {
                                    List<Product> productsForSlider = allProductsForCategories
                                            .subList(0, 3);
                                    List<Product> productsForAdapter = allProductsForCategories
                                            .subList(3, allProductsForCategories.size());

                                    setUpAdapter(productsForSlider, productsForAdapter);

                                } else {
                                    setUpAdapter(allProductsForCategories,
                                            new ArrayList<Product>());
                                }

                                showFilteredAb(category.getTitle());

                                if (allProductsForCategories.size() == 0) {
                                    Toast.makeText(MainActivity.this,
                                            getString(R.string.no_result_finded),
                                            Toast.LENGTH_LONG).show();
                                }
                        }
						break;
					}
				}
			}
		}, 300);
	}

	/**
	 * Close category.
	 * 
	 * @param category
	 *            the category
	 * @param position
	 *            the position
	 */
	public void closeCategory(Category category, int position) {

		for (int i = position + 1; i < mMainAdapterData.size(); i++) {
			if (mMainAdapterData.get(i).getTreeIndex() > category
					.getTreeIndex()) {
				mMainAdapterData.set(i, null);
			} else {
				break;
			}
		}

		mMainAdapterData.removeAll(Collections.singleton(null));
		mSlidingAdapter.notifyDataSetChanged();
	}

	/**
	 * Refresh cart counter.
	 */
	public void refreshCartCounter() {
		this.mAbCartCounter.setText(""
				+ CartManager.getInstance().getAllItems().size());
	}

	/**
	 * Sets the ab title.
	 * 
	 * @param title
	 *            the new ab title
	 */
	public void setAbTitle(String title) {
		mAbTitle.setText(title);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v7.app.ActionBarActivity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		checkForBackStackCounter();

		if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
			mDrawerLayout.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}

	/**
	 * Check for back stack counter.
	 */
	public void checkForBackStackCounter() {
		if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
			setAbTitle("");
		}
	}

	/**
	 * Show checkout ab.
	 */
	public void showCheckoutAb() {
		setAbTitle(getString(R.string.shopping_cart));

		mAbSearch.setVisibility(View.GONE);
		mAbCartImageView.setVisibility(View.GONE);
		mAbCartCounter.setVisibility(View.GONE);
		mAbSent.setVisibility(View.GONE);

		mAbCheckout.setVisibility(View.VISIBLE);
	}

	/**
	 * Show common ab.
	 */
	public void showCommonAb() {
		mAbSearch.setVisibility(View.VISIBLE);
		mAbCartImageView.setVisibility(View.VISIBLE);
		mAbCartCounter.setVisibility(View.VISIBLE);

		mAbCheckout.setVisibility(View.GONE);
		mAbClearFilter.setVisibility(View.GONE);
		mAbSearchEt.setVisibility(View.GONE);
		mAbCancelSearch.setVisibility(View.GONE);
		mAbSent.setVisibility(View.GONE);

	}

	/**
	 * Show search ab.
	 */
	public void showSearchAb() {
		mAbSearch.setVisibility(View.GONE);
		mAbCartImageView.setVisibility(View.GONE);
		mAbCartCounter.setVisibility(View.GONE);
		mAbCheckout.setVisibility(View.GONE);
		mAbClearFilter.setVisibility(View.GONE);
		mAbSent.setVisibility(View.GONE);

		mAbSearchEt.setVisibility(View.VISIBLE);
		mAbCancelSearch.setVisibility(View.VISIBLE);
	}

	/**
	 * Show filtered ab.
	 * 
	 * @param title
	 *            the title
	 */
	public void showFilteredAb(String title) {
		mAbSearch.setVisibility(View.GONE);
		mAbCartImageView.setVisibility(View.GONE);
		mAbCartCounter.setVisibility(View.GONE);
		mAbSent.setVisibility(View.GONE);

		mAbCheckout.setVisibility(View.GONE);
		mAbClearFilter.setVisibility(View.VISIBLE);
		mAbTitle.setText(title);
	}

	/**
	 * Show order summary ab.
	 */
	public void showOrderSummaryAb() {
		mAbCheckout.setVisibility(View.GONE);

		setAbTitle(getString(R.string.order_summary));
		mAbSent.setVisibility(View.VISIBLE);
	}

	/**
	 * Gets the ab sent.
	 * 
	 * @return the ab sent
	 */
	public View getAbSent() {
		return mAbSent;
	}

	/**
	 * Gets the ab search et.
	 * 
	 * @return the ab search et
	 */
	public EditText getAbSearchEt() {
		return mAbSearchEt;
	}

	/**
	 * Show screen.
	 * 
	 * @param content
	 *            the content
	 * @param contentTag
	 *            the content tag
	 * @param addToBackStack
	 *            the add to back stack
	 * @param clearBackStack
	 *            the clear back stack
	 */
	private void showScreen(Fragment content, String contentTag,
			boolean addToBackStack, boolean clearBackStack) {
		FragmentManager fm = getSupportFragmentManager();
		fm.addOnBackStackChangedListener(this);
		FragmentTransaction ft = fm.beginTransaction();

		ft.replace(R.id.main_placeholder_content, content, contentTag);

		if (clearBackStack) {
			fm.popBackStackImmediate(null,
					FragmentManager.POP_BACK_STACK_INCLUSIVE);
		}

		if (addToBackStack) {
			ft.addToBackStack(String.valueOf(System.identityHashCode(content)));
		}

		ft.commitAllowingStateLoss();
		fm.executePendingTransactions();
	}

	/**
	 * Show screen.
	 * 
	 * @param content
	 *            the content
	 * @param contentTag
	 *            the content tag
	 * @param addToBackStack
	 *            the add to back stack
	 * @param clearBackStack
	 *            the clear back stack
	 * @param imageView
	 *            the image view
	 */
	private void showScreen(Fragment content, String contentTag,
			boolean addToBackStack, boolean clearBackStack, ImageView imageView) {
		FragmentManager fm = getSupportFragmentManager();
		fm.addOnBackStackChangedListener(this);
		FragmentTransaction ft = fm.beginTransaction();
		ft.addSharedElement(imageView, getString(R.string.transition_cover));

		ft.replace(R.id.main_placeholder_content, content, contentTag);

		if (clearBackStack) {
			fm.popBackStackImmediate(null,
					FragmentManager.POP_BACK_STACK_INCLUSIVE);
		}

		if (addToBackStack) {
			ft.addToBackStack(String.valueOf(System.identityHashCode(content)));
		}

		ft.commitAllowingStateLoss();
		fm.executePendingTransactions();
	}

	/**
	 * Inits the actionbar.
	 */
	private void initActionbar() {
		mToolbar = (Toolbar) findViewById(R.id.toolbar);
		

		setSupportActionBar(mToolbar);

		LayoutInflater inflater = LayoutInflater.from(this);
		View toolbarLayout = (RelativeLayout) inflater.inflate(
				R.layout.ab_custom, null);
		
		mAbCartCounter = (TextView) toolbarLayout
				.findViewById(R.id.ab_custom_card_counter);
		mAbTitle = (TextView) toolbarLayout.findViewById(R.id.ab_custom_title);
		mAbSearch = toolbarLayout.findViewById(R.id.ab_custom_search);
		mAbCheckout = toolbarLayout.findViewById(R.id.ab_custom_checkout);
		mAbClearFilter = toolbarLayout
				.findViewById(R.id.ab_custom_clear_result);
		mAbSearchEt = (EditText) toolbarLayout
				.findViewById(R.id.ab_custom_search_et);
		mAbCancelSearch = toolbarLayout
				.findViewById(R.id.ab_custom_cancel_search);
		mAbCartImageView = toolbarLayout.findViewById(R.id.ab_custom_card);
		mAbSent = toolbarLayout.findViewById(R.id.ab_custom_sent);

		Toolbar.LayoutParams layoutParams = new Toolbar.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		mToolbar.addView(toolbarLayout, layoutParams);
		mToolbar.bringToFront();

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				mToolbar, R.string.drawer_open, R.string.drawer_close) {

			@Override
			public void onDrawerSlide(View drawerView, float slideOffset) {
				// TODO Auto-generated method stub
				super.onDrawerSlide(drawerView, slideOffset);
			}

			/** Called when a drawer has settled in a completely closed state. */
			@Override
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				getSupportActionBar().setTitle(getString(R.string.app_name));
			}

			/** Called when a drawer has settled in a completely open state. */
			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				getSupportActionBar().setTitle(
						getString(R.string.choose_category));
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		mDrawerToggle
				.setToolbarNavigationClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						getSupportFragmentManager().popBackStack();

						checkForBackStackCounter();
					}
				});

		mAbCartImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showCartFragment(true, false);
			}
		});

		mAbClearFilter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mAbTitle.setText("");
				setUpAdapter();
			}
		});

		mAbSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showSearchAb();

				mAbSearchEt.requestFocus();

				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.showSoftInput(mAbSearchEt, InputMethodManager.SHOW_IMPLICIT);

				mAbSearchEt.addTextChangedListener(mSearchTextWatcher);
			}
		});

		mAbCancelSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				showCommonAb();
				setUpAdapter();

				InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(mAbSearchEt.getWindowToken(), 0);

				mAbSearchEt.removeTextChangedListener(mSearchTextWatcher);

				mAbSearchEt.setText("");
			}
		});

		mAbCheckout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showCartFragmentPreview(true, false);
			}
		});
	}

	/**
	 * The Class SearchTextWatcher.
	 */
	private class SearchTextWatcher implements TextWatcher {

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.text.TextWatcher#onTextChanged(java.lang.CharSequence,
		 * int, int, int)
		 */
		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			if (s.length() > 2) {

				List<Product> filteredProduct = catalogXmlParser.getCatalog()
						.findAllProductsWithTitleContaining(s);

				showFilteredFragment(filteredProduct);
			} else if (s.length() == 0) {
				mAbCancelSearch.performClick();
			}

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * android.text.TextWatcher#beforeTextChanged(java.lang.CharSequence,
		 * int, int, int)
		 */
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.text.TextWatcher#afterTextChanged(android.text.Editable)
		 */
		@Override
		public void afterTextChanged(Editable s) {

		}
	}
}
