package kz.kaliaishah.studentapi.repository;

import kz.kaliaishah.studentapi.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
