package com.liangweibang.o2o.web.shopadmin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liangweibang.o2o.dto.ShopExecution;
import com.liangweibang.o2o.entity.Area;
import com.liangweibang.o2o.entity.PersonInfo;
import com.liangweibang.o2o.entity.Shop;
import com.liangweibang.o2o.entity.ShopCategory;
import com.liangweibang.o2o.enums.ShopStateEnum;
import com.liangweibang.o2o.service.AreaService;
import com.liangweibang.o2o.service.ShopCategoryService;
import com.liangweibang.o2o.service.ShopService;
import com.liangweibang.o2o.util.HttpServletRequestUtil;
import com.liangweibang.o2o.util.ImageUtil;
import com.liangweibang.o2o.util.PathUtil;

@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {
	
	@Autowired
	private ShopService shopService;
	@Autowired
	private ShopCategoryService shopCategoryService;
	@Autowired
	private AreaService areaService;
	
	@RequestMapping(value = "/getshopinitinfo", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopInitInfo() {
		Map<String, Object> modelMAp = new HashMap<>();
		List<ShopCategory> shopCategories = new ArrayList<ShopCategory>();
		List<Area> areas = new ArrayList<Area>();
		
		try {
			shopCategories = shopCategoryService.getShopCategoryList(new ShopCategory());
			areas = areaService.getAreaList();
		} catch(Exception e) {
			
		}
		return null;
	}
	
	@RequestMapping(value = "/registerShop", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> registerShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		// 1. 接收并转化相应的参数，包括店铺信息以及图片信息
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
			shop = mapper.readValue(shopStr, Shop.class);
		} catch(Exception e) {
			modelMap.put("success", false);
			modelMap.put("errmsg", e.getMessage());
			return modelMap;
		}
		
		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		if (resolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
		} else {
			modelMap.put("success", false);
			modelMap.put("errmsg", "上传图片不能为空");
			return modelMap;
		}
		// 2. 注册店铺
		if (shop != null && shopImg != null) {
			PersonInfo owner = new PersonInfo();
			// TODO session
			owner.setUserId(1L);
			shop.setOwner(owner);
			ShopExecution shopExecution;
			try {
				shopExecution = shopService.addShop(shop, shopImg.getInputStream(), shopImg.getOriginalFilename());
				if (shopExecution.getState() == ShopStateEnum.CHECK.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errmsg", shopExecution.getStateInfo());
				}
			} catch (IOException e) {
				modelMap.put("success", false);
				modelMap.put("errmsg", e.getMessage());
				return modelMap;
			}
			return modelMap;
		} else {
			modelMap.put("success", false);
			modelMap.put("errmsg", "请输入店铺信息");
			return modelMap;
		}
	}
	
//	private static void inputStreamToFile(InputStream inputStream, File file) {
//		FileOutputStream outputStream = null;
//		try {
//			outputStream = new FileOutputStream(file);
//			int bytesRead = 0;
//			byte[] buffer = new byte[1024];
//			while((bytesRead = inputStream.read(buffer)) != -1) {
//				outputStream.write(buffer, 0, bytesRead);
//			}
//		} catch(Exception e) {
//			throw new RuntimeException("调用 inputStreamToFile 产生异常：" + e.getMessage());
//		} finally {
//			try {
//				if (outputStream != null) {
//					outputStream.close();
//				}
//				if (inputStream != null) {
//					inputStream.close();
//				}
//			} catch(IOException e) {
//				throw new RuntimeException("inputStreamToFile 关闭 io 产生异常：" + e.getMessage());
//			}
//		}
//	}
}
