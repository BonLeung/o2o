package com.liangweibang.o2o.enums;

public enum ProductCategoryStateEnum {
	
	SUCCESS(1, "创建成功"),
	INNER_ERROR(-1001, "操作失败"),
	EMPTY_LIST(-1002, "添加个数少于1");
	
	private int state;
	
	private String stateInfo;
	
	private ProductCategoryStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}
	
	public int getState() {
		return this.state;
	}
	
	public String getStateInfo() {
		return this.stateInfo;
	}
	
	public static ProductCategoryStateEnum stateOf(int index) {
		for (ProductCategoryStateEnum productCategoryStateEnum : values()) {
			if (productCategoryStateEnum.getState() == index) {
				return productCategoryStateEnum;
			}
		}
		return null;
	}
}
