package com.liangweibang.o2o.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liangweibang.o2o.dao.ProductDao;
import com.liangweibang.o2o.dao.ProductImgDao;
import com.liangweibang.o2o.dto.ImageHolder;
import com.liangweibang.o2o.dto.ProductExecution;
import com.liangweibang.o2o.entity.Product;
import com.liangweibang.o2o.entity.ProductImg;
import com.liangweibang.o2o.enums.ProductStateEnum;
import com.liangweibang.o2o.exception.ProductOperationException;
import com.liangweibang.o2o.service.ProductService;
import com.liangweibang.o2o.util.ImageUtil;
import com.liangweibang.o2o.util.PathUtil;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ProductImgDao productImgDao;

	@Override
	@Transactional
	public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImageHolderList)
			throws ProductOperationException {
		// 1.处理缩略图，获取缩略图相对路径并赋值给 product
		// 2.往 tb_product 写入商品逆袭，获取 productId
		// 3.结合 productId 批量处理商品详情图
		// 4.将商品详情图列表批量插入 tb_product_img 中
		
		// 空值判断
		if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
			// 给商品设置默认属性
			product.setCreateTime(new Date());
			product.setUpdateTime(new Date());
			// 默认为上架状态
			product.setStatus(1);
			// 若商品缩略图不添加则为空
			if (thumbnail != null) {
				addThubmnail(product, thumbnail);
			}
			try {
				// 创建商品信息
				int effectedNum = productDao.insertProduct(product);
				if (effectedNum <= 0) {
					throw new ProductOperationException("创建商品失败");
				}
			} catch(Exception e) {
				throw new ProductOperationException("创建商品失败：" + e.getMessage());
			}
			// 若商品性情图不为空则添加
			if (productImageHolderList != null && productImageHolderList.size() > 0) {
				addProductImageList(product, productImageHolderList);
			}
			return new ProductExecution(ProductStateEnum.SUCCESS, product);
		} else {
			return new ProductExecution(ProductStateEnum.EMPTY);
		}
	}
	
	// 添加缩略图
	private void addThubmnail(Product product, ImageHolder thumbnail) {
		String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
		String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
		product.setAddr(thumbnailAddr);
	}
	
	// 批量添加图片
	private void addProductImageList(Product product, List<ImageHolder> productImageHolderList) {
		// 获取图片存储路径，这里直接存放到相应店铺的文件夹下
		String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
		List<ProductImg> productImgList = new ArrayList<>();
		// 遍历图片依次去处理，并添加进 productImg 实体里
		for (ImageHolder productImageHolder : productImageHolderList) {
			String imgAddr = ImageUtil.generateNormalImg(productImageHolder, dest);
			ProductImg productImg = new ProductImg();
			productImg.setAddr(imgAddr);
			productImg.setProductId(product.getProductId());
			productImg.setCreateTime(new Date());
			productImgList.add(productImg);
		}
		// 如果确实有图片需要添加的，就执行批量添加操作
		if (productImgList.size() > 0) {
			try {
				int effectedNum = productImgDao.batchInsertProductImg(productImgList);
				if (effectedNum <= 0) {
					throw new ProductOperationException("创建商品详情图失败");
				}
			} catch(Exception e) {
				throw new ProductOperationException("创建商品详情图失败：" + e.toString());
			}
		}
	}

}
