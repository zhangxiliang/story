package com.wole.story.ui;

import com.umeng.analytics.MobclickAgent;
import com.wole.story.app.StoryApplication;
import com.wole.story.config.StorySetting;
import com.wole.story.view.LoadingWindow;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout.LayoutParams;
import android.widget.RelativeLayout;

public class BaseActivity extends FragmentActivity {
	protected FragmentManager mFragmentManager;

	private View mLoadingView;
	protected Activity mActivity;
	protected LoadingWindow mLoadingWindow;
	protected StorySetting mStorySetting;
	protected StoryApplication mStoryApplication;
	protected FragmentTransaction mFragmentTransaction;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		mActivity = this;
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		mLoadingWindow=new LoadingWindow(this);
		mStoryApplication=StoryApplication.getInstance();
		mFragmentManager = this.getSupportFragmentManager();
		mStorySetting=StorySetting.getInstance();
		IntentFilter filter = new IntentFilter();
		filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		filter.addAction("finish");
		registerReceiver(mFinishReceiver, filter);

	}


	
	private BroadcastReceiver mFinishReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if ("finish".equals(intent.getAction())) {
				Log.e("#########", "I am " + getLocalClassName()
						+ ",now finishing myself...");
				finish();
			}
		}
	};

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		this.unregisterReceiver(mFinishReceiver);
	}

	protected void showLoadingView() {
		mLoadingView=getWindow().getDecorView().findViewById(R.id.loading_rl);
		mLoadingView.setVisibility(View.VISIBLE);
	
	}

	protected void hiddenLoadingView() {
		mLoadingView=getWindow().getDecorView().findViewById(R.id.loading_rl);
		mLoadingView.setVisibility(View.GONE);
		
	}
	protected void addFragment(int containerId, Fragment fragment) {

		addFragment(containerId, fragment, null);
	}

	protected void  addFragmentNotCommit(int containerId,Fragment fragment) {
		FragmentTransaction mFragmentTransaction = mFragmentManager
				.beginTransaction();
		mFragmentTransaction.add(containerId, fragment);
	}
	
	protected void addFragment(int containerId, Fragment fragment, String tag) {
		if (null == fragment) {
			return;
		}

		FragmentTransaction mFragmentTransaction = mFragmentManager
				.beginTransaction();
		mFragmentTransaction.add(containerId, fragment, tag);
		mFragmentTransaction.addToBackStack(null);
		mFragmentTransaction.commitAllowingStateLoss();

	}

	protected void removeFragment(Fragment fragment) {
		if (null != fragment && fragment.isAdded()) {
			FragmentTransaction mFragmentTransaction = mFragmentManager
					.beginTransaction();
			mFragmentTransaction.remove(fragment);
			mFragmentTransaction.commitAllowingStateLoss();
		}
	}

	protected void replaceFragment(int containerId, Fragment fragment) {

		if (fragment != null && !fragment.isAdded()) {
			FragmentTransaction mFragmentTransaction = mFragmentManager
					.beginTransaction();
			
			mFragmentTransaction.replace(containerId, fragment);
			mFragmentTransaction.addToBackStack(null);
			mFragmentTransaction.commitAllowingStateLoss();
		}
	}

	protected void hidenFragment(Fragment fragment) {

		if (null != fragment && fragment.isVisible()) {
			FragmentTransaction mFragmentTransaction = mFragmentManager
					.beginTransaction();
			mFragmentTransaction.hide(fragment);
			mFragmentTransaction.commitAllowingStateLoss();
		}
	}

	protected void showFragment(Fragment fragment) {

		if (null != fragment && fragment.isHidden()) {
			FragmentTransaction mFragmentTransaction = mFragmentManager
					.beginTransaction();
			mFragmentTransaction.show(fragment);
			mFragmentTransaction.commitAllowingStateLoss();
		}
	}

	protected String getResourceString(int stringId) {
		return getResources().getString(stringId);
	}

	protected int getResourceColor(int colorId) {
		return getResources().getColor(colorId);
	}

	protected Drawable getResourceDrawable(int drawableId) {
		return getResources().getDrawable(drawableId);
	}

	protected void startActivity(Class<?> cls) {
		startActivity(cls, null);
	}

	protected void startActivity(Class<?> cls, Bundle bundle) {
		Intent intent = new Intent(this, cls);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		startActivity(intent);
	}

	protected void startActivityForResult(Class<?> cls, int requestCode) {
		startActivity(cls, null);
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		  switch (item.getItemId()) {  
		    case android.R.id.home:  
		        finish();  
		        return true;  
		    }  
		  return false;
	}
	
	protected FragmentTransaction getTransaction(){
		 mFragmentTransaction = mFragmentManager
				.beginTransaction();
		return mFragmentTransaction;
	}
}
