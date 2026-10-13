<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Iniciar sesión - Pollería</title>

        <link href="${pageContext.request.contextPath}/css/loginStyle.css"
              rel="stylesheet" type="text/css"/>
    </head>

    <body>

        <div class="login-container">

            <h1>Pollería</h1>
            <p class="subtitle">Inicia sesión para continuar</p>

            <% if (request.getAttribute("error") != null) { %>
            <div class="error">
                <%= request.getAttribute("error") %>
            </div>
            <% } %>

            <form action="${pageContext.request.contextPath}/login"
                  method="POST">

                <label for="correo">Correo electrónico</label>
                <input
                    type="email"
                    id="correo"
                    name="correo"
                    placeholder="usuario@correo.com"
                    required
                    autocomplete="username">

                <label for="password">Contraseña</label>
                <input
                    type="password"
                    id="password"
                    name="password"
                    placeholder="Ingresa tu contraseña"
                    required
                    autocomplete="current-password">

                <button type="submit">Iniciar sesión</button>

            </form>

        </div>

    </body>
</html>