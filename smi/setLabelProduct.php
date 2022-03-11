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
        $poferta = $_POST['poferta'];

        $query = 
        "REPLACE INTO tbllabelproducts 
        SET code = $code, 
            activado = $activado, 
            codlocal = $codlocal, 
            detalle = '$detalle',
            dep = '$dep',
            ean_13 = $ean_13,
            linea = $linea,
            sucursal = '$sucursal',
            stock_ = $stock_,
            pventa = $pventa,
            p_oferta = $poferta";

        $res = $mysql->query($query);

        if ($mysql->affected_rows > 0) {
            if($res === true) {
                echo "Etiqueta Actualizada!!!";
            } else {
                echo "No se pudo Actualizar";
            }
        } else {
            echo "No se Encontraron Registros";
        }
        $mysql->close();
    }
?>