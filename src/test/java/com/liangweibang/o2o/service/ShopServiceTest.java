package com.liangweibang.o2o.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.liangweibang.o2o.BaseTest;
import com.liangweibang.o2o.dto.ShopExecution;
import com.liangweibang.o2o.entity.Area;
import com.liangweibang.o2o.entity.PersonInfo;
import com.liangweibang.o2o.entity.Shop;
import com.liangweibang.o2o.entity.ShopCategory;
import com.liangweibang.o2o.enums.ShopStateEnum;
import com.liangweibang.o2o.exception.ShopOperationException;

public class ShopServiceTest extends BaseTest {
	
	@Autowired
	private ShopService shopService;
	
	@Test
	public void testGetShopList() {
		Shop shopCondition = new Shop();
		ShopCategory shopCategory = new ShopCategory();
		shopCategory.setShopCategoryId(2L);
		shopCondition.setShopCategory(shopCategory);
		ShopExecution shopExecution = shopService.getShopList(shopCondition, 2, 2);
		System.out.println(shopExecution.getShopList().size());
		System.out.println(shopExecution.getCount());
	}
	
	@Test
	@Ignore
	public void testModifyShop() throws ShopOperationException, FileNotFoundException {
		Shop shop = new Shop();
		shop.setShopId(3l);
		shop.setName("修改的店铺名称");
		File shopImg = new File("/Users/liangweibang/baidu/img/dabai.jpeg");
		InputStream inputStream = new FileInputStream(shopImg);
		ShopExecution shopExecution = shopService.modifyShop(shop, inputStream, "daibai.jpeg");
		System.out.println(shopExecution.getShop().getAvatar());
	}
	
	@Test
	@Ignore
	public void testAddShop() throws FileNotFoundException {
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
		shop.setName("测试的店铺1111");
		shop.setDesc("test3");
		shop.setAddr("test3");
		shop.setPhone("test3");
		shop.setCreateTime(new Date());
		shop.setStatus(ShopStateEnum.CHECK.getState());
		shop.setAdvice("审核中");
		
		File shopImg = new File("/Users/liangweibang/baidu/img/xiaohuangren.jpeg");
		InputStream inputStream = new FileInputStream(shopImg);
		ShopExecution shopExecution = shopService.addShop(shop, inputStream, shopImg.getName());
		assertEquals(ShopStateEnum.CHECK.getState(), shopExecution.getState());
	}
}
