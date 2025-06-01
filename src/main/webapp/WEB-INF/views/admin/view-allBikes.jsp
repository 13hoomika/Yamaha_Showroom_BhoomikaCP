<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>All Bikes | Yamaha Motors</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="${pageContext.request.contextPath}/static/images/yamaha_icon.png" type="image/png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap.min.css"/>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/icons/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/admin-dashboard.css">
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

        .bike-type-badge {
            background-color: #003399 !important;
            position: absolute;
            top: 10px;
            right: 10px;
            font-size: 0.75rem;
            z-index: 10;
        }

        .carousel-inner {
            border-radius: 10px 10px 0 0;
        }
    </style>
</head>
<body>

<div class="wrapper">
    <!-- Sidebar and Header -->
    <jsp:include page="../fragments/admin-sidebar.jsp"/>
    <div id="content" class="content">
        <jsp:include page="../fragments/admin-header.jsp"/>

        <div class="container-fluid mt-4">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h4><i class="fas fa-motorcycle me-2"></i>View Bikes</h4>
            </div>

            <!-- Filter Section -->
            <div class="row mb-3">
                <div class="col-md-3">
                    <form method="get" action="${pageContext.request.contextPath}/admin/view-allBikes">
                        <div class="input-group">
                            <select name="bikeType" class="form-select" onchange="this.form.submit()">
                                <option value="" ${empty param.bikeType ? 'selected' : ''}>All Types</option>
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
                <div class="col-auto">
                    <a href="${pageContext.request.contextPath}/admin/add-bike" class="btn btn-primary">
                        <i class="fas fa-plus"></i> Add New Bike
                    </a>
                </div>
                <div class="col-auto">
                    <a href="${pageContext.request.contextPath}/admin/assign-bikes" class="btn btn-primary">
                        <i class="fas fa-plus"></i> Assign Bikes
                    </a>
                </div>
            </div>

            <!-- Bike Cards -->
            <div class="row g-4">
                <c:choose>
                    <c:when test="${not empty bikeList}">
                        <c:forEach items="${bikeList}" var="bike">
                            <div class="col-md-6 col-lg-4">
                                <div class="card bike-card shadow-sm position-relative">
                                    <!-- Bike Type Badge -->
                                    <span class="badge bike-type-badge">${bike.bikeType}</span>

                                    <!-- Image Carousel -->
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

                                    <!-- Bike Info -->
                                    <div class="card-body">
                                        <h5 class="card-title">${bike.bikeModel}</h5>
                                        <h6 class="card-subtitle mb-2 text-muted">₹${bike.bikePrice}</h6>

                                        <ul class="list-group list-group-flush mb-3">
                                            <li class="list-group-item"><strong>Year:</strong> ${bike.bikeYear}</li>
                                            <li class="list-group-item"><strong>Color:</strong> ${bike.bikeColor}</li>
                                            <li class="list-group-item"><strong>Mileage:</strong> ${bike.mileage} km/l</li>
                                            <li class="list-group-item"><strong>Engine:</strong> ${bike.engineCapacity} cc</li>
                                            <li class="list-group-item"><strong>Fuel Tank:</strong> ${bike.fuelTankCapacity} ltr</li>
                                        </ul>
                                        <c:choose>
                                            <c:when test="${not empty bike.availableInShowroom}">
                                                <div class="text-muted"><strong>Showroom:</strong> ${bike.availableInShowroom}</div>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="text-danger">Not available in showrooms yet</div>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div class="alert alert-info">
                            No bikes found. <a href="${pageContext.request.contextPath}/admin/add-bike">Add a new bike</a>.
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap JS Bundle -->
<script src="${pageContext.request.contextPath}/static/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/admin-sidebar.js"></script>

</body>
</html>
