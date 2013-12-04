package com.github.feedly.activities;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.widget.ListView;

import com.foxykeep.datadroid.requestmanager.Request;
import com.foxykeep.datadroid.requestmanager.RequestManager.RequestListener;
import com.github.feedly.provider.FeedlyContract;
import com.github.feedly.requests.FeedlyRequestManager;
import com.github.feedly.util.RequestFactory;
import com.github.feedlyclient.R;

public class RSSFeedActivity extends FragmentActivity
	implements LoaderCallbacks<Cursor>	{
	
	public final static int LOADER_ID = 1;
	private SimpleCursorAdapter adapter;
    ListView listView;
	private FeedlyRequestManager requestManager;
	
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
		
		//WebApiHelper.register(getApplicationContext());
		
		//FragmentManager manager = getSupportFragmentManager();
	    //FragmentTransaction transaction = manager.beginTransaction();
	    //transaction.replace(R.id.fragment_container, new AuthenticationFragment(), "auth_fragment");
	    //transaction.addToBackStack("auth_fragment");
	    //transaction.commit();
		
		listView = (ListView)findViewById(R.id.rssFeedListView);
		adapter = new SimpleCursorAdapter(this,
                R.layout.one_resource, 
                null, 
                new String[]{ FeedlyContract.Categories.COLUMN_NAME_CATEGORY_NAME },
                new int[]{ R.id.resourseInfo }, 
                0);
        listView.setAdapter(adapter);
        
        /*listView.setOnRefreshListener(new OnRefreshListener<ListView>() {

                @Override
                public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                        update();
                }
        });*/
        
        //getSupportLoaderManager().initLoader(LOADER_ID, null, loaderCallbacks);
        
        requestManager = FeedlyRequestManager.from(this);
	}
	
	@Override
    public void onStart() {
            
            super.onStart();
            getSupportLoaderManager().initLoader(LOADER_ID, null, this);
    }

	public void update() {
		
		//listView.setRefreshing();
        Request updateRequest = new Request(RequestFactory.REQUEST_CATEGORIES);
        //updateRequest.put("screen_name", "habrahabr");
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
			FeedlyContract.Categories._ID,
			FeedlyContract.Categories.COLUMN_NAME_CATEGORY_NAME
		};
		return new CursorLoader(RSSFeedActivity.this, FeedlyContract.Categories.CONTENT_URI,
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
}
