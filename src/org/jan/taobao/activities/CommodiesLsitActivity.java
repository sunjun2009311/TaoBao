package org.jan.taobao.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jan.taobao.activities.XListView.IXListViewListener;
import org.jan.taobao.entity.TaobaoUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class CommodiesLsitActivity extends Activity implements IXListViewListener{

	XListView itemList;
	SimpleAdapter myAdaptor;
	private Button moreBtn;
	private ArrayList<HashMap<String, Object>> mList;
	private View moreView;
	private Handler mHandler;
	private int maxNum = 5;
	int index=1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.items_list_layout);
		needLogin();
		itemList = (XListView) findViewById(R.id.xListView);
		itemList.setPullLoadEnable(true);
		
//		moreView = getLayoutInflater().inflate(R.layout.moredata, null);
//		moreBtn = (Button) moreView.findViewById(R.id.more);
		myAdaptor = new SimpleAdapter(CommodiesLsitActivity.this, getList(),
				R.layout.simple_item, new String[] { "item_image", "item_name",
						"item_price" }, new int[] { R.id.item_image,
						R.id.item_name, R.id.item_price });
		itemList.setAdapter(myAdaptor);
		itemList.setXListViewListener(this);
		mHandler = new Handler();
	}
	
	public List<HashMap<String, Object>> getList(){
		mList = new ArrayList<HashMap<String, Object>>();
		for (int i = index; i < maxNum; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("item_image", R.drawable.taobao_shouji_1);
			map.put("item_name", i+".三星GT-S5830");
			map.put("item_price","￥1630.0");
			mList.add(map);
		}
		return mList;
	}
	
	List<HashMap<String, Object>> initList(){
		
		int count = myAdaptor.getCount();
		if(count+maxNum<=20){			
			for(int i=count+1;i<=count+maxNum;i++){
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("item_image", R.drawable.taobao_shouji_1);
				map.put("item_name", i+".三星GT-S5830");
				map.put("item_price","￥1630.0");
				mList.add(map);
			}
		}else{
			for(int i=count+1;i<=20;i++){
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("item_image", R.drawable.taobao_shouji_1);
				map.put("item_name", i+".三星GT-S5830");
				map.put("item_price","￥1630.0");
				mList.add(map);
			}
		}
		return mList;
		
	}
	/**
	 * 每次启动需要检查是否已经登陆
	 */
	public void needLogin(){
		if(isLogin()){
			return;
		}else{
			Intent intent = new Intent(CommodiesLsitActivity.this,LoginActivity.class);
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
			}else{
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
}
