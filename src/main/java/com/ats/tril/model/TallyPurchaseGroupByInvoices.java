package com.ats.tril.model;

import java.util.List;


public class TallyPurchaseGroupByInvoices {

	List<PurchaseInvoices> purchaseInvoices;

	public List<PurchaseInvoices> getPurchaseInvoices() {
		return purchaseInvoices;
	}

	public void setPurchaseInvoices(List<PurchaseInvoices> purchaseInvoices) {
		this.purchaseInvoices = purchaseInvoices;
	}

	@Override
	public String toString() {
		return "TallyPurchaseGroupByInvoices [purchaseInvoices=" + purchaseInvoices + "]";
	}
	
}
