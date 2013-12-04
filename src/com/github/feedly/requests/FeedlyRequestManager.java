package com.github.feedly.requests;

import android.content.Context;

import com.foxykeep.datadroid.requestmanager.RequestManager;
import com.github.feedly.service.FeedlyService;

public class FeedlyRequestManager extends RequestManager {
	
	private FeedlyRequestManager(Context context) {
        super(context, FeedlyService.class);
	}

	private static FeedlyRequestManager sInstance;

	public static FeedlyRequestManager from(Context context) {
        if (sInstance == null) {
                sInstance = new FeedlyRequestManager(context);
        }
        return sInstance;
	}
}
