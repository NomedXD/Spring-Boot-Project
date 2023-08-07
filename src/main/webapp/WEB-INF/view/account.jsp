<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<head>
    <title>Account</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${contextPath}/jsp-scc-styles/account.css">

    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="${contextPath}/fontawesome/css/all.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="${contextPath}/jsp-scc-styles/header.css">
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
                                <h4>${sessionScope.user.name}</h4>
                                <p class="text-secondary mb-1">Full Stack Developer</p>
                                <p class="text-muted font-size-sm">${sessionScope.user.street}, ${sessionScope.user.accommodationNumber}, ${sessionScope.user.flatNumber}</p>
                                <button class="btn btn-primary">Follow</button>
                                <button class="btn btn-outline-primary">Message</button>
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
                                ${sessionScope.user.name} ${sessionScope.user.surname}
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3">
                                <h6 class="mb-0">Email</h6>
                            </div>
                            <div class="col-sm-9 text-secondary">
                                ${sessionScope.user.mail}
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3">
                                <h6 class="mb-0">Birthday</h6>
                            </div>
                            <div class="col-sm-9 text-secondary">
                                ${sessionScope.user.date}
                            </div>
                        </div>
                        <hr>
                        <form action="${contextPath}/account/update" method="POST" id="updateUser">
                            <div class="row">
                                <div class="col-sm-3">
                                    <h6 class="mb-0">Mobile</h6>
                                </div>
                                <div class="col-sm-9 text-secondary">
                                    ${sessionScope.user.mobile}
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
                                    ${sessionScope.user.street}
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
                                    ${sessionScope.user.accommodationNumber}
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
                                    ${sessionScope.user.flatNumber}
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
    <c:forEach items="${sessionScope.user.orders}" var="order">
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
                                                    <img src="${contextPath}/${product.image.path}" alt=""
                                                         width="35" class="img-fluid">
                                                </div>
                                                <div class="flex-lg-grow-1 ms-3">
                                                    <h6 class="small mb-0">
                                                        <a href="${contextPath}/product/${product.id}" class="text-reset">
                                                            ${product.name}
                                                        </a>
                                                    </h6>
                                                    <span class="small">${product.category}</span>
                                                </div>
                                            </div>
                                        </td>
                                        <td>1</td>
                                        <td class="text-end">${product.price}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                                <tfoot>
                                <tr>
                                    <td colspan="2">Subtotal</td>
                                    <td class="text-end">-</td>
                                </tr>
                                <tr>
                                    <td colspan="2">Shipping</td>
                                    <td class="text-end">${order.shippingCost}</td>
                                </tr>
                                <tr>
                                    <td colspan="2">Discount (Code: ${order.code})</td>
                                    <td class="text-danger text-end">-$10.00</td>
                                </tr>
                                <tr class="fw-bold">
                                    <td colspan="2">TOTAL</td>
                                    <td class="text-end">${order.price}</td>
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
                                        Total: ${order.price} <span class="badge bg-success rounded-pill">PAID</span></p>
                                </div>
                                <div class="col-lg-6">
                                    <h3 class="h6">Billing address</h3>
                                    <address>
                                        <strong>${order.user.name} ${order.user.surname}</strong><br>
                                        1355 Market St, Suite 900<br>
                                        San Francisco, CA 94103<br>
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
                                1355 Market St, Suite 900<br>
                                San Francisco, CA 94103<br>
                                <abbr title="Phone">P:</abbr> ${order.user.mobile}
                            </address>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>


</div>
</body>
</html>
