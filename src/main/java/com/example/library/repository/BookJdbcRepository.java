package com.example.library.repository;

import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.model.Category;
import com.example.library.model.Publisher;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class BookJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public BookJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private List<Category> getCategoryForBook(Long categoryId) {
        if (categoryId == null) return List.of();
        String sql = "SELECT id, name FROM category WHERE id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Category category = new Category();
            category.setId(rs.getLong("id"));
            category.setName(rs.getString("name"));
            return category;
        }, categoryId);
    }

    private class BookRowMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            Book book = new Book();
            book.setId(rs.getLong("book_id"));
            book.setTitle(rs.getString("title"));

            Author author = new Author();
            author.setId(rs.getLong("author_id"));
            author.setName(rs.getString("author_name"));
            book.setAuthor(author);

            Publisher publisher = new Publisher();
            publisher.setId(rs.getLong("publisher_id"));
            publisher.setName(rs.getString("publisher_name"));
            book.setPublisher(publisher);

            book.setCategories(getCategoryForBook(rs.getLong("category_id")));
            return book;
        }
    }

    public List<Book> findAll() {
        String sql = """
            SELECT b.id AS book_id, b.title, b.category_id,
                   a.id AS author_id, a.name AS author_name,
                   p.id AS publisher_id, p.name AS publisher_name
            FROM book b
            JOIN author a ON b.author_id = a.id
            JOIN publisher p ON b.publisher_id = p.id
        """;
        return jdbcTemplate.query(sql, new BookRowMapper());
    }

    public Optional<Book> findById(Long id) {
        String sql = """
            SELECT b.id AS book_id, b.title, b.category_id,
                   a.id AS author_id, a.name AS author_name,
                   p.id AS publisher_id, p.name AS publisher_name
            FROM book b
            JOIN author a ON b.author_id = a.id
            JOIN publisher p ON b.publisher_id = p.id
            WHERE b.id = ?
        """;
        List<Book> list = jdbcTemplate.query(sql, new BookRowMapper(), id);
        return list.stream().findFirst();
    }

    public void save(Book book) {
        String sql = "INSERT INTO book (title, author_id, publisher_id, category_id) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, book.getTitle(), book.getAuthor().getId(), book.getPublisher().getId(),
                book.getCategories() != null && !book.getCategories().isEmpty() ? book.getCategories().get(0).getId() : null);
    }

    public void update(Long id, Book book) {
        String sql = "UPDATE book SET title = ?, author_id = ?, publisher_id = ?, category_id = ? WHERE id = ?";
        jdbcTemplate.update(sql, book.getTitle(), book.getAuthor().getId(), book.getPublisher().getId(),
                book.getCategories() != null && !book.getCategories().isEmpty() ? book.getCategories().get(0).getId() : null, id);
    }

    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM book WHERE id = ?", id);
    }
}