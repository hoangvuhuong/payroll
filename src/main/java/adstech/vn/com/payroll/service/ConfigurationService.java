package adstech.vn.com.payroll.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import adstech.vn.com.payroll.contract.ResponseContract;
import adstech.vn.com.payroll.contract.ScheduleConfigContract;
import adstech.vn.com.payroll.contract.WorkingDayContract;
import adstech.vn.com.payroll.model.BasicSalary;
import adstech.vn.com.payroll.model.BonusDefinition;
import adstech.vn.com.payroll.model.BonusThreshold;
import adstech.vn.com.payroll.model.Department;
import adstech.vn.com.payroll.model.DepartmentService;
import adstech.vn.com.payroll.model.KpiDefinition;
import adstech.vn.com.payroll.model.SystemParam;
import adstech.vn.com.payroll.model.WorkingSchedule;
import adstech.vn.com.payroll.repository.BasicSalaryRepository;
import adstech.vn.com.payroll.repository.BonusDefinitionRepository;
import adstech.vn.com.payroll.repository.BonusThresholdRepository;
import adstech.vn.com.payroll.repository.DepartmentRepository;
import adstech.vn.com.payroll.repository.DepartmentServiceRepository;
import adstech.vn.com.payroll.repository.KpiDefinitionRepository;
import adstech.vn.com.payroll.repository.ServiceRepository;
import adstech.vn.com.payroll.repository.SystemParamRepository;
import adstech.vn.com.payroll.repository.WorkingScheduleRepository;
import adstech.vn.com.payroll.util.CommonConstant;
import adstech.vn.com.payroll.util.CommonUtil;
import adstech.vn.com.payroll.util.DateTimeUtil;
import adstech.vn.com.payroll.model.Service;

@org.springframework.stereotype.Service
public class ConfigurationService implements IConfigurationService {
	@Autowired
	BasicSalaryRepository salaryRepository;

	@Autowired
	BonusDefinitionRepository bonusDefineRepository;

	@Autowired
	BonusThresholdRepository bonusThresholdRepository;

	@Autowired
	KpiDefinitionRepository kpiRepository;

	@Autowired
	WorkingScheduleRepository workingScheduleRepository;

	@Autowired
	SystemParamRepository systemParamRepository;

	@Autowired
	DepartmentRepository departmentRepository;

	@Autowired
	DepartmentServiceRepository departmentServiceRepository;

	@Autowired
	ServiceRepository serviceRepository;

	@Override
	public ResponseContract<?> createBasicSalary(BasicSalary salary) {
		try {
			return new ResponseContract<Integer>(CommonConstant.RESPONSE_STATUS_SUCCESS, null,
					salaryRepository.create(salary));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseContract<String>(CommonConstant.RESPONSE_STATUS_FAILURE, e.getMessage(), null);
		}
	}

	@Override
	public ResponseContract<?> updateBasicSalary(BasicSalary salary) {
		try {
			return new ResponseContract<Integer>(CommonConstant.RESPONSE_STATUS_SUCCESS, null,
					salaryRepository.update(salary));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseContract<String>(CommonConstant.RESPONSE_STATUS_FAILURE, e.getMessage(), null);
		}
	}

	@Override
	public ResponseContract<?> deleteBasicSalary(long id) {
		try {
			return new ResponseContract<Integer>(CommonConstant.RESPONSE_STATUS_SUCCESS, null,
					salaryRepository.delete(id));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseContract<String>(CommonConstant.RESPONSE_STATUS_FAILURE, e.getMessage(), null);
		}
	}

	@Override
	public ResponseContract<?> findBasicSalaryByDepartment(Long departmebtId) {
		try {
			return new ResponseContract<List<BasicSalary>>(CommonConstant.RESPONSE_STATUS_SUCCESS, null,
					salaryRepository.findDepartment(departmebtId));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseContract<String>(CommonConstant.RESPONSE_STATUS_FAILURE, e.getMessage(), null);
		}
	}

