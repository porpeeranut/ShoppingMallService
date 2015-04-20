package com.dmbteam.catalogapp.util;

import android.content.Context;

import com.dmbteam.catalogapp.R;

public class ThemesManager {

	public enum APP_THEMES {
		ThemeGreen, ThemeBlue, ThemeOrange, ThemePurple, ThemeDarkBlue, ThemeNeutral, ThemePink;
	}

	public static void setCorrectTheme(Context context, APP_THEMES theme) {

		if (theme == APP_THEMES.ThemeGreen) {
			context.setTheme(R.style.ThemeGreen);

		} else if (theme == APP_THEMES.ThemeBlue) {
			context.setTheme(R.style.ThemeBlue);

		} else if (theme == APP_THEMES.ThemeDarkBlue) {
			context.setTheme(R.style.ThemeDarkBlue);

		} else if (theme == APP_THEMES.ThemeNeutral) {
			context.setTheme(R.style.ThemeNeutral);

		} else if (theme == APP_THEMES.ThemeOrange) {
			context.setTheme(R.style.ThemeOrange);

		} else if (theme == APP_THEMES.ThemePink) {
			context.setTheme(R.style.ThemePink);

		} else if (theme == APP_THEMES.ThemePurple) {
			context.setTheme(R.style.ThemePurple);

		}
	}
}
