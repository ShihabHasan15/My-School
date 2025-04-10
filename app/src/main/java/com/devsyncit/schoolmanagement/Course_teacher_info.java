package com.devsyncit.schoolmanagement;

public class Course_teacher_info {
    String teacher_name;
    String subject_name;

    public Course_teacher_info(String teacher_name, String subject_name){
        this.teacher_name = teacher_name;
        this.subject_name = subject_name;
    }
    public String getSubject_name() {
        return subject_name;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

}
