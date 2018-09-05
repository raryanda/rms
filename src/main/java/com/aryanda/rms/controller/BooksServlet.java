package com.aryanda.rms.controller;

import com.aryanda.rms.dao.BooksDao;
import com.aryanda.rms.dao.impl.BooksDaoImpl;
import com.aryanda.rms.model.Books;
import com.aryanda.rms.util.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

@WebServlet("/books/*")
public class BooksServlet extends AbstractController {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path;
        if (request.getSession().getAttribute("locale") == null)
            setSessionLocale(request);

        if ("/list".equalsIgnoreCase(request.getPathInfo())) {
            showBooks(request, request.getParameter("searchTitle"), request.getParameter("searchAuthor"));
            path = PAGE_LIST;
        } else if ("/add".equalsIgnoreCase(request.getPathInfo())) {
            path = PAGE_ADD;
        } else {
            path = doAction(request);
        }

        request.getRequestDispatcher(VIEW_PREFIX + path).forward(request, response);
    }

    private void showBooks(HttpServletRequest request, String searchTitle, String searchAuthor) {
        ResourceBundle messageBundle = ResourceBundle.getBundle(BUNDLE_MESSAGE);
        boolean searchBoth = Util.isNotEmpty(searchTitle) && Util.isNotEmpty(searchAuthor);
        BooksDao booksDao = BooksDaoImpl.getInstance();
        List<Books> books;
        String message = null;
        if (searchBoth) {
            books = booksDao.getByTitleAndAuthor(searchTitle, searchAuthor);
            message = !books.isEmpty() ? String.format(messageBundle.getString("search_found"), searchTitle, searchAuthor, books.size())
                    : String.format(messageBundle.getString("search_not_found"), searchTitle, searchAuthor);
        } else if (Util.isNotEmpty(searchTitle)) {
            books = booksDao.getByTitle(searchTitle);
            message = !books.isEmpty() ? String.format(messageBundle.getString("search_found_title"), searchTitle, books.size())
                    : String.format(messageBundle.getString("search_not_found_title"), searchTitle);
        } else if (Util.isNotEmpty(searchAuthor)) {
            books = booksDao.getByAuthor(searchAuthor);
            message = !books.isEmpty() ? String.format(messageBundle.getString("search_found_author"), searchAuthor, books.size())
                    : String.format(messageBundle.getString("search_not_found_author"), searchAuthor);
        } else {
            books = booksDao.getAll();
        }
        request.setAttribute("books", books);
        if (message != null)
            request.setAttribute("message", message);
    }

    private String doAction(HttpServletRequest request) {
        ResourceBundle messageBundle = ResourceBundle.getBundle(BUNDLE_MESSAGE);
        String[] split = request.getPathInfo().split("/");
        Long id = null;
        String path = PAGE_LIST;
        BooksDao booksDao = BooksDaoImpl.getInstance();

        try {
            id = Long.parseLong(split[1]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        boolean idValidWithAction = id != null && split.length > 2 && split[2] != null;
        if (idValidWithAction) {
            Books book = booksDao.get(id).orElse(null);

            if (book != null) {
                switch (split[2].toLowerCase()) {
                    case "update":
                        request.setAttribute("book", book);
                        request.setAttribute("updateBook", true);
                        path = PAGE_ADD;
                        break;
                    case "delete":
                        booksDao.delete(book);
                        request.setAttribute("message", String.format(messageBundle.getString("success_delete"), book.getTitle(), book.getAuthor()));
                        break;
                    default:
                        request.setAttribute("message", messageBundle.getString("error_request"));
                        break;
                }
            } else {
                request.setAttribute("message", messageBundle.getString("error_book_not_found"));
            }
        }

        if (id == null) {
            request.setAttribute("message", messageBundle.getString("error_request"));
        }

        request.setAttribute("books", booksDao.getAll());
        return path;
    }
}
