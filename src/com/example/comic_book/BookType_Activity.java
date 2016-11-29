package com.example.comic_book;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class BookType_Activity extends Activity{
	 int i=0;
    private ArrayList<View> list_daohang;
    int [] picture=new int []{R.drawable.a,R.drawable.b,R.drawable.d,R.drawable.c};
	private ViewPager viewpager_small_daohang;
	private GridView gridView;
	private ArrayList<String> list_type;
	
	Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			gridView.setAdapter(new Mygridview());
		};
	};
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.booktype_activity);
    	
    	list_daohang = new ArrayList<View>();
    	for (int i = 0; i <4; i++) {
			View v = View.inflate(BookType_Activity.this, R.layout.view, null);
			v.setBackgroundResource(picture[i]);
			list_daohang.add(v);
		}
    	getData();
    	viewpager_small_daohang = (ViewPager) findViewById(R.id.viewpager_small_daohang);
    	viewpager_small_daohang.setAdapter(new Myviewpager());
    	
    	Timer timer=new Timer();
    	timer.schedule(new TimerTask() {
    		
			@Override
			public void run() {
				// TODO Auto-generated method stub
				  runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						viewpager_small_daohang.setCurrentItem(i);
						i++;
					}
				});
			}
		}, 0, 1000);
    	
    	gridView = (GridView) findViewById(R.id.gridView);
    	//列表的条目点击事件
    	gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(BookType_Activity.this, BookNameActivity.class);
				//intent 传值，将类型传过去
				intent.putExtra("type", list_type.get(position));
				//点击后跳转新界面
				startActivity(intent);
				
				 
			}
		});
    	
    }
	
	class Mygridview extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list_type.size();
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
			View inflate = View.inflate(BookType_Activity.this, R.layout.gridview, null);
			ImageView image_type = (ImageView) inflate.findViewById(R.id.image_type);
			TextView text_type = (TextView) inflate.findViewById(R.id.text_type);
			//设置数据
			image_type.setImageResource(picture[position]);
			text_type.setText(list_type.get(position));
			return inflate;
		}
		
	}
	
	
    class Myviewpager extends PagerAdapter{
  
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return Integer.MAX_VALUE;
		}
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
        	// TODO Auto-generated method stub
        	 
        	container.addView(list_daohang.get(position%4));
        	return  list_daohang.get(position%4);
        }
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0==arg1;
		}
    	@Override
    	public void destroyItem(ViewGroup container, int position, Object object) {
    		// TODO Auto-generated method stub
    		//super.destroyItem(container, position, object);
    		container.removeView((View) object);
    	}
    }
  //从网络获取数据
  	public void getData(){
  		 list_type = new ArrayList<String>();
  		new Thread(){ //请求数据，耗时操作，在子线程中进行
  			 public void run() {
  				 URL url;
  				try {
  					url = new  URL("http://japi.juhe.cn/comic/category?key=01d1a6bf384ed52133b1403c64a3d155");
  					 //打开网络连接
  					 HttpURLConnection connection = (HttpURLConnection) url.openConnection();
  					 //得到响应码
  					 int code = connection.getResponseCode();
  					 //判断
  					 if (code==200) {
  						InputStream inputStream = connection.getInputStream();
  						//将流转换成字符串
  						ByteArrayOutputStream  OutputStream = new ByteArrayOutputStream();
  						int len=0;
  						byte[] buffer=new byte[1024];
  						while ((len=inputStream.read(buffer))!=-1) {
  							OutputStream.write(buffer, 0, len);
  						}
  						String json = OutputStream.toString(); //转换完成
  						//开始解析
  						JSONObject jsonObject = new JSONObject(json);
  						JSONArray jsonArray = jsonObject.getJSONArray("result");
  						
  					    for (int i = 0; i < jsonArray.length(); i++) {
  					    	String string = jsonArray.getString(i);
  					    	list_type.add(string);
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
