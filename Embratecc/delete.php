<?php 

include "conexao.php";

$id=$_POST['id'];



$sql4= "DELETE FROM  cadastro WHERE id=:id";

$stmt=$PDO->prepare($sql4);

$stmt ->bindParam(':id', $id);

if($stmt->execute()){
    $dados=array("DELETE"=>"OK");
}else{
    $dados=array("DELETE"=>"ERRO");
}

echo json_encode($dados);
?>