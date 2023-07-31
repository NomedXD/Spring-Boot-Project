<%@ page contentType="text/html;charset=UTF-8" %>
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
    <link href="${contextPath}/jsp-scc-styles/login.css" rel="stylesheet">
</head>
<body>
<div class="login-dark">
    <form action="${contextPath}/login" method="POST">
        <h2 class="sr-only">Login Form</h2>
        <div class="illustration"><i class="icon ion-ios-locked-outline"></i></div>
        <div class="form-group"><input class="form-control" name="mail" placeholder="Mail"></div>
        <div class="form-group"><input class="form-control" type="password" name="password" placeholder="Password">
        </div>
        <div class="form-group">
            <button class="btn btn-primary btn-block" type="submit">Log In</button>
        </div>
        <c:if test="${not empty loginErrorMessage}">
            <div class="form-group">
                <div class="bar error">${loginErrorMessage}</div>
            </div>
        </c:if>
        <div class="form-group">
            <a class="btn btn-primary btn-block" href="${contextPath}/registration">Register</a>
        </div>
        <a href="#" class="forgot">Forgot your email or password?</a>
    </form>
</div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/js/bootstrap.bundle.min.js"></script>
</body>

</html>
