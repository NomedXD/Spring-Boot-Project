<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<head>
    <title>Category</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${contextPath}/jsp-scc-styles/category.css">
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="${contextPath}/fontawesome/css/all.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="${contextPath}/jsp-scc-styles/header.css">
    <link href="${contextPath}/jsp-scc-styles/pagination.css" rel="stylesheet"/>
</head>
<body class="body">
<jsp:include page="header.jsp"/>
<c:choose>
    <c:when test="${products.size() == 0}">
        <div class="bar warn">No products found. Try later</div>
    </c:when>
    <c:otherwise>
        <sec:authorize access="hasAuthority('ADMIN')">
            <!--Export/import-->
            <div class="container-fluid mt-2">
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
        </sec:authorize>
        <div class="row p-2 rounded mt-2">
            <div class="dropdown">
                <button type="button" class="btn btn-dark dropdown-toggle float-end" data-bs-toggle="dropdown">
                    Page size
                </button>
                <ul class="dropdown-menu">
                    <c:choose>
                        <c:when test="${pageSize == 5}">
                            <li><a class="dropdown-item disabled" href="${contextPath}/category/${products[0].category.id}?page=1&size=5">5 is current</a></li>
                        </c:when>
                        <c:otherwise><li><a class="dropdown-item" href="${contextPath}/category/${products[0].category.id}?page=1&size=5">5</a></li></c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${pageSize == 10}"><li disabled><a class="dropdown-item disabled" href="${contextPath}/category/${products[0].category.id}/?page=1&size=10">10 is current</a></li></c:when>
                        <c:otherwise><li><a class="dropdown-item" href="${contextPath}/category/${products[0].category.id}?page=1&size=10">10</a></li></c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${pageSize == 15}"><li disabled><a class="dropdown-item disabled" href="${contextPath}/category/${products[0].category.id}?page=1&size=15">15 is current</a></li></c:when>
                        <c:otherwise><li><a class="dropdown-item" href="${contextPath}/category/${products[0].category.id}?page=1&size=15">15</a></li></c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </div>
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
                        <a class="btn btn-dark btn-sm" type="button"
                           href="${pageContext.request.contextPath}/product/${product.id}">More info</a>
                        <button class="btn btn-outline-dark btn-sm mt-2" type="button">Add to wishlist</button>
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
                                <a href="${contextPath}/category/${products[0].category.id}?page=${currentPage - 1}&size=${pageSize}" class="page-link"
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
                                                                            href="${contextPath}/category/${products[0].category.id}?page=${currentPage}&size=${pageSize}">${currentPage}</a>
                                            </li>
                                        </c:when>
                                        <c:otherwise>
                                            <li class="page-item"><a class="page-link"
                                                                     href="${contextPath}/category/${products[0].category.id}?page=${i}&size=${pageSize}">${i}</a></li>
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
                                                                            href="${contextPath}/category/${products[0].category.id}?page=${currentPage}&size=${pageSize}">${currentPage}</a>
                                            </li>
                                        </c:when>
                                        <c:otherwise>
                                            <li class="page-item"><a class="page-link"
                                                                     href="${contextPath}/category/${products[0].category.id}?page=${j}&size=${pageSize}">${j}</a></li>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                        <c:if test="${currentPage <= lastPageNumber - 1}">
                            <li class="page-item">
                                <a href="${contextPath}/category/${products[0].category.id}?page=${currentPage + 1}&size=${pageSize}" class="page-link" aria-label="Next">
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
<jsp:include page="footer.jsp"/>
</html>
