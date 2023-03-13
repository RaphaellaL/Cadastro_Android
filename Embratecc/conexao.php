<?php

$dsn="mysql:host=localhost;dbname=clientes;charset=utf8";
$user="root";
$senha="";

try{
    $PDO=new PDO($dsn, $user ,$senha);
     // echo "foi";

}catch(PDOException $erro){
    


}

?>
