package org.jan.taobao.activities;

import java.io.InputStream;

import org.jan.taobao.activities.RegisterActivity.myEditOnKeyListener;
import org.jan.taobao.entity.TaobaoUser;
import org.jan.taobao.utils.SaxTaobaoUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity implements OnClickListener {
	public static final String LOGIN_TAG = "login_debug_log";
	private EditText userName;
	private EditText userPwd;
	private Button loginBtn;
	private TaobaoUser user = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login_layout);
//		getWindow().setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,
//				android.R.drawable.ic_menu_more);
		initView();
	}

	public void initView() {
		userName = (EditText) findViewById(R.id.user_r_name);
		userPwd = (EditText) findViewById(R.id.user_r_password);
		loginBtn = (Button) findViewById(R.id.login_btn);
		loginBtn.setOnClickListener(this);
		myEditOnKeyListener onkey = new myEditOnKeyListener();
		userName.setOnKeyListener(onkey);
		userPwd.setOnKeyListener(onkey);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_btn:
			if (validate()) {
				if (login()) {
					if (user != null) {
						Log.d(LOGIN_TAG, "login is succeed");
						Intent intent = new Intent(LoginActivity.this,
								MainActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						Bundle data = new Bundle();
						data.putParcelable("user", user);
						intent.putExtra("TaobaoUser", data);
						startActivity(intent);
						finish();
					} else {
						Log.d(LOGIN_TAG, "login is failed 2");
					}
				} else {
					Dialog alertDialog = new AlertDialog.Builder(
							LoginActivity.this).setTitle("提示")
							.setMessage("你输入的账号或密码有误！")
							.setIcon(R.drawable.ic_launcher).setPositiveButton("确定", new DialogInterface.OnClickListener() {   
			                    @Override   
			                    public void onClick(DialogInterface dialog, int which) { 
			                    	userName.setText("");
			                    	userPwd.setText("");
			                    }   
			                }).create();
					alertDialog.show();
					Log.d(LOGIN_TAG, "login is failed 1");
				}
			}
			break;
		default:
			break;
		}
	}

	/**
	 * 验证用户输入是否合法
	 */
	private boolean validate() {
		String name = userName.getText().toString().trim();
		if ("".equals(name)) {
			userName.setText("");
			userName.setHint("用户名不能为空");
			userName.setHintTextColor(getResources().getColor(R.color.red));
			return false;
		}
		String password = userPwd.getText().toString().trim();
		if ("".equals(password)) {
			userPwd.setText("");
			userPwd.setHint("密码不能为空");
			userPwd.setHintTextColor(getResources().getColor(R.color.red));
			return false;
		}
		return true;
	}

	/**
	 * 单机测试登陆（读取xml信息）
	 * 
	 * @return 是否读取成功
	 */
	private boolean login() {
		Log.d(LOGIN_TAG, "do login");
		String input_name = userName.getText().toString().trim();
		String input_pwd = userPwd.getText().toString().trim();
		Log.d(LOGIN_TAG, "login-> input name=" + input_name + ",pwd="
				+ input_pwd);
		try {
			InputStream is = openFileInput("taobao_user.xml");
			user = SaxTaobaoUtils.parsetoUser(is);
			Log.d(LOGIN_TAG, "user is " + user.toString());
			if (user != null) {
				if (input_name.equals(user.getUserName())
						&& input_pwd.equals(user.getUserPwd())) {
					return true;
				} else {
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	/**
	 * 启动注册界面
	 * 
	 * @param source
	 */
	public void registerFun(View source) {
		Log.d(LOGIN_TAG, "do registerFun");
		Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
		startActivity(intent);
//		finish();
	}
}
