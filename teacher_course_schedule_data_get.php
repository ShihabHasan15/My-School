<?php
header('Content-Type: application/json; charset=utf-8');

$con = mysqli_connect("localhost", "root", "", "teacher");
$t_id = $_GET['t_id'];
$class_day = $_GET['day'];

$result = mysqli_query($con, "SELECT teacher.Id, teacher.Name, course_schedule.course_id, course.course_name, course_schedule.class_start_time, course_schedule.class_end_time, course_schedule.class_day, course_schedule.class
FROM course_schedule JOIN teacher
ON course_schedule.teacher_id = teacher.Id
JOIN course ON course_schedule.course_id = course.course_id
WHERE teacher.Id = $t_id AND course_schedule.class_day = $class_day");

$teacher_schedule_info = array();

foreach($result as $item){
    $schedule['Id'] = $item['Id'];
    $schedule['Name'] = $item['Name'];
    $schedule['course_id'] = $item['course_id'];
    $schedule['course_name'] = $item['course_name'];
    $schedule['class_start_time'] = $item['class_start_time'];
    $schedule['class_end_time'] = $item['class_end_time'];
    $schedule['class_day'] = $item['class_day'];
    $schedule['class'] = $item['class'];

    array_push($teacher_schedule_info, $schedule);
}

echo json_encode($teacher_schedule_info);

?>