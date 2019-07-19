<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="dto.ExhibitDto" %><%--
  Created by IntelliJ IDEA.
  User: user
  Date: 7/18/2019
  Time: 10:03 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        <%@include file="style/index.css"%>
    </style>
    <title>Museum</title>
    <link rel="stylesheet" href="style/index.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
<jsp:include page="menu.jsp"/>
<div class="worker-container">
    <div class="row">
        <div class="col"></div>
        <div class="col-6">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">First name</th>
                    <th scope="col">Last name</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${workers}" var="item">
                    <tr>
                        <th scope="row"><a
                                href="http://localhost:8080/museum/worker?id=${(item.getId())}">${(item.getId())}</a>
                        </th>
                        <td><a href="http://localhost:8080/worker?id=${(item.getId())}">${(item.getFName())}</a></td>
                        <td><a href="http://localhost:8080/worker?id=${(item.getId())}">${(item.getSName())}</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="col"></div>
    </div>
</div>
<%--    <%--%>
<%--        ArrayList<dto.ExhibitDto> list = (ArrayList<dto.ExhibitDto>) request.getAttribute("exhibits");--%>
<%--        for (ExhibitDto exhibitDto : list) {--%>
<%--            out.println(exhibitDto.getId());--%>
<%--            out.println(exhibitDto.getName());--%>
<%--            out.println(exhibitDto.getMaterial());--%>
<%--            out.println(exhibitDto.getTechnology());--%>
<%--            out.write("<br>");--%>
<%--        }--%>
<%--    %>--%>
</table>
</body>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
        integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
        integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
        integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
        crossorigin="anonymous"></script>
</html>
