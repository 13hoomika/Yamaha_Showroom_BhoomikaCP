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
                <h4><i class="fas fa-motorcycle me-2"></i>Add New Bike</h4>
                <a href="${pageContext.request.contextPath}/admin/manage-bikes" class="btn btn-outline-secondary">
                    <i class="fas fa-arrow-left me-1"></i> Back to List
                </a>
            </div>

            <div class="card">
                <div class="card-body">
                    <form id="bikeForm" action="${pageContext.request.contextPath}/admin/add-bike" method="post" enctype="multipart/form-data">
                        <div class="row g-3">
                            <!-- Bike Model -->
                            <div class="col-md-6">
                                <label for="bikeModel" class="form-label">Model</label>
                                <input type="text" class="form-control" id="bikeModel" name="bikeModel" placeholder="Enter Bike model Name" required>
                            </div>

                            <!-- Bike Type -->
                            <div class="col-md-6">
                                <label for="bikeType" class="form-label">Bike Type</label>
                                <select class="form-select" id="bikeType" name="bikeType" required>
                                    <option value="">Select Bike Type</option>
                                    <c:forEach var="type" items="${bikeTypes}">
                                        <option value="${type}">${type.displayName}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <!-- Price -->
                            <div class="col-md-4">
                                <label for="bikePrice" class="form-label">Price (Rs.)</label>
                                <input type="number" class="form-control" id="bikePrice" name="bikePrice" required>
                            </div>

                            <!-- Year -->
                            <div class="col-md-4">
                                <label for="bikeYear" class="form-label">Year</label>
                                <input type="number" class="form-control" id="bikeYear" name="bikeYear" min="2000" max="2025" required>
                            </div>

                            <!-- Engine Capacity -->
                            <div class="col-md-4">
                                <label for="engineCapacity" class="form-label">Engine Capacity(CC)</label>
                                <input type="number" class="form-control" id="engineCapacity" name="engineCapacity" required>
                            </div>

                            <!-- Mileage -->
                            <div class="col-md-4">
                                <label for="mileage" class="form-label">Mileage(kmpl)</label>
                                <input type="text" inputmode="decimal" class="form-control" id="mileage" name="mileage" required>
                            </div>

                            <!-- Fuel Tank Capacity -->
                            <div class="col-md-4">
                                <label for="fuelTankCapacity" class="form-label">Fuel Tank Capacity (ltr)</label>
                                <input type="number" class="form-control" id="fuelTankCapacity" name="fuelTankCapacity" required>
                            </div>

                            <!-- Color -->
                            <div class="col-md-4">
                                <label for="bikeColor" class="form-label">Color</label>
                                <input type="text" class="form-control" id="bikeColor" name="bikeColor" required>
                            </div>

                            <!-- Description -->
                            <div class="col-12">
                                <label for="bikeDescription" class="form-label">Description</label>
                                <textarea class="form-control" id="bikeDescription" name="bikeDescription" rows="3"></textarea>
                            </div>

                            <!-- Images Upload -->
                            <div class="col-12">
                                <label for="bikeImages" class="form-label">Upload Images (Max 5)</label>
                                <input class="form-control" type="file" name="bikeImages" id="bikeImages" multiple required accept="image/*" />
                                <div class="form-text">Upload high-quality images of the bike (front, side, back views recommended)</div>
                            </div>

                            <!-- Form Buttons -->
                            <div class="col-12 mt-4">
                                <button type="submit" class="btn btn-primary me-2">
                                    <i class="fas fa-save me-1"></i> Save Bike
                                </button>
                                <button type="reset" class="btn btn-outline-secondary">
                                    <i class="fas fa-undo me-1"></i> Reset
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
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