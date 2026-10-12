<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Iniciar sesión - Pollería</title>

        <style>
            * {
                box-sizing: border-box;
            }

            body {
                margin: 0;
                min-height: 100vh;
                display: flex;
                justify-content: center;
                align-items: center;
                font-family: Arial, sans-serif;
                background: #f4f4f4;
            }

            .login-container {
                width: 100%;
                max-width: 400px;
                padding: 35px;
                background: white;
                border-radius: 12px;
                box-shadow: 0 4px 18px rgba(0, 0, 0, 0.12);
            }

            h1 {
                margin-top: 0;
                margin-bottom: 8px;
                text-align: center;
                color: #333;
            }

            .subtitle {
                margin-bottom: 25px;
                text-align: center;
                color: #777;
            }

            label {
                display: block;
                margin-bottom: 7px;
                font-weight: bold;
                color: #444;
            }

            input {
                width: 100%;
                padding: 12px;
                margin-bottom: 18px;
                border: 1px solid #ccc;
                border-radius: 6px;
                font-size: 15px;
            }

            input:focus {
                outline: none;
                border-color: #e87524;
            }

            button {
                width: 100%;
                padding: 12px;
                border: none;
                border-radius: 6px;
                background: #e87524;
                color: white;
                font-size: 16px;
                font-weight: bold;
                cursor: pointer;
            }

            button:hover {
                background: #cc6118;
            }

            .error {
                margin-bottom: 18px;
                padding: 10px;
                border-radius: 6px;
                background: #ffe8e8;
                color: #b42318;
                font-size: 14px;
            }
        </style>
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