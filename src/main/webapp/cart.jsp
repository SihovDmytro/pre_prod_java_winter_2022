<%@ taglib prefix="WStags" uri="http://webShopTags.com" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.lang.String" %>
<%@page import="java.math.BigDecimal" %>
<%@page import="java.lang.Long" %>
<%@page import="com.webShop.util.Constants" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Cart</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="css/webShop.css">
</head>
<body>
<div class="limit">
    <div class="content-container">
        <table id="cart">
            <thead>
            <tr>
                <th></th>
                <th></th>
                <th>Number</th>
                <th>Price, UAN</th>
                <th>Total, UAN</th>
                <th></th>
            </tr>
            </thead>
            <c:forEach var="entry" items="${sessionScope.cartService.cart}">
                <tr>
                    <td><img src="images/${entry.key.image}" width="100" height="100"></td>
                    <td>${entry.key.name}</td>
                    <td><input type="number" id="countProducts" onchange="updateCart(${entry.key.id}, this)" min="1"
                               max="100"
                               step="1" value="${entry.value}"/></td>
                    <td>${String.format("%.2f",entry.key.price)}</td>
                    <td>${String.format("%.2f",entry.key.price.multiply(BigDecimal.valueOf(Long.valueOf(entry.value))))}</td>
                    <td><img src="images/icons/trash.png" width="32" height="32"
                             onclick="deleteFromCart(${entry.key.id}, this)"/>
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td>Cart price:</td>
                <td>${String.format("%.2f",sessionScope.cartService.calculateTotal())}</td>
            </tr>
        </table>
        <c:if test="${sessionScope.cartService.cart.size()!=0}">
            <a href="${Constants.BUY_CART_SERVLET}">Proceed</a>
        </c:if>
    </div>
</div>
<script src="vendor/jquery/jquery-3.2.1.min.js"></script>
<script src="js/cart.js"></script>
</body>
</html>