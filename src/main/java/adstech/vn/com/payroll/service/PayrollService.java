package adstech.vn.com.payroll.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import adstech.vn.com.payroll.contract.BonusIncomeContract;
import adstech.vn.com.payroll.contract.Contract;
import adstech.vn.com.payroll.contract.ErpContract;
import adstech.vn.com.payroll.contract.PersonalPayrollContract;
import adstech.vn.com.payroll.contract.ResponseContract;
import adstech.vn.com.payroll.contract.SummaryRevenueContract;
import adstech.vn.com.payroll.model.BasicSalary;
import adstech.vn.com.payroll.model.BonusDefinition;
import adstech.vn.com.payroll.model.BonusThreshold;
import adstech.vn.com.payroll.model.Department;
import adstech.vn.com.payroll.model.Employee;
import adstech.vn.com.payroll.model.KpiDefinition;
import adstech.vn.com.payroll.model.WorkingSchedule;
import adstech.vn.com.payroll.repository.BasicSalaryRepository;
import adstech.vn.com.payroll.repository.BonusDefinitionRepository;
import adstech.vn.com.payroll.repository.BonusThresholdRepository;
import adstech.vn.com.payroll.repository.DepartmentRepository;
import adstech.vn.com.payroll.repository.EmployeeRepository;
import adstech.vn.com.payroll.repository.KpiDefinitionRepository;
import adstech.vn.com.payroll.repository.WorkingScheduleRepository;
import adstech.vn.com.payroll.security.UserPrincipal;
import adstech.vn.com.payroll.util.AuthenUtil;
import adstech.vn.com.payroll.util.CommonConstant;
import adstech.vn.com.payroll.util.CommonUtil;
import adstech.vn.com.payroll.util.DateTimeUtil;
import adstech.vn.com.payroll.util.RestUtil;

@Service
public class PayrollService implements IPayrollService {

	@Autowired
	BonusDefinitionRepository bonusDefinitionRepository;

	@Autowired
	BonusThresholdRepository bonusThresholdRepository;

	@Autowired
	BasicSalaryRepository basicSalaryRepository;

	@Autowired
	WorkingScheduleRepository workingScheduleRepository;

	@Autowired
	KpiDefinitionRepository kpiDefinitionRepository;

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	DepartmentRepository departmentRepository;

	@Value("${link.erp}")
	String erpUrl;

