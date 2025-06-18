<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Bike | Yamaha Motors</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/static/images/yamaha_icon.png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap.min.css"/>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/icons/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/admin-style.css">
    <style>
        .image-preview-container {
            display: flex;
            flex-wrap: wrap;
            gap: 15px;
            margin-top: 10px;
        }
        .image-preview {
            width: 120px;
            height: 120px;
            border: 1px dashed #ccc;
            border-radius: 5px;
            display: flex;
            align-items: center;
            justify-content: center;
            overflow: hidden;
            position: relative;
        }
        .image-preview img {
            max-width: 100%;
            max-height: 100%;
        }
        .image-preview .remove-btn {
            position: absolute;
            top: 5px;
            right: 5px;
            background: rgba(0,0,0,0.5);
            color: white;
            border: none;
            border-radius: 50%;
            width: 24px;
            height: 24px;
            display: flex;
            align-items: center;
            justify-content: center;
            cursor: pointer;
        }
        .required-field::after {
            content: " *";
            color: red;
        }
    </style>
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
                <h4><i class="bi bi-bicycle me-2"></i>Add New Bike</h4>
                <a href="${pageContext.request.contextPath}/admin/view-allBikes" class="btn btn-outline-secondary">
                    <i class="bi bi-arrow-left me-1"></i> Back to List
                </a>
            </div>

            <div class="card">
                <div class="card-body">
                    <form id="bikeForm" action="${pageContext.request.contextPath}/admin/add-bike" method="post" enctype="multipart/form-data">
                        <c:if test="${not empty successMessage}">
                            <div class="alert alert-success alert-dismissible">${successMessage}</div>
                        </c:if>
                        <c:if test="${not empty errorMessage}">
                            <div class="alert alert-danger alert-dismissible">${errorMessage}</div>
                        </c:if>
                        <div class="row g-3">
                            <!-- Bike Model -->
                            <div class="col-md-6">
                                <label for="bikeModel" class="form-label required-field">Model</label>
                                <input type="text" class="form-control" id="bikeModel" name="bikeModel" placeholder="Enter Bike model Name"
                                        onchange="validateName('bikeModel', 'bikeModelError', '/bike/checkBikeModel/')" required>
                                <span id="bikeModelError" class="validation-error"></span>
                            </div>

                            <!-- Bike Type -->
                            <div class="col-md-6">
                                <label for="bikeType" class="form-label required-field">Bike Type</label>
                                <select class="form-select" id="bikeType" name="bikeType" required>
                                    <option value="">Select Bike Type</option>
                                    <c:forEach var="type" items="${bikeTypes}">
                                        <option value="${type}">${type.displayName}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <!-- Price -->
                            <div class="col-md-4">
                                <label for="bikePrice" class="form-label required-field">Price (Rs.)</label>
                                <input type="text" class="form-control" id="bikePrice" name="bikePrice" required>
                            </div>

                            <!-- Year -->
                            <div class="col-md-4">
                                <label for="bikeYear" class="form-label required-field">Year</label>
                                <input type="text" class="form-control" id="bikeYear" name="bikeYear" min="2000" max="2025" required>
                            </div>

                            <!-- Engine Capacity -->
                            <div class="col-md-4">
                                <label for="engineCapacity" class="form-label required-field">Engine Capacity(CC)</label>
                                <input type="number" class="form-control" id="engineCapacity" name="engineCapacity" required>
                            </div>

                            <!-- Mileage -->
                            <div class="col-md-4">
                                <label for="mileage" class="form-label required-field">Mileage(kmpl)</label>
                                <input type="text" class="form-control" id="mileage" name="mileage" required>
                            </div>

                            <!-- Fuel Tank Capacity -->
                            <div class="col-md-4">
                                <label for="fuelTankCapacity" class="form-label required-field">Fuel Tank Capacity (ltr)</label>
                                <input type="number" step="0.01" class="form-control" id="fuelTankCapacity" name="fuelTankCapacity" required>
                            </div>

                            <!-- Color -->
                            <div class="col-md-4">
                                <label for="bikeColor" class="form-label required-field">Color</label>
                                <input type="text" class="form-control" id="bikeColor" name="bikeColor"
                                        oninput="validateName('bikeColor', 'bikeColorError', '/bike/checkBikeColor/')" required>
                                <span id="bikeColorError" class="validation-error"></span>
                            </div>

                            <!-- Showroom Availability
                            <div class="col-md-6">
                                <label for="showroomId" class="form-label">Showroom</label>
                                <select class="form-select" id="showroomId" name="showroomId">
                                    <option value="">Select Showroom</option>
                                </select>
                            </div>-->

                            <!-- Description -->
                            <div class="col-12">
                                <label for="bikeDescription" class="form-label">Description</label>
                                <textarea class="form-control" id="bikeDescription" name="bikeDescription" rows="3"></textarea>
                            </div>

                            <!-- Images Upload Section -->
                            <div class="col-12">
                                <h5 class="mb-3">Upload Bike Images</h5>

                                <div class="row">
                                    <!-- Front Image -->
                                    <div class="col-md-6 mb-3">
                                        <label for="frontImage" class="form-label required-field">Front View</label>
                                        <input class="form-control" type="file" name="multipartFileList" id="frontImage" required accept="image/*" />
                                        <div class="form-text">Clear image showing the front of the bike</div>
                                        <div class="image-preview-container" id="frontPreview"></div>
                                    </div>

                                    <!-- Back Image -->
                                    <div class="col-md-6 mb-3">
                                        <label for="backImage" class="form-label required-field">Back View</label>
                                        <input class="form-control" type="file" name="multipartFileList" id="backImage" required accept="image/*" />
                                        <div class="form-text">Clear image showing the rear of the bike</div>
                                        <div class="image-preview-container" id="backPreview"></div>
                                    </div>

                                    <!-- Left Image -->
                                    <div class="col-md-6 mb-3">
                                        <label for="leftImage" class="form-label">Left Side View</label>
                                        <input class="form-control" type="file" name="multipartFileList" id="leftImage" required accept="image/*" />
                                        <div class="form-text">Clear image showing the left side</div>
                                        <div class="image-preview-container" id="leftPreview"></div>
                                    </div>

                                    <!-- Right Image -->
                                    <div class="col-md-6 mb-3">
                                        <label for="rightImage" class="form-label">Right Side View</label>
                                        <input class="form-control" type="file" name="multipartFileList" id="rightImage" required accept="image/*" />
                                        <div class="form-text">Clear image showing the right side</div>
                                        <div class="image-preview-container" id="rightPreview"></div>
                                    </div>
                                </div>
                            </div>

                            <!-- Form Buttons -->
                            <div class="col-12 mt-4">
                                <button type="submit" id="submitBtn" class="btn btn-primary me-2" disabled>
                                    <i class="bi bi-floppy me-1"></i> Save Bike
                                </button>
                                <button type="reset" class="btn btn-outline-secondary">
                                    <i class="bi bi-arrow-counterclockwise me-1"></i> Reset
                                </button>
                                <p style="color: red;">${sessionScope.addBikeErrorMsg}</p>
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
<script src="${pageContext.request.contextPath}/static/js/alerts.js"></script>
<script src="${pageContext.request.contextPath}/static/js/uniqueFieldValidator.js"></script>
<script>
    // Image preview functionality for all image inputs
    function setupImagePreview(inputId, previewId) {
        const input = document.getElementById(inputId);
        const previewContainer = document.getElementById(previewId);

        input.addEventListener('change', function() {
            previewContainer.innerHTML = '';

            if (this.files && this.files[0]) {
                const file = this.files[0];
                const reader = new FileReader();

                reader.onload = function(e) {
                    const previewDiv = document.createElement('div');
                    previewDiv.className = 'image-preview';

                    const img = document.createElement('img');
                    img.src = e.target.result;

                    const removeBtn = document.createElement('button');
                    removeBtn.className = 'remove-btn';
                    removeBtn.innerHTML = '×';
                    removeBtn.onclick = function() {
                        previewContainer.removeChild(previewDiv);
                        input.value = '';
                    };

                    previewDiv.appendChild(img);
                    previewDiv.appendChild(removeBtn);
                    previewContainer.appendChild(previewDiv);
                }

                reader.readAsDataURL(file);
            }
        });
    }

    // Initialize previews for all image inputs
        document.addEventListener('DOMContentLoaded', function() {
            setupImagePreview('frontImage', 'frontPreview');
            setupImagePreview('backImage', 'backPreview');
            setupImagePreview('leftImage', 'leftPreview');
            setupImagePreview('rightImage', 'rightPreview');

            // Reset form handling
            document.querySelector('button[type="reset"]').addEventListener('click', function() {
                document.querySelectorAll('.image-preview-container').forEach(container => {
                    container.innerHTML = '';
                });
            });

            // Input listeners
            document.getElementById('bikeModel').addEventListener('input', validateForm);
            document.getElementById('bikeType').addEventListener('input', validateForm);
            document.getElementById('bikePrice').addEventListener('input', validateForm);
            document.getElementById('bikeYear').addEventListener('input', validateForm);
            document.getElementById('engineCapacity').addEventListener('input', validateForm);
            document.getElementById('mileage').addEventListener('input', validateForm);
            document.getElementById('fuelTankCapacity').addEventListener('input', validateForm);
            document.getElementById('bikeColor').addEventListener('input', validateForm);
            document.getElementById('bikeDescription').addEventListener('input', validateForm);
            document.getElementById('frontImage').addEventListener('input', validateForm);
            document.getElementById('backImage').addEventListener('input', validateForm);
            document.getElementById('leftImage').addEventListener('input', validateForm);
            document.getElementById('rightImage').addEventListener('input', validateForm);
        });

        // Function to validate the entire form
        function validateForm() {
            // Get all field values
            const model = document.getElementById('bikeModel').value.trim();
            const bikeType = document.getElementById('bikeType').value.trim();
            const price = document.getElementById('bikePrice').value.trim();
            const year = document.getElementById('bikeYear').value.trim();
            const engine = document.getElementById('engineCapacity').value.trim();
            const mileage = document.getElementById('mileage').value.trim();
            const tankCapacity = document.getElementById('fuelTankCapacity').value.trim();
            const color = document.getElementById('bikeColor').value.trim();
            const description = document.getElementById('bikeDescription').value.trim();

            // Check file inputs (all are required)
            const frontImg = document.getElementById('frontImage').files.length > 0;
            const backImg = document.getElementById('backImage').files.length > 0;
            const leftImg = document.getElementById('leftImage').files.length > 0;
            const rightImg = document.getElementById('rightImage').files.length > 0;

             // Get error messages
             const modelError = document.getElementById('bikeModelError').innerText;
             const colorError = document.getElementById('bikeColorError').innerText;

             const submitBtn = document.getElementById('submitBtn');

             // Check required fields
             const allRequiredFilled = model && bikeType && price && year && engine &&
                              mileage && tankCapacity && color && description &&
                              frontImg && backImg && leftImg && rightImg;

             // Check if there are no validation errors
             const noErrors = !modelError && !colorError;

            // For debugging:
            console.log('Required fields filled:', allRequiredFilled);
            console.log('No errors:', noErrors);
            console.log('Model error:', modelError);
            console.log('Color error:', colorError);

             // Enable button only if ALL fields are filled and no errors
             submitBtn.disabled = !(allRequiredFilled && noErrors);

             // Debugging logs
             console.log('All fields status:', {
                 model, bikeType, price, year, engine,
                 mileage, tankCapacity, color, description,
                 frontImg, backImg, leftImg, rightImg
             });
             console.log('Errors:', {modelError, colorError});
             console.log('Button should be enabled:', allRequiredFilled && noErrors);
       }
</script>
</body>
</html>