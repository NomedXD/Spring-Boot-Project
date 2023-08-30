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
            <a href="${contextPath}/category/export/${products[0].category.id}" class="btn btn-dark btn-lg" data-mdb-ripple-color="dark">Export products</a>
            <c:if test="${not empty eiMessage}">${eiMessage}</c:if>
            <form action="${contextPath}/category/import/${products[0].category.id}" method="POST" enctype="multipart/form-data" id="importProducts">
                <input type="file" class="file-input" name="file"
                       aria-describedby="inputGroupFileAddon01" required>
            </form>
            <button type="submit" class="btn btn-dark btn-lg" data-mdb-ripple-color="dark" form="importProducts">Import products</button>
        </div>
        <c:forEach items="${products}" var="product">
            <div class="row p-2 bg-white border rounded mt-2">
                <div class="col-md-3 mt-1"><img class="img-fluid img-responsive rounded product-image"
                                                src="${contextPath}/${product.image.path}"></div>
                <div class="col-md-6 mt-1">
                    <h5>${product.name}</h5>
                    <div class="d-flex flex-row">
                        <div class="ratings mr-2"><i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i><i
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
                        <a class="btn btn-primary btn-sm" type="button" href="${pageContext.request.contextPath}/product/${product.id}">More info</a>
                        <button class="btn btn-outline-primary btn-sm mt-2" type="button">Add to wishlist</button>
                    </div>
                </div>
            </div>
        </c:forEach>
    </c:otherwise>
</c:choose>
</body>
</html>
