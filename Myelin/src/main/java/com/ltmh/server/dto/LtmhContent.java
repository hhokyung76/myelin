package com.ltmh.server.dto;

import java.io.Serializable;

public class LtmhContent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8698353968945532392L;
	
	private String id;
	private String custId;
	private String custEmail;
	private String ltmhFlag;
	private String ltmhSubject;
	private String ltmhContent;
	
	public LtmhContent() {	}
	
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
	
	public String getLtmhSubject() {
		return ltmhSubject;
	}

	public void setLtmhSubject(String ltmhSubject) {
		this.ltmhSubject = ltmhSubject;
	}

	@Override
	public String toString() {
		return "LtmhContent [id=" + id + ", custId=" + custId + ", custEmail=" + custEmail + ", ltmhFlag=" + ltmhFlag
				+ ", ltmhSubject=" + ltmhSubject + ", ltmhContent=" + ltmhContent + "]";
	}

	
	
}
