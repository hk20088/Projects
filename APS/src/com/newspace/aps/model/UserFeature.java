package com.newspace.aps.model;

import java.sql.Timestamp;

public class UserFeature
{
	private Integer Id;
	private String FeatureCode;
	private Integer EndUser;
	private Integer SystemDomain;
	private String FeatureValue;
	private String ExtraValue;
	private Timestamp CreateTime;
	private Timestamp UpdateTime;
	
	public UserFeature()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	
	public UserFeature(Integer endUser, Integer systemDomain)
	{
		super();
		EndUser = endUser;
		SystemDomain = systemDomain;
	}
	
	public Integer getEndUser()
	{
		return EndUser;
	}
	
	public void setEndUser(Integer endUser)
	{
		EndUser = endUser;
	}
	
	public Integer getSystemDomain()
	{
		return SystemDomain;
	}
	
	public void setSystemDomain(Integer systemDomain)
	{
		SystemDomain = systemDomain;
	}
	
	public Integer getId()
	{
		return Id;
	}
	
	public void setId(Integer id)
	{
		Id = id;
	}
	
	public String getFeatureCode()
	{
		return FeatureCode;
	}
	
	public void setFeatureCode(String featureCode)
	{
		FeatureCode = featureCode;
	}
	
	public String getFeatureValue()
	{
		return FeatureValue;
	}
	
	public void setFeatureValue(String featureValue)
	{
		FeatureValue = featureValue;
	}
	
	public String getExtraValue()
	{
		return ExtraValue;
	}
	
	public void setExtraValue(String extraValue)
	{
		ExtraValue = extraValue;
	}
	
	public Timestamp getCreateTime()
	{
		return CreateTime;
	}
	
	public void setCreateTime(Timestamp createTime)
	{
		CreateTime = createTime;
	}
	
	public Timestamp getUpdateTime()
	{
		return UpdateTime;
	}
	
	public void setUpdateTime(Timestamp updateTime)
	{
		UpdateTime = updateTime;
	}
	
}
