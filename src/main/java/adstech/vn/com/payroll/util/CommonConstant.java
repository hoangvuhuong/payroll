package adstech.vn.com.payroll.util;

public class CommonConstant {
	public static final String RESPONSE_STATUS_SUCCESS = "success";
	public static final String RESPONSE_STATUS_FAILURE = "error";
	
	public static final String RESPONSE_STATUS_FAILURE_SCHEDULE_OVERLAP_DAY = "BadRequest.WorkingDayOverlap";
	public static final String RESPONSE_STATUS_FAILURE_SCHEDULE_INVALID_DAY = "BadRequest.WorkingDayInvalid";
	public static final String RESPONSE_STATUS_FAILURE_START_TIME_AFTER_END_TIME = "BadRequest.StartTimeAfterEndTime";
	public static final String RESPONSE_STATUS_FAILURE_PAYROLL_DATE_NULL = "BadRequest.PayrollDateNull";
	public static final String RESPONSE_STATUS_FAILURE_SCHEDULE_TIME_WRONG_FORMAT= "BadRequest.WrongTimeFormat";
	public static final String RESPONSE_STATUS_FAILURE_DEPARTMENT_NOT_EXIT= "BadRequest.DepartmentNotExit";
	
	public static final String PARAM_NAME_PAYROLL_DATE = "payroll.date";
	
	public static final String BONUS_METHOD_KPI_PERSONAL = "KPI_PERSONAL"; // KPI ca nhan
	public static final String BONUS_METHOD_KPI_PERSONAL_REFERENCE_BONUS_TABLE = "KPI_PERSONAL_REFERENCE_BONUS_TABLE"; // KPI ca nhan tham chieu bang tinh thuong
	public static final String BONUS_METHOD_KPI_MANAGER = "KPI_MANAGER"; // KPI quan ly
	public static final String BONUS_METHOD_KPI_MANAGER_REFERENCE_BONUS_TABLE = "KPI_MANAGER_REFERENCE_BONUS_TABLE"; // KPI ca nhan tham chieu bang tinh thuong
	public static final String BONUS_METHOD_REVENUE = "REVENUE"; // doanh so thu tien
	public static final String BONUS_METHOD_FINAL_REVENUE = "FINAL_REVENUE"; // doanh so tính thưởng
	public static final String BONUS_METHOD_RECEIVED_REVENUE = "RECEIVED_REVENUE"; // doanh so thuc hien
	public static final String BONUS_METHOD_AFTER_TAX_REVENUE = "AFTER_TAX_REVENUE"; // doanh so thuc hien
	public static final String BONUS_METHOD_CONTRACT_VALUE = "CONTRACT_VALUE"; // gia tri hop dong
	public static final String BONUS_METHOD_LAPSE_RATE = "LAPSE_RATE"; // gti le lapse
	public static final String BONUS_METHOD_QUIT_RATE = "QUIT_RATE"; // ti le quit
	public static final String BONUS_METHOD_EMPLOYEE_QUANTITY = "EMPLOYEE_QUANTITY"; // quan so
	
	public static final String BONUS_PERIOD_MONTH = "MONTH"; //Tháng
	public static final String BONUS_PERIOD_QUARTER = "QUARTER"; //Quý
	public static final String BONUS_PERIOD_YEAR = "YEAR"; //Tháng
	
	public static final String BONUS_FACTOR_REVENUE = "REVENUE"; //theo doanh so thu tien
	public static final String BONUS_FACTOR_AFTER_TAX_REVENUE = "AFTER_TAX_REVENUE"; //doanh so sau thue
	public static final String BONUS_FACTOR_RECEIVED_REVENUE = "RECEIVED_REVENUE"; //doanh so thuc hien
	public static final String BONUS_FACTOR_FINAL_REVENUE = "FINAL_REVENUE"; //doanh so tinh thuong
	
	public static final String EMPLOYEE_LEVEL_CTV = "CTV"; //Cong tac vien
	public static final String EMPLOYEE_LEVEL_TV = "NVTV"; //Nhan vien thu viec/sale chien
	public static final String EMPLOYEE_LEVEL_NVKD = "NVKD"; //Nhan vien kinh doanh 1
	public static final String EMPLOYEE_LEVEL_NVKD_D = "NVKD_D"; //Nhan vien kinh doanh 2/nhân viên kinh doanh 3-4 tháng
	public static final String EMPLOYEE_LEVEL_NVKD_C = "NVKD_C"; //Nhan vien kinh doanh 3/nhân viên kinh doanh 5-6 tháng
	public static final String EMPLOYEE_LEVEL_NVKD_B = "NVKD_B"; //Nhan vien kinh doanh 4/nhân viên kinh doanh 7-8 tháng
	public static final String EMPLOYEE_LEVEL_NVKD_A = "NVKD_A"; //Nhan vien kinh doanh 5/nhân viên kinh doanh 9-12 tháng
	public static final String EMPLOYEE_LEVEL_PRE_TN = "PRE_TN"; //Pre trưởng nhóm
	public static final String EMPLOYEE_LEVEL_TN_D = "TN_D"; //Trưởng nhóm D
	public static final String EMPLOYEE_LEVEL_TN_C = "TN_C"; //Trưởng nhóm C
	public static final String EMPLOYEE_LEVEL_TN_B = "TN_B"; //Trưởng nhóm B
	public static final String EMPLOYEE_LEVEL_TN_A = "TN_A"; //Trưởng nhóm A
	public static final String EMPLOYEE_LEVEL_PRE_TP = "PRE_TP"; //Pre trưởng phòng
	public static final String EMPLOYEE_LEVEL_TP_D = "TP_D"; //Trưởng phòng D
	public static final String EMPLOYEE_LEVEL_TP_C = "TP_C"; //Trưởng phòng C
	public static final String EMPLOYEE_LEVEL_TP_B = "TP_B"; //Trưởng phòng B
	public static final String EMPLOYEE_LEVEL_TP_A = "TP_A"; //Trưởng phòng A
	public static final String EMPLOYEE_LEVEL_TP_A_PLUS = "TP_A_PLUS"; //Trưởng phòng A+
	public static final String EMPLOYEE_LEVEL_KT_GAT = "KT_GAT"; // Nhan vien ki thuat quang cao
	public static final String EMPLOYEE_LEVEL_KT_AM = "KT_AM"; // Truong nhom ki thuat quang cao
	public static final String EMPLOYEE_LEVEL_TPKT = "TPKT"; // Truong phong ki thuat
	
}
