package com.liangweibang.o2o.service;

import java.io.InputStream;

import com.liangweibang.o2o.dto.ImageHolder;
import com.liangweibang.o2o.dto.ShopExecution;
import com.liangweibang.o2o.entity.Shop;
import com.liangweibang.o2o.exception.ShopOperationException;

public interface ShopService {

	/**
	 * 新增店铺
	 * @param shop
	 * @param shopImgInputStream
	 * @param fileName
	 * @return
	 */
	ShopExecution addShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException;
	
	/**
	 * 通过 shopId 获取店铺信息
	 * @param shopId
	 * @return
	 */
	Shop getByShopId(long shopId);
	
	/**
	 * 更新店铺信息，包括图片处理
	 * @param shop
	 * @param shopInputStream
	 * @param fileName
	 * @return
	 */
	ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException;
	
	/**
	 * 根据 shopCondition 分页返回相应店铺列表
	 * @param shopCondition
	 * @param pageIndex
	 * @param pageNum
	 * @return
	 */
	ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageNum);
}
