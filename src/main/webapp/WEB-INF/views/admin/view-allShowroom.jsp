<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Showrooms | Yamaha Motors</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/admin-dashboard.css">
</head>
<body>
<div class="wrapper">
    <!-- Include the same sidebar as dashboard.jsp -->
    <jsp:include page="../fragments/admin-sidebar.jsp" />

    <!-- Page Content -->
    <div id="content" class="content">
        <!-- Top Header -->
        <jsp:include page="../fragments/admin-header.jsp" />

        <div class="container-fluid mt-4">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h4><i class="fas fa-store me-2"></i>Manage Showrooms</h4>
                <a href="${pageContext.request.contextPath}/admin/add-showroom" class="btn btn-primary">
                    <i class="fas fa-plus me-1"></i> Add New Showroom
                </a>
            </div>

            <!-- Showroom List Table -->
            <div class="row">
                <c:forEach var="showroom" items="${showroomList}">
                    <div class="col-md-4 mb-4">
                        <div class="card h-100 shadow-sm">
                            <img src="${pageContext.request.contextPath}/static/images/showroom-Images/${showroom.showroomImg}"
                                 class="card-img-top" alt="${showroom.showroomName}" style="height: 200px; object-fit: cover;">
                            <div class="card-body">
                                <h5 class="card-title">${showroom.showroomName}</h5>
                                <p class="card-text">
                                    <strong>Manager:</strong> ${showroom.showroomManager}<br>
                                    <strong>Phone:</strong> ${showroom.showroomPhone}<br>
                                    <strong>Email:</strong> ${showroom.showroomEmail}<br>
                                    <strong>Location:</strong> ${showroom.showroomLocation}<br>
                                    <strong>Address:</strong> ${showroom.showroomAddress}
                                </p>
                                <!-- Optional Action Buttons -->
                                <div class="d-flex justify-content-between">
                                    <a href="#" class="btn btn-outline-primary btn-sm">Edit</a>
                                    <a href="#" class="btn btn-outline-danger btn-sm">Delete</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/admin-sidebar.js"></script>
</body>
</html>