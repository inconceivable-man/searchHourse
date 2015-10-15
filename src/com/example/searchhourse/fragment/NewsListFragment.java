package com.example.searchhourse.fragment;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.example.make_view.TopView;
import com.example.searchhourse.Config;
import com.example.searchhourse.InfoActivity;
import com.example.searchhourse.R;
import com.example.searchhourse.SelectCityActivity;
import com.example.searchhourse.adapter.NewsAdapter;
import com.example.searchhourse.bean.News;
import com.example.searchhourse.tool.HttpUtil;
import com.example.searchhourse.tool.HttpUtil.CallBack;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;

public class NewsListFragment extends Fragment implements OnClickListener, OnItemClickListener {

	private PullToRefreshListView ptrListView;
	private List<News> datas;
	private NewsAdapter adapter;
	private int reqnum = 10, pageflag = 0, buttonmore = 0;
	private TopView topView;

	private TextView selectCityTextView;
	private String city_id;
	private String city_name;
	private int REQUESTCODE = 1;
	private View view;

	public static NewsListFragment newInstance() {
		NewsListFragment newsListFragment = new NewsListFragment();
		return newsListFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		city_id = getArguments().getString(SelectCityActivity.CITY_ID);
		city_name = getArguments().getString(SelectCityActivity.CITY_NAME);

		initView();

		adapter = new NewsAdapter(getActivity());
		ptrListView.setAdapter(adapter);
		initDatas(reqnum, pageflag, buttonmore, city_id);

		initTop();

		setPullDownLayout();// 设置下拉布局的相关信息
		initPTR2();
		
		ptrListView.setOnItemClickListener(this);
		return view;
	}

	private void setPullDownLayout() {
		java.text.DateFormat df = DateFormat.getDateFormat(getActivity());

		// 获取下拉的布局
		ILoadingLayout proxy = ptrListView.getLoadingLayoutProxy(true, false);

		proxy.setPullLabel("下拉刷新");
		proxy.setReleaseLabel("放开以加载...");
		proxy.setRefreshingLabel("玩命加载中....");
		proxy.setLastUpdatedLabel("最后的更新时间：" + df.format(new Date()));

	}

	private void initPTR2() {
		ptrListView.setMode(Mode.BOTH);
		ptrListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				new Thread() {
					public void run() {
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						reqnum = 20;
						pageflag = 0;
						buttonmore = 1;
						initDatas(reqnum, pageflag, buttonmore, city_id);
					};
				}.start();
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// TODO 上拉事件
				new Thread() {
					public void run() {
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						reqnum = 20;
						pageflag = 1;
						buttonmore = 1;
						initDatas(reqnum, pageflag, buttonmore, city_id);
					};
				}.start();
			}
		});
	}

	private void initView() {
		view = LayoutInflater.from(getActivity()).inflate(
				R.layout.ptr_listview, null);
		ptrListView = (PullToRefreshListView) view
				.findViewById(R.id.ptrListViewId);
		selectCityTextView = (TextView) view
				.findViewById(R.id.text_selectCityId);
		// 初始化城市选择textView
		selectCityTextView.setText(city_name);
		selectCityTextView.setOnClickListener(this);
	}

	private void initDatas(int reqnum, int pageflag, int buttonmore,
			String cityId) {
		datas = new ArrayList<News>();
		adapter.clear();
		
		String url = String.format(Config.FIRST_PAGE_LISTVIEW, reqnum,
				pageflag, buttonmore, cityId);
		HttpUtil.gets(HttpUtil.TYPE_TEXT, url, new CallBack() {

			@Override
			public void response(byte[] bytes, String url) {
				try {
					String jsonString = new String(bytes, "utf-8");
					JSONObject obj = new JSONObject(jsonString);
					JSONArray data = obj.getJSONArray("data");

					TypeToken<List<News>> token = new TypeToken<List<News>>() {
					};
					Gson gson = new Gson();

					List<News> list = gson.fromJson(data.toString(),
							token.getType());
					datas.addAll(list);
					adapter.addList(list);
					adapter.notifyDataSetChanged();
					ptrListView.onRefreshComplete();
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

	private void initTop() {
		topView = new TopView(getActivity(), city_id);
		ptrListView.getRefreshableView().addHeaderView(topView);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(getActivity(), SelectCityActivity.class);
		getActivity().startActivityForResult(intent, REQUESTCODE);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		int dataPosition = position-2;
		Intent intent = new Intent(getActivity(), InfoActivity.class);
		intent.putExtra(Config.NEWS_ID, datas.get(dataPosition).getId());
		intent.putExtra(Config.COMMENT_ID, datas.get(dataPosition).getCommentid());
		startActivity(intent);
	}
}
