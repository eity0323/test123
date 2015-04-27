package com.example.cpplayer.ui.helper;
/**
 * @author sien
 * @description 视频播放管理�?
 */
import java.util.ArrayList;
import java.util.List;

import com.example.cpplayer.ui.views.CPVideoView;

import android.net.Uri;

public class VideoViewHelper {
	private static VideoViewHelper instance;
	private List<CPVideoView> videoList = new ArrayList<CPVideoView>();
	
	public static VideoViewHelper getInstance(){
		if(instance == null){
			synchronized (VideoViewHelper.class) {
				if (instance == null) {
					instance = new VideoViewHelper();
				}
			}
		}
		return instance;
	}
	
	public void bind(CPVideoView videoView,String path){
		if(videoView != null){
			Uri videoUri = Uri.parse(path);
			videoView.setVideoURI(videoUri);
			
			videoView.start();
			
			videoList.add(videoView);
		}
	}
	
	public void restart(CPVideoView videoView){
		if(videoView != null){
			videoView.seekTo(0);
			videoView.start();
		}
	}
	
	public void stop(CPVideoView videoView){
		if(videoView != null){
			videoView.stopPlayback();
		}
	}
	
	public void reset(){
		for(CPVideoView view : videoList){
			view.stopPlayback();
		}
		
		videoList.clear();
	}
	
}
