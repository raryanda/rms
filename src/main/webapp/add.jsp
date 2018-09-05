<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<c:set var="pageURL" value="${pageContext.request.contextPath}" />
<c:set var="locale"  value="${not empty param.locale ? param.locale : not empty locale ? locale : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${locale}" />
<fmt:setBundle basename="message" />
<head>
    <title>Books Manage - RMS</title>
    <script src="${pageURL}/webjars/materializecss/1.0.0-rc.2/js/materialize.min.js"></script>
    <link rel="stylesheet" href="${pageURL}/webjars/materializecss/1.0.0-rc.2/css/materialize.min.css"/>
    <style>
        body {
            padding: 20px;
        }
    </style>
</head>
<body>
<c:set var="formAction" value="${updateBook == true ? 'update' : 'add'}"/>
<c:if test="${updateBook == true || book != null}">
    <c:set var="bookId" value="${book.id}"/>
    <c:set var="bookTitle" value="${book.title}"/>
    <c:set var="bookAuthor" value="${book.author}"/>
</c:if>
<div class="row">
    <fmt:message key="ui_manage_add" var="addBookHeader" />
    <fmt:message key="ui_manage_update" var="updateBookHeader" />
    <h5><c:out value="${updateBook == true ? updateBookHeader : addBookHeader}"/></h5>
</div>

<c:if test="${message != null}">
    <div class="row">
        <div class="col s6">
                ${message}
        </div>
    </div>
</c:if>

<div class="row">
    <form action="${pageURL}/books/manage/${formAction}" method="POST">
        <input type="hidden" id="bookId" name="bookId" value="${bookId}"/>
        <div class="col s6">
            <div class="row">
                <div class="input-field">
                    <label for="bookTitle"><fmt:message key="ui_title" /></label>
                    <input type="text" id="bookTitle" name="bookTitle" value="${bookTitle}"/>
                </div>
            </div>
            <div class="row">
                <div class="input-field">
                    <label for="bookAuthor"><fmt:message key="ui_author" /></label>
                    <input type="text" id="bookAuthor" name="bookAuthor" value="${bookAuthor}"/>
                </div>
            </div>
            <div class="row">
                <div class="input-field">
                    <button class="btn waves-effect waves-light" type="submit"><fmt:message key="ui_submit" /></button>
                    <button class="btn waves-effect waves-light red" type="button" onclick="location.href = '${pageURL}/books/list'"><fmt:message key="ui_cancel" /></button>
                </div>
            </div>
        </div>
    </form>
</div>

</body>
</html>
