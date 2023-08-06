<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<head>
    <title>Category</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${contextPath}/jsp-scc-styles/cart.css">

    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="${contextPath}/fontawesome/css/all.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css">
    <link href="https://fonts.googleapis.com/css?family=Roboto:400,500,700" rel="stylesheet">
    <link rel="stylesheet" href="${contextPath}/jsp-scc-styles/header.css">
    <link href="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js">
</head>
<body class="body">
<jsp:include page="header.jsp"/>

<section class="h-100 h-custom">
    <div class="container py-5 h-100">
        <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col-12">
                <div class="card card-registration card-registration-2" style="border-radius: 15px;">
                    <div class="card-body p-0">
                        <div class="row g-0">
                            <div class="col-lg-8">
                                <div class="p-5">
                                    <div class="d-flex justify-content-between align-items-center mb-5">
                                        <h1 class="fw-bold mb-0 text-black">Shopping Cart</h1>
                                        <h6 class="mb-0 text-muted">3 items</h6>
                                    </div>
                                    <hr class="my-4">
                                    <c:forEach items="${sessionScope.cart.products}" var="cartProduct">
                                        <div class="row mb-4 d-flex justify-content-between align-items-center">
                                            <div class="col-md-2 col-lg-2 col-xl-2">
                                                <img src="${contextPath}/${cartProduct.image.path}"
                                                        class="img-fluid rounded-3" alt="Cotton T-shirt">
                                            </div>
                                            <div class="col-md-3 col-lg-3 col-xl-3">
                                                <h6 class="text-muted">${cartProduct.name}</h6>
                                                <h6 class="text-black mb-0">Cotton T-shirt</h6>
                                            </div>
                                            <div class="col-md-3 col-lg-3 col-xl-2 d-flex">
                                                <button class="btn btn-link px-2"
                                                        onclick="this.parentNode.querySelector('input[type=number]').stepDown(); calculateTotal();">
                                                    <i class="fas fa-minus"></i>
                                                </button>

                                                <input id="form1" min="0" name="quantity" value="1" type="number"
                                                       class="form-control form-control-sm productCount" />

                                                <button class="btn btn-link px-2"
                                                        onclick="this.parentNode.querySelector('input[type=number]').stepUp(); calculateTotal();">
                                                    <i class="fas fa-plus"></i>
                                                </button>
                                            </div>
                                            <div class="col-md-3 col-lg-2 col-xl-2 offset-lg-1">
                                                <h6 class="mb-0 singlePrice">${cartProduct.price}</h6>
                                            </div>
                                            <div class="col-md-1 col-lg-1 col-xl-1 text-end">
                                                <a href="${contextPath}/cart/remove/${cartProduct.id}" class="text-muted">
                                                    <i class="fas fa-times"></i>
                                                </a>
                                            </div>
                                        </div>
                                    </c:forEach>
                                    <hr class="my-4">
                                    <div class="pt-5">
                                        <h6 class="mb-0"><a href="#" class="text-body"><i class="fas fa-long-arrow-alt-left me-2"></i>Back to shop</a></h6>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-4 bg-grey">
                                <div class="p-5">
                                    <h3 class="fw-bold mb-5 mt-2 pt-1">Summary</h3>
                                    <hr class="my-4">
                                    <div class="d-flex justify-content-between mb-4">
                                        <h5 class="text-uppercase">items 3</h5>
                                        <h5>€ 132.00</h5>
                                    </div>
                                    <form action="${contextPath}/checkout" method="POST">
                                        <h5 class="text-uppercase mb-3">Shipping</h5>
                                        <div class="mb-4 pb-2">
                                            <select class="selectPicker" data-size="4">
                                                <option value="shipping_city">shipping_city</option>
                                                <option value="shipping_postcode">shipping_postcode</option>
                                                <option value="shipping_country">shipping_country</option>
                                            </select>
                                        </div>
                                        <h5 class="text-uppercase mb-3">Give code</h5>
                                        <div class="mb-5">
                                            <div class="form-outline">
                                                <input type="text" id="form3Examplea2" class="form-control form-control-lg" />
                                                <label class="form-label" for="form3Examplea2">Enter your code</label>
                                            </div>
                                        </div>
                                        <hr class="my-4">
                                        <div class="d-flex justify-content-between mb-5">
                                            <h5 class="text-uppercase">Total price</h5>
                                            <h5 id="total">€ 137.00</h5>
                                        </div>
                                        <button type="button" class="btn btn-dark btn-block btn-lg" data-mdb-ripple-color="dark">Submit</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script>
        function calculateTotal() {
            let singlePrices = document.getElementsByClassName('mb-0 singlePrice');
            let productCounts = document.getElementsByClassName('form-control form-control-sm productCount');
            let totalElement = document.getElementById('total');
            let total = 0;
            for (let i = 0; i < singlePrices.length; ++i) {
                total = total + parseFloat(singlePrices[i].innerHTML) * parseInt(productCounts[i].value, 10);
            }
            totalElement.innerHTML = total.toString(10) + '$';
        }
    </script>
</section>

</body>
</html>

