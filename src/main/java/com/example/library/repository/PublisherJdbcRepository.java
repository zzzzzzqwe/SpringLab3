package com.example.library.repository;

import com.example.library.model.Publisher;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class PublisherJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public PublisherJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static class PublisherRowMapper implements RowMapper<Publisher> {
        @Override
        public Publisher mapRow(ResultSet rs, int rowNum) throws SQLException {
            Publisher obj = new Publisher();
            obj.setId(rs.getLong("id"));
            obj.setName(rs.getString("name"));
            return obj;
        }
    }

    public List<Publisher> findAll() {
        return jdbcTemplate.query("SELECT * FROM publisher", new PublisherRowMapper());
    }

    public Optional<Publisher> findById(Long id) {
        List<Publisher> list = jdbcTemplate.query("SELECT * FROM publisher WHERE id = ?", new PublisherRowMapper(), id);
        return list.stream().findFirst();
    }

    public void save(Publisher obj) {
        jdbcTemplate.update("INSERT INTO publisher(name) VALUES (?)", obj.getName());
    }

    public void update(Long id, Publisher obj) {
        jdbcTemplate.update("UPDATE publisher SET name = ? WHERE id = ?", obj.getName(), id);
    }

    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM publisher WHERE id = ?", id);
    }
}