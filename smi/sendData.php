<?php
    if($_SERVER['REQUEST_METHOD'] == 'POST'){
        require_once("db.php");

        $query = "DELETE FROM tblproducts";

        $res = $mysql->query($query);

        if ($res === true) {
            echo "200";
        } else {
            echo "400";
        }

        $mysql->close();
    }
?>