<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Users | Yamaha Motors</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/admin-dashboard.css">
</head>
<body>
<div class="wrapper">
    <!-- Include sidebar fragment -->
    <jsp:include page="../fragments/admin-sidebar.jsp" />

    <!-- Page Content -->
    <div id="content" class="content">
        <!-- Include header fragment -->
        <jsp:include page="../fragments/admin-header.jsp" />

        <div class="container-fluid mt-4">
            <div class="card followup-card">
                <div class="card-header text-white">
                    <h5><i class="fas fa-user-circle me-2"></i>Follow-Up for ${user.userName}</h5>
                </div>
                <div class="card-body">
                    <!-- User Details -->
                    <div class="row mb-4 user-details p-3">
                        <div class="col-md-6">
                            <!--<p>
                                <strong><i class="fas fa-envelope me-2"></i>Email:</strong>
                                ${user.userEmail}
                            </p>-->
                            <p>
                                <strong><i class="fas fa-phone me-2"></i>Phone:</strong>
                                ${user.userPhoneNumber}
                            </p>
                            <p>
                                <strong><i class="fas fa-clipboard-list me-2"></i>Schedule For	:</strong>
                                ${user.scheduleType.displayName}
                            </p>
                            <c:if test="${user.scheduleType.name() == 'SCHEDULE_VISIT'}">
                               <p>
                                   <strong><i class="fa fa-calendar-alt me-2"></i>Schedule to :</strong>
                                   ${user.scheduleDate}
                               </p>
                            </c:if>
                        </div>

                        <div class="col-md-6">
                            <p>
                                <strong><i class="fas fa-motorcycle me-2"></i>Bike Interest:</strong>
                                <span class="badge bg-info">${user.bikeType.displayName}</span>
                            </p>
                            <p>
                                <strong><i class="fas fa-store me-2"></i>Showroom:</strong>
                                ${user.showroomName}
                            </p>
                           <c:if test="${user.scheduleType.name() == 'SCHEDULE_VISIT'}">
                              <strong><i class="fas fa-clock me-2"></i>Schedule at    :</strong>
                              ${user.scheduleTime}
                           </c:if>
                        </div>
                    </div>

                    <!-- Add New Follow-Up Form -->
                    <h5 class="mt-5 mb-3"><i class="fas fa-plus-circle me-2"></i>Add New Follow-Up</h5>
                    <form method="post" action="${pageContext.request.contextPath}/admin/followup-user">
                        <c:if test="${not empty success}">
                            <div class="alert alert-success">${success}</div>
                        </c:if>
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger">${error}</div>
                        </c:if>

                        <input type="hidden" name="id" value="${user.userId}">

                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label class="form-label">Follow-Up Date</label>
                                <input type="date" class="form-control" name="followupDate"
                                       value="${param.followupDate}" required>
                            </div>
                            <div class="col-md-6">
                                <label class="form-label">Call Status</label>
                                <select name="callStatus" class="form-select" required>
                                    <option value="">Select Status</option>
                                    <option value="Interested">Interested</option>
                                    <option value="Not Interested">Not Interested</option>
                                    <option value="Follow Up Required">Follow Up Required</option>
                                    <option value="No Response">No Response</option>
                                </select>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Notes</label>
                            <textarea name="notes" class="form-control" rows="3"
                                      placeholder="Enter follow-up details..." required></textarea>
                        </div>

                        <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                            <button type="submit" class="btn btn-primary me-md-2">
                                <i class="fas fa-save me-1"></i> Save
                            </button>
                            <a href="${pageContext.request.contextPath}/admin/manage-users"
                               class="btn btn-outline-secondary">
                               <i class="fas fa-arrow-left me-1"></i> Back to Users
                            </a>
                        </div>
                    </form>

                    <!-- Follow-Up History Table -->
                    <h5 class="mb-3"><i class="fas fa-history me-2"></i>Follow-Up History</h5>
                    <div class="table-responsive">
                        <table class="table table-striped table-hover">
                            <thead class="table-dark">
                                <tr>
                                    <th>Date</th>
                                    <th>Status</th>
                                    <th>Notes</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:choose>
                                    <c:when test="${not empty logs}">
                                        <c:forEach items="${logs}" var="log">
                                            <tr>
                                                <td>${log.followupDate}</td>
                                                <td>
                                                    <span class="badge
                                                        ${log.callStatus == 'Interested' ? 'bg-success' :
                                                          log.callStatus == 'Not Interested' ? 'bg-danger' : 'bg-warning'}">
                                                        ${log.callStatus}
                                                    </span>
                                                </td>
                                                <td>${log.notes}</td>
                                            </tr>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <tr>
                                            <td colspan="3" class="text-center text-muted">No follow-up records found</td>
                                        </tr>
                                    </c:otherwise>
                                </c:choose>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- JavaScript Libraries -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/admin-sidebar.js"></script>
<script>
    // Set default datetime to now
    document.addEventListener('DOMContentLoaded', function() {
        const now = new Date();
        const localDate = now.toISOString().slice(0, 10); // Format: yyyy-MM-dd
                document.querySelector('input[type="date"]').value = localDate;
    });
</script>
</body>
</html>