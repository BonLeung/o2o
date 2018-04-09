package com.liangweibang.o2o.entity;

import java.util.Date;
import java.util.List;

public class Product {

	// id
	private Long productId;
	// 商品名称
	private String name;
	// 商品描述
	private String desc;
	// 缩略图地址
	private String addr;
	// 原价
	private String normalPrice;
	// 折扣价
	private String promotionPrice;
	// 权重
	private Integer priority;
	// 创建时间
	private Date createTime;
	// 修改时间
	private Date updateTime;
	// 商品状态：0:下架，1:上架
	private Integer status;
	// 商品详情图片
	private List<ProductImg> productImgs;
	// 商品类别
	private ProductCategory productCategory;
	// 商品对应的店铺
	private Shop shop;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getNormalPrice() {
		return normalPrice;
	}

	public void setNormalPrice(String normalPrice) {
		this.normalPrice = normalPrice;
	}

	public String getPromotionPrice() {
		return promotionPrice;
	}

	public void setPromotionPrice(String promotionPrice) {
		this.promotionPrice = promotionPrice;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<ProductImg> getProductImgs() {
		return productImgs;
	}

	public void setProductImgs(List<ProductImg> productImgs) {
		this.productImgs = productImgs;
	}

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

}
