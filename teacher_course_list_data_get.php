<?php

header('Content-Type: application/json; charset=utf-8');

$con = mysqli_connect("localhost", "root", "", "teacher");

$result = mysqli_query($con, "SELECT teacher.Id, teacher.Name, teacher_course_list.class, teacher_course_list.section, teacher_course_list.no_of_students, teacher_course_list.course_name
FROM teacher_course_list INNER JOIN teacher
ON teacher.Id = teacher_course_list.Id
WHERE teacher_course_list.Id = 101");

$teacher_courses = array();

foreach($result as $item){

    $teacherCourseInfo['id'] = $item['Id'];
    $teacherCourseInfo['name'] = $item['Name'];
    $teacherCourseInfo['class'] = $item['class'];
    $teacherCourseInfo['section'] = $item['section'];
    $teacherCourseInfo['no_of_students'] = $item['no_of_students'];
    $teacherCourseInfo['course_name'] = $item['course_name'];


    array_push($teacher_courses, $teacherCourseInfo);
}

echo json_encode($teacher_courses);

?>