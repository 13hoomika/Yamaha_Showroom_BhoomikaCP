// Function to validate userName
function validateName(inputId, errorId, endpoint) {
    const checkUserName = document.getElementById(inputId).value.trim();
    console.log("Checking name:", checkUserName); //debugging

    if (checkUserName !== "") {
        const encodedName = encodeURIComponent(checkUserName);
        const xhttp = new XMLHttpRequest();
        xhttp.open("GET", contextPath + endpoint + encodedName, true);
        xhttp.send();
        xhttp.onload = function () {
            console.log("Name Validation:", this.responseText);
            document.getElementById(errorId).innerText = this.responseText;
            validateForm();
        };
    } else {
        document.getElementById(errorId).innerText = "";
        validateForm();
    }
}

// Function to validate email
function checkEmail(inputId, errorId, endpoint) {
   var checkEmailValue = document.getElementById(inputId).value.trim(); // Remove spaces
   console.log("Checking email:", checkEmailValue); // Debugging

   if (checkEmailValue) {
       var encodedEmail = encodeURIComponent(checkEmailValue); // Encode special characters
       var xhttp = new XMLHttpRequest();
       xhttp.open("GET", contextPath + endpoint + encodedEmail, true);
       xhttp.send();
       xhttp.onload = function () {
           console.log("Response:", this.responseText);
           document.getElementById(errorId).innerHTML = this.responseText;
           validateForm(); // Call validateForm after AJAX response
       };
   } else {
       document.getElementById(errorId).innerHTML = ""; // Clear error if field is empty
       validateForm(); // Call validateForm to recheck form state
   }
}

// Function to validate phone number
function checkPhNo(inputId, errorId, endpoint) {
   var checkPhValue = document.getElementById(inputId).value;
   console.log("Checking ph no:",checkPhValue);
   if (checkPhValue) {
       var xhttp = new XMLHttpRequest();
       xhttp.open("GET", contextPath + endpoint + checkPhValue, true);
       xhttp.send();
       xhttp.onload = function () {
           console.log(this.responseText);
           document.getElementById(errorId).innerHTML = this.responseText;
           validateForm(); // Call validateForm after AJAX response
       };
   } else {
       document.getElementById(errorId).innerHTML = ""; // Clear error if field is empty
       validateForm(); // Call validateForm to recheck form state
   }
}

function validateDrivingLicense() {
    const checkDlInput = document.getElementById('drivingLicenseNumber').value.trim();
    console.log(checkDlInput);

    const dlError = document.getElementById('dlNoError');

    if (checkDlInput != "") {
         var xhttp = new XMLHttpRequest();
         xhttp.open("GET", contextPath + "/user/checkDlNumber/" + checkDlInput);
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

function checkAge(inputId, errorId, endpoint) {
    const ageValue = document.getElementById(inputId).value.trim();
    console.log("Checking age:", ageValue);

    if (ageValue !== "") {
        const xhttp = new XMLHttpRequest();
        xhttp.open("GET", contextPath + endpoint + ageValue, true);
        xhttp.send();
        xhttp.onload = function () {
            console.log("Age Validation response:", this.responseText);
            document.getElementById(errorId).innerText = this.responseText;
            validateForm();
        };
    } else {
        document.getElementById(errorId).innerText = "";
        validateForm();
    }
}

/* ============================= Update User ======================================= */
function checkPhNoForUpdate() {
    const phoneField = document.getElementById('userPhoneNumber');
    const phone = phoneField.value.trim();
    const current = phoneField.getAttribute('data-current');
    const errorSpan = document.getElementById('phNoError');

    console.log("Ph Entered:", phone, "| Current ph:", current);

    // AJAX or fetch to check uniqueness
    if (phone !== current) {
        fetch(contextPath + "/user/checkPhValue/" + phone + "/" + current)
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

    // Only check with server if changed
    if (dl !== current) {
        const xhttp = new XMLHttpRequest();
        xhttp.open("GET", contextPath + "/user/checkDlNumber/" + dl + "/" + current, true);
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

