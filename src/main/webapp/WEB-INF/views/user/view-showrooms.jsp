<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>View Showrooms | Yamaha Motors</title>
    <link rel="icon" href="${pageContext.request.contextPath}/static/images/yamaha_icon.png" type="image/png">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        /* Global Reset */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', sans-serif;
            background-color: #f4f4f4;
            color: #333;
            padding-top: 70px;
        }

        /* Navbar */
        .navbar {
            background-color: #1f2d3d;
            color: white;
            padding: 15px 30px;
            position: fixed;
            top: 0;
            width: 100%;
            z-index: 1000;
            display: flex;
            justify-content: space-between;
            align-items: center;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
        }

        .navbar h2 {
            font-size: 22px;
            color: #ffffff;
        }

        .nav-links a {
            color: #ffffff;
            text-decoration: none;
            margin-left: 20px;
            font-weight: 500;
            transition: color 0.3s ease;
        }

        .nav-links a:hover {
            color: #00aced;
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 30px 20px;
        }

        .section-title {
            font-size: 28px;
            margin-bottom: 30px;
            text-align: center;
            color: #1e3a8a;
        }

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

            .nav-links {
                display: flex;
                flex-direction: column;
                align-items: flex-end;
                gap: 10px;
            }
        }
    </style>
</head>
<body>

<!-- Navbar -->
<div class="navbar">
    <h2>Yamaha Motors</h2>
    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/user/dashboard">Dashboard</a>
        <a href="${pageContext.request.contextPath}/user/bikes">Bikes</a>
        <a href="${pageContext.request.contextPath}/user/updateProfile">Update Profile</a>
        <a href="${pageContext.request.contextPath}/user/logout">Logout</a>
    </div>
</div>

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
