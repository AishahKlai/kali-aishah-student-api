package kz.kaliaishah.studentapi;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import kz.kaliaishah.studentapi.model.Student;
import kz.kaliaishah.studentapi.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class StudentSearchTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository repository;

    @Test
    void searchByLastNameFindsKali() throws Exception {
        repository.deleteAll();
        repository.save(new Student("Aishah", "Kali", "kaliaishah@gmail.com", 2));
        repository.save(new Student("Abai", "Asan", "asan@example.com", 3));

        mockMvc.perform(get("/api/students/search").param("lastName", "kali"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].firstName").value("Aishah"));
    }
}
