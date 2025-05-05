package com.example.library.repository;

import com.example.library.model.Library;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class LibraryJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public LibraryJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static class LibraryRowMapper implements RowMapper<Library> {
        @Override
        public Library mapRow(ResultSet rs, int rowNum) throws SQLException {
            Library obj = new Library();
            obj.setId(rs.getLong("id"));
            obj.setName(rs.getString("name"));
            return obj;
        }
    }

    public List<Library> findAll() {
        return jdbcTemplate.query("SELECT * FROM library", new LibraryRowMapper());
    }

    public Optional<Library> findById(Long id) {
        List<Library> list = jdbcTemplate.query("SELECT * FROM library WHERE id = ?", new LibraryRowMapper(), id);
        return list.stream().findFirst();
    }

    public void save(Library obj) {
        jdbcTemplate.update("INSERT INTO library(name) VALUES (?)", obj.getName());
    }

    public void update(Long id, Library obj) {
        jdbcTemplate.update("UPDATE library SET name = ? WHERE id = ?", obj.getName(), id);
    }

    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM library WHERE id = ?", id);
    }
}