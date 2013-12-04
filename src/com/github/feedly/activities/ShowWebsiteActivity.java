package com.github.feedly.activities;

import java.net.URL;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.feedlyclient.R;

public class ShowWebsiteActivity extends FragmentActivity {

	private WebChromeClient getWebChromeClient()
	{
		return new WebChromeClient()
		{
			public void onProgressChanged(WebView view, int progress)
			{
				if(this != null)
				{
					setProgress(progress * 100);
				}
			}
		};
	}
	
	public WebViewClient getWebViewClient()
	{
		final ProgressDialog dialog = new ProgressDialog(this);
		dialog.setTitle("Loading Website... Please Wait");
		return new WebViewClient()
		{
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon)
			{
				super.onPageStarted(view, url, favicon);
				dialog.show();
			}
			
			@Override
			public void onPageFinished(WebView view, String url)
			{
				super.onPageFinished(view, url);
				dialog.hide();
			}
		};
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_website);
		
		Intent intent = getIntent();
        String message = intent.getStringExtra(RSSFeedActivity.MSG_TO_WEBSITE_ACTIVITY);
        initializeViews(message, savedInstanceState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_website, menu);
		return true;
	}
	
	@SuppressLint("SetJavaScriptEnabled")
	private void initializeViews(String url_address, Bundle savedInstanceState)
	{
		if(savedInstanceState == null && this != null)
		{
			boolean isTablet = true;
			WebSettings.ZoomDensity zoomDensity = isTablet ? WebSettings.ZoomDensity.MEDIUM : WebSettings.ZoomDensity.FAR;

			WebView description = (WebView)findViewById(R.id.show_site);
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
			
			description.loadUrl(url_address);
		}
	}

}
