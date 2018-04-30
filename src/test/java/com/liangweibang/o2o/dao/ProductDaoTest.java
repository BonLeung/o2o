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
import com.liangweibang.o2o.entity.Product;
import com.liangweibang.o2o.entity.ProductCategory;
import com.liangweibang.o2o.entity.ProductImg;
import com.liangweibang.o2o.entity.Shop;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductDaoTest extends BaseTest {

	@Autowired
	private ProductDao productDao;
	@Autowired
	private ProductImgDao productImgDao;
	
	@Test
	@Ignore
	public void testAInsertProduct() {
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
	
	@Test
	@Ignore
	public void testBQueryProductList() throws Exception {
		Product productCondition = new Product();
		// 分页查询，预期返回三条结果
		List<Product> productList = productDao.queryProductList(productCondition, 0, 3);
		assertEquals(3, productList.size());
		// 。查询名称为测试的商品总数
		int count = productDao.queryProductCount(productCondition);
		assertEquals(4, count);
		// 使用商品名称模糊查询，预期返回两条结果
		productCondition.setName("测试");
		productList = productDao.queryProductList(productCondition, 0, 3);
		assertEquals(2, productList.size());
		count = productDao.queryProductCount(productCondition);
		assertEquals(2, count);
	}
	
	@Test
	@Ignore
	public void testCQueryProductByProductId() throws Exception {
		long productId = 2;
		// 初始化两个商品详情图实例作为 productId 为2的商品下的详情图片
		ProductImg productImg1 = new ProductImg();
		productImg1.setAddr("图片1");
		productImg1.setDesc("测试图片1");
		productImg1.setPriority(1);
		productImg1.setCreateTime(new Date());
		productImg1.setProductId(productId);
		ProductImg productImg2 = new ProductImg();
		productImg2.setAddr("图片2");
		productImg2.setDesc("测试图片2");
		productImg2.setPriority(1);
		productImg2.setCreateTime(new Date());
		productImg2.setProductId(productId);
		List<ProductImg> productImgList = new ArrayList<>();
		productImgList.add(productImg1);
		productImgList.add(productImg2);
		int effectedNum = productImgDao.batchInsertProductImg(productImgList);
		assertEquals(2, effectedNum);
		// 查询 productId 为 2 的商品信息并校验返回的详情图实例列表size 是否为 2
		Product product = productDao.queryProductById(productId);
		assertEquals(2, product.getProductImgs().size());
		// 删除新增的这两个商品详情图实例
		effectedNum = productImgDao.deleteProductImgByProductId(productId);
		assertEquals(2, effectedNum);
	}
	
	@Test
	@Ignore
	public void testDUpdateProduct() throws Exception {
		Product product = new Product();
		ProductCategory productCategory = new ProductCategory();
		Shop shop = new Shop();
		shop.setShopId(3l);
		productCategory.setProductCategoryId(31l);
		product.setProductId(2l);
		product.setShop(shop);
		product.setName("奶茶111");
		product.setProductCategory(productCategory);
		// 修改 productID 为 2 的商品的名称
		// 以及商品类别并校验影响的行数是否为 1
		int effectedNum = productDao.updateProduct(product);
		assertEquals(1, effectedNum);
	}
	
	@Test
	public void testEUpdateProductCategoryToNull() {
		// 将productCategoryId为4的商品类别下的商品的商品类别置为空
		int effectedNum = productDao.updateProductCategoryToNull(4l);
		assertEquals(5, effectedNum);
		
	}
}
