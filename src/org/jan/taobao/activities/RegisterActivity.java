package org.jan.taobao.activities;

import java.io.FileOutputStream;

import org.jan.taobao.entity.TaobaoUser;
import org.jan.taobao.utils.SaxTaobaoUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends Activity implements OnClickListener {

	private EditText user_name;
	private EditText user_pwd;
	private EditText indentifyCode;
	private Button register_btn;
	private Button exitBtn;
	private TaobaoUser user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_LEFT_ICON);
		setContentView(R.layout.register_layout);
		getWindow().setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,
				android.R.drawable.ic_menu_more);
		inieView();

	}

	private void inieView() {
		user_name = (EditText) findViewById(R.id.user_r_name);
		user_pwd = (EditText) findViewById(R.id.user_r_password);
		indentifyCode = (EditText) findViewById(R.id.indentifyingcode);
		register_btn = (Button) findViewById(R.id.register_btn);
		exitBtn = (Button) findViewById(R.id.exit_btn);
		register_btn.setOnClickListener(this);
		exitBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View source) {
		switch (source.getId()) {
		case R.id.register_btn:
			// TODO ע�Ṧ��
			if (validate()) {
				register();
				Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
				startActivity(intent);
				finish();
			}
			break;
		case R.id.exit_btn:
			finish();
			break;
		default:
			break;
		}
	}

	/**
	 * ��֤�û������Ƿ�Ϸ�
	 */
	private boolean validate() {
		String name = user_name.getText().toString().trim();
		if ("".equals(name)) {
			user_name.setText("");
			user_name.setHint("�û�������Ϊ��");
			user_name.setHintTextColor(getResources().getColor(R.color.red));
			return false;
		}
		String password = user_pwd.getText().toString().trim();
		if ("".equals(password)) {
			user_pwd.setText("");
			user_pwd.setHint("���벻��Ϊ��");
			user_pwd.setHintTextColor(getResources().getColor(R.color.red));
			return false;
		}
		String identifyCode = indentifyCode.getText().toString().trim();
		if ("".equals(identifyCode)) {
			indentifyCode.setText("");
			indentifyCode.setHint("����д...");
			indentifyCode
					.setHintTextColor(getResources().getColor(R.color.red));
			return false;
		}
		return true;
	}
	/**
	 * �û�ע�����
	 * @return �Ƿ�ע��ɹ�
	 */
	private boolean register(){
		String userName=user_name.getText().toString();
		String pwd = user_pwd.getText().toString();
		user = new TaobaoUser();
		user.setUserName(userName);
		user.setUserPwd(pwd);
		String userInfo=null;
		try {
			userInfo=SaxTaobaoUtils.parsetoXml(user);
			FileOutputStream fos=openFileOutput("taobao_user.xml", MODE_PRIVATE);
			fos.write(userInfo.getBytes("utf-8"));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
}
