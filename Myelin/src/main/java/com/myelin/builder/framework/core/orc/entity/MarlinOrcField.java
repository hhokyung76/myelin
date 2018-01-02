package com.myelin.builder.framework.core.orc.entity;

public class MarlinOrcField {

	public enum OrcFieldType { Int, Decimal, Double, String, Long, Timestamp, Char, Date}
	
	private String fieldName;
	private OrcFieldType fieldType;
	
	public MarlinOrcField(String fieldName, OrcFieldType fieldType) {
		super();
		this.fieldName = fieldName;
		this.fieldType = fieldType;
	}
	
	public MarlinOrcField() {
	}
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public OrcFieldType getFieldType() {
		return fieldType;
	}
	public void setFieldType(OrcFieldType fieldType) {
		this.fieldType = fieldType;
	}
	
	
	
}
