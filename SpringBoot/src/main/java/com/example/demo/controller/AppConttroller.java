package com.example.demo.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.security.MessageDigest;

import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.UpdateInfo;

@RequestMapping("/app")
@RestController
public class AppConttroller {
	/**
	 * http://10.129.51.27:8080/app/check?package=cn.czl.updatedemo&version=1&channel=main
	 * 
	 * @return
	 * @throws FileNotFoundException
	 */
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	public UpdateInfo check(@RequestParam(name = "package", required = true) String pkg,
			@RequestParam(name = "version", required = true) int version,
			@RequestParam(name = "channel", required = true) String channel) throws FileNotFoundException {
		UpdateInfo updateInfo = new UpdateInfo();
		if (version > 1) {
			updateInfo.hasUpdate = false;
		} else {
			updateInfo.hasUpdate = true;
			updateInfo.versionCode = 2;
			updateInfo.versionName = "v2.0.2018-1-16";
			updateInfo.updateContent = "1、修改了bug1；\r\n" + "2、修改了bug2；\r\n" + "3、新增了某个功能。";

			updateInfo.url = "http://10.129.51.27:8080/apk/apk2018-1-15.apk";
			File file = ResourceUtils.getFile("classpath:static/apk/apk2018-1-15.apk");
			updateInfo.md5 = md5(file);
			updateInfo.size = file.length();
		}
		return updateInfo;
	}

	public static String md5(File file) {
		MessageDigest digest = null;
		FileInputStream fis = null;
		byte[] buffer = new byte[1024];
		try {
			if (!file.isFile()) {
				return "";
			}
			digest = MessageDigest.getInstance("MD5");
			fis = new FileInputStream(file);
			while (true) {
				int len;
				if ((len = fis.read(buffer, 0, 1024)) == -1) {
					fis.close();
					break;
				}
				digest.update(buffer, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		BigInteger var5 = new BigInteger(1, digest.digest());
		return String.format("%1$032x", new Object[] { var5 });
	}
}
