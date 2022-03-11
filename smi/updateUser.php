<?php
    if($_SERVER['REQUEST_METHOD'] == 'POST'){
        require_once("db.php");

        $id = $_POST['id'];
        $name = $_POST['name'];
        $userName = $_POST['userName'];
        $password = $_POST['password'];
        $rol = $_POST['rol'];
        $cryptedPassword = password_hash($password, PASSWORD_DEFAULT);

        $query = "UPDATE tblusers SET username = '$userName', password = '$cryptedPassword', name = '$name', rol = $rol WHERE id = $id";

        $res = $mysql->query($query);

        if ($mysql->affected_rows > 0) {
            if($res === true) {
                echo "Usuario Actualizado!!!";
            } else {
                echo "No se pudo Actualizar";
            }
        } else {
            echo "No se Encontraron Registros";
        }
        $mysql->close();
    }
?>