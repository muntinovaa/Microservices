package com.example.microservices.student.controller;


import com.example.microservices.student.model.Student;
import com.example.microservices.student.model.Teacher;
import com.example.microservices.student.repository.StudentRepository;
import com.example.microservices.student.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/school")
public class SchoolController {


    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    @Autowired
    public SchoolController(StudentRepository studentRepository, TeacherRepository teacherRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    // Endpoint to add a student to a teacher's list
    @PostMapping("/teacher/{teacherId}/addStudent")
    public ResponseEntity<Teacher> addStudentToTeacher(@PathVariable Long teacherId, @RequestBody Student student) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + teacherId));

        // If the student is new, save it to the database
        if (student.getId() == 0 || !studentRepository.existsById(student.getId())) {
            student = studentRepository.save(student);
        } else {
            // If the student exists, fetch it from the database to ensure it's managed by Hibernate
            Student finalStudent = student;
            student = studentRepository.findById(student.getId())
                    .orElseThrow(() -> new RuntimeException("Student not found with id: " + finalStudent.getId()));
        }
        // Add the student to the teacher's list and save the teacher entity
        teacher.getStudents().add(student);
        teacherRepository.save(teacher);

        return ResponseEntity.ok(teacher);
    }


}
