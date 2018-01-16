package com.example.demo.domain;

public class UpdateInfo {
	// 是否有新版本
	public boolean hasUpdate = false;
	// 是否静默下载：有新版本时不提示直接下载
	public boolean isSilent = false;
	// 是否强制安装：不安装无法使用app
	public boolean isForce = false;
	// 是否下载完成后自动安装
	public boolean isAutoInstall = true;
	// 是否可忽略该版本
	public boolean isIgnorable = true;

	public int versionCode;
	public String versionName;
	public String updateContent;

	public String url;
	public String md5;
	public long size;
	
}
