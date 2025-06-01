<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="sidebar" id="sidebar">
    <div class="sidebar-header">
        <h3 class="text-center">Yamaha Motors</h3>
        <button type="button" id="sidebarCollapse" class="btn d-lg-none">
            <i class="bi bi-x"></i>
        </button>
    </div>
    <ul class="sidebar-menu">
        <li>
            <a href="${pageContext.request.contextPath}/admin/dashboard">
                <i class="bi bi-speedometer2"></i> Dashboard
            </a>
        </li>

        <!-- <li>
            <a href="${pageContext.request.contextPath}/admin/manage-bikes">
                <i class="bi bi-bicycle"></i> Manage Bikes
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/admin/manage-showrooms">
                <i class="bi bi-shop"></i> Manage Showrooms
            </a>
        </li> -->
        <li>
            <a href="${pageContext.request.contextPath}/admin/view-allBikes">
                <i class="bi bi-bicycle"></i> Bikes
            </a>
        </li>

        <li>
            <a href="${pageContext.request.contextPath}/admin/view-showrooms">
                <i class="bi bi-shop"></i> Showrooms
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/admin/userRegister">
                <i class="bi bi-person-plus"></i> Register user
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/admin/manage-users">
                <i class="bi bi-person-gear"></i> Manage Users
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/admin/logout">
                <i class="bi bi-box-arrow-right"></i> Logout
            </a>
        </li>
    </ul>
</div>
