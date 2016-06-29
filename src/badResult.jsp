<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ page errorPage="error.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Error</title>
  <link rel="stylesheet" type="text/css" href="bootstrap.min.css">
</head>
<body>
  <div class="container">
    <h1>Error Detected</h1>
    <p>${result}</p>
    <p><a href="${pageContext.request.contextPath}/shop.jsp">Continue shopping</a></p>
  </div>
</body>
</html>

