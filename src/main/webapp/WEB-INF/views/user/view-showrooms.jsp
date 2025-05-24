<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>View Showrooms | Yamaha Motors</title>
    <link rel="icon" href="${pageContext.request.contextPath}/static/images/yamaha_icon.png" type="image/png">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"/>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/user-styles.css">

    <style>
        .row {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
            justify-content: center;
        }

        .col-md-4 {
            flex: 0 0 32%;
            max-width: 32%;
        }

        .mb-4 {
            margin-bottom: 20px;
        }

        .card {
            background: #fff;
            border-radius: 10px;
            overflow: hidden;
            transition: transform 0.3s ease;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
            height: 100%;
        }

        .card:hover {
            transform: translateY(-5px);
        }

        .card-img-top {
            width: 100%;
            height: 200px;
            object-fit: cover;
        }

        .card-body {
            padding: 20px;
        }

        .card-title {
            font-size: 20px;
            margin-bottom: 10px;
            color: #1e3a8a;
        }

        .card-text {
            font-size: 14px;
            color: #555;
            line-height: 1.6;
        }

        @media (max-width: 992px) {
            .col-md-4 {
                flex: 0 0 48%;
                max-width: 48%;
            }
        }

        @media (max-width: 768px) {
            .col-md-4 {
                flex: 0 0 100%;
                max-width: 100%;
            }

        }
    </style>
</head>
<body>

<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-light  fixed-top shadow">
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
                    <a class="nav-link" href="${pageContext.request.contextPath}/user/getProfile">Update Profile</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/user/logout">Logout</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<!-- Showrooms Section -->
<div class="container">
    <h2 class="section-title">Available Showrooms</h2>

    <div class="row">
        <c:forEach var="showroom" items="${showroomList}">
            <div class="col-md-4 mb-4">
                <div class="card h-100 shadow-sm">
                    <img src="${pageContext.request.contextPath}/static/images/showroom-Images/${showroom.showroomImg}"
                         class="card-img-top" alt="${showroom.showroomName}">
                    <div class="card-body">
                        <h5 class="card-title">${showroom.showroomName}</h5>
                        <p class="card-text">
                            <strong>Manager:</strong> ${showroom.showroomManager}<br>
                            <strong>Phone:</strong> ${showroom.showroomPhone}<br>
                            <strong>Email:</strong> ${showroom.showroomEmail}<br>
                            <strong>Address:</strong> ${showroom.showroomAddress}
                        </p>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

</body>
</html>
