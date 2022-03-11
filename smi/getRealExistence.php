<?php

if($_SERVER['REQUEST_METHOD'] == 'GET'){
    require_once("db2.php");

    $cbarra = $_GET['cbarra'];

    $query = "SELECT EX.st_actual FROM cbarra_2 AS CB
    LEFT JOIN rexistenc EX
    ON EX.codigo = CB.code
    WHERE CB.cbarra = $cbarra";

    $result = $mysql->query($query);

    if($mysql->affected_rows > 0) {
        while($row = $result->fetch_assoc()) {
            $array = $row;
        }
        echo json_encode(['Stock'=>$array]);
    } else {
        echo "{\"Stock\": {\"stock_\": 0}}";
    }

    $result->close();
    $mysql->close();
}
?>