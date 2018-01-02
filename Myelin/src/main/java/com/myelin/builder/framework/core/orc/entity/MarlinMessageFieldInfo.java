package com.myelin.builder.framework.core.orc.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("serial")
public class MarlinMessageFieldInfo  implements Serializable {
	
	final static Logger logger = LogManager.getLogger(MarlinMessageFieldInfo.class);
	
	private Integer fieldIndex;
	private String fieldName;
	private String fieldType;
	private String fieldValue;
	private boolean isInOriginMsg;  // 원본 메시지에서 파생된 필드(true)인지 아니면 추가적인 사용자 정의에 의한 필드(false)인지 구분.
	private boolean isUseScript;
	private boolean isIpInfo;
	private MarlinIpInfo marlinIpInfo;
	private boolean isOtherIpInfoRef;
	private String otherIpInfoRefFieldName;
	private String otherIpInfoRefSubName;
	private String script;
	private boolean isTimestamp;
	private String timestampFormat;

	public MarlinMessageFieldInfo() {
		isInOriginMsg = false;
		isUseScript = false;
		isIpInfo = false;
		isOtherIpInfoRef = false;
		marlinIpInfo = new MarlinIpInfo();
	}

	public Integer getFieldIndex() {
		return fieldIndex;
	}

	public void setFieldIndex(Integer fieldIndex) {
		this.fieldIndex = fieldIndex;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public boolean isInOriginMsg() {
		return isInOriginMsg;
	}

	public void setInOriginMsg(boolean isInOriginMsg) {
		this.isInOriginMsg = isInOriginMsg;
	}

	public boolean isUseScript() {
		return isUseScript;
	}

	public void setUseScript(boolean isUseScript) {
		this.isUseScript = isUseScript;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public boolean isIpInfo() {
		return isIpInfo;
	}

	public void setIpInfo(boolean isIpInfo) {
		this.isIpInfo = isIpInfo;
	}

	public MarlinIpInfo getMarlinIpInfo() {
		return marlinIpInfo;
	}

	public void setMarlinIpInfo(MarlinIpInfo marlinIpInfo) {
		this.marlinIpInfo = marlinIpInfo;
	}

	public boolean isOtherIpInfoRef() {
		return isOtherIpInfoRef;
	}

	public void setOtherIpInfoRef(boolean isOtherIpInfoRef) {
		this.isOtherIpInfoRef = isOtherIpInfoRef;
	}

	public String getOtherIpInfoRefFieldName() {
		return otherIpInfoRefFieldName;
	}

	public void setOtherIpInfoRefFieldName(String otherIpInfoRefFieldName) {
		this.otherIpInfoRefFieldName = otherIpInfoRefFieldName;
	}

	public String getOtherIpInfoRefSubName() {
		return otherIpInfoRefSubName;
	}

	public void setOtherIpInfoRefSubName(String otherIpInfoRefSubName) {
		this.otherIpInfoRefSubName = otherIpInfoRefSubName;
	}

	public boolean isTimestamp() {
		return isTimestamp;
	}

	public void setTimestamp(boolean isTimestamp) {
		this.isTimestamp = isTimestamp;
	}

	public String getTimestampFormat() {
		return timestampFormat;
	}

	public void setTimestampFormat(String timestampFormat) {
		this.timestampFormat = timestampFormat;
	}

	

	@Override
	public String toString() {
		return "MarlinMessageFieldInfo [fieldIndex=" + fieldIndex + ", fieldName=" + fieldName + ", fieldType="
				+ fieldType + ", fieldValue=" + fieldValue + ", isInOriginMsg=" + isInOriginMsg + ", isUseScript="
				+ isUseScript + ", isIpInfo=" + isIpInfo + ", marlinIpInfo=" + marlinIpInfo + ", isOtherIpInfoRef="
				+ isOtherIpInfoRef + ", otherIpInfoRefFieldName=" + otherIpInfoRefFieldName + ", otherIpInfoRefSubName="
				+ otherIpInfoRefSubName + ", script=" + script + ", isTimestamp=" + isTimestamp + ", timestampFormat="
				+ timestampFormat + "]";
	}

	public MarlinMessageFieldInfo cloneMessageField() {
		MarlinMessageFieldInfo fieldInfo = new MarlinMessageFieldInfo();
		
		fieldInfo.setFieldIndex(getFieldIndex());
		fieldInfo.setFieldName(getFieldName());
		fieldInfo.setFieldType(getFieldType());
		fieldInfo.setFieldValue(getFieldValue());
		fieldInfo.setInOriginMsg(isInOriginMsg);
		fieldInfo.setOtherIpInfoRef(isOtherIpInfoRef);
		fieldInfo.setOtherIpInfoRefFieldName(otherIpInfoRefFieldName);
		fieldInfo.setOtherIpInfoRefSubName(otherIpInfoRefSubName);
		fieldInfo.setIpInfo(isIpInfo);
		fieldInfo.setTimestamp(isTimestamp);
		fieldInfo.setTimestampFormat(this.timestampFormat);
		

		MarlinIpInfo marlinIpInfoTemp = new MarlinIpInfo();
		fieldInfo.setMarlinIpInfo(marlinIpInfoTemp);
		
		fieldInfo.setScript(getScript());
		fieldInfo.setUseScript(isUseScript);
		
		return fieldInfo;
	}
	
	public Timestamp convertStrToTimeStamp(String value) {
		Timestamp timest = null;
		
		try{
//		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
		    SimpleDateFormat dateFormat = new SimpleDateFormat(getTimestampFormat());
		    Date parsedDate = dateFormat.parse(value);
		    timest = new java.sql.Timestamp(parsedDate.getTime());
		}catch(Exception e){//this generic but you can control another types of exception
			logger.error(e.getMessage());  
			return null;
		}
		
		return timest;
		
	}
	
	public long convertStrToDate(String value) {
		long dateLong = 0;
		
		try{
//		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
		    SimpleDateFormat dateFormat = new SimpleDateFormat(getTimestampFormat());
		    Date parsedDate = dateFormat.parse(value);
		    dateLong = parsedDate.getTime();
		}catch(Exception e){//this generic but you can control another types of exception
			logger.error(e.getMessage());  
			return 0;
		}
		
		return dateLong;
		
	}
	
	
}
