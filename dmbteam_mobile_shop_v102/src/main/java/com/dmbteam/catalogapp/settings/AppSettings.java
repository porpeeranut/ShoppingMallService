package com.dmbteam.catalogapp.settings;

import static com.dmbteam.catalogapp.util.ThemesManager.APP_THEMES.*;
import com.dmbteam.catalogapp.util.ThemesManager.APP_THEMES;

/**
 * The Class AppSettings.
 */
public class AppSettings {

	/** Theme of the app 
	 * 
	 * Replace the value with any of these:
	 * 
	 * ThemeGreen, ThemeBlue, ThemeOrange, ThemePurple, ThemeDarkBlue, ThemeNeutral, ThemePink
	 * 
	 * */
	public static final APP_THEMES CURRENT_THEME = ThemeGreen;

	/** The Constant CURRENCY. */
	public static final String CURRENCY = "$";

	/** The Constant VAT. */
	public static final double VAT = 0.2;

	/**
	 * The Constant XMLResourcePath. Here you can either have some network xml
	 * resource or local file in assets folder.
	 */
	public static final String XMLResourcePath = "catalog.xml";
	// public static final String XMLResourcePath =
	// "http://dmb-team.com/apps/catalog/catalog_web.xml";

	/**
	 * The Constant applicationId. This is related to parse and push
	 * notifications
	 */
	public static final String applicationId = "H3SJDXva33rnKrG3FUPzGgGvFNIzp5Ld0JqSbDUD";

	/** The Constant clientKey. This is related to parse and push notifications */
	public static final String clientKey = "NVemF7kKI5euG3d7V166aSUVVerzbp4qJJIuzMgs";

	/** The Constant CATALOG_NAME. */
	public static final String CATALOG_NAME = "กัน บ้องถอบ";

	/** The Constant MAIL. */
	public static final String MAIL = "gun@bongtrop.com";

	/** The Constant PHONE. */
	public static final String PHONE = "555-555-33";

	/** The Constant SKYPE. */
	public static final int MONEY = 0;

	/** The Constant FACEBOOK. */
	public static final String FACEBOOK = " ";
}
