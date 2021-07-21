package com.mindware.appform.controller;

import com.mindware.appform.entity.netbank.Gblab;
import com.mindware.appform.service.GblabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value ="/rest",  produces = {"application/json"})
public class GblabController {
    private Integer cage;

    private String idcard;

    @Autowired
    private GblabService gblabService;

    @GetMapping(value ="/v1/gblab/findGblabByCage", name ="Actividades del cliente")
    ResponseEntity<List<Gblab>> findGblabByCage(@RequestHeader Map<String, String> headers){
        headers.forEach((key,value) -> {
            if(key.equals("gblabcage")) cage = Integer.parseInt(value);
        });
        List<Gblab> gblabList = gblabService.findGblabByCage(cage);
        return new ResponseEntity<>(gblabList, HttpStatus.OK);
    }


}
