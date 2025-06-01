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
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/static/images/yamaha_icon.png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap.min.css"/>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/icons/bootstrap-icons.css">
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
            <div class="card">
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-hover">
                            <thead class="table-light">
                                <tr>
                                    <th>Sl.No</th>
                                    <th>Name</th>
                                    <th>Manager</th>
                                    <th>Contact</th>
                                    <th>Bike Count</th>
                                    <!-- <th>Actions</th> -->
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${showroomsList}" var="showroom" varStatus="status">
                                    <tr>
                                        <td>${status.count}</td>
                                        <td>${showroom.showroomName}</td>
                                        <td>${showroom.showroomManager}</td>
                                        <td>${showroom.showroomPhone}</td>
                                        <td>${showroom.bikeCount}</td>
                                        <!-- <td class="text-center align-middle">
                                            <a href="${pageContext.request.contextPath}/admin/delete/showroom/${showroom.showroomId}"
                                               class="icon-btn"
                                               onclick="return confirm('Are you sure you want to delete this showroom?');">
                                                <i class="fas fa-trash-alt icon-delete"></i>
                                            </a>
                                        </td> -->
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/admin-sidebar.js"></script>
</body>
</html>