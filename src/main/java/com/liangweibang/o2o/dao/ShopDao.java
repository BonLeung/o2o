package com.liangweibang.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.liangweibang.o2o.entity.Shop;

public interface ShopDao {

	/**
	 * 新增店铺
	 * @param shop
	 * @return
	 */
	int insertShop(Shop shop);
	
	/**
	 * 更新店铺
	 * @param shop
	 * @return
	 */
	int updateShop(Shop shop);
	
	/**
	 * 通过 shop_id 查询店铺
	 * @param shopId
	 * @return
	 */
	Shop queryByShopId(long shopId);
	
	/**
	 * 分页查询店铺，可输入的条件有：店铺名（模糊），店铺类别，区域，所有者
	 * @param shopCondition
	 * @param rowIndex	从第几行开始取数据
	 * @param pageSize	返回的条数
	 * @return
	 */
	List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition, @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);
	
	/**
	 * 返回 queryShopList 的总数
	 * @param shopCondition
	 * @return
	 */
	int queryShopCount(@Param("shopCondition") Shop shopCondition);
	
}
