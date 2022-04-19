<?php
    $mysql = new mysqli("5.188.225.128:2083", "smiadm", "ALEycj8spCI6", "civaweb_ki341951_smi");
    
    if ($mysql -> connect_error) {
        die("Error de Conexion " . $mysql->connect_error);
    }
?>