	@Override
	public ResponseContract<?> getCurrentIncome(Long userId) {
		try {
			PersonalPayrollContract result = new PersonalPayrollContract();
			result.setUserId(userId);

			String url = erpUrl + "/users/" + userId + "/contracts/receipts/paymented";

			UserPrincipal principal = AuthenUtil.getPrincipal();
			Map<String, String> header = new HashMap<>();
			header.put("Authorization", "Bearer " + principal.getToken());

			JsonNode erpContractResponse = RestUtil.getRequest(url, header, new HashMap<>());
			if (erpContractResponse == null || erpContractResponse.get("status").asInt() != 200) {
				return new ResponseContract<PersonalPayrollContract>(CommonConstant.RESPONSE_STATUS_SUCCESS, null,
						result);
			}

			ObjectReader reader = new ObjectMapper().readerFor(new TypeReference<List<ErpContract>>() {
			});

			List<ErpContract> erpContracts = reader.readValue(erpContractResponse.get("data"));

			if (erpContracts == null || erpContracts.size() == 0) {
				return new ResponseContract<PersonalPayrollContract>(CommonConstant.RESPONSE_STATUS_SUCCESS, null,
						result);
			}

			List<Contract> contracts = new ArrayList<>();
			for (ErpContract erpContract : erpContracts) {
				Contract contract = new Contract();
				contract.setContractName(erpContract.getContractCode());
				contract.setFinalRevenue(erpContract.getRewardedRevenue());
				contract.setRevenue(erpContract.getAmount());
				contract.setRevenueAfterTax(erpContract.getAmountNotVat());
				contract.setReceivedRevenue(erpContract.getAmountNotVat());
				contract.setServiceType(erpContract.getTemType());
				contract.setSignDate(erpContract.getContractBegin());
				contracts.add(contract);
			}

			result.setContracts(contracts);
			
			Employee employee = employeeRepository.get(userId);

			if (employee == null) {
				return new ResponseContract<PersonalPayrollContract>(CommonConstant.RESPONSE_STATUS_SUCCESS, null,
						result);
			}

			Department department = departmentRepository.get(employee.getDepartmentId());

			String employeeLevel = employee.getEmployeeLevel();

			List<String> serviceTypes = departmentRepository.getServices(department.getId());

			BigDecimal basicSalary = null;
			if (employee.getBasicSalary() == null) {
				BasicSalary salary = basicSalaryRepository.findByEmployeeLevel(employeeLevel, department.getId());
				basicSalary = salary.getBasicSalary();
			} else {
				basicSalary = employee.getBasicSalary();
			}

			List<WorkingSchedule> schedules = workingScheduleRepository.getSchedules();
			List<Integer> workingDays = schedules.stream().map(w -> w.getDayOfWeek()).collect(Collectors.toList());

			int currentWorkingday = DateTimeUtil.countWorkingDay(workingDays);
			int workingDayOfMonth = DateTimeUtil.countWorkingDayOfMonth(workingDays);
			result.setNumOfWorkday(currentWorkingday);
			BigDecimal basicSalaryIncome = basicSalary
					.multiply(new BigDecimal((float) currentWorkingday / workingDayOfMonth));
			result.setBasicSalaryIncome(basicSalaryIncome);

			List<BonusDefinition> bonusDefinitions = bonusDefinitionRepository.findByEmployeeLevel(employeeLevel);

			List<Long> bonusIds = bonusDefinitions.stream().map(b -> b.getBonusId()).collect(Collectors.toList());

			List<BonusThreshold> thresholds = bonusThresholdRepository.findByBonusId(bonusIds);
			Map<Long, List<BonusThreshold>> mapDefineThresholds = new HashMap<>();
			Map<Long, List<BonusThreshold>> mapDefineReferences = new HashMap<>();

			for (BonusThreshold bonusThreshold : thresholds) {
				if (bonusThreshold.getIsReference() != null && !bonusThreshold.getIsReference()) {
					List<BonusThreshold> tmp = new ArrayList<>();
					if (mapDefineThresholds.containsKey(bonusThreshold.getBonusId())) {
						tmp = new ArrayList<>(mapDefineThresholds.get(bonusThreshold.getBonusId()));
					}
					tmp.add(bonusThreshold);
					mapDefineThresholds.put(bonusThreshold.getBonusId(), tmp);
				} else {
					List<BonusThreshold> tmp = new ArrayList<>();
					if (mapDefineReferences.containsKey(bonusThreshold.getBonusId())) {
						tmp = new ArrayList<>(mapDefineReferences.get(bonusThreshold.getBonusId()));
					}
					tmp.add(bonusThreshold);
					mapDefineReferences.put(bonusThreshold.getBonusId(), tmp);
				}
			}

			BigDecimal totalRevenue = BigDecimal.ZERO, totalBonusRevenue = BigDecimal.ZERO,
					totalAfterTaxRevenue = BigDecimal.ZERO, totalReceivedRevenue = BigDecimal.ZERO;
			int numOfContract = 0;
			Map<String, SummaryRevenueContract> mapServiceRevenues = new HashMap<>();
			for (Contract contract : contracts) {
				if (CommonUtil.isNull(serviceTypes) || serviceTypes.contains(contract.getServiceType())) {
					numOfContract++;
					if (contract.getRevenue() != null) {
						totalRevenue = totalRevenue.add(contract.getRevenue());
					}
					if (contract.getFinalRevenue() != null) {
						totalBonusRevenue = totalBonusRevenue.add(contract.getFinalRevenue());
					}
					if (contract.getReceivedRevenue() != null) {
						totalReceivedRevenue = totalReceivedRevenue.add(contract.getReceivedRevenue());
					}
					if (contract.getRevenueAfterTax() != null) {
						totalAfterTaxRevenue = totalAfterTaxRevenue.add(contract.getRevenueAfterTax());
					}
				}

				SummaryRevenueContract summaryRevenueContract = new SummaryRevenueContract();
				if (mapServiceRevenues.containsKey(contract.getServiceType())) {
					summaryRevenueContract = mapServiceRevenues.get(contract.getServiceType());
				}
				if (contract.getRevenue() != null) {
					summaryRevenueContract.setRevenue(summaryRevenueContract.getRevenue().add(contract.getRevenue()));
				}
				if (contract.getFinalRevenue() != null) {
					summaryRevenueContract
							.setFinalRevenue(summaryRevenueContract.getFinalRevenue().add(contract.getFinalRevenue()));
				}
				if (contract.getReceivedRevenue() != null) {
					summaryRevenueContract.setReceivedRevenue(
							summaryRevenueContract.getReceivedRevenue().add(contract.getReceivedRevenue()));
				}
				if (contract.getRevenueAfterTax() != null) {
					summaryRevenueContract.setAfterTaxRevenue(
							summaryRevenueContract.getAfterTaxRevenue().add(contract.getRevenueAfterTax()));
				}
				summaryRevenueContract.setNumOfContract(summaryRevenueContract.getNumOfContract() + 1);
				mapServiceRevenues.put(contract.getServiceType(), summaryRevenueContract);
			}

			// thuong theo so luong hop dong
			Map<String, BonusDefinition> mapServiceContractBonus = new HashMap<>();
			BonusDefinition kpiPersonalBonus = null;
			BonusDefinition kpiManagerBonus = null;
			BonusDefinition kpiManagerBonusReferBonusTable = null;
			BonusDefinition kpiPersonalBonusReferBonusTable = null;
			List<BonusDefinition> revenueBonus = new ArrayList<>();
			List<BonusDefinition> finalRevenueBonus = new ArrayList<>();
			List<BonusDefinition> afterTaxRevenueBonus = new ArrayList<>();
			List<BonusDefinition> receivedRevenueBonus = new ArrayList<>();
			BonusDefinition lapseRateBonus = null;
			BonusDefinition quitRateBonus = null;
			List<BonusDefinition> emloyeeQuantityBonus = new ArrayList<>();

			for (BonusDefinition bonusDefinition : bonusDefinitions) {
				bonusDefinition.setBonusReferences(mapDefineReferences.get(bonusDefinition.getBonusId()));
				bonusDefinition.setBonusThresholds(mapDefineThresholds.get(bonusDefinition.getBonusId()));
				switch (bonusDefinition.getBonusMethod()) {
				case CommonConstant.BONUS_METHOD_CONTRACT_VALUE:
					mapServiceContractBonus.put(bonusDefinition.getServiceType(), bonusDefinition);
					break;
				case CommonConstant.BONUS_METHOD_AFTER_TAX_REVENUE:
					afterTaxRevenueBonus.add(bonusDefinition);
					break;
				case CommonConstant.BONUS_METHOD_EMPLOYEE_QUANTITY:
					emloyeeQuantityBonus.add(bonusDefinition);
					break;
				case CommonConstant.BONUS_METHOD_FINAL_REVENUE:
					finalRevenueBonus.add(bonusDefinition);
					break;
				case CommonConstant.BONUS_METHOD_KPI_MANAGER:
					if (bonusDefinition.getDepartmentId().equals(employee.getDepartmentId())) {
						kpiManagerBonus = bonusDefinition;
					}
					break;
				case CommonConstant.BONUS_METHOD_KPI_PERSONAL:
					if (bonusDefinition.getDepartmentId().equals(employee.getDepartmentId())) {
						kpiPersonalBonus = bonusDefinition;
					}
					break;
				case CommonConstant.BONUS_METHOD_KPI_MANAGER_REFERENCE_BONUS_TABLE:
					if (bonusDefinition.getDepartmentId().equals(employee.getDepartmentId())) {
						kpiManagerBonusReferBonusTable = bonusDefinition;
					}
					break;
				case CommonConstant.BONUS_METHOD_KPI_PERSONAL_REFERENCE_BONUS_TABLE:
					if (bonusDefinition.getDepartmentId().equals(employee.getDepartmentId())) {
						kpiPersonalBonusReferBonusTable = bonusDefinition;
					}
					break;
				case CommonConstant.BONUS_METHOD_LAPSE_RATE:
					lapseRateBonus = bonusDefinition;
					break;
				case CommonConstant.BONUS_METHOD_QUIT_RATE:
					quitRateBonus = bonusDefinition;
					break;
				case CommonConstant.BONUS_METHOD_RECEIVED_REVENUE:
					receivedRevenueBonus.add(bonusDefinition);
					break;
				case CommonConstant.BONUS_METHOD_REVENUE:
					revenueBonus.add(bonusDefinition);
					break;
				default:
					break;
				}
			}

			List<BonusIncomeContract> bonusIncomes = new ArrayList<>();
			if (!mapServiceContractBonus.isEmpty()) {
				bonusIncomes.addAll(calculateContractBonus(serviceTypes, contracts, mapServiceContractBonus));
			}
			if (kpiPersonalBonus != null) {
				BonusIncomeContract kpiPersonalBonusIncome = calculateKpiBonus(department.getDepartmentName(),
						kpiPersonalBonus, employeeLevel, userId, department.getId(), totalRevenue, totalAfterTaxRevenue,
						totalReceivedRevenue, totalBonusRevenue, numOfContract);
				bonusIncomes.add(kpiPersonalBonusIncome);
			}
			if (kpiManagerBonus != null) {
				BonusIncomeContract kpiManagerBonusIncome = calculateKpiBonus(department.getDepartmentName(),
						kpiManagerBonus, employeeLevel, userId, department.getId(), totalRevenue, totalAfterTaxRevenue,
						totalReceivedRevenue, totalBonusRevenue, numOfContract);
				bonusIncomes.add(kpiManagerBonusIncome);
			}
			if (kpiPersonalBonusReferBonusTable != null) {
				// GET all contract of direct subordinates
				BonusIncomeContract kpiPersonalBonusIncome = calculateKpiBonus(department.getDepartmentName(),
						kpiPersonalBonusReferBonusTable, employeeLevel, userId, department.getId(), totalRevenue,
						totalAfterTaxRevenue, totalReceivedRevenue, totalBonusRevenue, numOfContract);
				bonusIncomes.add(kpiPersonalBonusIncome);
			}
			if (kpiManagerBonusReferBonusTable != null) {
				// GET all contract of direct subordinates
				BonusIncomeContract kpiManagerBonusIncome = calculateKpiBonus(department.getDepartmentName(),
						kpiManagerBonusReferBonusTable, employeeLevel, userId, department.getId(), totalRevenue,
						totalAfterTaxRevenue, totalReceivedRevenue, totalBonusRevenue, numOfContract);
				bonusIncomes.add(kpiManagerBonusIncome);
			}

			if (!CommonUtil.isNull(afterTaxRevenueBonus)) {
				bonusIncomes.addAll(calculateRevenueBonus(serviceTypes, afterTaxRevenueBonus, mapServiceRevenues,
						totalRevenue, totalAfterTaxRevenue, totalReceivedRevenue, totalBonusRevenue, numOfContract,
						department.getDepartmentName()));
			}

			if (!CommonUtil.isNull(finalRevenueBonus)) {
				bonusIncomes.addAll(calculateRevenueBonus(serviceTypes, finalRevenueBonus, mapServiceRevenues,
						totalRevenue, totalAfterTaxRevenue, totalReceivedRevenue, totalBonusRevenue, numOfContract,
						department.getDepartmentName()));
			}

			if (!CommonUtil.isNull(receivedRevenueBonus)) {
				bonusIncomes.addAll(calculateRevenueBonus(serviceTypes, receivedRevenueBonus, mapServiceRevenues,
						totalRevenue, totalAfterTaxRevenue, totalReceivedRevenue, totalBonusRevenue, numOfContract,
						department.getDepartmentName()));
			}

			if (!CommonUtil.isNull(revenueBonus)) {
				bonusIncomes.addAll(calculateRevenueBonus(serviceTypes, revenueBonus, mapServiceRevenues, totalRevenue,
						totalAfterTaxRevenue, totalReceivedRevenue, totalBonusRevenue, numOfContract,
						department.getDepartmentName()));
			}

			result.setBonusIncomes(bonusIncomes);
			BigDecimal grossIncome = basicSalaryIncome, netIncome = basicSalaryIncome;
			for (BonusIncomeContract bonusIncomeContract : bonusIncomes) {
				grossIncome = grossIncome.add(bonusIncomeContract.getBonusIncome());
				netIncome = netIncome.add(bonusIncomeContract.getBonusIncome().multiply(new BigDecimal(0.9)));
			}
			result.setGrossIncome(grossIncome);
			result.setNetIncome(netIncome);
			return new ResponseContract<PersonalPayrollContract>(CommonConstant.RESPONSE_STATUS_SUCCESS, null, result);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseContract<String>(CommonConstant.RESPONSE_STATUS_FAILURE, e.getMessage(), null);
		}
	}

