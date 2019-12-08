<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Store employee</title>
    <jsp:include page="/WEB-INF/includes/bootstrap.jsp" />
</head>
<body>
    <div class="container">
        <div class="mt-2 mb-2">
            <p class="h1">Delete employee</p>
            <p class="mb-2">Here you can delete employee</p>
        </div>
        <jsp:useBean id="employee" class="pl.brzezins.dto.EmployeeDto" scope="request">
            <jsp:setProperty property="*" name="employee"/>
        </jsp:useBean>
        <form action="${pageContext.request.contextPath}/employees/delete" method="post">
            <input name="id" type="hidden" value="${employee.id}">
            <div class="form-group">
                <label for="name">Name</label>
                <input id="name" name="name" type="text" class="form-control" value="${employee.name}" disabled>
            </div>
            <div class="form-group">
                <label for="surname">Surname</label>
                <input id="surname" name="surname" type="text" class="form-control" value="${employee.surname}" disabled>
            </div>
            <div class="form-group">
                <label for="telephone">Telephone</label>
                <input id="telephone" name="telephone" type="text" class="form-control" value="${employee.telephone}" disabled>
            </div>
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/employees">Cancel</a>
            <button type="submit" class="btn btn-primary">Delete</button>
        </form>
    </div>
</body>
</html>