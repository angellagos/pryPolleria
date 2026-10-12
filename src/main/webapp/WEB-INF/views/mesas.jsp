<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Gestión de mesas | Pollería</title>

        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f5f5f5;
                margin: 0;
                padding: 30px;
                color: #333;
            }

            .contenedor {
                max-width: 1000px;
                margin: auto;
                background: white;
                padding: 25px;
                border-radius: 10px;
                box-shadow: 0 2px 8px #00000015;
            }

            h1 {
                color: #d35400;
            }

            .mesas {
                display: grid;
                grid-template-columns: repeat(auto-fit, minmax(180px, 1fr));
                gap: 18px;
                margin-top: 25px;
            }

            .mesa {
                padding: 22px;
                border-radius: 10px;
                text-align: center;
                border: 1px solid #ddd;
            }

            .mesa h2 {
                margin-top: 0;
            }

            .libre {
                background-color: #eaf7ed;
                border-color: #218838;
            }

            .ocupada {
                background-color: #fdecea;
                border-color: #c0392b;
            }

            .inactiva {
                background-color: #eeeeee;
                border-color: #777777;
            }

            .estado {
                font-weight: bold;
            }

            .vacio {
                text-align: center;
                padding: 25px;
            }
        </style>
    </head>

    <body>
        <div class="contenedor">

            <h1>Gestión de mesas</h1>
            <p>Consulta el estado y la capacidad de las mesas.</p>

            <c:if test="${param.registro == 'exito'}">
                <p style="color: #218838;">
                    Mesa registrada correctamente.
                </p>
            </c:if>

            <c:if test="${param.edicion == 'exito'}">
                <p style="color: #218838;">
                    Datos de la mesa actualizados correctamente.
                </p>
            </c:if>

            <c:if test="${param.actualizacion == 'exito'}">
                <p style="color: #218838;">
                    Estado de la mesa actualizado correctamente.
                </p>
            </c:if>

            <c:if test="${param.eliminacion == 'exito'}">
                <p style="color: #218838;">
                    Mesa eliminada correctamente.
                </p>
            </c:if>

            <h2>Registrar mesa</h2>

            <form action="${pageContext.request.contextPath}/mesas"
                  method="post">

                <label for="numero">Número de mesa:</label>
                <input type="number" id="numero" name="numero"
                       min="1" required>

                <br><br>

                <label for="capacidad">Capacidad de personas:</label>
                <input type="number" id="capacidad" name="capacidad"
                       min="1" required>

                <br><br>

                <button type="submit">Registrar mesa</button>
            </form>

            <hr>

            <h2>Mesas registradas</h2>

            <div class="mesas">

                <c:choose>
                    <c:when test="${empty mesas}">
                        <div class="vacio">
                            No hay mesas registradas.
                        </div>
                    </c:when>

                    <c:otherwise>
                        <c:forEach var="mesa" items="${mesas}">

                            <c:set var="claseEstado" value="inactiva"/>

                            <c:if test="${mesa.estado == 'LIBRE'}">
                                <c:set var="claseEstado" value="libre"/>
                            </c:if>

                            <c:if test="${mesa.estado == 'OCUPADA'}">
                                <c:set var="claseEstado" value="ocupada"/>
                            </c:if>
                            <c:if test="${mesa.estado == 'INACTIVA'}">
                                <c:set var="claseEstado" value="inactiva"/>
                            </c:if>
                            <div class="mesa ${claseEstado}">
                                <h2>Mesa ${mesa.numero}</h2>

                                <p>
                                    Capacidad: ${mesa.capacidad} personas
                                </p>

                                <p class="estado">
                                    Estado: ${mesa.estado}
                                </p>
                                <form action="${pageContext.request.contextPath}/mesas"
                                      method="post">

                                    <input type="hidden" name="accion"
                                           value="actualizarDatos">

                                    <input type="hidden" name="idMesa"
                                           value="${mesa.idMesa}">

                                    <label for="numero-${mesa.idMesa}">
                                        Número:
                                    </label>

                                    <input type="number"
                                           id="numero-${mesa.idMesa}"
                                           name="numero"
                                           value="${mesa.numero}"
                                           min="1"
                                           required>

                                    <br><br>

                                    <label for="capacidad-${mesa.idMesa}">
                                        Capacidad:
                                    </label>

                                    <input type="number"
                                           id="capacidad-${mesa.idMesa}"
                                           name="capacidad"
                                           value="${mesa.capacidad}"
                                           min="1"
                                           required>

                                    <br><br>

                                    <button type="submit">
                                        Guardar cambios
                                    </button>

                                </form>


                                <hr>

                                <form action="${pageContext.request.contextPath}/mesas"
                                      method="post">

                                    <input type="hidden" name="accion"
                                           value="actualizarEstado">

                                    <input type="hidden" name="idMesa"
                                           value="${mesa.idMesa}">

                                    <label for="estado-${mesa.idMesa}">
                                        Cambiar estado:
                                    </label>

                                    <select id="estado-${mesa.idMesa}"
                                            name="estado" required>

                                        <option value="LIBRE"
                                                ${mesa.estado == 'LIBRE' ? 'selected' : ''}>
                                            Libre
                                        </option>

                                        <option value="OCUPADA"
                                                ${mesa.estado == 'OCUPADA' ? 'selected' : ''}>
                                            Ocupada
                                        </option>

                                        <option value="INACTIVA"
                                                ${mesa.estado == 'INACTIVA' ? 'selected' : ''}>
                                            Inactiva
                                        </option>

                                    </select>

                                    <br><br>

                                    <button type="submit">
                                        Guardar estado
                                    </button>

                                </form>
                                <hr>

                                <form action="${pageContext.request.contextPath}/mesas"
                                      method="post"
                                      onsubmit="return confirm('¿Estás seguro de que deseas eliminar esta mesa?');">

                                    <input type="hidden" name="accion" value="eliminar">

                                    <input type="hidden" name="idMesa"
                                           value="${mesa.idMesa}">

                                    <button type="submit"
                                            style="background-color: #c0392b; color: white;
                                            border: none; padding: 10px 15px;
                                            border-radius: 5px; cursor: pointer;">
                                        Eliminar mesa
                                    </button>

                                </form>

                            </div>
                        </c:forEach>
                    </c:otherwise>


                </c:choose>

            </div>

        </div>
    </body>
</html>