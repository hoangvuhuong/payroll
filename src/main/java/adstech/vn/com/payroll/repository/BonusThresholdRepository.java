package adstech.vn.com.payroll.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import adstech.vn.com.payroll.model.BonusThreshold;

@Repository
public class BonusThresholdRepository {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	public int create(List<BonusThreshold> bonusThresholds, Long bonusId) {
		String sql = "INSERT INTO tbl_bonus_thresholds (bonus_id, upper_bound, lower_bound, bonus_percent, bonus_value, is_reference, "
				+ "bonus_level, created_at,created_by) VALUES(:bonusId, :upperBound, :lowerBound, :bonusPercent, :bonusValue, "
				+ ":isReference, :bonusLevel, CURRENT_TIMESTAMP, :createdBy)";
		BeanPropertySqlParameterSource[] sources = new BeanPropertySqlParameterSource[bonusThresholds.size()];
		for (int i = 0; i < bonusThresholds.size(); i++) {
			BonusThreshold threshold = bonusThresholds.get(i);
			threshold.setBonusId(bonusId);
			sources[i] = new BeanPropertySqlParameterSource(threshold);
		}
		jdbcTemplate.batchUpdate(sql, sources);
		return bonusThresholds.size();
	}

	public int update(List<BonusThreshold> bonusThresholds) {
		String sql = "UPDATE tbl_bonus_thresholds SET bonus_id =:bonusId, upper_bound=:upperBound, lower_bound=:lowerBound, "
				+ "bonus_percent=:bonusPercent, bonus_value=:bonusValue, is_reference = :isReference, bonus_level = :bonusLevel, "
				+ "updated_at = CURRENT_TIMESTAMP,updated_by =:updatedBy WHERE id=:id;";
		BeanPropertySqlParameterSource[] sources = new BeanPropertySqlParameterSource[bonusThresholds.size()];
		for (int i = 0; i < bonusThresholds.size(); i++) {
			sources[i] = new BeanPropertySqlParameterSource(bonusThresholds.get(i));
		}
		jdbcTemplate.batchUpdate(sql, sources);
		return bonusThresholds.size();
	}

	public int delete(long id) {
		String sql = "DELETE FROM tbl_bonus_thresholds WHERE id=:id";
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("id", id);
		return jdbcTemplate.update(sql, maps);

	}

	public int deleteByBonusId(long bonusId) {
		String sql = "DELETE FROM tbl_bonus_thresholds WHERE bonus_id=:bonusId";
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("bonusId", bonusId);
		return jdbcTemplate.update(sql, maps);

	}

	public List<BonusThreshold> findByBonusId(long bonusId) {
		String sql = "SELECT * FROM tbl_bonus_thresholds WHERE bonus_id =:bonusId;";
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("bonusId", bonusId);
		return jdbcTemplate.query(sql, maps, new BeanPropertyRowMapper<BonusThreshold>(BonusThreshold.class));
	}
	
	public List<BonusThreshold> findByBonusId(List<Long> bonusIds) {
		String sql = "SELECT * FROM tbl_bonus_thresholds WHERE bonus_id IN (:bonusIds);";
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("bonusIds", bonusIds);
		return jdbcTemplate.query(sql, maps, new BeanPropertyRowMapper<BonusThreshold>(BonusThreshold.class));
	}

}
