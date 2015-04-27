package com.example.cpplayer.ui.helper;

public class APPEvent {
	public static class freshVideoEvent{
		String items;
		int status;
		public static int STATUS_SUCCESS = 0;
		public static int STATUS_FAIL = 1;
		
		public freshVideoEvent(String item,int status){
			this.items = item;
			this.status = status;
		}
		
		public String getData(){
			return items;
		}
		
		public int getStatus(){
			return status;
		}
	}
}
