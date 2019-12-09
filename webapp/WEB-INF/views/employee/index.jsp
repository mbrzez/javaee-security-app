<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Store employee</title>
    <jsp:include page="/WEB-INF/includes/bootstrap.jsp" />
</head>
<body>
<div class="container">
    <div class="mt-2 mb-2">
        <p class="h1">Employees list</p>
        <p class="mb-2">This is an employee panel. To add new employee click the button</p>
        <a class="btn btn-primary" href="${pageContext.request.contextPath}/employees/create">Add employee</a>
    </div>
    <table class="table">
        <thead>
            <tr><th>Id</th><th>Name</th><th>Surname</th><th>Telephone</th><th>Options</th></tr>
        </thead>
        <tbody>
            <c:forEach items="${employees}" var="employee">
                <tr>
                    <td>${employee.id}</td>
                    <td>${employee.name}</td>
                    <td>${employee.surname}</td>
                    <td>${employee.telephone}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/employees/edit?id=${employee.id}">Edit</a>
                        <a class="ml-2" href="${pageContext.request.contextPath}/employees/delete?id=${employee.id}">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
