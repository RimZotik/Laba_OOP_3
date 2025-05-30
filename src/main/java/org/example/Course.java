package org.example;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToMany(mappedBy = "courses")
    private List<Student> students;

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
    
    public Course() {
    }

    public Course(String title, Teacher teacher) {
        this.title = title;
        this.teacher = teacher;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
