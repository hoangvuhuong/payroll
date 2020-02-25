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

import adstech.vn.com.payroll.model.DepartmentService;

@Repository
public class DepartmentServiceRepository {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	public Long create(DepartmentService departmentService) {
		String sql = "INSERT INTO tbl_department_services(department_name, department_id, service_id,service_type) "
				+ "VALUES(:departmentName, :departmentId, :serviceId,:serviceType);";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(departmentService);
		jdbcTemplate.update(sql, source, keyHolder);
		return keyHolder.getKey().longValue();
	}

	public int update(DepartmentService departmentService) {
		String sql = "UPDATE tbl_department_services SET department_name =:departmentName, department_id =:departmentId, "
				+ "service_id =:serviceId,service_type =:serviceType WHERE id =:id;";
		BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(departmentService);
		return jdbcTemplate.update(sql, source);

	}

	public int delete(long id) {
		String sql = "DELETE FROM tbl_department_services WHERE id =:id;";
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("id", id);
		return jdbcTemplate.update(sql, maps);
	}

	public DepartmentService get(Long departmentServiceId) {
		String sql = "SELECT * FROM tbl_department_services WHERE id = :departmentServiceId";
		Map<String, Object> map = new HashMap<>();
		map.put("departmentId", departmentServiceId);
		return jdbcTemplate.queryForObject(sql, map,
				new BeanPropertyRowMapper<DepartmentService>(DepartmentService.class));
	}

	public List<DepartmentService> getByServiceType(String serviceType) {
		String sql = "SELECT * FROM tbl_department_services WHERE service_type =:serviceType;";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("serviceType", serviceType);
		return jdbcTemplate.query(sql, map, new BeanPropertyRowMapper<DepartmentService>(DepartmentService.class));
	}

	public int deleteByDepartmentId(Long departmentId) {
		String sql = "DELETE FROM tbl_department_services WHERE department_id =:departmentId;";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("departmentId", departmentId);
		return jdbcTemplate.update(sql, map);
	}

	public int deleteByServiceId(Long serviceId) {
		String sql = "DELETE FROM tbl_department_services WHERE service_id =:serviceId;";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("serviceId", serviceId);
		return jdbcTemplate.update(sql, map);
	}
}
