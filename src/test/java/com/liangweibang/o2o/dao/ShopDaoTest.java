package com.liangweibang.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.liangweibang.o2o.BaseTest;
import com.liangweibang.o2o.entity.Area;
import com.liangweibang.o2o.entity.PersonInfo;
import com.liangweibang.o2o.entity.Shop;
import com.liangweibang.o2o.entity.ShopCategory;

public class ShopDaoTest extends BaseTest {

	@Autowired
	private ShopDao shopDao;
	
	@Test
	@Ignore
	public void testInsertShop() {
		Shop shop = new Shop();
		PersonInfo owner = new PersonInfo();
		Area area = new Area();
		ShopCategory shopCategory = new ShopCategory();
		owner.setUserId(1L);
		area.setAreaId(2);
		shopCategory.setShopCategoryId(1L);
		shop.setOwner(owner);
		shop.setArea(area);
		shop.setShopCategory(shopCategory);
		shop.setName("测试的店铺110");
		shop.setDesc("test");
		shop.setAddr("test");
		shop.setAvatar("test");
		shop.setPhone("test");
		shop.setCreateTime(new Date());
		shop.setStatus(1);
		shop.setAdvice("审核中");
		
		int effectedNum = shopDao.insertShop(shop);
		assertEquals(1, effectedNum);
	}
	
	@Test
	@Ignore
	public void testUpdateShop() {
		Shop shop = new Shop();
		shop.setShopId(31L);
		shop.setName("测试名称3");
		shop.setDesc("测试描述3");
		shop.setAddr("测试地址3");
		
		int effectedNum = shopDao.updateShop(shop);
		assertEquals(1, effectedNum);
	}
	
	@Test 
	@Ignore
	public void testQueryShopByShopId() {
		long shopId = 3;
		Shop shop = shopDao.queryByShopId(shopId);
		System.out.println(shop.getName());
		System.out.println(shop.getShopId());
	}
	
	@Test
	public void testQueryShopListAndCount() {
		Shop shopCondition = new Shop();
		PersonInfo owner = new PersonInfo();
		owner.setUserId(1L);
		shopCondition.setOwner(owner);
		List<Shop> shopList = shopDao.queryShopList(shopCondition, 0, 5);
		System.out.println(shopList.size());
		int count = shopDao.queryShopCount(shopCondition);
		System.out.println("shop总数:" + count);
		
		ShopCategory shopCategory = new ShopCategory();
		shopCategory.setShopCategoryId(2L);
		shopCondition.setShopCategory(shopCategory);
		List<Shop> shopList2 = shopDao.queryShopList(shopCondition, 0, 5);
		System.out.println(shopList2.size());
		int count2 = shopDao.queryShopCount(shopCondition);
		System.out.println("shop总数:" + count2);
	}
}
