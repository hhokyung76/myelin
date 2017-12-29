package com.ltmh.app.entity;

import org.joda.time.DateTime;

public class LtmContentPlan {
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
	String ltmhFlag = "";
	String ltmhSubject = "";
	String ltmhContent = "";
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
	public String getLtmhFlag() {
		return ltmhFlag;
	}
	public void setLtmhFlag(String ltmhFlag) {
		this.ltmhFlag = ltmhFlag;
	}
	public String getLtmhContent() {
		return ltmhContent;
	}
	public void setLtmhContent(String ltmhContent) {
		this.ltmhContent = ltmhContent;
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
	
	public String getLtmhSubject() {
		return ltmhSubject;
	}
	public void setLtmhSubject(String ltmhSubject) {
		this.ltmhSubject = ltmhSubject;
	}
	@Override
	public String toString() {
		return "LtmContentPlan [id=" + id + ", custId=" + custId + ", custEmail=" + custEmail + ", ltmhFlag=" + ltmhFlag
				+ ", ltmhSubject=" + ltmhSubject + ", ltmhContent=" + ltmhContent + ", createTime=" + createTime
				+ ", firstTime=" + firstTime + ", pYear=" + pYear + ", pMonth=" + pMonth + ", pDay=" + pDay + ", pHour="
				+ pHour + ", pMinute=" + pMinute + "]";
	}
	
    
}
