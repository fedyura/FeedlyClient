package com.github.feedly.provider;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

public final class FeedlyContract {
	
	public static final String AUTHORITY = "com.github.kr.feedly.FeedlyProvider";
	public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);
	
	private static final String TEXT_TYPE = " TEXT";
    //private static final String INTEGER_TYPE = " INTEGER";
    
    //private static final String COMMA_SEP = ",";
    
	public static final class Categories implements BaseColumns {
		
		public static final String CONTENT_PATH = "categories";
		public static final String TABLE_NAME = "Categories";
		public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, TABLE_NAME);
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + CONTENT_PATH;
		
		public static final String COLUMN_NAME_CATEGORY_NAME = "name";
		
		private static final String SQL_CREATE_CATEGORIES =
				"CREATE TABLE " + Categories.TABLE_NAME + " (" +
                Categories._ID + " INTEGER PRIMARY KEY," +
                Categories.COLUMN_NAME_CATEGORY_NAME + TEXT_TYPE + 
                " )";
		
		private static final String SQL_DELETE_CATEGORIES = 
                "DROP TABLE IF EXISTS " + Categories.TABLE_NAME;

		public static void onCreate(SQLiteDatabase db) {
        
				db.execSQL(SQL_CREATE_CATEGORIES);
		}

		public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        
				db.execSQL(SQL_DELETE_CATEGORIES);
		}
	}
	
	public static final class AuthorPage implements BaseColumns {
		
		public static final String CONTENT_PATH = "author_page";
		public static final String TABLE_NAME = "AuthorPage";
		public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, TABLE_NAME);
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + CONTENT_PATH;
        
        public static final String COLUMN_NAME_DATA = "data";
        
        private static final String SQL_CREATE_AUTHORPAGE =
				"CREATE TABLE " + AuthorPage.TABLE_NAME + " (" +
				AuthorPage._ID + " INTEGER PRIMARY KEY," +
				AuthorPage.COLUMN_NAME_DATA + TEXT_TYPE + 
                " )";
		
		private static final String SQL_DELETE_AUTHORPAGE = 
                "DROP TABLE IF EXISTS " + AuthorPage.TABLE_NAME;

		public static void onCreate(SQLiteDatabase db) {
        
				db.execSQL(SQL_CREATE_AUTHORPAGE);
		}

		public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        
				db.execSQL(SQL_DELETE_AUTHORPAGE);
		}
	}
}
