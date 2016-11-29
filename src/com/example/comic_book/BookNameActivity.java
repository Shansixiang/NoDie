package com.example.comic_book;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class BookNameActivity extends Activity{
     private ListView listview_name;
     private ArrayList<Book> list_book;
     private ImageView image_name;
     
     Handler handler=new Handler(){
    	 public void handleMessage(android.os.Message msg) {
    		 listview_name.setAdapter(new Mylist());
    		 listview_name.setOnItemClickListener(new OnItemClickListener() {

 				@Override
 				public void onItemClick(AdapterView<?> parent, View view,
 						int position, long id) {
 					Intent intent = new Intent(BookNameActivity.this, ChapterActivity.class);
 					intent.putExtra("BookName", list_book.get(position).getName());
 				  //点击条目，跳转新界面，把 对应的书名 传过去
 					startActivity(intent);
 					
 				}
 			});
    	 };
     };
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.book_name_activity);
    	
    	
    	//得到intent传过来的值
    	Intent intent = getIntent();
    	String name = intent.getStringExtra("type");
    	TextView text_name = (TextView) findViewById(R.id.text_name);
    	text_name.setText(name);
    	getData(name);
    	//找到  列表  控件  用来展示书名
    	listview_name = (ListView) findViewById(R.id.listview_name);
    }
	
	class Mylist extends BaseAdapter{

		

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list_book.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view = View.inflate(BookNameActivity.this, R.layout.listview_name, null);
			image_name = (ImageView) view.findViewById(R.id.image_name);
			TextView text_book = (TextView) view.findViewById(R.id.text_book);
			TextView text_jieshao = (TextView) view.findViewById(R.id.text_jieshao);
			TextView text_date = (TextView) view.findViewById(R.id.text_date);
			TextView text_ara = (TextView) view.findViewById(R.id.text_ara);
			
			//设置数据
			text_book.setText(list_book.get(position).getName());
			text_jieshao.setText(list_book.get(position).getDes());
			text_date.setText(list_book.get(position).getLastUpdate());
			text_ara.setText(list_book.get(position).getArea());
			Myasynctask myasynctask = new Myasynctask();
			myasynctask.execute(list_book.get(position).getCoverImg());
			return view;
		}
		
	}
	
	// 从网络获取数据
		public void getData(final String type) {
			list_book = new ArrayList<Book>();
			new Thread() { // 耗时操作，在子线程中进行
				public void run() {
					try {
						URL url = new URL(
								"http://japi.juhe.cn/comic/book?name=&type="
										+ URLEncoder.encode(type)
										+ "&skip=&finish=&key=01d1a6bf384ed52133b1403c64a3d155");
						HttpURLConnection connection = (HttpURLConnection) url
								.openConnection();
						int code = connection.getResponseCode();
						if (code == 200) {
							InputStream inputStream = connection.getInputStream();
							ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
							int len = 0;
							byte[] buffer = new byte[1024];
							while ((len = inputStream.read(buffer)) != -1) {
								outputStream.write(buffer, 0, len);
							}
							String json = outputStream.toString();
							// 开始解析
							JSONObject jsonObject = new JSONObject(json);
							JSONObject jsonObject2 = jsonObject
									.getJSONObject("result");
							JSONArray jsonArray = jsonObject2
									.getJSONArray("bookList");
							for (int i = 0; i < jsonArray.length(); i++) {
								JSONObject jsonObject3 = jsonArray.getJSONObject(i);
								String name = jsonObject3.getString("name");
								String area = jsonObject3.getString("area");
								String des = jsonObject3.getString("des");
								String lastUpdate = jsonObject3
										.getString("lastUpdate");
								String coverImg = jsonObject3.getString("coverImg");
								Book book = new Book(name, area, des, lastUpdate,
										coverImg);
								list_book.add(book);
							}
							// 发送一个空消息
							handler.sendEmptyMessage(0);

						}

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				};
			}.start();
		}
		
		class Myasynctask extends AsyncTask<String, Void, Bitmap> {

			private Bitmap bitmap;
			@Override   //后台方法，子线程
			protected Bitmap doInBackground(String... params) {//可变长参数
				// TODO Auto-generated method stub
				URL url;
				try {
					url = new URL(params[0]);
				
				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
				int code = connection.getResponseCode();
				if (code==200) {
					//得到数据的输入流
					InputStream inputStream = connection.getInputStream();
					bitmap = BitmapFactory.decodeStream(inputStream);
				}
				
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return bitmap;
			}

	        @Override  //前台方法，主线程
	        protected void onPostExecute(Bitmap result) {
	        	// TODO Auto-generated method stub
	        	super.onPostExecute(result);
	        	image_name.setImageBitmap(result);
	        }
		}
}
