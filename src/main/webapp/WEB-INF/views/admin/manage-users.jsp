<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Users | Yamaha Motors</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/admin-dashboard.css">
</head>
<body>
<div class="wrapper">
    <!-- Include sidebar fragment -->
    <jsp:include page="../fragments/admin-sidebar.jsp" />

    <!-- Page Content -->
    <div id="content" class="content">
        <!-- Include header fragment -->
        <jsp:include page="../fragments/admin-header.jsp" />

        <div class="container-fluid mt-4">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h4><i class="fas fa-users me-2"></i>Manage Users</h4>
            </div>

            <!-- User List Table -->
            <div class="card">
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-hover">
                            <thead class="table-light">
                                <tr>
                                    <th>ID</th>
                                    <th>Username</th>
                                    <th>Email</th>
                                    <th>Age</th>
                                    <th>Phone</th>
                                    <th>License No.</th>
                                    <th>Bike Type</th>
                                    <th>Showroom</th>
                                    <th>Schedule For</th>
                                    <th>Follow-Up</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${users}" var="user" varStatus="status">
                                    <tr>
                                        <td>${status.count}</td>
                                        <td>${user.userName}</td>
                                        <td>${user.userEmail}</td>
                                        <td>${user.userAge}</td>
                                        <td>${user.userPhoneNumber}</td>
                                        <td>${user.drivingLicenseNumber}</td>
                                        <td>
                                            <c:if test="${user.bikeType != null}">
                                                ${user.bikeType.displayName}
                                            </c:if>
                                        </td>
                                        <td>
                                            <c:if test="${user.showroomName != null}">
                                                ${user.showroomName}
                                            </c:if>
                                        </td>
                                        <td>
                                            <c:if test="${user.scheduleType != null}">
                                                ${user.scheduleType.displayName}
                                            </c:if>
                                        </td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/admin/followup-user?id=${user.userId}"
                                               class="btn btn-sm btn-info">
                                               <i class="fas fa-phone"></i> Follow Up
                                            </a>
                                        </td>
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

<!-- JavaScript Libraries -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/admin-sidebar.js"></script>
</body>
</html>