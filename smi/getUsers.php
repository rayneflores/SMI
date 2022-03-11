<?php

if($_SERVER['REQUEST_METHOD'] == 'GET'){
    require_once("db.php");

    $query = "SELECT * FROM tblusers;";
    $result = $mysql->query($query);

    if($mysql->affected_rows > 0) {
        while($row = $result->fetch_assoc()) {
            $array[] = $row;
        }
        echo json_encode(['Users'=>$array]);
    } else {
        echo "No se Encontraron Registros";
    }

    $result->close();
    $mysql->close();
}
?>