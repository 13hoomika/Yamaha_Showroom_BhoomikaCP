<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="sidebar" id="sidebar">
    <div class="sidebar-header">
        <h3 class="text-center">Yamaha Motors</h3>
        <button type="button" id="sidebarCollapse" class="btn d-lg-none">
            <i class="fas fa-times"></i>
        </button>
    </div>
    <ul class="sidebar-menu">
        <li>
            <a href="${pageContext.request.contextPath}/admin/dashboard">
                <i class="fas fa-gauge-high"></i> Dashboard
            </a>
        </li>

        <!-- <li>
            <a href="${pageContext.request.contextPath}/admin/manage-bikes">
                <i class="fas fa-screwdriver-wrench"></i> Manage Bikes
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/admin/manage-showrooms">
                <i class="fas fa-warehouse"></i> Manage Showrooms
            </a>
        </li> -->
        <li>
            <a href="${pageContext.request.contextPath}/admin/view-allBikes">
                <i class="fas fa-motorcycle"></i> Bikes
            </a>
        </li>

        <li>
            <a href="${pageContext.request.contextPath}/admin/view-showrooms">
                <i class="fas fa-store"></i> Showrooms
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/admin/userRegister">
                <i class="fas fa-user"></i> Register user
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/admin/manage-users">
                <i class="fas fa-user-gear"></i> Manage Users
            </a>
        </li>
        <!--<li>
            <a href="${pageContext.request.contextPath}/admin/manage-followup">
                <i class="fas fa-calendar-check"></i> Follow Up
            </a>
        </li>-->
        <li>
            <a href="${pageContext.request.contextPath}/admin/logout">
                <i class="fas fa-arrow-right-from-bracket"></i> Logout
            </a>
        </li>
    </ul>
</div>
