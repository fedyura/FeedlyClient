package com.github.feedly.util;

import android.content.Context;

import com.github.feedlyclient.R;

public class GetBestArticles extends WebApiRequest {
	
	public GetBestArticles(Context context, String streamId)
	{
		super(context.getResources().getString(R.string.feedly_api_url), "GET", context);
		setMethod(R.string.feedly_api_get_best_articles);
		addParam(R.string.stream_par, streamId);
	}
}
