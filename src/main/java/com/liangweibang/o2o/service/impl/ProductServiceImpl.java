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
import com.liangweibang.o2o.util.PageCalculator;
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

	@Override
	public Product getProductById(long productId) {
		return productDao.queryProductById(productId);
	}

	@Override
	@Transactional
	public ProductExecution modifyProduct(Product product, ImageHolder thumbnail,
			List<ImageHolder> productImageHolderList) throws ProductOperationException {
		// 1.若缩略图参数有值，则处理缩略图
		// 若原先存在缩略图则先删除再添加新图，之后获取缩略图相对路径并赋值给product
		// 2.若商品详情图列表参数有值，对商品详情图列表进行同样的操作
		// 3.将tb_product_img下的该商品原先的商品详情图记录全部清除
		// 4.更新tb_product_img以及tb_product的信息
		
		// 空值判断
		if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
			// 给商品设置默认属性
			product.setUpdateTime(new Date());
			// 若商品缩略图不为空且原有缩略图不为空则删除原有缩略图并添加
			if (thumbnail != null) {
				// 先获取一遍原有信息，因为原来的信息里有原图片地址
				Product tempProduct = productDao.queryProductById(product.getProductId());
				if (tempProduct.getAddr() != null) {
					ImageUtil.deleteFileOrPath(tempProduct.getAddr());
				}
				addThubmnail(product, thumbnail);
			}
			
			// 如果有新存入的商品详情图，则将原先的删除，并添加新的图片
			if (productImageHolderList != null && productImageHolderList.size() > 0) {
				deleteProductImgList(product.getProductId());
				addProductImageList(product, productImageHolderList);
			}
			try {
				// 更新商品信息
				int effectedNum = productDao.updateProduct(product);
				if (effectedNum <= 0) {
					throw new ProductOperationException("更新商品信息失败");
				}
				return new ProductExecution(ProductStateEnum.SUCCESS, product);
			} catch(Exception e) {
				throw new ProductOperationException("更新商品信息失败：" + e.toString());
			}
		} else {
			// 传参为空则返回空值错误信息
			return new ProductExecution(ProductStateEnum.EMPTY);
		}
		
	}
	
	/**
	 * 删除某个商品下的所有详情图
	 * @param productId
	 */
	public void deleteProductImgList(long productId) {
		// 根据 productID 获取原来的图片
		List<ProductImg> productImgList = productImgDao.queryProductImgList(productId);
		// 干掉原先的图片
		for (ProductImg productImg : productImgList) {
			ImageUtil.deleteFileOrPath(productImg.getAddr());
		}
		// 删除数据库里原有的图片的信息
		productImgDao.deleteProductImgByProductId(productId);
	}

	@Override
	public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
		// 页码转成数据库的行码，并调用dao层取回指定页码的商品列表
		int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
		List<Product> productList = productDao.queryProductList(productCondition, rowIndex, pageSize);
		// 基于同样的查询条件返回该查询条件下的商品总数
		int count = productDao.queryProductCount(productCondition);
		ProductExecution productExecution = new ProductExecution();
		productExecution.setProductList(productList);
		productExecution.setCount(count);
		return productExecution;
	}

}
