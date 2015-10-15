package com.example.make_view;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.searchhourse.Config;
import com.example.searchhourse.R;
import com.example.searchhourse.XinfangActivity;
import com.example.searchhourse.bean.Advertisement;
import com.example.searchhourse.tool.HttpUtil;
import com.example.searchhourse.tool.HttpUtil.CallBack;
import com.example.searchhourse.tool.ImageUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class TopView extends LinearLayout implements OnPageChangeListener {

	private ViewPager viewPager;
	private TextView titleText;
	private LinearLayout navLayout;

	private List<Advertisement> list;
	private List<ImageView> imageViews;
	private View more_view;
	private LinearLayout more_linear;
	private Button btn_checked_more;

	public TopView(Context context, String city_id) {
		super(context);

		LayoutInflater.from(context).inflate(R.layout.top_advertisement_layout,
				this, true);

		String cityId = city_id;
		String url = String.format(Config.FIRST_PAGE_WEBVIEW, cityId);
		initView();
		initDatas(url);
		viewPager.setOnPageChangeListener(this);
	}

	private void initDatas(String url) {
		HttpUtil.gets(HttpUtil.TYPE_TEXT, url, new CallBack() {

			@Override
			public void response(byte[] bytes, String url) {
				try {
					String jsonString = new String(bytes, "utf-8");

					JSONObject obj = new JSONObject(jsonString);
					JSONArray data = obj.getJSONArray("data");

					TypeToken<List<Advertisement>> token = new TypeToken<List<Advertisement>>() {
					};
					Gson gson = new Gson();
					list = gson.fromJson(data.toString(), token.getType());
					if (list.size() != 0) {
						createImageViews();
					}
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

	private void createImageViews() {
		imageViews = new ArrayList<ImageView>();
		titleText.setText(list.get(0).getTitle());
		ImageView imgView = null;
		for (int i = 0; i < list.size(); i++) {
			imgView = new ImageView(getContext());
			imgView.setScaleType(ScaleType.CENTER_CROP);
			String imagUrl = list.get(i).getPicurl();
			imgView.setTag(imagUrl);
			imageViews.add(imgView);
			Bitmap bitmap = ImageUtil.getImage(imagUrl);
			if (bitmap != null) {
				imgView.setImageBitmap(bitmap);
			} else {
				HttpUtil.gets(HttpUtil.TYPE_IMAGE, imagUrl, new CallBack() {

					@Override
					public void response(byte[] bytes, String url) {
						Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0,
								bytes.length);
						for (int j = 0; j < imageViews.size(); j++) {
							ImageView imgV = imageViews.get(j);
							if (imgV.getTag() == url)
								imgV.setImageBitmap(bitmap);
						}
					}

					@Override
					public boolean isCanclled(String url) {
						return false;
					}
				});
			}
		}
		viewPager.setAdapter(new AdvertisementAdapter());
		new PlayImgThread().start();
	}

	private Button btn_checked_xinfang;
	
	private void initView() {
		viewPager = (ViewPager) findViewById(R.id.viewPagerId);
		titleText = (TextView) findViewById(R.id.titleId);
		navLayout = (LinearLayout) findViewById(R.id.navLayoutId);
		more_view = findViewById(R.id.more_viewId);
		more_linear = (LinearLayout) findViewById(R.id.more_linearId);
		more_linear.setVisibility(GONE);
		SelectMenuOnclickListener selectMenuLinstener = new SelectMenuOnclickListener();
		btn_checked_more = (Button) findViewById(R.id.btn_checked_moreId);
		btn_checked_more.setOnClickListener(selectMenuLinstener);
		btn_checked_xinfang = (Button) findViewById(R.id.btn_checked_xinfangId);
		btn_checked_xinfang.setOnClickListener(selectMenuLinstener);
	}

	class SelectMenuOnclickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_checked_moreId:
				if (more_linear.getVisibility() == VISIBLE) {
					more_view.setVisibility(VISIBLE);
					more_linear.setVisibility(GONE);
				} else {
					more_view.setVisibility(GONE);
					more_linear.setVisibility(VISIBLE);
				}
				break;
			case R.id.btn_checked_xinfangId:
				Intent intent = new Intent(getContext(), XinfangActivity.class);
				getContext().startActivity(intent);
				break;
			}
		}

	}

	// 定义ViewPager显示的适配器
	class AdvertisementAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(imageViews.get(position));
			return imageViews.get(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(imageViews.get(position));
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		curPosition = arg0;
		titleText.setText(list.get(arg0).getTitle());
	}

	private Handler mHandelr = new Handler();
	private int curPosition = 0;

	class PlayImgThread extends Thread {
		@Override
		public void run() {
			super.run();

			while (TopView.this != null) {
				if (curPosition == imageViews.size()) {
					curPosition = 0;
				}
				try {
					Thread.sleep(4000);
					mHandelr.post(new Runnable() {

						@Override
						public void run() {
							titleText.setText(list.get(curPosition).getTitle());

							if (curPosition == 0)
								viewPager.setCurrentItem(curPosition++, false);// false不带有动画效果地切换
							else
								viewPager.setCurrentItem(curPosition++);
						}
					});
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
