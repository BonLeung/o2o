package com.liangweibang.o2o.service.impl;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liangweibang.o2o.dao.ShopDao;
import com.liangweibang.o2o.dto.ShopExecution;
import com.liangweibang.o2o.entity.Shop;
import com.liangweibang.o2o.enums.ShopStateEnum;
import com.liangweibang.o2o.exception.ShopOperationException;
import com.liangweibang.o2o.service.ShopService;
import com.liangweibang.o2o.util.ImageUtil;
import com.liangweibang.o2o.util.PageCalculator;
import com.liangweibang.o2o.util.PathUtil;

@Service
public class ShopServiceImpl implements ShopService {
	
	@Autowired
	private ShopDao shopDao;

	@Override
	@Transactional
	public ShopExecution addShop(Shop shop, InputStream shopImgInputStream, String fileName) {
		// 空值判断
		if (shop == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}
		try {
			// 给店铺信息赋初始值
			shop.setStatus(0);
			shop.setCreateTime(new Date());
			shop.setUpdateTime(new Date());
			int effectedNum = shopDao.insertShop(shop);
			System.out.println(effectedNum);

			if (effectedNum <= 0) {
				throw new ShopOperationException("店铺创建失败");
			} else {
				if (shopImgInputStream != null) {
					// 存储图片
					try {
						addShopImg(shop, shopImgInputStream, fileName);
					} catch (Exception e) {
						throw new ShopOperationException("addShopImg error:" + e.getMessage());
					}
					// 更新店铺的图片地址
					effectedNum = shopDao.updateShop(shop);
					if (effectedNum <= 0) {
						throw new ShopOperationException("更新图片地址失败");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ShopOperationException("addShop error:" + e.getMessage());
		}
		return new ShopExecution(ShopStateEnum.CHECK, shop);
	}

	private void addShopImg(Shop shop, InputStream shopImgInputStream, String fileName) {
		// 获取 shop 图片目录的相对值路径
		String dest = PathUtil.getShopImagePath(shop.getShopId());
		String shopImgAddr = ImageUtil.generateThumbnail(shopImgInputStream, fileName, dest);
		shop.setAvatar(shopImgAddr);
	}

	@Override
	public Shop getByShopId(long shopId) {
		return shopDao.queryByShopId(shopId);
	}

	@Override
	public ShopExecution modifyShop(Shop shop, InputStream shopImgInputStream, String fileName)
			throws ShopOperationException {
		if (shop == null || shop.getShopId() == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		} else {
			try {
				// 1. 判断是否需要处理图片
				if (shopImgInputStream != null && fileName != null && !"".equals(fileName)) {
					Shop tempShop = shopDao.queryByShopId(shop.getShopId());
					if (tempShop.getAvatar() != null) {
						ImageUtil.deleteFileOrPath(tempShop.getAvatar());
					}
					addShopImg(shop, shopImgInputStream, fileName);
				}
				// 2. 更新店铺信息
				shop.setUpdateTime(new Date());
				int effectedNum = shopDao.updateShop(shop);
				if (effectedNum <= 0) {
					return new ShopExecution(ShopStateEnum.INNER_ERROR);
				} else {
					shop = shopDao.queryByShopId(shop.getShopId());
					return new ShopExecution(ShopStateEnum.SUCCESS, shop);
				}
			} catch(Exception e) {
				throw new ShopOperationException("modifyShop error:" + e.getMessage());
			}
		}
		
	}

	@Override
	public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
		int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
		List<Shop> shopList = shopDao.queryShopList(shopCondition, rowIndex, pageSize);
		int count = shopDao.queryShopCount(shopCondition);
		ShopExecution shopExecution = new ShopExecution();
		if (shopList != null) {
			shopExecution.setShopList(shopList);
			shopExecution.setCount(count);
		} else {
			shopExecution.setState(ShopStateEnum.INNER_ERROR.getState());
		}
		
		return shopExecution;
	}
	
}
