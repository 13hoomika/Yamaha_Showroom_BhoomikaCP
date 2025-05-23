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
function checkPhNoForRegister() {
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

function checkPhNoForUpdate() {
    const phInput = document.getElementById('userPhoneNumber');
    const currentPh = phInput.getAttribute('data-current');
    const phone = phInput.value.trim();

    fetch(contextPath + `/checkPhValue/${phone}/${currentPh}`)
        .then(response => response.text())
        .then(data => {
            document.getElementById('phNoError').innerText = data;
            validateForm(); // trigger form validation again
        });
}

