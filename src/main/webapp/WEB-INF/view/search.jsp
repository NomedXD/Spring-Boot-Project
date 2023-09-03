<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous"></script>
    <link rel="stylesheet" href="${contextPath}/fontawesome/css/all.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link href="${contextPath}/jsp-scc-styles/search.css" rel="stylesheet"/>
    <link rel="stylesheet" href="${contextPath}/jsp-scc-styles/header.css">
</head>
<body class="body">
<jsp:include page="header.jsp"/>
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
            <div class="advance-search">
                <span class="desc">Advanced Search</span>
                <div class="row">
                    <div class="input-field">
                        <div class="input-select">
                            <select data-trigger="" name="choices-single-defaul">
                                <option placeholder="" value="">ACCESSORIES</option>
                                <option>ACCESSORIES</option>
                                <option>SUBJECT B</option>
                                <option>SUBJECT C</option>
                            </select>
                        </div>
                    </div>
                    <div class="input-field">
                        <div class="input-select">
                            <select data-trigger="" name="choices-single-defaul">
                                <option placeholder="" value="">COLOR</option>
                                <option>GREEN</option>
                                <option>SUBJECT B</option>
                                <option>SUBJECT C</option>
                            </select>
                        </div>
                    </div>
                    <div class="input-field">
                        <div class="input-select">
                            <select data-trigger="" name="choices-single-defaul">
                                <option placeholder="" value="">SIZE</option>
                                <option>SIZE</option>
                                <option>SUBJECT B</option>
                                <option>SUBJECT C</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="row second">
                    <div class="input-field">
                        <div class="input-select">
                            <select data-trigger="" name="choices-single-defaul">
                                <option placeholder="" value="">SALE</option>
                                <option>SALE</option>
                                <option>SUBJECT B</option>
                                <option>SUBJECT C</option>
                            </select>
                        </div>
                    </div>
                    <div class="input-field">
                        <div class="input-select">
                            <select data-trigger="" name="choices-single-defaul">
                                <option placeholder="" value="">TIME</option>
                                <option>THIS WEEK</option>
                                <option>SUBJECT B</option>
                                <option>SUBJECT C</option>
                            </select>
                        </div>
                    </div>
                    <div class="input-field">
                        <div class="input-select">
                            <select data-trigger="" name="choices-single-defaul">
                                <option placeholder="" value="">TYPE</option>
                                <option>TYPE</option>
                                <option>SUBJECT B</option>
                                <option>SUBJECT C</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="row third">
                    <div class="input-field">
                        <button class="btn-search" type="submit">Search</button>
                        <button class="btn-delete" id="delete">Delete</button>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
<div class="container">
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
                    <a class="btn btn-primary btn-sm" type="button" href="${contextPath}/product/${product.id}">More
                        info</a>
                    <button class="btn btn-outline-primary btn-sm mt-2" type="button">Add to wishlist</button>
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
<script src="${contextPath}/jsp-scripts/choices.js"></script>
<script>
    const customSelects = document.querySelectorAll("select");
    const deleteBtn = document.getElementById('delete')
    const choices = new Choices('select',
        {
            searchEnabled: false,
            removeItemButton: true,
            itemSelectText: '',
        });
    for (let i = 0; i < customSelects.length; i++) {
        -
            customSelects[i].addEventListener('addItem', function (event) {
                if (event.detail.value) {
                    let parent = this.parentNode.parentNode
                    parent.classList.add('valid')
                    parent.classList.remove('invalid')
                } else {
                    let parent = this.parentNode.parentNode
                    parent.classList.add('invalid')
                    parent.classList.remove('valid')
                }
            }, false);
    }
    deleteBtn.addEventListener("click", function (e) {
        e.preventDefault()
        const deleteAll = document.querySelectorAll('.choices__button')
        for (let i = 0; i < deleteAll.length; i++) {
            deleteAll[i].click();
        }
    });
</script>
</body>
</html>
