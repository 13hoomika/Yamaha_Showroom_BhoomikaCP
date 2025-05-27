<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Available Bikes</title>
    <link rel="icon" href="${pageContext.request.contextPath}/static/images/yamaha_icon.png" type="image/png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/icons/bootstrap-icons.css">

    <script src="${pageContext.request.contextPath}/static/bootstrap/js/bootstrap.bundle.min.js"></script>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/user-styles.css">

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
    <h1 class="banner-title">Products</h1>
</div>

<div class="container">
    <!-- <h3 class="section-title">Available Bikes</h3> -->

    <c:choose>
        <c:when test="${not empty bikeList}">
            <div class="row g-4">
                <c:forEach var="bike" items="${bikeList}">
                    <div class="col-md-6 col-lg-4">

                        <div class="card bike-card" id="bike-${bike.bikeId}"
                             data-bs-toggle="modal"
                             data-bs-target="#bikeDetailsModal"
                             data-title="${bike.bikeModel}"
                             data-desc="${bike.bikeDescription}"
                             data-price="₹${bike.bikePrice}"
                             data-showroom="${bike.availableInShowroom}"
                             data-engine="${bike.engineCapacity}cc"
                             data-bike-type="${bike.bikeType}">

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
                                <c:choose>
                                    <c:when test="${not empty bike.availableInShowroom}">
                                        <div class="list-group-item"><strong>Showroom:</strong> ${bike.availableInShowroom}</div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="text-danger">Not available in showrooms yet</div>
                                    </c:otherwise>
                                </c:choose>
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

    <!-- Bike Details Modal -->
    <div class="modal fade" id="bikeDetailsModal" tabindex="-1" aria-labelledby="bikeDetailsModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-lg modal-dialog-centered">
        <div class="modal-content rounded-4 shadow">
          <div class="modal-header border-0">
            <h5 class="modal-title fw-bold" id="bikeDetailsModalLabel">Bike Model</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>

          <c:forEach var="img" items="${bike.images}" varStatus="status">
            <c:set var="imgList" value="${imgList}${img.filename}" />
            <c:if test="${!status.last}">
              <c:set var="imgList" value="${imgList}," />
            </c:if>
          </c:forEach>

          <div class="modal-body" data-imgs="${imgList}">
              <div id="modalCarousel" class="carousel slide mb-3" data-bs-ride="carousel">
                <div class="carousel-inner" id="modalCarouselInner">
                  <!-- JS will fill carousel items here -->
                </div>
                <button class="carousel-control-prev" type="button" data-bs-target="#modalCarousel" data-bs-slide="prev">
                  <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                  <span class="visually-hidden">Previous</span>
                </button>
                <button class="carousel-control-next" type="button" data-bs-target="#modalCarousel" data-bs-slide="next">
                  <span class="carousel-control-next-icon" aria-hidden="true"></span>
                  <span class="visually-hidden">Next</span>
                </button>
              </div>

              <p id="modalBikeDescription" class="text-muted"></p>

              <ul class="list-group list-group-flush mb-3">
                <li class="list-group-item d-flex justify-content-between"><strong>Bike Type:</strong> <span id="modalBikeType"></span></li>
                <li class="list-group-item d-flex justify-content-between"><strong>Price:</strong> <span id="modalBikePrice"></span></li>
                <li class="list-group-item d-flex justify-content-between"><strong>Engine:</strong> <span id="modalBikeEngine"></span></li>
                <li class="list-group-item d-flex justify-content-between"><strong>Showroom:</strong> <span id="modalBikeShowroom"></span></li>
              </ul>

              <button class="custom-submit-btn w-100">Schedule Test Drive / Booking</button>
            </div>
        </div>
      </div>
    </div>
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

    document.addEventListener('DOMContentLoaded', () => {
      const modal = document.getElementById('bikeDetailsModal');

      modal.addEventListener('show.bs.modal', event => {
        const bikeCard = event.relatedTarget;

        const title = bikeCard.getAttribute('data-title');
        const description = bikeCard.getAttribute('data-desc');
        const price = bikeCard.getAttribute('data-price');
        const showroom = bikeCard.getAttribute('data-showroom');
        const engine = bikeCard.getAttribute('data-engine');
        const bikeType = bikeCard.getAttribute('data-bike-type');

        // Get the images from the clicked bike card's carousel
        const bikeId = bikeCard.id.split('-')[1]; // Assuming bikeCard has an id like "carousel-1"
        const carouselItems = document.querySelectorAll(`#carousel-${bikeId} .carousel-item`);

        modal.querySelector('#bikeDetailsModalLabel').textContent = title;
        modal.querySelector('#modalBikeDescription').textContent = description;
        modal.querySelector('#modalBikePrice').textContent = price;
        modal.querySelector('#modalBikeEngine').textContent = engine;
        modal.querySelector('#modalBikeType').textContent = bikeType;
        const showroomElem = modal.querySelector('#modalBikeShowroom');
        showroomElem.textContent = showroom?.trim() !== '' ? showroom : 'Not available in showrooms yet';

        // Build carousel inner HTML dynamically
        const carouselInner = modal.querySelector('#modalCarouselInner');
        carouselInner.innerHTML = ''; // clear previous images

        carouselItems.forEach((item, index) => {
          const imgSrc = item.querySelector('img').src;
          const activeClass = index === 0 ? 'active' : '';
          const carouselItem = `
            <div class="carousel-item ${activeClass}">
              <img src="${imgSrc}" class="d-block w-100 rounded" alt="${title} image ${index + 1}" style="max-height: 300px; object-fit: cover;">
            </div>
          `;
          carouselInner.insertAdjacentHTML('beforeend', carouselItem);
        });

        // Initialize or reset carousel
        const carousel = bootstrap.Carousel.getInstance(document.getElementById('modalCarousel'));
        if (carousel) {
          carousel.to(0);
        } else {
          new bootstrap.Carousel(document.getElementById('modalCarousel'));
        }
      });
    });



</script>

</body>
</html>
