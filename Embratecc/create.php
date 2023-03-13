<?php

    include "conexao.php";


    $nome=$_POST['nome'];
    $cpf=$_POST['cpf'];
    $telefone=$_POST['telefone'];
    $email=$_POST['email'];

    $sql1="INSERT INTO cadastro (nome,cpf,telefone,email) VALUES(:NOME,:CPF,:TELEFONE,:EMAIL)";
    $stmt= $PDO->prepare($sql1);
    $stmt->bindParam(':NOME',$nome);
    $stmt->bindParam(':CPF',$cpf);
    $stmt->bindParam(':TELEFONE',$telefone);
    $stmt->bindParam(':EMAIL',$email);

    if($stmt->execute()){
        $id=$PDO->lastInsertId();
        $dados=array("CREATE"=>"OK", "ID"=>$id);
    }else {
        $dados=array("CREATE"=>"ERRO");
    }

    echo json_encode($dados);

?>