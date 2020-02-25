package adstech.vn.com.payroll.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import adstech.vn.com.payroll.contract.ResponseContract;
import adstech.vn.com.payroll.service.IPayrollService;

@RestController
public class PayrollController {
	@Autowired
	IPayrollService payrollService;
	
	@GetMapping("/user/{userId}/current-income")
	public ResponseContract<?> getCurrentImcome(@PathVariable Long userId){
		return payrollService.getCurrentIncome(userId);
	}
}