	@Override
	public ResponseContract<?> findBasicSalaryById(long id) {
		try {
			return new ResponseContract<BasicSalary>(CommonConstant.RESPONSE_STATUS_SUCCESS, null,
					salaryRepository.findById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseContract<String>(CommonConstant.RESPONSE_STATUS_FAILURE, e.getMessage(), null);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseContract<?> createBonusDefinition(BonusDefinition bonusDefine) {
		try {
			Long bonusId = bonusDefineRepository.create(bonusDefine);
			List<BonusThreshold> thresholds = new ArrayList<>();
			if (bonusDefine.getBonusThresholds() != null && bonusDefine.getBonusThresholds().size() > 0) {
				thresholds.addAll(bonusDefine.getBonusThresholds());
			}
			if (bonusDefine.getBonusReferences() != null && bonusDefine.getBonusReferences().size() > 0) {
				thresholds.addAll(bonusDefine.getBonusReferences());
			}
			if (thresholds.size() > 0) {
				bonusThresholdRepository.create(thresholds, bonusId);
			}
			return new ResponseContract<Integer>(CommonConstant.RESPONSE_STATUS_SUCCESS, null, 1);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseContract<String>(CommonConstant.RESPONSE_STATUS_FAILURE, e.getMessage(), null);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseContract<?> updateBonusDefinition(Long id, BonusDefinition bonusDefine) {
		try {
			bonusDefine.setBonusId(id);

			List<BonusThreshold> updateThresholds = new ArrayList<>(), createThresholds = new ArrayList<>();

			if (bonusDefine.getBonusThresholds() != null && bonusDefine.getBonusThresholds().size() > 0) {
				for (BonusThreshold bonusThreshold : bonusDefine.getBonusThresholds()) {
					if (bonusThreshold.getId() != null) {
						createThresholds.add(bonusThreshold);
					} else {
						updateThresholds.add(bonusThreshold);
					}
				}
			}
			if (bonusDefine.getBonusReferences() != null && bonusDefine.getBonusReferences().size() > 0) {
				for (BonusThreshold bonusThreshold : bonusDefine.getBonusReferences()) {
					if (bonusThreshold.getId() != null) {
						createThresholds.add(bonusThreshold);
					} else {
						updateThresholds.add(bonusThreshold);
					}
				}
			}

			bonusDefineRepository.update(bonusDefine);

			if (createThresholds.size() > 0) {
				bonusThresholdRepository.create(createThresholds, id);
			}
			if (updateThresholds.size() > 0) {
				bonusThresholdRepository.update(updateThresholds);
			}
			return new ResponseContract<Integer>(CommonConstant.RESPONSE_STATUS_SUCCESS, null, 1);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseContract<String>(CommonConstant.RESPONSE_STATUS_FAILURE, e.getMessage(), null);
		}
	}

	@Override
	public ResponseContract<?> deleteBonusDefinition(long bonusId) {
		try {
			bonusDefineRepository.delete(bonusId);
			bonusThresholdRepository.deleteByBonusId(bonusId);
			return new ResponseContract<Integer>(CommonConstant.RESPONSE_STATUS_SUCCESS, null, 1);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseContract<String>(CommonConstant.RESPONSE_STATUS_FAILURE, e.getMessage(), null);
		}
	}

	@Override
	public ResponseContract<?> findBonusDefinitionById(long bonusId) {
		try {
			BonusDefinition bonusDefinition = bonusDefineRepository.findById(bonusId);
			List<BonusThreshold> allThreBonusThresholds = bonusThresholdRepository.findByBonusId(bonusId);
			List<BonusThreshold> bonusThresholds = new ArrayList<>(), bonusReferences = new ArrayList<>();
			for (BonusThreshold bonusThreshold : allThreBonusThresholds) {
				if (bonusThreshold.getIsReference()) {
					bonusReferences.add(bonusThreshold);
				} else {
					bonusThresholds.add(bonusThreshold);
				}
			}
			bonusDefinition.setBonusReferences(bonusReferences);
			bonusDefinition.setBonusThresholds(bonusThresholds);
			return new ResponseContract<BonusDefinition>(CommonConstant.RESPONSE_STATUS_SUCCESS, null, bonusDefinition);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseContract<String>(CommonConstant.RESPONSE_STATUS_FAILURE, e.getMessage(), null);
		}
	}

	@Override
	public ResponseContract<?> createKpiDefinition(KpiDefinition kpi) {
		try {
			return new ResponseContract<Integer>(CommonConstant.RESPONSE_STATUS_SUCCESS, null,
					kpiRepository.create(kpi));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseContract<String>(CommonConstant.RESPONSE_STATUS_FAILURE, e.getMessage(), null);
		}
	}

	@Override
	public ResponseContract<?> updateKpiDefinition(KpiDefinition kpi) {
		try {
			return new ResponseContract<Integer>(CommonConstant.RESPONSE_STATUS_SUCCESS, null,
					kpiRepository.update(kpi));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseContract<String>(CommonConstant.RESPONSE_STATUS_FAILURE, e.getMessage(), null);
		}
	}

	@Override
	public ResponseContract<?> findKpiDefinitionById(long id) {
		try {
			return new ResponseContract<KpiDefinition>(CommonConstant.RESPONSE_STATUS_SUCCESS, null,
					kpiRepository.findById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseContract<String>(CommonConstant.RESPONSE_STATUS_FAILURE, e.getMessage(), null);
		}
	}

	@Override
	public ResponseContract<?> findKpiDefinitionByDepartment(Long departmentId) {
		try {
			return new ResponseContract<List<KpiDefinition>>(CommonConstant.RESPONSE_STATUS_SUCCESS, null,
					kpiRepository.getByDepartment(departmentId));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseContract<String>(CommonConstant.RESPONSE_STATUS_FAILURE, e.getMessage(), null);
		}
	}

	@Override
	public ResponseContract<?> deleteKpiDefinition(long id) {
		try {
			return new ResponseContract<Integer>(CommonConstant.RESPONSE_STATUS_SUCCESS, null,
					kpiRepository.delete(id));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseContract<String>(CommonConstant.RESPONSE_STATUS_FAILURE, e.getMessage(), null);
		}
	}

	@Override
	public ResponseContract<?> findBonusDefinitionByServiceType(String serviceType) {
		try {
			List<BonusDefinition> bonusDefinitions = bonusDefineRepository.findByServiceType(serviceType);
			if (bonusDefinitions == null || bonusDefinitions.size() == 0) {
				return new ResponseContract<ArrayList<BonusDefinition>>(CommonConstant.RESPONSE_STATUS_SUCCESS, null,
						new ArrayList<>());
			}
			List<Long> bonusIds = bonusDefinitions.stream().map(b -> b.getBonusId()).collect(Collectors.toList());
			List<BonusThreshold> thresholds = bonusThresholdRepository.findByBonusId(bonusIds);
			Map<Long, List<BonusThreshold>> mapDefineThresholds = new HashMap<>();

			for (BonusThreshold bonusThreshold : thresholds) {
				List<BonusThreshold> tmp = new ArrayList<>();
				if (mapDefineThresholds.containsKey(bonusThreshold.getBonusId())) {
					tmp = new ArrayList<>(mapDefineThresholds.get(bonusThreshold.getBonusId()));
				}
				tmp.add(bonusThreshold);
				mapDefineThresholds.put(bonusThreshold.getBonusId(), tmp);
			}

			Map<String, List<BonusDefinition>> mapEmployeeLvBonus = new HashMap<>();

			for (BonusDefinition bonusDefinition : bonusDefinitions) {
				List<BonusThreshold> allThreBonusThresholds = mapDefineThresholds.get(bonusDefinition.getBonusId());
				if (allThreBonusThresholds != null) {
					List<BonusThreshold> bonusThresholds = new ArrayList<>(), bonusReferences = new ArrayList<>();
					for (BonusThreshold bonusThreshold : allThreBonusThresholds) {
						if (bonusThreshold.getIsReference() != null && bonusThreshold.getIsReference()) {
							bonusReferences.add(bonusThreshold);
						} else {
							bonusThresholds.add(bonusThreshold);
						}
					}
					bonusDefinition.setBonusReferences(bonusReferences);
					bonusDefinition.setBonusThresholds(bonusThresholds);
				}
				if (bonusDefinition.getEmployeeLevel() == null) {
					bonusDefinition.setEmployeeLevel("General");
				}

				List<BonusDefinition> tmp = new ArrayList<>();
				if (mapEmployeeLvBonus.containsKey(bonusDefinition.getEmployeeLevel())) {
					tmp.addAll(mapEmployeeLvBonus.get(bonusDefinition.getEmployeeLevel()));
				}

				tmp.add(bonusDefinition);
				mapEmployeeLvBonus.put(bonusDefinition.getEmployeeLevel(), tmp);
			}

			return new ResponseContract<Map<String, List<BonusDefinition>>>(CommonConstant.RESPONSE_STATUS_SUCCESS,
					null, mapEmployeeLvBonus);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseContract<String>(CommonConstant.RESPONSE_STATUS_FAILURE, e.getMessage(), null);
		}
	}

	@Override
	public ResponseContract<?> deleteBonusThresholds(long id) {
		try {
			return new ResponseContract<Integer>(CommonConstant.RESPONSE_STATUS_SUCCESS, null,
					bonusThresholdRepository.delete(id));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseContract<String>(CommonConstant.RESPONSE_STATUS_FAILURE, e.getMessage(), null);
		}
	}

	@Override
	public ResponseContract<?> saveSchedules(ScheduleConfigContract schedule) {
		try {

			if (schedule.getPayrollDate() == null) {
				return new ResponseContract<String>(CommonConstant.RESPONSE_STATUS_FAILURE_PAYROLL_DATE_NULL,
						"Payroll date is required", null);
			}
			List<Integer> workingDays = new ArrayList<>();
			List<WorkingSchedule> workingSchedules = new ArrayList<>();
			for (WorkingDayContract workingDay : schedule.getWorkingSchedules()) {
				if (workingDays.contains(workingDay.getDayOfWeek())) {
					return new ResponseContract<String>(CommonConstant.RESPONSE_STATUS_FAILURE_SCHEDULE_OVERLAP_DAY,
							"Working days is overlap", null);
				}
				if (workingDay.getDayOfWeek() > 7 || workingDay.getDayOfWeek() < 1) {
					return new ResponseContract<String>(CommonConstant.RESPONSE_STATUS_FAILURE_SCHEDULE_INVALID_DAY,
							"Working days must be less than 8 and greater 0 (from Monday to Sunday)", null);
				}
				workingDays.add(workingDay.getDayOfWeek());
				Date startTime = null, endTime = null;
				try {
					startTime = DateTimeUtil.stringToTime(workingDay.getStartTime());
					endTime = DateTimeUtil.stringToTime(workingDay.getEndTime());
				} catch (Exception e) {
					return new ResponseContract<String>(
							CommonConstant.RESPONSE_STATUS_FAILURE_SCHEDULE_TIME_WRONG_FORMAT,
							"startTime/ endTime must be in format: hh:mm:ss", null);
				}
				if (startTime.after(endTime)) {
					return new ResponseContract<String>(
							CommonConstant.RESPONSE_STATUS_FAILURE_START_TIME_AFTER_END_TIME,
							"Start time must be less than end time", null);
				}
				WorkingSchedule workingSchedule = new WorkingSchedule();
				workingSchedule.setDayOfWeek(workingDay.getDayOfWeek());
				workingSchedule.setStartTime(workingDay.getStartTime());
				workingSchedule.setEndTime(workingDay.getEndTime());

				workingSchedules.add(workingSchedule);
			}

			String updateUser = CommonUtil.getUserName();

			workingScheduleRepository.saveSchedule(workingSchedules, updateUser);

			SystemParam payrollDate = new SystemParam();
			payrollDate.setKey(CommonConstant.PARAM_NAME_PAYROLL_DATE);
			payrollDate.setValue(schedule.getPayrollDate().toString());

			systemParamRepository.saveParam(payrollDate);

			return new ResponseContract<Integer>(CommonConstant.RESPONSE_STATUS_SUCCESS, null, 1);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseContract<String>(CommonConstant.RESPONSE_STATUS_FAILURE, e.getMessage(), null);
		}
	}

	@Override
	public ResponseContract<?> getSchedules() {
		try {
			ScheduleConfigContract result = new ScheduleConfigContract();
			String payrollDate = systemParamRepository.getParam(CommonConstant.PARAM_NAME_PAYROLL_DATE);
			if (payrollDate != null) {
				result.setPayrollDate(Integer.parseInt(payrollDate));
			}
			List<WorkingSchedule> workingSchedules = workingScheduleRepository.getSchedules();

			List<WorkingDayContract> workingDayContracts = new ArrayList<>();
			if (!CommonUtil.isNull(workingSchedules)) {
				for (WorkingSchedule workingDay : workingSchedules) {
					WorkingDayContract contract = new WorkingDayContract();
					contract.setDayOfWeek(workingDay.getDayOfWeek());
					contract.setStartTime(workingDay.getStartTime());
					contract.setEndTime(workingDay.getEndTime());
					workingDayContracts.add(contract);
				}
			}

			result.setWorkingSchedules(workingDayContracts);

			return new ResponseContract<ScheduleConfigContract>(CommonConstant.RESPONSE_STATUS_SUCCESS, null, result);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseContract<String>(CommonConstant.RESPONSE_STATUS_FAILURE, e.getMessage(), null);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseContract<?> createDepartment(Department department) {
		try {
			// create Department

			Long departmentId = departmentRepository.create(department);

			// create DepartmentService
			for (Long serviceId : department.getServiceIds()) {
				DepartmentService departmentService = new DepartmentService();
				departmentService.setServiceId(serviceId);
				departmentService.setDepartmentId(departmentId);
				departmentService.setDepartmentName(department.getDepartmentName());
				departmentService.setServiceType(department.getServiceType());
				departmentServiceRepository.create(departmentService);
			}
			return new ResponseContract<Long>(CommonConstant.RESPONSE_STATUS_SUCCESS, null, departmentId);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseContract<String>(CommonConstant.RESPONSE_STATUS_FAILURE, e.getMessage(), null);
		}

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseContract<?> updateDepartment(Department department) {
		try {
			// update Department
			int rowsUpdate = departmentRepository.update(department);
			departmentServiceRepository.deleteByDepartmentId(department.getId());
			// update DepartmentService
			for (Long serviceId : department.getServiceIds()) {
				DepartmentService departmentService = new DepartmentService();
				departmentService.setServiceId(serviceId);
				departmentService.setDepartmentId(department.getId());
				departmentService.setDepartmentName(department.getDepartmentName());
				departmentService.setServiceType(department.getServiceType());
				departmentServiceRepository.create(departmentService);
			}
			return new ResponseContract<Integer>(CommonConstant.RESPONSE_STATUS_SUCCESS, null, rowsUpdate);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseContract<String>(CommonConstant.RESPONSE_STATUS_FAILURE, e.getMessage(), null);
		}
	}

	@Override
	public ResponseContract<?> deleteDepartment(Long departmentId) {
		try {
			departmentServiceRepository.deleteByDepartmentId(departmentId);
			return new ResponseContract<Integer>(CommonConstant.RESPONSE_STATUS_SUCCESS, null,
					departmentRepository.delete(departmentId));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseContract<String>(CommonConstant.RESPONSE_STATUS_FAILURE, e.getMessage(), null);
		}
	}

	@Override
	public ResponseContract<?> getDepartment(Long departmentId) {
		try {
			return new ResponseContract<Department>(CommonConstant.RESPONSE_STATUS_SUCCESS, null,
					departmentRepository.get(departmentId));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseContract<String>(CommonConstant.RESPONSE_STATUS_FAILURE, e.getMessage(), null);
		}
	}

	@Override
	public ResponseContract<?> createService(Service service) {
		try {
			return new ResponseContract<Long>(CommonConstant.RESPONSE_STATUS_SUCCESS, null,
					serviceRepository.create(service));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseContract<String>(CommonConstant.RESPONSE_STATUS_FAILURE, e.getMessage(), null);
		}
	}

	@Override
	public ResponseContract<?> updateService(Service service) {
		try {
			return new ResponseContract<Integer>(CommonConstant.RESPONSE_STATUS_SUCCESS, null,
					serviceRepository.update(service));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseContract<String>(CommonConstant.RESPONSE_STATUS_FAILURE, e.getMessage(), null);
		}
	}

	@Override
	public ResponseContract<?> deleteService(Long serviceId) {
		try {
			departmentServiceRepository.deleteByServiceId(serviceId);
			return new ResponseContract<Integer>(CommonConstant.RESPONSE_STATUS_SUCCESS, null,
					serviceRepository.delete(serviceId));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseContract<String>(CommonConstant.RESPONSE_STATUS_FAILURE, e.getMessage(), null);
		}
	}

	@Override
	public ResponseContract<?> getService(Long serviceId) {
		try {
			return new ResponseContract<Service>(CommonConstant.RESPONSE_STATUS_SUCCESS, null,
					serviceRepository.get(serviceId));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseContract<String>(CommonConstant.RESPONSE_STATUS_FAILURE, e.getMessage(), null);
		}
	}

	@Override
	public ResponseContract<?> getServicetByServiceType(String serviceType) {
		try {
			return new ResponseContract<List<Service>>(CommonConstant.RESPONSE_STATUS_SUCCESS, null,
					serviceRepository.getByServiceType(serviceType));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseContract<String>(CommonConstant.RESPONSE_STATUS_FAILURE, e.getMessage(), null);
		}
	}

	@Override
	public ResponseContract<?> getAllServiceType() {
		try {
			return new ResponseContract<List<String>>(CommonConstant.RESPONSE_STATUS_SUCCESS, null,
					serviceRepository.getAllServiceType());

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new ResponseContract<String>(CommonConstant.RESPONSE_STATUS_FAILURE, e.getMessage(), null);
		}
	}

	@Override
	public ResponseContract<?> getAllService() {
		try {
			return new ResponseContract<List<Service>>(CommonConstant.RESPONSE_STATUS_SUCCESS, null,
					serviceRepository.getAllService());
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseContract<String>(CommonConstant.RESPONSE_STATUS_FAILURE, e.getMessage(), null);
		}
	}

	public ResponseContract<?> findBonusDefinitionByDepartmentId(Long departmentId) {
		try {
			Department department = departmentRepository.get(departmentId);
			if (department == null) {
				return new ResponseContract<String>(CommonConstant.RESPONSE_STATUS_FAILURE_DEPARTMENT_NOT_EXIT,
						"Department not exits", null);
			}
			List<String> serviceTypes = departmentRepository.getServices(departmentId);
			List<BonusDefinition> bonusDefinitions = bonusDefineRepository.findByDepartmentId(departmentId,
					serviceTypes);
			if (bonusDefinitions == null || bonusDefinitions.size() == 0) {
				return new ResponseContract<ArrayList<BonusDefinition>>(CommonConstant.RESPONSE_STATUS_SUCCESS, null,
						new ArrayList<>());
			}
			List<Long> bonusIds = bonusDefinitions.stream().map(b -> b.getBonusId()).collect(Collectors.toList());
			List<BonusThreshold> thresholds = bonusThresholdRepository.findByBonusId(bonusIds);
			Map<Long, List<BonusThreshold>> mapDefineThresholds = new HashMap<>();

			for (BonusThreshold bonusThreshold : thresholds) {
				List<BonusThreshold> tmp = new ArrayList<>();
				if (mapDefineThresholds.containsKey(bonusThreshold.getBonusId())) {
					tmp = new ArrayList<>(mapDefineThresholds.get(bonusThreshold.getBonusId()));
				}
				tmp.add(bonusThreshold);
				mapDefineThresholds.put(bonusThreshold.getBonusId(), tmp);
			}

			Map<String, List<BonusDefinition>> mapEmployeeLvBonus = new HashMap<>();

			for (BonusDefinition bonusDefinition : bonusDefinitions) {
				List<BonusThreshold> allThreBonusThresholds = mapDefineThresholds.get(bonusDefinition.getBonusId());
//				if (allThreBonusThresholds == null || allThreBonusThresholds.size() == 0) {
//					continue;
//				}
				if (allThreBonusThresholds != null) {
					List<BonusThreshold> bonusThresholds = new ArrayList<>(), bonusReferences = new ArrayList<>();
					for (BonusThreshold bonusThreshold : allThreBonusThresholds) {
						if (bonusThreshold.getIsReference() != null && bonusThreshold.getIsReference()) {
							bonusReferences.add(bonusThreshold);
						} else {
							bonusThresholds.add(bonusThreshold);
						}
					}
					bonusDefinition.setBonusReferences(bonusReferences);
					bonusDefinition.setBonusThresholds(bonusThresholds);

					if (bonusDefinition.getEmployeeLevel() == null) {
						bonusDefinition.setEmployeeLevel("General");
					}
				}

				List<BonusDefinition> tmp = new ArrayList<>();
				if (mapEmployeeLvBonus.containsKey(bonusDefinition.getEmployeeLevel())) {
					tmp.addAll(mapEmployeeLvBonus.get(bonusDefinition.getEmployeeLevel()));
				}

				tmp.add(bonusDefinition);
				mapEmployeeLvBonus.put(bonusDefinition.getEmployeeLevel(), tmp);
			}

			return new ResponseContract<Map<String, List<BonusDefinition>>>(CommonConstant.RESPONSE_STATUS_SUCCESS,
					null, mapEmployeeLvBonus);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseContract<String>(CommonConstant.RESPONSE_STATUS_FAILURE, e.getMessage(), null);
		}
	}
}
