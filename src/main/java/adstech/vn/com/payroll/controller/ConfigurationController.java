package adstech.vn.com.payroll.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import adstech.vn.com.payroll.contract.ResponseContract;
import adstech.vn.com.payroll.contract.ScheduleConfigContract;
import adstech.vn.com.payroll.model.BasicSalary;
import adstech.vn.com.payroll.model.BonusDefinition;
import adstech.vn.com.payroll.model.Department;
import adstech.vn.com.payroll.model.KpiDefinition;
import adstech.vn.com.payroll.model.Service;
import adstech.vn.com.payroll.model.Ticket;
import adstech.vn.com.payroll.repository.TicketRepository;
import adstech.vn.com.payroll.service.IConfigurationService;
import adstech.vn.com.payroll.util.CommonConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/config")
@Api(tags = "Api cấu hình")
public class ConfigurationController {
	@Autowired
	IConfigurationService configurationService;
	
	@PostMapping("/bonus-definition")
	@ApiOperation(value = "Tạo mới cấu hình thưởng", response = CreateUpdateResult.class)
	public ResponseContract<?> createBonusDefinition(@RequestBody BonusDefinition bonus) {
		return configurationService.createBonusDefinition(bonus);
	}

	@PutMapping("/bonus-definition/{id}")
	@ApiOperation(value = "Update cấu hình thưởng", response = CreateUpdateResult.class)
	public ResponseContract<?> updateBonusDefinition(@PathVariable Long id, @RequestBody BonusDefinition bonus) {
		return configurationService.updateBonusDefinition(id, bonus);
	}

	@DeleteMapping("/bonus-definition/{id}")
	@ApiOperation(value = "Xóa cấu hình thưởng", response = CreateUpdateResult.class)
	public ResponseContract<?> deleteBonusDefinition(@PathVariable Long id) {
		return configurationService.deleteBonusDefinition(id);
	}

	@DeleteMapping("/bonus-threshold/{id}")
	@ApiOperation(value = "Xóa ngưỡng thưởng", response = CreateUpdateResult.class)
	public ResponseContract<?> deleteBonusThreshold(@PathVariable Long id) {
		return configurationService.deleteBonusThresholds(id);
	}

	@GetMapping("/bonus-definition/{id}")
	@ApiOperation(value = "Lấy cấu hình thưởng theo id", response = GetBonusDefinitionResult.class)
	public ResponseContract<?> findBonusDefinitionById(@PathVariable Long id) {
		return configurationService.findBonusDefinitionById(id);
	}

	@GetMapping("/bonus-definitions")
	@ApiOperation(value = "Lấy cấu hình thưởng theo loại hình dịch vụ/phong ban", response = GetListBonusDefinitionResult.class)
	public ResponseContract<?> findBonusDefinitionByService(@RequestParam(required = false) String serviceType,
			@RequestParam(required = false) Long dId) {
		if(serviceType != null) {
			return configurationService.findBonusDefinitionByServiceType(serviceType);
		} else {
			return configurationService.findBonusDefinitionByDepartmentId(dId);
		}
	}

	@PostMapping("/basic-salary")
	@ApiOperation(value = "Tạo mới lương cơ bản", response = CreateUpdateResult.class)
	public ResponseContract<?> createBasicSalary(@RequestBody BasicSalary salary) {
		return configurationService.createBasicSalary(salary);
	}

	@PutMapping("/basic-salary/{id}")
	@ApiOperation(value = "Update lương cơ bản", response = CreateUpdateResult.class)
	public ResponseContract<?> updateBasicSalary(@RequestBody BasicSalary salary, @PathVariable Long id) {
		salary.setId(id);
		return configurationService.updateBasicSalary(salary);

	}

	@GetMapping("/basic-salary/{id}")
	@ApiOperation(value = "Lấy lương cơ bản theo id", response = GetBasicSalaryResult.class)
	public ResponseContract<?> findBasicSalaryById(@PathVariable Long id) {
		return configurationService.findBasicSalaryById(id);
	}

