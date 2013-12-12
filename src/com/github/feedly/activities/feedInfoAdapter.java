package com.github.feedly.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.feedly.provider.FeedlyContract;
import com.github.feedly.provider.FeedlyContract.Feeds;
import com.github.feedlyclient.R;

public class feedInfoAdapter extends SimpleCursorAdapter {
	
	public static String MSGWithFeedId = "com.github.feedlyclient.FeedIdMsg";
	Context mContext;
	
	public feedInfoAdapter(Context context) {
		super(context, R.layout.one_resource, 
                null, 
                new String[]{ FeedlyContract.Feeds.COLUMN_NAME_TITLE },
                new int[]{ R.id.resourseInfo }, 
                0);
		mContext = context;
	}
	
	@Override
	public View getView( int position, View convertView, ViewGroup parent ) {
		
		TextView feedName;
		Button bestArticles;
		Cursor cursor = (Cursor) getItem(position);
		final String feedId = cursor.getString(cursor.getColumnIndex(Feeds.COLUMN_NAME_FEEDID));
		
		if (convertView == null) {
			
			convertView = LayoutInflater.from(mContext).inflate(R.layout.one_resource, null);
			feedName = (TextView) convertView.findViewById(R.id.resourseInfo);
			bestArticles = (Button) convertView.findViewById(R.id.bestMat);
			
			bestArticles.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(mContext, BestFeedsActivity.class);
					intent.putExtra(MSGWithFeedId, feedId);
					mContext.startActivity(intent);
				}
			});
			
		}
		else {
			
			feedName = (TextView) convertView.findViewById(R.id.resourseInfo);
			bestArticles = (Button) convertView.findViewById(R.id.bestMat);
		}
		
		feedName.setText(cursor.getString(cursor.getColumnIndex(Feeds.COLUMN_NAME_TITLE)));
		return  convertView;
	}
}
