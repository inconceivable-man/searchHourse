package com.example.searchhourse.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.searchhourse.Config;
import com.example.searchhourse.R;
import com.example.searchhourse.XinfangInfoActivity;
import com.example.searchhourse.bean.BookMark;
import com.example.searchhourse.bean.Xinfang;
import com.example.searchhourse.tool.AbsAdapter;
import com.example.searchhourse.tool.VolleyUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class XinfangListFragment extends Fragment implements
		OnCheckedChangeListener, OnItemClickListener {

	private RequestQueue rQueue;
	private ImageLoader imgLoader;
	private PullToRefreshListView ptrListView;
	private AbsAdapter<Xinfang> adapter;
	private List<Xinfang> datas;
	private String city_id = "1";
	private int page = 1;
	private LayoutParams layoutParams;
	private PopupWindow popupWindow;
	private ListView tabListView;
	private RadioGroup radioGroup;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return LayoutInflater.from(getActivity()).inflate(
				R.layout.xinfang_fragment_list, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		init();
	}

	private void init() {
		tabListView = new ListView(getActivity());
		ptrListView = (PullToRefreshListView) getView().findViewById(
				R.id.xinfangListViewId);
		radioGroup = (RadioGroup) getView().findViewById(
				R.id.radioGroup_xinfangId);
		xinfang_sum = (TextView) getView().findViewById(R.id.xinfang_sumId);
		radioGroup.setOnCheckedChangeListener(this);
		rQueue = VolleyUtils.getRequestQueue(getActivity()
				.getApplicationContext());
		datas = new ArrayList<Xinfang>();
		adapter = new AbsAdapter<Xinfang>(
				getActivity().getApplicationContext(), datas,
				R.layout.item_xinfang) {

			@Override
			public void bindView(
					com.example.searchhourse.tool.AbsAdapter.ViewHolder viewHolder,
					Xinfang data) {
				((TextView) viewHolder.getView(R.id.xinfang_title_textViewId))
						.setText(data.getFname());
				((TextView) viewHolder.getView(R.id.xinfang_place_textViewId))
						.setText(data.getFregion());
				((TextView) viewHolder.getView(R.id.xinfang_price_textViewId))
						.setText(data.getFpricedisplaystr());
				((TextView) viewHolder.getView(R.id.xinfang_address_textViewId))
						.setText(data.getFaddress());

				// 加载标签
				List<BookMark> bookMarks = data.getBookMarks();
				if (bookMarks != null) {
					int bookMark_size = bookMarks.size();
					TextView tv = null;
					int[] backgrounds = new int[] { R.drawable.bg_tag_red,
							R.drawable.bg_tag_orange, R.drawable.bg_tag_blue };
					LinearLayout layout = (LinearLayout) viewHolder
							.getView(R.id.xinfang_bookmark_linearLayoutId);
					layout.removeAllViews();

					layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT);
					layoutParams.rightMargin = 4;
					for (int i = 0; i < bookMark_size; i++) {
						tv = new TextView(getActivity().getApplicationContext());
						tv.setTextColor(Color.parseColor("#FFFFFF"));
						tv.setTextSize(10);
						tv.setPadding(10, 2, 10, 2);

						BookMark bookMark = bookMarks.get(i);
						tv.setBackgroundResource(backgrounds[i]);
						tv.setText(bookMark.getTag());
						layout.addView(tv, layoutParams);
					}
				}

				// 加载图片
				ImageView imageView = (ImageView) viewHolder
						.getView(R.id.xinfang_imageViewId);
				imageView.setImageResource(R.drawable.gallery_default_image);
				// 三级缓冲加载图片
				imgLoader = VolleyUtils.getImageLoader(getActivity()
						.getApplicationContext());
				String imgUrl = data.getFcover();
				imgLoader.get(imgUrl, ImageLoader.getImageListener(imageView,
						R.drawable.gallery_default_image,
						R.drawable.gallery_default_image));
			}
		};
		ptrListView.setAdapter(adapter);
		initPTR();
		initDatas(page, city_id);
		ptrListView.setOnItemClickListener(this);
	}

	private void initPopupWindow(View view) {
		popupWindow = new PopupWindow(tabListView,
				android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				getResources().getDisplayMetrics().heightPixels / 2, true);
		popupWindow.setFocusable(true);

		popupWindow.setTouchInterceptor(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
//				RadioButton rb1 = (RadioButton) getView().findViewById(
//						lastRadioButtonId);
//				rb1.setSelected(false);
				
				return false;
			}

		});

		// 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
		popupWindow.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.background));
		// 设置动画效果
		popupWindow.setAnimationStyle(R.style.AppTheme);

		// 设置好参数之后再show
		popupWindow.showAsDropDown(view);

	}

	private void initPTR() {
		ptrListView.setMode(Mode.BOTH);
		ptrListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				datas.clear();
				initDatas(page, city_id);
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				page += 1;
				initDatas(page, city_id);
			}
		});

	}

	private void initDatas(int page, String city_id) {
		JsonObjectRequest request = new JsonObjectRequest(Method.GET,
				String.format(Config.LOOKING_NEWHOUSE, page, city_id), null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						try {
							String xinfangSum = response.getString("total");
							xinfang_sum.setText("共有" + xinfangSum + "个楼盘");
							JSONArray array = response.getJSONArray("data");
							Gson gson = new Gson();
							parseJson(array, gson);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {

					}

				});
		rQueue.add(request);
	}

	protected void parseJson(JSONArray array, Gson gson) {
		TypeToken<List<Xinfang>> token = new TypeToken<List<Xinfang>>() {
		};
		List<Xinfang> list = gson.fromJson(array.toString(), token.getType());
		JSONObject data = null;
		for (int i = 0; i < array.length(); i++) {
			try {
				data = array.getJSONObject(i);
				JSONArray arrays = data.getJSONArray("bookmark");
				List<BookMark> bookmarks = new ArrayList<BookMark>();
				BookMark bookmark = null;
				for (int j = 0; j < arrays.length(); j++) {
					bookmark = new BookMark();
					bookmark.setTag(arrays.getJSONObject(j).getString("tag"));
					bookmark.setType(arrays.getJSONObject(j).getInt("type"));
					bookmarks.add(bookmark);
				}
				list.get(i).setBookMarks(bookmarks);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		ptrListView.onRefreshComplete();
		if (list != null) {
			datas.addAll(list);
			adapter.notifyDataSetChanged();
		}
	}

	private int lastRadioButtonId;
	private TextView xinfang_sum;

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		if (popupWindow != null && popupWindow.isShowing()) {
			RadioButton rb1 = (RadioButton) getView().findViewById(
					lastRadioButtonId);
			if (rb1 != null) {
				rb1.setSelected(false);
			}
			popupWindow.dismiss();
		}
		RadioButton rb = (RadioButton) getView().findViewById(checkedId);
		rb.setSelected(true);
		if (lastRadioButtonId == checkedId) {
			lastRadioButtonId = 0;
			rb.setSelected(false);
			popupWindow.dismiss();
		} else {
			lastRadioButtonId = checkedId;
			switch (checkedId) {
			case R.id.radioButton_quyuId:
				initPopupWindow(group);
				radioGroup.setFocusable(true);
				break;
			case R.id.radioButton_leibieId:
				initPopupWindow(group);
				radioGroup.setFocusable(true);
				break;
			case R.id.radioButton_jiageId:
				initPopupWindow(group);
				radioGroup.setFocusable(true);
				break;
			case R.id.radioButton_moreId:
				initPopupWindow(group);
				radioGroup.setFocusable(true);
				break;
			}
		}
		rb.setChecked(false);
	}

	// 点击调到新房详情
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(getActivity(), XinfangInfoActivity.class);
		intent.putExtra("fid", datas.get(position).getFid());
		startActivity(intent);
	}

}
