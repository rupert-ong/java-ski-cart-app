<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ page errorPage="error.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Shopping Cart Demo</title>
    <link rel="stylesheet" type="text/css" href="bootstrap.min.css">
</head>
<body>
    <sql:setDataSource var="myDS" driver="org.postgresql.Driver" url="jdbc:postgresql://localhost:5432/skistuff" user="rupert" password="secret"/>
    <sql:query var="listStuff" dataSource="${myDS}">
        SELECT * FROM skisEtc ORDER BY id;
    </sql:query>

    <div class="container">
        <h1>Ski Equipment</h1>
        <form action="handleOrder" method="post">
            <table class="table">
                <tr>
                     <th>Buy</th>
                     <th>Qty</th>
                     <th>Id</th>
                     <th>Product</th>
                     <th>Category</th>
                     <th>Price</th>
                 </tr>
                 <c:set var="ind" value="1" scope="page" />
                 <c:forEach var="item" items="${listStuff.rows}">
                    <tr>
                        <td><input type="checkbox" name="check-${ind}" id="check-${ind}"></td>
                        <td><input type="number" name="num-${ind}" id="num-${ind}" class="form-control" value="0" min="0" max="99" step="1"></td>
                        <td><input type="text" name="id-${ind}" id="id-${ind}" value="${item.id}" class="form-control" readonly></td>
                        <td><input type="text" name="prod-${ind}" id="prod-${ind}" value="${item.product}" class="form-control" readonly></td>
                        <td><input type="text" name="cat-${ind}" id="cat-${ind}" value="${item.category}" class="form-control" readonly></td>
                        <td><input type="text" name="price-${ind}" id="price-${ind}" value="${item.price}" class="form-control" readonly></td>
                    </tr>
                    <c:set var="ind" value="${ind+1}" />
                 </c:forEach>
            </table>
            <ul class="list-inline">
                <li><input type="submit" class="btn btn-primary" value="Submit Order"></li>
            </ul>
        </form>
    </div>

</body>
</html>