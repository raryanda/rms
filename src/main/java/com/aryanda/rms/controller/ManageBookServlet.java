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
import java.util.ResourceBundle;

@WebServlet("/books/manage/*")
public class ManageBookServlet extends AbstractController {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bookTitle = request.getParameter("bookTitle");
        String bookAuthor = request.getParameter("bookAuthor");
        String path = doAction(request, bookTitle, bookAuthor);

        request.getRequestDispatcher(VIEW_PREFIX + path).forward(request, response);
    }

    private String doAction(HttpServletRequest request, String bookTitle, String bookAuthor) {
        ResourceBundle messageBundle = ResourceBundle.getBundle(BUNDLE_MESSAGE);
        BooksDao booksDao = BooksDaoImpl.getInstance();
        String path = PAGE_LIST;

        boolean validInput = Util.isNotEmpty(bookTitle) && Util.isNotEmpty(bookAuthor);
        if ("/add".equalsIgnoreCase(request.getPathInfo())) {
            Books book = new Books(bookTitle, bookAuthor);
            if (validInput) {
                booksDao.save(book);
                request.setAttribute("message", messageBundle.getString("success_add"));
            } else {
                request.setAttribute("message", messageBundle.getString("error_field_empty"));
                request.setAttribute("book", book);
                path = PAGE_ADD;
            }
        } else if ("/update".equalsIgnoreCase(request.getPathInfo())) {
            Long bookId = null;
            try {
                bookId = Long.parseLong(request.getParameter("bookId"));
                if (!booksDao.get(bookId).isPresent()) {
                    bookId = null;
                    validInput = false;
                }
            } catch (NumberFormatException e) {
                validInput = false;
            }

            Books book = new Books(bookId, bookTitle, bookAuthor);
            if (validInput) {
                booksDao.update(book);
                request.setAttribute("message", messageBundle.getString("success_update"));
            } else {
                if (book.getId() != null) {
                    request.setAttribute("updateBook", true);
                    request.setAttribute("message", messageBundle.getString("error_field_empty"));
                    request.setAttribute("book", book);
                    path = PAGE_ADD;
                } else {
                    request.setAttribute("message", messageBundle.getString("error_request"));
                }
            }
        } else {
            request.setAttribute("message", messageBundle.getString("error_request"));
        }

        if (path.equalsIgnoreCase(PAGE_LIST)) {
            request.setAttribute("books", booksDao.getAll());
        }
        return path;
    }
}
