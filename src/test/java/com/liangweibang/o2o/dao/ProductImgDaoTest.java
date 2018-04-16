package com.liangweibang.o2o.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.liangweibang.o2o.BaseTest;
import com.liangweibang.o2o.entity.ProductImg;
import com.mysql.fabric.xmlrpc.base.Array;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductImgDaoTest extends BaseTest {
	
	@Autowired
	private ProductImgDao productImgDao;
	
	@Test
	public void testABatchInsertProductImg() {
		ProductImg productImg1 = new ProductImg();
		productImg1.setAddr("图片1");
		productImg1.setDesc("测试图片1");
		productImg1.setPriority(1);
		productImg1.setProductId(2l);
		
		ProductImg productImg2 = new ProductImg();
		productImg2.setAddr("图片2");
		productImg2.setDesc("测试图片2");
		productImg2.setPriority(2);
		productImg2.setProductId(2l);
		
		List<ProductImg> productImgList = new ArrayList<>();
		productImgList.add(productImg1);
		productImgList.add(productImg2);
		
		productImgDao.batchInsertProductImg(productImgList);
		
	}
	
}
