<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Available Bikes</title>
    <link rel="icon" href="${pageContext.request.contextPath}/static/images/yamaha_icon.png" type="image/png">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"/>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    <style>
        .bike-card { border-radius: 10px; transition: 0.3s; }
        .bike-card:hover { box-shadow: 0 5px 15px rgba(0,0,0,0.2); transform: scale(1.01); }
        .bike-img { height: 200px; object-fit: cover; }

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
    </style>
</head>
<body>
<!-- Navbar -->
<div class="navbar">
    <h2>Yamaha Motors</h2>
    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/user/dashboard">Dashboard</a>
        <a href="${pageContext.request.contextPath}/user/bikes">Bikes</a>
        <!-- <a href="${pageContext.request.contextPath}/user/schedule">Schedule</a>-->
        <a href="${pageContext.request.contextPath}/user/logout">Logout</a>
    </div>
</div>
<div class="container mt-4">
    <h3 class="mb-4">Available Bikes</h3>

    <c:choose>
        <c:when test="${not empty bikeList}">
            <div class="row g-4">
                <c:forEach var="bike" items="${bikeList}">
                    <div class="col-md-6 col-lg-4">
                        <div class="card bike-card">
                            <!-- Image -->
                            <c:if test="${not empty bike.images}">
                                <div id="carousel-${bike.bikeId}" class="carousel slide" data-bs-ride="carousel">
                                    <div class="carousel-inner">
                                        <c:forEach items="${bike.images}" var="image" varStatus="loop">
                                            <div class="carousel-item ${loop.index == 0 ? 'active' : ''}">
                                                <img src="bikeImage?imageName=${image}"
                                                     class="d-block w-100 card-img-top"
                                                     alt="${bike.bikeModel} Image ${loop.index + 1}">
                                            </div>
                                        </c:forEach>
                                    </div>

                                    <c:if test="${fn:length(bike.images) > 1}">
                                        <button class="carousel-control-prev" type="button"
                                                data-bs-target="#carousel-${bike.bikeId}" data-bs-slide="prev">
                                            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                            <span class="visually-hidden">Previous</span>
                                        </button>
                                        <button class="carousel-control-next" type="button"
                                                data-bs-target="#carousel-${bike.bikeId}" data-bs-slide="next">
                                            <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                            <span class="visually-hidden">Next</span>
                                        </button>
                                    </c:if>
                                </div>
                            </c:if>

                            <div class="card-body">
                                <h5 class="card-title">${bike.bikeModel}</h5>
                                <p class="card-text text-muted">₹${bike.bikePrice}</p>
                                <p class="card-text">${bike.bikeDescription}</p>

                                <ul class="list-group list-group-flush">
                                    <li class="list-group-item"><strong>Bike Type:</strong> ${bike.bikeType}</li>
                                    <li class="list-group-item"><strong>Engine:</strong> ${bike.engineCapacity}cc</li>
                                    <li class="list-group-item"><strong>Mileage:</strong> ${bike.mileage} km/l</li>
                                    <li class="list-group-item"><strong>Fuel Tank:</strong> ${bike.fuelTankCapacity} L</li>
                                </ul>
                                <div class="text-muted"><strong>Showroom:</strong> ${bike.availableInShowroom}</div>


                                <!-- Schedule Button
                                <form method="get" action="${pageContext.request.contextPath}/user/schedule">
                                    <input type="hidden" name="bikeId" value="${bike.bikeId}"/>
                                    <div class="d-grid gap-2 mt-3">
                                        <button type="submit" name="type" value="0" class="btn btn-outline-primary btn-sm">Book</button>
                                        <button type="submit" name="type" value="1" class="btn btn-outline-secondary btn-sm">Test Ride</button>
                                    </div>
                                </form>-->
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:when>
        <c:otherwise>
            <div class="alert alert-info">No bikes available in this showroom.</div>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
