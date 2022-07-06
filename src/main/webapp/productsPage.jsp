<%@page import="com.webShop.entity.SortOption" %>
<%@page import="com.webShop.util.Attributes" %>
<%@page import="com.webShop.util.Constants" %>
<%@page import="com.webShop.service.CartService" %>
<%@page import="com.webShop.util.Messages" %>
<%@page import="java.lang.String" %>
<%@page import="java.lang.Math" %>
<%@page import="com.webShop.util.ProductsPageConfig" %>
<%@ taglib prefix="WStags" uri="http://webShopTags.com" %>
<%@page import="com.webShop.util.WebShopUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Products</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="css/webShop.css">
</head>
<body>
<div class="limit">
    <div class="container-filters">
        <form class="filter-form" id="filter-form" name="filter-form"
              action="${Constants.FILTER_PRODUCTS_SERVLET}" method="get">
            <div class="page-size-block">
                <p>Products per page</p>
                <select name="pageSize" id="pageSize">
                    <c:forEach var="entry"
                               items="${applicationScope[Attributes.PAGE_SIZE]}">
                        <option value="${entry}" ${sessionScope.productsPageBean.pageSize == entry ? "selected" : ""}>${entry}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="page-size-block">
                <p>Sort</p>
                <select name="sortType" id="sortType">
                    <c:forEach var="entry"
                               items="${applicationScope[Attributes.SORT_TYPE]}">
                        <option value="${SortOption.valueOf(entry).sortName}" ${sessionScope.productsPageBean.sortType.sortName.equals(SortOption.valueOf(entry).sortName) ? "selected" : ""}>
                                ${SortOption.valueOf(entry).sortName}
                        </option>
                    </c:forEach>
                </select>
            </div>
            <div class="filter-block">
                <p>Price</p>
                <input type="number" name="minPrice" id="minPrice" step="1" min="0" max="999999999"
                       placeholder="min"
                       value="${sessionScope.productsPageBean.minPrice!=null ? sessionScope.productsPageBean.minPrice : ""}">
                <input type="number" name="maxPrice" id="maxPrice" step="1" min="0" max="999999999"
                       placeholder="max"
                       value="${sessionScope.productsPageBean.maxPrice!=null ? sessionScope.productsPageBean.maxPrice : ""}">
            </div>
            <div class="filter-block">
                <p>Name</p>
                <input type="text" name="name" id="name" maxlength="45"
                       value="${sessionScope.productsPageBean.name!=null ? sessionScope.productsPageBean.name : ""}">
            </div>
            <div class="filter-block">
                <p>Category</p>
                <select name="category" id="category">
                    <option></option>
                    <c:forEach var="entry"
                               items="${applicationScope[Attributes.CATEGORIES_SERVICE].getAllCategories()}">
                        <option value="${entry.name}" ${sessionScope.productsPageBean.category.equals(entry.name) ? "selected" : ""}>${entry.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="filter-block">
                <p>Producer</p>

                <c:forEach var="entry" items="${applicationScope[Attributes.PRODUCERS_SERVICE].getAllProducers()}">
                    <p>
                        <input type="checkbox"
                               name="producer"
                               value="${entry.name}" ${(WebShopUtil.contains(sessionScope.productsPageBean.producers, entry.name)) ? "checked" : ""} />
                        <label for="${entry.name}">${entry.name}</label>
                    </p>
                </c:forEach>
            </div>

            <div>
                <button id="doFilter">Apply</button>
            </div>
        </form>
    </div>
    <div class="container-products">
        <c:choose>
            <c:when test="${requestScope.products.size() > 0}">
                <c:if test="${sessionScope.productsPageBean.pageSize < requestScope.countProducts}">
                    <div class="pages">
                        <c:set var="currPage" scope="page" value="${sessionScope.productsPageBean.page}"/>
                        <c:if test="${currPage>1}">

                            <c:forEach begin="${Math.max(currPage - ProductsPageConfig.PAGINATION_RANGE, 1)}" step="1"
                                       end="${currPage-1}" varStatus="loop">
                                    <a href=${requestScope.url}?${WebShopUtil.updatePage(requestScope.queryString, loop.index)}>${loop.index}</a>
                            </c:forEach>
                        </c:if>

                            ${currPage}

                        <c:set var="lastPage" scope="page"
                               value="${WebShopUtil.getLastPage(requestScope.countProducts, sessionScope.productsPageBean.pageSize)}"/>
                        <c:if test="${currPage < lastPage}">
                            <c:forEach begin="${currPage+1}" step="1"
                                       end="${currPage+ProductsPageConfig.PAGINATION_RANGE}" varStatus="loop">
                                <c:if test="${loop.index <= lastPage}">
                                    <a href=${requestScope.url}?${WebShopUtil.updatePage(requestScope.queryString, loop.index)}>${loop.index}</a>
                                </c:if>
                            </c:forEach>
                        </c:if>
                    </div>
                </c:if>
                <c:set var="productsInRow" value="0"/>
                <table class="products">
                    <c:forEach var="product" items="${requestScope.products}">
                        <c:if test="${productsInRow==0}">
                            <tr>
                        </c:if>
                        <td>
                            <p><img src="images/${product.image}" width="200" height="200"></p>
                            <hr>
                            <p>${product.name}</p>
                            <p>${product.description}</p>
                            <p>Price: ${String.format("%.2f",product.price)} UAN</p>
                            <hr>
                            <button>Add to cart</button>
                        </td>
                        <c:set var="productsInRow" value="${productsInRow+1}"/>
                        <c:if test="${productsInRow>=ProductsPageConfig.PRODUCTS_PER_LINE}">
                            </tr>
                            <c:set var="productsInRow" value="0"/>
                        </c:if>
                    </c:forEach>
                </table>

            </c:when>
            <c:otherwise>
                <p>${Messages.DID_NOT_MATCH}</p>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<script src="vendor/jquery/jquery-3.2.1.min.js"></script>
<script src="js/productsPage.js"></script>
</body>
</html>