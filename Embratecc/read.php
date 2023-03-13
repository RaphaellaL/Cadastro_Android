<?php 
    include "conexao.php";

    $sql2= "SELECT * FROM cadastro";
    $dados=$PDO -> query( $sql2);

    $resultado=array();
    while($d=$dados->fetch(PDO::FETCH_OBJ)){
        $resultado[]= array("id"=>$d->id,"nome"=>$d->nome,"cpf"=>$d->cpf,"telefone"=>$d->telefone,"email"=>$d->email);

       
    }

    echo json_encode($resultado);

?>