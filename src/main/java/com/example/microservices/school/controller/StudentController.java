package com.example.microservices.school.controller;

import com.example.microservices.school.model.Student;
import com.example.microservices.school.model.Teacher;
import com.example.microservices.school.repository.TeacherRepository;
import com.example.microservices.school.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/school")
public class StudentController {

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    public StudentController(StudentRepository studentRepository, TeacherRepository teacherRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    // Create a new student
    @PostMapping("/students")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student savedStudent = studentRepository.save(student);
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }

    // Get a single student by ID
    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable long id) {
        Student student = studentRepository.findById(id)
                                           .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        return ResponseEntity.ok(student);
    }

    // Get all students
    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return ResponseEntity.ok(students); // Return the list with an OK status
    }

    // Update a student
    @PutMapping("/students/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable long id, @RequestBody Student studentDetails) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));

        // Assuming the Student class has setters for the fields you want to update
        student.setName(studentDetails.getName());
        Student updatedStudent = studentRepository.save(student);

        return ResponseEntity.ok(updatedStudent);
    }

    // Delete a student
    @DeleteMapping("/students/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable long id) {
        return studentRepository.findById(id).map(student -> {
               studentRepository.delete(student);
            return ResponseEntity.ok().build(); // Return 200 OK to indicate successful deletion
        }).orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
    }

    // GET request to retrieve the list of students for a given teacher
    @GetMapping("/teacher/{teacherId}/students")
    public ResponseEntity<List<Student>> getStudentsByTeacherId(@PathVariable Long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Teacher not found"));

        List<Student> students = new ArrayList<>(teacher.getStudents());
        return ResponseEntity.ok(students);
    }

    // GET request to retrieve the list of teachers for a given student
    @GetMapping("/student/{studentId}/teachers")
    public ResponseEntity<List<Teacher>> getTeachersByStudentId(@PathVariable Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));

        List<Teacher> teachers = new ArrayList<>(student.getTeachers());
        return ResponseEntity.ok(teachers);
    }
}
