package com.example.searchhourse;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.searchhourse.fragment.NewsListFragment;

public class MainActivity extends Activity {

	private String city_id = "1";
	private String city_name = "±±¾©";
	private int REQUESTCODE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initFragment();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	public void checkHome(View v) {
		initFragment();
	}

	public void checkFind(View v) {

	}

	public void checkNews(View v) {

	}

	public void checkMine(View v) {

	}

	private void initFragment() {
		Bundle bundle = new Bundle();
		bundle.putString(SelectCityActivity.CITY_ID, city_id);
		bundle.putString(SelectCityActivity.CITY_NAME, city_name);

		FragmentManager manager = getFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		NewsListFragment fragment = NewsListFragment.newInstance();
		fragment.setArguments(bundle);
		transaction.replace(R.id.fragment, fragment, null).commit();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUESTCODE && resultCode == RESULT_OK) {
			city_id = data.getStringExtra(SelectCityActivity.CITY_ID);
			city_name = data.getStringExtra(SelectCityActivity.CITY_NAME);
			initFragment();
		}
	}
}
