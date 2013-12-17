package org.jan.taobao.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

@SuppressLint("UseSparseArrays")
public class BatchManageListActivity extends Activity implements
		OnClickListener {
	private static final String BATCH_TAG = "batchTag";

	private Button mBackBtn;
	private ImageButton mBatchManageBtn;
	private ImageButton mCancelBtn;
	private ImageButton mDelBtn;
	private ImageButton mMoreBtn;
	private ListView listView;
	private ArrayList<HashMap<String, Object>> mList;
	private BatchManagerAdaptor mAdaptor;
	private HashMap<Integer, Boolean> mMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.manage_main_layout);
		initView();

	}

	public void initView() {
		mMap = new HashMap<Integer, Boolean>();
		mBackBtn = (Button) findViewById(R.id.list2_back_btn);
		mBackBtn.setOnClickListener(this);
		mBatchManageBtn = (ImageButton) findViewById(R.id.batchManageBtn);
		mBatchManageBtn.setOnClickListener(this);
		mCancelBtn = (ImageButton) findViewById(R.id.cancelBtn);
		mCancelBtn.setOnClickListener(this);
		mDelBtn = (ImageButton) findViewById(R.id.deleteBtn);
		mDelBtn.setOnClickListener(this);
		mMoreBtn = (ImageButton) findViewById(R.id.moreItemBtn);
		mMoreBtn.setOnClickListener(this);
		listView = (ListView) findViewById(R.id.manageList);
		Intent intent = getIntent();
		if (intent.hasExtra("mList")) {
			mList = (ArrayList<HashMap<String, Object>>) intent
					.getSerializableExtra("mList");
			if (mList != null) {
				Log.d(BATCH_TAG, "mList.size=" + mList.size());
				Log.d(BATCH_TAG, "mList[1]="
						+ mList.get(1).get("item_image").toString());
				setListAdaptor(mList);
			} else {
				Log.d(BATCH_TAG, "mList is null");
			}

			listView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent,
						View currentView, int position, long id) {

					CheckBox box = (CheckBox) currentView
							.findViewById(R.id.checkBox);
					if (box != null) {
						box.toggle();
						if (box.isChecked()) {
							Toast.makeText(
									BatchManageListActivity.this,
									"position=" + position + ",id=" + id
											+ "，checked=" + true, 300).show();
							mMap.put(position, true);
						}
					}
				}
			});
		}
	}

	public void setListAdaptor(List<HashMap<String, Object>> list) {
		mAdaptor = new BatchManagerAdaptor(BatchManageListActivity.this, list,
				R.layout.simple_item2, new String[] { "item_image",
						"item_name", "item_price" },
				new int[] { R.id.item_image_2, R.id.item_name_2,
						R.id.item_price_2 });
		listView.setAdapter(mAdaptor);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.deleteBtn:
			delete(mMap);
			break;
		case R.id.batchManageBtn:
			// 全选

			batchMyList(true);
			break;
		case R.id.cancelBtn:
			batchMyList(false);
			break;
		case R.id.moreItemBtn:

			break;
		case R.id.back_btn:
			finish();
			break;
		default:
			break;
		}
	}

	public void delete(Map<Integer, Boolean> map) {
		Log.d(BATCH_TAG, "mMap.size=" + map.size());
		Iterator<Integer> iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			int i = iterator.next();
			Log.d(BATCH_TAG, "index=" + i);
			boolean isChecked = map.get(i);
			if (isChecked) {
				mList.remove(i);
			}
		}
		setListAdaptor(mList);
		mMap.clear();
	}

	// 全选操作
	public void batchMyList(boolean choosed) {
		mMap.clear();
		int count = listView.getChildCount();
		Log.d(BATCH_TAG, "child's count=" + count);
		for (int i = 0; i < count; i++) {
			((CheckBox) ((listView.getChildAt(i)).findViewById(R.id.checkBox)))
					.setChecked(choosed);
		}
		for (int j = 0; j < listView.getCount(); j++) {
			mMap.put(j, choosed);
		}
		((BatchManagerAdaptor) listView.getAdapter()).selectAll(choosed);
	}

	class BatchManagerAdaptor extends SimpleAdapter {
		private HashMap<Integer, Boolean> isSelected = null;

		public BatchManagerAdaptor(Context context,
				List<? extends Map<String, ?>> data, int resource,
				String[] from, int[] to) {
			super(context, data, resource, from, to);
			isSelected = new HashMap<Integer, Boolean>();
			selectAll(false);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = super.getView(position, convertView, parent);
			Boolean checked = false;
			checked = isSelected.get(position);
			if (null == checked) {
				checked = false;
			}
			CheckBox checkBox = (CheckBox) convertView
					.findViewById(R.id.checkBox);
			checkBox.setChecked(checked);
			return convertView;
		}

		public void select(int postion, boolean isChecked) {
			isSelected.put(postion, isChecked);
		}

		public void selectAll(boolean isChecked) {
			int count = this.getCount();
			for (int b = 0; b < count; b++) {
				select(b, isChecked);
			}
		}
	}

}
