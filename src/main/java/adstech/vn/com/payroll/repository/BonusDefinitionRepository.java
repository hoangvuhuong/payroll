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

import adstech.vn.com.payroll.model.BonusDefinition;
import adstech.vn.com.payroll.util.CommonUtil;

@Repository
public class BonusDefinitionRepository {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	public Long create(BonusDefinition bonusDefine) {
		String sql = "INSERT INTO tbl_bonus_definitions(bonus_name, description, service_type, department_id, employee_level, bonus_type, bonus_method, "
				+ "bonus_period, bonus_factor, from_ads, is_external, is_internal, created_at,created_by, status) VALUES(:bonusName, :description, "
				+ ":serviceType, :departmentId, :employeeLevel, :bonusType, :bonusMethod, :bonusPeriod, :bonusFactor, :fromAds, :isExternal, :isInternal, "
				+ "CURRENT_TIMESTAMP, :createdBy, :status);";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(bonusDefine);
		jdbcTemplate.update(sql, source, keyHolder);
		
		return keyHolder.getKey().longValue();
	}

	public int update(BonusDefinition bonusDefine) {
		String sql = "UPDATE tbl_bonus_definitions SET bonus_name = :bonusName, description = :description, service_type =:serviceType, "
				+ "employee_level=:employeeLevel, bonus_type=:bonusType, bonus_method=:bonusMethod, bonus_period = :bonusPeriod, "
				+ "bonus_factor = :bonusFactor, from_ads=:fromAds, is_external = :isExternal, is_internal = :isInternal, department_id = :departmentId, "
				+ "updated_at= CURRENT_TIMESTAMP, updated_by=:updatedBy, status = :status WHERE bonus_id=:bonusId;";
		BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(bonusDefine);
		return jdbcTemplate.update(sql, source);
	}

	public int delete(long bonusId) {
		String sql = "DELETE FROM tbl_bonus_definitions WHERE bonus_id=:bonusId;";
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("bonusId", bonusId);
		return jdbcTemplate.update(sql, maps);
	}

	public BonusDefinition findById(long bonusId) {
		String sql = "SELECT * FROM tbl_bonus_definitions WHERE bonus_id =:bonusId;";
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("bonusId", bonusId);
		return jdbcTemplate.queryForObject(sql, maps,
				new BeanPropertyRowMapper<BonusDefinition>(BonusDefinition.class));
	}
	
	public List<BonusDefinition> findByServiceType(String serviceType) {
		String sql = "SELECT a.*, b.department_name FROM tbl_bonus_definitions a LEFT JOIN tbl_departments b ON a.department_id = b.id "
				+ "WHERE a.service_type =:serviceType;";
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("serviceType", serviceType);
		return jdbcTemplate.query(sql, maps,
				new BeanPropertyRowMapper<BonusDefinition>(BonusDefinition.class));
	}
	
	public List<BonusDefinition> findByDepartmentId(Long departmentId, List<String> serviceTypes) {
		String sql = "SELECT * FROM tbl_bonus_definitions WHERE department_id =:departmentId OR is_external = 1";
		Map<String, Object> maps = new HashMap<String, Object>();
		if(!CommonUtil.isNull(serviceTypes)) {
			sql += " OR service_type IN (:serviceTypes)";
			maps.put("departmentId", departmentId);
		}
		maps.put("serviceTypes", serviceTypes);
		return jdbcTemplate.query(sql, maps,
				new BeanPropertyRowMapper<BonusDefinition>(BonusDefinition.class));
	}
	
	public List<BonusDefinition> findByEmployeeLevel(String employeeLevel) {
		String sql = "SELECT * FROM tbl_bonus_definitions WHERE employee_level =:employeeLevel OR employee_level IS NULL";
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("employeeLevel", employeeLevel);
		return jdbcTemplate.query(sql, maps,
				new BeanPropertyRowMapper<BonusDefinition>(BonusDefinition.class));
	}
}
