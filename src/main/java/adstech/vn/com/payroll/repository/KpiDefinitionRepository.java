package adstech.vn.com.payroll.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import adstech.vn.com.payroll.model.KpiDefinition;
import adstech.vn.com.payroll.util.CommonUtil;

@Repository
public class KpiDefinitionRepository {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	public int create(KpiDefinition kpi) {
		String sql = "INSERT INTO tbl_kpi_definitions(service_type, employee_level, department_id, user_id, kpi_type, target, target_type, status, "
				+ "created_at, created_by) VALUES (:serviceType, :employeeLevel, :departmentId, :userId, :kpiType, :target, :targetType, :status, "
				+ "CURRENT_TIMESTAMP, :createdBy);";
		BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(kpi);
		return jdbcTemplate.update(sql, source);
	}

	public int update(KpiDefinition kpi) {
		String sql = "UPDATE tbl_kpi_definitions SET service_type =:serviceType, department_id = :departmentId, user_id = :userId, "
				+ "employee_level =:employeeLevel, kpi_type =:kpiType, target =:target, target_type = :targetType, status =:status, "
				+ "updated_by =:updatedBy, updated_at = CURRENT_TIMESTAMP WHERE id =:id;";
		BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(kpi);
		return jdbcTemplate.update(sql, source);
	}

	public KpiDefinition findById(long id) {
		String sql = "SELECT * FROM tbl_kpi_definitions WHERE id =:id;";
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("id", id);
		return jdbcTemplate.queryForObject(sql, maps, new BeanPropertyRowMapper<KpiDefinition>(KpiDefinition.class));
	}

	public List<KpiDefinition> findByServiceType(String serviceType) {
		String sql = "SELECT * FROM tbl_kpi_definitions WHERE service_type=:serviceType ;";
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("serviceType", serviceType);
		return jdbcTemplate.query(sql, maps, new BeanPropertyRowMapper<KpiDefinition>(KpiDefinition.class));
	}
	
	public KpiDefinition findByServiceType(String serviceType, String employeeLevel) {
		String sql = "SELECT * FROM tbl_kpi_definitions WHERE service_type=:serviceType AND employee_level = :employeeLevel";
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("serviceType", serviceType);
		maps.put("employeeLevel", employeeLevel);
		List<KpiDefinition> kpiDefinitions = jdbcTemplate.query(sql, maps, new BeanPropertyRowMapper<KpiDefinition>(KpiDefinition.class));
		if(!CommonUtil.isNull(kpiDefinitions)) {
			return kpiDefinitions.get(0);
		}
		return null;
	}
	
	public KpiDefinition getByUser(Long userId) {
		String sql = "SELECT * FROM tbl_kpi_definitions WHERE user_id = :userId";
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("userId", userId);
		List<KpiDefinition> kpiDefinitions = jdbcTemplate.query(sql, maps, new BeanPropertyRowMapper<KpiDefinition>(KpiDefinition.class));
		if(!CommonUtil.isNull(kpiDefinitions)) {
			return kpiDefinitions.get(0);
		}
		return null;
	}
	
	public KpiDefinition getByDepartment(Long departmentId, String employeeLevel) {
		String sql = "SELECT * FROM tbl_kpi_definitions WHERE department_id = :departmentId AND employee_level = :employeeLevel";
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("departmentId", departmentId);
		maps.put("employeeLevel", employeeLevel);
		List<KpiDefinition> kpiDefinitions = jdbcTemplate.query(sql, maps, new BeanPropertyRowMapper<KpiDefinition>(KpiDefinition.class));
		if(!CommonUtil.isNull(kpiDefinitions)) {
			return kpiDefinitions.get(0);
		}
		return null;
	}
	
	public List<KpiDefinition> getByDepartment(Long departmentId) {
		String sql = "SELECT * FROM tbl_kpi_definitions WHERE department_id=:departmentId ;";
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("departmentId", departmentId);
		return jdbcTemplate.query(sql, maps, new BeanPropertyRowMapper<KpiDefinition>(KpiDefinition.class));
	}

	public int delete(long id) {
		String sql = "DELETE FROM tbl_kpi_definitions WHERE id =:id;";
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("id", id);
		return jdbcTemplate.update(sql, maps);
	}
}
