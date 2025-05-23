// Function to validate email
   function checkEmail() {
       var checkEmailValue = document.getElementById('userEmail').value.trim(); // Remove spaces
       console.log("Checking email:", checkEmailValue); // Debugging

       if (checkEmailValue) {
           var encodedEmail = encodeURIComponent(checkEmailValue); // Encode special characters
           var xhttp = new XMLHttpRequest();
           xhttp.open("GET", contextPath + "/checkEmailValue/" + encodedEmail, true);
           xhttp.send();
           xhttp.onload = function () {
               console.log("Response:", this.responseText);
               document.getElementById("emailError").innerHTML = this.responseText;
               validateForm(); // Call validateForm after AJAX response
           };
       } else {
           document.getElementById("emailError").innerHTML = ""; // Clear error if field is empty
           validateForm(); // Call validateForm to recheck form state
       }
   }

   // Function to validate phone number
   function checkPhNo() {
       var checkPhValue = document.getElementById("userPhoneNumber").value;
       console.log(checkPhValue);
       if (checkPhValue != "") {
           var xhttp = new XMLHttpRequest();
           xhttp.open("GET", contextPath + "/checkPhValue/" + checkPhValue);
           xhttp.send();
           xhttp.onload = function () {
               console.log(this.responseText);
               document.getElementById("phNoError").innerHTML = this.responseText;
               validateForm(); // Call validateForm after AJAX response
           };
       } else {
           document.getElementById("phNoError").innerHTML = ""; // Clear error if field is empty
           validateForm(); // Call validateForm to recheck form state
       }
   }

   // Function to validate the entire form
   function validateForm() {
       const name = document.getElementById('userName').value.trim();
       const email = document.getElementById('userEmail').value.trim();
       const phone = document.getElementById('userPhoneNumber').value.trim();
       const age = document.getElementById('userAge').value.trim();
       const address = document.getElementById('userAddress').value.trim();
       const license = document.getElementById('drivingLicenseNumber').value.trim();
       const showroom = document.getElementById('showroom').value;
       const schedule = document.getElementById('scheduleType').value;

       const emailError = document.getElementById('emailError').innerText;
       const phError = document.getElementById('phNoError').innerText;

       const submitBtn = document.getElementById('submitBtn');

       const allRequiredFilled = name && email && phone && age && address && license && showroom && schedule;
       const noErrors = !emailError && !phError;

       if (allRequiredFilled && noErrors) {
           submitBtn.disabled = false;
       } else {
           submitBtn.disabled = true;
       }
   }