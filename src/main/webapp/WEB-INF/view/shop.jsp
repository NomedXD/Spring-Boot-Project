<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<head>
    <title>Shop</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${contextPath}/jsp-scc-styles/shop.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="${contextPath}/fontawesome/css/all.css">
    <link href="${contextPath}/jsp-scc-styles/pagination.css" rel="stylesheet"/>
    <link rel="stylesheet" href="${contextPath}/jsp-scc-styles/header.css">
</head>
<body class="body">
<jsp:include page="header.jsp"/>
<div class="gallery">
    <c:choose>
        <c:when test="${categories.size() == 0}">
            <div class="bar warn">No categories found. Try later</div>
        </c:when>
        <c:otherwise>
            <sec:authorize access="hasAuthority('ADMIN')">
                <!--Export/import-->
                <div class="container-fluid mt-2">
                    <a href="${contextPath}/catalog/export" class="btn btn-dark btn-lg" data-mdb-ripple-color="dark">Export
                        categories</a>
                    <c:if test="${not empty eiMessage}">${eiMessage}</c:if>
                    <form action="${contextPath}/catalog/import" method="POST" enctype="multipart/form-data"
                          id="importCategories">
                        <input type="file" class="file-input" name="file"
                               aria-describedby="inputGroupFileAddon01" required>
                    </form>
                    <button type="submit" class="btn btn-dark btn-lg" data-mdb-ripple-color="dark"
                            form="importCategories">
                        Import categories
                    </button>
                </div>
            </sec:authorize>
            <div class="container-fluid">
                <div class="dropdown row p-2 mt-2 d-flex justify-content-end">
                    <button type="button" class="btn btn-dark dropdown-toggle float-end" data-bs-toggle="dropdown">
                        Page size
                    </button>
                    <ul class="dropdown-menu">
                        <c:choose>
                            <c:when test="${pageSize == 5}">
                                <li><a class="dropdown-item disabled" href="${contextPath}/catalog?page=1&size=5">5 is
                                    current</a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a class="dropdown-item" href="${contextPath}/catalog?page=1&size=5">5</a></li>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${pageSize == 10}">
                                <li disabled><a class="dropdown-item disabled"
                                                href="${contextPath}/catalog?page=1&size=10">10 is current</a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a class="dropdown-item" href="${contextPath}/catalog?page=1&size=10">10</a></li>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${pageSize == 15}">
                                <li disabled><a class="dropdown-item disabled"
                                                href="${contextPath}/catalog?page=1&size=15">15 is current</a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a class="dropdown-item" href="${contextPath}/catalog?page=1&size=15">15</a></li>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </div>
            </div>
            <c:forEach items="${categories}" var="item">
                <div class="content">
                    <img class="shopImg" src="${contextPath}/${item.getPrimeCategoryImage().path}"
                         alt="${contextPath}/images/error-page.png">
                    <h3>${item.name}</h3>
                    <p>${item.sometext}</p>
                    <form action="${contextPath}/category/${item.id}?page=1&size=5" method="GET">
                        <button class="buy-1">Buy now</button>
                    </form>
                </div>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</div>
<div class="container">
    <div class="paginationContainer">
        <nav class="pagination-outer" aria-label="Page navigation">
            <ul class="pagination">
                <c:if test="${currentPage >= 2}">
                    <li class="page-item">
                        <a href="${contextPath}/catalog?page=${currentPage - 1}&size=${pageSize}"
                           class="page-link"
                           aria-label="Previous">
                            <span aria-hidden="true"><</span>
                        </a>
                    </li>
                </c:if>
                <c:choose>
                    <c:when test="${currentPage <= totalPaginatedVisiblePages / 2 + 1}">
                        <c:forEach begin="1" end="${lastPageNumber}" var="i">
                            <c:choose>
                                <c:when test="${i == currentPage}">
                                    <li class="page-item active"><a class="page-link"
                                                                    href="${contextPath}/catalog?page=${currentPage}&size=${pageSize}">${currentPage}</a>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li class="page-item"><a class="page-link"
                                                             href="${contextPath}/catalog?page=${i}&size=${pageSize}">${i}</a>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <c:forEach begin="${currentPage - totalPaginatedVisiblePages / 2}"
                                   end="${lastPageNumber}" var="j">
                            <c:choose>
                                <c:when test="${j == currentPage}">
                                    <li class="page-item active"><a class="page-link"
                                                                    href="${contextPath}/catalog?page=${currentPage}&size=${pageSize}">${currentPage}</a>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li class="page-item"><a class="page-link"
                                                             href="${contextPath}/catalog?page=${j}&size=${pageSize}">${j}</a>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
                <c:if test="${currentPage <= lastPageNumber - 1}">
                    <li class="page-item">
                        <a href="${contextPath}/catalog?page=${currentPage + 1}&size=${pageSize}"
                           class="page-link" aria-label="Next">
                            <span aria-hidden="true">></span>
                        </a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>
</div>
</body>
<jsp:include page="footer.jsp"/>
</html>