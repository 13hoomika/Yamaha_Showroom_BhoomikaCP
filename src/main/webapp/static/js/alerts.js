// alerts.js
document.addEventListener("DOMContentLoaded", function () {
    setTimeout(function () {
        const alert = document.querySelector('.alert');
        if (alert) {
            const bsAlert = bootstrap.Alert.getOrCreateInstance(alert);
            bsAlert.close(); // triggers Bootstrap's dismiss sequence
        }
    }, 3000);
});