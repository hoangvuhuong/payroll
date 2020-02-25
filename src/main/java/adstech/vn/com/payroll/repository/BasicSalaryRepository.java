package adstech.vn.com.payroll.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import adstech.vn.com.payroll.model.BasicSalary;

@Repository
public class BasicSalaryRepository {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;
	public int create(BasicSalary kpi) {
		String sql = "INSERT INTO tbl_basic_salaries(department_id, employee_level, salary_coefficient, basic_salary, check_kpi, created_at, created_by) "
				+ " VALUES (:departmentId, :employeeLevel, :salaryCoefficient, :basicSalary, :checkKpi, CURRENT_TIMESTAMP, :createdBy);";
		BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(kpi);
		return jdbcTemplate.update(sql, source);
	}
	public int update(BasicSalary kpi) {
		String sql = "UPDATE tbl_basic_salaries SET department_id =:departmentId, employee_level =:employeeLevel,"
				+ " salary_coefficient =:salaryCoefficient, basic_salary =:basicSalary, check_kpi=:checkKpi,updated_at = CURRENT_TIMESTAMP,updated_by=:updatedBy WHERE id =:id;";
		BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(kpi);
		return jdbcTemplate.update(sql, source);
	}
	public int delete(long id) {
		String sql ="DELETE FROM tbl_basic_salaries WHERE id =:id;";
		Map<String , Object> maps = new HashMap<String, Object>();
		maps.put("id",id);
		return jdbcTemplate.update(sql, maps);
	}
	public BasicSalary findById(long id) {
		String sql = "SELECT * FROM tbl_basic_salaries WHERE id =:id";
		Map<String , Object> maps = new HashMap<String, Object>();
		maps.put("id",id);
		return jdbcTemplate.queryForObject(sql, maps, new BeanPropertyRowMapper<BasicSalary>(BasicSalary.class));
	}
	public List<BasicSalary> findDepartment(Long departmentId) {
		String sql = "SELECT * FROM tbl_basic_salaries WHERE department_id=:departmentId ;";
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("departmentId", departmentId);
		return jdbcTemplate.query(sql, maps, new BeanPropertyRowMapper<BasicSalary>(BasicSalary.class));
	}
	public BasicSalary findByEmployeeLevel(String employeeLevel, Long departmentId) {
		String sql = "SELECT * FROM tbl_basic_salaries WHERE employee_level =:employeeLevel AND department_id =:departmentId";
		Map<String , Object> maps = new HashMap<String, Object>();
		maps.put("employeeLevel", employeeLevel);
		maps.put("departmentId", departmentId);
		return jdbcTemplate.queryForObject(sql, maps, new BeanPropertyRowMapper<BasicSalary>(BasicSalary.class));
	}
}
