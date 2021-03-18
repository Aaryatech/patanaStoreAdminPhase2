package com.ats.tril.model.billbook;

public class BillReceiptDetail {

	private int brDetailId;

	private int brId;
	private int billId;
	private String billNo;
	private String billDate;
	private float billTotal; 
	private float billPending;
	private float billPaid;
	private int delStatus;
	private int exInt1;
	private int exInt2;
	private String exVar1;
	private String exVar2;
	private float exFloat1;
	private float exFloat2;
	
	

	public BillReceiptDetail() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public BillReceiptDetail(int brDetailId, int brId, int billId, String billNo, String billDate, float billTotal,
			float billPending, float billPaid, int delStatus, int exInt1, int exInt2, String exVar1, String exVar2,
			float exFloat1, float exFloat2) {
		super();
		this.brDetailId = brDetailId;
		this.brId = brId;
		this.billId = billId;
		this.billNo = billNo;
		this.billDate = billDate;
		this.billTotal = billTotal;
		this.billPending = billPending;
		this.billPaid = billPaid;
		this.delStatus = delStatus;
		this.exInt1 = exInt1;
		this.exInt2 = exInt2;
		this.exVar1 = exVar1;
		this.exVar2 = exVar2;
		this.exFloat1 = exFloat1;
		this.exFloat2 = exFloat2;
	}



	
	
	



	public float getBillPending() {
		return billPending;
	}



	public void setBillPending(float billPending) {
		this.billPending = billPending;
	}



	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public int getBrDetailId() {
		return brDetailId;
	}

	public void setBrDetailId(int brDetailId) {
		this.brDetailId = brDetailId;
	}

	public int getBrId() {
		return brId;
	}

	public void setBrId(int brId) {
		this.brId = brId;
	}

	public int getBillId() {
		return billId;
	}

	public void setBillId(int billId) {
		this.billId = billId;
	}

	public String getBillDate() {
		return billDate;
	}

	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}

	public float getBillTotal() {
		return billTotal;
	}

	public void setBillTotal(float billTotal) {
		this.billTotal = billTotal;
	}

	public float getBillPaid() {
		return billPaid;
	}

	public void setBillPaid(float billPaid) {
		this.billPaid = billPaid;
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

	
	@Override
	public String toString() {
		return "BillReceiptDetail [brDetailId=" + brDetailId + ", brId=" + brId + ", billId=" + billId + ", billNo="
				+ billNo + ", billDate=" + billDate + ", billTotal=" + billTotal + ", billPending=" + billPending
				+ ", billPaid=" + billPaid + ", delStatus=" + delStatus + ", exInt1=" + exInt1 + ", exInt2=" + exInt2
				+ ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + ", exFloat1=" + exFloat1 + ", exFloat2=" + exFloat2
				+ "]";
	}

}
