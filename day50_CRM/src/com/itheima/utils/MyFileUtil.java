package com.itheima.utils;

import java.util.UUID;

/**
 * 获取文件的uuid名称
 *
 */
public class MyFileUtil {
	public static String getFileName(String fileName){
		String prefix=UUID.randomUUID().toString().replaceAll("-", "");
		String suffix=fileName.substring(fileName.lastIndexOf("."));
		return prefix+suffix;
	}

}
