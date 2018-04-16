package com.liangweibang.o2o.service.impl;

import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liangweibang.o2o.dao.ProductDao;
import com.liangweibang.o2o.dao.ProductImgDao;
import com.liangweibang.o2o.dto.ImageHolder;
import com.liangweibang.o2o.dto.ProductExecution;
import com.liangweibang.o2o.entity.Product;
import com.liangweibang.o2o.exception.ProductOperationException;
import com.liangweibang.o2o.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ProductImgDao productImgDao;

	@Override
	@Transactional
	public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImageList)
			throws ProductOperationException {
				return null;
		// 1.处理缩略图，获取缩略图相对路径并赋值给 product
		// 2.往 tb_product 写入商品逆袭，获取 productId
		// 3.结合 productId 批量处理商品详情图
		// 4.将商品详情图列表批量插入 tb_product_img 中
		
	}

}
