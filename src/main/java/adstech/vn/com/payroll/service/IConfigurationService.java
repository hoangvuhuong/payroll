package adstech.vn.com.payroll.service;

import adstech.vn.com.payroll.contract.ResponseContract;
import adstech.vn.com.payroll.contract.ScheduleConfigContract;
import adstech.vn.com.payroll.model.BasicSalary;
import adstech.vn.com.payroll.model.BonusDefinition;
import adstech.vn.com.payroll.model.Department;
import adstech.vn.com.payroll.model.KpiDefinition;
import adstech.vn.com.payroll.model.Service;
import adstech.vn.com.payroll.model.Ticket;

public interface IConfigurationService {

	public ResponseContract<?> createBasicSalary(BasicSalary salary);

	public ResponseContract<?> updateBasicSalary(BasicSalary salary);

	public ResponseContract<?> deleteBasicSalary(long id);

	public ResponseContract<?> findBasicSalaryByDepartment(Long departmebtId);

	public ResponseContract<?> findBasicSalaryById(long id);

	public ResponseContract<?> createBonusDefinition(BonusDefinition bonusDefine);

	public ResponseContract<?> updateBonusDefinition(Long id, BonusDefinition bonusDefine);

	public ResponseContract<?> deleteBonusDefinition(long bonusId);

	public ResponseContract<?> findBonusDefinitionById(long bonusId);

	public ResponseContract<?> findBonusDefinitionByServiceType(String serviceType);
	
	public ResponseContract<?> findBonusDefinitionByDepartmentId(Long departmentId);

	public ResponseContract<?> deleteBonusThresholds(long id);

	public ResponseContract<?> createKpiDefinition(KpiDefinition kpi);

	public ResponseContract<?> updateKpiDefinition(KpiDefinition kpi);

	public ResponseContract<?> findKpiDefinitionById(long id);
	
	public ResponseContract<?> findKpiDefinitionByDepartment(Long departmentId);
	
	public ResponseContract<?> deleteKpiDefinition(long id);

	public ResponseContract<?> saveSchedules(ScheduleConfigContract schedule);

	public ResponseContract<?> getSchedules();

	public ResponseContract<?> createDepartment(Department department);

	public ResponseContract<?> updateDepartment(Department department);

	public ResponseContract<?> deleteDepartment(Long departmentId);

	public ResponseContract<?> getDepartment(Long departmentId);

	// public ResponseContract<?> getServiceTypeByDepartmentID(Long departmentId);

	public ResponseContract<?> createService(Service service);

	public ResponseContract<?> updateService(Service service);

	public ResponseContract<?> deleteService(Long serviceId);

	public ResponseContract<?> getService(Long serviceId);

	public ResponseContract<?> getServicetByServiceType(String serviceType);

	public ResponseContract<?> getAllService();

	public ResponseContract<?> getAllServiceType();
	
	
}
