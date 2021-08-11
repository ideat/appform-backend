package com.mindware.appform.controller;

import com.mindware.appform.entity.netbank.Gbcon;
import com.mindware.appform.service.netabank.GbconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value ="/rest",  produces = {"application/json"})
public class GbconController {

    @Autowired
    private GbconService service;

    @GetMapping(value ="/v1/gbcon/findAll", name ="Lista parametros configuracion global")
    ResponseEntity<List<Gbcon>> findAll(){

        List<Gbcon> gbconList = service.findAll();
        return new ResponseEntity<>(gbconList, HttpStatus.OK);
    }

}
