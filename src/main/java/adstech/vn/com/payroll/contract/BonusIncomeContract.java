package adstech.vn.com.payroll.contract;

import java.math.BigDecimal;

public class BonusIncomeContract {
	private String serviceType;
	private String departmentName;
	private int numOfContract;
	private BigDecimal bonusFactorValue;//doanh số tin thuong
	private String bonusMethod;
	private BigDecimal bonusIncome; //So tien thuong
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public int getNumOfContract() {
		return numOfContract;
	}
	public void setNumOfContract(int numOfContract) {
		this.numOfContract = numOfContract;
	}
	public String getBonusMethod() {
		return bonusMethod;
	}
	public void setBonusMethod(String bonusMethod) {
		this.bonusMethod = bonusMethod;
	}
	public BigDecimal getBonusIncome() {
		return bonusIncome;
	}
	public void setBonusIncome(BigDecimal bonusIncome) {
		this.bonusIncome = bonusIncome;
	}
	public BigDecimal getBonusFactorValue() {
		return bonusFactorValue;
	}
	public void setBonusFactorValue(BigDecimal bonusFactorValue) {
		this.bonusFactorValue = bonusFactorValue;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
}
