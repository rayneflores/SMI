<?php
    if($_SERVER['REQUEST_METHOD'] == 'POST'){
        require_once("db.php");

        $name = $_POST['name'];
        $userName = $_POST['userName'];
        $password = $_POST['password'];
        $rol = $_POST['rol'];
        $cryptedPassword = password_hash($password, PASSWORD_DEFAULT);

        $query = "INSERT INTO 
            tblusers (name, username, password, rol) 
            VALUES ('$name','$userName','$cryptedPassword', $rol)";

        $res = $mysql->query($query);

        if ($res === true) {
            echo "Usuario Creado!!!";
        } else {
            echo "Error";
        }

        $mysql->close();
    }
?>