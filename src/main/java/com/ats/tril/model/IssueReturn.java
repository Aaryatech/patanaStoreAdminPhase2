package com.ats.tril.model;

public class IssueReturn {
	
	private int id;

	private int issueId;

	private int itemId;

	private int issueUom;

	private float returnQty;
	
	private int extra1;

	private int extra2;

	private String var1;

	private String var2;

	private String returnDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIssueId() {
		return issueId;
	}

	public void setIssueId(int issueId) {
		this.issueId = issueId;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getIssueUom() {
		return issueUom;
	}

	public void setIssueUom(int issueUom) {
		this.issueUom = issueUom;
	}

	public float getReturnQty() {
		return returnQty;
	}

	public void setReturnQty(float returnQty) {
		this.returnQty = returnQty;
	}

	public int getExtra1() {
		return extra1;
	}

	public void setExtra1(int extra1) {
		this.extra1 = extra1;
	}

	public int getExtra2() {
		return extra2;
	}

	public void setExtra2(int extra2) {
		this.extra2 = extra2;
	}

	public String getVar1() {
		return var1;
	}

	public void setVar1(String var1) {
		this.var1 = var1;
	}

	public String getVar2() {
		return var2;
	}

	public void setVar2(String var2) {
		this.var2 = var2;
	}

	public String getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}

	@Override
	public String toString() {
		return "IssueReturn [id=" + id + ", issueId=" + issueId + ", itemId=" + itemId + ", issueUom=" + issueUom
				+ ", returnQty=" + returnQty + ", extra1=" + extra1 + ", extra2=" + extra2 + ", var1=" + var1
				+ ", var2=" + var2 + ", returnDate=" + returnDate + "]";
	}
	
	

}
