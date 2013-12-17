package org.jan.taobao.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.jan.taobao.activities.RegisterActivity.myEditOnKeyListener;
import org.jan.taobao.activities.XListView.IXListViewListener;
import org.jan.taobao.entity.TaobaoUser;
import org.jan.taobao.utils.MyListUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class CommodiesLsitActivity extends Activity implements
		IXListViewListener {

	XListView itemList;
	SimpleAdapter myAdaptor;
	private LinearLayout mSearchll;
	private EditText search;
	private Button mSearch;
	private Button mBackBtn;
	private ImageButton mBatchManageBtn;
	private ImageButton mSearchBtn;
	private ImageButton mSortBtn;
	private ImageButton mMoreBtn;
	private ArrayList<HashMap<String, Object>> mList;
	private ArrayList<HashMap<String, Object>> mResultList;
	private View moreView;
	private Handler mHandler;
	private int maxNum = 5;
	int index = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.items_list_layout);
		needLogin();
		initView();
		itemList = (XListView) findViewById(R.id.xListView);
		itemList.setPullLoadEnable(true);
		mList = (ArrayList<HashMap<String, Object>>) getList();
		myAdaptor = new SimpleAdapter(CommodiesLsitActivity.this, mList,
				R.layout.simple_item, new String[] { "item_image", "item_name",
						"item_price" }, new int[] { R.id.item_image,
						R.id.item_name, R.id.item_price });
		itemList.setAdapter(myAdaptor);
		itemList.setXListViewListener(this);
		mHandler = new Handler();
	}

	private void initView() {
		mSearchll = (LinearLayout) findViewById(R.id.mySearchll);
		search = (EditText) findViewById(R.id.mySearch);
		myEditOnKeyListener onkey = new myEditOnKeyListener();
		search.setOnKeyListener(onkey);
		mSearch = (Button) findViewById(R.id.serach);
		mSearchll.setVisibility(View.GONE);
		MyOnClickListener onClick = new MyOnClickListener();
		mSearch.setOnClickListener(onClick);
		mBackBtn = (Button) findViewById(R.id.list1_back_btn);
		mBackBtn.setOnClickListener(onClick);
		mBatchManageBtn = (ImageButton) findViewById(R.id.batchManageBtn);
		mBatchManageBtn.setOnClickListener(onClick);
		mSearchBtn = (ImageButton) findViewById(R.id.searchBtn);
		mSearchBtn.setOnClickListener(onClick);
		mSortBtn = (ImageButton) findViewById(R.id.sortBtn);
		mSortBtn.setOnClickListener(onClick);
		mMoreBtn = (ImageButton) findViewById(R.id.moreItemBtn);
		mMoreBtn.setOnClickListener(onClick);

	}

	public List<HashMap<String, Object>> getList() {
		mList = new ArrayList<HashMap<String, Object>>();
		Random rd = new Random();
		for (int i = index; i < maxNum; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("item_image", R.drawable.taobao_shouji_1);
			map.put("item_name", i + ".三星GT-S5830");
			map.put("item_price", "￥" + Math.abs(rd.nextInt()) % 4000 + ".0");
			mList.add(map);
		}
		return mList;
	}

	/**
	 * 检索当前数据中带有info字符的数据
	 * 
	 * @param info
	 * @return 搜索的结果
	 */
	public List<HashMap<String, Object>> getSearchList(String info) {
		mResultList = new ArrayList<HashMap<String, Object>>();
		for (HashMap map : mList) {
			if (map.get("item_name").toString().contains(info)) {
				Log.d("DEBUG", map.get("item_name").toString());
				mResultList.add(map);
			}
		}
		return mResultList;

	}

	List<HashMap<String, Object>> initList() {
		int count = myAdaptor.getCount();
		Random rd = new Random();
		if (count + maxNum <= 20) {
			for (int i = count + 1; i <= count + maxNum; i++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("item_image", R.drawable.taobao_shouji_1);
				map.put("item_name", i + ".三星GT-S583" + i);
				map.put("item_price", "￥" + Math.abs(rd.nextInt()) % 4000
						+ ".0");
				mList.add(map);
			}
		} else {
			for (int i = count + 1; i <= 20; i++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("item_image", R.drawable.taobao_shouji_1);
				map.put("item_name", i + ".三星GT-S5830");
				map.put("item_price", "￥1630.0");
				mList.add(map);
			}
		}
		return mList;

	}

	/**
	 * 每次启动需要检查是否已经登陆
	 */
	public void needLogin() {
		if (isLogin()) {
			return;
		} else {
			Intent intent = new Intent(CommodiesLsitActivity.this,
					LoginActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
	}

	public boolean isLogin() {
		Intent intent = getIntent();
		if (intent != null) {
			if (intent.hasExtra("TaobaoUser")) {
				Bundle data = intent.getBundleExtra("TaobaoUser");
				TaobaoUser user = data.getParcelable("user");
				Toast.makeText(CommodiesLsitActivity.this,
						"用户：" + user.getUserName() + " 登陆", Toast.LENGTH_SHORT)
						.show();
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				onLoad();
			}
		}, 1500);

	}

	@Override
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				initList();
				myAdaptor.notifyDataSetChanged();
				onLoad();
			}
		}, 1500);

	}

	private void onLoad() {
		itemList.stopRefresh();
		itemList.stopLoadMore();
		itemList.setRefreshTime("刚刚");
	}

	class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.list1_back_btn:
				startActivity(new Intent(CommodiesLsitActivity.this,
						LoginActivity.class));
				finish();
				break;
			// 批量操作
			case R.id.batchManageBtn:
				
				break;
			case R.id.searchBtn:
				if (mSearchll.isShown()) {
					mSearchll.setVisibility(View.GONE);
				} else {
					mSearchll.setVisibility(View.VISIBLE);
				}
				break;
			case R.id.sortBtn:
				mResultList = MyListUtils.sortList(mList);
				Log.d("debug", "mList.size=" + mList.size());
				myAdaptor = new SimpleAdapter(CommodiesLsitActivity.this,
						mResultList, R.layout.simple_item, new String[] {
								"item_image", "item_name", "item_price" },
						new int[] { R.id.item_image, R.id.item_name,
								R.id.item_price });
				itemList.setAdapter(myAdaptor);
				break;
			case R.id.moreItemBtn:

				break;
			case R.id.serach:
				final String info = search.getText().toString().trim();
				if (!"".equals(info)) {
					mHandler.postDelayed(new Runnable() {
						@Override
						public void run() {
							mResultList = (ArrayList<HashMap<String, Object>>) getSearchList(info);
							Log.d("debug", "mList.size=" + mList.size());
							myAdaptor = new SimpleAdapter(
									CommodiesLsitActivity.this, mResultList,
									R.layout.simple_item, new String[] {
											"item_image", "item_name",
											"item_price" }, new int[] {
											R.id.item_image, R.id.item_name,
											R.id.item_price });
							itemList.setAdapter(myAdaptor);
						}
					}, 300);

				} else {
					// 没有输入的情况

				}
				break;
			default:
				break;
			}
		}

	}
}
