<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>View Showrooms | Yamaha Motors</title>
    <link rel="icon" href="${pageContext.request.contextPath}/static/images/yamaha_icon.png" type="image/png">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/icons/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/user-styles.css">
    <script src="${pageContext.request.contextPath}/static/bootstrap/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<div class="wrapper">
    <jsp:include page="../fragments/user-header.jsp" />
    <div class="banner">
        <h1 class="banner-title">Showrooms</h1>
    </div>

    <!-- Showrooms Section -->
    <div class="container">
        <!-- <h2 class="section-title">Available Showrooms</h2> -->

        <div class="row">
            <c:forEach var="showroom" items="${showroomList}">
                <div class="col-lg-4 col-md-6 col-sm-6 mb-4">
                    <div class="card product-card">
                        <img src="${pageContext.request.contextPath}/static/images/showroom-Images/${showroom.showroomImg}"
                             class="card-img-top" alt="${showroom.showroomName}">
                        <div class="card-body">
                            <h5 class="card-title">${showroom.showroomName}</h5>
                            <p class="card-text">
                                <strong>Manager:</strong> ${showroom.showroomManager}<br>
                                <strong>Phone:</strong> ${showroom.showroomPhone}<br>
                                <strong>Email:</strong> ${showroom.showroomEmail}<br>
                                <strong>Address:</strong> ${showroom.showroomAddress}
                            </p>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/views/fragments/home-footer.jsp" %>
<script src="${pageContext.request.contextPath}/static/js/uploadAvatar.js"></script>

</body>
</html>
