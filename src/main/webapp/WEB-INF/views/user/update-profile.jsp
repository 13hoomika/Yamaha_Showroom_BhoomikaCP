<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Update Profile | Yamaha Motors</title>
    <link rel="icon" href="${pageContext.request.contextPath}/static/images/yamaha_icon.png" type="image/png">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/icons/bootstrap-icons.css">

    <!-- Custom CSS must come after Bootstrap to override it -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/user-styles.css">

    <script src="${pageContext.request.contextPath}/static/bootstrap/js/bootstrap.bundle.min.js"></script>
</head>
<body>

<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-light fixed-top shadow">
    <div class="container-fluid">
        <a class="navbar-brand fw-bold" href="#">Yamaha Motors</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarUserLinks" aria-controls="navbarUserLinks" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse justify-content-end" id="navbarUserLinks">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/user/dashboard">Dashboard</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/user/bikes">Bikes</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/user/showrooms">Showrooms</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/user/logout">Logout</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="banner">
    <h1 class="banner-title">Profile Settings</h1>
</div>

<div class="container">
    <h2 class="section-title">Edit Profile Details</h2>

    <!-- <div class="d-flex justify-content-center mb-5 pb-4">
        <form id="avatarUploadForm" action="${pageContext.request.contextPath}/user/uploadAvatar" method="post" enctype="multipart/form-data">
            <label for="avatarInput" class="avatar-wrapper position-relative" style="cursor: pointer;">
                <img src="${empty loggedInUser.profileImage
                            ? pageContext.request.contextPath.concat('/static/images/user-avatar.png')
                            : pageContext.request.contextPath.concat(loggedInUser.profileImage)}"
                     alt="User" class="rounded-circle user-avatar" style="width: 150px; height: 150px; object-fit: cover;"/>
                <div class="avatar-overlay position-absolute d-flex justify-content-center align-items-center" style="width: 150px; height: 150px;">
                    <i class="bi bi-camera-fill text-white"></i>
                </div>
            </label>
            <input type="file" id="avatarInput" name="avatar" accept="image/*" style="display: none;" onchange="uploadAvatar()">
        </form>
    </div> -->

    <div class="card-body">
        <c:if test="${not empty success}">
            <div class="alert alert-success">${success}</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/user/updateProfile" method="post" novalidate>
            <div class="row g-3">
                <div class="col-md-6">
                    <label for="userName" class="form-label">Name</label>
                    <input type="text" class="form-control" id="userName" name="userName"
                           placeholder="Enter name" value="${profile.userName}" onchange="validateUserName()" required
                    oninput="validateUserName()">
                    <span id="userNameError" class="validation-error""></span>
                </div>

                <div class="col-md-6">
                    <label for="userEmail" class="form-label">Email</label>
                    <input type="email" class="form-control" id="userEmail" name="userEmail"
                           value="${profile.userEmail}" readonly>
                </div>

                <div class="col-md-4">
                    <label for="userAge" class="form-label">Age</label>
                    <input type="number" class="form-control" id="userAge" name="userAge"
                           placeholder="Your age" value="${profile.userAge}" required>
                </div>

                <div class="col-md-8">
                    <label for="userPhoneNumber" class="form-label">Phone</label>
                    <input type="tel" class="form-control" id="userPhoneNumber" name="userPhoneNumber"
                           placeholder="10 digit phone" value="${profile.userPhoneNumber}"
                           data-current="${profile.userPhoneNumber}" oninput="checkPhNoForUpdate()" required>
                    <span id="phNoError" style="color: red; font-size: 0.85rem;"></span>
                </div>

                <div class="col-md-12">
                    <label for="userAddress" class="form-label">Address</label>
                    <input type="text" class="form-control" id="userAddress" name="userAddress"
                           placeholder="Enter address" value="${profile.userAddress}" required>
                </div>

                <div class="col-md-12">
                    <label for="drivingLicenseNumber" class="form-label">Driving License Number</label>
                    <input type="text" class="form-control" id="drivingLicenseNumber" name="drivingLicenseNumber"
                           placeholder="KA0120231234567" value="${profile.drivingLicenseNumber}" required
                           data-current="${profile.drivingLicenseNumber}" oninput="checkDlNoForUpdate()">
                    <span id="dlError" style="color: red; font-size: 0.85rem;"></span>
                </div>
            </div>

            <div class="d-grid mt-4">
                <button type="submit" id="submitBtn" class="btn custom-submit-btn" disabled>Update Profile</button>
            </div>
        </form>
    </div>
</div>

<script src="${pageContext.request.contextPath}/static/bootstrap/js/bootstrap.bundle.min.js"></script>
<script>
    const contextPath = '${pageContext.request.contextPath}';
</script>
<script src="${pageContext.request.contextPath}/static/js/uniqueFieldValidator.js"></script>
<script src="${pageContext.request.contextPath}/static/js/uploadAvatar.js"></script>
<script>
    document.addEventListener("DOMContentLoaded", function () {
        // Input listeners
        document.getElementById('userName').addEventListener('input', validateForm);
        document.getElementById('userEmail').addEventListener('input', validateForm);
        document.getElementById('userAge').addEventListener('input', validateForm);
        document.getElementById('userPhoneNumber').addEventListener('input', validateForm);
        document.getElementById('userAddress').addEventListener('input', validateForm);
        document.getElementById('drivingLicenseNumber').addEventListener('input', validateForm);
    });

   // Function to validate the entire form
   function validateForm() {
     const name = document.getElementById('userName').value.trim();
     const email = document.getElementById('userEmail').value.trim();
     const phone = document.getElementById('userPhoneNumber').value.trim();
     const age = document.getElementById('userAge').value.trim();
     const address = document.getElementById('userAddress').value.trim();
     const license = document.getElementById('drivingLicenseNumber').value.trim();

     const nameError = document.getElementById('userNameError').innerText;
     const phError = document.getElementById('phNoError').innerText;
     const dlError = document.getElementById('dlError').innerText;

     const submitBtn = document.getElementById('submitBtn');

     const allRequiredFilled = name && email && phone && age && address && license;
     const noErrors = !nameError && !phError && !dlError;

     if (allRequiredFilled && noErrors) {
         submitBtn.disabled = false;
     } else {
         submitBtn.disabled = true;
     }
   }
</script>
</body>
</html>
