package com.github.feedly.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.feedlyclient.R;

public class MainFragment extends Fragment {
	
	 private final PagerAdapter pagerAdapter;
	 
	 public MainFragment(PagerAdapter pagerAdapter) {
		 System.out.println("3");
		 this.pagerAdapter = pagerAdapter;
	 }

	 @Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
                 Bundle savedInstanceState){
         System.out.println("In onCreateView");
		 View view = inflater.inflate(R.layout.activity_view_pager, null);

         ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager);
         viewPager.setAdapter(pagerAdapter);
         System.out.println("4");
         return view;
	 }
}
