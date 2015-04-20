package com.dmbteam.catalogapp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AutoIncrementGenerator {

	private static final String PREFERENCES = "catalog.preferences";

	private static final String KEY_PREFS_INCREMENT = "key.prefs.increment";

	public static int getIncrementValue(Context context) {
		SharedPreferences prefs = getSharedPreferences(context);

		int currentValue = prefs.getInt(KEY_PREFS_INCREMENT, 0);

		int incrementedValue = ++currentValue;

		Editor editor = prefs.edit();
		editor.putInt(KEY_PREFS_INCREMENT, incrementedValue);
		editor.commit();

		return incrementedValue;
	}

	public static SharedPreferences getSharedPreferences(Context context) {
		return context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
	}
}
