package com.dmbteam.catalogapp.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.TypedArray;

public class ThemeManager {

	/**
	 * Gets the id for specific attribute.
	 * 
	 * @param context
	 *            the context
	 * @param attrId
	 *            the attr id
	 * @return the id for specific attribute
	 */
	public static int getIdForSpecificAttribute(Context context, int attrId) {
		TypedArray themesArray = context.getTheme().obtainStyledAttributes(
				getThemeName(context), new int[] { attrId });
		return themesArray.getResourceId(0, 0);
	}

	public static int getThemeName(Context context) {
		PackageInfo packageInfo;
		try {
			packageInfo = context.getPackageManager().getPackageInfo(
					context.getPackageName(), PackageManager.GET_META_DATA);
			int themeResId = packageInfo.applicationInfo.theme;

			return themeResId;
			// return context.getResources().getResourceEntryName(themeResId);
		} catch (NameNotFoundException e) {
			return -1;
		}
	}

}
