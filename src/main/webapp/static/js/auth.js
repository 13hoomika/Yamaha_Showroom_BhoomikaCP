document.addEventListener('DOMContentLoaded', function() {
    // Mobile Navigation Toggle
    const navbarToggle = document.getElementById('navbarToggle');
    const navLinks = document.getElementById('navLinks');
    const authButtons = document.getElementById('authButtons');

    navbarToggle?.addEventListener('click', function() {
        this.classList.toggle('active');
        navLinks?.classList.toggle('active');
        authButtons?.classList.toggle('active');
    });

    // Modal Handling
    const adminModal = document.getElementById('adminLoginModal');
    const adminLoginBtn = document.getElementById('adminLoginBtn');
    const closeButtons = document.querySelectorAll('.close');

    // Show modal
    adminLoginBtn?.addEventListener('click', function(e) {
        e.preventDefault();
        console.log('Admin Login Button clicked!');
        resetForms();
        adminModal.style.display = 'block';
    });

    // Close modal
    closeButtons.forEach(btn => {
        btn.addEventListener('click', function() {
            adminModal.style.display = 'none';  // Close the modal
        });
    });

     // Close modal when clicking outside the modal content
    window.addEventListener('click', function(event) {
        if (event.target === adminModal) {
            adminModal.style.display = 'none';  // Close the modal if clicked outside
        }
    });

    // Form Visibility Management
    const emailForm = document.getElementById('emailForm');
    const otpForm = document.getElementById('otpForm');

    // Initialize form states
    function resetForms() {
        emailForm.style.display = 'block';
        otpForm.style.display = 'none';
        const otpInput = document.getElementById('otpInput');

        const verifyButton = otpForm.querySelector('button[type="submit"]');
        if (verifyButton) {
            verifyButton.disabled = false;
        }

        if (otpInput) {
            otpInput.disabled = false;
            otpInput.placeholder = "Enter 6-digit OTP";
            otpInput.classList.remove("expired");
        }
    }

    // OTP Timer Functionality
    function startOtpTimer(durationInSeconds) {
        const timerElement = document.getElementById('timer');
        if (!timerElement) return;
        const otpInput = document.getElementById('otpInput');
        const verifyButton = otpForm.querySelector('button[type="submit"]');

        let timeLeft = durationInSeconds;

        const timerInterval = setInterval(function() {
            const minutes = Math.floor(timeLeft / 60);
            const seconds = timeLeft % 60;

            timerElement.textContent =
                `${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;

            if (timeLeft <= 0) {
                clearInterval(timerInterval);
                otpInput.disabled = true;
                verifyButton.disabled = true;
                otpInput.placeholder = "OTP expired. Please request a new one.";
                otpInput.classList.add("expired");
            } else {
                timeLeft--;
            }
        }, 1000);
    }

    // Show OTP form when OTP has been sent
    if (window.otpSent === 'true') {
        adminModal.style.display = 'block';
        emailForm.style.display = 'none';
        otpForm.style.display = 'block';
        startOtpTimer(120);
    }

});