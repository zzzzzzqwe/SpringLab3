package com.example.library.repository;

import com.example.library.model.Author;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public AuthorJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static class AuthorRowMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            Author author = new Author();
            author.setId(rs.getLong("id"));
            author.setName(rs.getString("name"));
            return author;
        }
    }

    public List<Author> findAll() {
        return jdbcTemplate.query("SELECT * FROM author", new AuthorRowMapper());
    }

    public Optional<Author> findById(Long id) {
        List<Author> authors = jdbcTemplate.query("SELECT * FROM author WHERE id = ?", new AuthorRowMapper(), id);
        return authors.stream().findFirst();
    }

    public void save(Author author) {
        jdbcTemplate.update("INSERT INTO author(name) VALUES (?)", author.getName());
    }

    public void update(Long id, Author author) {
        jdbcTemplate.update("UPDATE author SET name = ? WHERE id = ?", author.getName(), id);
    }

    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM author WHERE id = ?", id);
    }
}