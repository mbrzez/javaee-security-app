<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Store employee</title>
    <jsp:include page="/WEB-INF/includes/bootstrap.jsp" />
</head>
<body>
    <div class="container">
        <div class="mt-2 mb-2">
            <p class="h1">Create employee</p>
            <p class="mb-2">Here you can create employee</p>
        </div>
        <form action="${pageContext.request.contextPath}/employees/create" method="post">
            <div class="form-group">
                <label for="name">Name</label>
                <input id="name" name="name" type="text" class="form-control">
            </div>
            <div class="form-group">
                <label for="surname">Surname</label>
                <input id="surname" name="surname" type="text" class="form-control">
            </div>
            <div class="form-group">
                <label for="telephone">Telephone</label>
                <input id="telephone" name="telephone" type="text" class="form-control">
            </div>
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/employees">Cancel</a>
            <button class="btn btn-primary" type="submit">Submit</button>
        </form>
    </div>
</body>
</html>