	private List<BonusIncomeContract> calculateContractBonus(List<String> serviceTypes, List<Contract> contracts,
			Map<String, BonusDefinition> mapServicecontractBonus) {
		Map<String, BonusIncomeContract> mapServiceContractBonus = new HashMap<>();
		try {
			for (Contract contract : contracts) {
				BonusDefinition bonusDefinition = mapServicecontractBonus.get(contract.getServiceType());
				if (bonusDefinition == null) {
					continue;
				}
				if (!serviceTypes.contains(contract.getServiceType()) && !bonusDefinition.getIsExternal()
						|| serviceTypes.contains(contract.getServiceType()) && !bonusDefinition.getIsInternal()) {
					continue;
				}

				BigDecimal bonusValue = null, bonusPercent = null;
				for (BonusThreshold bonusThreshold : bonusDefinition.getBonusThresholds()) {
					if ((bonusThreshold.getLowerBound() == null
							|| contract.getRevenue().compareTo(bonusThreshold.getLowerBound()) >= 0)
							&& (bonusThreshold.getUpperBound() == null
									|| contract.getRevenue().compareTo(bonusThreshold.getUpperBound()) < 0)) {
						bonusValue = bonusThreshold.getBonusValue();
						bonusPercent = bonusThreshold.getBonusPercent();
						break;
					}
				}

				BonusIncomeContract temp = new BonusIncomeContract();
				if (mapServiceContractBonus.containsKey(contract.getServiceType())) {
					temp = mapServiceContractBonus.get(contract.getServiceType());
				} else {
					temp.setServiceType(contract.getServiceType());
					temp.setNumOfContract(0);
					temp.setBonusIncome(BigDecimal.ZERO);
					temp.setBonusMethod(bonusDefinition.getBonusMethod());
				}
				if (bonusValue != null) {
					temp.setBonusIncome(temp.getBonusIncome().add(bonusValue));
				}
				temp.setNumOfContract(temp.getNumOfContract() + 1);
				if (bonusPercent != null) {
					switch (bonusDefinition.getBonusFactor()) {
					case CommonConstant.BONUS_FACTOR_AFTER_TAX_REVENUE:
						temp.setBonusIncome(temp.getBonusIncome().add(contract.getRevenueAfterTax()
								.multiply(bonusPercent).divide(new BigDecimal(100), 0, RoundingMode.HALF_UP)));
						break;
					case CommonConstant.BONUS_FACTOR_FINAL_REVENUE:
						temp.setBonusIncome(temp.getBonusIncome().add(contract.getFinalRevenue().multiply(bonusPercent)
								.divide(new BigDecimal(100), 0, RoundingMode.HALF_UP)));
						break;
					case CommonConstant.BONUS_FACTOR_RECEIVED_REVENUE:
						temp.setBonusIncome(temp.getBonusIncome().add(contract.getReceivedRevenue()
								.multiply(bonusPercent).divide(new BigDecimal(100), 0, RoundingMode.HALF_UP)));
						break;
					case CommonConstant.BONUS_FACTOR_REVENUE:
						temp.setBonusIncome(temp.getBonusIncome().add(contract.getRevenue().multiply(bonusPercent)
								.divide(new BigDecimal(100), 0, RoundingMode.HALF_UP)));
						break;

					default:
						break;
					}
				}

				mapServiceContractBonus.put(contract.getServiceType(), temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<BonusIncomeContract> results = new ArrayList<>();

		for (Entry<String, BonusIncomeContract> entry : mapServiceContractBonus.entrySet()) {
			results.add(entry.getValue());
		}

		return results;
	}

	private BonusIncomeContract calculateKpiBonus(String departmentName, BonusDefinition definition,
			String employeeLevel, Long userId, Long departmentId, BigDecimal revenue, BigDecimal afterTaxRevenue,
			BigDecimal receivedRevenue, BigDecimal finalRevenue, int numOfContract) {
		BonusIncomeContract response = null;
		BigDecimal result = BigDecimal.ZERO;
		try {
			BigDecimal kpiResult, bonusRevenue;
			KpiDefinition kpiDefinition = kpiDefinitionRepository.getByUser(userId);
			if (kpiDefinition == null) {
				kpiDefinition = kpiDefinitionRepository.getByDepartment(departmentId, employeeLevel);
			}
			if (kpiDefinition == null) {
				return response;
			}

			switch (kpiDefinition.getTargetType()) {
			case CommonConstant.BONUS_FACTOR_AFTER_TAX_REVENUE:
				kpiResult = afterTaxRevenue;
				break;
			case CommonConstant.BONUS_FACTOR_REVENUE:
				kpiResult = revenue;
				break;
			case CommonConstant.BONUS_FACTOR_RECEIVED_REVENUE:
				kpiResult = receivedRevenue;
				break;
			default:
				kpiResult = finalRevenue;
				break;
			}

			switch (definition.getBonusFactor()) {
			case CommonConstant.BONUS_FACTOR_AFTER_TAX_REVENUE:
				bonusRevenue = afterTaxRevenue;
				break;
			case CommonConstant.BONUS_FACTOR_REVENUE:
				bonusRevenue = revenue;
				break;
			case CommonConstant.BONUS_FACTOR_RECEIVED_REVENUE:
				bonusRevenue = receivedRevenue;
				break;
			default:
				bonusRevenue = finalRevenue;
				break;
			}

			BigDecimal bonusValue = null, bonusPercent = null;
			BigDecimal percent = kpiResult.divide(kpiDefinition.getTarget(), 4, RoundingMode.HALF_UP)
					.multiply(new BigDecimal(100));
			for (BonusThreshold bonusThreshold : definition.getBonusThresholds()) {
				if ((bonusThreshold.getLowerBound() == null || percent.compareTo(bonusThreshold.getLowerBound()) >= 0)
						&& (bonusThreshold.getUpperBound() == null
								|| percent.compareTo(bonusThreshold.getUpperBound()) < 0)) {
					bonusValue = bonusThreshold.getBonusValue();
					bonusPercent = bonusThreshold.getBonusPercent();
					break;
				}
			}

			if (definition.getBonusMethod().equals(CommonConstant.BONUS_METHOD_KPI_MANAGER_REFERENCE_BONUS_TABLE)
					|| definition.getBonusMethod()
							.equals(CommonConstant.BONUS_METHOD_KPI_PERSONAL_REFERENCE_BONUS_TABLE)) {
				int decrementLevel = bonusValue.intValue();
				int refrenceLevel = 0;
				for (BonusThreshold bonusReference : definition.getBonusReferences()) {
					if ((bonusReference.getLowerBound() == null
							|| bonusRevenue.compareTo(bonusReference.getLowerBound()) >= 0)
							&& (bonusReference.getUpperBound() == null
									|| bonusRevenue.compareTo(bonusReference.getUpperBound()) < 0)) {
						refrenceLevel = bonusReference.getBonusLevel();
						break;
					}
				}
				int bonusLevel = refrenceLevel - decrementLevel;
				if (bonusLevel <= 0) {
					return response;
				}
				BonusThreshold bonusReference = definition.getBonusReferences().stream()
						.filter(r -> r.getBonusLevel().equals(bonusLevel)).findFirst().get();

				bonusPercent = bonusReference.getBonusPercent();
				bonusValue = bonusReference.getBonusValue();
			}

			if (bonusPercent != null) {
				result = result
						.add(bonusPercent.multiply(bonusRevenue).divide(new BigDecimal(100), 0, RoundingMode.HALF_UP));
			}
			if (bonusValue != null) {
				result = result.add(bonusValue);
			}
			if (result.compareTo(BigDecimal.ZERO) > 0) {
				response = new BonusIncomeContract();
				response.setBonusMethod(definition.getBonusMethod());
				response.setBonusFactorValue(bonusRevenue);
				response.setBonusIncome(result);
				response.setServiceType(departmentName);
				response.setNumOfContract(numOfContract);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	private List<BonusIncomeContract> calculateRevenueBonus(List<String> services, List<BonusDefinition> definitions,
			Map<String, SummaryRevenueContract> mapServiceRevenues, BigDecimal revenue, BigDecimal afterTaxRevenue,
			BigDecimal receivedRevenue, BigDecimal finalRevenue, int numOfContract, String departmentName) {
		List<BonusIncomeContract> resutls = new ArrayList<>();
		try {
			SummaryRevenueContract totalSummaryRevenueContract = new SummaryRevenueContract();
			totalSummaryRevenueContract.setAfterTaxRevenue(afterTaxRevenue);
			totalSummaryRevenueContract.setFinalRevenue(finalRevenue);
			totalSummaryRevenueContract.setNumOfContract(numOfContract);
			totalSummaryRevenueContract.setReceivedRevenue(receivedRevenue);
			totalSummaryRevenueContract.setRevenue(revenue);

			for (BonusDefinition bonusDefinition : definitions) {

				if (bonusDefinition.getDepartmentId() == null && (!services.contains(bonusDefinition.getServiceType())
						&& !bonusDefinition.getIsExternal()
						|| services.contains(bonusDefinition.getServiceType()) && !bonusDefinition.getIsInternal())) {
					continue;
				}

				SummaryRevenueContract summaryRevenueContract = bonusDefinition.getServiceType() != null
						? mapServiceRevenues.get(bonusDefinition.getServiceType())
						: totalSummaryRevenueContract;
				BigDecimal bonusMethodResult = BigDecimal.ZERO, bonusFactorResult = BigDecimal.ZERO;
				switch (bonusDefinition.getBonusMethod()) {
				case CommonConstant.BONUS_METHOD_AFTER_TAX_REVENUE:
					bonusMethodResult = summaryRevenueContract.getAfterTaxRevenue();
					break;
				case CommonConstant.BONUS_METHOD_FINAL_REVENUE:
					bonusMethodResult = summaryRevenueContract.getFinalRevenue();
					break;
				case CommonConstant.BONUS_METHOD_RECEIVED_REVENUE:
					bonusMethodResult = summaryRevenueContract.getReceivedRevenue();
					break;
				default:
					bonusMethodResult = summaryRevenueContract.getRevenue();
					break;
				}

				switch (bonusDefinition.getBonusFactor()) {
				case CommonConstant.BONUS_FACTOR_AFTER_TAX_REVENUE:
					bonusFactorResult = summaryRevenueContract.getAfterTaxRevenue();
					break;
				case CommonConstant.BONUS_FACTOR_REVENUE:
					bonusFactorResult = summaryRevenueContract.getRevenue();
					break;
				case CommonConstant.BONUS_FACTOR_RECEIVED_REVENUE:
					bonusFactorResult = summaryRevenueContract.getReceivedRevenue();
					break;
				default:
					bonusFactorResult = summaryRevenueContract.getFinalRevenue();
					break;
				}

				BigDecimal bonusValue = null, bonusPercent = null;
				for (BonusThreshold bonusThreshold : bonusDefinition.getBonusThresholds()) {
					if ((bonusThreshold.getLowerBound() == null
							|| bonusMethodResult.compareTo(bonusThreshold.getLowerBound()) >= 0)
							&& (bonusThreshold.getUpperBound() == null
									|| bonusMethodResult.compareTo(bonusThreshold.getUpperBound()) < 0)) {
						bonusValue = bonusThreshold.getBonusValue();
						bonusPercent = bonusThreshold.getBonusPercent();
						break;
					}
				}

				BigDecimal bonusResult = BigDecimal.ZERO;
				if (bonusValue != null) {
					bonusResult = bonusResult.add(bonusValue);
				}
				if (bonusPercent != null) {
					bonusResult = bonusResult.add(bonusPercent.multiply(bonusFactorResult).divide(new BigDecimal(100),
							0, RoundingMode.HALF_UP));
				}
				if (bonusResult.compareTo(BigDecimal.ZERO) > 0) {
					BonusIncomeContract bonusIncomeContract = new BonusIncomeContract();
					if (bonusDefinition.getServiceType() != null) {
						bonusIncomeContract.setServiceType(bonusDefinition.getServiceType());
					} else {
						bonusIncomeContract.setDepartmentName(departmentName);
					}
					bonusIncomeContract.setBonusFactorValue(bonusFactorResult);
					bonusIncomeContract.setBonusMethod(bonusDefinition.getBonusMethod());
					bonusIncomeContract.setBonusIncome(bonusResult);
					bonusIncomeContract.setNumOfContract(summaryRevenueContract.getNumOfContract());

					resutls.add(bonusIncomeContract);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return resutls;
	}

}
