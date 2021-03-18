package com.ats.tril.model.billbook;

public class BillSettledList {

	private int brId;
	private String brNo;
	private String brDate;
	private String brDateTime;
	private float amt;
	private int payMode;
	private String payDesc;
	private String remark;
	private int userId;
	private String userName;

	private float billTotal;
	private float billPending;
	private float billPaid;

	public int getBrId() {
		return brId;
	}

	public void setBrId(int brId) {
		this.brId = brId;
	}

	public String getBrNo() {
		return brNo;
	}

	public void setBrNo(String brNo) {
		this.brNo = brNo;
	}

	public String getBrDate() {
		return brDate;
	}

	public void setBrDate(String brDate) {
		this.brDate = brDate;
	}

	public float getAmt() {
		return amt;
	}

	public void setAmt(float amt) {
		this.amt = amt;
	}

	public int getPayMode() {
		return payMode;
	}

	public void setPayMode(int payMode) {
		this.payMode = payMode;
	}

	public String getPayDesc() {
		return payDesc;
	}

	public void setPayDesc(String payDesc) {
		this.payDesc = payDesc;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public float getBillTotal() {
		return billTotal;
	}

	public void setBillTotal(float billTotal) {
		this.billTotal = billTotal;
	}

	public float getBillPending() {
		return billPending;
	}

	public void setBillPending(float billPending) {
		this.billPending = billPending;
	}

	public float getBillPaid() {
		return billPaid;
	}

	public void setBillPaid(float billPaid) {
		this.billPaid = billPaid;
	}

	public String getBrDateTime() {
		return brDateTime;
	}

	public void setBrDateTime(String brDateTime) {
		this.brDateTime = brDateTime;
	}

	@Override
	public String toString() {
		return "BillSettledList [brId=" + brId + ", brNo=" + brNo + ", brDate=" + brDate + ", brDateTime=" + brDateTime
				+ ", amt=" + amt + ", payMode=" + payMode + ", payDesc=" + payDesc + ", remark=" + remark + ", userId="
				+ userId + ", userName=" + userName + ", billTotal=" + billTotal + ", billPending=" + billPending
				+ ", billPaid=" + billPaid + "]";
	}
	
}
