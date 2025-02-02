<?php

header('Content-Type: application/json; charset=utf-8');

$con = mysqli_connect("localhost", "root", "", "teacher");

$sql = "SELECT * FROM teacher";

$result = mysqli_query($con, $sql);

$teacher_data = array();

foreach($result as $item){

    $teacherInfo['id'] = $item['Id'];
    $teacherInfo['name'] = $item['Name'];
    $teacherInfo['email'] = $item['Email'];
    $teacherInfo['password'] = $item['Password'];
    $teacherInfo['mobile_number'] = $item['mobile_number'];
    $teacherInfo['department'] = $item['Department'];


    array_push($teacher_data, $teacherInfo);
}

echo json_encode($teacher_data);

?>