package com.aryanda.rms.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public abstract class AbstractController extends HttpServlet {

    protected static final String VIEW_PREFIX_OLD = "/WEB-INF/jsp";
    protected static final String VIEW_PREFIX = "/";
    protected static final String VIEW_SUFFIX = ".jsp";
    protected static final String PAGE_ADD = "add" + VIEW_SUFFIX;
    protected static final String PAGE_LIST = "list" + VIEW_SUFFIX;
    protected static final String BUNDLE_MESSAGE = "message";

    protected String getTemplatePath(String path) {
        if (path.equalsIgnoreCase("/"))
            return VIEW_PREFIX_OLD + path + "index" + VIEW_SUFFIX;

        return path.substring(path.length() - 1).equalsIgnoreCase("/") ? VIEW_PREFIX_OLD + path : VIEW_PREFIX_OLD + path + VIEW_SUFFIX;
    }

    protected void setSessionLocale(HttpServletRequest request) {
        request.getSession().setAttribute("locale", Locale.getDefault());
    }
}
