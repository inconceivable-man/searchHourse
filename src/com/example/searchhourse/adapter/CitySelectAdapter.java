package com.example.searchhourse.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.searchhourse.R;
import com.example.searchhourse.bean.City;

public class CitySelectAdapter extends BaseAdapter {

	private Context context;
	private List<City> citys;

	public CitySelectAdapter(Context context) {
		this.context = context;
		this.citys = new ArrayList<City>();
	}

	public void addCitys(List<City> list) {
		citys.addAll(list);
	}

	@Override
	public int getCount() {
		return citys.size();
	}

	@Override
	public Object getItem(int position) {
		return citys.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getViewTypeCount() {
		// TODO �����������͵ĸ���
		return City.getTypeCount();
	}

	@Override
	public int getItemViewType(int position) {// ����ָ������λ�õ�UI����
		return citys.get(position).getType();
	}

	@Override
	public boolean isEnabled(int position) {// �����ǩ�����ò��ɵ��
		if (getItemViewType(position) == City.TYPE_LABEL) {
			return false;
		}
		return super.isEnabled(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (getItemViewType(position) == City.TYPE_LABEL) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_city_select_label, null);
		} else {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_city_select_city, null);
		}

		TextView tv = (TextView) convertView.findViewById(R.id.titleId);
		tv.setText(citys.get(position).getCityname());

		return convertView;
	}

}
