<?php

if($_SERVER['REQUEST_METHOD'] == 'GET'){
    require_once("db.php");

    $query = "SELECT * FROM tbllabelproducts";

    $result = $mysql->query($query);

    if($mysql->affected_rows > 0) {
        while($row = $result->fetch_assoc()) {
            $array[] = $row;
        }
        echo json_encode(['Products'=>$array]);
    } else {
        echo "No hay Registros para Mostrar";
    }
    $result->close();
    $mysql->close();
}
?>