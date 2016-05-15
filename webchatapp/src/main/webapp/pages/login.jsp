<%@ page  contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
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

</form>

</body>
</html>
