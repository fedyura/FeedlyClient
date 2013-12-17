package com.github.feedly.operations;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;

import com.foxykeep.datadroid.exception.ConnectionException;
import com.foxykeep.datadroid.exception.CustomRequestException;
import com.foxykeep.datadroid.exception.DataException;
import com.foxykeep.datadroid.network.NetworkConnection;
import com.foxykeep.datadroid.network.NetworkConnection.ConnectionResult;
import com.foxykeep.datadroid.requestmanager.Request;
import com.foxykeep.datadroid.service.RequestService.Operation;
import com.github.feedly.activities.RSSFeedActivity;
import com.github.feedly.provider.FeedlyContract;
import com.github.feedly.util.GetFeedlyCategories;

public final class GetCategoryOperation implements Operation {

	String categoryCode;
	String numberFeeds;
	
	public GetCategoryOperation() {
		
		categoryCode = RSSFeedActivity.curFeed;
		numberFeeds = RSSFeedActivity.numberFeeds;
	}
	
	@Override
	public Bundle execute(Context context, Request request)
			throws ConnectionException, DataException, CustomRequestException {
		// TODO Auto-generated method stub
		
		System.out.println("I was here");
		GetFeedlyCategories feedlyCat = new GetFeedlyCategories(context, categoryCode, numberFeeds);
		NetworkConnection netConn = new NetworkConnection(context, feedlyCat.getEncodedUrl());
		
		System.out.println(feedlyCat.getOAuthToken());
		System.out.println(feedlyCat.getEncodedUrl());
		
		HashMap<String, String> params = new HashMap<String, String>();
        params.put("Authorization ", "OAuth " + feedlyCat.getOAuthToken());
        netConn.setParameters(params);
		
		ConnectionResult result = netConn.execute();
		ContentValues[] feedlyValues;
		
		try {
            JSONObject answerJson = new JSONObject(result.body);
            JSONArray feedlyJson = answerJson.getJSONArray("results");
        	
        	feedlyValues = new ContentValues[feedlyJson.length()];
            
        	for (int i = 0; i < feedlyJson.length(); ++i) {
                ContentValues feedly = new ContentValues();
                feedly.put(FeedlyContract.Feeds.COLUMN_NAME_TITLE, feedlyJson.getJSONObject(i).getString("title"));
                feedly.put(FeedlyContract.Feeds.COLUMN_NAME_WEBSITE, feedlyJson.getJSONObject(i).getString("website"));
                feedly.put(FeedlyContract.Feeds.COLUMN_NAME_FEEDID, feedlyJson.getJSONObject(i).getString("feedId"));
                feedly.put(FeedlyContract.Feeds.COLUMN_NAME_KEYWORD_NAME, "apple");
                feedlyValues[i] = feedly;
            }
        } catch (JSONException e) {
            throw new DataException(e.getMessage());
        }
        
        context.getContentResolver().delete(FeedlyContract.Feeds.CONTENT_URI, null, null);
        context.getContentResolver().bulkInsert(FeedlyContract.Feeds.CONTENT_URI, feedlyValues);
        return null;
	}
}
