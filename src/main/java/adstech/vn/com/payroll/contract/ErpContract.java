package adstech.vn.com.payroll.contract;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErpContract {
	private Long termId;
	private String termStatus;
	private String temType;
	private String contractCode;
	private Date adsStartTime;
	private Date adsEndTime;
	private Date contractBegin;
	private Date contractEnd;
	private BigDecimal vat;
	private BigDecimal vatAmount;
	private BigDecimal amount;
	private BigDecimal amountNotVat;
	private BigDecimal serviceFee;
	private BigDecimal actualBudget;
	private BigDecimal rewardedRevenue;
	private Object pivot;
	
	@JsonProperty("term_id")
	public Long getTermId() {
		return termId;
	}
	public void setTermId(Long termId) {
		this.termId = termId;
	}
	
	@JsonProperty("term_status")
	public String getTermStatus() {
		return termStatus;
	}
	public void setTermStatus(String termStatus) {
		this.termStatus = termStatus;
	}
	
	@JsonProperty("term_type")
	public String getTemType() {
		return temType;
	}
	public void setTemType(String temType) {
		this.temType = temType;
	}
	
	@JsonProperty("contract_code")
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	
	@JsonProperty("ads_start_time")
	public Date getAdsStartTime() {
		return adsStartTime;
	}
	public void setAdsStartTime(Date adsStartTime) {
		this.adsStartTime = adsStartTime;
	}
	
	@JsonProperty("ads_end_time")
	public Date getAdsEndTime() {
		return adsEndTime;
	}
	public void setAdsEndTime(Date adsEndTime) {
		this.adsEndTime = adsEndTime;
	}
	
	@JsonProperty("contract_begin")
	public Date getContractBegin() {
		return contractBegin;
	}
	public void setContractBegin(Date contractBegin) {
		this.contractBegin = contractBegin;
	}
	
	@JsonProperty("contract_end")
	public Date getContractEnd() {
		return contractEnd;
	}
	public void setContractEnd(Date contractEnd) {
		this.contractEnd = contractEnd;
	}
	
	@JsonProperty("vat")
	public BigDecimal getVat() {
		return vat;
	}
	public void setVat(BigDecimal vat) {
		this.vat = vat;
	}
	
	@JsonProperty("amount")
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	@JsonProperty("amount_not_vat")
	public BigDecimal getAmountNotVat() {
		return amountNotVat;
	}
	public void setAmountNotVat(BigDecimal amountNotVat) {
		this.amountNotVat = amountNotVat;
	}
	
	@JsonProperty("service_fee")
	public BigDecimal getServiceFee() {
		return serviceFee;
	}
	public void setServiceFee(BigDecimal serviceFee) {
		this.serviceFee = serviceFee;
	}
	
	@JsonProperty("actual_budget")
	public BigDecimal getActualBudget() {
		return actualBudget;
	}
	public void setActualBudget(BigDecimal actualBudget) {
		this.actualBudget = actualBudget;
	}
	
	@JsonProperty("rewarded_revenue")
	public BigDecimal getRewardedRevenue() {
		return rewardedRevenue;
	}
	public void setRewardedRevenue(BigDecimal rewardedRevenue) {
		this.rewardedRevenue = rewardedRevenue;
	}
	public Object getPivot() {
		return pivot;
	}
	public void setPivot(Object pivot) {
		this.pivot = pivot;
	}
	@JsonProperty("vat_amount")
	public BigDecimal getVatAmount() {
		return vatAmount;
	}
	public void setVatAmount(BigDecimal vatAmount) {
		this.vatAmount = vatAmount;
	}
}
