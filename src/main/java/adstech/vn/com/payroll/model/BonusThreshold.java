package adstech.vn.com.payroll.model;

import java.math.BigDecimal;
import java.util.Date;

public class BonusThreshold {
	private Long id;
	private Long bonusId;
	private BigDecimal upperBound;
	private BigDecimal lowerBound;
	private BigDecimal bonusPercent;
	private BigDecimal bonusValue;
	private Boolean isReference;
	private Integer bonusLevel;
	private String status;
	private Date createdAt;
	private String createdBy;
	private Date updatedAt;
	private String updatedBy;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getBonusId() {
		return bonusId;
	}
	public void setBonusId(Long bonusId) {
		this.bonusId = bonusId;
	}
	public BigDecimal getUpperBound() {
		return upperBound;
	}
	public void setUpperBound(BigDecimal upperBound) {
		this.upperBound = upperBound;
	}
	public BigDecimal getLowerBound() {
		return lowerBound;
	}
	public void setLowerBound(BigDecimal lowerBound) {
		this.lowerBound = lowerBound;
	}
	public BigDecimal getBonusPercent() {
		return bonusPercent;
	}
	public void setBonusPercent(BigDecimal bonusPercent) {
		this.bonusPercent = bonusPercent;
	}
	public BigDecimal getBonusValue() {
		return bonusValue;
	}
	public void setBonusValue(BigDecimal bonusValue) {
		this.bonusValue = bonusValue;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Boolean getIsReference() {
		return isReference;
	}
	public void setIsReference(Boolean isReference) {
		this.isReference = isReference;
	}
	public Integer getBonusLevel() {
		return bonusLevel;
	}
	public void setBonusLevel(Integer bonusLevel) {
		this.bonusLevel = bonusLevel;
	}
	
}
