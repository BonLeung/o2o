package com.liangweibang.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.liangweibang.o2o.BaseTest;
import com.liangweibang.o2o.entity.ProductCategory;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductCategoryDaoTest extends BaseTest {
	
	@Autowired
	private ProductCategoryDao productCategoryDao;
	
	@Test
	@Ignore
	public void testQueryProductCategoryList() {
		long shopId = 3;
		List<ProductCategory> productCategorieList = productCategoryDao.queryProductCategoryList(shopId);
		System.out.println(productCategorieList.size());
	}
	
	@Test
	public void testBatchInsertProductCategory() {
		ProductCategory productCategory1 = new ProductCategory();
		productCategory1.setName("商品类别1");
		productCategory1.setPriority(1);
		productCategory1.setShopId(3l);
		productCategory1.setCreateTime(new Date());
		
		ProductCategory productCategory2 = new ProductCategory();
		productCategory2.setName("商品类别2");
		productCategory2.setPriority(2);
		productCategory2.setShopId(3l);
		productCategory2.setCreateTime(new Date());
		
		ProductCategory productCategory3 = new ProductCategory();
		productCategory3.setName("商品类别3");
		productCategory3.setPriority(3);
		productCategory3.setShopId(3l);
		productCategory3.setCreateTime(new Date());
		
		List<ProductCategory> productCategoryList = new ArrayList<ProductCategory>();
		productCategoryList.add(productCategory1);
		productCategoryList.add(productCategory2);
		productCategoryList.add(productCategory3);
		
		int effectedNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
		System.out.println(effectedNum);
	}
	
	@Test
	public void testDeleteProductCategory() {
		long shopId = 3;
		List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategoryList(shopId);
		for (ProductCategory productCategory : productCategoryList) {
			if ("商品类别1".equals(productCategory.getName()) || "商品类别2".equals(productCategory.getName()) || "商品类别3".equals(productCategory.getName())) {
				int effectedNum = productCategoryDao.deleteProductCategory(productCategory.getProductCategoryId(), shopId);
				assertEquals(1, effectedNum);
			}
		}
	}
}