	@GetMapping("/basic-salaries")
	@ApiOperation(value = "Lấy lương cơ bản theo phòng ban", response = GetListBasicSalaryResult.class)
	public ResponseContract<?> findBasicSalaryByDepartment(@RequestParam Long dId) {
		return configurationService.findBasicSalaryByDepartment(dId);
	}

	@DeleteMapping("/basic-salary/{id}")
	@ApiOperation(value = "Xóa lương cơ bản", response = CreateUpdateResult.class)
	public ResponseContract<?> deleteBasicSalary(@PathVariable Long id) {
		return configurationService.deleteBasicSalary(id);
	}

	@PostMapping("/kpi-definition")
	@ApiOperation(value = "Thêm mới cấu hình kpi", response = CreateUpdateResult.class)
	public ResponseContract<?> createKpiDefinition(@RequestBody KpiDefinition kpi) {
		return configurationService.createKpiDefinition(kpi);
	}

	@PutMapping("/kpi-definition/{id}")
	@ApiOperation(value = "Update cấu hình kpi", response = CreateUpdateResult.class)
	public ResponseContract<?> updateKpiDefinition(@RequestBody KpiDefinition kpi, @PathVariable Long id) {
		kpi.setId(id);
		return configurationService.updateKpiDefinition(kpi);

	}

	@GetMapping("/kpi-definition/{id}")
	@ApiOperation(value = "Lấy cấu hình kpi theo id", response = GetKpiDefinitionResult.class)
	public ResponseContract<?> findKpiDefinitionById(@PathVariable Long id) {
		return configurationService.findKpiDefinitionById(id);
	}

	@GetMapping("/kpi-definitions")
	@ApiOperation(value = "Lấy cấu hình kpi theo phòng", response = GetListKpiDefinitionResult.class)
	public ResponseContract<?> findKpiDefinitionByServiceType(@RequestParam Long dId){
		return configurationService.findKpiDefinitionByDepartment(dId);
	}

	@DeleteMapping("/kpi-definition/{id}")
	@ApiOperation(value = "Xóa cấu hình kpi", response = CreateUpdateResult.class)
	public ResponseContract<?> deleteKpiDefinition(@PathVariable Long id) {
		return configurationService.deleteKpiDefinition(id);
	}

	@PostMapping("/working-schedule")
	@ApiOperation(value = "Update cấu hình lịch làm việc", response = CreateUpdateResult.class)
	public ResponseContract<?> updateWorkingSchedule(@RequestBody ScheduleConfigContract schedule) {
		return configurationService.saveSchedules(schedule);
	}

	@GetMapping("/working-schedule")
	@ApiOperation(value = "Lấy cấu hình lịch làm việc", response = GetListScheduleResult.class)
	public ResponseContract<?> getWorkingSchedule() {
		return configurationService.getSchedules();
	}

	@PostMapping("/department")
	@ApiOperation(value = "Tạo phòng", response = CreateUpdateResult.class)
	public ResponseContract<?> createDepartment(@RequestBody Department department) {
		return configurationService.createDepartment(department);
	}

	@GetMapping("/department/{id}")
	@ApiOperation(value = "Lấy phòng theo id", response = GetDepartmentResult.class)
	public ResponseContract<?> findDepartmentById(@PathVariable Long id) {
		return configurationService.getDepartment(id);
	}

	@PutMapping("/department/{id}")
	@ApiOperation(value = "Sửa phòng Theo id", response = CreateUpdateResult.class)
	public ResponseContract<?> updateDepartment(@RequestBody Department department, @PathVariable Long id) {
		department.setId(id);
		return configurationService.updateDepartment(department);
	}

	@DeleteMapping("/department/{id}")
	@ApiOperation(value = "Xóa phòng", response = CreateUpdateResult.class)
	public ResponseContract<?> deleteDepartment(@PathVariable Long id) {
		return configurationService.deleteDepartment(id);
	}

	@PostMapping("/service")
	@ApiOperation(value = "Tạo mới một dịch vụ", response = CreateUpdateResult.class)
	public ResponseContract<?> createService(@RequestBody Service service) {
		return configurationService.createService(service);
	}

