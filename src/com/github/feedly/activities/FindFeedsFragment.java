package com.github.feedly.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.github.feedly.provider.FeedlyContract.Feeds;
import com.github.feedlyclient.R;

public class FindFeedsFragment extends ListFragment {
	
	public FindFeedsFragment(Context context){
        setListAdapter(new feedInfoAdapter(context));
        System.out.println("5");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		System.out.println("6");
		return inflater.inflate(R.layout.find_feeds_fragment, container, false);
		
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id){
        
		System.out.println("7");
		Cursor cursor = (Cursor) ((SimpleCursorAdapter)getListAdapter()).getItem(position);
    	String website = cursor.getString(cursor.getColumnIndex(Feeds.COLUMN_NAME_WEBSITE));
    	startWebsiteActivity(website);
		
		/*Cursor cursor = (Cursor) ((GroupListAdapter)getListAdapter()).getItem(position);
        int _id = cursor.getInt(cursor.getColumnIndex(Contract._ID));
        String name = cursor.getString(cursor.getColumnIndex(Contract.COLUMN_GROUPS_NAME));
        int amount = cursor.getInt(cursor.getColumnIndex(Contract.COLUMN_GROUPS_AMOUNT));

        ((MainActivity)getActivity()).dialog = new GroupDialog(new Group(_id, name, amount));
        ((MainActivity)getActivity()).dialog.show(getFragmentManager(), null);*/
	}
	
	public void startWebsiteActivity(String url) {
		
		Intent intent = new Intent(getActivity(), ShowWebsiteActivity.class);
    	intent.putExtra(RSSFeedActivity.MSG_TO_WEBSITE_ACTIVITY, url);
        startActivity(intent);
	}
}
