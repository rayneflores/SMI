<?php
    if($_SERVER['REQUEST_METHOD'] == 'POST'){
        require_once("db.php");

        $code = $_POST['code'];
        $detalle = $_POST['detalle'];
        $und_defect = $_POST['und_defect'];
        $responsable = $_POST['responsable'];

        $query = 
        "REPLACE INTO tblvencproducts 
        SET code = $code,
            detalle = '$detalle',
            und_defect = $und_defect,
            responsable = '$responsable'";

        $res = $mysql->query($query);

        if ($mysql->affected_rows > 0) {
            if($res === true) {
                echo "Producto Agregado!!!";
            } else {
                echo "No se pudo Actualizar";
            }
        } else {
            echo "No se Encontraron Registros";
        }
        $mysql->close();
    }
?>