<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Users | Yamaha Motors</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/static/images/yamaha_icon.png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/icons/bootstrap-icons.css">
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
                <h4><i class="bi bi-person-plus-fill me-2"></i>Manage Users</h4>
            </div>

            <div class="row mb-2">
                <div class="col-md-2">
                    <form method="get" action="${pageContext.request.contextPath}/admin/manage-users" class="d-inline">
                        <div class="input-group">
                            <select name="scheduleType" class="form-select" onchange="this.form.submit()">
                                <option value="">All Schedules</option>
                                <c:forEach items="${SelectScheduleType}" var="schedule">
                                    <option value="${schedule.name()}"
                                        ${param.ScheduleType eq schedule.name() ? 'selected' : ''}>
                                        ${schedule.displayName}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </form>
                </div>
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
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${usersList}" var="user" varStatus="status">
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
                                        <td class="text-center align-middle">
                                            <a href="${pageContext.request.contextPath}/admin/followup-user?id=${user.userId}"
                                                class="icon-btn">
                                               <i class="bi bi-telephone-fill icon-followup"></i>
                                            </a>
                                        </td>
                                        <td class="text-center align-middle">
                                            <a href="${pageContext.request.contextPath}/admin/delete/user/${user.userId}"
                                               class="icon-btn"
                                               title="Delete User"
                                               onclick="return confirm('Are you sure you want to delete this user?');">
                                                <i class="bi bi-trash-fill icon-delete"></i>
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
<script src="${pageContext.request.contextPath}/static/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/admin-sidebar.js"></script>
</body>
</html>