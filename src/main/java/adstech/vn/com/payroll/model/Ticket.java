package adstech.vn.com.payroll.model;

import java.util.Date;

public class Ticket {
	private Long id;
	private Long userId;
	private Long approverId;
	private String approverEmail;
	private String reasonCode;
	private String reasonDetail;
	private String period;
	private String status;
	private Date date;
	private Date dueDate;
	private String rejectReason;
	private Date createdAt;
	private String createdBy;
	private Date udatedAt;
	private String updatedBy;
	private Date leavesFrom;
	private Date leavesTo;

	public Date getLeavesFrom() {
		return leavesFrom;
	}
	public void setLeavesFrom(Date leavesFrom) {
		this.leavesFrom = leavesFrom;
	}
	public Date getLeavesTo() {
		return leavesTo;
	}
	public void setLeavesTo(Date leavesTo) {
		this.leavesTo = leavesTo;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getApproverId() {
		return approverId;
	}
	public void setApproverId(Long approverId) {
		this.approverId = approverId;
	}
	public String getApproverEmail() {
		return approverEmail;
	}
	public void setApproverEmail(String approverEmail) {
		this.approverEmail = approverEmail;
	}
	
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	
	public String getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	public String getReasonDetail() {
		return reasonDetail;
	}
	public void setReasonDetail(String reasonDetail) {
		this.reasonDetail = reasonDetail;
	}
	public String getRejectReason() {
		return rejectReason;
	}
	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
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
	public Date getUdatedAt() {
		return udatedAt;
	}
	public void setUdatedAt(Date udatedAt) {
		this.udatedAt = udatedAt;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
}
