package com.example.searchhourse;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.example.make_view.SideBar;
import com.example.make_view.SideBar.OnTouchingLetterChangedListener;
import com.example.searchhourse.adapter.CitySelectAdapter;
import com.example.searchhourse.bean.City;
import com.example.searchhourse.tool.HttpUtil;
import com.example.searchhourse.tool.HttpUtil.CallBack;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class SelectCityActivity extends Activity implements OnItemClickListener {

	private ListView listView;
	private CitySelectAdapter adapter;
	private List<City> citys;
	private SideBar sideBar;
	private TextView dialog_text;

	public static final String CITY_ID = "cityId";
	public static final String CITY_NAME = "cityName";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_city_layout);

		listView = (ListView) findViewById(R.id.listViewId);
		sideBar = (SideBar) findViewById(R.id.sideBar);
		dialog_text = (TextView) findViewById(R.id.dialog_textId);
		adapter = new CitySelectAdapter(getApplicationContext());
		citys = new ArrayList<City>();
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(this);
		sideBar.setTextDialog(dialog_text);
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

			@Override
			public void onTouchingLetterChanged(int position) {
				String city_lable = Config.CITY_TYPE[position];
				for (int i = 0; i < citys.size(); i++) {
					if (citys.get(i).getCityname().equals(city_lable)) {
						listView.setSelection(i);
						break;
					}
				}
			}
		});
		initCitys();
	}

	private void initCitys() {
		HttpUtil.gets(HttpUtil.TYPE_TEXT, Config.CHOICE_CITY, new CallBack() {

			@Override
			public void response(byte[] bytes, String url) {
				try {
					JSONObject obj = new JSONObject(new String(bytes, "utf-8"));
					JSONObject cities = obj.getJSONObject("cities");
					for (int i = 0; i < Config.CITY_TYPE.length; i++) {
						City city_label = new City();
						city_label.setCityname(Config.CITY_TYPE[i]);
						city_label.setComparename("label");

						JSONArray array = cities
								.getJSONArray(Config.CITY_TYPE[i]);

						TypeToken<List<City>> token = new TypeToken<List<City>>() {
						};
						Gson gson = new Gson();
						List<City> datas = gson.fromJson(array.toString(),
								token.getType());
						citys.add(city_label);
						citys.addAll(datas);
					}
					adapter.addCitys(citys);
					adapter.notifyDataSetChanged();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public boolean isCanclled(String url) {
				// TODO Auto-generated method stub
				return false;
			}

		});
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent();
		intent.putExtra(CITY_ID, citys.get(position).getCityid());
		intent.putExtra(CITY_NAME, citys.get(position).getCityname());
		setResult(RESULT_OK, intent);
		this.finish();
	}

	public void returnMain(View v) {
		this.finish();
	}
}
