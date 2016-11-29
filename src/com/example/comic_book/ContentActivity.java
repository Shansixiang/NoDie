package com.example.comic_book;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class ContentActivity extends Activity {
	private ListView content_listview;
	private ArrayList<Image> list_image;
	private ImageView image_content;
	Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			//设置适配器
			content_listview.setAdapter(new Mycontent_listview());
		};
	};
	private ImageLoader instance;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content_activity);
		instance = ImageLoader.getInstance();
		//实例化
		instance.init(new ImageLoaderConfiguration.Builder(ContentActivity.this).build());
		//得到传过来的，书名，id
		Intent intent = getIntent();
		String book_name = intent.getStringExtra("book_name");
		String id=intent.getStringExtra("id");
		//找到content_listview控件
		content_listview = (ListView) findViewById(R.id.content_listview);
		//获取数据
		getData(book_name,id);
	}
	//适配器类
	class Mycontent_listview extends BaseAdapter{

		

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list_image.size();
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
			if (convertView==null) {
				convertView=View.inflate(ContentActivity.this, R.layout.content_listview, null);
			}
			image_content = (ImageView) convertView.findViewById(R.id.image_content);
//			MyasyncTask myasyncTask = new MyasyncTask();
//			myasyncTask.execute(list_image.get(position).getImageUrl());
			
			//初始化
			instance.displayImage(list_image.get(position).getImageUrl(), image_content);
			return  convertView;
		}
		
	}
	
//	class MyasyncTask extends AsyncTask<String, Void, Bitmap>{
//
//		private Bitmap bitmap;
//
//		@Override  //后台方法，子线程
//		protected Bitmap doInBackground(String... params) {
//			URL url;
//			try {
//				url = new URL(params[0]);
//			
//			HttpURLConnection connection = (HttpURLConnection) url
//					.openConnection();
//			int code = connection.getResponseCode();
//			if (code==200) {
//				//得到数据的输入流
//				InputStream inputStream = connection.getInputStream();
//				bitmap = BitmapFactory.decodeStream(inputStream);
//			}
//			
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			return bitmap;
//		}
//		 @Override
//		protected void onPostExecute(Bitmap result) {
//			// TODO Auto-generated method stub
//			super.onPostExecute(result);
//			image_content.setImageBitmap(result);
//		}
//	 }
	 
     //从网络获取数据
	private void getData(final String name,final String id) {
		list_image = new ArrayList<Image>();
		 new Thread(){  //耗时操作，在子线程中进行
			 public void run() {
				   try {
					URL url = new URL("http://japi.juhe.cn/comic/chapterContent?comicName="+URLEncoder.encode(name)+"&id="+id+"&key=01d1a6bf384ed52133b1403c64a3d155");
				    //打开网络连接
					HttpURLConnection Connection = (HttpURLConnection) url.openConnection();
				    //得到响应码
					int code = Connection.getResponseCode();
					if (code==200) {
						InputStream inputStream = Connection.getInputStream();
						ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
						int len=0; byte[] buffer=new byte[1024];
						while ((len=inputStream.read(buffer))!=-1) {
							outputStream.write(buffer, 0, len);
						}
						String json = outputStream.toString();
						//开始解析数据
						JSONObject jsonObject = new JSONObject(json);
						JSONObject jsonObject2 = jsonObject.getJSONObject("result");
						JSONArray jsonArray = jsonObject2.getJSONArray("imageList");
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject jsonObject3 = jsonArray.getJSONObject(i);
							String imageUrl = jsonObject3.getString("imageUrl");
							Image image = new Image(imageUrl);
							list_image.add(image);
						}
						//发送空消息
						handler.sendEmptyMessage(0);
					}
				   
				   
				   } catch ( Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 };
		 }.start();
		
	}
	 
}
