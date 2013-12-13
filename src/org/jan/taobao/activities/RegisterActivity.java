package org.jan.taobao.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends Activity implements OnClickListener{

	private EditText user_name;
	private EditText user_pwd;
	private EditText indentifyCode;
	private Button register_btn;
	private Button exitBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_layout);
		inieView();
		
	}
	
	private void inieView(){
		user_name=(EditText) findViewById(R.id.user_r_name);
		user_pwd= (EditText) findViewById(R.id.user_r_password);
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
			//TODO ×¢²á¹¦ÄÜ
			break;

		default:
			break;
		}
	}
}
