<?php

header('Content-Type: application/json; charset=utf-8');

$con = mysqli_connect("localhost", "root", "", "student");

$sql = "SELECT * FROM student";

$result = mysqli_query($con, $sql);

$student_data = array();

foreach($result as $item){

    $studentInfo['roll'] = $item['Roll'];
    $studentInfo['name'] = $item['Full_name'];
    $studentInfo['email'] = $item['Email'];
    $studentInfo['password'] = $item['Password'];
    $studentInfo['mobile_number'] = $item['Mobile_number'];
    $studentInfo['class'] = $item['Class'];


    array_push($student_data, $studentInfo);
}

echo json_encode($student_data);

?>