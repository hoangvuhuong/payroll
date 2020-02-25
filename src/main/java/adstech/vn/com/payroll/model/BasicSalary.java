package adstech.vn.com.payroll.model;

import java.math.BigDecimal;
import java.util.Date;

public class BasicSalary {
	private Long id;
	private Long departmentId;
	private String employeeLevel;
	private Integer salaryCoefficient;
	private BigDecimal basicSalary;
	private Boolean checkKpi;
	private String status;
	private Date createdAt;
	private Date updatedAt;
	private String createdBy;
	private String updatedBy;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	public String getEmployeeLevel() {
		return employeeLevel;
	}
	public void setEmployeeLevel(String employeeLevel) {
		this.employeeLevel = employeeLevel;
	}
	public Integer getSalaryCoefficient() {
		return salaryCoefficient;
	}
	public void setSalaryCoefficient(Integer salaryCoefficient) {
		this.salaryCoefficient = salaryCoefficient;
	}
	public BigDecimal getBasicSalary() {
		return basicSalary;
	}
	public void setBasicSalary(BigDecimal basicSalary) {
		this.basicSalary = basicSalary;
	}
	public Boolean getCheckKpi() {
		return checkKpi;
	}
	public void setCheckKpi(Boolean checkKpi) {
		this.checkKpi = checkKpi;
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
	
}
