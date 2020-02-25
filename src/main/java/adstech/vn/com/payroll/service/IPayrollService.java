package adstech.vn.com.payroll.service;

import adstech.vn.com.payroll.contract.ResponseContract;

public interface IPayrollService {
	public ResponseContract<?> getCurrentIncome(Long userId);
}
