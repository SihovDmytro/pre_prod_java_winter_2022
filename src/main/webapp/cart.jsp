<%@ include file="/WEB-INF/jspf/page.jspf" %>
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
        <WStags:cartTag/>
    </div>
</div>
<script src="vendor/jquery/jquery-3.2.1.min.js"></script>
<script src="js/localization.js"></script>
<script src="js/cart.js"></script>
</body>
</html>