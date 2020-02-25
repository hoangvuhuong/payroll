package adstech.vn.com.payroll.contract;

import java.math.BigDecimal;

public class SummaryRevenueContract {
	private BigDecimal revenue;
	private BigDecimal afterTaxRevenue;
	private BigDecimal receivedRevenue;
	private BigDecimal finalRevenue;
	private int numOfContract;
	
	public SummaryRevenueContract() {
		this.revenue = BigDecimal.ZERO;
		this.afterTaxRevenue = BigDecimal.ZERO;
		this.receivedRevenue = BigDecimal.ZERO;
		this.finalRevenue = BigDecimal.ZERO;
		this.numOfContract = 0;
	}
	
	public BigDecimal getRevenue() {
		return revenue;
	}
	public void setRevenue(BigDecimal revenue) {
		this.revenue = revenue;
	}
	public BigDecimal getAfterTaxRevenue() {
		return afterTaxRevenue;
	}
	public void setAfterTaxRevenue(BigDecimal afterTaxRevenue) {
		this.afterTaxRevenue = afterTaxRevenue;
	}
	public BigDecimal getReceivedRevenue() {
		return receivedRevenue;
	}
	public void setReceivedRevenue(BigDecimal receivedRevenue) {
		this.receivedRevenue = receivedRevenue;
	}
	public BigDecimal getFinalRevenue() {
		return finalRevenue;
	}
	public void setFinalRevenue(BigDecimal finalRevenue) {
		this.finalRevenue = finalRevenue;
	}
	public int getNumOfContract() {
		return numOfContract;
	}
	public void setNumOfContract(int numOfContract) {
		this.numOfContract = numOfContract;
	}
}
