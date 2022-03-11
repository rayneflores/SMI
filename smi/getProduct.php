<?php

if($_SERVER['REQUEST_METHOD'] == 'GET'){
    require_once("db2.php");

    $cbarra = $_GET['cbarra'];

    $query = "SELECT M1.*, EX.pventa FROM cbarra_2 AS CB 
    LEFT JOIN mae_0001 AS M1
    ON CB.code = M1.code
    LEFT JOIN rexistenc EX
    ON EX.codigo = CB.code
    WHERE CB.cbarra = $cbarra";

    $result = $mysql->query($query);

    if($mysql->affected_rows > 0) {
        while($row = $result->fetch_assoc()) {
            $array = $row;
        }
        echo json_encode(['Product'=>$array]);
    } else {
        echo "No se Encontraron Registros";
    }
    $result->close();
    $mysql->close();
}
?>