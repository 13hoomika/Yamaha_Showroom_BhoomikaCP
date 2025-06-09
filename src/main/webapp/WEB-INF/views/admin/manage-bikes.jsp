<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Bikes | Yamaha Motors</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/static/images/yamaha_icon.png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap.min.css"/>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/icons/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/admin-style.css">
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
                <h4><i class="fas fa-motorcycle me-2"></i>Manage Bikes</h4>
            </div>

            <div class="row mb-2">
                <!-- Bike Type Filter Dropdown -->
                <div class="col-auto">
                    <form method="get" action="${pageContext.request.contextPath}/admin/manage-bikes" class="d-inline">
                        <div class="input-group">
                            <select name="bikeType" class="form-select" onchange="this.form.submit()">
                                <option value="">All Types</option>
                                <c:forEach items="${allBikeTypes}" var="bikeTypes">
                                    <option value="${bikeTypes.name()}"
                                        ${param.bikeType eq bikeTypes.name() ? 'selected' : ''}>
                                        ${bikeTypes.displayName}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </form>
                </div>
                <!-- Add New Bike Button -->
                <div class="col-auto">
                    <a href="${pageContext.request.contextPath}/admin/add-bike" class="btn btn-primary">
                        <i class="fas fa-plus"></i> Add New Bike
                    </a>
                </div>
                <!-- Assign Bikes Button -->
                <div class="col-auto">
                    <a href="${pageContext.request.contextPath}/admin/assign-bikes" class="btn btn-primary">
                        <i class="fas fa-plus"></i> Assign Bikes
                    </a>
                </div>
            </div>

            <!-- Bike List Table -->
            <div class="card">
                <div class="card-body">
                    <div class="table-responsive">

                        <c:if test="${empty bikeList}">
                            <div class="alert alert-warning">No bikes found in the database</div>
                        </c:if>
                        <c:if test="${not empty success}">
                          <div class="alert alert-success alert-dismissible">${success}</div>
                          <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                        </c:if>

                        <c:if test="${not empty error}">
                            <div class="alert alert-danger alert-dismissible">${error}</div>
                            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                        </c:if>

                        <table class="table table-hover">
                            <thead class="table-light">
                                <tr>
                                    <th>Sl.No</th>
                                    <th>Model</th>
                                    <th>Showroom Name</th>
                                    <th>Price</th>
                                    <th>Type</th>
                                    <th>Year</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${bikeList}" var="bike" varStatus="status">
                                    <tr>
                                        <td>${status.count}</td>
                                        <td>${bike.bikeModel}</td>
                                        <td>${bike.availableInShowroom}</td>
                                        <td>Rs.${bike.bikePrice}</td>
                                        <td>${bike.bikeType.displayName}</td>
                                        <td>${bike.bikeYear}</td>
                                        <td class="text-center">
                                            <a href="${pageContext.request.contextPath}/admin/delete/bike/${bike.bikeId}"
                                               class="icon-btn"
                                               onclick="return confirm('Are you sure you want to delete this bike?');">
                                                <i class="fas fa-trash-alt icon-delete"></i>
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

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/admin-sidebar.js"></script>
<script src="${pageContext.request.contextPath}/static/js/alerts.js"></script>
</body>
</html>