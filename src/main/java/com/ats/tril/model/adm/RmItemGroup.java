package com.ats.tril.model.adm;

public class RmItemGroup {

	
	private int grpId;
	
	private int delStatus;
	
	private String grpName;
private int exInt1;//CATID SAC 10-07-2021
	

	public int getExInt1() {
		return exInt1;
	}

	public void setExInt1(int exInt1) {
		this.exInt1 = exInt1;
	}
	public int getGrpId() {
		return grpId;
	}

	public void setGrpId(int grpId) {
		this.grpId = grpId;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	public String getGrpName() {
		return grpName;
	}

	public void setGrpName(String grpName) {
		this.grpName = grpName;
	}

	@Override
	public String toString() {
		return "RmItemGroup [grpId=" + grpId + ", delStatus=" + delStatus + ", grpName=" + grpName + "]";
	}
	
	
}
