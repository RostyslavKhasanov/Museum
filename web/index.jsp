<%@ page import="jdbc.Connector" %>
<%@ page import="services.ExhibitService" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="dto.ExhibitDto" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: macbookpro
  Date: 7/16/19
  Time: 9:04 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Museum</title>
</head>
<%
    ExhibitService exhibitService;
    try {
        exhibitService = new ExhibitService(Connector.getConnection());
        List<ExhibitDto> exhibitDtos = exhibitService.findAll();
        for (int i = 0; i < exhibitDtos.size(); i++) {
            out.print(exhibitDtos.get(i).getName() + "<br>");
        }
    } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
    }

%>
<body>

</body>
</html>
