package com.dmbteam.catalogapp.xmlparse;

import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.simpleframework.xml.core.Persister;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.dmbteam.catalogapp.MainActivity;
import com.dmbteam.catalogapp.cmn.Catalog;
import com.dmbteam.catalogapp.cmn.Category;
import com.dmbteam.catalogapp.cmn.Product;
import com.dmbteam.catalogapp.settings.AppSettings;

/**
 * The Class CatalogXmlParser.
 */
public class CatalogXmlParser {

	/** The Constant LOG. */
	public static final String LOG = CatalogXmlParser.class.getSimpleName();

	/** The Catalog. */
	private Catalog mCatalog;

	/** The serializer. */
	private Persister serializer;

	/** The instance. */
	public static CatalogXmlParser instance;

	/**
	 * Instantiates a new catalog xml parser.
	 */
	private CatalogXmlParser() {

	}

	/**
	 * Gets the single instance of CatalogXmlParser.
	 *
	 * @return single instance of CatalogXmlParser
	 */
	public static CatalogXmlParser getInstance() {
		if (instance == null) {
			synchronized (CatalogXmlParser.class) {
				if (instance == null) {
					instance = new CatalogXmlParser();
				}
			}
		}

		return instance;
	}

	/**
	 * Parses the data.
	 *
	 * @param c the c
	 */
	public void parseData(Context c) {
		try {
			serializer = new Persister();

			if (AppSettings.XMLResourcePath.startsWith("http")) {
				new CatalogXmlNetworkStremReader(c).execute();
			} else {
				InputStream inputStream = c.getAssets().open("catalog.xml");

				mCatalog = serializer.read(Catalog.class, inputStream);

				mCatalog.makeCategoriesHierarchy(new ArrayList<Category>(
						mCatalog.getAllCategories()));

				mCatalog.initCategoriesForAdapter();

				((MainActivity) c).initMainComponents();
			}
		} catch (Exception e) {
			Log.e(LOG, "Error while parsing xml data" + e.getMessage());
		}

	}

	/**
	 * Gets the catalog.
	 *
	 * @return the catalog
	 */
	public Catalog getCatalog() {
		return mCatalog;
	}

	/**
	 * Gets the sub categories.
	 *
	 * @param category the category
	 * @param padding the padding
	 * @return the sub categories
	 */
	public List<Category> getSubCategories(Category category, int padding) {

		List<Category> subCategories = new ArrayList<Category>();

		if (category.getSubCategoriesIds() != null) {
			for (int i = 0; i < category.getSubCategoriesIds().size(); i++) {
				Category findedCategory = findCategoryById(category
						.getSubCategoriesIds().get(i));

				if (findedCategory != null) {
					findedCategory.setOpened(false);
					findedCategory.setTreeIndex(padding);

					subCategories.add(findedCategory);
				}
			}
		}

		return subCategories;
	}

	/**
	 * Find category by id.
	 *
	 * @param id the id
	 * @return the category
	 */
	public Category findCategoryById(int id) {
		for (int i = 0; i < mCatalog.getAllCategories().size(); i++) {
			if (mCatalog.getAllCategories().get(i).getId() == id) {
				return mCatalog.getAllCategories().get(i);
			}
		}

		return null;
	}

	/**
	 * Gets the all products for categories.
	 *
	 * @param categories the categories
	 * @return the all products for categories
	 */
	public List<Product> getAllProductsForCategories(List<Category> categories) {

		List<Product> resultList = new ArrayList<Product>();

		for (int i = 0; i < categories.size(); i++) {
			List<Product> allProductsForCurrentCategory = getAllProductsForCategory(categories
					.get(i));
			resultList.addAll(allProductsForCurrentCategory);
		}

		return resultList;
	}

	/**
	 * Gets the all products for category.
	 *
	 * @param category the category
	 * @return the all products for category
	 */
	public List<Product> getAllProductsForCategory(Category category) {

		List<Product> resultList = new ArrayList<Product>();

		for (int i = 0; i < mCatalog.getAllProducts().size(); i++) {
			if (mCatalog.getAllProducts().get(i).getCategory() == category
					.getId()) {
				resultList.add(mCatalog.getAllProducts().get(i));
			}
		}

		for (int i = 0; i < mCatalog.getSlider().size(); i++) {
			if (mCatalog.getSlider().get(i).getCategory() == category.getId()) {
				resultList.add(mCatalog.getSlider().get(i));
			}
		}

		return resultList;
	}

	/**
	 * The Class CatalogXmlNetworkStremReader.
	 */
	private class CatalogXmlNetworkStremReader extends
			AsyncTask<Void, Void, Boolean> {

		/** The context. */
		private Context context;

		/**
		 * Instantiates a new catalog xml network strem reader.
		 *
		 * @param c the c
		 */
		public CatalogXmlNetworkStremReader(Context c) {
			this.context = c;
		}

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
		 */
		@Override
		protected Boolean doInBackground(Void... params) {

			InputStream inputStream = null;

			try {

				DefaultHttpClient client = new DefaultHttpClient();

				HttpGet method = new HttpGet(new URI(
						AppSettings.XMLResourcePath));

				HttpResponse res = client.execute(method);
				res = client.execute(method);

				InputStream result = res.getEntity().getContent();

				if (result != null) {
					try {
						mCatalog = serializer.read(Catalog.class, result);
					} catch (Exception e) {
						System.out.println("");
					}

					mCatalog.makeCategoriesHierarchy(new ArrayList<Category>(
							mCatalog.getAllCategories()));

					mCatalog.initCategoriesForAdapter();

					return true;
				}

			} catch (Exception e) {

			}
			return false;
		}

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);

			if (result.booleanValue()) {
				((MainActivity) context).initMainComponents();
			}
		}
	}
}
