<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Index page</title>
</head>
<body>
    <h1>Index page</h1>
    <p>This is index.jsp page on ${pageContext.request.contextPath}</p>
    <a href="${pageContext.request.contextPath}/employees">Go to Employees servlet</a>
</body>
</html>
