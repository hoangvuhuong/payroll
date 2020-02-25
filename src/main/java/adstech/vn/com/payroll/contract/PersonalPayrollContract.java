package adstech.vn.com.payroll.contract;

import java.math.BigDecimal;
import java.util.List;

public class PersonalPayrollContract {
	private Long userId;
	private float numOfWorkday;
	private BigDecimal basicSalaryIncome;
	private List<BonusIncomeContract> bonusIncomes;
	private BigDecimal grossIncome;
	private BigDecimal netIncome;
	private List<Contract> contracts;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public float getNumOfWorkday() {
		return numOfWorkday;
	}
	public void setNumOfWorkday(float numOfWorkday) {
		this.numOfWorkday = numOfWorkday;
	}
	public BigDecimal getBasicSalaryIncome() {
		return basicSalaryIncome;
	}
	public void setBasicSalaryIncome(BigDecimal basicSalaryIncome) {
		this.basicSalaryIncome = basicSalaryIncome;
	}
	public List<BonusIncomeContract> getBonusIncomes() {
		return bonusIncomes;
	}
	public void setBonusIncomes(List<BonusIncomeContract> bonusIncomes) {
		this.bonusIncomes = bonusIncomes;
	}
	public BigDecimal getGrossIncome() {
		return grossIncome;
	}
	public void setGrossIncome(BigDecimal grossIncome) {
		this.grossIncome = grossIncome;
	}
	public BigDecimal getNetIncome() {
		return netIncome;
	}
	public void setNetIncome(BigDecimal netIncome) {
		this.netIncome = netIncome;
	}
	public List<Contract> getContracts() {
		return contracts;
	}
	public void setContracts(List<Contract> contracts) {
		this.contracts = contracts;
	}
}
