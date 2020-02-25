package adstech.vn.com.payroll.repository;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import adstech.vn.com.payroll.model.WorkingSchedule;
import adstech.vn.com.payroll.util.CommonUtil;

@Repository
public class WorkingScheduleRepository {

	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;
	
	public int saveSchedule(List<WorkingSchedule> schedules, String createdBy) {
		if(CommonUtil.isNull(schedules)) {
			return 0;
		}
		
		deleteSchedule();
		
		String sql = "INSERT INTO tbl_working_schedules(day_of_week, start_time, end_time, created_at, created_by) "
				+ "VALUES(:dayOfWeek, :startTime, :endTime, CURRENT_TIMESTAMP, :createdBy)";
		
		SqlParameterSource[] sources = new BeanPropertySqlParameterSource[schedules.size()];
		for(int i = 0; i < schedules.size(); i++) {
			WorkingSchedule schedule = schedules.get(i);
			schedule.setCreatedBy(createdBy);
			sources[i] = new BeanPropertySqlParameterSource(schedule);
		}
		
		jdbcTemplate.batchUpdate(sql, sources);
		
		return schedules.size();
	}
	
	public int deleteSchedule() {
		String sql = "DELETE FROM tbl_working_schedules";
		return jdbcTemplate.update(sql, new HashMap<>());
	}
	
	public List<WorkingSchedule> getSchedules(){
		String sql = "SELECT * FROM tbl_working_schedules";
		
		return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(WorkingSchedule.class));
	}
}
