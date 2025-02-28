<?php

header("Access-Control-Allow-Origin: *");
header('Content-Type: application/json; charset=utf-8');


$con = mysqli_connect("localhost", "root", "", "teacher");
$t_id = $_GET['t_id'];

$result = mysqli_query($con, "SELECT teacher.Id, teacher.Name, teacher.Email, teacher.mobile_number, 
teacher.Department, course_schedule.course_id, course.course_name, course_schedule.class_start_time, 
course_schedule.class_end_time, course_schedule.class_day, course_schedule.class, classes.no_of_students
FROM course_schedule JOIN teacher
ON course_schedule.teacher_id = teacher.Id
JOIN course ON course_schedule.course_id = course.course_id
JOIN classes ON course.class = classes.class
WHERE teacher.Id = $t_id");

$teacher_courses = array();

foreach($result as $item){

    $teacherCourseInfo['id'] = $item['Id'];//
    $teacherCourseInfo['name'] = $item['Name'];//
    $teacherCourseInfo['email'] = $item['Email'];
    $teacherCourseInfo['mobile_number'] = $item['mobile_number'];
    $teacherCourseInfo['department'] = $item['Department'];
    $teacherCourseInfo['course_id'] = $item['course_id'];
    $teacherCourseInfo['class_start_time'] = $item['class_start_time'];
    $teacherCourseInfo['class_end_time'] = $item['class_end_time'];
    $teacherCourseInfo['class_day'] = $item['class_day'];
    $teacherCourseInfo['class'] = $item['class'];//
    $teacherCourseInfo['no_of_students'] = $item['no_of_students'];//
    $teacherCourseInfo['course_name'] = $item['course_name'];//

    array_push($teacher_courses, $teacherCourseInfo);
}

echo json_encode($teacher_courses);

?>