package pe.edu.cibertec.app_t2_pregunta2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@PreAuthorize("hasRole('coordinador')")
@RestController
@RequestMapping("api/v1/t2")
public class TestController {


    @GetMapping
    public ResponseEntity<String> testApiRest(){
        return new ResponseEntity<>("API Autorizada", HttpStatus.OK);
    }
}