package com.example.searchhourse;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.searchhourse.tool.VolleyUtils;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebSettings.LayoutAlgorithm;

public class XinfangInfoActivity extends Activity {

	private RequestQueue rQueue;
	private WebView info_web;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xinfang_info);

		rQueue = VolleyUtils.getRequestQueue(getApplicationContext());

		initView();

		initData();

	}

	private void initView() {
		info_web = (WebView) findViewById(R.id.xinfang_webViewId);
		info_web.getSettings()
				.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
	}

	private void initData() {
		String fid = getIntent().getStringExtra("fid");
		String url = String
				.format(Config.NEW_HOUSE_INFO, Integer.parseInt(fid));

		JsonObjectRequest request = new JsonObjectRequest(Method.GET, url,
				null, new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						try {
							String houseurl = response.getString("houseurl");
							info_web.loadUrl(houseurl);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
					}
				});
		rQueue.add(request);
	}

}
