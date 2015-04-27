/*
 * Copyright (C) 2011 VOV IO (http://vov.io/)
 */

package com.example.cpplayer.ui;

import com.example.cpplayer.R;
import com.example.cpplayer.ui.helper.APPEvent;
import com.example.cpplayer.ui.helper.APPEvent.freshVideoEvent;
import com.example.cpplayer.ui.views.CPVideoView;

import de.greenrobot.event.EventBus;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnInfoListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;

public class VideoPlayerActivity extends Activity implements OnCompletionListener, OnInfoListener {

	private String mPath;
	private CPVideoView mVideoView;

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);

		// ~~~ 获取播放地址和标题
		Intent intent = getIntent();
		mPath = intent.getStringExtra("path");
		if (TextUtils.isEmpty(mPath)) {
			mPath = Environment.getExternalStorageDirectory() + "/video/你太猖狂.flv";

		} else if (intent.getData() != null)
			mPath = intent.getData().toString();

		// ~~~ 绑定控件
		setContentView(R.layout.videoview);
		mVideoView = (CPVideoView) findViewById(R.id.surface_view);

		// ~~~ 绑定事件
		mVideoView.setOnCompletionListener(this);
		
		mVideoView.needSound = true;

		// ~~~ 绑定数据
		if (mPath.startsWith("http:"))
			mVideoView.setVideoURI(Uri.parse(mPath));
		else
			mVideoView.setVideoPath(mPath);
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (mVideoView != null)
			mVideoView.pause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (mVideoView != null)
			mVideoView.start();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mVideoView != null)
			mVideoView.stopPlayback();
		
		EventBus.getDefault().post(new APPEvent.freshVideoEvent("", freshVideoEvent.STATUS_SUCCESS));
	}

	private void stopPlayer() {
		if (mVideoView != null)
			mVideoView.pause();
	}

	private void startPlayer() {
		if (mVideoView != null)
			mVideoView.start();
	}

	private boolean isPlaying() {
		return mVideoView != null && mVideoView.isPlaying();
	}

	@Override
	public boolean onInfo(MediaPlayer mp, int what, int extra) {
		switch (what) {
		case MediaPlayer.MEDIA_INFO_BUFFERING_START:
			//开始缓存，暂停播放
			if (isPlaying()) {
				stopPlayer();
			}
			break;
		case MediaPlayer.MEDIA_INFO_BUFFERING_END:
			//缓存完成，继续播放
			startPlayer();
			break;
		}
		return true;
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		mVideoView.seekTo(0);
		mVideoView.start();
	}
}
