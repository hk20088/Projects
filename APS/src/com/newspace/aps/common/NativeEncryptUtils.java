package com.newspace.aps.common;

import java.io.File;

import com.newspace.common.utils.PathUtils;

/**
 * {@link NativeEncryptUtils.java}
 * @description:  使用JNI调用本地库进行加密/解密的工具类
 * @author Huqili
 * @date 2015年7月18日
 *
 */
public class NativeEncryptUtils
{
	/**
	 * 静态块加载jni库 
	 */
	static
	{
		String path = PathUtils.getClassPackagePath(NativeEncryptUtils.class);
		String os = System.getProperty("os.name");
		if (os.indexOf("Windows") >= 0)
		{
			path += File.separator + "aes.dll";

			path = PathUtils.parseFilePath(path);
			System.load(path);
		}
		else
		{
			String path1 = path + File.separator + "libaes.so";
			path1 = PathUtils.parseFilePath(path1);
			System.load(path1);
		}
	}

	private static NativeEncryptUtils instance = new NativeEncryptUtils();

	private NativeEncryptUtils()
	{
	}

	/**
	 * 本地方法，加密操作
	 * @param content 需要加密的内容
	 * @param key 加密的公钥
	 * @return
	 * @throws Exception
	 */
	private native String encrypt(String content, String key) throws Exception;

	/**
	 * 本地方法，解密操作
	 * @param cryptograph 密文
	 * @param key 加密和解密的公钥
	 * @return
	 * @throws Exception
	 */
	private native String decrypt(String cryptograph, String key) throws Exception;

	/**
	 * 调用本地方法进行加密
	 * @param content 需要加密的内容
	 * @param key 加密的公钥
	 * @return
	 * @throws Exception
	 */
	public static String encryptAES(String content, String key) throws Exception
	{
		return instance.encrypt(content, key);
	}

	/**
	 * 调用本地方法进行解密
	 * @param cryptograph 密文
	 * @param key 解密的公钥
	 * @return
	 * @throws Exception
	 */
	public static String decryptAES(String cryptograph, String key) throws Exception
	{
		return instance.decrypt(cryptograph, key);
	}
}