package com.dmbteam.catalogapp.xmlparse;

import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.simpleframework.xml.core.Persister;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.dmbteam.catalogapp.MainActivity;
import com.dmbteam.catalogapp.cmn.Catalog;
import com.dmbteam.catalogapp.cmn.Category;
import com.dmbteam.catalogapp.cmn.Product;
import com.dmbteam.catalogapp.lib.Connecter;
import com.dmbteam.catalogapp.lib.Normal;
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

				//mCatalog.initCategoriesForAdapter();

				//((MainActivity) c).initMainComponents();
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



    public void set_New_Data(Context context) {
        String key = Normal.get_key_in_Pref(context);
        Connecter api = new Connecter(Normal.get_apiURL_in_Pref(context));
        api.setKey(key);
        JSONObject objUserData = api.getInitData();
        JSONObject objStore = api.getStore();
        JSONObject objCategory = api.getCategory();
        //Log.e("userDate", objUserData.getString("data"));

        //obj.getString("data");

        List<Category> categories = new ArrayList<Category>();
        Category cate;
        cate = new Category(50, true, 0, "หน้าหลัก");
        categories.add(cate);
        //--------------------------------------------------------
        cate = new Category(100, true, 0, "ร้าน");
        try {
            JSONArray jArray = objStore.getJSONArray("data");
            for (int i = 0;i <  jArray.length();i++) {
                JSONObject tmp = (JSONObject)jArray.get(i);
                int cateID = Integer.parseInt(tmp.getString("id"));
                Category tmpCate = new Category(cateID, false, 100, tmp.getString("fullname"));
                categories.add(tmpCate);
                cate.addSubCategoryId(cateID);

                Log.e("categ", tmp.getString("fullname"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        categories.add(cate);
        //--------------------------------------------------------
        cate = new Category(200, true, 0, "ประเภท");
        try {
            JSONArray jArray = objCategory.getJSONArray("data");
            for (int i = 0;i <  jArray.length();i++) {
                JSONObject tmp = (JSONObject)jArray.get(i);
                int cateID = Integer.parseInt(tmp.getString("id"));
                Category tmpCate = new Category(cateID, false, 200, tmp.getString("name"));
                categories.add(tmpCate);
                cate.addSubCategoryId(cateID);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        categories.add(cate);
        //--------------------------------------------------------
        cate = new Category(300, true, 0, "ใบเสร็จ");
        categories.add(cate);

        cate = new Category(400, true, 0, "เติมเงิน");
        categories.add(cate);

        cate = new Category(500, true, 0, "Setting");
        categories.add(cate);

        cate = new Category(600, true, 0, "ออกจากระบบ");
        categories.add(cate);
        //--------------------------------------------------------

        List<Product> products = new ArrayList<Product>();
        Product prod;
        //  int id, int category, double price, String date, String title, String photo, String description
        prod = new Product(1, 101, 1000, "30.10.2014", "Nexus 9", "home_nexus9");
        prod.setDescription("8.9 IPS LCD, Android 5.0, NVIDIA Tegra K1 processor 2.3 GHz, 32 Gb");
        prod.setDiscount(20);
        products.add(prod);

        prod = new Product(6, 201, 999, "01.10.2014", "LG G3", "lg_g3");
        prod.setDescription("5.5 Inches, 1440x2560 pixels, 13 MP Camera, 16 GB Storage");
        prod.setDiscount(100);
        products.add(prod);

        prod = new Product(7, 101, 1499, "02.10.2014", "Acer S7 13.3", "acer_s7");
        prod.setDescription("The premium Ultrabook from Acer");
        prod.setDiscount(80);
        products.add(prod);

        mCatalog.setCategories(categories);
        mCatalog.setProducts(products);
        mCatalog.initCategoriesForAdapter();
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
