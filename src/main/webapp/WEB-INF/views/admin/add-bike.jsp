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
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/admin-dashboard.css">
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
                <h4><i class="fas fa-motorcycle me-2"></i>Add New Bike</h4>
                <a href="${pageContext.request.contextPath}/admin/manage-bikes" class="btn btn-outline-secondary">
                    <i class="fas fa-arrow-left me-1"></i> Back to List
                </a>
            </div>

            <div class="card">
                <div class="card-body">
                    <form id="bikeForm" action="${pageContext.request.contextPath}/admin/add-bike" method="post" enctype="multipart/form-data">
                        <div class="row g-3">
                            <!-- Bike Model -->
                            <div class="col-md-6">
                                <label for="bikeModel" class="form-label required-field">Model</label>
                                <input type="text" class="form-control" id="bikeModel" name="bikeModel" placeholder="Enter Bike model Name" required>
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
                                <input type="text" class="form-control" id="bikeColor" name="bikeColor" required>
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
                                        <input class="form-control" type="file" name="frontImage" id="frontImage" required accept="image/*" />
                                        <div class="form-text">Clear image showing the front of the bike</div>
                                        <div class="image-preview-container" id="frontPreview"></div>
                                    </div>

                                    <!-- Back Image -->
                                    <div class="col-md-6 mb-3">
                                        <label for="backImage" class="form-label required-field">Back View</label>
                                        <input class="form-control" type="file" name="backImage" id="backImage" required accept="image/*" />
                                        <div class="form-text">Clear image showing the rear of the bike</div>
                                        <div class="image-preview-container" id="backPreview"></div>
                                    </div>

                                    <!-- Left Image -->
                                    <div class="col-md-6 mb-3">
                                        <label for="leftImage" class="form-label">Left Side View</label>
                                        <input class="form-control" type="file" name="leftImage" id="leftImage" accept="image/*" />
                                        <div class="form-text">Optional image showing the left side</div>
                                        <div class="image-preview-container" id="leftPreview"></div>
                                    </div>

                                    <!-- Right Image -->
                                    <div class="col-md-6 mb-3">
                                        <label for="rightImage" class="form-label">Right Side View</label>
                                        <input class="form-control" type="file" name="rightImage" id="rightImage" accept="image/*" />
                                        <div class="form-text">Optional image showing the right side</div>
                                        <div class="image-preview-container" id="rightPreview"></div>
                                    </div>
                                </div>
                            </div>

                            <!-- Form Buttons -->
                            <div class="col-12 mt-4">
                                <button type="submit" class="btn btn-primary me-2">
                                    <i class="fas fa-save me-1"></i> Save Bike
                                </button>
                                <button type="reset" class="btn btn-outline-secondary">
                                    <i class="fas fa-undo me-1"></i> Reset
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/admin-sidebar.js"></script>
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
    });
</script>
</body>
</html>