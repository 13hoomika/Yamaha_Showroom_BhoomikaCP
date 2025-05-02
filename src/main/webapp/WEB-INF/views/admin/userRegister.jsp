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
    <style>
        .error {
            font-size: 0.85rem;
            color: #e30613;
        }
    </style>
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
                <h4><i class="fas fa-user me-2"></i>User Registration</h4>
                <a href="${pageContext.request.contextPath}/admin/manage-users" class="btn btn-outline-secondary">
                    <i class="fas fa-arrow-left me-1"></i> Back to List
                </a>
            </div>

            <div class="card">
                <div class="card-body">
                    <c:if test="${not empty successMessage}">
                        <div class="alert alert-success text-center">${successMessage}</div>
                    </c:if>

                    <form action="registerUser" method="post">
                        <div class="row g-3">
                            <div class="col-md-6">
                                <label for="userName" class="form-label">Name</label>
                                <input type="text" class="form-control" id="userName" name="userName" placeholder="Enter name" required>
                                <div class="error">${errors.userName}</div>
                            </div>

                            <div class="col-md-6">
                                <label for="userEmail" class="form-label">Email</label>
                                <input type="email" class="form-control" id="userEmail" name="userEmail" placeholder="example@gmail.com" required>
                                <div class="error">${errors.userEmail}</div>
                            </div>

                            <div class="col-md-4">
                                <label for="userAge" class="form-label">Age</label>
                                <input type="number" class="form-control" id="userAge" name="userAge" placeholder="Your age" required>
                                <div class="error">${errors.userAge}</div>
                            </div>

                            <div class="col-md-8">
                                <label for="userPhoneNumber" class="form-label">Phone</label>
                                <input type="tel" class="form-control" id="userPhoneNumber" name="userPhoneNumber" placeholder="10 digit phone" required>
                                <div class="error">${errors.userPhoneNumber}</div>
                            </div>

                            <div class="col-md-12">
                                <label for="userAddress" class="form-label">Address</label>
                                <input type="text" class="form-control" id="userAddress" name="userAddress" placeholder="Enter address" required>
                                <div class="error">${errors.userAddress}</div>
                            </div>

                            <div class="col-md-12">
                                <label for="drivingLicenseNumber" class="form-label">Driving License Number</label>
                                <input type="text" class="form-control" id="drivingLicenseNumber" name="drivingLicenseNumber" placeholder="KA1234567890123" required>
                                <div class="error">${errors.drivingLicenseNumber}</div>
                            </div>

                            <div class="col-md-4">
                                <label for="bikeType" class="form-label">Bike Type</label>
                                <select class="form-select" id="bikeType" name="bikeType">
                                    <option value="">Select</option>
                                    <c:forEach var="type" items="${bikeTypes}">
                                        <option value="${type}">${type.displayName}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="col-md-4">
                                <label for="showroom" class="form-label">Showroom</label>
                                <select class="form-select" id="showroom" name="showroomId" required>
                                    <option value="">Select Showroom</option>
                                    <c:forEach items="${showrooms}" var="showroom">
                                        <option value="${showroom.showroomId}"> <!-- Store ID -->
                                            ${showroom.showroomName}<!-- Display name -->
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="col-md-4">
                                <label for="scheduleType" class="form-label">Schedule</label>
                                <select class="form-select" id="scheduleType" name="scheduleType">
                                    <option value="">Select</option>
                                    <c:forEach var="type" items="${scheduleTypeList}">
                                        <option value="${type}">${type.displayName}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div id="scheduleDetails" class="row g-3 d-none mt-2">
                                <div class="col-md-6">
                                    <label for="scheduleDate" class="form-label">Preferred Date</label>
                                    <input type="date" class="form-control" id="scheduleDate" name="scheduleDate" min="">
                                </div>
                                <div class="col-md-6">
                                    <label for="scheduleTime" class="form-label">Preferred Time</label>
                                    <input type="time" class="form-control" id="scheduleTime" name="scheduleTime" min="09:00" max="17:00">
                                    <small class="text-muted">Select between 9:00 AM and 5:00 PM</small>
                                </div>
                            </div>

                        </div>

                        <div class="d-grid mt-4">
                            <button type="submit"  id="submitBtn" class="btn btn-primary">Register</button>
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
    document.addEventListener("DOMContentLoaded", function () {
        const scheduleType = document.getElementById("scheduleType");
        const scheduleDetails = document.getElementById("scheduleDetails");
        const dateInput = document.getElementById("scheduleDate");
        const timeInput = document.getElementById("scheduleTime");
        const submitBtn = document.getElementById("submitBtn");

        //  Set today's date as minimum
        const today = new Date().toISOString().split("T")[0];
        dateInput.min = today;


        function toggleFields() {
            const value = scheduleType.value;
            const isScheduleVisit = value === "SCHEDULE_VISIT";

            // Show/hide date/time fields
            scheduleDetails.classList.toggle("d-none", !isScheduleVisit);

            // Make them required only for Test Drive
            dateInput.required = isScheduleVisit;
            timeInput.required = isScheduleVisit;

            // Change button label
            submitBtn.textContent = isScheduleVisit ? "Schedule Now" : "Book Now";
        }

        scheduleType.addEventListener("change", toggleFields);
        toggleFields(); // Run once on page load
    });
</script>
</body>
</html>