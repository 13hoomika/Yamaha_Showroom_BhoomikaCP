document.addEventListener('DOMContentLoaded', function() {
    const sidebar = document.getElementById('sidebar');
    const content = document.getElementById('content');
    const sidebarToggle = document.getElementById('sidebarToggle');
    const sidebarCollapse = document.getElementById('sidebarCollapse');
    const overlay = document.createElement('div');
    overlay.className = 'overlay';
    document.body.appendChild(overlay);

    // Initialize sidebar state based on screen size
    function initSidebar() {
        if (window.innerWidth < 992) {
            // Mobile - start collapsed
            sidebar.classList.remove('active');
            overlay.classList.remove('active');
        } else {
            // Desktop - start expanded
            sidebar.classList.add('active');
            // Check localStorage for collapsed state
            if (localStorage.getItem('sidebarCollapsed') === 'true') {
                toggleSidebar();
            }
        }
    }

    // Toggle sidebar function
    function toggleSidebar() {
        const isMobile = window.innerWidth < 992;

        if (isMobile) {
            // Mobile behavior
            sidebar.classList.toggle('active');
            overlay.classList.toggle('active');
        } else {
            // Desktop behavior
            sidebar.classList.toggle('collapsed');
            content.classList.toggle('expanded');
            // Store state in localStorage
            localStorage.setItem('sidebarCollapsed', sidebar.classList.contains('collapsed'));
        }
    }

    // Close sidebar when clicking outside on mobile
    function handleOutsideClick(e) {
        if (window.innerWidth < 992 &&
            !sidebar.contains(e.target) &&
            e.target !== sidebarToggle &&
            sidebar.classList.contains('active')) {
            toggleSidebar();
        }
    }

    // Initialize sidebar
    initSidebar();

    // Event listeners
    sidebarToggle.addEventListener('click', function(e) {
        e.preventDefault();
        toggleSidebar();
    });

    sidebarCollapse.addEventListener('click', function(e) {
        e.preventDefault();
        toggleSidebar();
    });

    overlay.addEventListener('click', function() {
        if (window.innerWidth < 992) {
            toggleSidebar();
        }
    });

    // Handle window resize
    window.addEventListener('resize', function() {
        if (window.innerWidth >= 992) {
            // Switching to desktop - ensure sidebar is visible
            overlay.classList.remove('active');
            sidebar.classList.remove('active');
            sidebar.classList.remove('collapsed');
            content.classList.remove('expanded');
        } else {
            // Switching to mobile
            if (sidebar.classList.contains('active')) {
                overlay.classList.add('active');
            }
        }
    });

    // Close sidebar when clicking menu items on mobile
    document.querySelectorAll('.sidebar-menu a').forEach(link => {
        link.addEventListener('click', function(e) {
            if (window.innerWidth < 992) {
                toggleSidebar();
            }
        });
    });

    // Prevent content scrolling when sidebar is open on mobile
    document.addEventListener('scroll', function(e) {
        if (window.innerWidth < 992 && sidebar.classList.contains('active')) {
            window.scrollTo(0, 0);
        }
    });
});