package com.liangweibang.o2o.service;

import java.io.File;

import com.liangweibang.o2o.dto.ShopExecution;
import com.liangweibang.o2o.entity.Shop;

public interface ShopService {

	ShopExecution addShop(Shop shop, File shopImg);
}
