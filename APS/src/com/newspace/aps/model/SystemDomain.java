package com.newspace.aps.model;

/**
 * @description 系统信息表
 * @author huqili
 * @date 2016年9月24日
 *
 */
public class SystemDomain {

	private Integer Id;          // 主键
	private String DomainName;   // 系统名称
	private String EnglishName;  // 英文名称
	private String PackageName;  // 大厅包名

	public SystemDomain() {
		super();
	}

	public SystemDomain(Integer id, String domainName, String englishName, String packageName) {
		super();
		Id = id;
		DomainName = domainName;
		EnglishName = englishName;
		PackageName = packageName;
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getDomainName() {
		return DomainName;
	}

	public void setDomainName(String domainName) {
		DomainName = domainName;
	}

	public String getEnglishName() {
		return EnglishName;
	}

	public void setEnglishName(String englishName) {
		EnglishName = englishName;
	}

	public String getPackageName() {
		return PackageName;
	}

	public void setPackageName(String packageName) {
		PackageName = packageName;
	}
	
}
