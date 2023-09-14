<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<head>
    <title>Search</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="author" content="colorlib.com">
    <link href="https://fonts.googleapis.com/css?family=Roboto:400,700" rel="stylesheet"/>
    <link rel="stylesheet" href="${contextPath}/fontawesome/css/all.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css">
    <link href="${contextPath}/jsp-scc-styles/search.css" rel="stylesheet"/>
    <link href="${contextPath}/jsp-scc-styles/pagination.css" rel="stylesheet"/>
    <link rel="stylesheet" href="${contextPath}/jsp-scc-styles/header.css">
</head>
<body class="body">
<jsp:include page="header.jsp"/>
<div class="s007 container">
    <form action="${contextPath}/search?size=${pageSize}" method="POST">
        <div class="inner-form">
            <div class="basic-search">
                <div class="input-field">
                    <div class="icon-wrap">
                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20"
                             viewBox="0 0 20 20">
                            <path d="M18.869 19.162l-5.943-6.484c1.339-1.401 2.075-3.233 2.075-5.178 0-2.003-0.78-3.887-2.197-5.303s-3.3-2.197-5.303-2.197-3.887 0.78-5.303 2.197-2.197 3.3-2.197 5.303 0.78 3.887 2.197 5.303 3.3 2.197 5.303 2.197c1.726 0 3.362-0.579 4.688-1.645l5.943 6.483c0.099 0.108 0.233 0.162 0.369 0.162 0.121 0 0.242-0.043 0.338-0.131 0.204-0.187 0.217-0.503 0.031-0.706zM1 7.5c0-3.584 2.916-6.5 6.5-6.5s6.5 2.916 6.5 6.5-2.916 6.5-6.5 6.5-6.5-2.916-6.5-6.5z"></path>
                        </svg>
                    </div>
                    <input id="searchString" name="searchString" type="text" placeholder="Search..."/>
                    <div class="result-count">
                        <span>${totalSearchResults} </span>results
                    </div>
                </div>
            </div>
            <div class="advance-search">
                <span class="desc">Advanced Search</span>
                <div class="row">
                    <div class="input-field">
                        <div class="input-select">
                            <input placeholder="Price from" name="priceFrom" id="priceFrom"
                                   class="form-control border-success" style="color: #4ea63f" type="number">
                        </div>
                    </div>
                    <div class="input-field">
                        <div class="input-select">
                            <input placeholder="Price to" name="priceTo" id="priceTo"
                                   class="form-control border-success" style="color: #4ea63f" type="number">
                        </div>
                    </div>
                    <div class="input-field">
                        <div class="input-select">
                            <input placeholder="Category name" name="categoryName" id="categoryName"
                                   class="form-control border-success" style="color: #4ea63f" type="text"
                                   maxlength="15">
                        </div>
                    </div>
                </div>
                <div class="row second">
                </div>
                <div class="row third">
                    <div class="input-field">
                        <button class="btn-search" type="submit">Search</button>
                        <button class="btn-delete" type="submit" onclick="resetForm();">Delete</button>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
<div class="row p-2 rounded mt-2">
    <div class="dropdown">
        <button type="button" class="btn btn-dark dropdown-toggle float-end" data-bs-toggle="dropdown">
            Page size
        </button>
        <ul class="dropdown-menu">
            <c:choose>
                <c:when test="${pageSize == 5}">
                    <li><a class="dropdown-item disabled" href="${contextPath}/search?page=1&size=5">5 is current</a></li>
                </c:when>
                <c:otherwise><li><a class="dropdown-item" href="${contextPath}/search?page=1&size=5">5</a></li></c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${pageSize == 10}"><li disabled><a class="dropdown-item disabled" href="${contextPath}/search?page=1&size=10">10 is current</a></li></c:when>
                <c:otherwise><li><a class="dropdown-item" href="${contextPath}/search?page=1&size=10">10</a></li></c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${pageSize == 15}"><li disabled><a class="dropdown-item disabled" href="${contextPath}/search?page=1&size=15">15 is current</a></li></c:when>
                <c:otherwise><li><a class="dropdown-item" href="${contextPath}/search?page=1&size=15">15</a></li></c:otherwise>
            </c:choose>
        </ul>
    </div>
</div>
<div class="container">
    <c:forEach items="${products}" var="product">
        <div class="row p-2 bg-white border rounded mt-2">
            <div class="col-md-3 mt-1">
                <div id="carouselExampleControls${product.id}" class="carousel slide" data-bs-ride="carousel">
                    <div class="carousel-inner">
                        <div class="carousel-item active">
                            <img src="${contextPath}/${product.getPrimeImage().path}" class="d-block w-100" alt="...">
                        </div>
                        <c:forEach items="${product.getNonPrimeImages()}" var="nonPrimeImage">
                            <div class="carousel-item">
                                <img src="${contextPath}/${nonPrimeImage.path}" class="d-block w-100" alt="...">
                            </div>
                        </c:forEach>
                    </div>
                    <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleControls${product.id}"  data-bs-slide="prev">
                        <span style="filter: invert(100%)" class="carousel-control-prev-icon" aria-hidden="true"></span>
                    </button>
                    <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleControls${product.id}"  data-bs-slide="next">
                        <span style="filter: invert(100%)" class="carousel-control-next-icon" aria-hidden="true"></span>
                    </button>
                </div>
            </div>
            <div class="col-md-6 mt-1">
                <h5>${product.name}</h5>
                <div class="d-flex flex-row">
                    <div class="ratings mr-2"><i class="fa fa-star"></i><i class="fa fa-star"></i><i
                            class="fa fa-star"></i><i
                            class="fa fa-star"></i></div>
                    <span>310</span>
                </div>
                <p class="text-justify text-truncate para mb-0">${product.description}<br><br></p>
            </div>
            <div class="align-items-center align-content-center col-md-3 border-left mt-1">
                <div class="d-flex flex-row align-items-center">
                    <h4 class="mr-1">${product.price}$</h4>
                </div>
                <h6 class="text-success">Available</h6>
                <div class="d-flex flex-column mt-4">
                    <a class="btn btn-dark btn-sm" type="button" href="${contextPath}/product/${product.id}">More info</a>
                    <button class="btn btn-outline-dark btn-sm mt-2" type="button">Add to wishlist</button>
                </div>
            </div>
        </div>
    </c:forEach>
</div>
<div class="container">
    <div class="paginationContainer">
        <nav class="pagination-outer" aria-label="Page navigation">
            <ul class="pagination">
                <c:if test="${currentPage >= 2}">
                    <li class="page-item">
                        <a href="${contextPath}/search?page=${currentPage - 1}&size=${pageSize}" class="page-link" aria-label="Previous">
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
                                                                    href="${contextPath}/search?page=${currentPage}&size=${pageSize}">${currentPage}</a>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li class="page-item"><a class="page-link"
                                                             href="${contextPath}/search?page=${i}&size=${pageSize}">${i}</a></li>
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
                                                                    href="${contextPath}/search?page=${currentPage}&size=${pageSize}">${currentPage}</a>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li class="page-item"><a class="page-link"
                                                             href="${contextPath}/search?page=${j}&size=${pageSize}">${j}</a></li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
                <c:if test="${currentPage <= lastPageNumber - 1}">
                    <li class="page-item">
                        <a href="${contextPath}/search?page=${currentPage + 1}&size=${pageSize}" class="page-link" aria-label="Next">
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
<script rel="stylesheet" src="${contextPath}/jsp-scripts/search-form-script.js"></script>
</html>
