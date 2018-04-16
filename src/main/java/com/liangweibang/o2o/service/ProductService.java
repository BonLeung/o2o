package com.liangweibang.o2o.service;

import java.io.InputStream;
import java.util.List;

import com.liangweibang.o2o.dto.ImageHolder;
import com.liangweibang.o2o.dto.ProductExecution;
import com.liangweibang.o2o.entity.Product;
import com.liangweibang.o2o.exception.ProductOperationException;

public interface ProductService {

	/**
	 * 添加商品信息以及图片处理
	 * @return
	 * @throws ProductOperationException
	 */
	ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImageList) throws ProductOperationException;
}
