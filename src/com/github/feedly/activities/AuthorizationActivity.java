package com.github.feedly.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;

import com.github.feedlyclient.R;

public class AuthorizationActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_authorization);
		
		FragmentManager manager = getSupportFragmentManager();
	    FragmentTransaction transaction = manager.beginTransaction();
	    transaction.replace(R.id.fragment_container, new AuthenticationFragment(), "auth_fragment");
	    transaction.addToBackStack("auth_fragment");
	    transaction.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.authorization, menu);
		return true;
	}
}
