<?php

if($_SERVER['REQUEST_METHOD'] == 'GET'){
    require_once("db.php");

    $ean_13 = $_GET['ean_13'];

    $query = "SELECT stock_ FROM tblcountproducts WHERE ean_13 = '$ean_13'";

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