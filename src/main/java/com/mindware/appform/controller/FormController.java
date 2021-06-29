package com.mindware.appform.controller;

import com.mindware.appform.entity.Forms;
import com.mindware.appform.repository.FormsMapper;
import com.mindware.appform.service.FormsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value ="/rest",  produces = {"application/json"})
public class FormController {
    private Integer idClient;

    @Autowired
    private FormsMapper mapper;

    @Autowired
    private FormsService service;

    @PostMapping(value = "/v1/form/create", name = "Crear formulario")
    ResponseEntity<Forms> create (@RequestBody Forms forms){

        return new ResponseEntity<>(service.create(forms), HttpStatus.CREATED);
    }

    @GetMapping(value ="/v1/form/findByIdClient", name ="Formulario por ID Cliente")
    ResponseEntity<Forms> findById(@RequestHeader Map<String, String> headers){
        headers.forEach((key,value) -> {
            if(key.equals("id_client")) idClient = Integer.parseInt(value);
        });

        Forms forms = mapper.findByIdCliente(idClient);

        return new ResponseEntity<>(forms, HttpStatus.OK);
    }

    @PutMapping(value = "/v1/form/update", name = "Actualiza formulario")
    ResponseEntity<Forms> update (@RequestBody Forms forms){

        return new ResponseEntity<>(service.update(forms),HttpStatus.OK);
    }
}
