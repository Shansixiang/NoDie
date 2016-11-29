package com.example.comic_book;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SeconedActivity extends Activity {
    private EditText et_zhuce_password;
	private Button login;
	private EditText et_write_password;
	private AlertDialog show;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_seconed);
    	
    	et_write_password = (EditText) findViewById(R.id.et_write_password);
    	login = (Button) findViewById(R.id.login);
    	Button zhuce = (Button) findViewById(R.id.zhuce);
    	
        
        
    }
    //点击事件：进入注册界面
    public void zhece(View v){
    	//创建一个弹出框
    	 AlertDialog.Builder ad=new AlertDialog.Builder(SeconedActivity.this);
    	 View view = View.inflate(SeconedActivity.this, R.layout.zhuce, null);
    	 et_zhuce_password = (EditText) view.findViewById(R.id.et_zhuce_password);
    	 Button bt_queding = (Button) view.findViewById(R.id.bt_queding);
    	 //点击事件：保存设置的密码
    	 bt_queding.setOnClickListener(new OnClickListener() {
			private SharedPreferences sharedPreferences;
			@Override
			public void onClick(View v) {
				//得到存储器
				 sharedPreferences = getSharedPreferences("password", MODE_PRIVATE);
				 //得到编辑器
				 Editor edit = sharedPreferences.edit();
				 //进行存值并提交
				 edit.putString("password", et_zhuce_password.getText().toString()).commit();//提交
				 //返回
		    	 show.dismiss();
			}
		});
    	 ad.setView(view);
    	 show = ad.show();
    
    }
    //点击事件：判断密码，进行跳转登陆
    public void login(View v){
    	SharedPreferences sharedPreferences = getSharedPreferences("password", MODE_PRIVATE);
    	String write_password = et_write_password.getText().toString();
    	 if (write_password!=null&&write_password.equals(sharedPreferences.getString("password", "0"))) {
			 //跳转
    		 startActivity(new Intent(SeconedActivity.this, BookType_Activity.class));
		}else {
			 Toast.makeText(SeconedActivity.this, "很久没来了吧，密码都忘了！", 0).show();
		}
    }
    
    
    
} 
