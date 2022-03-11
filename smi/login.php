<?php

if($_SERVER['REQUEST_METHOD'] == 'POST'){
    require_once("db.php");

    $username = $_POST['username'];
    $password = $_POST['password'];
    

    $query = "SELECT * FROM tblusers WHERE username = '$username'";
    $result = $mysql->query($query);
    
    if($mysql->affected_rows > 0) {
        while($row = $result->fetch_assoc()) {
            $pass = $row['password'];
            if (password_verify($password, $pass)) {
                echo $row["id"];
            } else {
                echo "401";
            }
        }
    } else {
        echo "No se Encontraron Registros";
    }

    $result->close();
    $mysql->close();
}
?>