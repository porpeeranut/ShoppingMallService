package com.dmbteam.catalogapp.cmn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import com.dmbteam.catalogapp.cart.CartItem;

import android.util.Log;

/**
 * The Class Catalog.
 */
@Root
public class Catalog {

	/** The categories. */
	@ElementList
	private List<Category> categories;

	/** The products. */
	@ElementList
	private List<Product> products;

	/** The slider. */
	@ElementList
	private List<Product> slider;

	// All categories with hierarchy
	/** The Predefines categories. */
	private ArrayList<Category> mPredefinesCategories;

	// All categories for adapter
	/** The Main categories. */
	private ArrayList<Category> mMainCategories;

	/**
	 * Make categories hierarchy.
	 *
	 * @param localPredefinesCategories the local predefines categories
	 */
	public void makeCategoriesHierarchy(List<Category> localPredefinesCategories) {

		if (localPredefinesCategories != null
				&& localPredefinesCategories.size() > 0) {
			Category category = localPredefinesCategories.get(0);
			localPredefinesCategories.remove(0);

			if (category.getParentId() == 0) {
				category.setMain(true);
			}

			for (int i = 0; i < localPredefinesCategories.size(); i++) {
				if (localPredefinesCategories.get(i).getParentId() == category
						.getId()) {
					category.getSubCategoriesIds().add(
							localPredefinesCategories.get(i).getId());
					// localPredefinesCategories.set(i, null);
				}

				if (localPredefinesCategories.get(i).getId() == category
						.getParentId()) {
					localPredefinesCategories.get(i).addSubCategoryId(
							category.getId());
				}
			}

			getPredefinesCategories().add(category);

			// localPredefinesCategories.removeAll(Collections.singleton(null));

			makeCategoriesHierarchy(localPredefinesCategories);
		} else {
			System.out.println("");
		}
	}

	// All categories sorted by id
	/**
	 * Gets the all categories.
	 *
	 * @return the all categories
	 */
	public List<Category> getAllCategories() {
		return categories;
	}

	// All products sorted by id
	/**
	 * Gets the all products.
	 *
	 * @return the all products
	 */
	public List<Product> getAllProducts() {
		return products;
	}

	// All categories with hierarchy
	/**
	 * Gets the predefines categories.
	 *
	 * @return the predefines categories
	 */
	public ArrayList<Category> getPredefinesCategories() {

		if (mPredefinesCategories == null) {
			mPredefinesCategories = new ArrayList<Category>();
		}

		return mPredefinesCategories;
	}

	// Initialize first with main categories
	/**
	 * Inits the categories for adapter.
	 */
	public void initCategoriesForAdapter() {
		mMainCategories = new ArrayList<Category>();

		for (int i = 0; i < mPredefinesCategories.size(); i++) {
			if (mPredefinesCategories.get(i).isMain()) {
				mMainCategories.add(mPredefinesCategories.get(i));
			}
		}
	}

	/**
	 * Gets the main categories.
	 *
	 * @return the main categories
	 */
	public ArrayList<Category> getMainCategories() {
		return mMainCategories;
	}

	/**
	 * Gets the slider.
	 *
	 * @return the slider
	 */
	public List<Product> getSlider() {
		return slider;
	}

	/**
	 * Find all products with title containing.
	 *
	 * @param s the s
	 * @return the list
	 */
	public List<Product> findAllProductsWithTitleContaining(CharSequence s) {

		List<Product> products = new ArrayList<Product>();

		for (int i = 0; i < getAllProducts().size(); i++) {
			Product currentProduct = getAllProducts().get(i);

			if (currentProduct.getTitle().toLowerCase()
					.contains(s.toString().toLowerCase())) {
				products.add(currentProduct);
			}
		}
		
		for (int i = 0; i < getSlider().size(); i++) {
			Product currentProduct = getSlider().get(i);

			if (currentProduct.getTitle().toLowerCase()
					.contains(s.toString().toLowerCase())) {
				products.add(currentProduct);
			}
		}

		return products;
	}

}
