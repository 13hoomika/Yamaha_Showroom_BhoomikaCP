<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Showroom | Yamaha Motors</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/static/images/yamaha_icon.png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap.min.css"/>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/icons/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/admin-dashboard.css">
</head>
<body>
<div class="wrapper">
    <!-- Include the same sidebar as dashboard.jsp -->
    <jsp:include page="../fragments/admin-sidebar.jsp" />

    <!-- Page Content -->
    <div id="content" class="content">
        <!-- Top Header -->
        <jsp:include page="../fragments/admin-header.jsp" />

        <div class="container-fluid mt-4">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h4><i class="fas fa-store me-2"></i>Add New Showroom</h4>
                <a href="${pageContext.request.contextPath}/admin/view-showrooms" class="btn btn-outline-secondary">
                    <i class="bi bi-arrow-left me-1"></i> Back to List
                </a>
            </div>

            <div class="card">
                <div class="card-body">
                    <form id="showroomForm" action="${pageContext.request.contextPath}/admin/add-showroom" method="post" enctype="multipart/form-data">
                        <div class="row g-3">
                            <div class="col-md-6">
                                <label for="showroomName" class="form-label">Showroom Name</label>
                                <input type="text" class="form-control" id="showroomName" name="showroomName"
                                        oninput="validateName('showroomName', 'showroomNameError', '/showroom/checkShowroomName/')" required>
                                <span id="showroomNameError" class="validation-error"></span>
                            </div>
                            <div class="col-md-6">
                                <label for="showroomManager" class="form-label">Manager</label>
                                <input type="text" class="form-control" id="showroomManager" name="showroomManager" required>
                            </div>
                            <div class="col-12">
                                <label for="showroomAddress" class="form-label">Full Address</label>
                                <textarea class="form-control" id="showroomAddress" name="showroomAddress" rows="2" required></textarea>
                            </div>
                            <div class="col-md-6">
                                <label for="showroomPhone" class="form-label">Contact Phone</label>
                                <input type="tel" class="form-control" id="showroomPhone" name="showroomPhone" required>
                            </div>
                            <div class="col-md-6">
                                <label for="showroomEmail" class="form-label">Contact Email</label>
                                <input type="email" class="form-control" id="showroomEmail" name="showroomEmail"
                                           oninput="checkEmail('showroomEmail', 'emailError', '/showroom/checkEmailValue/')" required>
                                <span id="emailError" class="validation-error"></span>
                            </div>
                            <div class="col-md-12">
                                <label for="multipartFile" class="form-label">Upload Showroom Image</label>
                                <input type="file" class="form-control" name="multipartFile" accept="image/*" id="imageInput" required />
                                <!-- Image preview container -->
                                <div id="previewContainer" style="margin-top: 10px;">
                                    <img id="previewImage" src="#" alt="Image Preview" class="img-thumbnail" style="display:none; max-width: 300px;" />
                                </div>
                            </div>
                            <div class="col-12 mt-4">
                                <button type="submit" id="submitBtn" class="btn btn-primary me-2" disabled>
                                    <i class="bi bi-floppy me-1"></i> Save Showroom
                                </button>
                                <button type="reset" class="btn btn-outline-secondary">
                                    <i class="bi bi-arrow-counterclockwise me-1"></i> Reset
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    const contextPath = '${pageContext.request.contextPath}';
</script>
<script src="${pageContext.request.contextPath}/static/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/admin-sidebar.js"></script>
<script src="${pageContext.request.contextPath}/static/js/uniqueFieldValidator.js"></script>
<script>
    const imageInput = document.getElementById("imageInput");
    const preview = document.getElementById("previewImage");

    imageInput.addEventListener("change", function(event) {
        const file = event.target.files[0];
        if (file && file.type.startsWith("image/")) {
            const reader = new FileReader();
            reader.onload = function(e) {
                preview.src = e.target.result;
                preview.style.display = "block";
            };
            reader.readAsDataURL(file);
        } else {
            preview.src = "#";
            preview.style.display = "none";
            alert("Please select a valid image file.");
        }
    });

    // Reset image preview on form reset
    document.getElementById("showroomForm").addEventListener("reset", function() {
        preview.src = "#";
        preview.style.display = "none";
    });

    document.addEventListener("DOMContentLoaded", function () {
        // Input listeners
        document.getElementById('showroomName').addEventListener('input', validateForm);
        document.getElementById('showroomManager').addEventListener('input', validateForm);
        document.getElementById('showroomAddress').addEventListener('input', validateForm);
        document.getElementById('showroomPhone').addEventListener('input', validateForm);
        document.getElementById('showroomEmail').addEventListener('input', validateForm);
        document.getElementById('imageInput').addEventListener('input', validateForm);
    });
    // Function to validate the entire form
   function validateForm() {
     const name = document.getElementById('showroomName').value.trim();
     const manager = document.getElementById('showroomManager').value.trim();
     const address = document.getElementById('showroomAddress').value.trim();
     const phone = document.getElementById('showroomPhone').value.trim();
     const email = document.getElementById('showroomEmail').value.trim();
     const showroomImg = document.getElementById('imageInput').value.trim();

     const nameError = document.getElementById('showroomNameError').innerText;
     //const phError = document.getElementById('phNoError').innerText;
     const emailError = document.getElementById('emailError').innerText;

     const submitBtn = document.getElementById('submitBtn');

     const allRequiredFilled = name && email && phone && manager && address && showroomImg;
     const noErrors = !nameError &&  !emailError;
     //&& !phError;

     if (allRequiredFilled && noErrors) {
         submitBtn.disabled = false;
     } else {
         submitBtn.disabled = true;
     }
   }
</script>

</body>
</html>