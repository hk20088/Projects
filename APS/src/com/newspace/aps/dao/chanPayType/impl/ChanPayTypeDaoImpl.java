package com.newspace.aps.dao.chanPayType.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newspace.aps.dao.chanPayType.ChanPayTypeDao;
import com.newspace.aps.mapper.chanPayType.ChanPayTypeMapper;
import com.newspace.aps.model.ChanPayType;
import com.newspace.aps.model.Device;

@Service
@Transactional
public class ChanPayTypeDaoImpl implements ChanPayTypeDao{

	@Resource
	private ChanPayTypeMapper chanPayTypeMapper;
	
	/**
	 * 查询某渠道所支持的支付方式及优先级
	 * 查询条件：渠道，设备平台，只查询状态为 “N” 的支付方式
	 */
	@Override
	public List<ChanPayType> queryChanPayInfo(Device device)
	{
		List<ChanPayType> list = chanPayTypeMapper.queryChanPayInfo(device);
		if(list != null && !list.isEmpty())
		{
			//将list集合根据Priority进行升序排列
			Collections.sort(list, new Comparator<ChanPayType>() {

				@Override
				public int compare(ChanPayType o1, ChanPayType o2) {
					//如果用compareTo方法进行比较，Priority参数必须是int的包装类型Integer
					return o1.getPriority().compareTo(o2.getPriority());
				}
			});;
		}
		return list;
	}
	
}
