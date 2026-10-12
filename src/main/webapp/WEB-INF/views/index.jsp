<%@ page contentType="text/html;charset=UTF-8" %>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.dwi.prytecnoservicio.ConexionDB"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>TecnoServicio</title>
        <link href="css/estilos.css" rel="stylesheet" type="text/css"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
              rel="stylesheet">
    </head>
    <body>
        <header class="encabezado">
            <h1>TecnoServicio JSP</h1>
            <p>Soluciones tecnológicas para empresas</p>
        </header>
        <nav class="menu">
            <a href="index.jsp">Inicio</a>
            <a href="servicios.jsp">Servicios</a>
            <a href="contacto.jsp">Contacto</a>
        </nav>
        <main class="contenido">
            <!--            <h2>Servicios tecnológicos</h2>
                        <section class="servicios">
            <%--                <c:forEach var="servicio" items="${servicios}">
                                <article class="tarjeta">
                                    <h3><c:out value="${servicio.nombre}" /></h3>
                                    <p><c:out value="${servicio.descripcion}" /></p>
                                </article>
                            </c:forEach>
            --%>               
                        </section>-->

            <section class="container my-5">
                <h2 class="mb-4">Nuestros servicios</h2>
                <div class="row g-4">

                    <c:forEach var="servicio" items="${servicios}">
                        <div class="col-12 col-md-6 col-lg-4">
                            <div class="card h-100">
                                <div class="card-body">
                                    <h3 class="card-title">
                                        <c:out value="${servicio.nombre}" />
                                    </h3>
                                    <p class="card-text">
                                        <c:out
                                            value="${servicio.descripcion}" />
                                    </p>
                                    <button type="button"
                                            class="btn btn-primary">
                                        Consultar
                                    </button>



                                </div>
                            </div>
                        </div>
                    </c:forEach>

                    <c:if test="${empty servicios}">
                        <div class="alert alert-warning" role="alert">
                            No existen servicios registrados.
                        </div>
                    </c:if>

                </div>
            </section>

            <section class="contenido">
                <h2>Consultar servicio</h2>
                <form action="ConsultaServlet" method="get">
                    <label for="nombre">Nombre:</label>
                    <input type="text" id="nombre" name="nombre">

                    <label for="servicio">Servicio:</label>
                    <select id="servicio" name="servicio">
                        <option value="Mantenimiento">Mantenimiento</option>
                        <option value="Redes">Redes</option>
                        <option value="Seguridad">Seguridad</option>
                    </select>

                    <!-- AGREGADO EN S05.s1-->
                    <select name="tipoAtencion">
                        <option value="Presencial">Presencial</option>
                        <option value="Virtual">Virtual</option>
                    </select>
                    <button type="submit">Enviar consulta</button>
                </form>
            </section>

            <h1>${titulo}</h1>
            <p>${mensaje}</p>
            <p>${origen}</p>

            <!--BLOQUE ATRIBUTOS CUANDO CONSULTA-->
            <%
 String mensajeConsulta = (String) request.getAttribute("mensajeConsulta");
 String nombreConsulta = (String) request.getAttribute("nombreConsulta");
 String servicioConsulta = (String) request.getAttribute("servicioConsulta");

 String tipoAtencion =(String) request.getAttribute("tipoAtencion");
    
 if (mensajeConsulta == null)
     mensajeConsulta = "No se ha registrado una consulta.";

 if (nombreConsulta == null) nombreConsulta = "";
 if (servicioConsulta == null) servicioConsulta = "";
 if (tipoAtencion == null) tipoAtencion = "";
            %>
            <h2>Resultado de la consulta</h2>
            <!--<p>$ {mensajeConsulta}</p>-->
            <c:out value="${mensajeConsulta}" />

            <c:if test="${not empty mensajeConsulta}">
                <h3>Resultado de la consulta con JSTL</h3>
                <p><c:out value="${mensajeConsulta}" /></p>

                <c:if test="${servicioConsulta == 'Redes'}">
                    <p>Servicio especializado en instalación y configuración de redes.</p>
                </c:if>
            </c:if>

        </main>
        <!--        PIE DE PAGINA    -->
        <footer class="pie"><p>TecnoServicio ©Derechos Reservados</p></footer>
    </body>
</html>
