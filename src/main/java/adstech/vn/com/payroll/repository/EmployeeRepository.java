package adstech.vn.com.payroll.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import adstech.vn.com.payroll.model.Employee;
import adstech.vn.com.payroll.util.CommonUtil;

@Repository
public class EmployeeRepository {

	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;
	
	public Employee get(Long userId) {
		String sql = "SELECT * FROM tbl_employees WHERE user_id = :userId";
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		
		List<Employee> employees = jdbcTemplate.query(sql, map, BeanPropertyRowMapper.newInstance(Employee.class)); 
		
		if(!CommonUtil.isNull(employees)) {
			return employees.get(0);
		}
		
		return null;
	}
}
