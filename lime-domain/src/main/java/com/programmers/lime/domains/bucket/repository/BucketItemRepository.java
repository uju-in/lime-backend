package com.programmers.lime.domains.bucket.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.programmers.lime.domains.bucket.domain.BucketItem;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BucketItemRepository {

	private final JdbcTemplate jdbcTemplate;

	public void saveAll(List<BucketItem> bucketItems) {
		jdbcTemplate.batchUpdate("INSERT INTO bucket_items(item_id,bucket_id,created_at,modified_at) values (?,?,?,?)",
			new BatchPreparedStatementSetter() {
				@Override
				public void setValues(final PreparedStatement ps, final int i) throws SQLException {
					BucketItem bucketItem = bucketItems.get(i);

					ps.setLong(1, bucketItem.getItemId());
					ps.setLong(2, bucketItem.getBucketId());
					ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
					ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
				}

				@Override
				public int getBatchSize() {
					return bucketItems.size();
				}
			});
	}

	public void deleteAll(Long bucketId) {
		String sql = "DELETE FROM BucketItems WHERE bucket_id = ?";

		jdbcTemplate.batchUpdate(sql,
			new BatchPreparedStatementSetter() {
				@Override
				public void setValues(final PreparedStatement ps, final int i) throws SQLException {
					ps.setLong(1, bucketId);
				}

				@Override
				public int getBatchSize() {
					if (bucketId == null) {
						return 0;
					}
					return 1;
				}
			});
	}

	public List<BucketItem> findAllByBucketId(final Long bucketId) {
		String sql = "SELECT * FROM BucketItems WHERE bucket_id = ?";
		return jdbcTemplate.query(sql, bucketItemRowMapper(), bucketId);
	}

	private static RowMapper<BucketItem> bucketItemRowMapper() {
		return (rs, rowNum) ->
			new BucketItem(
				rs.getLong("id"),
				rs.getLong("item_id"),
				rs.getLong("bucket_id")
			);
	}

}
