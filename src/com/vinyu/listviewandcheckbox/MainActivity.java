package com.vinyu.listviewandcheckbox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class MainActivity extends Activity {

	private ListView listView;
	Button resetButton;
	Button delButton ;
	LinearLayout innerLinearLayout;
	private final String TAG = "ListActivity";
	private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initData();
		initPhone();
	}

	private void initPhone() {
		listView = (ListView) findViewById(R.id.listView);
		resetButton = (Button) findViewById(R.id.reset);
		delButton = (Button) findViewById(R.id.delete);
		innerLinearLayout = (LinearLayout) findViewById(R.id.innerLinearLayout);
		listView.setOnItemLongClickListener(listener);
		resetButton.setOnClickListener(resetButtonOnClickListener);
		delButton.setOnClickListener(delButtonOnClickListener);
		setAdapter();

		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				TextView name = (TextView) view.findViewById(R.id.name);
				Toast.makeText(getApplicationContext(), name.getText(),
						Toast.LENGTH_SHORT).show();
			}
		});
		
		
	}

	private void initData() {
		Map<String, Object> p1 = new HashMap<String, Object>();
		p1.put("icon", R.drawable.face1);
		p1.put("name", "曹操");
		p1.put("no", "13088154501");

		Map<String, Object> p2 = new HashMap<String, Object>();
		p2.put("icon", R.drawable.face2);
		p2.put("name", "刘备");
		p2.put("no", "17088595647");
		Map<String, Object> p3 = new HashMap<String, Object>();
		p3.put("icon", R.drawable.face3);
		p3.put("name", "孙权");
		p3.put("no", "13355215657");
		Map<String, Object> p4 = new HashMap<String, Object>();
		p4.put("icon", R.drawable.face4);
		p4.put("name", "司马懿");
		p4.put("no", "18956762146");
		Map<String, Object> p5 = new HashMap<String, Object>();
		p5.put("icon", R.drawable.face5);
		p5.put("name", "诸葛亮");
		p5.put("no", "18024315621");
		list.add(p1);
		list.add(p2);
		list.add(p3);
		list.add(p4);
		list.add(p5);

	}

	private void setAdapter() {
		if(innerLinearLayout.getVisibility()==LinearLayout.INVISIBLE){
			MyAdapter.cbVisible = false;
		}else{
			MyAdapter.cbVisible = true;
		}
		MyAdapter myAdapter = new MyAdapter(list, this);
		
		listView.setAdapter(myAdapter);
	}

	private OnItemLongClickListener listener = new OnItemLongClickListener() {

		public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id) {
			if(!MyAdapter.cbVisible){
				MyAdapter.cbVisible=true;
				innerLinearLayout.setVisibility(LinearLayout.VISIBLE);
				setAdapter();
				
			}else{
				MyAdapter.cbVisible=false;
				innerLinearLayout.setVisibility(LinearLayout.INVISIBLE);
				setAdapter();
			}
			return true;
		}
	};
	//取消按钮事件
	private android.view.View.OnClickListener resetButtonOnClickListener = new android.view.View.OnClickListener() {
		
		public void onClick(View v) {
			MyAdapter.cbVisible=false;
			innerLinearLayout.setVisibility(LinearLayout.INVISIBLE);
			setAdapter();
		}
	};
	//删除按钮事件
	private android.view.View.OnClickListener delButtonOnClickListener = new android.view.View.OnClickListener(){

		public void onClick(View v) {
			//批量删除
			final List<String> selected = MyAdapter.getSelected();
			if(selected.size()>0){
			Builder builder = new Builder(MainActivity.this);
			builder.setMessage("确定批量删除？");
			builder.setPositiveButton("确定", new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
						for (int i = 0; i < selected.size(); i++) {
							Iterator<Map<String, Object>> iterator = list.iterator();
							while (iterator.hasNext()) {
								if (iterator.next().get("name").equals(selected.get(i))) {
									iterator.remove();
								}
							}
						}
						selected.clear();
						setAdapter();
				}
			});
			builder.setNegativeButton("取消", null);
			builder.show();
			}else{
				Toast.makeText(MainActivity.this, "未选中任何项!!!", Toast.LENGTH_SHORT).show();
			}
		}
		
	};

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
