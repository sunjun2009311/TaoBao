package org.jan.taobao.activities;

import java.io.FileOutputStream;
import java.util.Arrays;

import org.jan.taobao.entity.TaobaoUser;
import org.jan.taobao.utils.CheckGetUtil;
import org.jan.taobao.utils.SaxTaobaoUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends Activity implements OnClickListener {

	private EditText mUserName;
	private EditText mUserPwd1;
	private EditText mUserPwd2;
	private EditText indentifyCode;
	private Button registerBtn;
	private TaobaoUser user;
	private Button backBtn;
	private CheckView mCheckView;
	// 验证码：
	int[] checkNum = { 0, 0, 0, 0 };
	private String mCheckCode=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register_layout);
//		getWindow().setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,
//				android.R.drawable.ic_menu_more);
		inieView();

	}

	private void inieView() {
		mUserName = (EditText) findViewById(R.id.user_r_name);
		mUserName.setOnKeyListener(new myEditOnKeyListener());
		mUserPwd1 = (EditText) findViewById(R.id.user_r_password1);
		mUserPwd1.setOnKeyListener(new myEditOnKeyListener());
		mUserPwd2 = (EditText) findViewById(R.id.user_r_password2);
		mUserPwd2.setOnKeyListener(new myEditOnKeyListener());
		indentifyCode = (EditText) findViewById(R.id.indentifyingcode);
		indentifyCode.setOnKeyListener(new myEditOnKeyListener());
		registerBtn = (Button) findViewById(R.id.register_btn);
		registerBtn.setOnClickListener(this);
		backBtn = (Button) findViewById(R.id.back_btn);
		backBtn.setOnClickListener(this);
		mCheckView = (CheckView) findViewById(R.id.checkView);
		checkNum = CheckGetUtil.getCheckNum();
		mCheckView.setCheckNum(checkNum);
		mCheckView.invaliChenkNum();
	
		Log.d("debug",getCheckCode());
//		Log.d("debug", checkNum[0]+","+checkNum[1]+","+checkNum[2]+","+checkNum[3]);
	}
	private String getCheckCode(){
		checkNum = mCheckView.getCheckNum();
		StringBuilder builder = new StringBuilder();
		for(int i:checkNum){
			builder.append(i+"");
		}
		mCheckCode = builder.toString();
		return mCheckCode;
	}
	@Override
	public void onClick(View source) {
		switch (source.getId()) {
		case R.id.register_btn:
			// TODO 注册功能
			if (validate()) {
				String password1 = mUserPwd1.getText().toString().trim();
				String password2 = mUserPwd2.getText().toString().trim();
				if (password1.equals(password2)) {
					register();
					Intent intent = new Intent(RegisterActivity.this,
							MainActivity.class);
					startActivity(intent);
					finish();
				} else {
					mUserPwd1.setText("");
					mUserPwd1.setHint("两次密码不一致");
					mUserPwd1.setHintTextColor(getResources().getColor(
							R.color.red));
					mUserPwd2.setText("");
				}
			}
			break;
		case R.id.back_btn:
			finish();
			break;
		default:
			break;
		}
	}

	/**
	 * 验证用户输入是否合法
	 */
	private boolean validate() {
		String name = mUserName.getText().toString().trim();
		if ("".equals(name)) {
			mUserName.setText("");
			mUserName.setHint("用户名不能为空");
			mUserName.setHintTextColor(getResources().getColor(R.color.red));
			return false;
		}
		String password1 = mUserPwd1.getText().toString().trim();
		if ("".equals(password1)) {
			mUserPwd1.setText("");
			mUserPwd1.setHint("密码不能为空");
			mUserPwd1.setHintTextColor(getResources().getColor(R.color.red));
			return false;
		}
		String password2 = mUserPwd2.getText().toString().trim();
		if ("".equals(password2)) {
			mUserPwd2.setText("");
			mUserPwd2.setHint("密码不能为空");
			mUserPwd2.setHintTextColor(getResources().getColor(R.color.red));
			return false;
		}
		String identifyCode = indentifyCode.getText().toString().trim();
		if ("".equals(identifyCode)) {
			indentifyCode.setText("");
			indentifyCode.setHint("请填写...");
			indentifyCode
					.setHintTextColor(getResources().getColor(R.color.red));
			return false;
		}else{
			if(identifyCode.equals(getCheckCode())){
				return true;
			}else{
				indentifyCode.setText("");
				indentifyCode.setHint("填写不正确");
				indentifyCode
						.setHintTextColor(getResources().getColor(R.color.red));
				return false;
			}
		}
	}

	/**
	 * 用户注册服务
	 * 
	 * @return 是否注册成功
	 */
	private boolean register() {
		String userName = mUserName.getText().toString();
		String pwd = mUserPwd1.getText().toString();
		user = new TaobaoUser();
		user.setUserName(userName);
		user.setUserPwd(pwd);
		String userInfo = null;
		try {
			userInfo = SaxTaobaoUtils.parsetoXml(user);
			FileOutputStream fos = openFileOutput("taobao_user.xml",
					Context.MODE_PRIVATE);
			fos.write(userInfo.getBytes("utf-8"));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	static class myEditOnKeyListener implements OnKeyListener {

		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_ENTER
					&& event.getAction() == KeyEvent.ACTION_DOWN) {
				InputMethodManager imm = (InputMethodManager) v.getContext()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				if (imm.isActive()) {
					imm.hideSoftInputFromWindow(v.getApplicationWindowToken(),
							0);
				}
				return true;
			}
			return false;
		}

	}
}
