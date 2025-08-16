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
<div class="wrapper">
    <jsp:include page="../fragments/user-header.jsp" />
    <div class="banner">
        <h1 class="banner-title">Products</h1>
    </div>

    <div class="container">
        <!-- <h3 class="section-title">Available Bikes</h3> -->

        <div class="row g-4">
            <c:choose>
                <c:when test="${not empty bikeList}">
                        <c:forEach var="bike" items="${bikeList}">
                            <div class="col-lg-4 col-md-6 col-sm-6">
                                <div class="card product-card" id="bike-${bike.bikeId}">
                                    <!-- Image -->
                                    <c:if test="${not empty bike.images}">
                                        <div id="carousel-${bike.bikeId}" class="carousel slide" data-bs-ride="carousel">
                                            <div class="carousel-inner">
                                                <c:forEach items="${bike.images}" var="image" varStatus="loop">
                                                    <div class="carousel-item ${loop.index == 0 ? 'active' : ''}">
                                                        <img src="bikeImage?bikeId=${bike.bikeId}&imageName=${image}"
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
                                            <li class="list-group-item"><strong>Engine:</strong> ${bike.engineCapacity} cc</li>
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
                                    -->

                                        <div class="d-flex justify-content-between mt-3">
                                            <c:set var="imageUrls" value="" />
                                            <c:forEach items="${bike.images}" var="image" varStatus="loop">
                                                    <c:set var="imageUrls" value="${imageUrls}${pageContext.request.contextPath}/user/bikeImage?bikeId=${bike.bikeId}&imageName=${image}" />                                                <c:if test="${!loop.last}">
                                                    <c:set var="imageUrls" value="${imageUrls}," />
                                                </c:if>
                                            </c:forEach>

                                            <button class="view-details-btn btn-sm"
                                                    data-bike-id="${bike.bikeId}"
                                                    data-bs-toggle="modal"
                                                    data-bs-target="#bikeDetailsModal"
                                                    data-title="${bike.bikeModel}"
                                                    data-desc="${bike.bikeDescription}"
                                                    data-price="₹ ${bike.bikePrice}"
                                                    data-showroom="${bike.availableInShowroom}"
                                                    data-engine="${bike.engineCapacity} cc"
                                                    data-mileage="${bike.mileage} km/l"
                                                    data-fuelTankCapacity="${bike.fuelTankCapacity} L"
                                                    data-bike-type="${bike.bikeType}"
                                                    data-image-urls="${imageUrls}"> <%-- ADD THIS LINE --%>
                                                View Details
                                            </button>

                                            <form action="scheduleBooking" method="get">
                                                <input type="hidden" name="bikeId" value="${bike.bikeId}">
                                                <button class="custom-btn btn-sm" type="submit">Book Now</button>
                                            </form>
                                        </div>

                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                </c:when>
                <c:otherwise>
                    <div class="col-12 text-center text-muted">
                        <h5>No bikes available at the moment.</h5>
                    </div>
                </c:otherwise>

            </c:choose>
        </div>

        <!-- Bike Details Modal -->
        <div class="modal fade" id="bikeDetailsModal" tabindex="-1" aria-labelledby="bikeDetailsModalLabel">
          <div class="modal-dialog modal-lg modal-dialog-centered">
            <div class="modal-content rounded-4 shadow">
              <div class="modal-header border-0">
                <h5 class="modal-title fw-bold" id="bikeDetailsModalLabel">Bike Model</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
              </div>

              <div class="modal-body">
                  <div id="modalCarousel" class="carousel slide mb-3"> <!-- Removed data-bs-ride causing the automatic cycling-->
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
                    <li class="list-group-item d-flex justify-content-between"><strong>Mileage:</strong> <span id="modalMileage"></span></li>
                    <li class="list-group-item d-flex justify-content-between"><strong>Fuel Tank Capacity:</strong> <span id="modalFuelTank"></span></li>
                    <li class="list-group-item d-flex justify-content-between"><strong>Showroom:</strong> <span id="modalBikeShowroom"></span></li>
                  </ul>

                  <form id="scheduleForm" method="get" action="scheduleBooking">
                    <input type="hidden" name="bikeId" id="modalBikeId">
                    <button type="submit" class="custom-btn w-100">Schedule Test Drive / Booking</button>
                  </form>
                </div>
            </div>
          </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/views/fragments/home-footer.jsp" %>

<script>
    const contextPath = '${pageContext.request.contextPath}';
</script>

<script src="${pageContext.request.contextPath}/static/js/uploadAvatar.js"></script>
<!-- Read More Toggle Script
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
 -->
<script>


