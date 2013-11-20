package com.github.feedly.operations;

import org.json.JSONArray;
import org.json.JSONException;

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
import com.github.feedly.provider.FeedlyContract;

public final class FeedlyOperation implements Operation {

	@Override
	public Bundle execute(Context context, Request request)
			throws ConnectionException, DataException, CustomRequestException {
		// TODO Auto-generated method stub
		NetworkConnection netConn = new NetworkConnection(context, "http://cloud.feedly.com/v3/categories");
		
		//HashMap<String, String> params = new HashMap<String, String>();
        //params.put("screen_name", request.getString("screen_name"));
        //netConn.setParameters(params);
        ConnectionResult result = netConn.execute();
        ContentValues[] feedlyValues;
        
        try {
            JSONArray feedlyJson = new JSONArray(result.body);
            feedlyValues = new ContentValues[feedlyJson.length()];
            
            for (int i = 0; i < feedlyJson.length(); ++i) {
                ContentValues feedly = new ContentValues();
                feedly.put("body", feedlyJson.getJSONObject(i).getString("label"));
                feedlyValues[i] = feedly;
            }
        } catch (JSONException e) {
            throw new DataException(e.getMessage());
        }
        
        context.getContentResolver().delete(FeedlyContract.Categories.CONTENT_URI, null, null);
        context.getContentResolver().bulkInsert(FeedlyContract.Categories.CONTENT_URI, feedlyValues);
        return null;
	}
	
	

	
}
