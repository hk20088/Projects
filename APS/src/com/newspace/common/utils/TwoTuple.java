package com.newspace.common.utils;

/**
 * @description 工具类，当函数需要返回两个参数时使用
 * @author huqili
 * @date 2016年7月13日
 *
 */
public final class TwoTuple<A,B> 
{
	public final A first;
	public final B second;
	
	public TwoTuple(A first, B second)
	{
		this.first = first;
		this.second = second;
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		if (first != null)
			sb.append(first.toString());
		else
			sb.append("NULL");
		sb.append(" , ");
		if (second != null)
			sb.append(second.toString());
		else
			sb.append("NULL");
		sb.append(" , ");
		return sb.toString();
	}
}
