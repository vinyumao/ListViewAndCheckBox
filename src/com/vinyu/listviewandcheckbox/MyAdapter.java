package com.vinyu.listviewandcheckbox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.R.bool;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MyAdapter extends BaseAdapter {

	private List<Map<String, Object>> list;
	private Context context;
	private static List<String> selected;

	public static boolean cbVisible = false;

	public static List<String> getSelected() {
		return selected;
	}

	public static void setSelected(List<String> selected) {
		MyAdapter.selected = selected;
	}

	public MyAdapter(List<Map<String, Object>> list, Context context) {
		this.list = list;
		this.context = context;
		selected = new ArrayList<String>();
	}

	public int getCount() {
		return list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	// 负责填充每一行的数据
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.phone_item, null);
		}
		View view = convertView;
		ImageView icon = (ImageView) view.findViewById(R.id.icon);
		TextView name = (TextView) view.findViewById(R.id.name);
		TextView no = (TextView) view.findViewById(R.id.no);

		Map<String, Object> person = list.get(position);
		name.setText((CharSequence) person.get("name"));
		no.setText((CharSequence) person.get("no"));
		int picId = (Integer) person.get("icon");
		Drawable drawable = context.getResources().getDrawable(picId);
		icon.setImageDrawable(drawable);
		if (position % 2 == 0) {
			view.setBackgroundColor(context.getResources().getColor(
					android.R.color.darker_gray));
		} else {
			view.setBackgroundColor(context.getResources().getColor(
					android.R.color.holo_blue_dark));
		}
		CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox);
		if (cbVisible) {
			checkBox.setVisibility(CheckBox.VISIBLE);
		} else {
			checkBox.setVisibility(CheckBox.INVISIBLE);
		}

		final int count = position;
		checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					selected.add((String) list.get(count).get("name"));
				} else {
					selected.remove(list.get(count).get("name"));
				}
				setSelected(selected);
			}
		});
		return view;
	}

}
