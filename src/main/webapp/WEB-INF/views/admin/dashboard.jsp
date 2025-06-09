<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard | Yamaha Motors</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/static/images/yamaha_icon.png">
    <!-- Bootstrap -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/icons/bootstrap-icons.css">
    <!-- style.css -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/admin-style.css">
</head>
<body>
<div class="wrapper">
    <!-- Include Sidebar Fragment -->
    <jsp:include page="../fragments/admin-sidebar.jsp" />

    <!-- Page Content -->
    <div id="content" class="content">
        <!-- Include Header Fragment -->
        <jsp:include page="../fragments/admin-header.jsp" />

        <!-- Dashboard Overview -->
        <div class="container-fluid mt-4">
            <h4 class="mb-4">Dashboard Overview</h4>
            <div class="row">
                <div class="col-md-4 col-sm-4">
                    <div class="card dashboard-card">
                        <div class="card-header">
                            <h5 class="card-title mb-0">Total Bikes</h5>
                        </div>
                        <div class="card-body">
                            <h2 class="card-text">${bikeCount}</h2>
                            <a href="${pageContext.request.contextPath}/admin/manage-bikes" class="btn btn-sm btn-outline-primary">Manage</a>
                        </div>
                    </div>
                </div>
                <div class="col-md-4 col-sm-4">
                    <div class="card dashboard-card">
                        <div class="card-header">
                            <h5 class="card-title mb-0">Showrooms</h5>
                        </div>
                        <div class="card-body">
                            <h2 class="card-text">${showroomCount}</h2>
                            <a href="${pageContext.request.contextPath}/admin/manage-showrooms" class="btn btn-sm btn-outline-primary">Manage</a>
                        </div>
                    </div>
                </div>
                <div class="col-md-4 col-sm-4">
                    <div class="card dashboard-card">
                        <div class="card-header">
                            <h5 class="card-title mb-0">Total Users</h5>
                        </div>
                        <div class="card-body">
                            <h2 class="card-text">${usersCount}</h2>
                            <a href="${pageContext.request.contextPath}/admin/manage-users" class="btn btn-sm btn-outline-primary">View Users</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- JavaScript Libraries -->
<script src="${pageContext.request.contextPath}/static/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/admin-sidebar.js"></script>
</body>
</html>