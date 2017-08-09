<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page isELIgnored="false" %>
<%--
  Created by IntelliJ IDEA.
  User: alexfomin
  Date: 26.07.17
  Time: 9:55
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<p>
    Hello, ${requestScope.user.fName}
</p>
</body>
</html>
