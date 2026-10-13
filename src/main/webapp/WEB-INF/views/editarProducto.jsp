<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar producto | Pollería</title>

    <link href="${pageContext.request.contextPath}/css/editarPedidoStyle.css"
              rel="stylesheet" type="text/css"/>
</head>

<body>
    <div class="contenedor">
        <h1>Editar producto</h1>

        <p>Modifica los datos del producto seleccionado.</p>

        <c:if test="${not empty error}">
            <p style="color: #c0392b;">${error}</p>
        </c:if>

        <form action="${pageContext.request.contextPath}/productos"
              method="post">

            <input type="hidden" name="accion" value="actualizar">

            <input type="hidden" name="idProducto"
                   value="${producto.idProducto}">

            <label for="nombre">Nombre del producto:</label>
            <input type="text" id="nombre" name="nombre"
                   value="${producto.nombre}"
                   maxlength="100" required>

            <label for="descripcion">Descripción:</label>
            <textarea id="descripcion" name="descripcion"
                      rows="3" maxlength="500">${producto.descripcion}</textarea>

            <label for="precio">Precio (S/):</label>
            <input type="number" id="precio" name="precio"
                   value="${producto.precio}"
                   min="0.01" step="0.01" required>

            <label for="idCategoria">Categoría:</label>
            <select id="idCategoria" name="idCategoria" required>
                <c:forEach var="categoria" items="${categorias}">
                    <option value="${categoria.idCategoria}"
                        ${categoria.idCategoria == producto.categoria.idCategoria
                            ? 'selected' : ''}>
                        ${categoria.nombre}
                    </option>
                </c:forEach>
            </select>

            <label for="disponible">Disponibilidad:</label>
            <select id="disponible" name="disponible">
                <option value="true"
                    ${producto.disponible ? 'selected' : ''}>
                    Disponible
                </option>
                <option value="false"
                    ${not producto.disponible ? 'selected' : ''}>
                    No disponible
                </option>
            </select>

            <button type="submit">Guardar cambios</button>
        </form>

        <a class="volver"
           href="${pageContext.request.contextPath}/productos">
            Volver a productos
        </a>
    </div>
</body>
</html>