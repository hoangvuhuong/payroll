package adstech.vn.com.payroll.repository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import adstech.vn.com.payroll.model.Ticket;

@Repository
public class TicketRepository {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	public Long create(Ticket ticket) {
		String sql = "INSERT INTO tbl_tickets(user_id, approver_id, approver_email, reason_code, reason_detail, reject_reason, "
				+ "period, status, date, due_date, leaves_to, leaves_from, created_at, created_by) VALUES(:userId, :approverId, :approverEmail, :reasonCode,"
				+ " :reasonDetail, :rejectReason, :period, :status, :date, :dueDate, :leavesTom, :leavesFrom,CURRENT_TIMESTAMP, :createdBy);";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(ticket);
		jdbcTemplate.update(sql, source, keyHolder);
		return keyHolder.getKey().longValue();

	}

	public int update(Ticket ticket) {
		String sql = "UPDATE tbl_tickets SET user_id =:userId, approver_id =:approverId, approver_email =:approverEmail, "
				+ "reason_code =:reasonCode, reason_detail =:reasonDetail, reject_reason =:rejectReason, "
				+ "period =:period, status =:status, date =:date, due_date =:dueDate, leaves_to=:leavesTo,leaves_from =:leavesFrom,"
				+ " updated_at =CURRENT_TIMESTAMP, updated_by =:updatedBy WHERE id =:id";
		BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(ticket);
		return jdbcTemplate.update(sql, source);

	}
	
	public int delete(Long id) {
		String sql = "DELETE FROM tbl_tickets WHERE id =:id;";
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("id",id);
		return jdbcTemplate.update(sql, maps);
	}
	
	public Ticket getById(Integer id) {
		String sql = "SELECT * FROM tbl_tickets WHERE id =:id;";
		Map<String, Object> maps = new HashMap<String , Object>();
		maps.put("id", id);
		return jdbcTemplate.queryForObject(sql, maps, new BeanPropertyRowMapper<Ticket>(Ticket.class));
	}
}
