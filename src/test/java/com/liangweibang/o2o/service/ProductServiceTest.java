package com.liangweibang.o2o.service;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.liangweibang.o2o.BaseTest;
import com.liangweibang.o2o.dto.ImageHolder;
import com.liangweibang.o2o.dto.ProductExecution;
import com.liangweibang.o2o.entity.Product;
import com.liangweibang.o2o.entity.ProductCategory;
import com.liangweibang.o2o.entity.Shop;
import com.liangweibang.o2o.enums.ProductStateEnum;
import com.liangweibang.o2o.exception.ShopOperationException;

public class ProductServiceTest extends BaseTest {

	@Autowired
	private ProductService productService;
	
	@Test
	@Ignore
	public void testAddProduct() throws ShopOperationException, FileNotFoundException {
		Product product = new Product();
		Shop shop = new Shop();
		shop.setShopId(3l);
		ProductCategory productCategory = new ProductCategory();
		productCategory.setProductCategoryId(31l);
		product.setShop(shop);
		product.setProductCategory(productCategory);
		product.setName("测试商品1");
		product.setDesc("测试商品1");
		product.setPriority(20);
		product.setCreateTime(new Date());
		product.setStatus(ProductStateEnum.SUCCESS.getState());
		// 创建缩略图文件流
		File thumbnailFile = new File("/Users/liangweibang/baidu/img/xiaohuangren.jpeg");
		InputStream is = new FileInputStream(thumbnailFile);
		ImageHolder thumbnail = new ImageHolder(thumbnailFile.getName(), is);
		// 创建两个商品详情图文件流并将他们添加到详情图列表中
		File productImg1 = new File("/Users/liangweibang/baidu/img/xiaohuangren.jpeg");
		InputStream is1 = new FileInputStream(productImg1);
		File productImg2 = new File("/Users/liangweibang/baidu/img/dabai.jpeg");
		InputStream is2 = new FileInputStream(productImg2);
		List<ImageHolder> productImageList = new ArrayList<>();
		productImageList.add(new ImageHolder(productImg1.getName(), is1));
		productImageList.add(new ImageHolder(productImg2.getName(), is2));
		// 添加商品并验证
		ProductExecution productExecution = productService.addProduct(product, thumbnail, productImageList);
		assertEquals(ProductStateEnum.SUCCESS.getState(), productExecution.getState());
	}
	
	@Test
	public void testModifyProduct() throws ShopOperationException, FileNotFoundException {
		// 创建shopId 为3且productCategory为31的商品实例并给其成员变量赋值
		Product product = new Product();
		Shop shop = new Shop();
		shop.setShopId(3l);
		ProductCategory productCategory = new ProductCategory();
		productCategory.setProductCategoryId(2l);
		product.setProductId(2l);
		product.setShop(shop);
		product.setProductCategory(productCategory);
		product.setName("正式的商品");
		product.setDesc("正式的商品");
		// 创建缩略图文件流
		File thumbnailFile = new File("/users/liangweibang/baidu/img/dabai.jpeg");
		InputStream inputStream = new FileInputStream(thumbnailFile);
		ImageHolder thumbnail = new ImageHolder(thumbnailFile.getName(), inputStream);
		// 创建两个商品详情图文件流并将它们添加到详情图列表中
		File productImg1 = new File("/users/liangweibang/baidu/img/xiaohuangren.jpeg");
		InputStream inputStream1 = new FileInputStream(productImg1);
		File productImg2 = new File("/users/liangweibang/baidu/img/xiaohuangrennew.png");
		InputStream inputStream2 = new FileInputStream(productImg2);
		List<ImageHolder> productImgList = new ArrayList<>();
		productImgList.add(new ImageHolder(productImg1.getName(), inputStream1));
		productImgList.add(new ImageHolder(productImg2.getName(), inputStream2));
		ProductExecution productExecution = productService.modifyProduct(product, thumbnail, productImgList);
		assertEquals(ProductStateEnum.SUCCESS.getState(), productExecution.getState());
		
	}
	
}
