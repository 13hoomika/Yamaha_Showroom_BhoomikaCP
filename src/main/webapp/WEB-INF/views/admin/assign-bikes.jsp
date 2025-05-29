<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Bike | Yamaha Motors</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/static/images/yamaha_icon.png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap.min.css"/>
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
                <h4><i class="bi bi-bicycle me-2"></i>Assign Bikes to Showrooms</h4>
                <a href="${pageContext.request.contextPath}/admin/view-allBikes" class="btn btn-outline-secondary">
                    <i class="bi bi-arrow-left-short me-1"></i> Back to List
                </a>
            </div>

            <div class="card">
                <div class="card-body">
                    <c:if test="${not empty success}">
                        <div class="alert alert-success">${success}</div>
                    </c:if>
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">${error}</div>
                    </c:if>

                    <form method="post" action="${pageContext.request.contextPath}/admin/assign-bike">
                        <div class="row g-3">
                            <div class="col-md-6">
                                <label class="form-label">Select Bike</label>
                                <select name="bikeId" class="form-select" required>
                                    <option value="">Select Bike</option>
                                    <c:forEach items="${unassignedBikes}" var="bike">
                                        <option value="${bike.bikeId}">
                                            ${bike.bikeModel} (${bike.bikeYear})
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="col-md-6">
                                <label class="form-label">Select Showroom</label>
                                <select name="showroomId" class="form-select" required>
                                    <option value="">Select Showroom</option>
                                    <c:forEach items="${showrooms}" var="showroom">
                                        <option value="${showroom.showroomId}">
                                            ${showroom.showroomName} (${showroom.bikeCount}/5 bikes)
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="col-12">
                                <button type="submit" class="btn btn-primary">
                                    <i class="bi bi-link-45deg me-1"></i> Assign Bike
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/static/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/admin-sidebar.js"></script>
<script>
    document.getElementById('bikeImages').addEventListener('change', function() {
        if (this.files.length > 5) {
            alert("You can only upload up to 5 images.");
            this.value = "";
        }
    });
</script>
</body>
</html>