package com.liangweibang.o2o.web.shopadmin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liangweibang.o2o.dto.ProductCategoryExecution;
import com.liangweibang.o2o.dto.Result;
import com.liangweibang.o2o.entity.ProductCategory;
import com.liangweibang.o2o.entity.Shop;
import com.liangweibang.o2o.enums.ProductCategoryStateEnum;
import com.liangweibang.o2o.service.ProductCategoryService;
import com.liangweibang.o2o.service.ShopCategoryService;

@Controller
@RequestMapping("/shopadmin")
public class ProductCategoryManagementController {

	@Autowired
	private ProductCategoryService productCategoryService;
	
	@RequestMapping(value = "/getproductcategorylist", method = RequestMethod.GET)
	@ResponseBody
	private Result<List<ProductCategory>> getProductCategoryList(HttpServletRequest request) {
		Shop shop = new Shop();
		shop.setShopId(3l);
		request.getSession().setAttribute("currentShop", shop);
		
		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
		if (currentShop != null && currentShop.getShopId() > 0) {
			List<ProductCategory> productCategorieList = productCategoryService.getProductCategoryList(currentShop.getShopId());
			return new Result<>(true, productCategorieList);
		} else {
			ProductCategoryStateEnum productCategoryStateEnum = ProductCategoryStateEnum.INNER_ERROR;
			return new Result<>(false, productCategoryStateEnum.getState(), productCategoryStateEnum.getStateInfo());
		}
	}
	
	@RequestMapping(value = "/addproductcategory", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> addProductCategory(@RequestBody List<ProductCategory> productCategoryList, HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
		for (ProductCategory productCategory : productCategoryList) {
			productCategory.setShopId(currentShop.getShopId());
		}
		if (productCategoryList != null && productCategoryList.size() > 0) {
			try {
				ProductCategoryExecution productCategoryExecution = productCategoryService.batchAddProductCategory(productCategoryList);
				if (productCategoryExecution.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
					modelMap.put("productCategoryList", productCategoryExecution.getProductCategoryList());
				} else {
					modelMap.put("success", false);
					modelMap.put("errmsg", productCategoryExecution.getStateInfo());
				}
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errmsg", e.getMessage());
				return modelMap;
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errmsg", "请增加至少一个商品类别");
		}
		
		return modelMap;
	}
	
	@RequestMapping(value = "/removeproductcategory", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> removeProductCategory(Long productCategoryId, HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (productCategoryId != null && productCategoryId > 0) {
			try {
				Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
				ProductCategoryExecution productCategoryExecution = productCategoryService.deleteProductCategory(productCategoryId, currentShop.getShopId());
				if (productCategoryExecution.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errmsg", productCategoryExecution.getStateInfo());
				}
			} catch(Exception e) {
				modelMap.put("success", false);
				modelMap.put("errmsg", e.getMessage());
				return modelMap;
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errmsg", "请选择要删除的商品类别");
		}
		return modelMap;
	}
}
