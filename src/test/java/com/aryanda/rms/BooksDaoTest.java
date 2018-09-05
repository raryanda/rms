package com.aryanda.rms;

import com.aryanda.rms.dao.BooksDao;
import com.aryanda.rms.dao.DataSourceFactory;
import com.aryanda.rms.dao.impl.BooksDaoImpl;
import com.aryanda.rms.model.Books;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BooksDaoTest {

    private static Connection conn = null;
    private static final Long BOOKS_ID = 100L;
    private static final String BOOKS_TITLE = "The Return of The King";
    private static final String BOOKS_AUTHOR = "J.R.R. Tolkien";
    private static final String BOOKS_TITLE_UPDATE = "Oathbringer";
    private static final String BOOKS_AUTHOR_UPDATE = "Brandon Sanderson";
    private static BooksDao booksDao = null;

    /*@BeforeClass
    public static void initDB() throws SQLException {
        conn = DataSourceFactory.getConnection();
        System.out.println("Connection opened...");
        conn.setAutoCommit(false);
    }

    @AfterClass
    public static void closeDB() throws SQLException {
        if (conn != null) {
            conn.close();
            System.out.println("Connection closed...");
        }
    }*/

    @BeforeClass
    public static void init() {
        booksDao = BooksDaoImpl.getInstance();
    }

    @Test
    public void testAsave1() {
        Books books = new Books(BOOKS_ID, BOOKS_TITLE, BOOKS_AUTHOR);
        assertThat(booksDao.save(books), is(true));
        System.out.println("Book saved...");
    }

    @Test
    public void testAsave2() {
        Books books = new Books(BOOKS_ID, BOOKS_TITLE, BOOKS_AUTHOR);
        assertThat(booksDao.save(books), is(false));
        System.out.println("Duplicate book...");
    }

    @Test
    public void testBupdate() {
        Books books = new Books(BOOKS_ID, BOOKS_TITLE_UPDATE, BOOKS_AUTHOR_UPDATE);
        assertThat(booksDao.update(books), is(true));
        System.out.println("Book updated...");
    }

    @Test
    public void testCgetByTitle() {
        // bad test if table is not empty
        List<Books> list = booksDao.getByTitle(BOOKS_TITLE_UPDATE);
        assertTrue(list.size() > 0);
        System.out.println("Book(s) with title " + BOOKS_TITLE_UPDATE + " found = " + list.size());
    }

    @Test
    public void testDgetByAuthor() {
        // bad test if table is not empty
        List<Books> list = booksDao.getByAuthor(BOOKS_AUTHOR_UPDATE);
        assertTrue(list.size() > 0);
        System.out.println("Book(s) with author " + BOOKS_AUTHOR_UPDATE + " found = " + list.size());
    }

    @Test
    public void testEdelete() {
        Books books = new Books();
        books.setId(BOOKS_ID);
        assertThat(booksDao.delete(books), is(true));
        System.out.println("Book deleted...");
    }
}
