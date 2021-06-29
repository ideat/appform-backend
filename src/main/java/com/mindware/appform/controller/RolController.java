package com.mindware.appform.controller;

import com.mindware.appform.entity.Rol;
import com.mindware.appform.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value ="/rest",  produces = {"application/json"})
public class RolController {

    @Autowired
    private RolService service;

    @PostMapping(value = "/v1/rol/create", name = "Crear Rol")
    ResponseEntity<?> create(@RequestBody Rol rol){
//        try {
//            Rol result = service.add(rol);
//            return new ResponseEntity<>(result, HttpStatus.CREATED);
//        }catch(Exception e){
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
        Rol result = service.add(rol);
        return new ResponseEntity<>(result, HttpStatus.CREATED);

    }

    @PutMapping(value = "/v1/rol/update", name = "Actualiza Rol")
    ResponseEntity<Rol> update(@RequestBody Rol rol){
        return new ResponseEntity<>(service.update(rol), HttpStatus.OK);
    }

    @GetMapping(value = "/v1/rol/findAll", name = "Obtiene todos los roles")
    ResponseEntity<List<Rol>> findAll(){

       return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }
}
