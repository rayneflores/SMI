<?php
    $mysql = new mysqli("localhost", "civaweb_smiadm", "ALEycj8spCI6", "civaweb_ki341951_rexistenc");

    if ($mysql -> connect_error) {
        die("Error de Conexion " . $mysql->connect_error);
    }
?>
