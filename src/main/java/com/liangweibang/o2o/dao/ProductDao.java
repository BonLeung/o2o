package com.liangweibang.o2o.dao;

import com.liangweibang.o2o.entity.Product;

public interface ProductDao {

	/**
	 * 插入商品
	 * @param product
	 * @return
	 */
	int insertProduct(Product product);
	

	/**
	 * 通过 productId 查询唯一的商品信息
	 * @param productId
	 * @return
	 */
	Product queryProductById(long productId);
	
	/**
	 * 更新商品信息
	 * @param product
	 * @return
	 */
	int updateProduct(Product product);
	
}
