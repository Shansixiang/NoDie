package com.example.comic_book;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class MainActivity extends Activity {

    private ArrayList<View> list;
    int [] picture=new int []{R.drawable.a,R.drawable.b,R.drawable.d,R.drawable.c};
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();//初始化数据
        ViewPager viewpager = (ViewPager) findViewById(R.id.viewpager);
        viewpager.setAdapter(new MyViewPager());
        //页面改变监听
        viewpager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				if (arg0==3) {
					SystemClock.sleep(1000);
					startActivity(new Intent(MainActivity.this, SeconedActivity.class));
				}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
    }
     //初始化数据的方法
   private void initData() {
		 list = new ArrayList<View>();
		 for (int i = 0; i <4; i++) {
			 View view = View.inflate(MainActivity.this, R.layout.view, null);
			 view.setBackgroundResource(picture[i]);
			 list.add(view);
		}
		
		
	}

class  MyViewPager extends PagerAdapter{

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}
     @Override
    public Object instantiateItem(ViewGroup container, int position) {
    	// TODO Auto-generated method stub
    	 container.addView(list.get(position));
    	return list.get(position) ;
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
  
}
