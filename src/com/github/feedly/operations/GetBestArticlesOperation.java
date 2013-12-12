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
import com.github.feedly.activities.BestFeedsActivity;
import com.github.feedly.provider.FeedlyContract;
import com.github.feedly.util.GetBestArticles;

public final class GetBestArticlesOperation implements Operation {

	String streamId;
	
	public GetBestArticlesOperation() {
		
		streamId = BestFeedsActivity.curFeedId;
	}
	
	@Override
	public Bundle execute(Context context, Request request)
			throws ConnectionException, DataException, CustomRequestException {
		// TODO Auto-generated method stub
		
		GetBestArticles feedlyCat = new GetBestArticles(context, streamId);
		NetworkConnection netConn = new NetworkConnection(context, feedlyCat.getEncodedUrl());
		
		System.out.println(feedlyCat.getEncodedUrl());
		HashMap<String, String> params = new HashMap<String, String>();
        params.put("Authorization ", "OAuth " + feedlyCat.getOAuthToken());
        netConn.setParameters(params);
		
		ConnectionResult result = netConn.execute();
		ContentValues[] feedlyValues;
		System.out.println(result.body);
		
		try {
            JSONObject answerJson = new JSONObject(result.body);
            //System.out.println("I am here");
            JSONArray feedlyJson = answerJson.getJSONArray("items");
        	
        	feedlyValues = new ContentValues[feedlyJson.length()];
            //System.out.println("I was here");
        	for (int i = 0; i < feedlyJson.length(); ++i) {
                ContentValues feedly = new ContentValues();
                //System.out.println(feedlyJson.getJSONObject(i).getString("title"));
                //System.out.println(feedlyJson.getJSONObject(i).getString("originId"));
                //System.out.println(feedlyJson.getJSONObject(i).getString("title"));
                feedly.put(FeedlyContract.Articles.COLUMN_NAME_TITLE, feedlyJson.getJSONObject(i).getString("title"));
                feedly.put(FeedlyContract.Articles.COLUMN_NAME_REFERENCE, feedlyJson.getJSONObject(i).getString("originId"));
                feedly.put(FeedlyContract.Articles.COLUMN_NAME_KEYWORD_NAME, streamId);
                feedlyValues[i] = feedly;
            }
        } catch (JSONException e) {
            throw new DataException(e.getMessage());
        }
        
        context.getContentResolver().delete(FeedlyContract.Articles.CONTENT_URI, null, null);
        context.getContentResolver().bulkInsert(FeedlyContract.Articles.CONTENT_URI, feedlyValues);
        return null;
	}
}
