package com.example.isweixin;

public class SearchUser {
	public int id;
	private String txPath;
	private String weixinID;
	private String name;
	private String desc;

	SearchUser(){
		txPath = "";
		weixinID= "";
		name= "";
		desc= "";
	}
	
	public String getTxPath() {
		return txPath;
	}

	public void setTxPath(String txPath) {
		this.txPath = txPath;
	}

	public String getWeixinID() {
		return weixinID;
	}

	public void setWeixinID(String weixinID) {
		this.weixinID = weixinID;
	}
	public int getID(){
		return id;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
