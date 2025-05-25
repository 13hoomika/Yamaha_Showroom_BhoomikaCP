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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/user-styles.css">
    <style>
    .bike-card {
        transition: transform 0.2s ease-in-out;
        border-radius: 10px;
        overflow: hidden;
        height: 100%;
    }

    .bike-card:hover {
        transform: translateY(-5px);
        box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
    }

    .card-img-top {
        height: 220px;
        object-fit: cover;
    }
    </style>
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
                    <a class="nav-link" href="${pageContext.request.contextPath}/user/showrooms">Showrooms</a
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

<div class="banner">
    <h1 class="banner-title">Products</h1>
</div>

<div class="container">
    <!-- <h3 class="section-title">Available Bikes</h3> -->

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
                                    <!-- <p class="card-text">
                                        <c:choose>
                                            <c:when test="${fn:length(bike.bikeDescription) > 100}">
                                                ${fn:substring(bike.bikeDescription, 0, 100)}...
                                                <a href="#" class="text-primary" data-bs-toggle="modal" data-bs-target="#descModal-${bike.bikeId}">Read more</a>
                                            </c:when>
                                            <c:otherwise>
                                                ${bike.bikeDescription}
                                            </c:otherwise>
                                        </c:choose>
                                    </p> -->

                                <p class="card-text">
                                    <c:choose>
                                        <c:when test="${fn:length(bike.bikeDescription) > 100}">
                                            <span id="short-${bike.bikeId}">
                                                ${fn:substring(bike.bikeDescription, 0, 100)}
                                                <a href="javascript:void(0);" onclick="toggleDescription('${bike.bikeId}')" class="text-primary">Read more</a>
                                            </span>
                                            <span id="full-${bike.bikeId}" style="display: none;">
                                                ${bike.bikeDescription}
                                                <a href="javascript:void(0);" onclick="toggleDescription('${bike.bikeId}')" class="text-danger">Show less</a>
                                            </span>
                                        </c:when>
                                        <c:otherwise>
                                            ${bike.bikeDescription}
                                        </c:otherwise>
                                    </c:choose>
                                </p>


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
                                <!-- Description Modal
                                <div class="modal fade" id="descModal-${bike.bikeId}" tabindex="-1" aria-labelledby="descModalLabel-${bike.bikeId}" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="descModalLabel-${bike.bikeId}">${bike.bikeModel} - Full Description</h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body">
                                                ${bike.bikeDescription}
                                            </div>
                                        </div>
                                    </div>
                                </div> -->
                    </div>
                </c:forEach>
            </div>
        </c:when>
        <c:otherwise>
            <div class="alert alert-info">No bikes available in this showroom.</div>
        </c:otherwise>
    </c:choose>
</div>

<!-- Read More Toggle Script -->
<script>
    function toggleDescription(bikeId) {
        const shortText = document.getElementById('short-' + bikeId);
        const fullText = document.getElementById('full-' + bikeId);

        if (shortText.style.display === 'none') {
            shortText.style.display = 'inline';
            fullText.style.display = 'none';
        } else {
            shortText.style.display = 'none';
            fullText.style.display = 'inline';
        }
    }
</script>

</body>
</html>
