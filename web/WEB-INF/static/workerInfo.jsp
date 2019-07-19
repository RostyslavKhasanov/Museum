<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
<jsp:include page="menu.jsp"/>
<div class="card">
    <h6>Worker id: ${(worker.getId())}</h6>
    <h6>First name: ${(worker.getFName())}</h6>
    <h6>Last name: ${(worker.getSName())}</h6>
    <br>
    <c:forEach items="${worker.getHalls()}" var="item">
        <h6>Serves ${(item.getName())}</h6>
    </c:forEach>
    <c:forEach items="${worker.getExcursions()}" var="item">
        <h6>Excursion: begin - ${(item.getBegin())} end - ${(item.getEnd())}</h6>
    </c:forEach>
    <c:forEach items="${worker.getHalls()}" var="item">
        <h6>${(item.getExhibits(0).getName())}</h6>
    </c:forEach>
</div>
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
