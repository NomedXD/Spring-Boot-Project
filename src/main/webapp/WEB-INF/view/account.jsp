<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<sec:authentication var="user" property="principal.user"/>
<head>
    <title>Account</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${contextPath}/jsp-scc-styles/account.css">
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="${contextPath}/fontawesome/css/all.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="${contextPath}/jsp-scc-styles/header.css">
    <link href="${contextPath}/jsp-scc-styles/pagination.css" rel="stylesheet"/>
</head>
<body class="body">
<jsp:include page="header.jsp"/>
<!--User account info below-->
<div class="container">
    <div class="main-body" style="padding-top:10px">
        <div class="row gutters-sm">
            <div class="col-md-4 mb-3">
                <div class="cardUpper">
                    <div class="card-body">
                        <div class="d-flex flex-column align-items-center text-center">
                            <img src="https://bootdey.com/img/Content/avatar/avatar7.png" alt="Admin"
                                 class="rounded-circle" width="150">
                            <div class="mt-3">
                                <h4>${user.name}</h4>
                                <p class="text-secondary mb-1">Full Stack Developer</p>
                                <p class="text-muted font-size-sm">${user.street}, ${user.accommodationNumber}, ${user.flatNumber}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-8">
                <div class="card mb-3">
                    <div class="card-body">
                        <div class="row">
                            <div class="col-sm-3">
                                <h6 class="mb-0">Full Name</h6>
                            </div>
                            <div class="col-sm-9 text-secondary">
                                ${user.name} ${user.surname}
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3">
                                <h6 class="mb-0">Email</h6>
                            </div>
                            <div class="col-sm-9 text-secondary">
                                ${user.mail}
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3">
                                <h6 class="mb-0">Birthday</h6>
                            </div>
                            <div class="col-sm-9 text-secondary">
                                ${user.date}
                            </div>
                        </div>
                        <hr>
                        <form action="${contextPath}/account/update?page=${currentPage}&size=${pageSize}" method="POST"
                              id="updateUser">
                            <div class="row">
                                <div class="col-sm-3">
                                    <h6 class="mb-0">Mobile</h6>
                                </div>
                                <div class="col-sm-9 text-secondary">
                                    ${user.mobile}
                                    <div class="d-flex">
                                        <div class="d-inline-block">
                                            <input type="text" name="mobile" class="form-control">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col-sm-3">
                                    <h6 class="mb-0">Address: Street</h6>
                                </div>
                                <div class="col-sm-9 text-secondary">
                                    ${user.street}
                                    <div class="d-flex">
                                        <div class="d-inline-block">
                                            <input type="text" name="street" class="form-control">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col-sm-3">
                                    <h6 class="mb-0">Address: Accommodation number</h6>
                                </div>
                                <div class="col-sm-9 text-secondary">
                                    ${user.accommodationNumber}
                                    <div class="d-flex">
                                        <div class="d-inline-block">
                                            <input type="text" name="accommodationNumber" class="form-control">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col-sm-3">
                                    <h6 class="mb-0">Address: Flat number</h6>
                                </div>
                                <div class="col-sm-9 text-secondary">
                                    ${user.flatNumber}
                                    <div class="d-flex">
                                        <div class="d-inline-block">
                                            <input type="text" name="flatNumber" class="form-control">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>
                        <hr>
                        <div class="row">
                            <div class="col-sm-12">
                                <button class="btn btn-info " type="submit" form="updateUser">Edit</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!--Orders details below-->
