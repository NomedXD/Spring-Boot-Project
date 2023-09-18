<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<head>
    <title>Shop</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
    <link rel="stylesheet" href="${contextPath}/jsp-scc-styles/register.css">
</head>
<body>
<div class="login-dark">
    <form action="${contextPath}/registration" method="POST" class="js-form">
        <h2 class="sr-only">Login Form</h2>
        <div class="illustration"><i class="icon ion-ios-locked-outline"></i></div>
        <div class="form-group">
            <input class="form-control js-input-email" name="mail" placeholder="Enter email">
        </div>
        <div class="form-group">
            <input class="form-control js-input-name" name="name" placeholder="Enter name">
        </div>
        <div class="form-group">
            <input class="form-control js-input-surname" name="surname" placeholder="Enter surname">
        </div>
        <div class="form-group">
            <input class="form-control js-input-date" type="date" name ="date" placeholder="Input date">
        </div>
        <div class="form-group">
            <input class="form-control js-input-password" type="password" name="password" placeholder="Enter password">
        </div>
        <div class="form-group">
            <input class="form-control js-input-repeatPassword" name="repeatPasswordParam" placeholder="Confirm password">
        </div>
        <div class="form-group">
            <button class="btn btn-primary btn-block" type="submit">Submit and log in</button>
        </div>
        <c:if test="${not empty registrationErrorMessage}">
            <div class="form-group">
                <div class="bar error">${registrationErrorMessage}</div>
            </div>
        </c:if>
        <div class="form-group">
            <a class="btn btn-primary btn-block" href="${contextPath}/login">Login</a>
        </div>
    </form>
</div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/js/bootstrap.bundle.min.js"></script>
</body>
</html>
<script src="${contextPath}/jsp-scripts/script.js"></script>
