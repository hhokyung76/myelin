package com.myelin.builder.framework.core.orc.entity;

import com.myelin.builder.framework.core.orc.entity.MarlinMessageFieldInfo;

public class StoreValueInfo {

	private String value;
	private MarlinMessageFieldInfo fieldInfo;

	public StoreValueInfo() {
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public MarlinMessageFieldInfo getFieldInfo() {
		return fieldInfo;
	}
	public void setFieldInfo(MarlinMessageFieldInfo fieldInfo) {
		this.fieldInfo = fieldInfo;
	}
	
}
