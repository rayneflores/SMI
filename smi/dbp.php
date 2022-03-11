<?php
    $mysql = new mysqli("5.188.225.128:2083", "ki341951", "lEdbwvJnPcKO", "ki341951_rexistenc");

    if ($mysql -> connect_error) {
        die("Error de Conexion " . $mysql->connect_error);
    } else {
        echo "Conexion satisfactoria!!!";
    }
?>