package kz.kaliaishah.studentapi;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import kz.kaliaishah.studentapi.model.Student;
import kz.kaliaishah.studentapi.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository repository;

    @BeforeEach
    void clean() {
        repository.deleteAll();
    }

    @Test
    void infoShowsStudentName() throws Exception {
        mockMvc.perform(get("/api/info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.student").value("Kali Aishah"));
    }

    @Test
    void createStudent() throws Exception {
        String json = "{\"firstName\":\"Aishah\",\"lastName\":\"Kali\",\"email\":\"kaliaishah@gmail.com\",\"course\":2}";
        mockMvc.perform(post("/api/students").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.lastName").value("Kali"));
    }

    @Test
    void getAllStudents() throws Exception {
        repository.save(new Student("Aishah", "Kali", "kaliaishah@gmail.com", 2));
        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void updateStudent() throws Exception {
        Student saved = repository.save(new Student("Aishah", "Kali", "kaliaishah@gmail.com", 2));
        String json = "{\"firstName\":\"Aishah\",\"lastName\":\"Kali\",\"email\":\"kaliaishah@gmail.com\",\"course\":3}";
        mockMvc.perform(put("/api/students/" + saved.getId()).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.course").value(3));
    }

    @Test
    void deleteStudent() throws Exception {
        Student saved = repository.save(new Student("Aishah", "Kali", "kaliaishah@gmail.com", 2));
        mockMvc.perform(delete("/api/students/" + saved.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void nonexistentStudentReturns404() throws Exception {
        mockMvc.perform(get("/api/students/9999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Student with id 9999 not found"));
    }
}
