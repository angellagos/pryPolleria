<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Gestión de mesas | Pollería</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
              rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

        <link href="${pageContext.request.contextPath}/css/mesaStyle.css"
              rel="stylesheet" type="text/css"/>

    </head>

    <body class="bg-light">

        <div class="container py-5">

            <div class="bg-white rounded-4 shadow-sm p-4 p-md-5">

                <div class="d-flex justify-content-between
                     align-items-center flex-wrap gap-3 mb-4">

                    <div>
                        <h1 class="fw-bold mb-2" style="color: #e87524;">
                            Gestión de mesas
                        </h1>
                        <p class="text-secondary mb-0">
                            Consulta el estado y la capacidad de las mesas.
                        </p>
                    </div>

                    <a href="${pageContext.request.contextPath}/pedidos"
                       class="btn btn-outline-secondary">
                        Volver a pedidos
                    </a>

                </div>

                <c:if test="${param.registro == 'exito'}">
                    <div class="alert alert-success alert-dismissible fade show"
                         role="alert">
                        Mesa registrada correctamente.
                        <button type="button" class="btn-close"
                                data-bs-dismiss="alert"
                                aria-label="Cerrar"></button>
                    </div>
                </c:if>

                <c:if test="${param.edicion == 'exito'}">
                    <div class="alert alert-success alert-dismissible fade show"
                         role="alert">
                        Datos de la mesa actualizados correctamente.
                        <button type="button" class="btn-close"
                                data-bs-dismiss="alert"
                                aria-label="Cerrar"></button>
                    </div>
                </c:if>

                <c:if test="${param.actualizacion == 'exito'}">
                    <div class="alert alert-success alert-dismissible fade show"
                         role="alert">
                        Estado de la mesa actualizado correctamente.
                        <button type="button" class="btn-close"
                                data-bs-dismiss="alert"
                                aria-label="Cerrar"></button>
                    </div>
                </c:if>

                <c:if test="${param.eliminacion == 'exito'}">
                    <div class="alert alert-success alert-dismissible fade show"
                         role="alert">
                        Mesa eliminada correctamente.
                        <button type="button" class="btn-close"
                                data-bs-dismiss="alert"
                                aria-label="Cerrar"></button>
                    </div>
                </c:if>

                <div class="card border-0 shadow-sm mb-5">
                    <div class="card-header bg-white border-bottom py-3">
                        <h2 class="h5 mb-0 fw-bold">Registrar mesa</h2>
                    </div>

                    <div class="card-body">
                        <form action="${pageContext.request.contextPath}/mesas"
                              method="post">

                            <div class="row g-3">

                                <div class="col-md-5">
                                    <label for="numero" class="form-label">
                                        Número de mesa
                                    </label>

                                    <input type="number"
                                           class="form-control"
                                           id="numero"
                                           name="numero"
                                           min="1"
                                           required>
                                </div>

                                <div class="col-md-5">
                                    <label for="capacidad" class="form-label">
                                        Capacidad de personas
                                    </label>

                                    <input type="number"
                                           class="form-control"
                                           id="capacidad"
                                           name="capacidad"
                                           min="1"
                                           required>
                                </div>

                                <div class="col-md-2 d-flex align-items-end">
                                    <button type="submit"
                                            class="btn w-100 text-white"
                                            style="background-color: #e87524;">
                                        Registrar
                                    </button>
                                </div>

                            </div>
                        </form>
                    </div>
                </div>

                <h2 class="h4 fw-bold mb-4">Mesas registradas</h2>

                <div class="row g-4">

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
                                <div class="col-12 col-md-6 col-xl-4">
                                    <div class="card h-100 shadow-sm border-0">
                                        <div class="card-body">

                                            <div class="d-flex justify-content-between align-items-center mb-3">
                                                <h2 class="h4 fw-bold mb-0">Mesa ${mesa.numero}</h2>

                                                <span class="badge rounded-pill
                                                      ${mesa.estado == 'LIBRE' ? 'text-bg-success' :
                                                        mesa.estado == 'OCUPADA' ? 'text-bg-danger' :
                                                        'text-bg-secondary'}">
                                                          ${mesa.estado}
                                                      </span>
                                                </div>

                                                <p class="text-secondary mb-4">
                                                    Capacidad: ${mesa.capacidad} personas
                                                </p>
                                                <form action="${pageContext.request.contextPath}/mesas"
                                                      method="post">

                                                    <input type="hidden" name="accion"
                                                           value="actualizarDatos">

                                                    <input type="hidden" name="idMesa"
                                                           value="${mesa.idMesa}">

                                                    <label for="numero-${mesa.idMesa}" class="form-label">
                                                        Número de mesa
                                                    </label>

                                                    <input type="number"
                                                           class="form-control mb-3"
                                                           id="numero-${mesa.idMesa}"
                                                           name="numero"
                                                           value="${mesa.numero}"
                                                           min="1"
                                                           required>

                                                    <label for="capacidad-${mesa.idMesa}" class="form-label">
                                                        Capacidad de personas
                                                    </label>

                                                    <input type="number"
                                                           class="form-control mb-3"
                                                           id="capacidad-${mesa.idMesa}"
                                                           name="capacidad"
                                                           value="${mesa.capacidad}"
                                                           min="1"
                                                           required>

                                                    <button type="submit" class="btn btn-guardar w-100">
                                                        Guardar cambios
                                                    </button>

                                                </form>


                                                <hr>

                                                <form action="${pageContext.request.contextPath}/mesas"
                                                      method="post"
                                                      class="mb-3">

                                                    <input type="hidden" name="accion"
                                                           value="actualizarEstado">

                                                    <input type="hidden" name="idMesa"
                                                           value="${mesa.idMesa}">

                                                    <label for="estado-${mesa.idMesa}">
                                                        Cambiar estado:
                                                    </label>

                                                    <select class="form-select mb-3" id="estado-${mesa.idMesa}"
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



                                                    <button  type="submit" class="btn btn-guardar w-100">
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

                                                    <button class="btn btn-danger w-100" type="submit">
                                                        Eliminar mesa
                                                    </button>

                                                </form>

                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:otherwise>


                        </c:choose>

                    </div>

                </div>
        </body>
    </html>