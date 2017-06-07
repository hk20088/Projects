package com.newspace.aps.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newspace.aps.mapper.systemDomain.SystemDomainMapper;
import com.newspace.aps.model.SystemDomain;
import com.newspace.common.utils.SpringBeanUtils;

public class GeneralUtils {
	
	//根据包名获取系统ID
	public static Map<String,Integer> getSysDomain = new HashMap<String,Integer>();
	
	static
	{
		SystemDomainMapper mapper = (SystemDomainMapper)SpringBeanUtils.getBeanByClass(SystemDomainMapper.class);
		List<SystemDomain> systems = mapper.querySystemDomainList();
		if(systems != null && systems.size() > 0)
		{
			for(SystemDomain sys:systems){
				getSysDomain.put(sys.getPackageName(), sys.getId());
			}
		}
	}
	
	
}
