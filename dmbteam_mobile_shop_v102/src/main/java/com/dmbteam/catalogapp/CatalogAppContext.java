package com.dmbteam.catalogapp;

import com.dmbteam.catalogapp.settings.AppSettings;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.PushService;

import android.app.Application;

/**
 * The Class CatalogAppContext.
 */
public class CatalogAppContext extends Application {
	
	/* (non-Javadoc)
	 * @see android.app.Application#onCreate()
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		
		if(!AppSettings.applicationId.isEmpty() && !AppSettings.clientKey.isEmpty()){
			Parse.initialize((CatalogAppContext)getApplicationContext(), AppSettings.applicationId, AppSettings.clientKey);
			PushService.setDefaultPushCallback(this, MainActivity.class);
			ParseInstallation.getCurrentInstallation().saveEventually();
		}
	}
}
