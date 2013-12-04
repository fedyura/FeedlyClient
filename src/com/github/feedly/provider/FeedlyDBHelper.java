package com.github.feedly.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.github.feedly.provider.FeedlyContract.Feeds;

public class FeedlyDBHelper extends SQLiteOpenHelper {
	
	public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Feedly.db";
    
    public FeedlyDBHelper(Context context) {
        
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
            
        Feeds.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            
    	Feeds.onUpgrade(db, oldVersion, newVersion);
    	onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    
        onUpgrade(db, oldVersion, newVersion);
    }
    
    @Override
    public void close(){
    
    	getReadableDatabase().close();
    	getWritableDatabase().close();
    	super.close();
    }
}