document.addEventListener('DOMContentLoaded', () => {
    const modal = document.getElementById('bikeDetailsModal');

    modal.addEventListener('show.bs.modal', event => {
        const button = event.relatedTarget;
        const bikeId = button.getAttribute('data-bike-id');

        console.log('--- DEBUG START ---');
        console.log('Button clicked:', button);
        console.log('data-bike-id attribute value:', button.getAttribute('data-bike-id'));
        console.log('Value of bikeId variable:', bikeId);
        console.log('--- DEBUG END ---');

        document.getElementById('modalBikeId').value = bikeId;

        const title = button.getAttribute('data-title');
        const description = button.getAttribute('data-desc');
        const price = button.getAttribute('data-price');
        const showroom = button.getAttribute('data-showroom');
        const engine = button.getAttribute('data-engine');
        const mileage = button.getAttribute('data-mileage');
        const fuelTank = button.getAttribute('data-fuelTankCapacity');
        const bikeType = button.getAttribute('data-bike-type');

        const imageUrlsString = button.getAttribute('data-image-urls');
        const imageUrls = imageUrlsString ? imageUrlsString.split(',') : [];
        console.log('Image URLs from data attribute:', imageUrls);

        modal.querySelector('#bikeDetailsModalLabel').textContent = title;
        modal.querySelector('#modalBikeDescription').textContent = description;
        modal.querySelector('#modalBikePrice').textContent = price;
        modal.querySelector('#modalBikeEngine').textContent = engine;
        modal.querySelector('#modalMileage').textContent = mileage;
        modal.querySelector('#modalFuelTank').textContent = fuelTank;
        modal.querySelector('#modalBikeType').textContent = bikeType;

        const showroomElem = modal.querySelector('#modalBikeShowroom');
        showroomElem.textContent = showroom?.trim() !== '' ? showroom : 'Not available in showrooms yet';

        const carouselInner = modal.querySelector('#modalCarouselInner');
        carouselInner.innerHTML = '';

        if (imageUrls.length > 0) {
            console.log('Creating carousel items with URLs:', imageUrls);

            // Modify your image creation code like this:
            imageUrls.forEach((url, index) => {
                const fullImageUrl = url.startsWith('/') ? window.location.origin + url : url;
                console.log(`Processing URL ${index}:`, fullImageUrl);

                const carouselItemDiv = document.createElement('div');
                carouselItemDiv.classList.add('carousel-item');
                if (index === 0) carouselItemDiv.classList.add('active');

                const imgElement = new Image(); // Use Image constructor instead of createElement
                imgElement.classList.add('d-block', 'w-100', 'rounded');
                imgElement.alt = `${title} Image ${index + 1}`;
                imgElement.style.maxHeight = '300px';
                imgElement.style.objectFit = 'cover';

                // Add error handling
                imgElement.onerror = function() {
                    console.error('Failed to load image:', this.src);
                    this.src = `${contextPath}/static/images/no-image.jpg`;
                };

                // Set src only after the image is added to DOM
                carouselItemDiv.appendChild(imgElement);
                carouselInner.appendChild(carouselItemDiv);

                // Now set the src - this is the key change
                imgElement.src = fullImageUrl;
                console.log('Image src set to:', imgElement.src);
            });
        } else {
            showNoImagePlaceholder(carouselInner);
        }
        initCarousel();
    });

    function showNoImagePlaceholder(container) {
        // This is already correctly using contextPath, so it's fine
        const contextPath = '${pageContext.request.contextPath}';
        container.innerHTML = `
            <div class="carousel-item active">
                <img src="${contextPath}/static/images/no-image.jpg"
                     class="d-block w-100 rounded" alt="No image available"
                     style="max-height: 350px; object-fit: cover;">
            </div>
        `;
    }

    function initCarousel() {
        const carouselElement = document.getElementById('modalCarousel');
        if (!carouselElement) {
            console.error('Modal carousel element #modalCarousel not found!');
            return;
        }

        const carouselItems = carouselElement.querySelectorAll('.carousel-item');

        const controls = carouselElement.querySelectorAll('.carousel-control-prev, .carousel-control-next');
        controls.forEach(control => {
            control.style.display = carouselItems.length > 1 ? 'flex' : 'none';
        });

        // Initialize carousel without auto-cycling
        const existingInstance = bootstrap.Carousel.getInstance(carouselElement);
        if (existingInstance) {
            existingInstance.dispose();
        }
        new bootstrap.Carousel(carouselElement, {
            interval: false // This disables auto-cycling
        });

        // Ensure first item is active
        carouselElement.querySelectorAll('.carousel-item').forEach((item, idx) => {
            if (idx === 0) {
                item.classList.add('active');
            } else {
                item.classList.remove('active');
            }
        });
    }
});
</script>

</body>
</html>
