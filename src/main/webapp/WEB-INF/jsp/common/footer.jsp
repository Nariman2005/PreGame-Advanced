<%-- ../common/footer.jsp --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%-- ... (your existing footer HTML structure) ... --%>
<footer class="main-footer">
    <%-- ... footer content ... --%>
</footer>

<!-- App JavaScript - using absolute path -->
<script src="${pageContext.request.contextPath}/js/script.js"></script>

<!-- Inline JavaScript backup in case external file fails to load -->
<script>
    if (typeof window.scriptLoaded === 'undefined') {
        console.warn('External script /js/script.js did not load or set window.scriptLoaded. Using backup JS from footer.');
        // ... (your backup JS for mobile menu and user dropdown) ...
    }
</script>

</body> <%-- The closing body tag is here --%>
</html> <%-- The closing html tag is here --%>