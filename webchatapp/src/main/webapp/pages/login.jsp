<%@ page  contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Title</title>
</head>
<body>

<form action="${pageContext.request.contextPath}/login" method="post">
    <label>
        Username:
        <input name="username">
    </label>
    <label>
        Password:
        <input type="password" name="password">
    </label>
    <input type="submit" value="Submit">

    <div>
        <c:choose>
            <c:when test="${requestScope.errorMsg!=null}"><c:out value="${requestScope.errorMsg}"/></c:when>
            <c:otherwise/>
        </c:choose>
    </div>

</form>

</body>
</html>
