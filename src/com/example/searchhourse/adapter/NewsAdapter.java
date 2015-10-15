package com.example.searchhourse.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.searchhourse.R;
import com.example.searchhourse.bean.News;
import com.example.searchhourse.tool.HttpUtil;
import com.example.searchhourse.tool.HttpUtil.CallBack;
import com.example.searchhourse.tool.ImageUtil;

public class NewsAdapter extends BaseAdapter {

	private Context context;
	private List<News> datas;

	public NewsAdapter(Context context) {
		this.context = context;
		this.datas = new ArrayList<News>();
	}

	public void addList(List<News> list) {
		datas.addAll(list);
	}
	
	public void clear(){
		datas.clear();
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getViewTypeCount() {
		return News.getTypeCount();
	}

	@Override
	public int getItemViewType(int position) {
		return Integer.parseInt(datas.get(position).getType());
	}

	@Override
	public View getView(int position, View convertView, final ViewGroup parent) {
		ViewHodler hol = null;
		if (convertView == null) {
			hol = new ViewHodler();
			if (getItemViewType(position) == News.TYPE_small_image) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.item_news_smallimage, null);
			} else {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.item_news_bigimage, null);
			}
			hol.ImageView = (ImageView) convertView
					.findViewById(R.id.news_imageViewId);
			hol.title_text = (TextView) convertView
					.findViewById(R.id.news_title_textViewId);
			hol.summary_text = (TextView) convertView
					.findViewById(R.id.news_summary_textViewId);
			convertView.setTag(hol);
		} else {
			if (getItemViewType(position) == News.TYPE_small_image) {
				if (convertView != LayoutInflater.from(context).inflate(
						R.layout.item_news_smallimage, null)) {
					convertView = LayoutInflater.from(context).inflate(
							R.layout.item_news_smallimage, null);
					hol = new ViewHodler();
					hol.ImageView = (ImageView) convertView
							.findViewById(R.id.news_imageViewId);
					hol.title_text = (TextView) convertView
							.findViewById(R.id.news_title_textViewId);
					hol.summary_text = (TextView) convertView
							.findViewById(R.id.news_summary_textViewId);
					convertView.setTag(hol);
				}
			}else{
				if (convertView != LayoutInflater.from(context).inflate(
						R.layout.item_news_bigimage, null)) {
					convertView = LayoutInflater.from(context).inflate(
							R.layout.item_news_bigimage, null);
					hol = new ViewHodler();
					hol.ImageView = (ImageView) convertView
							.findViewById(R.id.news_imageViewId);
					hol.title_text = (TextView) convertView
							.findViewById(R.id.news_title_textViewId);
					hol.summary_text = (TextView) convertView
							.findViewById(R.id.news_summary_textViewId);
					convertView.setTag(hol);
				}
			}
		}
		
		hol = (ViewHodler) convertView.getTag();
		hol.title_text.setText(datas.get(position).getTitle());
		hol.summary_text.setText(datas.get(position).getSummary());
		String url = datas.get(position).getGroupthumbnail();
		hol.ImageView.setTag(url);
		Bitmap bitmap = ImageUtil.getImage(url);
		if(bitmap != null){
			hol.ImageView.setImageBitmap(bitmap);
		}else{
			HttpUtil.gets(HttpUtil.TYPE_IMAGE, url, new CallBack() {
				
				@Override
				public void response(byte[] bytes, String url) {
					ImageView imageView = (ImageView) parent.findViewWithTag(url);
					if(imageView != null){
						imageView.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
					}
				}
				
				@Override
				public boolean isCanclled(String url) {
					return parent.findViewWithTag(url)==null;
				}
			});
		}
		return convertView;
	}

	class ViewHodler {
		ImageView ImageView;
		TextView title_text, summary_text;
	}
}
