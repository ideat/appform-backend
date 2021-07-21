package com.mindware.appform.controller;

import com.mindware.appform.entity.netbank.Gbpmt;
import com.mindware.appform.service.netabank.GbpmtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value ="/rest",  produces = {"application/json"})
public class GbpmtController {

    @Autowired
    private GbpmtService service;

    @GetMapping(value ="/v1/gbpmt/findAll", name ="Parametros globales")
    ResponseEntity<Gbpmt> findAll(){
        Gbpmt result = service.findAll();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
