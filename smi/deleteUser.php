<?php
    if($_SERVER['REQUEST_METHOD'] == 'POST'){
        require_once("db.php");

        $id = $_POST['id'];

        $query = "DELETE FROM tblusers WHERE id = '$id'";
        $res = $mysql->query($query);

        if($mysql->affected_rows > 0) {
            if($res === true) {
                echo "Usuario Eliminado";
            }
        } else {
            echo "No se encontro el usuario";
        }

        $mysql->close();
    }

?>