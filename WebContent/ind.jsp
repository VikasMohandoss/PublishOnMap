<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Map</title>
</head>
<body>
<sql:setDataSource var="snapshot" driver="org.postgresql.Driver"
     url="jdbc:postgresql://localhost:5432/TwitterData"
     user="postgres"  password="password"/>
 
<sql:query dataSource="${snapshot}" var="result">
SELECT latitude, longitude from twittergeolocation limit 40;
</sql:query>
<table border="1" width="100%">
<tr>
   <th>Latitude</th>
   <th>Longitude</th>
</tr>
<c:forEach var="row" items="${result.rows}">
<tr>
   <td><c:out value="${row.latitude}"/></td>
   <td><c:out value="${row.longitude}"/></td>
</tr>
</c:forEach>
</table>

</body>
</html>