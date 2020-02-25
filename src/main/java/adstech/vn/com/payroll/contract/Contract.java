package adstech.vn.com.payroll.contract;

import java.math.BigDecimal;
import java.util.Date;

public class Contract {
	private String contractName;
	private Date signDate;
	private BigDecimal revenue;//doanh số thu tiền
	private BigDecimal revenueAfterTax;//doanh số sau thuế
	private BigDecimal finalRevenue;//doanh so tinh thuong
	private BigDecimal receivedRevenue; //doanh so thuc hien
	private String serviceType;
	public String getContractName() {
		return contractName;
	}
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	public Date getSignDate() {
		return signDate;
	}
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	public BigDecimal getRevenue() {
		return revenue;
	}
	public void setRevenue(BigDecimal revenue) {
		this.revenue = revenue;
	}
	public BigDecimal getRevenueAfterTax() {
		return revenueAfterTax;
	}
	public void setRevenueAfterTax(BigDecimal revenueAfterTax) {
		this.revenueAfterTax = revenueAfterTax;
	}
	public BigDecimal getFinalRevenue() {
		return finalRevenue;
	}
	public void setFinalRevenue(BigDecimal finalRevenue) {
		this.finalRevenue = finalRevenue;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public BigDecimal getReceivedRevenue() {
		return receivedRevenue;
	}
	public void setReceivedRevenue(BigDecimal receivedRevenue) {
		this.receivedRevenue = receivedRevenue;
	}
}
