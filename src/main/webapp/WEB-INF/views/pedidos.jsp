<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport"
              content="width=device-width, initial-scale=1.0">

        <title>Gestión de pedidos | Pollería</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
              rel="stylesheet">

        <link href="${pageContext.request.contextPath}/css/pedidoStyle.css"
              rel="stylesheet" type="text/css"/>
    </head>

    <body class="bg-light">

        <div class="container py-5">

            <div class="bg-white rounded-4 shadow-sm p-4 p-md-5">

                <!-- Encabezado -->
                <div class="d-flex justify-content-between align-items-center
                     flex-wrap gap-3 mb-4">

                    <div>
                        <h1 class="fw-bold mb-2" style="color: #e87524;">
                            Gestión de pedidos
                        </h1>
                        <p class="text-secondary mb-0">
                            Registra pedidos y consulta su estado.
                        </p>
                    </div>

                    <a href="${pageContext.request.contextPath}/logout"
                       class="btn btn-guardar">
                        Cerrar sesión
                    </a>

                </div>

                <!-- Mensaje de éxito -->
                <c:if test="${param.registro == 'exito'}">
                    <div class="alert alert-success" role="alert">
                        Pedido registrado correctamente.
                        Número: ${param.idPedido}
                    </div>
                </c:if>

                <!-- Formulario -->
                <div class="card border-0 shadow-sm mb-5">

                    <div class="card-header bg-white py-3">
                        <h2 class="h5 fw-bold mb-0">Registrar nuevo pedido</h2>
                    </div>

                    <div class="card-body">

                        <form action="${pageContext.request.contextPath}/pedidos"
                              method="post">

                            <div class="row g-3">

                                <div class="col-md-4">
                                    <label for="tipoAtencion" class="form-label">
                                        Tipo de atención
                                    </label>

                                    <select id="tipoAtencion"
                                            name="tipoAtencion"
                                            class="form-select"
                                            required>
                                        <option value="">Seleccionar tipo</option>
                                        <option value="MESA">Mesa</option>
                                        <option value="PARA_LLEVAR">Para llevar</option>
                                        <option value="DELIVERY">Delivery</option>
                                    </select>
                                </div>

                                <div class="col-md-4">
                                    <label for="idMesa" class="form-label">
                                        Mesa
                                    </label>

                                    <select id="idMesa"
                                            name="idMesa"
                                            class="form-select">
                                        <option value="">Seleccionar mesa</option>

                                        <c:forEach var="mesa" items="${mesas}">
                                            <c:if test="${mesa.estado == 'LIBRE'}">
                                                <option value="${mesa.idMesa}">
                                                    Mesa ${mesa.numero}
                                                    - Capacidad: ${mesa.capacidad}
                                                </option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="col-md-4">
                                    <label for="direccionDelivery"
                                           class="form-label">
                                        Dirección de delivery
                                    </label>

                                    <input type="text"
                                           class="form-control"
                                           id="direccionDelivery"
                                           name="direccionDelivery"
                                           maxlength="255">
                                </div>

                                <div class="col-12">
                                    <label for="observacion" class="form-label">
                                        Observaciones
                                    </label>

                                    <textarea id="observacion"
                                              name="observacion"
                                              class="form-control"
                                              rows="2"
                                              maxlength="500"></textarea>
                                </div>

                            </div>

                            <hr class="my-4">

                            <h3 class="h6 fw-bold mb-3">
                                Productos del pedido
                            </h3>

                            <div id="productosPedido">

                                <div class="producto-pedido row g-3 align-items-end mb-3">

                                    <div class="col-md-8">
                                        <label class="form-label">Producto</label>

                                        <select name="idProducto"
                                                class="form-select"
                                                required>
                                            <option value="">Seleccionar producto</option>

                                            <c:forEach var="producto" items="${productos}">
                                                <option value="${producto.idProducto}">
                                                    ${producto.nombre} - S/ ${producto.precio}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <div class="col-md-2">
                                        <label class="form-label">Cantidad</label>

                                        <input type="number"
                                               name="cantidad"
                                               class="form-control"
                                               min="1"
                                               value="1"
                                               required>
                                    </div>

                                    <div class="col-md-2">
                                        <button type="button"
                                                class="btn btn-secundario w-100"
                                                onclick="agregarProducto()">
                                            + Producto
                                        </button>
                                    </div>

                                </div>

                            </div>

                            <div class="d-flex justify-content-end mt-4">
                                <button type="submit" class="btn btn-guardar">
                                    Registrar pedido
                                </button>
                            </div>

                        </form>

                    </div>
                </div>

                <script>
                    function agregarProducto() {
                        const contenedor =
                                document.getElementById("productosPedido");

                        const fila = contenedor.querySelector(
                                ".producto-pedido"
                                ).cloneNode(true);

                        fila.querySelector("select").selectedIndex = 0;
                        fila.querySelector('input[name="cantidad"]').value = 1;

                        contenedor.appendChild(fila);
                    }
                </script>

                <h2 class="h4 fw-bold mb-3">Pedidos registrados</h2>

                <div class="table-responsive">
                    <c:choose>
                        <c:when test="${empty pedidos}">
                            <div class="alert alert-light border text-center py-4">
                                No hay pedidos registrados.
                            </div>
                        </c:when>

                        <c:otherwise>
                            <table class="table table-hover align-middle">
                                <thead class="table-light">
                                    <tr>
                                        <th>N.º</th>
                                        <th>Fecha</th>
                                        <th>Tipo de atención</th>
                                        <th>Mesa</th>
                                        <th>Usuario ID</th>
                                        <th>Estado ID</th>
                                        <th class="text-end">Total</th>
                                    </tr>
                                </thead>

                                <tbody>
                                    <c:forEach var="pedido" items="${pedidos}">
                                        <tr>
                                            <td>${pedido.idPedido}</td>
                                            <td>${pedido.fechaPedido}</td>
                                            <td>${pedido.tipoAtencion}</td>

                                            <td>
                                                <c:choose>
                                                    <c:when test="${pedido.idMesa != null}">
                                                        ${pedido.idMesa}
                                                    </c:when>
                                                    <c:otherwise>No aplica</c:otherwise>
                                                </c:choose>
                                            </td>

                                            <td>${pedido.idUsuario}</td>
                                            <td>${pedido.idEstado}</td>

                                            <td class="text-end text-nowrap">
                                                S/ ${pedido.total}
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:otherwise>
                    </c:choose>
                </div> <!-- Cierra el panel blanco -->
            </div>     <!-- Cierra container -->

            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    </body>
</html>