<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<c:set var="pageURL" value="${pageContext.request.contextPath}" />
<c:set var="locale"  value="${not empty param.locale ? param.locale : not empty locale ? locale : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${locale}" />
<fmt:setBundle basename="message" />
<head>
    <title>Books List - RMS</title>
    <script src="${pageURL}/webjars/materializecss/1.0.0-rc.2/js/materialize.min.js"></script>
    <link rel="stylesheet" href="${pageURL}/webjars/materializecss/1.0.0-rc.2/css/materialize.min.css"/>
    <style>
        tr > th:first-child {
            width: 10%;
        }
        tr > td:first-child {
            width: 10%;
            text-align: center;
        }
        body {
            padding: 20px;
        }
    </style>
</head>
<body>

<div class="row">
    <div class="col s6">
        <h6><a href="${pageURL}/books/list?locale=en_US"><fmt:message key="lang_english" /></a> | <a href="${pageURL}/books/list?locale=en_ID"><fmt:message key="lang_indonesian" /></a></h6>
    </div>
</div>
<div class="row">
    <form action="${pageURL}/books/list">
        <div class="col s6">
            <div class="input-field col s4">
                <label for="searchTitle"><fmt:message key="ui_title" /></label>
                <input type="text" id="searchTitle" name="searchTitle"/>
            </div>
            <div class="input-field col s4">
                <label for="searchAuthor"><fmt:message key="ui_author" /></label>
                <input type="text" id="searchAuthor" name="searchAuthor"/>
            </div>
            <div class="input-field col s4">
                <button class="btn waves-effect waves-light" type="submit"><fmt:message key="ui_search" /></button>
            </div>
       </div>
    </form>
</div>

<c:if test="${message != null}">
<div class="row">
    <div class="col s6">
        ${message}
    </div>
</div>
</c:if>

<div class="row">
    <div class="col s6">
        <button class="btn waves-effect waves-light" type="button" onclick="location.href='${pageURL}/books/add'"><fmt:message key="ui_new_book" /></button>
    </div>
</div>

<div class="row">
    <div class="col s6">
        <table class="striped">
            <thead>
                <tr>
                    <th>No.</th>
                    <th><fmt:message key="ui_title" /></th>
                    <th><fmt:message key="ui_author" /></th>
                    <th><fmt:message key="ui_actions" /></th>
                </tr>
            </thead>
            <c:set var="count" value="0"/>
            <tbody>
            <fmt:message key="ui_delete_confirm" var="confirmDelete" />
            <c:forEach items="${books}" var="book">
                <c:set var="count" value="${count + 1}"/>
                <tr>
                    <td>${count}</td>
                    <td>${book.title}</td>
                    <td>${book.author}</td>
                    <td><a href="${pageURL}/books/${book.id}/update"><fmt:message key="ui_update" /></a> |
                        <a href="${pageURL}/books/${book.id}/delete" onclick="return confirm('${fn:replace(fn:replace(confirmDelete, "{0}", book.title), "{1}", book.author)}')"><fmt:message key="ui_delete" /></a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

</body>
</html>
