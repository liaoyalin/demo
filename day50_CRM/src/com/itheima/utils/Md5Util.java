package com.itheima.utils;

import org.springframework.util.DigestUtils;

/**
 * @author 对密码进行MD5加密
 *
 */
public class Md5Util {
	public static String encodePwd(String pwd){
		for (int i = 0; i < 10; i++) {
			
			pwd= DigestUtils.md5DigestAsHex(pwd.getBytes());
		}
		return pwd;
	}

}
