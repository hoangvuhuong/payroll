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

import adstech.vn.com.payroll.model.Service;

@Repository
public class ServiceRepository {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;
	public Long create(Service service) {
		String sql = "INSERT INTO tbl_services(service_name,service_type, description, status, created_at, created_by) "
				+ "VALUES(:serviceName,:serviceType, :description, :status, CURRENT_TIMESTAMP, :createdBy);";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(service);
		jdbcTemplate.update(sql,source,keyHolder);
		return keyHolder.getKey().longValue();
	}
	public int update(Service service) {
		String sql = "UPDATE tbl_services SET service_name =:serviceName,service_type =:serviceType, description =:description, "
				+ "status=:status, updated_at =CURRENT_TIMESTAMP, updated_by =:updatedBy WHERE id =:id;";
		BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(service);
		return jdbcTemplate.update(sql,source);
	}
	
	public int delete(long id) {
		String sql = "DELETE FROM tbl_services WHERE id =:id";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return jdbcTemplate.update(sql,map);
	}
	public Service get(long id) {
		String sql = "SELECT * FROM tbl_services WHERE id =:id";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return jdbcTemplate.queryForObject(sql,map,  new BeanPropertyRowMapper<Service>(Service.class));
	}
	public List<Service> getByServiceType(String serviceType){
		String sql = "SELECT * FROM tbl_services WHERE service_type =:serviceType";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("serviceType", serviceType);
		return jdbcTemplate.query(sql,map, new BeanPropertyRowMapper<Service>(Service.class));
	}
	public List<String> getAllServiceType(){
		String sql = "SELECT service_type FROM tbl_services;";
		return jdbcTemplate.queryForList(sql, new HashMap<>(),String.class);
	}
	public List<Service> getAllService(){
		String sql = "SELECT * FROM tbl_services;";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Service>(Service.class));
	}
}
