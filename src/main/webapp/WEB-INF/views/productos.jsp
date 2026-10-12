<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Productos | Pollería</title>

        <link href="${pageContext.request.contextPath}/css/productoStyle.css"
              rel="stylesheet" type="text/css"/>
    </head>

    <body>
        <div class="contenedor">
            <h1>Gestión de Menu</h1>
            <p>Alimentos registrados en la pollería.</p>

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

            <h2>Registrar producto</h2>

            <form action="${pageContext.request.contextPath}/productos"
                  method="post">

                <label for="nombre">Nombre del producto:</label><br>
                <input type="text" id="nombre" name="nombre"
                       maxlength="100" required>
                <br><br>

                <label for="descripcion">Descripción:</label><br>
                <textarea id="descripcion" name="descripcion"
                          rows="3" maxlength="500"></textarea>
                <br><br>

                <label for="precio">Precio (S/):</label><br>
                <input type="number" id="precio" name="precio"
                       min="0.01" step="0.01" required>
                <br><br>

                <label for="idCategoria">Categoría:</label><br>
                <select id="idCategoria" name="idCategoria" required>
                    <option value="">Selecciona una categoría</option>

                    <c:forEach var="categoria" items="${categorias}">
                        <option value="${categoria.idCategoria}">
                            ${categoria.nombre}
                        </option>
                    </c:forEach>
                </select>
                <br><br>

                <button type="submit">Registrar producto</button>
            </form>

            <hr>
            <h2>Productos registrados</h2>

            <table>
                <thead>
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
                                <td colspan="6" class="vacio">
                                    No hay productos registrados.
                                </td>
                            </tr>
                        </c:when>

                        <c:otherwise>
                            <c:forEach var="producto" items="${productos}">
                                <tr>
                                    <td>${producto.idProducto}</td>
                                    <td>${producto.nombre}</td>
                                    <td>${producto.categoria.nombre}</td>
                                    <td class="precio">
                                        S/ ${producto.precio}
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${producto.disponible}">
                                                <span class="disponible">Disponible</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="agotado">No disponible</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/productos?accion=editar&id=${producto.idProducto}">
                                            Editar
                                        </a>

                                        <form action="${pageContext.request.contextPath}/productos"
                                              method="post"
                                              style="display: inline;"
                                              onsubmit="return confirm('¿Estás seguro de que deseas eliminar este producto?');">

                                            <input type="hidden" name="accion" value="eliminar">
                                            <input type="hidden" name="idProducto"
                                                   value="${producto.idProducto}">

                                            <button type="submit"
                                                    style="background-color: #c0392b; color: white;
                                                    border: none; padding: 7px 10px;
                                                    border-radius: 4px; cursor: pointer;">
                                                Eliminar
                                            </button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
    </body>
</html>
