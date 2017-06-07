package com.newspace.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description 随意数生成工具类
 * @author huqili
 * @date 2016年9月17日
 *
 */
public class RandomUtils {

	// 用于订单号自增的原子类
	private static volatile AtomicInteger incrementInt = new AtomicInteger(0);
	
	/**
	 * 生成8+m+n随机数，规则：yyyyMMdd+(m+n)位流水（m位随机+n位自增）
	 * @return
	 */
	public static String getRandom(int m,int n)
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append(new SimpleDateFormat("yyyyMMdd").format(new Date()));
		sb.append(getRandomNumber(m));
		
		int num = 1;
		if (!incrementInt.compareAndSet(getMax(n), 1))
			num  = incrementInt.incrementAndGet();
		
		sb.append(padWithZero(num,n));
		return sb.toString();
	}
	
	/**
	 * 生成n位最大整数，例如如果n为4，则这里生成的最大值为 9999
	 * @param n
	 * @return
	 */
	private static int getMax(int n)
	{
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < n ;i++)
		{
			sb.append(9);
		}
		
		return Integer.parseInt(sb.toString());
	}

	
	/**
	 * 生成四位随机码  可以扩展：传入参数n，生成n位随机码
	 * @return
	 */
	private static String getRandomNumber(int m)
	{
		
		
		int num = (int) (Math.random() * getNum(m,true) + getNum(m, false));
		return String.valueOf(num);
	}
	
	/**
	 * 获取 9+m个0 或 1+m个0的值
	 * @param m
	 * @param flag
	 * @return
	 */
	private static Integer getNum(int m,boolean flag)
	{
		StringBuffer sb = new StringBuffer();
		if(flag)
		{
			sb.append("9");
		}
		else
		{
			sb.append("1");
		}
		for(int i=0; i<m-1; i++)
		{
			sb.append("0");
		}
		
		return Integer.valueOf(sb.toString());
	}

	/**
	 * 生成数字字符串，不足n位的用0补充
	 * @param num
	 * @param n
	 * @return
	 */
	private static String padWithZero(int num,int n)
	{
		String strNum = String.valueOf(num);
		StringBuffer sb = new StringBuffer(strNum);
		
		for(int i=0; i < n-strNum.length(); i++)
		{
			sb.insert(0, "0");
		}
		
		return sb.toString();
	}
	
}
