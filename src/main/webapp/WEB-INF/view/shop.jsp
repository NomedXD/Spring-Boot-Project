<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<head>
    <title>Shop</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${contextPath}/jsp-scc-styles/shop.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <link rel="stylesheet" href="${contextPath}/fontawesome/css/all.css">
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
            <c:forEach items="${categories}" var="item">
                <div class="content">
                    <img class="shopImg" src="${contextPath}/${item.image.path}" alt="${contextPath}/images/error-page.png">
                    <h3>${item.name}</h3>
                    <p>${item.sometext}</p>
                    <form action="${contextPath}/category/${item.id}" method="GET">
                        <button class="buy-1">Buy now</button>
                    </form>
                </div>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>