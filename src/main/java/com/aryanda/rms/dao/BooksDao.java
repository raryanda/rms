package com.aryanda.rms.dao;

import com.aryanda.rms.model.Books;

import java.util.List;

public interface BooksDao extends Dao<Books> {
    List<Books> getByTitle(String title);
    List<Books> getByAuthor(String author);
    List<Books> getByTitleAndAuthor(String title, String author);
}
