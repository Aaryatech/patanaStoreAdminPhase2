package com.ats.tril.model.billbook;

import java.util.List;


public class BillReceiptHeaderDisplay {

	private int brId;
	private String brNo;
	private String brDate;
	private String vendorName;
	private float amt;
	private int payMode;
	private String payDesc;
	private String remark;

	private String brDateTime;
	private int vendorId;
	private int userId;
	private int delStatus;
	private int exInt1;
	private int exInt2;
	private String exVar1;
	private String exVar2;
	private float exFloat1;
	private float exFloat2;
	private String userName;

	private List<BillReceiptDetail> billReceiptDetailList;

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

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
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

	public String getBrDateTime() {
		return brDateTime;
	}

	public void setBrDateTime(String brDateTime) {
		this.brDateTime = brDateTime;
	}

	public int getVendorId() {
		return vendorId;
	}

	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	public int getExInt1() {
		return exInt1;
	}

	public void setExInt1(int exInt1) {
		this.exInt1 = exInt1;
	}

	public int getExInt2() {
		return exInt2;
	}

	public void setExInt2(int exInt2) {
		this.exInt2 = exInt2;
	}

	public String getExVar1() {
		return exVar1;
	}

	public void setExVar1(String exVar1) {
		this.exVar1 = exVar1;
	}

	public String getExVar2() {
		return exVar2;
	}

	public void setExVar2(String exVar2) {
		this.exVar2 = exVar2;
	}

	public float getExFloat1() {
		return exFloat1;
	}

	public void setExFloat1(float exFloat1) {
		this.exFloat1 = exFloat1;
	}

	public float getExFloat2() {
		return exFloat2;
	}

	public void setExFloat2(float exFloat2) {
		this.exFloat2 = exFloat2;
	}

	public List<BillReceiptDetail> getBillReceiptDetailList() {
		return billReceiptDetailList;
	}

	public void setBillReceiptDetailList(List<BillReceiptDetail> billReceiptDetailList) {
		this.billReceiptDetailList = billReceiptDetailList;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "BillReceiptHeaderDisplay [brId=" + brId + ", brNo=" + brNo + ", brDate=" + brDate + ", vendorName="
				+ vendorName + ", amt=" + amt + ", payMode=" + payMode + ", payDesc=" + payDesc + ", remark=" + remark
				+ ", brDateTime=" + brDateTime + ", vendorId=" + vendorId + ", userId=" + userId + ", delStatus="
				+ delStatus + ", exInt1=" + exInt1 + ", exInt2=" + exInt2 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2
				+ ", exFloat1=" + exFloat1 + ", exFloat2=" + exFloat2 + ", userName=" + userName
				+ ", billReceiptDetailList=" + billReceiptDetailList + "]";
	}

}
