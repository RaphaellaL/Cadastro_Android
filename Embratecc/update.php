<?php 

include "conexao.php";

$id=$_POST['id'];
$nome=$_POST['nome'];
$cpf=$_POST['cpf'];
$telefone=$_POST['telefone'];
$email=$_POST['email'];


$sql3= "UPDATE cadastro set nome=:nome,cpf=:cpf,telefone=:telefone,email=:email  WHERE id=:id";

$stmt=$PDO->prepare($sql3);

$stmt ->bindParam(':nome', $nome);
$stmt ->bindParam(':cpf', $cpf);
$stmt ->bindParam(':telefone', $telefone);
$stmt ->bindParam(':email', $email);
$stmt ->bindParam(':id', $id);

if($stmt->execute()){
    $dados=array("UPDATE"=>"OK");
}else{
    $dados=array("UPDATE"=>"ERRO");
}

echo json_encode($dados);
?>