<nav class="navbar navbar-expand-lg navbar-light bg-white shadow-sm">
    <div class="container-fluid">
        <button type="button" id="sidebarToggle" class="btn">
            <i class="bi bi-list"></i>
        </button>
        <div class="user-profile ms-auto">
            <!-- <span class="me-2">${admin.adminName}</span> -->
            <span class="me-2">Admin</span>
            <img src="${pageContext.request.contextPath.concat('/static/images/user-avatar.png')}" class="rounded-circle" alt="Admin" width="45" height="45">
        </div>
    </div>
</nav>