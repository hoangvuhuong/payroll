package adstech.vn.com.payroll.contract;

import java.util.List;

public class ScheduleConfigContract {
	private Integer payrollDate;
	private List<WorkingDayContract> workingSchedules;
	
	public Integer getPayrollDate() {
		return payrollDate;
	}
	public void setPayrollDate(Integer payrollDate) {
		this.payrollDate = payrollDate;
	}
	public List<WorkingDayContract> getWorkingSchedules() {
		return workingSchedules;
	}
	public void setWorkingSchedules(List<WorkingDayContract> workingSchedules) {
		this.workingSchedules = workingSchedules;
	}
}
