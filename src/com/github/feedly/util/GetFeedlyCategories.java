package com.github.feedly.util;

import android.content.Context;

import com.github.feedlyclient.R;

public class GetFeedlyCategories extends WebApiRequest {
	
	public GetFeedlyCategories(Context context)
	{
		super(context.getResources().getString(R.string.feedly_api_url), "GET", context);
		setMethod(R.string.feedly_api_get_categories);
	}
}
