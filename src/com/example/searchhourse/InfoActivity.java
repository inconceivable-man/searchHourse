package com.example.searchhourse;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.searchhourse.adapter.CommentsAdapter;
import com.example.searchhourse.bean.NewsComment;
import com.example.searchhourse.tool.HttpUtil;
import com.example.searchhourse.tool.HttpUtil.CallBack;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class InfoActivity extends Activity {

	private ViewPager viewPager;
	private WebView web_content;
	private List<View> views;
	private List<NewsComment> comments;
	private LinearLayout comments_layout;
	private Button info_btn_comments;

	private TextView title_text, time_text;
	private ListView listView;
	private CommentsAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);

		initViews();
		initNews();
	}

	private void initViews() {
		views = new ArrayList<View>();
		info_btn_comments = (Button) findViewById(R.id.info_btn_commentsId);
		viewPager = (ViewPager) findViewById(R.id.info_contentViewPagerId);
		comments_layout = (LinearLayout) LayoutInflater.from(
				getApplicationContext()).inflate(R.layout.news_comment, null);
		web_content = (WebView) LayoutInflater.from(getApplicationContext())
				.inflate(R.layout.news_content_webview, null);
		web_content.getSettings().setLayoutAlgorithm(
				LayoutAlgorithm.SINGLE_COLUMN);
		viewPager.addView(web_content);
		viewPager.addView(comments_layout);
		// 加载list集合
		views.add(web_content);
		views.add(comments_layout);
		viewPager.setAdapter(new NewsContentAdapter());

	}

	private void initComments(String title, String time) {
		title_text = (TextView) comments_layout
				.findViewById(R.id.comment_titleId);
		time_text = (TextView) comments_layout
				.findViewById(R.id.comment_title_timeId);
		listView = (ListView) findViewById(R.id.comment_listViewId);

		title_text.setText(title);
		time_text.setText(time);
	}

	private void initNews() {
		String new_id = getIntent().getStringExtra(Config.NEWS_ID);
		HttpUtil.gets(HttpUtil.TYPE_TEXT,
				String.format(Config.NEWS_DETAIL, new_id), new CallBack() {

					@Override
					public void response(byte[] bytes, String url) {
						try {
							JSONObject obj = new JSONObject(new String(bytes,
									"utf-8"));
							String surl = obj.getString("surl");
							web_content.loadUrl(surl);
							String title = obj.getString("title");
							String time = obj.getString("time") + "     "
									+ obj.getString("source");
							initComments(title, time);
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

					@Override
					public boolean isCanclled(String url) {
						return false;
					}
				});
		// 加载评论集合
		comments = new ArrayList<NewsComment>();
		String comments_id = getIntent().getStringExtra(Config.COMMENT_ID);
		HttpUtil.gets(HttpUtil.TYPE_TEXT,
				String.format(Config.NEWS_COMMENT, comments_id),
				new CallBack() {

					@Override
					public void response(byte[] bytes, String url) {
						try {
							JSONObject obj = new JSONObject(new String(bytes,
									"utf-8"));
							JSONObject data = obj.getJSONObject("data");
							JSONArray datas = data.getJSONArray("comments");

							TypeToken<List<NewsComment>> token = new TypeToken<List<NewsComment>>() {
							};
							Gson gson = new Gson();
							comments = gson.fromJson(datas.toString(),
									token.getType());
							info_btn_comments.setText(comments.size() + "");
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

					@Override
					public boolean isCanclled(String url) {
						return false;
					}
				});
	}

	private int currentPosition = 0;

	public void showComments(View v) {
		if (currentPosition == 1) {
			info_btn_comments.setSelected(false);
			info_btn_comments.setText(comments.size() + "");
			currentPosition = 0;
			viewPager.setCurrentItem(currentPosition);
		} else if (currentPosition == 0) {
			currentPosition = 1;
			info_btn_comments.setSelected(true);
			viewPager.setCurrentItem(currentPosition);
			// 加载评论
			adapter = new CommentsAdapter(getApplicationContext(), comments);
			listView.setAdapter(adapter);
			info_btn_comments.setText("原文");
		}
	}

	class NewsContentAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return views.size();
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(views.get(position));
			return views.get(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(views.get(position));
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

	}

	public void backHome(View v) {
		this.finish();
	}

}
