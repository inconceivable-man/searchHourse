package com.example.searchhourse;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.searchhourse.fragment.XinfangListFragment;
import com.example.searchhourse.fragment.XinfangMapFragment;

public class XinfangActivity extends Activity {

	private boolean isMap = false;
	private FragmentManager manager;
	private XinfangListFragment fragmentList;
	private XinfangMapFragment fragmentMap;
	private Button btn_select_map;
	private TextView info_title_textId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_xinfang);
		
		info_title_textId = (TextView) findViewById(R.id.info_title_textId);
		
		manager = getFragmentManager();
		fragmentList = new XinfangListFragment();
		fragmentMap = new XinfangMapFragment();
		btn_select_map = (Button) findViewById(R.id.xinfang_btn_selectMap);
		btn_select_map.setSelected(true);
		initFragment(fragmentList);
	}
	
	private void initFragment(Fragment fragment) {
		manager.beginTransaction().replace(R.id.xinfang_fragmentId, fragment).commit();
	}

	public void checkedMap(View v){
		if(isMap){
			info_title_textId.setText("新房");
			btn_select_map.setSelected(true);
			initFragment(fragmentList);
			isMap = false;
		}else{
			info_title_textId.setText("地图展示");
			btn_select_map.setSelected(false);
			initFragment(fragmentMap);
			isMap = true;
		}
	}
	
	
	
	public void backHome(View v) {
		finish();
	}
}
