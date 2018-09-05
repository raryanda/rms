package com.aryanda.rms.dao.impl;

import com.aryanda.rms.dao.BooksDao;
import com.aryanda.rms.dao.DataSourceFactory;
import com.aryanda.rms.model.Books;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BooksDaoImpl implements BooksDao {

    private static class SingletonHelper {
        private static final BooksDaoImpl INSTANCE = new BooksDaoImpl();
    }

    public static BooksDao getInstance() {
        return SingletonHelper.INSTANCE;
    }

    @Override
    public List<Books> getByTitle(String title) {
        List<Books> result = new ArrayList<>();
        try (Connection conn = DataSourceFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM books WHERE LOWER(title) LIKE LOWER(?)")) {
            stmt.setString(1, "%" + title + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Books books = new Books(rs.getLong("id"), rs.getString("title"), rs.getString("author"));
                    result.add(books);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Books> getByAuthor(String author) {
        List<Books> result = new ArrayList<>();
        try (Connection conn = DataSourceFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM books WHERE LOWER(author) LIKE LOWER(?)")) {
            stmt.setString(1, "%" + author + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Books books = new Books(rs.getLong("id"), rs.getString("title"), rs.getString("author"));
                    result.add(books);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Books> getByTitleAndAuthor(String title, String author) {
        List<Books> result = new ArrayList<>();
        try (Connection conn = DataSourceFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM books WHERE LOWER(title) LIKE LOWER(?) AND LOWER(author) LIKE LOWER(?)")) {
            stmt.setString(1, "%" + title + "%");
            stmt.setString(2, "%" + author + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Books books = new Books(rs.getLong("id"), rs.getString("title"), rs.getString("author"));
                    result.add(books);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;

    }

    @Override
    public Optional<Books> get(Long id) {
        try (Connection conn = DataSourceFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM books WHERE id = ?")) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Books books = new Books(rs.getLong("id"), rs.getString("title"), rs.getString("author"));
                    return Optional.of(books);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Books> getAll() {
        List<Books> result = new ArrayList<>();
        try (Connection conn = DataSourceFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM books")) {
            while (rs.next()) {
                Books books = new Books(rs.getLong("id"), rs.getString("title"), rs.getString("author"));
                result.add(books);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean save(Books books) {
        try (Connection conn = DataSourceFactory.getConnection();
             PreparedStatement stmt = books.getId() != null ? conn.prepareStatement("INSERT INTO books VALUES (?, ?, ?)")
                     : conn.prepareStatement("INSERT INTO books VALUES (DEFAULT, ?, ?)")) {
            if (books.getId() != null) {
                stmt.setLong(1, books.getId());
                stmt.setString(2, books.getTitle());
                stmt.setString(3, books.getAuthor());
            } else {
                stmt.setString(1, books.getTitle());
                stmt.setString(2, books.getAuthor());
            }

            if (stmt.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Books books) {
        try (Connection conn = DataSourceFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE books SET title = ?, author = ? WHERE id = ?")) {
            stmt.setString(1, books.getTitle());
            stmt.setString(2, books.getAuthor());
            stmt.setLong(3, books.getId());
            if (stmt.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Books books) {
        try (Connection conn = DataSourceFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM books WHERE id = ?")) {
            stmt.setLong(1, books.getId());
            if (stmt.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
