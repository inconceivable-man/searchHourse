package com.example.searchhourse.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.baidu.mapapi.SDKInitializer;
import com.example.searchhourse.R;

public class XinfangMapFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		SDKInitializer.initialize(getActivity().getApplicationContext());
		RelativeLayout layout = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.xinfang_fragment_map, null);
		return layout;
	}
}
