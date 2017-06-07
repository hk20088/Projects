package com.newspace.aps.model.goods;

import java.util.HashMap;
import java.util.Map;

/**
 * VIP类型
 * @author huqili
 * @date 2016年11月17日
 *
 */
public enum VipTypeEnum {

	VIP_MONTH("MONTHLY",30),
	VIP_HALF_YEAR("SIX",180),
	VIP_ONE_YEAR("YEARLY",365);
	
	private String vipType;
	private int days;
	
	private static final Map<String, Integer> getDays = new HashMap<String,Integer>();
	
	VipTypeEnum(String vipType,int days) 
	{
		this.vipType = vipType;
		this.days = days;
	}
	
	static
	{
		for(VipTypeEnum vipType : VipTypeEnum.values())
		{
			getDays.put(vipType.getVipType(), vipType.getDays());
		}
	}
	
	/**
	 * 根据类型获取对应的天数
	 * @param vipType
	 * @return
	 */
	public static int getDays(String vipType)
	{
		return getDays.get(vipType);
	}
	
	public String getVipType() {
		return vipType;
	}
	public void setVipType(String vipType) {
		this.vipType = vipType;
	}
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	
	
	
}
