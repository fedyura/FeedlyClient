package com.github.feedly.util;

import com.foxykeep.datadroid.requestmanager.Request;

public final class RequestFactory {
	
	public static final int REQUEST_CATEGORIES = 1;
    
    public static Request getTweetsRequest(String screenName) {
            Request request = new Request(REQUEST_CATEGORIES);
            return request;
    }
    
    private RequestFactory() {
    }

}
