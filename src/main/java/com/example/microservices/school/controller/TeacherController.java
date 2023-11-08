package com.example.microservices.school.controller;

import com.example.microservices.school.model.Student;
import com.example.microservices.school.model.Teacher;
import com.example.microservices.school.repository.StudentRepository;
import com.example.microservices.school.repository.TeacherRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/school")
public class TeacherController {
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private StudentRepository studentRepository;

    // Create a new teacher
    @PostMapping("/teachers")
    public ResponseEntity<Teacher> createTeacher(@RequestBody Teacher teacher) {
        Teacher savedTeacher = teacherRepository.save(teacher);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTeacher);
    }

    // Read all teachers
    @GetMapping("/teachers")
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    // Read a single teacher by ID
    @GetMapping("/teachers/{id}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable(value = "id") Long id) {
        Teacher teacher = teacherRepository.findById(id)
                                           .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + id));
        return ResponseEntity.ok().body(teacher);
    }

    // Update a teacher
    @PutMapping("/teachers/{id}")
    public ResponseEntity<Teacher> updateTeacher(@PathVariable(value = "id") Long id, @RequestBody Teacher teacherDetails) {
        Teacher teacher = teacherRepository.findById(id)
                                           .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + id));

        teacher.setName(teacherDetails.getName());
        // You can add more fields to be updated as needed
        Teacher updatedTeacher = teacherRepository.save(teacher);
        return ResponseEntity.ok(updatedTeacher);
    }

    // Delete a teacher
    @DeleteMapping("/teachers/{id}")
    public ResponseEntity<?> deleteTeacher(@PathVariable(value = "id") Long id) {
        Teacher teacher = teacherRepository.findById(id)
                                           .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + id));

                          teacherRepository.delete(teacher);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/teacher/{teacherId}/addstudent")
    public ResponseEntity<Teacher> addStudentByTeacherId(@PathVariable(value = "teacherId") Long teacherId,
                                                         @RequestBody Student student) {
        // Find the teacher by id
        Teacher teacher = teacherRepository.findById(teacherId)
                                           .orElseThrow(() -> new EntityNotFoundException("Teacher not found with id: " + teacherId));
        // Check if the student is new or existing
        Student managedStudent;
        if (student.getId()!= null ) { // If student has an id, we assume it's an existing student
            managedStudent = studentRepository.findById(student.getId())
                                              .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + student.getId()));
        } else {
            // If new student, save the student entity
            managedStudent = studentRepository.save(student);
        }
        // Add the student to the teacher's student collection
        teacher.getStudents().add(managedStudent);
        // Add the teacher to the student's teacher collection
        managedStudent.getTeachers().add(teacher);
        Teacher updatedTeacher = teacherRepository.save(teacher);
        // Save the teacher entity with the updated student collection
        return new ResponseEntity<>(updatedTeacher, HttpStatus.OK);
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
