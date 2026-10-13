<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Productos | Pollería</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
              rel="stylesheet">


        <link href="${pageContext.request.contextPath}/css/productoStyle.css"
              rel="stylesheet" type="text/css"/>
    </head>

    <body class="bg-light">

        <jsp:include page="menu.jsp" />

        <div class="container py-4">
            <div class="bg-white rounded-4 shadow-sm p-4 p-md-5">

                <div class="mb-4">
                    <h1 class="fw-bold mb-2" style="color: #e87524;">
                        Gestión de productos
                    </h1>
                    <p class="text-secondary mb-0">
                        Administra los alimentos registrados en la pollería.
                    </p>
                </div>

                <c:if test="${not empty error}">
                    <p style="color: #c0392b;">${error}</p>
                </c:if>

                <c:if test="${param.registro == 'exito'}">
                    <p style="color: #218838;">
                        Producto registrado correctamente.
                    </p>
                </c:if>

                <c:if test="${param.eliminacion == 'exito'}">
                    <p style="color: #218838;">
                        Producto eliminado correctamente.
                    </p>
                </c:if>

                <div class="card border-0 shadow-sm mb-5">

                    <div class="card-header bg-white py-3">
                        <h2 class="h5 fw-bold mb-0">Registrar producto</h2>
                    </div>

                    <div class="card-body">
                        <form action="${pageContext.request.contextPath}/productos"
                              method="post">

                            <div class="row g-3">

                                <div class="col-md-6">
                                    <label for="nombre" class="form-label">
                                        Nombre del producto
                                    </label>
                                    <input type="text"
                                           class="form-control"
                                           id="nombre"
                                           name="nombre"
                                           maxlength="100"
                                           required>
                                </div>

                                <div class="col-md-6">
                                    <label for="precio" class="form-label">
                                        Precio (S/)
                                    </label>
                                    <input type="number"
                                           class="form-control"
                                           id="precio"
                                           name="precio"
                                           min="0.01"
                                           step="0.01"
                                           required>
                                </div>

                                <div class="col-md-6">
                                    <label for="idCategoria" class="form-label">
                                        Categoría
                                    </label>
                                    <select id="idCategoria"
                                            name="idCategoria"
                                            class="form-select"
                                            required>
                                        <option value="">Selecciona una categoría</option>

                                        <c:forEach var="categoria" items="${categorias}">
                                            <option value="${categoria.idCategoria}">
                                                ${categoria.nombre}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="col-12">
                                    <label for="descripcion" class="form-label">
                                        Descripción
                                    </label>
                                    <textarea id="descripcion"
                                              name="descripcion"
                                              class="form-control"
                                              rows="3"
                                              maxlength="500"></textarea>
                                </div>

                                <div class="col-12 text-end">
                                    <button type="submit" class="btn btn-guardar">
                                        Registrar producto
                                    </button>
                                </div>

                            </div>
                        </form>
                    </div>
                </div>

                <hr>
                <h2 class="h4 fw-bold mb-3">Productos registrados</h2>

                <div class="table-responsive">
                    <table class="table table-hover align-middle">

                        <thead class="table-light">
                            <tr>
                                <th>ID</th>
                                <th>Producto</th>
                                <th>Categoría</th>
                                <th>Precio</th>
                                <th>Disponibilidad</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>

                        <tbody>
                            <c:choose>
                                <c:when test="${empty productos}">
                                    <tr>
                                        <td colspan="6" class="text-center text-secondary py-4">
                                            No hay productos registrados.
                                        </td>
                                    </tr>
                                </c:when>

                                <c:otherwise>
                                    <c:forEach var="producto" items="${productos}">
                                        <tr>
                                            <td>${producto.idProducto}</td>
                                            <td class="fw-semibold">${producto.nombre}</td>
                                            <td>${producto.categoria.nombre}</td>

                                            <td class="text-nowrap">
                                                S/ <fmt:formatNumber value="${producto.precio}"
                                                                  minFractionDigits="2"
                                                                  maxFractionDigits="2"/>
                                            </td>

                                            <td>
                                                <c:choose>
                                                    <c:when test="${producto.disponible}">
                                                        <span class="badge text-bg-success">
                                                            Disponible
                                                        </span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge text-bg-secondary">
                                                            No disponible
                                                        </span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>

                                            <td>
                                                <div class="d-flex gap-2 flex-wrap">
                                                    <a class="btn btn-sm btn-outline-warning"
                                                       href="${pageContext.request.contextPath}/productos?accion=editar&id=${producto.idProducto}">
                                                        Editar
                                                    </a>

                                                    <form action="${pageContext.request.contextPath}/productos"
                                                          method="post"
                                                          onsubmit="return confirm('¿Estás seguro de que deseas eliminar este producto?');">

                                                        <input type="hidden" name="accion"
                                                               value="eliminar">
                                                        <input type="hidden" name="idProducto"
                                                               value="${producto.idProducto}">

                                                        <button type="submit"
                                                                class="btn btn-sm btn-danger">
                                                            Eliminar
                                                        </button>
                                                    </form>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </tbody>

                    </table>
                </div>

            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    </body>

</html>