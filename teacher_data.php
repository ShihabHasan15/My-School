<?php

$con = mysqli_connect("localhost", "root", "", "teacher");


$Id = $_GET['id'];
$Name = $_GET['name'];
$Email = $_GET['email'];
$Password = $_GET['password'];
$Mobile_number = $_GET['mb_number'];
$Department = $_GET['department'];

$sql = "INSERT INTO teacher (Id, Name, Email, Password, mobile_number, Department) VALUES 
('$Id', '$Name', '$Email', '$Password', '$Mobile_number', '$Department')";

$result = mysqli_query($con, $sql);

if($result){
    echo "Sign Up Successfully";
}else{
    echo "Already Exist user";
}

?>