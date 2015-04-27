package com.example.cpplayer.ui.views;
/**
 * @author sien
 * @description 列表项布�?���?
 */
import com.example.cpplayer.R;
import com.example.cpplayer.ui.helper.VideoViewHelper;
import com.example.cpplayer.ui.model.VideoVO;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SimpleCPListItemView extends LinearLayout {
	Context mcontext;
	TextView textView;
	ImageView imageView;
	CPVideoView videoView;
	FrameLayout container;
	LinearLayout overlap;
	
	CPListItemClickListener listener;
	VideoVO curItemData;
	
	public SimpleCPListItemView(Context context) {
		super(context);
		
		mcontext = context;
		
		initLayout();
	}

	public SimpleCPListItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		mcontext = context;

		initLayout();
	}
	
	public void setCPListItemClickListener(CPListItemClickListener listen){
		listener = listen;
	}
	
	public void bind(VideoVO itemData){
		if(itemData != null){
			curItemData = itemData;
					
			textView.setText(itemData.name);
			
			toggleShowVideo(true);
			
			ImageLoader.getInstance().loadImage(itemData.imgurl, imglistener);
			if(!TextUtils.isEmpty(itemData.videourl)){
				VideoViewHelper.getInstance().bind(videoView, itemData.videourl);
			}
			
			
//			if(!TextUtils.isEmpty(itemData.videourl)){
//				toggleShowVideo(true);
//				
//				videoView.setBackgroundResource(R.drawable.logo_pptv);
//				VideoViewHelper.getInstance().bind(videoView, itemData.videourl);
//			}else{
//				toggleShowVideo(false);
//				ImageLoader.getInstance().displayImage(itemData.imgurl, imageView);
//				
//			}
		}
		
	}
	
	public void restart(){
		if(videoView != null){
			videoView.start();
		}
	}
	
	private void initLayout(){
		View v = LayoutInflater.from(mcontext).inflate(R.layout.simple_cplist_item,null); 
		textView = (TextView)v.findViewById(R.id.itemTitle);
		imageView = (ImageView)v.findViewById(R.id.itemImage);
		videoView = (CPVideoView)v.findViewById(R.id.itemVideo);
		container = (FrameLayout)v.findViewById(R.id.itemContainer);
		overlap = (LinearLayout)v.findViewById(R.id.videoOverlap);
		
		videoView.setOnPreparedListener(prepareListener);
		videoView.setOnCompletionListener(videoCompletionListener);
		videoView.setOnErrorListener(videoErrorListener);
		
		imageView.setOnClickListener(clickListener);
		overlap.setOnClickListener(clickListener);
		
		videoView.setZOrderMediaOverlay(false);
		videoView.setZOrderOnTop(false);
		
		addView(v);
	}
	
	private void toggleShowVideo(boolean show){
		if(show){
			imageView.setVisibility(View.GONE);
			videoView.setVisibility(View.VISIBLE);
			container.setVisibility(View.VISIBLE);
		}else{
			imageView.setVisibility(View.VISIBLE);
			videoView.setVisibility(View.GONE);
			container.setVisibility(View.GONE);
		}
	}
	
	private ImageLoadingListener imglistener = new ImageLoadingListener() {
		
		@Override
		public void onLoadingStarted(String arg0, View arg1) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
			// TODO Auto-generated method stub
			videoView.setBackgroundDrawable( new BitmapDrawable(arg2) );
		}
		
		@Override
		public void onLoadingCancelled(String arg0, View arg1) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private OnPreparedListener prepareListener = new OnPreparedListener() {
		
		@Override
		public void onPrepared(MediaPlayer mp) {
			videoView.setBackgroundDrawable(null);
		}
	};
	
	private OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if(listener != null){
				listener.onItemClick(curItemData);
			}
		}
		
	};
	
	private OnErrorListener videoErrorListener = new OnErrorListener() {
		
		@Override
		public boolean onError(MediaPlayer mp, int what, int extra) {
			Log.i("SimpleCPListItemView", "视频播放错误");
			
			toggleShowVideo(false);
			ImageLoader.getInstance().displayImage(curItemData.imgurl, imageView);
			return true;
		}
	};
	
	private OnCompletionListener videoCompletionListener = new OnCompletionListener() {
		
		@Override
		public void onCompletion(MediaPlayer mp) {
			VideoViewHelper.getInstance().restart(videoView);
		}
	};
	
	public interface CPListItemClickListener{
		public void onItemClick(VideoVO itemData);
	}
}
