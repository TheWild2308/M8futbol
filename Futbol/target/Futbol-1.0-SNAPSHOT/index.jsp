<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Estadísticas de Fútbol</title>
    <meta charset="UTF-8">
</head>
<body bgcolor="red;">
<h1>¿Quién crees que debe ganar el trofeo al Mejor Jugador FIFA?</h1>
<form action="futbol" method="POST">
    Nombre del Visitante: <input type="text" size="20" name="txtNombre">
    eMail: <input type="text" size="20" name="txtMail">
    <input type="radio" name="R1" value="Leo Messi" />Leo Messi<br/>
    <input type="radio" name="R1" value="Luis Suarez" />Luis Suárez<br/>
    <input type="radio" name="R1" value="Cristiano Ronaldo">Cristiano Ronaldo<br/>
    <input type="radio" name="R1" value="Otro" /> Otro: <input type="text" size="20" name="txtOtros" />
    <input  type="submit" name="B1" value="Votar" />
    <input type="reset" name="B2" value="Reset" />

</form>
</body>
</html>