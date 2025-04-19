<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/otp.css">

<div class="otp-verification-container">
    <div class="otp-card">
        <div class="otp-header">
            <h2 class="otp-title">OTP Verification</h2>
            <div class="otp-icon">
                <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="#4a6bff" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"></path>
                </svg>
            </div>
        </div>

        <p class="otp-instruction">We've sent a 6-digit verification code to your registered email address.</p>

        <c:if test="${not empty otpError}">
            <div class="otp-error-message">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="#ff4a4a" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <circle cx="12" cy="12" r="10"></circle>
                    <line x1="12" y1="8" x2="12" y2="12"></line>
                    <line x1="12" y1="16" x2="12.01" y2="16"></line>
                </svg>
                ${otpError}
            </div>
        </c:if>

        <form class="otp-form" action="${pageContext.request.contextPath}/admin/verify" method="post">
            <div class="form-group">
                <label for="otp" class="form-label">Enter Verification Code</label>
                <div class="otp-input-container">
                    <input type="text"
                           id="otp"
                           name="otp"
                           class="otp-input"
                           maxlength="6"
                           pattern="[0-9]{6}"
                           title="6-digit number"
                           required
                           autocomplete="off"
                           autofocus>
                </div>
            </div>

            <button type="submit" class="otp-submit-btn">
                Verify Code
                <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="#ffffff" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <path d="M5 12h13M12 5l7 7-7 7"></path>
                </svg>
            </button>
        </form>

        <div class="otp-timer">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="#6b7280" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <circle cx="12" cy="12" r="10"></circle>
                <polyline points="12 6 12 12 16 14"></polyline>
            </svg>
            <span>Code expires in: </span>
            <span id="timer" class="timer-countdown">02:00</span>
        </div>

        <div class="otp-footer">
            <p>Didn't receive code? <a href="#" class="resend-link">Resend OTP</a></p>
        </div>
    </div>
</div>

<script>
document.addEventListener('DOMContentLoaded', function() {
    let minutes = 2;
    let seconds = 0;
    let timerInterval;
    const timerElement = document.getElementById('timer');

    function updateDisplay() {
        timerElement.textContent =
            `${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;

        if (minutes === 0) {
            timerElement.classList.add('warning');
        } else {
            timerElement.classList.remove('warning');
        }
    }

    function updateTimer() {
        if (minutes === 0 && seconds === 0) {
            clearInterval(timerInterval);
            timerElement.textContent = "00:00";
            timerElement.classList.add('expired');

            const errorDiv = document.createElement('div');
            errorDiv.className = 'otp-error-message';
            errorDiv.innerHTML = `OTP has expired. Please request a new one.`;
            document.querySelector('.otp-form').after(errorDiv);
            document.querySelector('.otp-submit-btn').disabled = true;
            return;
        }

        if (seconds === 0) {
            minutes--;
            seconds = 59;
        } else {
            seconds--;
        }

        updateDisplay();
    }

    updateDisplay(); // show 02:00
    timerInterval = setInterval(updateTimer, 1000);
}


    function handleExpiration() {
        clearInterval(timerInterval);
        timerElement.textContent = "00:00";
        timerElement.classList.add('expired');

        // Show error message
        const errorDiv = document.createElement('div');
        errorDiv.className = 'otp-error-message';
        errorDiv.innerHTML = `
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="#ff4a4a" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <circle cx="12" cy="12" r="10"></circle>
                <line x1="12" y1="8" x2="12" y2="12"></line>
                <line x1="12" y1="16" x2="12.01" y2="16"></line>
            </svg>
            OTP has expired. Please request a new one.
        `;

        document.querySelector('.otp-form').after(errorDiv);
        document.querySelector('.otp-submit-btn').disabled = true;
    }

    // Start timer
    // Show timer immediately before interval starts
    updateTimer();
    timerInterval = setInterval(updateTimer, 1000);
    console.log("Timer started with interval:", timerInterval);

    // Auto-focus submit button when OTP is complete
    document.getElementById('otp').addEventListener('input', function(e) {
        if (this.value.length === 6) {
            document.querySelector('.otp-submit-btn').focus();
        }
    });

    // Resend OTP functionality
    document.querySelector('.resend-link').addEventListener('click', function(e) {
        e.preventDefault();
        console.log("Resend OTP clicked");

        // Reset the timer
        clearInterval(timerInterval);
        minutes = 1;
        seconds = 59;

        // Remove any existing expiration message
        const existingError = document.querySelector('.otp-form + .otp-error-message');
        if (existingError) {
            existingError.remove();
        }

        // Update display immediately
        updateDisplay();
        timerElement.classList.remove('expired', 'warning');

        // Re-enable submit button if it was disabled
        document.querySelector('.otp-submit-btn').disabled = false;

        // Restart timer
        timerInterval = setInterval(updateTimer, 1000);

        // In a real app, you would call your backend to resend OTP here
        alert('New OTP has been sent to your email!');
    });
});
</script>