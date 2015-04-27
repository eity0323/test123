package com.example.cpplayer.ui;

import java.util.ArrayList;
import java.util.List;

import com.example.cpplayer.R;
import com.example.cpplayer.ui.adapter.DownloadTaskAdapter;
import com.github.snowdream.android.app.DownloadListener;
import com.github.snowdream.android.app.DownloadManager;
import com.github.snowdream.android.app.DownloadStatus;
import com.github.snowdream.android.app.DownloadTask;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class DownloadDemo extends Activity {
	private DownloadManager downloadManager = null;
    private List<DownloadTask> list = null;
    private DownloadTaskAdapter adapter = null;
    private ListView listView;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.download_activity);

	        listView = (ListView)findViewById(R.id.downListView);
	        listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					 DownloadTask task = (DownloadTask) adapter.getItem(arg2);

			        if (task == null) {
			            return;
			        }

			        switch (task.getStatus()) {
			            case DownloadStatus.STATUS_PENDING:
			            case DownloadStatus.STATUS_FAILED:
			            case DownloadStatus.STATUS_STOPPED:
			            case DownloadStatus.STATUS_FINISHED:
			                downloadManager.start(task, listener);
			                break;
			            case DownloadStatus.STATUS_RUNNING:
			                downloadManager.stop(task, listener);
			                break;
			            default:
			                break;
			        }
					
				}
			});
	        
	        List<String> items1;
	        items1 = new ArrayList<String>();
	        for (int i = 1; i <= 20; i++) {
	            items1.add("MenuItem " + i);
	        }

	        downloadManager = new DownloadManager(this);
	        list = new ArrayList<DownloadTask>();

	        adapter = new DownloadTaskAdapter(this, list);
	        listView.setAdapter(adapter);

	        DownloadTask task = new DownloadTask(this);
	        task.setUrl("http://221.7.133.74:15202/messenger/uploadfile/2015042211472519889050331232.mp4");
	        downloadManager.add(task, listener);
	    }

	    @Override
	    protected void onDestroy() {
	        adapter.destory();
	        super.onDestroy();
	    }

	    private DownloadListener listener = new DownloadListener<Integer, DownloadTask>() {
	        /**
	         * The download task has been added to the sqlite.
	         * <p/>
	         * operation of UI allowed.
	         *
	         * @param downloadTask the download task which has been added to the sqlite.
	         */
	        @Override
	        public void onAdd(DownloadTask downloadTask) {
	            super.onAdd(downloadTask);
	            list.add(downloadTask);
	            adapter.notifyDataSetChanged();
	        }

	        /**
	         * The download task has been delete from the sqlite
	         * <p/>
	         * operation of UI allowed.
	         *
	         * @param downloadTask the download task which has been deleted to the sqlite.
	         */
	        @Override
	        public void onDelete(DownloadTask downloadTask) {
	            super.onDelete(downloadTask);
	        }

	        /**
	         * The download task is stop
	         * <p/>
	         * operation of UI allowed.
	         *
	         * @param downloadTask the download task which has been stopped.
	         */
	        @Override
	        public void onStop(DownloadTask downloadTask) {
	            super.onStop(downloadTask);
	        }

	        /**
	         * Runs on the UI thread before doInBackground(Params...).
	         */
	        @Override
	        public void onStart() {
	            super.onStart();
	        }

	        /**
	         * Runs on the UI thread after publishProgress(Progress...) is invoked. The
	         * specified values are the values passed to publishProgress(Progress...).
	         *
	         * @param values The values indicating progress.
	         */
	        @Override
	        public void onProgressUpdate(Integer... values) {
	            super.onProgressUpdate(values);
	            adapter.notifyDataSetChanged();
	        }

	        /**
	         * Runs on the UI thread after doInBackground(Params...). The specified
	         * result is the value returned by doInBackground(Params...). This method
	         * won't be invoked if the task was cancelled.
	         *
	         * @param downloadTask The result of the operation computed by
	         *                     doInBackground(Params...).
	         */
	        @Override
	        public void onSuccess(DownloadTask downloadTask) {
	            super.onSuccess(downloadTask);
	        }

	        /**
	         * Applications should preferably override onCancelled(Object). This method
	         * is invoked by the default implementation of onCancelled(Object). Runs on
	         * the UI thread after cancel(boolean) is invoked and
	         * doInBackground(Object[]) has finished.
	         */
	        @Override
	        public void onCancelled() {
	            super.onCancelled();
	        }

	        @Override
	        public void onError(Throwable thr) {
	            super.onError(thr);
	        }

	        /**
	         * Runs on the UI thread after doInBackground(Params...) when the task is
	         * finished or cancelled.
	         */
	        @Override
	        public void onFinish() {
	            super.onFinish();
	        }
	    };
}
