package org.jan.taobao.activities;

import org.jan.taobao.entity.TaobaoUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private ListView itemlist;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		needLogin();
	}
	/**
	 * 每次启动需要检查是否已经登陆
	 */
	public void needLogin(){
		if(isLogin()){
			return;
		}else{
			Intent intent = new Intent(MainActivity.this,LoginActivity.class);
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
				Toast.makeText(MainActivity.this,
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
