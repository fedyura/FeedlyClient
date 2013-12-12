package com.github.feedly.provider;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

public final class FeedlyContract {
	
	public static final String AUTHORITY = "com.github.kr.feedly.FeedlyProvider";
	public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);
	
	private static final String TEXT_TYPE = " TEXT";
    //private static final String INTEGER_TYPE = " INTEGER";
    
    private static final String COMMA_SEP = ",";
    
	public static final class Feeds implements BaseColumns {
		
		public static final String CONTENT_PATH = "feeds";
		public static final String TABLE_NAME = "Feeds";
		public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, TABLE_NAME);
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + CONTENT_PATH;
		
		public static final String COLUMN_NAME_KEYWORD_NAME = "name";
		public static final String COLUMN_NAME_TITLE = "title";
		public static final String COLUMN_NAME_WEBSITE = "website";
		public static final String COLUMN_NAME_FEEDID = "feedid";
		
		private static final String SQL_CREATE_CATEGORIES =
				"CREATE TABLE " + Feeds.TABLE_NAME + " (" +
                Feeds._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                Feeds.COLUMN_NAME_KEYWORD_NAME + TEXT_TYPE + COMMA_SEP +
                Feeds.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                Feeds.COLUMN_NAME_WEBSITE + TEXT_TYPE + COMMA_SEP +
                Feeds.COLUMN_NAME_FEEDID + TEXT_TYPE +
                " )";
		
		private static final String SQL_DELETE_CATEGORIES = 
                "DROP TABLE IF EXISTS " + Feeds.TABLE_NAME;

		public static void onCreate(SQLiteDatabase db) {
        
				db.execSQL(SQL_CREATE_CATEGORIES);
		}

		public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        
				db.execSQL(SQL_DELETE_CATEGORIES);
		}
	}
	
	public static final class Articles implements BaseColumns {
		
		public static final String CONTENT_PATH = "articles";
		public static final String TABLE_NAME = "Articles";
		public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, TABLE_NAME);
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + CONTENT_PATH;
		
		public static final String COLUMN_NAME_TITLE = "title";
		public static final String COLUMN_NAME_REFERENCE = "website";
		public static final String COLUMN_NAME_KEYWORD_NAME = "name";
		
		private static final String SQL_CREATE_ARTICLES =
				"CREATE TABLE " + Articles.TABLE_NAME + " (" +
                Articles._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                Articles.COLUMN_NAME_KEYWORD_NAME + TEXT_TYPE + COMMA_SEP +
                Articles.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                Articles.COLUMN_NAME_REFERENCE + TEXT_TYPE +
                " )";
		
		private static final String SQL_DELETE_ARTICLES = 
                "DROP TABLE IF EXISTS " + Articles.TABLE_NAME;

		public static void onCreate(SQLiteDatabase db) {
        
				db.execSQL(SQL_CREATE_ARTICLES);
		}

		public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        
				db.execSQL(SQL_DELETE_ARTICLES);
		}
	}
}
