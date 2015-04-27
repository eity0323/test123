package com.example.cpplayer.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.example.cpplayer.R;
import com.example.cpplayer.ui.adapter.SimpleCPListAdapter;
import com.example.cpplayer.ui.helper.APPEvent;
import com.example.cpplayer.ui.model.VideoVO;
import com.example.cpplayer.ui.views.SimpleCPListItemView.CPListItemClickListener;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import de.greenrobot.event.EventBus;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ListView;

public class CPMainActivity extends Activity {
	private SimpleCPListAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cpmain_activity);
		
		EventBus.getDefault().register(this);
		
		
		DisplayImageOptions options = new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.pub_no_image).showImageOnFail(R.drawable.pub_no_image)
				.showImageOnLoading(R.drawable.pub_no_image).cacheInMemory(true).cacheOnDisk(true).build();

		// 初始化图片下载组�?
		ImageLoaderConfiguration imageconfig = new ImageLoaderConfiguration.Builder(getApplicationContext()).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory().diskCacheSize(50 * 1024 * 1024).diskCacheFileCount(200)
				.diskCacheFileNameGenerator(new Md5FileNameGenerator()).defaultDisplayImageOptions(options).build();
		ImageLoader.getInstance().init(imageconfig);
		
		initLayout();
	}
	
	private void initLayout(){
		//listview布局---------------
		ListView listview = (ListView)findViewById(R.id.cplistview);
		
		adapter = new SimpleCPListAdapter(this);
		adapter.setCPListItemClickListener(new CPListItemClickListener() {
			
			@Override
			public void onItemClick(VideoVO itemData) {
				if(itemData != null){
					String path = itemData.videourl;//Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + "tempvideo0.mp4";
					Intent intent = new Intent(getApplicationContext(), VideoPlayerActivity.class);
					intent.putExtra("path", path);
					intent.putExtra("title", "tempvideo0");
					startActivity(intent);
				}
			}
		});
		
		listview.setAdapter(adapter);
		
		List<VideoVO> datas = initData();
		adapter.setDataSource(datas);
	}
	
	public void onEventMainThread(APPEvent.freshVideoEvent event){
		if(event != null){
			adapter.restart();
		}
	}
	
	private List<VideoVO> initData(){
		List<VideoVO> list = new ArrayList<VideoVO>();
		
		VideoVO item = new VideoVO();
		item.imgurl = "http://221.7.133.74:15202/messenger/suneee/download?key=201504011605235374461113153.jpg";
		item.name = "title1";
		//item.videourl = "android.resource://" + getPackageName() + "/" + R.raw.sound;
				//Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + "guixiangyuan.mp4";//"http://221.7.133.74:15202/messenger/uploadfile/2015042211472519889050331232.mp4";
		item.id = "1";
		list.add(item);
		
		item = new VideoVO();
		item.imgurl = "http://221.7.133.74:15202/messenger/suneee/download?key=201503301128538455213183543.jpg";
		item.name = "路口在减";
		item.videourl = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + "sound.mp4";//"android.resource://" + getPackageName() + "/" + R.raw.tempvideo0;
		item.id = "1";
		list.add(item);
		
		item = new VideoVO();
		item.imgurl = "http://221.7.133.74:15202/messenger/suneee/download?key=201503301128538455213183543.jpg";
		item.name = "路口在减";
		item.videourl = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + "guixiangyuan.mp4";//"android.resource://" + getPackageName() + "/" + R.raw.guixiangyuan;
		item.id = "1";
		list.add(item);
		
		
		item = new VideoVO();
		item.imgurl = "http://221.7.133.74:15202/messenger/suneee/download?key=201503301128538455213183543.jpg";
		item.name = "路口在减";
		item.videourl = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + "sound.mp4";//"android.resource://" + getPackageName() + "/" + R.raw.sound;
		item.id = "1";
		list.add(item);
		
		
		item = new VideoVO();
		item.imgurl = "http://221.7.133.74:15202/messenger/suneee/download?key=201503301128538455213183543.jpg";
		item.name = "路口在减";
		item.videourl = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + "tempvideo0.mp4";//"android.resource://" + getPackageName() + "/" + R.raw.guixiangyuan;
		item.id = "1";
		list.add(item);
		
		item = new VideoVO();
		item.imgurl = "http://221.7.133.74:15202/messenger/suneee/download?key=201503301128538455213183543.jpg";
		item.name = "路口在减";
		item.videourl = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + "tempvideo0.mp4";//"android.resource://" + getPackageName() + "/" + R.raw.tempvideo0;
		item.id = "1";
		list.add(item);
		
		item = new VideoVO();
		item.imgurl = "http://221.7.133.74:15202/messenger/suneee/download?key=201503301128538455213183543.jpg";
		item.name = "路口在减";
		item.videourl = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + "tempvideo0.mp4";//"android.resource://" + getPackageName() + "/" + R.raw.guixiangyuan;
		item.id = "1";
		list.add(item);
		return list;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		EventBus.getDefault().unregister(this);
	}
	
}
