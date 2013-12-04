package com.github.feedly.operations;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.foxykeep.datadroid.exception.ConnectionException;
import com.foxykeep.datadroid.exception.CustomRequestException;
import com.foxykeep.datadroid.exception.DataException;
import com.foxykeep.datadroid.network.NetworkConnection;
import com.foxykeep.datadroid.network.NetworkConnection.ConnectionResult;
import com.foxykeep.datadroid.network.NetworkConnection.Method;
import com.foxykeep.datadroid.requestmanager.Request;
import com.foxykeep.datadroid.service.RequestService.Operation;
import com.github.feedly.activities.AuthenticationFragment;
import com.github.feedly.util.RetrieveOAuth2TokenRequest;
import com.github.feedlyclient.R;

public class AuthentificationOperation implements Operation {

	String authorCode;
	
	public AuthentificationOperation() {
		
		authorCode = AuthenticationFragment.authorCode;
	}
	
	@Override
	public Bundle execute(Context context, Request request)
			throws ConnectionException, DataException, CustomRequestException {
		// TODO Auto-generated method stub
		
		RetrieveOAuth2TokenRequest webRequest = new RetrieveOAuth2TokenRequest(context, authorCode);
		NetworkConnection netConn = new NetworkConnection(context, webRequest.getEncodedUrl());
		netConn.setMethod(Method.POST);
		
		ConnectionResult result = netConn.execute();
		
		try {
			
			JSONObject json = new JSONObject(result.body);
			String accessToken = json.getString(getResourceString(context, R.string.feedly_api_access_token));
			String refreshToken = json.getString(getResourceString(context, R.string.feedly_api_refresh_token));
			String userId = json.getString(getResourceString(context, R.string.feedly_api_user_id));
			String expiresIn = json.getString(getResourceString(context, R.string.feedly_api_expires_in));
			String timestamp = Long.toString(System.currentTimeMillis()/1000);
			System.out.println(accessToken);
			saveToSharedPreferences(context, R.string.feedly_api_access_token, accessToken);
			saveToSharedPreferences(context, R.string.feedly_api_refresh_token, refreshToken);
			saveToSharedPreferences(context, R.string.feedly_api_user_id, userId);
			saveToSharedPreferences(context, R.string.feedly_api_expires_in, expiresIn);
			saveToSharedPreferences(context, R.string.feedly_api_timestamp, timestamp);
		}
		catch (JSONException e) {
            throw new DataException(e.getMessage());
        }
		/*try {
            
        	JSONArray feedlyJson = new JSONArray(result.body);
            feedlyValues = new ContentValues[feedlyJson.length()];
            
            for (int i = 0; i < feedlyJson.length(); ++i) {
                ContentValues feedly = new ContentValues();
                feedly.put("body", feedlyJson.getJSONObject(i).getString("label"));
                feedlyValues[i] = feedly;
            
            }
        } catch (JSONException e) {
            throw new DataException(e.getMessage());
        }*/
		
		/*ContentValues feedly = new ContentValues();
		feedly.put(FeedlyContract.AuthorPage.COLUMN_NAME_DATA, result.body);
		
		context.getContentResolver().delete(FeedlyContract.AuthorPage.CONTENT_URI, null, null);
        context.getContentResolver().insert(FeedlyContract.AuthorPage.CONTENT_URI, feedly);*/
		
		return null;
	}
	
	private void saveToSharedPreferences(Context context, int prefKeyId, String value)
	{
		SharedPreferences currentPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		Editor currentEditor = currentPreferences.edit();
		currentEditor.putString(getResourceString(context, prefKeyId), value);
		currentEditor.commit();
	}
	
	private String getResourceString(Context context, int resourceId)
	{
		return context.getResources().getString(resourceId);
	}

}
