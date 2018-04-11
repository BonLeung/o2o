package com.liangweibang.o2o.service;

import java.io.File;
import java.io.InputStream;

import com.liangweibang.o2o.dto.ShopExecution;
import com.liangweibang.o2o.entity.Shop;

public interface ShopService {

	ShopExecution addShop(Shop shop, InputStream shopImgInputStream, String fileName);
}
