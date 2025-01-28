<?php

$con = mysqli_connect("localhost", "root", "", "student");

$Roll = $_GET['roll'];
$Full_name = $_GET['name'];
$Email = $_GET['email'];
$Password = $_GET['password'];
$Mobile_number = $_GET['mb_number'];
$Class = $_GET['class'];

$sql = "INSERT INTO student (Roll, Full_name, Email, Password, Mobile_number, Class) VALUES 
('$Roll', '$Full_name', '$Email', '$Password', '$Mobile_number', '$Class')";

$result = mysqli_query($con, $sql);

if($result){
    echo "Sign Up Successfully";
}else{
    echo "Already Exist user";
}

?>