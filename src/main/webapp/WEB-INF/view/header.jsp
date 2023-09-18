<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<header>
    <c:set var="contextPath" value="${pageContext.request.contextPath}"/>
    <link rel="stylesheet" href="${contextPath}/fontawesome/css/all.css">
    <script src="https://kit.fontawesome.com/4ac4b6b525.js" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js"></script>
    <div class="container-fluid">

        <div class="navb-logo">
            <img src="${contextPath}/images/logo.png" alt="Logo">
        </div>

        <div class="navb-items d-none d-xl-flex">

            <div class="item">
                <a class="btn btn-outline-dark" href="${contextPath}/catalog?page=1&size=5">Home</a>
            </div>

            <div class="item">
                <a class="btn btn-outline-dark" href="${contextPath}/cart">Cart
                    <c:choose>
                        <c:when test="${empty sessionScope.cart}"> 0</c:when>
                        <c:otherwise> ${sessionScope.cart.totalSize}</c:otherwise>
                    </c:choose>
                </a>

            </div>

            <div class="item">
                <a class="btn btn-outline-dark" href="${contextPath}/account?page=1&size=5">Account</a>
            </div>

            <div class="item">
                <a class="btn btn-outline-dark" href="${contextPath}/search?page=1&size=5">Search</a>
            </div>

            <div class="item">
                <a class="btn btn-outline-dark" href="${contextPath}/login" type="button"><i class="fa fa-sign-in"></i></a>
            </div>
            <sec:authorize access="isAuthenticated()">
                <div class="item">
                    <a class="btn btn-outline-dark" href="${contextPath}/logout" type="button"><i class="fa fa-sign-out"></i></a>
                </div>
            </sec:authorize>
        </div>

        <!-- Button trigger modal -->
        <div class="mobile-toggler d-xl-none">
            <a href="#" data-bs-toggle="modal" data-bs-target="#navbModal">
                <i class="fa-solid fa-bars"></i>
            </a>
        </div>

        <!-- Modal -->
        <div class="modal fade" id="navbModal" tabindex="-1" aria-labelledby="exampleModalLabel"
             aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">

                    <div class="modal-header">
                        <img src="${contextPath}/images/logo.png" alt="Logo">
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"><i
                                class="fa-solid fa-xmark"></i></button>
                    </div>

                    <div class="modal-body">

                        <div class="modal-line">
                            <i class="fa-solid fa-house"></i>
                            <a class="btn btn-outline-secondary" href="${contextPath}/catalog?page=1&size=5">Home</a>
                        </div>

                        <div class="modal-line">
                            <i class="fa-solid fa-bell-concierge"></i>
                            <a class="btn btn-outline-secondary" href="${contextPath}/cart">Cart
                                <c:choose>
                                    <c:when test="${empty sessionScope.cart}"> 0</c:when>
                                    <c:otherwise> ${sessionScope.cart.totalSize}</c:otherwise>
                                </c:choose>
                            </a>
                        </div>

                        <div class="modal-line">
                            <i class="fa-solid fa-file-lines"></i>
                            <a class="btn btn-outline-secondary" href="${contextPath}/account?page=1&size=5">Account</a>
                        </div>

                        <div class="modal-line">
                            <i class="fa-solid fa-circle-info"></i>
                            <a class="btn btn-outline-secondary" href="${contextPath}/search?page=1&size=5">Search</a>
                        </div>

                        <a href="#" class="navb-button" type="button">Sales</a>
                    </div>

                    <div class="mobile-modal-footer">

                        <a target="_blank" href="#"><i class="fa-brands fa-instagram"></i></a>
                        <a target="_blank" href="#"><i class="fa-brands fa-linkedin-in"></i></a>
                        <a target="_blank" href="#"><i class="fa-brands fa-youtube"></i></a>
                        <a target="_blank" href="#"><i class="fa-brands fa-facebook"></i></a>
                    </div>
                </div>
            </div>
        </div>

    </div>
</header>