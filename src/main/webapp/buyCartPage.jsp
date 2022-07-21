<%@page import="com.webShop.util.Constants" %>
<%@ taglib prefix="WStags" uri="http://webShopTags.com" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Cart</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="css/webShop.css">
</head>
<body>
<WStags:languageTag/>
<div class="limit">
    <div class="content-container">
        <form class="buy-cart-form" method="post" action="${Constants.BUY_CART_SERVLET}">
            <div class="delivery-container">
                <p>Delivery</p>
                <div class="wrap-input">
                    <input class="input100" type="radio" name="delivery" id="toPostalOffice" checked>
                    <label for="toPostalOffice">To postal office</label>
                </div>
                <div class="wrap-input">
                    <input class="input100" type="radio" name="delivery" id="toAddress">
                    <label for="toAddress">To address</label>
                </div>
                <div class="wrap-input">
                    <input class="input100" type="text" name="address" id="address" placeholder="Address">
                </div>
            </div>

            <hr>

            <div class="payment-container">
                <p>Payment type</p>
                <div class="wrap-input">
                    <input type="radio" name="payment" id="byCardOnSite" checked>
                    <label class="input100" for="byCardOnSite">By cart on the site</label>
                </div>
                <div class="wrap-input">
                    <label for="number">Card number</label>
                    <input class="input100" type="text" name="number" id="number">
                </div>
                <div class="wrap-input">
                    <label for="month">Expiration date</label>
                    <select name="month" id="month">
                        <c:forEach var="month" begin="1" end="12" step="1">
                            <option value="${month}">${month}</option>
                        </c:forEach>
                    </select>
                    <select name="year">
                        <jsp:useBean id="date" class="java.util.Date"/>
                        <c:forEach var="year" begin="${date.year+1900}"
                                   end="${date.year+1900+20}" step="1">
                            <option value="${year}">${year}</option>
                        </c:forEach>
                    </select>
                </div>
                <button>Make order</button>
            </div>
        </form>
    </div>

</div>
<script src="vendor/jquery/jquery-3.2.1.min.js"></script>
<script src="js/localization.js"></script>
</body>
</html>