package com.mindware.appform.controller;

import com.mindware.appform.entity.Parameter;
import com.mindware.appform.service.ParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value ="/rest",  produces = {"application/json"})
public class ParameterController {
    @Autowired
    private ParameterService service;

    @PostMapping(value = "/v1/parameter/create", name = "Crear parametro")
    ResponseEntity<Parameter> create(@RequestBody Parameter parameter){
        if(parameter.getId()==null){
            return new ResponseEntity<>(service.create(parameter), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(service.update(parameter),HttpStatus.OK);
        }
    }

    @GetMapping(value = "/v1/parameter/findAll", name = "Obtiene todos los parametros")
    ResponseEntity<List<Parameter>> findAll(){
        List<Parameter> parameterList = service.findAll();
        return new ResponseEntity<>(parameterList,HttpStatus.OK);
    }

    @GetMapping(value = "/v1/parameter/findAllActive", name = "Obtiene todos los parametros Activos")
    ResponseEntity<List<Parameter>> findAllActive(){
        List<Parameter> parameterList = service.findAllActive();
        return new ResponseEntity<>(parameterList,HttpStatus.OK);
    }

}
