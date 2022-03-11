<?php
    if($_SERVER['REQUEST_METHOD'] == 'POST'){
        require_once("db.php");

        $code = $_POST['code'];
        $activado = $_POST['activado'];
        $codlocal = $_POST['codlocal'];
        $detalle = $_POST['detalle'];
        $dep = $_POST['dep'];
        $ean_13 = $_POST['ean_13'];
        $linea = $_POST['linea'];
        $sucursal = $_POST['sucursal'];
        $stock_ = $_POST['stock_'];
        $pventa = $_POST['pventa'];

        $query = 
        "REPLACE INTO tblproducts 
        SET code = $code, 
            activado = $activado, 
            codlocal = $codlocal, 
            detalle = '$detalle',
            dep = '$dep',
            ean_13 = $ean_13,
            linea = $linea,
            sucursal = '$sucursal',
            stock_ = $stock_,
            pventa = $pventa";

        $res = $mysql->query($query);

        if ($mysql->affected_rows > 0) {
            if($res === true) {
                echo "Producto Actualizado!!!";
            } else {
                echo "No se pudo Actualizar";
            }
        } else {
            echo "No se Encontraron Registros";
        }
        $mysql->close();
    }
?>