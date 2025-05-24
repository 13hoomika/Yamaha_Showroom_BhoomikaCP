// Function to validate userName
function validateUserName() {
    const checkUserName = document.getElementById('userName').value.trim();
    console.log("Checking name:", checkUserName); //debugging

    if (checkUserName !== "") {
        const encodedName = encodeURIComponent(checkUserName);
        const xhttp = new XMLHttpRequest();
        xhttp.open("GET", contextPath + "/checkUserName/" + encodedName, true);
        xhttp.send();
        xhttp.onload = function () {
            console.log("Username Validation:", this.responseText);
            document.getElementById("userNameError").innerText = this.responseText;
            validateForm();
        };
    } else {
        document.getElementById("userNameError").innerText = "";
        validateForm();
    }
}

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

function validateDrivingLicense() {
    const checkDlInput = document.getElementById('drivingLicenseNumber').value.trim();
    console.log(checkDlInput);

    const dlError = document.getElementById('dlNoError');

    if (checkDlInput != "") {
         var xhttp = new XMLHttpRequest();
         xhttp.open("GET", contextPath + "/checkDlNumber/" + checkDlInput);
         xhttp.send();
         xhttp.onload = function () {
             console.log(this.responseText);
             document.getElementById("dlNoError").innerHTML = this.responseText;
             validateForm(); // Call validateForm after AJAX response
         };
    } else {
         document.getElementById("dlNoError").innerHTML = ""; // Clear error if field is empty
         validateForm();
    }
}

/* ============================= Update ======================================= */

/*function checkPhNoForUpdate() {
    const phInput = document.getElementById('userPhoneNumber');
    const currentPh = phInput.getAttribute('data-current');
    const phone = phInput.value.trim();

    fetch(contextPath + `/checkPhValue/${phone}/${currentPh}`)
        .then(response => response.text())
        .then(data => {
            document.getElementById('phNoError').innerText = data;
            validateForm(); // trigger form validation again
        });
}*/

function checkPhNoForUpdate() {
    const phoneField = document.getElementById('userPhoneNumber');
    const phone = phoneField.value.trim();
    const current = phoneField.getAttribute('data-current');
    const errorSpan = document.getElementById('phNoError');

    console.log("Ph Entered:", phone, "| Current ph:", current);

    // Regex: 10 digits, starting with 6-9
    const isValid = /^[6-9]\d{9}$/.test(phone);

    if (!isValid) {
        errorSpan.innerText = "Phone must be 10 digits starting with 6, 7, 8, or 9.";
        validateForm();
        return;
    }

    // AJAX or fetch to check uniqueness
    if (phone !== current) {
        fetch(contextPath + "/checkPhValue/" + phone + "/" + current)
            .then(res => res.text())
            .then(msg => {
                errorSpan.innerText = msg;
                validateForm();
            });
    } else {
        errorSpan.innerText = "";
        validateForm();
    }
}

function checkDlNoForUpdate() {
    const dlField = document.getElementById('drivingLicenseNumber'); // Get the input field
    const dl = dlField.value.trim(); // Value entered by the user
    const current = dlField.getAttribute('data-current'); // Original DL value
    const errorSpan = document.getElementById('dlError');

    console.log("DL Entered:", dl, "| Current DL:", current);

    // Format validation
    const dlRegex = /^[A-Z]{2}[0-9]{2}[0-9]{4}[0-9]{7}$/;
    if (!dlRegex.test(dl)) {
        errorSpan.innerText = "Enter a valid DL number (e.g., KA0120231234567)";
        validateForm();
        return;
    }

    // Only check with server if changed
    if (dl !== current) {
        const xhttp = new XMLHttpRequest();
        xhttp.open("GET", contextPath + "/checkDlNumber/" + dl + "/" + current, true);
        xhttp.send();
        xhttp.onload = function () {
            console.log("DL Check Response:", this.responseText);
            errorSpan.innerText = this.responseText;
            validateForm();
        };
    } else {
        errorSpan.innerText = "";
        validateForm();
    }
}










