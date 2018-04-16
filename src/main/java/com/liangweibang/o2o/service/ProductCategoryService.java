package com.liangweibang.o2o.service;

import java.util.List;

import com.liangweibang.o2o.dto.ProductCategoryExecution;
import com.liangweibang.o2o.entity.ProductCategory;
import com.liangweibang.o2o.exception.ProductCategoryOperationException;

public interface ProductCategoryService {

	/**
	 * 查询指定店铺下的所有商品类别信息
	 * @param shopId
	 * @return
	 */
	List<ProductCategory> getProductCategoryList(long shopId);
	
	/**
	 * 批量增加商品列类别
	 * @param productCategoryList
	 * @return
	 * @throws ProductCategoryOperationException
	 */
	ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) throws ProductCategoryOperationException;
	
	/**
	 * 将此类别下的商品里的类别置为空，再删除该商品类别
	 * @param productCategoryId
	 * @param shopId
	 * @return
	 */
	ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId);
}
