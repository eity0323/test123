package com.example.cpplayer.ui.adapter;

import java.util.List;

import com.example.cpplayer.ui.interfaces.CPVideoFinderImpl;
import com.example.cpplayer.ui.interfaces.ICPVideoFinder;
import com.example.cpplayer.ui.model.VideoVO;
import com.example.cpplayer.ui.views.SimpleCPListItemView;
import com.example.cpplayer.ui.views.SimpleCPListItemView.CPListItemClickListener;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class SimpleCPListAdapter extends BaseAdapter {
	Context mcontext;
	List<VideoVO> datasource;
	
	ICPVideoFinder finder;
	
	CPListItemClickListener listener;
	
	public SimpleCPListAdapter(Context context){
		mcontext = context;
		
		finder = new CPVideoFinderImpl();
		datasource = finder.findAll();
	}

	@Override
	public int getCount() {
		return datasource.size();
	}

	@Override
	public Object getItem(int position) {
		return datasource.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public void setDataSource(List<VideoVO> data){
		datasource = data;
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		SimpleCPListItemView cplistItemView;
		if(convertView == null){
			cplistItemView = new SimpleCPListItemView(mcontext);
			
			cplistItemView.setCPListItemClickListener(listener);
		}else{
			cplistItemView = (SimpleCPListItemView)convertView;
		}
		
		cplistItemView.bind(datasource.get(position));
		
		return cplistItemView;
	}
	
	public void setCPListItemClickListener(CPListItemClickListener listen){
		listener = listen;
	}
	
	public void restart(){
		notifyDataSetChanged();
	}

}
