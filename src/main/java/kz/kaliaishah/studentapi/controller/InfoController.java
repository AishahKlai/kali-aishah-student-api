package kz.kaliaishah.studentapi.controller;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {

    @Value("${app.student.name}")
    private String studentName;

    @Value("${app.student.id}")
    private String studentId;

    @Value("${app.version}")
    private String version;

    @GetMapping({"/", "/api/info"})
    public Map<String, String> info() {
        Map<String, String> info = new LinkedHashMap<>();
        info.put("application", "kali-aishah-student-api");
        info.put("student", studentName);
        info.put("studentId", studentId);
        info.put("version", version);
        info.put("message", "Student CRUD API by " + studentName);
        return info;
    }
}
