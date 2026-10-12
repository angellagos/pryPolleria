<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport"
              content="width=device-width, initial-scale=1.0">

        <title>Gestión de pedidos | Pollería</title>

        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f5f5f5;
                margin: 0;
                padding: 30px;
                color: #333;
            }

            .contenedor {
                max-width: 1100px;
                margin: auto;
                background: white;
                padding: 25px;
                border-radius: 10px;
                box-shadow: 0 2px 8px #00000015;
            }

            h1 {
                color: #d35400;
            }

            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
            }

            th, td {
                padding: 12px;
                border-bottom: 1px solid #ddd;
                text-align: left;
            }

            th {
                background-color: #d35400;
                color: white;
            }

            tr:hover {
                background-color: #f9f9f9;
            }

            .vacio {
                text-align: center;
                padding: 25px;
            }
        </style>
    </head>

    <body>
        <div class="contenedor">

            <div style="display: flex;
                 justify-content: space-between;
                 align-items: center;
                 margin-bottom: 20px;">

                <h1 style="margin: 0; color: #e87524;">
                    Gestión de pedidos
                </h1>

                <a href="${pageContext.request.contextPath}/logout"
                   style="background: #e87524;
                   color: white;
                   padding: 10px 16px;
                   border-radius: 6px;
                   text-decoration: none;
                   font-weight: bold;">
                    Cerrar sesión
                </a>

            </div>
            <c:if test="${param.registro == 'exito'}">
                <p style="color: #218838;">
                    Pedido registrado correctamente.
                    Número: ${param.idPedido}
                </p>
            </c:if>
            <h2>Registrar nuevo pedido</h2>

            <form action="${pageContext.request.contextPath}/pedidos"
                  method="post">

                <label for="tipoAtencion">Tipo de atención:</label>

                <select id="tipoAtencion" name="tipoAtencion" required>
                    <option value="">Seleccionar tipo</option>
                    <option value="MESA">Mesa</option>
                    <option value="PARA_LLEVAR">Para llevar</option>
                    <option value="DELIVERY">Delivery</option>
                </select>

                <br><br>

                <label for="idMesa">Mesa:</label>

                <select id="idMesa" name="idMesa">
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

                <br><br>

                <label for="direccionDelivery">
                    Dirección de delivery:
                </label>

                <input type="text"
                       id="direccionDelivery"
                       name="direccionDelivery"
                       maxlength="255">

                <br><br>

                <label for="observacion">Observaciones:</label>

                <textarea id="observacion"
                          name="observacion"
                          rows="3"
                          maxlength="500"></textarea>

                <hr>

                <h3>Productos del pedido</h3>

                <div id="productosPedido">

                    <div class="producto-pedido">

                        <label>Producto:</label>

                        <select name="idProducto" required>
                            <option value="">Seleccionar producto</option>

                            <c:forEach var="producto" items="${productos}">
                                <option value="${producto.idProducto}">
                                    ${producto.nombre} - S/ ${producto.precio}
                                </option>
                            </c:forEach>
                        </select>

                        <label>Cantidad:</label>

                        <input type="number"
                               name="cantidad"
                               min="1"
                               value="1"
                               required>

                    </div>

                </div>

                <br>

                <button type="button" onclick="agregarProducto()">
                    Agregar otro producto
                </button>

                <br><br>

                <button type="submit">
                    Registrar pedido
                </button>

            </form>

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
            <p>Consulta los pedidos registrados en la pollería.</p>

            <c:choose>
                <c:when test="${empty pedidos}">
                    <div class="vacio">
                        No hay pedidos registrados.
                    </div>
                </c:when>

                <c:otherwise>
                    <table>
                        <thead>
                            <tr>
                                <th>N.º</th>
                                <th>Fecha</th>
                                <th>Tipo de atención</th>
                                <th>Mesa</th>
                                <th>Usuario ID</th>
                                <th>Estado ID</th>
                                <th>Total</th>
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
                                            <c:otherwise>
                                                No aplica
                                            </c:otherwise>
                                        </c:choose>
                                    </td>

                                    <td>${pedido.idUsuario}</td>
                                    <td>${pedido.idEstado}</td>
                                    <td>S/ ${pedido.total}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>

        </div>
    </body>
</html>