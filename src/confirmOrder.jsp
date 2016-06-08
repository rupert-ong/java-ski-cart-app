<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ page errorPage="error.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Order Confirmation</title>
    <link rel="stylesheet" type="text/css" href="bootstrap.min.css">
</head>
<body>
    <div class="container">
        <h1>Order Confirmation</h1>
        <form action="placeOrder" method="post">
            <%-- Sent to OrderHandler servlet to iterate through items --%>
            <input type="hidden" name="rowCount" value="${items.size()}">
            <table class="table">
                <tr>
                     <th>Qty</th>
                     <th>Id</th>
                     <th>Product</th>
                     <th>Category</th>
                     <th>Price</th>
                 </tr>
                 <c:set var="ind" value="1" scope="page" />
                 <c:forEach var="item" items="${items}">
                    <tr>
                        <td><input type="number" name="num-${ind}" id="num-${ind}" class="form-control" value="${item.qty}" min="0" max="99" step="1"></td>
                        <td><input type="text" name="id-${ind}" id="id-${ind}" value="${item.id}" class="form-control" readonly></td>
                        <td><input type="text" name="prod-${ind}" id="prod-${ind}" value="${item.product}" class="form-control" readonly></td>
                        <td><input type="text" name="cat-${ind}" id="cat-${ind}" value="${item.category}" class="form-control" readonly></td>
                        <td><input type="text" name="price-${ind}" id="price-${ind}" value="${item.price}" class="form-control" readonly></td>
                    </tr>
                    <c:set var="ind" value="${ind+1}" />
                 </c:forEach>
            </table>
            <table class="table">
                <tr>
                    <th>Total:</th>
                    <td><fmt:formatNumber type="currency" value="${total}" /></td>
                </tr>
            </table>
            <ul class="list-inline">
                <li><input type="submit" class="btn btn-primary" value="Confirm Order"></li>
            </ul>
        </form>
    </div>

</body>
</html>