<div class="container-fluid">
    <c:if test="${not empty orders}">
        <!--Export/import-->
        <div class="d-flex justify-content-between align-items-center py-3">
            <sec:authorize access="hasAuthority('USER')">
                <a href="${contextPath}/account/export" class="btn btn-dark btn-block btn-lg"
                   data-mdb-ripple-color="dark">Export
                    orders</a>
                <c:if test="${not empty eiMessage}">${eiMessage}</c:if>
            </sec:authorize>
            <sec:authorize access="hasAuthority('ADMIN')">
                <form action="${contextPath}/account/import" method="POST" enctype="multipart/form-data">
                    <input type="file" class="custom-file-input" name="file"
                           aria-describedby="inputGroupFileAddon01" required>
                    <button type="submit" class="btn btn-dark btn-block btn-lg" data-mdb-ripple-color="dark">Import
                        orders
                    </button>
                </form>
            </sec:authorize>
        </div>
        <div class="row p-2 rounded mt-2">
            <div class="dropdown">
                <button type="button" class="btn btn-dark dropdown-toggle float-end" data-bs-toggle="dropdown">
                    Page size
                </button>
                <ul class="dropdown-menu">
                    <c:choose>
                        <c:when test="${pageSize == 5}">
                            <li><a class="dropdown-item disabled" href="${contextPath}/account?page=1&size=5">5 is
                                current</a></li>
                        </c:when>
                        <c:otherwise>
                            <li><a class="dropdown-item" href="${contextPath}/account?page=1&size=5">5</a></li>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${pageSize == 10}">
                            <li disabled><a class="dropdown-item disabled" href="${contextPath}/account?page=1&size=10">10
                                is current</a></li>
                        </c:when>
                        <c:otherwise>
                            <li><a class="dropdown-item" href="${contextPath}/account?page=1&size=10">10</a></li>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${pageSize == 15}">
                            <li disabled><a class="dropdown-item disabled" href="${contextPath}/account?page=1&size=15">15
                                is current</a></li>
                        </c:when>
                        <c:otherwise>
                            <li><a class="dropdown-item" href="${contextPath}/account?page=1&size=15">15</a></li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </div>
        <c:forEach items="${orders}" var="order">
            <div class="container">
                <!-- Title -->
                <div class="d-flex justify-content-between align-items-center py-3">
                    <h2 class="h5 mb-0"><a href="#" class="text-muted"></a> Order #${order.id}</h2>
                </div>
                <!-- Main content -->
                <div class="row">
                    <div class="col-lg-8">
                        <!-- Details -->
                        <div class="card mb-4">
                            <div class="card-body">
                                <div class="mb-3 d-flex justify-content-between">
                                    <div>
                                        <span class="me-3">${order.date}</span>
                                        <span class="me-3">#${order.id}</span>
                                        <span class="me-3">Visa ${order.creditCardNumber}</span>
                                        <span class="badge rounded-pill bg-info">SHIPPING</span>
                                    </div>
                                    <div class="d-flex">
                                        <button class="btn btn-link p-0 me-3 d-none d-lg-block btn-icon-text"><i
                                                class="bi bi-download"></i> <span class="text">Invoice</span></button>
                                        <div class="dropdown">
                                            <button class="btn btn-link p-0 text-muted" type="button"
                                                    data-bs-toggle="dropdown">
                                                <i class="bi bi-three-dots-vertical"></i>
                                            </button>
                                            <ul class="dropdown-menu dropdown-menu-end">
                                                <li><a class="dropdown-item" href="#"><i class="bi bi-pencil"></i> Edit</a>
                                                </li>
                                                <li><a class="dropdown-item" href="#"><i class="bi bi-printer"></i>
                                                    Print</a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                                <table class="table table-borderless">
                                    <tbody>
                                    <c:forEach items="${order.productList}" var="product">
                                        <tr>
                                            <td>
                                                <div class="d-flex mb-2">
                                                    <div class="flex-shrink-0">
                                                        <img src="${contextPath}/${product.getPrimeImage().path}" alt=""
                                                             width="35" class="img-fluid">
                                                    </div>
                                                    <div class="flex-lg-grow-1 ms-3">
                                                        <h6 class="small mb-0">
                                                            <a href="${contextPath}/product/${product.id}"
                                                               class="text-reset">
                                                                    ${product.name}
                                                            </a>
                                                        </h6>
                                                        <span class="small">${product.category.name}</span>
                                                    </div>
                                                </div>
                                            </td>
                                            <td>
                                                <c:forEach items="${order.orderDetails}" var="details">
                                                    <c:if test="${details.productId == product.id}">${details.productQuantity}</c:if>
                                                </c:forEach>
                                            </td>
                                            <td class="text-end">${product.price}</td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                    <tfoot>
                                    <tr>
                                        <td colspan="2">Subtotal</td>
                                        <td class="text-end">$${order.price}</td>
                                    </tr>
                                    <tr>
                                        <td colspan="2">Shipping</td>
                                        <td class="text-end">$${order.shippingCost}</td>
                                    </tr>
                                    <tr>
                                        <c:choose>
                                            <c:when test="${not empty order.discountCode}">
                                                <c:set var="codeName" value="${order.discountCode.name}"/>
                                                <c:set var="codeDiscount" value="${order.discountCode.discount}"/>
                                                <c:set var="totalPrice" value="${order.price - order.discountCode.discount - order.shippingCost}"/>
                                            </c:when>
                                            <c:otherwise>
                                                <c:set var="codeName" value="-"/>
                                                <c:set var="codeDiscount" value="${0}"/>
                                                <c:set var="totalPrice" value="${order.price - order.shippingCost}"/>
                                            </c:otherwise>
                                        </c:choose>
                                        <td colspan="2">Discount (Code: ${codeName} )</td>
                                        <td class="text-danger text-end">-$${codeDiscount}</td>
                                    </tr>
                                    <tr class="fw-bold">
                                        <td colspan="2">TOTAL</td>
                                        <td class="text-end">$${totalPrice}</td>
                                    </tr>
                                    </tfoot>
                                </table>
                            </div>
                        </div>
                        <!-- Payment -->
                        <div class="card mb-4">
                            <div class="card-body">
                                <div class="row">
                                    <div class="col-lg-6">
                                        <h3 class="h6">Payment Method</h3>
                                        <p>Visa ${order.creditCardNumber}<br>
                                            Total: ${order.price} <span
                                                    class="badge bg-success rounded-pill">PAID</span></p>
                                    </div>
                                    <div class="col-lg-6">
                                        <h3 class="h6">Billing address</h3>
                                        <address>
                                            <strong>${order.user.name} ${order.user.surname}</strong><br>
                                            <c:choose>
                                                <c:when test="${empty order.address}">-</c:when>
                                                <c:otherwise>${order.address}</c:otherwise>
                                            </c:choose><br>
                                            <abbr title="Phone">P:</abbr> ${order.user.mobile}
                                        </address>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-4">
                        <!-- Customer Notes -->
                        <div class="card mb-4">
                            <div class="card-body">
                                <h3 class="h6">Customer Notes</h3>
                                <p>${order.customerNotes}</p>
                            </div>
                        </div>
                        <div class="card mb-4">
                            <!-- Shipping information -->
                            <div class="card-body">
                                <h3 class="h6">Shipping Information</h3>
                                <hr>
                                <h3 class="h6">Address</h3>
                                <address>
                                    <strong>${order.user.name} ${order.user.surname}</strong><br>
                                    <c:choose>
                                        <c:when test="${empty order.address}">${order.shippingType}</c:when>
                                        <c:otherwise>${order.shippingType}<br>${order.address}</c:otherwise>
                                    </c:choose><br>
                                    <abbr title="Phone">P:</abbr> ${order.user.mobile}
                                </address>
                            </div>
                        </div>
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
                                <a href="${contextPath}/account?page=${currentPage - 1}&size=${pageSize}"
                                   class="page-link"
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
                                                                            href="${contextPath}/account?page=${currentPage}&size=${pageSize}">${currentPage}</a>
                                            </li>
                                        </c:when>
                                        <c:otherwise>
                                            <li class="page-item"><a class="page-link"
                                                                     href="${contextPath}/account?page=${i}&size=${pageSize}">${i}</a>
                                            </li>
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
                                                                            href="${contextPath}/account?page=${currentPage}&size=${pageSize}">${currentPage}</a>
                                            </li>
                                        </c:when>
                                        <c:otherwise>
                                            <li class="page-item"><a class="page-link"
                                                                     href="${contextPath}/account?page=${j}&size=${pageSize}">${j}</a>
                                            </li>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                        <c:if test="${currentPage <= lastPageNumber - 1}">
                            <li class="page-item">
                                <a href="${contextPath}/account?page=${currentPage + 1}&size=${pageSize}"
                                   class="page-link" aria-label="Next">
                                    <span aria-hidden="true">></span>
                                </a>
                            </li>
                        </c:if>
                    </ul>
                </nav>
            </div>
        </div>
    </c:if>
</div>
</body>
<jsp:include page="footer.jsp"/>
</html>
