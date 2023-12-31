<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<body class="bg-purple">
<link rel="stylesheet" href="${contextPath}/jsp-scc-styles/error.css">
<div class="stars">
  <div class="custom-navbar">
  </div>
  <div class="central-body">
    <img class="image-404" src="http://salehriaz.com/404Page/img/404.svg" width="300px">
    <c:if test="${not empty errorMessage}">
      <a class="btn-go-home">${errorMessage}</a>
    </c:if>
    <a href="${contextPath}/catalog" class="btn-go-home" target="_blank">GO BACK HOME</a>
  </div>
  <div class="objects">
    <img class="object_rocket" src="${contextPath}/images/snLogo.svg" width="40px">
    <div class="earth-moon">
      <img class="object_earth" src="http://salehriaz.com/404Page/img/earth.svg" width="100px">
      <img class="object_moon" src="http://salehriaz.com/404Page/img/moon.svg" width="80px">
    </div>
    <div class="box_astronaut">
      <img class="object_astronaut" src="http://salehriaz.com/404Page/img/astronaut.svg" width="140px">
    </div>
  </div>
  <div class="glowing_stars">
    <div class="star"></div>
    <div class="star"></div>
    <div class="star"></div>
    <div class="star"></div>
    <div class="star"></div>

  </div>

</div>

</body>