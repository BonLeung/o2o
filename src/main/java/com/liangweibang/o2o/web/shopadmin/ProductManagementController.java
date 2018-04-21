package com.liangweibang.o2o.web.shopadmin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liangweibang.o2o.dto.ImageHolder;
import com.liangweibang.o2o.dto.ProductExecution;
import com.liangweibang.o2o.entity.Product;
import com.liangweibang.o2o.entity.Shop;
import com.liangweibang.o2o.enums.ProductStateEnum;
import com.liangweibang.o2o.exception.ProductOperationException;
import com.liangweibang.o2o.service.ProductService;
import com.liangweibang.o2o.util.CodeUtil;
import com.liangweibang.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/shopadmin")
public class ProductManagementController {

	@Autowired
	private ProductService productService;
	
	// 支持上传商品详情图的最大数量
	private static final int MAXIMAGECOUNT = 6;
	
	@RequestMapping(value = "/addproduct", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> addProduct(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		// 验证码校验
		if (!CodeUtil.checkVerifyCode(request) ) {
			modelMap.put("success", false);
			modelMap.put("errmsg", "验证码错误");
			return modelMap;
		}
		// 接收前端参数的变量的初始化，包括商品，缩略图，商品详情图列表实体类
		ObjectMapper mapper = new ObjectMapper();
		Product product = null;
		String productStr = HttpServletRequestUtil.getString(request, "productStr");
		MultipartHttpServletRequest multipartHttpServletRequest = null;
		ImageHolder thumbnail = null;
		List<ImageHolder> productImgList = new ArrayList<>();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		try {
			// 如果请求中存在文件流，则取出相关的文件（包括缩略图和详情图）
			if (multipartResolver.isMultipart(request)) {
				multipartHttpServletRequest = (MultipartHttpServletRequest) request;
				// 取出缩略图并构建 ImgeHolder 对象
				CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("thumbnail");
				thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(), thumbnailFile.getInputStream());
				// 取出详情图列表，并构建List<ImageHolder>列表对象，最多支持六张图片上传
				for (int i = 0; i < MAXIMAGECOUNT; i++) {
					CommonsMultipartFile productImgFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("productImg" + i);
					if (productImgFile != null) {
						// 若取出的第 i 个详情图片文件流不为空，则将其加入详情图列表
						ImageHolder productImg = new ImageHolder(productImgFile.getOriginalFilename(), productImgFile.getInputStream());
						productImgList.add(productImg);
					} else {
						// 如取出的第 i 个详情图片文件流为空，则终止循环
						break;
					}
				}
			} else {
				modelMap.put("success", false);
				modelMap.put("errmsg", "上传图片不能为空");
				return modelMap;
			}
		} catch(Exception e) {
			modelMap.put("success", false);
			modelMap.put("errmsg", e.toString());
			return modelMap;
		}
		try {
			// 尝试获取前端传过来的表单 string 流并将其转换成 Product 实体类
			product = mapper.readValue(productStr, Product.class);
		} catch(Exception e) {
			modelMap.put("success", false);
			modelMap.put("errmsg", e.toString());
			return modelMap;
		}
		// 若 Product 信息、缩略图以及详情图列表为非空，则开始进行商品添加操作
		if (product != null && thumbnail != null && productImgList.size() > 0) {
			try {
				// 从 session 中获取当前店铺的 id 并赋值给 product，减少对前端数据的依赖
				Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
				Shop shop = new Shop();
				shop.setShopId(currentShop.getShopId());
				product.setShop(shop);
				// 执行添加操作
				ProductExecution productExecution = productService.addProduct(product, thumbnail, productImgList);
				if (productExecution.getState() == ProductStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errmsg", productExecution.getStateInfo());
				}
			} catch(ProductOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errmsg", e.toString());
				return modelMap;
			}
		}
		return modelMap;
	}
}
