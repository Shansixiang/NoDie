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
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ChapterActivity extends Activity{
    
	   //handler
    Handler handler=new Handler(){
    	public void handleMessage(android.os.Message msg) {
    		//给chapter_listview设置适配器
    		chapter_listview.setAdapter(new Mychapter_listview());
    		//chapter_listview的条目点击事件
    		chapter_listview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					Intent intent = new  Intent(ChapterActivity.this, ContentActivity.class);
					//传值,将漫画名和id传过去
					intent.putExtra("book_name",book_name );
					intent.putExtra("id",list_chapter.get(position).getId() );
					startActivity(intent);
				}
			});
    	};
    };

    private ArrayList<Chapter> list_chapter;
	private ListView chapter_listview;

	private String book_name;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.chapter_activity);
    	//找到chapter_listview的控件
    	chapter_listview = (ListView) findViewById(R.id.chapter_listview);
    	Intent intent = getIntent();
    	book_name = intent.getStringExtra("BookName");
    	 TextView text_ = (TextView) findViewById(R.id.text_);
    	 text_.setText(book_name);
    	//获取数据
    	getData(book_name);
    	System.out.println(list_chapter);
    	
    	
    }
	 //chapter_listview的适配器类
	class Mychapter_listview extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
	return list_chapter.size();
			 
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
			//优化
			if (convertView==null) {
				convertView=View.inflate(ChapterActivity.this, R.layout.chapter_listview,null);
			}
			//找控件
			TextView text_chapter_name = (TextView)convertView.findViewById(R.id.text_chapter_name);
			//设置数据
			text_chapter_name.setText(list_chapter.get(position).getName());
		 
			return convertView;
		}
		
	}
    //从网络上获取数据
    public void getData(final String bookName){
    	//创建一个集合，放Chapter对象
    	list_chapter = new ArrayList<Chapter>();
    	new Thread(){
    		public void run() {
    			URL url;
				try {
					url = new URL("http://japi.juhe.cn/comic/chapter?comicName="+URLEncoder.encode(bookName)+"&skip=&key=01d1a6bf384ed52133b1403c64a3d155");
				
				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
				//得到响应码
				int code = connection.getResponseCode();
				if (code==200) { //成功
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
					JSONObject jsonObject2 = jsonObject.getJSONObject("result");
					JSONArray jsonArray = jsonObject2.getJSONArray("chapterList");
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject3 = jsonArray.getJSONObject(i);
						String name = jsonObject3.getString("name");
						String id = jsonObject3.getString("id");
						//创建一个Chapter对象
						Chapter chapter = new Chapter(name, id);
						list_chapter.add(chapter);
					}
					handler.sendEmptyMessage(0);
				}
    			
    			
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		};
    	}.start();
    }
}
