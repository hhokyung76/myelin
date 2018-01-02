package com.myelin.builder.app.entity;

import org.joda.time.DateTime;

public class MyelinContentPlan {
	/**
	 * id string,
cust_id string,
cust_email string,
ltmh_flag string,
ltmh_content string,
create_time timestamp

p_year string,
 p_month string,
 p_day string,
 p_hour string,
 p_minute string
	 */
	
	String id = "";
	
	String custId = "";
	String custEmail = "";
	String myelinRoomId= "";
	String myelinFlag = "";
	String myelinSubject = "";
	String myelinContent = "";
	DateTime createTime = null;
	String firstTime = "";
	
	String pYear = "";
	String pMonth = "";
	String pDay = "";
	String pHour = "";
	String pMinute = "";
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getCustEmail() {
		return custEmail;
	}
	public void setCustEmail(String custEmail) {
		this.custEmail = custEmail;
	}
	
	public String getMyelinRoomId() {
		return myelinRoomId;
	}
	public void setMyelinRoomId(String myelinRoomId) {
		this.myelinRoomId = myelinRoomId;
	}
	public String getMyelinFlag() {
		return myelinFlag;
	}
	public void setMyelinFlag(String myelinFlag) {
		this.myelinFlag = myelinFlag;
	}
	public String getMyelinSubject() {
		return myelinSubject;
	}
	public void setMyelinSubject(String myelinSubject) {
		this.myelinSubject = myelinSubject;
	}
	public String getMyelinContent() {
		return myelinContent;
	}
	public void setMyelinContent(String myelinContent) {
		this.myelinContent = myelinContent;
	}
	public DateTime getCreateTime() {
		return createTime;
	}
	public void setCreateTime(DateTime createTime) {
		this.createTime = createTime;
	}
	
	public String getFirstTime() {
		return firstTime;
	}
	public void setFirstTime(String firstTime) {
		this.firstTime = firstTime;
	}
	public String getpYear() {
		return pYear;
	}
	public void setpYear(String pYear) {
		this.pYear = pYear;
	}
	public String getpMonth() {
		return pMonth;
	}
	public void setpMonth(String pMonth) {
		this.pMonth = pMonth;
	}
	public String getpDay() {
		return pDay;
	}
	public void setpDay(String pDay) {
		this.pDay = pDay;
	}
	public String getpHour() {
		return pHour;
	}
	public void setpHour(String pHour) {
		this.pHour = pHour;
	}
	public String getpMinute() {
		return pMinute;
	}
	public void setpMinute(String pMinute) {
		this.pMinute = pMinute;
	}
	@Override
	public String toString() {
		return "LtmContentPlan [id=" + id + ", custId=" + custId + ", custEmail=" + custEmail + ", myelinRoomId="
				+ myelinRoomId + ", myelinFlag=" + myelinFlag + ", myelinSubject=" + myelinSubject + ", myelinContent="
				+ myelinContent + ", createTime=" + createTime + ", firstTime=" + firstTime + ", pYear=" + pYear
				+ ", pMonth=" + pMonth + ", pDay=" + pDay + ", pHour=" + pHour + ", pMinute=" + pMinute + "]";
	}
	
    
}
