package adstech.vn.com.payroll.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import adstech.vn.com.payroll.model.Department;
import adstech.vn.com.payroll.util.CommonUtil;

@Repository
public class DepartmentRepository {

	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	public Long create(Department department) {
		String sql = "INSERT INTO tbl_departments(department_name, service_type, description, status, created_at,created_by)"
				+ "VALUES(:departmentName, :serviceType, :description, :status, CURRENT_TIMESTAMP, :createdBy);";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(department);
		jdbcTemplate.update(sql, source, keyHolder);
		return keyHolder.getKey().longValue();

	}

	public int update(Department department) {
		String sql = "UPDATE tbl_departments SET department_name =:departmentName, service_type =:serviceType, description =:description, status=:status, "
				+ "updated_at =:updatedAt,updated_by =:updatedBy WHERE id =:id";
		BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(department);
		return jdbcTemplate.update(sql, source);
	}

	public int delete(Long id) {
		String sql = "DELETE FROM tbl_departments WHERE id =:id";
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("id", id);
		return jdbcTemplate.update(sql, maps);
	}

	public Department get(Long departmentId) {
		String sql = "SELECT * FROM tbl_departments WHERE id = :departmentId";
		Map<String, Object> map = new HashMap<>();
		map.put("departmentId", departmentId);

		List<Department> departments = jdbcTemplate.query(sql, map,
				BeanPropertyRowMapper.newInstance(Department.class));

		if (!CommonUtil.isNull(departments)) {
			return departments.get(0);
		}

		return null;
	}
	
	public List<Department> get(List<Long> departmentIds) {
		String sql = "SELECT * FROM tbl_departments WHERE id IN (:departmentIds)";
		Map<String, Object> map = new HashMap<>();
		map.put("departmentIds", departmentIds);
		
		return jdbcTemplate.query(sql, map, BeanPropertyRowMapper.newInstance(Department.class)); 
	}
	
	public List<String> getServices(Long departmentId){
		String sql = "SELECT service_type FROM tbl_department_services WHERE department_id = :departmentId";
		Map<String, Object> map = new HashMap<>();
		map.put("departmentId", departmentId);

		return jdbcTemplate.queryForList(sql, map, String.class);
	}

}
