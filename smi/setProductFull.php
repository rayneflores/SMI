<?php
    if($_SERVER['REQUEST_METHOD'] == 'POST'){
        require_once("db.php");

        $code = $_POST['code'];
        $codlocal = $_POST['codlocal'];
        $detalle = $_POST['detalle'];
        $ean_13 = $_POST['ean_13'];
        $sucursal = $_POST['sucursal'];
        $stock_ = $_POST['stock_'];
        $pventa = $_POST['pventa'];
        $p_oferta = $_POST['p_oferta'];
        $avg_pro = $_POST['avg_pro'];
        $codBarra = $_POST['codBarra'];
        $pcadena = $_POST['pcadena'];
        $pedido = $_POST['pedido'];

        $query = 
        "REPLACE INTO tblreqproducts 
        SET code = $code, 
            codlocal = $codlocal, 
            detalle = '$detalle',
            ean_13 = $ean_13,
            sucursal = '$sucursal',
            stock_ = $stock_,
            pventa = $pventa,
            p_oferta = $p_oferta,
            avg_pro = $avg_pro,
            codBarra = $codBarra,
            pcadena = $pcadena,
            pedido = $pedido";

        $res = $mysql->query($query);

        if ($mysql->affected_rows > 0) {
            if($res === true) {
                echo "Pedido Actualizado!!!";
            } else {
                echo "No se pudo Actualizar";
            }
        } else {
            echo "No se Encontraron Registros";
        }
        $mysql->close();
    }
?>