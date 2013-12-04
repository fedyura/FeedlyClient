package com.github.feedly.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class FeedlyProvider extends ContentProvider {
	
	private FeedlyDBHelper fDBHelper;
	
	private static final int FEEDS = 0;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(FeedlyContract.AUTHORITY, FeedlyContract.Feeds.TABLE_NAME, FEEDS);
    }
	
    @Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
    	switch (uriMatcher.match(uri)) {
        	case FEEDS:
                return fDBHelper.getWritableDatabase().delete(FeedlyContract.Feeds.TABLE_NAME, selection, selectionArgs);
        	default:
                return 0;
        }
	}
	
    @Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
    	switch (uriMatcher.match(uri)) {
        	case FEEDS:
                return FeedlyContract.Feeds.CONTENT_TYPE;
        	default:
                return null;
        }
	}
	
    @Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
    	switch (uriMatcher.match(uri)) {
        	case FEEDS: {
                fDBHelper.getWritableDatabase().insert(FeedlyContract.Feeds.TABLE_NAME, null, values);
                getContext().getContentResolver().notifyChange(FeedlyContract.Feeds.CONTENT_URI, null);
        	}
        	default:
                return null;
        }
	}
	
    @Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		fDBHelper = new FeedlyDBHelper(getContext());
    	return false;
	}
	
    @Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
			String sortOrder) {
		// TODO Auto-generated method stub
    	String tableName;

        int uriType = uriMatcher.match(uri);
        switch (uriType) {    
        	case FEEDS:
                tableName = FeedlyContract.Feeds.TABLE_NAME;
                break;
        	default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        SQLiteDatabase db = fDBHelper.getReadableDatabase();
        Cursor c = db.query(tableName, projection, selection, selectionArgs, null, null, sortOrder);
        
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
	}
	
    @Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
    	SQLiteDatabase db = fDBHelper.getWritableDatabase();
        int count= 0;
        switch (uriMatcher.match(uri)) {
        	case FEEDS:
                count = db.update(FeedlyContract.Feeds.TABLE_NAME, values, selection, selectionArgs);
        	    break;
        	default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
	}
}
