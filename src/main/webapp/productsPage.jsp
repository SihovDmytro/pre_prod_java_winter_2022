<%@page import="com.webShop.entity.SortOption" %>
<%@page import="com.webShop.util.Attributes" %>
<%@page import="com.webShop.util.Constants" %>
<%@page import="com.webShop.util.WebShopUtil" %>

<%@ include file="/WEB-INF/jspf/page.jspf" %>
<head>
    <title>Products</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="css/webShop.css">
</head>
<body>
<WStags:languageTag/>
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
        <WStags:productsTag/>
    </div>
</div>
<script src="vendor/jquery/jquery-3.2.1.min.js"></script>
<script src="js/localization.js"></script>
<script src="js/productsPage.js"></script>
</body>
</html>