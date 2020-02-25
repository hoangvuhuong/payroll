package adstech.vn.com.payroll.model;

import java.util.Date;
import java.util.List;

public class BonusDefinition {
	private Long bonusId;
	private String bonusName;
	private String description;
	private Long departmentId;
	private String departmentName;
	private String serviceType;
	private String employeeLevel;
	private String bonusType;
	private String bonusMethod;
	private String bonusPeriod;
	private String bonusFactor;
	private Boolean fromAds;
	private Boolean isInternal;
	private Boolean isExternal;
	private String status;
	private Date createdAt;
	private Date updatedAt;
	private String createdBy;
	private String updatedBy;
	
	private List<BonusThreshold> bonusThresholds;
	private List<BonusThreshold> bonusReferences;
	
	public Long getBonusId() {
		return bonusId;
	}
	public void setBonusId(Long bonusId) {
		this.bonusId = bonusId;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getEmployeeLevel() {
		return employeeLevel;
	}
	public void setEmployeeLevel(String employeeLevel) {
		this.employeeLevel = employeeLevel;
	}
	public String getBonusType() {
		return bonusType;
	}
	public void setBonusType(String bonusType) {
		this.bonusType = bonusType;
	}
	public String getBonusMethod() {
		return bonusMethod;
	}
	public void setBonusMethod(String bonusMethod) {
		this.bonusMethod = bonusMethod;
	}
	public Boolean getFromAds() {
		return fromAds;
	}
	public void setFromAds(Boolean fromAds) {
		this.fromAds = fromAds;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Boolean getIsInternal() {
		return isInternal;
	}
	public void setIsInternal(Boolean isInternal) {
		this.isInternal = isInternal;
	}
	public Boolean getIsExternal() {
		return isExternal;
	}
	public void setIsExternal(Boolean isExternal) {
		this.isExternal = isExternal;
	}
	public List<BonusThreshold> getBonusThresholds() {
		return bonusThresholds;
	}
	public void setBonusThresholds(List<BonusThreshold> bonusThresholds) {
		this.bonusThresholds = bonusThresholds;
	}
	public List<BonusThreshold> getBonusReferences() {
		return bonusReferences;
	}
	public void setBonusReferences(List<BonusThreshold> bonusReferences) {
		this.bonusReferences = bonusReferences;
	}
	public String getBonusPeriod() {
		return bonusPeriod;
	}
	public void setBonusPeriod(String bonusPeriod) {
		this.bonusPeriod = bonusPeriod;
	}
	public String getBonusFactor() {
		return bonusFactor;
	}
	public void setBonusFactor(String bonusFactor) {
		this.bonusFactor = bonusFactor;
	}
	public String getBonusName() {
		return bonusName;
	}
	public void setBonusName(String bonusName) {
		this.bonusName = bonusName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	
}
