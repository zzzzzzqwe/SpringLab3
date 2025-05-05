package com.example.library.repository;

import com.example.library.model.Category;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class CategoryJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public CategoryJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static class CategoryRowMapper implements RowMapper<Category> {
        @Override
        public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
            Category obj = new Category();
            obj.setId(rs.getLong("id"));
            obj.setName(rs.getString("name"));
            return obj;
        }
    }

    public List<Category> findAll() {
        return jdbcTemplate.query("SELECT * FROM category", new CategoryRowMapper());
    }

    public Optional<Category> findById(Long id) {
        List<Category> list = jdbcTemplate.query("SELECT * FROM category WHERE id = ?", new CategoryRowMapper(), id);
        return list.stream().findFirst();
    }

    public void save(Category obj) {
        jdbcTemplate.update("INSERT INTO category(name) VALUES (?)", obj.getName());
    }

    public void update(Long id, Category obj) {
        jdbcTemplate.update("UPDATE category SET name = ? WHERE id = ?", obj.getName(), id);
    }

    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM category WHERE id = ?", id);
    }
}