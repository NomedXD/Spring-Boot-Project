<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<head>
    <title>Category</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${contextPath}/jsp-scc-styles/category.css">
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="${contextPath}/fontawesome/css/all.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="${contextPath}/jsp-scc-styles/header.css">
    <link href="${contextPath}/jsp-scc-styles/search.css" rel="stylesheet"/>
</head>
<body class="body">
<jsp:include page="header.jsp"/>
<c:choose>
    <c:when test="${products.size() == 0}">
        <div class="bar warn">No products found. Try later</div>
    </c:when>
    <c:otherwise>
        <!--Export/import-->
        <div class="container-fluid">
            <a href="${contextPath}/category/export/${products[0].category.id}" class="btn btn-dark btn-lg"
               data-mdb-ripple-color="dark">Export products</a>
            <c:if test="${not empty eiMessage}">${eiMessage}</c:if>
            <form action="${contextPath}/category/import/${products[0].category.id}" method="POST"
                  enctype="multipart/form-data" id="importProducts">
                <input type="file" class="file-input" name="file"
                       aria-describedby="inputGroupFileAddon01" required>
            </form>
            <button type="submit" class="btn btn-dark btn-lg" data-mdb-ripple-color="dark" form="importProducts">Import
                products
            </button>
        </div>
        <div class="s007 container">
            <form action="${contextPath}/search" method="POST">
                <div class="inner-form">
                    <div class="basic-search">
                        <div class="input-field">
                            <div class="icon-wrap">
                                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20"
                                     viewBox="0 0 20 20">
                                    <path d="M18.869 19.162l-5.943-6.484c1.339-1.401 2.075-3.233 2.075-5.178 0-2.003-0.78-3.887-2.197-5.303s-3.3-2.197-5.303-2.197-3.887 0.78-5.303 2.197-2.197 3.3-2.197 5.303 0.78 3.887 2.197 5.303 3.3 2.197 5.303 2.197c1.726 0 3.362-0.579 4.688-1.645l5.943 6.483c0.099 0.108 0.233 0.162 0.369 0.162 0.121 0 0.242-0.043 0.338-0.131 0.204-0.187 0.217-0.503 0.031-0.706zM1 7.5c0-3.584 2.916-6.5 6.5-6.5s6.5 2.916 6.5 6.5-2.916 6.5-6.5 6.5-6.5-2.916-6.5-6.5z"></path>
                                </svg>
                            </div>
                            <input id="search" name="searchString" type="text" placeholder="Search..."/>
                            <div class="result-count">
                                <span>${totalSearchResults} </span>results
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
        <c:forEach items="${products}" var="product">
            <div class="row p-2 bg-white border rounded mt-2">
                <div class="col-md-3 mt-1"><img class="img-fluid img-responsive rounded product-image"
                                                src="${contextPath}/${product.image.path}"></div>
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
                        <a class="btn btn-primary btn-sm" type="button"
                           href="${pageContext.request.contextPath}/product/${product.id}">More info</a>
                        <button class="btn btn-outline-primary btn-sm mt-2" type="button">Add to wishlist</button>
                    </div>
                </div>
            </div>
        </c:forEach>
        <div class="container">
            <div class="paginationContainer">
                <nav class="pagination-outer" aria-label="Page navigation">
                    <ul class="pagination">
                        <c:if test="${currentPage >= 2}">
                            <li class="page-item">
                                <a href="${contextPath}/search/${currentPage - 1}" class="page-link" aria-label="Previous">
                                    <span aria-hidden="true"><</span>
                                </a>
                            </li>
                        </c:if>
                        <c:choose>
                            <c:when test="${currentPage <= totalPaginatedVisiblePages / 2 + 1}">
                                <c:forEach begin="1" end="${lastPageNumber}" var="i">
                                    <c:choose>
                                        <c:when test="${i == currentPage}">
                                            <li class="page-item active"><a class="page-link" href="${contextPath}/search/${currentPage}">${currentPage}</a></li>
                                        </c:when>
                                        <c:otherwise>
                                            <li class="page-item"><a class="page-link" href="${contextPath}/search/${i}">${i}</a></li>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <c:forEach begin="${currentPage - totalPaginatedVisiblePages / 2}"
                                           end="${lastPageNumber}" var="j">
                                    <c:choose>
                                        <c:when test="${j == currentPage}">
                                            <li class="page-item active"><a class="page-link" href="${contextPath}/search/${currentPage}">${currentPage}</a></li>
                                        </c:when>
                                        <c:otherwise>
                                            <li class="page-item"><a class="page-link" href="${contextPath}/search/${j}">${j}</a></li>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                        <c:if test="${currentPage <= lastPageNumber - 1}">
                            <li class="page-item">
                                <a href="${contextPath}/search/${currentPage + 1}" class="page-link" aria-label="Next">
                                    <span aria-hidden="true">></span>
                                </a>
                            </li>
                        </c:if>
                    </ul>
                </nav>
            </div>
        </div>
    </c:otherwise>
</c:choose>
</body>
</html>
