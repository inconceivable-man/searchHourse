package com.example.searchhourse.adapter;

import java.util.List;

import com.example.searchhourse.R;
import com.example.searchhourse.bean.NewsComment;
import com.example.searchhourse.tool.HttpUtil;
import com.example.searchhourse.tool.ImageUtil;
import com.example.searchhourse.tool.HttpUtil.CallBack;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CommentsAdapter extends BaseAdapter {

	private Context context;
	private List<NewsComment> comments;

	public CommentsAdapter(Context context, List<NewsComment> comments) {
		this.context = context;
		this.comments = comments;
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		if (observer != null)
			super.unregisterDataSetObserver(observer);
	}

	@Override
	public int getCount() {
		return comments.size();
	}

	@Override
	public Object getItem(int position) {
		return comments.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, final ViewGroup parent) {
		ViewHolder hol = null;
		if (convertView == null) {
			hol = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.news_comment_item, null);
			hol.imageView = (ImageView) convertView
					.findViewById(R.id.item_comment_imageViewId);
			hol.nick = (TextView) convertView
					.findViewById(R.id.item_comment_nick);
			hol.time = (TextView) convertView
					.findViewById(R.id.item_comment_time);
			hol.content = (TextView) convertView
					.findViewById(R.id.item_comment_content);
			convertView.setTag(hol);
		}
		hol = (ViewHolder) convertView.getTag();
		hol.nick.setText(comments.get(position).getNick());
		hol.time.setText(comments.get(position).getTime());
		hol.content.setText(comments.get(position).getContent());
		String url = comments.get(position).getHead();
		hol.imageView.setTag(url);
		Bitmap bitmap = ImageUtil.getImage(url);
		if (bitmap != null) {
			hol.imageView.setImageBitmap(bitmap);
		} else {
			HttpUtil.gets(HttpUtil.TYPE_IMAGE, url, new CallBack() {

				@Override
				public void response(byte[] bytes, String url) {
					ImageView imageView = (ImageView) parent
							.findViewWithTag(url);
					if (imageView != null) {
						imageView.setImageBitmap(BitmapFactory.decodeByteArray(
								bytes, 0, bytes.length));
					}
				}

				@Override
				public boolean isCanclled(String url) {
					return parent.findViewWithTag(url) == null;
				}
			});
		}

		return convertView;
	}

	class ViewHolder {
		ImageView imageView;
		TextView nick, time, content;
	}
}
