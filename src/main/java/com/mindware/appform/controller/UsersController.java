package com.mindware.appform.controller;

import com.mindware.appform.entity.Users;
import com.mindware.appform.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value ="/rest",  produces = {"application/json"})
public class UsersController {

    @Autowired
    private UsersService service;
    
    @PostMapping(value ="/v1/user/add", name = "Crear usuario")
    public ResponseEntity<Users> add(@RequestBody Users users){

        return new ResponseEntity<>(service.add(users), HttpStatus.CREATED);

    }

    @PutMapping(value = "/v1/user/update", name = "Actualizar datos usuario")
    public ResponseEntity<Users> udpate(@RequestBody Users users){
        return new ResponseEntity<>(service.update(users), HttpStatus.OK);
    }

    @PutMapping(value = "/v1/user/updatePassword", name = "Actualizar password usuario")
    public ResponseEntity<Void> udpatePassword(@RequestBody Users users){
        service.updatePassword(users);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/v1/user/findAll", name = "Obtiene todos los usuarios")
    public ResponseEntity<List<Users>> findAll(){
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/v1/user/findByRol", name = "Obtiene todos los usuarios por Rol")
    public ResponseEntity<List<Users>> findByRol(@PathVariable("rolName") String rolName){
        return new ResponseEntity<>(service.findByRol(rolName), HttpStatus.OK);
    }

    @GetMapping(value = "/v1/user/findByLogin/{login}", name = "Obtiene usuario por su login")
    public ResponseEntity<Users> findbyLogin(@PathVariable("login") String login){
        return new ResponseEntity<>(service.findByLogin(login).get(), HttpStatus.OK);
    }

}
