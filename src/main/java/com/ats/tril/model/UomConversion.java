package com.ats.tril.model;

public class UomConversion {

	private int convertId;

	private int uom1;
	private int uom2; 
	private float ration1;
	private float ration2;
	private String remark;
	private int delStuatus;
	public int getConvertId() {
		return convertId;
	}
	public void setConvertId(int convertId) {
		this.convertId = convertId;
	}
	public int getUom1() {
		return uom1;
	}
	public void setUom1(int uom1) {
		this.uom1 = uom1;
	}
	public int getUom2() {
		return uom2;
	}
	public void setUom2(int uom2) {
		this.uom2 = uom2;
	}
	public float getRation1() {
		return ration1;
	}
	public void setRation1(float ration1) {
		this.ration1 = ration1;
	}
	public float getRation2() {
		return ration2;
	}
	public void setRation2(float ration2) {
		this.ration2 = ration2;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getDelStuatus() {
		return delStuatus;
	}
	public void setDelStuatus(int delStuatus) {
		this.delStuatus = delStuatus;
	}
	@Override
	public String toString() {
		return "UomConversion [convertId=" + convertId + ", uom1=" + uom1 + ", uom2=" + uom2 + ", ration1=" + ration1
				+ ", ration2=" + ration2 + ", remark=" + remark + ", delStuatus=" + delStuatus + "]";
	}

	 

}
