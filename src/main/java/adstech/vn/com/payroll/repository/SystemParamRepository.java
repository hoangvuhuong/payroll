package adstech.vn.com.payroll.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import adstech.vn.com.payroll.model.SystemParam;

@Repository
public class SystemParamRepository {

	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	public int saveParam(SystemParam param) {
		String sql = "INSERT INTO tbl_system_params(`key`, `value`) VALUES(:key, :value) ON DUPLICATE KEY UPDATE `value` = :value";

		BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(param);

		return jdbcTemplate.update(sql, source);
	}

	public String getParam(String key) {
		String sql = "SELECT `value` FROM tbl_system_params WHERE `key` = :key";

		Map<String, Object> map = new HashMap<>();
		map.put("key", key);

		List<String> results = jdbcTemplate.queryForList(sql, map, String.class);

		if (results != null && results.size() > 0) {
			return results.get(0);
		}
		return null;
	}
}
