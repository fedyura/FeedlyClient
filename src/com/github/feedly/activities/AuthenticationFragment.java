package com.github.feedly.activities;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.foxykeep.datadroid.requestmanager.Request;
import com.foxykeep.datadroid.requestmanager.RequestManager.RequestListener;
import com.github.feedly.requests.FeedlyRequestManager;
import com.github.feedly.util.GetFeedlyCodeRequest;
import com.github.feedly.util.RequestFactory;
import com.github.feedlyclient.R;

public class AuthenticationFragment extends Fragment {
	
	public final static int LOADER_ID = 1;
	public static String authorCode;
	
	public ProgressDialog authenticationDialog;
	private FeedlyRequestManager requestManager;
	
	RequestListener requestListener = new RequestListener() {
		
		void showError() {
	        
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	        builder.
	                setTitle(android.R.string.dialog_alert_title).
	                setMessage(getString(R.string.faled_to_load_data)).
	                create().
	                show();
		}
		
		@Override
		public void onRequestFinished(Request request, Bundle resultData) {
			// TODO Auto-generated method stub
			showError();
		}

		@Override
		public void onRequestConnectionError(Request request, int statusCode) {
			// TODO Auto-generated method stub
			showError();
		}

		@Override
		public void onRequestDataError(Request request) {
			// TODO Auto-generated method stub
			showError();
		}

		@Override
		public void onRequestCustomError(Request request, Bundle resultData) {
			// TODO Auto-generated method stub
			//showError();
		}
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.feedly_oauth2_browser, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		requestManager = FeedlyRequestManager.from(getActivity());
		initializeViews(savedInstanceState);
	}

	private WebChromeClient getWebChromeClient()
	{
		return new WebChromeClient()
		{
			public void onProgressChanged(WebView view, int progress)
			{
				if(getActivity() != null)
				{
					getActivity().setProgress(progress * 100);
				}
			}
		};
	}
	
	public WebViewClient getWebViewClient()
	{
		final ProgressDialog dialog = new ProgressDialog(getActivity());
		dialog.setTitle("Loading Website... Please Wait");
		return new WebViewClient()
		{
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon)
			{
				super.onPageStarted(view, url, favicon);
				updateProgressBarVisibility(true);
				dialog.show();
			}
			
			@Override
			public void onPageFinished(WebView view, String url)
			{
				super.onPageFinished(view, url);
				updateProgressBarVisibility(false);
				dialog.hide();
			}
			
			public boolean shouldOverrideUrlLoading(WebView view, String url)
			{
				
				if (url.startsWith(getResourceString(R.string.feedly_redirect_uri))) {
					
					String code = getCodeFromUrl(url);
					if (code == null)
					{
						return false;
					}
					
					authorCode = code;
					Request updateRequest = new Request(RequestFactory.REQUEST_AUTHENTIFICATION);
			        requestManager.execute(updateRequest, requestListener);
			        
			        Intent intent = new Intent(getActivity(), RSSFeedActivity.class);
			    	startActivity(intent);
			        return true;
				}
				//saveFeedlyTokensFromResponseToPreferences(response);
				/*OnApiRequestListener listener = new OnApiRequestListener() {
					
					@Override
					public void onStartRequest()
					{
						showAuthenticationDialog("Loading...");
					}
					
					@Override
					public void onFinishRequest(String response)
					{
						if(WebApiHelper.getInstance().saveFeedlyTokensFromResponseToPreferences(response))
						{
							// do something
						}
						hideAuthenticationDialog();
					}
					
					@Override
					public void onException(Exception ex)
					{
						
					}
				};
				if (WebApiHelper.getInstance().handleFeedlyAuthenticationResponse(url, listener))
				{
					return true;
				}*/
        		return super.shouldOverrideUrlLoading(view, url);
        	}
		};
	}
	
	@SuppressLint("SetJavaScriptEnabled")
	private void initializeViews(Bundle savedInstanceState)
	{
		if(savedInstanceState == null && getActivity() != null)
		{
			boolean isTablet = true;
			WebSettings.ZoomDensity zoomDensity = isTablet ? WebSettings.ZoomDensity.MEDIUM : WebSettings.ZoomDensity.FAR;

			WebView description = (WebView)getView().findViewById(R.id.description);
			description.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
			description.getSettings().setJavaScriptEnabled(true);
			description.getSettings().setDefaultTextEncodingName("utf-8");
			description.getSettings().setLoadWithOverviewMode(true);
			description.getSettings().setDefaultZoom(zoomDensity);
			description.getSettings().setSupportZoom(true);
			description.getSettings().setBuiltInZoomControls(true);
			description.requestFocus(View.FOCUS_DOWN);
			description.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
			description.getSettings().setUseWideViewPort(isTablet);
			description.setWebChromeClient(this.getWebChromeClient());
			description.setWebViewClient(this.getWebViewClient());
			
			GetFeedlyCodeRequest request = new GetFeedlyCodeRequest(getActivity());
			description.loadUrl(request.getEncodedUrl());
		}
	}
	
	private void updateProgressBarVisibility(boolean visible)
	{
		if(this.getActivity() != null)
		{
			this.getActivity().setProgressBarVisibility(visible);
		}
	}
	
	public boolean saveFeedlyTokensFromResponseToPreferences(String response)
	{
		try
		{
			JSONObject json = new JSONObject(response);
			String accessToken = json.getString(getResourceString(R.string.feedly_api_access_token));
			String refreshToken = json.getString(getResourceString(R.string.feedly_api_refresh_token));
			String userId = json.getString(getResourceString(R.string.feedly_api_user_id));
			String expiresIn = json.getString(getResourceString(R.string.feedly_api_expires_in));
			String timestamp = Long.toString(System.currentTimeMillis()/1000);
			saveToSharedPreferences(R.string.feedly_api_access_token, accessToken);
			saveToSharedPreferences(R.string.feedly_api_refresh_token, refreshToken);
			saveToSharedPreferences(R.string.feedly_api_user_id, userId);
			saveToSharedPreferences(R.string.feedly_api_expires_in, expiresIn);
			saveToSharedPreferences(R.string.feedly_api_timestamp, timestamp);
			return true;

		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	private void saveToSharedPreferences(int prefKeyId, String value)
	{
		SharedPreferences currentPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
		Editor currentEditor = currentPreferences.edit();
		currentEditor.putString(getResourceString(prefKeyId), value);
		currentEditor.commit();
	}
	
	private String getResourceString(int resourceId)
	{
		return getActivity().getResources().getString(resourceId);
	}

	private String getCodeFromUrl(String url)
    {
    	try
    	{
			List<NameValuePair> params = URLEncodedUtils.parse(new URI(url), "UTF-8");
			String paramName = getResourceString(R.string.feedly_api_param_code);
	    	for (NameValuePair param : params)
	    	{
	    		if (param.getName().equals(paramName))
	    		{
	    			return param.getValue();
	    		}
	    	}
		}
    	catch (URISyntaxException e)
    	{
			// TODO do something??
		}
    	return null;
    }
}
