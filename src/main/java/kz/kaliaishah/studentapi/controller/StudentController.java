package kz.kaliaishah.studentapi.controller;

import jakarta.validation.Valid;
import java.util.List;
import kz.kaliaishah.studentapi.exception.StudentNotFoundException;
import kz.kaliaishah.studentapi.model.Student;
import kz.kaliaishah.studentapi.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentRepository repository;

    public StudentController(StudentRepository repository) {
        this.repository = repository;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Student> create(@Valid @RequestBody Student student) {
        student.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(student));
    }

    // READ all
    @GetMapping
    public List<Student> getAll() {
        return repository.findAll();
    }

    // READ one
    @GetMapping("/{id}")
    public Student getById(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
    }
    // READ — search by last name (improvement by Kali Aishah)
    @GetMapping("/search")
    public List<Student> searchByLastName(@RequestParam String lastName) {
        return repository.findByLastNameContainingIgnoreCase(lastName);
    }


    // UPDATE
    @PutMapping("/{id}")
    public Student update(@PathVariable Long id, @Valid @RequestBody Student data) {
        Student student = repository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
        student.setFirstName(data.getFirstName());
        student.setLastName(data.getLastName());
        student.setEmail(data.getEmail());
        student.setCourse(data.getCourse());
        return repository.save(student);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new StudentNotFoundException(id);
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
