package com.liangweibang.o2o.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.liangweibang.o2o.BaseTest;
import com.liangweibang.o2o.entity.Product;
import com.liangweibang.o2o.entity.ProductCategory;
import com.liangweibang.o2o.entity.Shop;

public class ProductDaoTest extends BaseTest {

	@Autowired
	private ProductDao productDao;
	
	@Test
	public void testInsertProduct() {
		Shop shop = new Shop();
		shop.setShopId(3l);
		ProductCategory productCategory = new ProductCategory();
		productCategory.setProductCategoryId(4l);
		
		Product product1 = new Product();
		product1.setName("测试商品1");
		product1.setAddr("addr1");
		product1.setDesc("desc1");
		product1.setProductCategory(productCategory);
		product1.setShop(shop);
		product1.setStatus(1);
		product1.setPriority(1);
		
		Product product2 = new Product();
		product2.setName("测试商品2");
		product2.setAddr("addr1");
		product2.setDesc("desc1");
		product2.setProductCategory(productCategory);
		product2.setShop(shop);
		product2.setPriority(1);
		product2.setStatus(1);
		
		Product product3 = new Product();
		product3.setName("测试商品3");
		product3.setAddr("addr1");
		product3.setDesc("desc1");
		product3.setProductCategory(productCategory);
		product3.setShop(shop);
		product3.setPriority(1);
		product3.setStatus(1);
		
		int effectedNum1 = productDao.insertProduct(product1);
		assertEquals(1, effectedNum1);
		int effectedNum2 = productDao.insertProduct(product2);
		assertEquals(1, effectedNum2);
		int effectedNum3 = productDao.insertProduct(product3);
		assertEquals(1, effectedNum3);
	}
}
