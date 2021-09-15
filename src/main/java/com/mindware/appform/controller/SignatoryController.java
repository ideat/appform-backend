package com.mindware.appform.controller;

import com.mindware.appform.entity.Signatory;
import com.mindware.appform.exceptions.AppException;
import com.mindware.appform.service.SignatoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value ="/rest",  produces = {"application/json"})
public class SignatoryController {

    @Autowired
    private SignatoryService service;

    private String plaza;

    @PostMapping(value ="/v1/signatory/add", name ="Crear Representante Legal" )
    public ResponseEntity<Signatory> add(@RequestBody Signatory signatory){
        if(signatory.getId()==null) {
            return new ResponseEntity<>(service.add(signatory), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(service.update(signatory), HttpStatus.OK);
        }
    }

    @PutMapping(value="/v1/signatory/update", name="Actualizar Representante Legal")
    public ResponseEntity<Signatory> update(@RequestBody Signatory signatory){
        return new ResponseEntity<>(service.update(signatory), HttpStatus.OK);
    }

    @GetMapping(value = "/v1/signatory/findAll", name = "Obtiene todos los Representantes Legales")
    public ResponseEntity<List<Signatory>> findAll(){
        return new ResponseEntity<>(service.findAll(),HttpStatus.OK);
    }

    @GetMapping(value = "/v1/signatory/getByPlaza", name ="Obtiene representante Legal Activo de la Plaza")
    public ResponseEntity<Signatory> getByPlaza(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) -> {
           if(key.equals("plaza")) plaza = value;
        });
        Optional<Signatory> signatory = service.findByPlaza(Integer.valueOf(plaza));
        if(signatory.isPresent()){
            return new ResponseEntity<>(signatory.get(),HttpStatus.OK);
        }else{
            throw new AppException("Plaza no tiene un representante legal", HttpStatus.BAD_REQUEST);
        }
    }

}
