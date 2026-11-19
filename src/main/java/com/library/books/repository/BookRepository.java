package com.library.books.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.library.books.model.Book;

// Repository layer: all SQL (JDBC via JdbcTemplate)
@Repository
public class BookRepository {

    private final JdbcTemplate jdbc;

    public BookRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    // Converts one row of the ResultSet into a Book object
    private final RowMapper<Book> mapper = (rs, rowNum) -> new Book(
            rs.getInt("id"),
            rs.getString("title"),
            rs.getString("author"),
            rs.getString("category"),
            rs.getInt("available_copies"));

    public List<Book> findAll() {
        return jdbc.query("SELECT * FROM book ORDER BY id", mapper);
    }

    public Optional<Book> findById(int id) {
        return jdbc.query("SELECT * FROM book WHERE id = ?", mapper, id)
                   .stream().findFirst();
    }

    public Book save(Book b) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO book (title, author, category, available_copies) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, b.getTitle());
            ps.setString(2, b.getAuthor());
            ps.setString(3, b.getCategory());
            ps.setInt(4, b.getAvailableCopies());
            return ps;
        }, keyHolder);
        b.setId(keyHolder.getKey().intValue());
        return b;
    }

    public int update(Book b) {
        return jdbc.update("UPDATE book SET title = ?, author = ?, category = ?, available_copies = ? WHERE id = ?",
                b.getTitle(), b.getAuthor(), b.getCategory(), b.getAvailableCopies(), b.getId());
    }

    public int deleteById(int id) {
        return jdbc.update("DELETE FROM book WHERE id = ?", id);
    }
}
