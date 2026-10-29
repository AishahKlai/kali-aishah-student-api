package kz.kaliaishah.studentapi.repository;

import java.util.List;
import kz.kaliaishah.studentapi.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

    // Spring Data builds the SQL from the method name: WHERE lower(last_name) LIKE lower('%value%')
    List<Student> findByLastNameContainingIgnoreCase(String lastName);
}
