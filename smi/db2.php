<?php
    $mysql = new mysqli("192.168.18.2", "smiadmin", "123456", "ki341951_rexistenc");

    if ($mysql -> connect_error) {
        die("Error de Conexion " . $mysql->connect_error);
    }
?>