package com.example.searchhourse.tool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class AbsAdapter<T> extends BaseAdapter {

	private Context context;
	private List<T> datas;
	private int layoutResId;

	public AbsAdapter(Context context, List<T> datas, int layoutResId) {
		super();
		this.context = context;
		this.datas = datas;
		this.layoutResId = layoutResId;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder hol = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(layoutResId,
					null);
			hol = new ViewHolder(convertView);
			convertView.setTag(hol);
		} else {
			hol = (ViewHolder) convertView.getTag();
		}

		bindView(hol, datas.get(position));
		return convertView;
	}

	public abstract void bindView(ViewHolder viewHolder, T data);

	public class ViewHolder {
		private Map<Integer, View> cacheViews;
		private View itemView;

		public ViewHolder(View view) {
			itemView = view;
			cacheViews = new HashMap<Integer, View>();
		}

		public View getView(Integer id) {
			View v = cacheViews.get(id);
			if (v == null) {
				v = itemView.findViewById(id);
				if (v != null)
					cacheViews.put(id, v);
			}
			return v;
		}
	}

}
