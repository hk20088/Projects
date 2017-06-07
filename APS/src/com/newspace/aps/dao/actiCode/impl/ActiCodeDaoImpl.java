package com.newspace.aps.dao.actiCode.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newspace.aps.dao.actiCode.ActiCodeDao;
import com.newspace.aps.mapper.actiCode.ActiCodeMapper;
import com.newspace.aps.model.ActiCode;

@Service
@Transactional
public class ActiCodeDaoImpl implements ActiCodeDao{

	@Resource
	private ActiCodeMapper actiCodeMapper;

	/**
	 * 根据用户ID查询绑定的智能卡号
	 */
	@Override
	public String queryExtraId(int userId) {
		
		String ExtraId = null;
		List<ActiCode> list = actiCodeMapper.queryActiCode(userId);
		if(list != null && !list.isEmpty())
		{
			for(ActiCode code : list)
			{
				if(code.getExtraId() != null)
				{
					ExtraId = code.getExtraId();
					break;
				}
			}
		}
		
		return ExtraId;
	}
}
