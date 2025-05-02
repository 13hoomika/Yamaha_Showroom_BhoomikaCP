<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/fragments/header.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Yamaha Motors - Ravs your heart</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;500;600;700&family=Bebas+Neue&display=swap" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <!-- Swiper JS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swiper@9/swiper-bundle.min.css">
    <!-- style.css -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
</head>
<body>
    <!-- Hero Slider -->
    <section class="hero-slider section-space">
        <div class="swiper">
            <div class="swiper-wrapper">
                <!-- Slide 1 - video -->
                <div class="swiper-slide">
                    <video class="hero-video" autoplay muted loop playsinline>
                        <source src="${pageContext.request.contextPath}/static/videos/The%20Call%20of%20the%20Blue%20Version%204.0.mp4" type="video/mp4">
                        <!-- Fallback if needed -->
                        <img src="${pageContext.request.contextPath}/static/images/fallback.jpg" alt="Video Preview">
                    </video>
                </div>

                <!-- Slide 2 - YouTube Video -->
                <div class="swiper-slide">
                    <video class="hero-video" autoplay muted loop playsinline>
                        <source src="https://videos.pexels.com/video-files/30384343/13021441_1920_1080_60fps.mp4" type="video/mp4">
                        <!-- Fallback image if video doesn't load -->
                        <img src="https://images.unsplash.com/photo-1473040675625-5278279001d7?q=80&w=2070&auto=format&fit=crop" alt="Sport Bikes">
                    </video>
                </div>

                <!-- Slide 3 -->
                <div class="swiper-slide">
                    <img src="https://images.unsplash.com/photo-1601795313997-9734fed2b3ef?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D" alt="Cruiser Bikes" class="hero-image">
                </div>

                <!-- Slide 4
                <div class="swiper-slide">
                    <img src="https://advanywhere.com/wp-content/uploads/2022/12/Recenzja-motocykla-Tenere-700-1920x1044.jpg" alt="Adventure Bikes" class="hero-image">
                </div>-->
            </div>
            <!-- Navigation Arrows -->
            <div class="swiper-button-next"></div>
            <div class="swiper-button-prev"></div>

            <!-- Pagination -->
            <div class="swiper-pagination"></div>
        </div>
    </section>



    <!-- Call to Action -->
    <section class="cta-section section-space">
        <div class="container">
            <div class="cta-content">
                <h2 class="cta-title">Ready to Own the Road?</h2>
                <p class="cta-text">Visit our showroom to experience these machines in person. Our experts will help you find the perfect motorcycle for your riding style.</p>
                <div class="btn-group">
                    <a href="${pageContext.request.contextPath}/user/register" class="btn btn-red" id="userLoginBtn">Schedule a Visit</a>
                    <a href="${pageContext.request.contextPath}/user/register" class="btn btn-outline">Book a Test Ride</a>
                </div>
            </div>
        </div>
    </section>

    <!-- Scripts -->
    <script src="https://cdn.jsdelivr.net/npm/swiper@9/swiper-bundle.min.js"></script>
    <script>
        // Initialize Swiper
        const swiper = new Swiper('.swiper', {
            // Optional parameters
            loop: true,
            autoplay: {
                delay: 6000,
                disableOnInteraction: false,
            },
            speed: 1000,
            effect: 'fade',
            fadeEffect: {
                crossFade: true
            },

            // Navigation arrows
            navigation: {
                nextEl: '.swiper-button-next',
                prevEl: '.swiper-button-prev',
            },

            // Pagination
            pagination: {
                el: '.swiper-pagination',
                clickable: true,
            },
        });

        // Pause autoplay when hovering over slider
        const slider = document.querySelector('.swiper');
        slider.addEventListener('mouseenter', function() {
            swiper.autoplay.stop();
        });

        slider.addEventListener('mouseleave', function() {
            swiper.autoplay.start();
        });
    </script>

</body>
</html>
<%@ include file="/WEB-INF/views/fragments/footer.jsp" %>
