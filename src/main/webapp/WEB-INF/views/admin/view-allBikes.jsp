<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View All Bikes | Yamaha Motors</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/admin-dashboard.css">
    <style>
        .bike-card {
            transition: transform 0.3s;
            margin-bottom: 5px;
            height: 100%;
        }
        .bike-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 20px rgba(0,0,0,0.1);
        }
        .card-img-top {
            height: 200px;
            object-fit: cover;
        }
        .filter-section {
            background-color: #f8f9fa;
            padding: 20px;
            border-radius: 5px;
            margin-bottom: 30px;
        }
        .bike-type-badge {
            background-color: #003399 !important;
            position: absolute;
            top: 10px;
            right: 10px;
            font-size: 0.8rem;
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
                <h4><i class="fas fa-motorcycle me-2"></i>All Bikes</h4>
            </div>

            <!-- Filter Section -->
            <div class="row mb-2">
                <div class="col-md-2">
                    <form method="get" action="${pageContext.request.contextPath}/admin/view-allBikes" class="d-inline">
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
            </div>

            <!-- Bike Cards -->
            <div class="row">
                <c:choose>
                    <c:when test="${not empty bikeList}">
                        <c:forEach items="${bikeList}" var="bike">
                            <div class="col-md-4 col-lg-3">
                                <div class="card bike-card">
                                    <!-- Bike Image Carousel -->
                                    <c:choose>
                                        <c:when test="${not empty bike.bikeImageUrls}">
                                            <div id="carousel-${bike.bikeId}" class="carousel slide" data-bs-ride="carousel">
                                                <div class="carousel-inner">
                                                    <c:forEach var="url" items="${bike.bikeImageUrls}" varStatus="status">
                                                        <div class="carousel-item ${status.index == 0 ? 'active' : ''}">
                                                            <img src="${pageContext.request.contextPath}/bikes/image/${url}" class="d-block w-100" alt="${bike.bikeModel}">                                                        </div>
                                                    </c:forEach>
                                                    <c:forEach var="url" items="${bike.bikeImageUrls}" varStatus="status">
                                                        <div>DEBUG: Image URL: ${url}</div>
                                                        <div class="carousel-item ${status.index == 0 ? 'active' : ''}">
                                                            <img src="/bikes/image/${url}" class="d-block w-100" alt="${bike.bikeModel}">
                                                        </div>
                                                    </c:forEach>

                                                </div>
                                                <button class="carousel-control-prev" type="button" data-bs-target="#carousel-${bike.bikeId}" data-bs-slide="prev">
                                                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                                    <span class="visually-hidden">Previous</span>
                                                </button>
                                                <button class="carousel-control-next" type="button" data-bs-target="#carousel-${bike.bikeId}" data-bs-slide="next">
                                                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                                    <span class="visually-hidden">Next</span>
                                                </button>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <img src="data:image/svg+xml,...No Image"
                                                 class="card-img-top"
                                                 alt="No Image Available">
                                        </c:otherwise>
                                    </c:choose>

                                    <!-- Bike Type Badge -->
                                    <span class="badge bike-type-badge">${bike.bikeType}</span>

                                    <div class="card-body">
                                        <h5 class="card-title">${bike.bikeModel}</h5>
                                        <h6 class="card-subtitle mb-2 text-muted">₹${bike.bikePrice}</h6>

                                        <ul class="list-group list-group-flush mb-3">
                                            <li class="list-group-item">
                                                <small class="text-muted">Year:</small>
                                                ${bike.bikeYear}
                                            </li>
                                            <li class="list-group-item">
                                                <small class="text-muted">Color:</small>
                                                ${bike.bikeColor}
                                            </li>
                                            <li class="list-group-item">
                                                <small class="text-muted">Mileage:</small>
                                                ${bike.mileage} km/l
                                            </li>
                                            <li class="list-group-item">
                                                <small class="text-muted">Engine:</small>
                                                ${bike.engineCapacity} cc
                                            </li>
                                            <li class="list-group-item">
                                                <small class="text-muted">Fuel Tank Capacity:</small>
                                                ${bike.fuelTankCapacity} ltr
                                            </li>
                                        </ul>

                                        <div class="d-flex justify-content-between">
                                            <span class="badge bg-light text-dark">${bike.availableInShowroom}</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div class="alert alert-info">
                            No bikes found. <a href="/admin/add-bike">Add a new bike</a>.
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
