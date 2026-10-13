<%@page contentType="text/html" pageEncoding="UTF-8"%>

<nav class="navbar navbar-expand-lg navbar-dark mb-4"
     style="background-color: #e87524;">

    <div class="container-fluid">

        <a class="navbar-brand fw-bold"
           href="${pageContext.request.contextPath}/pedidos">
            Pollería
        </a>

        <div class="navbar-nav me-auto">
            <a class="nav-link text-white"
               href="${pageContext.request.contextPath}/pedidos">
                Pedidos
            </a>

            <a class="nav-link text-white"
               href="${pageContext.request.contextPath}/mesas">
                Mesas
            </a>

            <a class="nav-link text-white"
               href="${pageContext.request.contextPath}/productos">
                Productos
            </a>
        </div>

        <a class="btn btn-light"
           href="${pageContext.request.contextPath}/logout">
            Cerrar sesión
        </a>

    </div>
</nav>