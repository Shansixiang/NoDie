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
    //����¼�������ע�����
    public void zhece(View v){
    	//����һ��������
    	 AlertDialog.Builder ad=new AlertDialog.Builder(SeconedActivity.this);
    	 View view = View.inflate(SeconedActivity.this, R.layout.zhuce, null);
    	 et_zhuce_password = (EditText) view.findViewById(R.id.et_zhuce_password);
    	 Button bt_queding = (Button) view.findViewById(R.id.bt_queding);
    	 //����¼����������õ�����
    	 bt_queding.setOnClickListener(new OnClickListener() {
			private SharedPreferences sharedPreferences;
			@Override
			public void onClick(View v) {
				//�õ��洢��
				 sharedPreferences = getSharedPreferences("password", MODE_PRIVATE);
				 //�õ��༭��
				 Editor edit = sharedPreferences.edit();
				 //���д�ֵ���ύ
				 edit.putString("password", et_zhuce_password.getText().toString()).commit();//�ύ
				 //����
		    	 show.dismiss();
			}
		});
    	 ad.setView(view);
    	 show = ad.show();
    
    }
    //����¼����ж����룬������ת��½
    public void login(View v){
    	SharedPreferences sharedPreferences = getSharedPreferences("password", MODE_PRIVATE);
    	String write_password = et_write_password.getText().toString();
    	 if (write_password!=null&&write_password.equals(sharedPreferences.getString("password", "0"))) {
			 //��ת
    		 startActivity(new Intent(SeconedActivity.this, BookType_Activity.class));
		}else {
			 Toast.makeText(SeconedActivity.this, "�ܾ�û���˰ɣ����붼���ˣ�", 0).show();
		}
    }
    
    
    
} 
