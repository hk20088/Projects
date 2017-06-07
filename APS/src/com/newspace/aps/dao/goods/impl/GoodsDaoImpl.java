package com.newspace.aps.dao.goods.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newspace.aps.dao.goods.GoodsDao;
import com.newspace.aps.mapper.goods.GoodsMapper;
import com.newspace.aps.model.goods.Goods;

@Service
@Transactional
public class GoodsDaoImpl implements GoodsDao{

	@Resource
	private GoodsMapper goodsMapper;
	
	@Override
	public Goods queryGoods(int Id) {
		return goodsMapper.queryGoods(Id);
	}

}
