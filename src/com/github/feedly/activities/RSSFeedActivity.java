package com.github.feedly.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.foxykeep.datadroid.requestmanager.Request;
import com.foxykeep.datadroid.requestmanager.RequestManager.RequestListener;
import com.github.feedly.provider.FeedlyContract;
import com.github.feedly.provider.FeedlyContract.Feeds;
import com.github.feedly.requests.FeedlyRequestManager;
import com.github.feedly.util.RequestFactory;
import com.github.feedlyclient.R;

public class RSSFeedActivity extends FragmentActivity
	implements LoaderCallbacks<Cursor>	{
	
	public final static int LOADER_ID = 1;
	private SimpleCursorAdapter adapter;
    ListView listView;
	private FeedlyRequestManager requestManager;
	public final static String MSG_TO_WEBSITE_ACTIVITY = "com.github.feedly.MSG_TO_WEBSITE_ACTIVITY";
	public static String curFeed;
	
	RequestListener requestListener = new RequestListener() {
		
		void showError() {
	        
			AlertDialog.Builder builder = new AlertDialog.Builder(RSSFeedActivity.this);
	        builder.
	                setTitle(android.R.string.dialog_alert_title).
	                setMessage(getString(R.string.faled_to_load_data)).
	                create().
	                show();
		}
		
		@Override
		public void onRequestFinished(Request request, Bundle resultData) {
			// TODO Auto-generated method stub
			
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
			showError();
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rssfeed);
		
		listView = (ListView)findViewById(R.id.rssFeedListView);
		adapter = new SimpleCursorAdapter(this,
                R.layout.one_resource, 
                null, 
                new String[]{ FeedlyContract.Feeds.COLUMN_NAME_TITLE },
                new int[]{ R.id.resourseInfo }, 
                0);
        listView.setAdapter(adapter);
        
        listView.setOnItemClickListener(new OnItemClickListener() {
                  
        	public void onItemClick(AdapterView<?> parent, View view,
                                int position, long id) {
        	             
        	Cursor cursor = (Cursor) ((SimpleCursorAdapter)parent.getAdapter()).getItem(position);
        	String website = cursor.getString(cursor.getColumnIndex(Feeds.COLUMN_NAME_WEBSITE));
        	startWebsiteActivity(website);
        	}
        });
        
        requestManager = FeedlyRequestManager.from(this);
	}
	
	public void startWebsiteActivity(String url) {
		
		Intent intent = new Intent(this, ShowWebsiteActivity.class);
    	intent.putExtra(MSG_TO_WEBSITE_ACTIVITY, url);
        startActivity(intent);
	}
	
	@Override
    public void onStart() {
            
            super.onStart();
            getSupportLoaderManager().initLoader(LOADER_ID, null, this);
    }

	public void update() {
		
		Request updateRequest = new Request(RequestFactory.REQUEST_CATEGORIES);
        requestManager.execute(updateRequest, requestListener);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rssfeed, menu);
		return true;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		// TODO Auto-generated method stub
		final String[] projection = { 
			FeedlyContract.Feeds._ID,
			FeedlyContract.Feeds.COLUMN_NAME_TITLE,
			FeedlyContract.Feeds.COLUMN_NAME_WEBSITE
		};
		
		return new CursorLoader(RSSFeedActivity.this, FeedlyContract.Feeds.CONTENT_URI,
								projection, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
		// TODO Auto-generated method stub
		adapter.swapCursor(cursor);
		if (cursor.getCount() == 0) {
            update();
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> cursor) {
		// TODO Auto-generated method stub
		adapter.swapCursor(null);
	}
	
	public void findFeeds(View view) {
		
		EditText edText = (EditText) findViewById(R.id.topicEditText);
		curFeed = edText.getText().toString();
		update();
	}
}
