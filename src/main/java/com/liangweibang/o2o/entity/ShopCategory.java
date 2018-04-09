package com.liangweibang.o2o.entity;

import java.util.Date;

public class ShopCategory {

	// id
	private Long shopCategoryId;
	// 名称
	private String name;
	// 描述
	private String desc;
	// 头图
	private String img;
	// 权重
	private Integer priority;
	// 创建时间
	private Date createTime;
	// 修改时间
	private Date updateTime;
	// 上级
	private ShopCategory parent;

	
}
