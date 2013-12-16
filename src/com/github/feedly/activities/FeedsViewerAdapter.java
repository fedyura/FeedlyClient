package com.github.feedly.activities;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class FeedsViewerAdapter extends FragmentPagerAdapter {
	
	private static final int count = 1;
	private final FindFeedsFragment findFragment;
	
	public FeedsViewerAdapter(Context context, FragmentManager fm) {
        super(fm);
        // TODO Auto-generated constructor stub
        findFragment = new FindFeedsFragment(context);
        System.out.println("11");
	}
	
	@Override
    public Fragment getItem(int position) {
            // TODO Auto-generated method stub
		System.out.println("10");    
		System.out.println(position);
		switch (position) {
            	case 0:
                    return findFragment;
            	/*
            	case 1:
                    return groupsFragment;
                */
            	default:
                    return null;
            }
    }

    @Override
    public int getCount() {
            // TODO Auto-generated method stub
            return count;
    }

    public feedInfoAdapter getFeedInfoAdapter(){
            
    	System.out.println("8");
    	return (feedInfoAdapter) (findFragment.getListView()).getAdapter();
    }

    /*
    public GroupListAdapter getGroupsAdapter(){
            return (GroupListAdapter) (groupsFragment.getListView()).getAdapter();
    }
    */
}