	@PutMapping("/service/{id}")
	@ApiOperation(value = "Update một dịch vụ", response = CreateUpdateResult.class)
	public ResponseContract<?> updateService(@RequestBody Service service, @PathVariable Long id) {
		service.setId(id);
		return configurationService.updateService(service);
	}

	@DeleteMapping("/service/{id}")
	@ApiOperation(value = "Xóa một dịch vụ", response = CreateUpdateResult.class)
	public ResponseContract<?> deleteService(@PathVariable Long id) {
		return configurationService.deleteService(id);
	}

	@GetMapping("/service/{id}")
	@ApiOperation(value = "Lấy một dịch vụ", response = GetServiceResult.class)
	public ResponseContract<?> getService(@PathVariable Long id) {
		return configurationService.getService(id);
	}

	@GetMapping("/service")
	@ApiOperation(value = "Lấy danh sách dịch vụ theo loại hình dịch vụ", response = GetListServiceResult.class)
	public ResponseContract<?> getServiceByServiceType(@RequestParam String serviceType) {
		return configurationService.getServicetByServiceType(serviceType);
	}

	@GetMapping("/service/get-servicetype")
	@ApiOperation(value = "Lấy danh sách tất cả loại hình dịch vụ", response = GetListServiceType.class)
	public ResponseContract<?> getAllServiceType(){
		return configurationService.getAllServiceType();
	}
	@GetMapping("/service/getall")
	@ApiOperation(value = "Lấy danh sách tất cả dịch vụ", response = GetListService.class)
	public ResponseContract<?> getAllService(){
		return configurationService.getAllService();
	}
	private class CreateUpdateResult extends ResponseContract<Integer> {
		public CreateUpdateResult(String code, String message, Integer response) {
			super(code, message, response);
		}
	}

	private class GetBonusDefinitionResult extends ResponseContract<BonusDefinition> {
		public GetBonusDefinitionResult(String code, String message, BonusDefinition response) {
			super(code, message, response);
		}
	}

	private class GetListBonusDefinitionResult extends ResponseContract<List<BonusDefinition>> {
		public GetListBonusDefinitionResult(String code, String message, List<BonusDefinition> response) {
			super(code, message, response);
		}
	}

	private class GetBasicSalaryResult extends ResponseContract<BasicSalary> {
		public GetBasicSalaryResult(String code, String message, BasicSalary response) {
			super(code, message, response);
		}
	}

	private class GetListBasicSalaryResult extends ResponseContract<List<BasicSalary>> {
		public GetListBasicSalaryResult(String code, String message, List<BasicSalary> response) {
			super(code, message, response);
		}
	}

	private class GetKpiDefinitionResult extends ResponseContract<KpiDefinition> {
		public GetKpiDefinitionResult(String code, String message, KpiDefinition response) {
			super(code, message, response);
		}
	}

	private class GetListKpiDefinitionResult extends ResponseContract<List<KpiDefinition>> {
		public GetListKpiDefinitionResult(String code, String message, List<KpiDefinition> response) {
			super(code, message, response);
		}
	}

	private class GetListScheduleResult extends ResponseContract<List<ScheduleConfigContract>> {
		public GetListScheduleResult(String code, String message, List<ScheduleConfigContract> response) {
			super(code, message, response);
		}
	}

	private class GetDepartmentResult extends ResponseContract<Department> {
		public GetDepartmentResult(String code, String message, Department response) {
			super(code, message, response);
		}
	}

	private class GetServiceResult extends ResponseContract<Service> {
		public GetServiceResult(String code, String message, Service response) {
			super(code, message, response);
		}
	}

	private class GetListServiceResult extends ResponseContract<List<Service>> {
		public GetListServiceResult(String code, String message, List<Service> response) {
			super(code, message, response);
		}
	}
	private class GetListServiceType extends ResponseContract<List<String>> {
		public GetListServiceType(String code, String message, List<String> response) {
			super(code, message, response);
		}
	}
	private class GetListService extends ResponseContract<List<Service>> {
		public GetListService(String code, String message, List<Service> response) {
			super(code, message, response);
		}
	}
}

